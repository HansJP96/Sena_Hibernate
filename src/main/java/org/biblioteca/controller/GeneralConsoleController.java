package org.biblioteca.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.biblioteca.enums.CommonOutPrintControllerEnum;
import org.biblioteca.enums.ModelInputEnum;
import org.biblioteca.interfaces.annotations.Operation;
import org.biblioteca.interfaces.repository.GeneralRepositoryInterface;
import org.biblioteca.utils.HibernateValidationUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import static org.biblioteca.App.input;


/**
 * La Clase GeneralController sirve como la base de las acciones y propiedades comunes que pueden realizar los controladores
 * hijos de acuerdo al objeto modelo(model) que utilizan.
 * La clase aplica unicamente como controlador de interfaz de linea de comandos.
 *
 * @param <T> Clase modelo que representa una tabla de la Base de Datos
 */
public abstract class GeneralConsoleController<T> {

    protected static final String FINISH_OPERATION_KEY = ":q";
    protected String operationId;
    private GeneralRepositoryInterface<T> repositoryImp;
    private Class<?> repositoryClass;
    private final Validator validator = HibernateValidationUtil.generateValidator();

    /**
     * Permite mostrar y seleccionar las operaciones disponibles para una clase modelo determinada
     *
     * @param impObjectClass Instancia de la clase de implementacion del modelo
     * @param <V>            Interfaz de implementacion especifica que extiende de GeneralRepositoryInterface
     * @return Numero identifcador de la operacion que se desea realizar
     */
    public <V extends GeneralRepositoryInterface<T>> String selectAvailableOperations(V impObjectClass) {
        this.repositoryImp = impObjectClass;
        this.repositoryClass = impObjectClass.getClass();
        System.out.println("Por favor, elija una operacion entre las indicadas (digite numero):");
        List<Method> definedMethods = Arrays.stream(repositoryClass.getDeclaredMethods())
                .filter(Method::isBridge)
                .sorted(Comparator.comparingInt(method -> Integer.parseInt(method.getAnnotation(Operation.class).id())))
                .collect(Collectors.toList());
        HashMap<String, String> operationData = new HashMap<>();
        while (true) {
            printListedOperation(definedMethods, operationData);
            String selection = input.nextLine();
            if (operationData.containsKey(selection)) {
                operationId = selection;
                return selection;
            } else System.out.println("Por favor digite solo el numero de la operacion que desea realizar:");
        }
    }

    /**
     * Imprime las operaciones permitidas que contienen la anotacion Operation
     *
     * @param definedMethods Lista de objetos de la clase Method que contiene los metodos etiquetados con la anotacion Operation
     * @param operationData  Datos de las operaciones disponibles para la interaccion con el usuario
     */
    private void printListedOperation(List<Method> definedMethods, HashMap<String, String> operationData) {
        if (operationData.isEmpty()) {
            for (Method annotatedMethod : definedMethods) {
                Operation annotation = annotatedMethod.getAnnotation(Operation.class);
                String messageOption = String.format("%s. %s%n", annotation.id(), annotation.selectable());
                operationData.put(annotation.id(), messageOption);
                System.out.print(messageOption);
            }
        } else operationData.forEach((opId, opMessage) -> System.out.print(opMessage));
    }

    /**
     * Ejecuta la operacion indicada por el usuario utilizando la clase de implementacion del modelo que corresponde
     * e imprime el resultado que se obtiene de la operacion
     *
     * @param selectedOperation Numero identificador de la operacion que selecciona el usuario
     * @param modelObject       Instancia de clase modelo correspondiente al controlador del modelo especifico
     * @throws InvocationTargetException Captura error en ejecucion del metodo de la clase implementacion de modelo
     * @throws IllegalAccessException    Se ejecuta al intentar acceder a un atributo y/o metodo de la clase implementacion de modelo sin tener acceso directamente
     */
    public void executeOperationHandler(String selectedOperation, T modelObject) throws InvocationTargetException, IllegalAccessException {
        Method[] definedMethods = repositoryClass.getDeclaredMethods();
        for (Method method : definedMethods) {
            if (method.isAnnotationPresent(Operation.class)) {
                Operation annotationData = method.getAnnotation(Operation.class);
                if (annotationData.id().equals(selectedOperation)) {
                    transactionExecution(method, modelObject, annotationData);
                    break;
                }
            }
        }
    }

    /**
     * Ejecuta la transaccion hacia la BD de la operacion indicada
     *
     * @param method         Metodo de implementacion de la entidad modelo que se haya seleccionado
     * @param baseModel      Instancia de clase modelo correspondiente
     * @param annotationData Instancia de la anotacion Operation que contiene la metadata de la informacion a ejecutar
     * @throws InvocationTargetException Captura error en ejecucion del metodo de la clase implementacion de modelo
     * @throws IllegalAccessException    Se ejecuta al intentar acceder a un atributo y/o metodo de la clase implementacion de modelo sin tener acceso directamente
     */
    private void transactionExecution(Method method, T baseModel, Operation annotationData) throws InvocationTargetException, IllegalAccessException {
        boolean isSuccessfulTransaction = true;
        T filledModel = null;
        while (isSuccessfulTransaction) {
            filledModel = fillerSelection(annotationData, filledModel == null ? baseModel : filledModel);
            Object modelResult = method.invoke(repositoryImp, filledModel);
            isSuccessfulTransaction = printModel(modelResult, annotationData);
        }
    }

    /**
     * Permite recolectar el valor de un dato especifico para ejecutar la operacion que lo requiere
     *
     * @return Valor requerido para ejecutar la operacion
     */
    protected abstract T fillModelData();

    /**
     * Permite recolectar los datos necesarios para ejecutar la operacion que lo requiere
     *
     * @param modelInstance Instancia de la clase modelo especifica del controlador
     * @return Instancia de la clase modelo con valores asignados
     */
    protected abstract T fillModelData(T modelInstance);

    /**
     * Imprime los resultados obtenidos de la operacion seleccionada
     *
     * @param model           Instancia de la clase resultado de la operacion seleccionada
     * @param operationObject Instancia de la anotacion Operation
     */
    protected abstract boolean printModel(Object model, Operation operationObject);

    /**
     * Se validan resultados no esperados
     *
     * @param operationId Identificador de la operacion seleccionada
     */
    protected abstract void resultValidation(String operationId);

    /**
     * Permite recolectar la informacion de la instancia modelo indicada segun la informacion requerida para realizar
     * la operacion
     *
     * @param opAnnotation Instancia de la anotacion Operation
     * @param modelObject  Instancia de la clase modelo correspondiente
     * @return Ejecuta el metodo dataValidationProcess
     */
    private T fillerSelection(Operation opAnnotation, T modelObject) {
        if (opAnnotation.inputType() == ModelInputEnum.OBJECT_PARAM) {
            return fillModelData();
        }
        System.out.println(CommonOutPrintControllerEnum.OPERATION_DATA.getValue());
        return dataValidationProcess(modelObject, opAnnotation.opClassRestrict());
    }

    /**
     * Ejecuta el proceso de validacion de la data que contiene la instancia de la clase modelo segun la configuracion de la Entidad
     * @param modelInstance Instancia de la clase modelo base para guardar la informacion que digite el usuario
     * @param operationRestriction Arreglo de clases correspondiente a los grupos de validacion especificados para la operacion
     * @return Instancia de la clase modelo con la informacion verificada para completar la operacion
     */
    private T dataValidationProcess(T modelInstance, Class<?>... operationRestriction) {
        T filledModel = fillModelData(modelInstance);
        if (validateModelData(filledModel, operationRestriction)) {
            System.out.println("Por favor realice la correcion de los datos acorde a las recomendaciones dadas para poder continuar.");
            dataValidationProcess(filledModel, operationRestriction);
        }
        return filledModel;
    }

    /**
     * Ejecuta las validaciones especificadas en la clase modelo como Entidad
     * @param modelType Instancia de la clase modelo a validar
     * @param classRestrictions Arreglo de clases correspondiente a los grupos de validacion especificados para la operacion
     * @return Verdadero, en caso de que se haya encontrado al menos una restriccion de la Entidad vulnerada -
     * Falso, si los datos cumplen correctamente con todas las restricciones indicadas
     */
    private boolean validateModelData(T modelType, Class<?>... classRestrictions) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(modelType, classRestrictions);
        if (!constraintViolations.isEmpty()) {
            constraintViolations.forEach(this::printDataModelError);
            return true;
        }
        return false;
    }

    /**
     * Imprime al usuario las restricciones vulneradas
     * @param constraintViolation Instancia de ConstraintViolation.class enlazada a la Entidad evaluada
     */
    private void printDataModelError(ConstraintViolation<T> constraintViolation) {
        System.out.println("!!! " + constraintViolation.getMessage() + " !!!");
    }
}
