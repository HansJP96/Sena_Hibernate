package org.biblioteca.controller;

import org.biblioteca.enums.CommonOutPrintControllerEnum;
import org.biblioteca.enums.ModelInputEnum;
import org.biblioteca.interfaces.annotations.Operation;
import org.biblioteca.interfaces.repository.GeneralRepositoryInterface;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
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
    private GeneralRepositoryInterface<T> repositoryImp;
    private Class<?> repositoryClass;

    /**
     * Permite mostrar y seleccionar las operaciones disponibles para una clase modelo determinada
     * @param impObjectClass Instancia de la clase de implementacion del modelo
     * @param <V> Interfaz de implementacion especifica que extiende de GeneralRepositoryInterface
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
        List<String> listedPosition;
        while (true) {
            listedPosition = printListedOperation(definedMethods);
            String selection = input.nextLine();
            if (listedPosition.contains(selection)) return selection;
            else System.out.println("Por favor digite solo el numero de la operacion que desea realizar:");
        }
    }

    /**
     * Imprime las operaciones permitidas que contienen la anotacion Operation
     * @param definedMethods Lista de objetos de la clase Method que contiene los metodos etiquetados con la anotacion Operation
     * @return Lista de numeros identificadores que contiene la clase de implementacion del modelo
     */
    private List<String> printListedOperation(List<Method> definedMethods) {
        List<String> listOptions = new ArrayList<>();
        for (Method annotatedMethod : definedMethods) {
            Operation annotation = annotatedMethod.getAnnotation(Operation.class);
            System.out.printf("%s. %s%n", annotation.id(), annotation.selectable());
            listOptions.add(annotation.id());
        }
        return listOptions;
    }

    /**
     * Ejecuta la operacion indicada por el usuario utilizando la clase de implementacion del modelo que corresponde
     * e imprime el resultado que se obtiene de la operacion
     * @param selectedOperation Numero identificador de la operacion que selecciona el usuario
     * @param modelObject Instancia de clase modelo correspondiente al controlador del modelo especifico
     * @throws InvocationTargetException Captura error en ejecucion del metodo de la clase implementacion de modelo
     * @throws IllegalAccessException Se ejecuta al intentar acceder a un atributo y/o metodo de la clase implementacion de modelo sin tener acceso directamente
     */
    public void executeOperationHandler(String selectedOperation, T modelObject) throws InvocationTargetException, IllegalAccessException {
        Method[] metodosDefinidos = repositoryClass.getDeclaredMethods();
        for (Method metodo : metodosDefinidos) {
            if (metodo.isAnnotationPresent(Operation.class)) {
                Operation annotationData = metodo.getAnnotation(Operation.class);
                if (annotationData.id().equals(selectedOperation)) {
                    Object model = fillerSelection(annotationData, modelObject);
                    printModel(metodo.invoke(repositoryImp, model), annotationData);
                    break;
                }
            }
        }
    }

    /**
     * Permite recolectar el valor de un dato especifico para ejecutar la operacion que lo requiere
     * @return Valor requerido para ejecutar la operacion
     */
    protected abstract Object fillModelData();

    /**
     * Permite recolectar los datos necesarios para ejecutar la operacion que lo requiere
     * @param modelInstance Instancia de la clase modelo especifica del controlador
     * @param opId Identificador de la operacion a realizar
     * @return Instancia de la clase modelo con valores asignados
     */
    protected abstract T fillModelData(T modelInstance, String opId);

    /**
     * Imprime los resultados obtenidos de la operacion seleccionada
     * @param model Instancia de la clase resultado de la operacion seleccionada
     * @param operationObject Instancia de la anotacion Operation
     */
    protected abstract void printModel(Object model, Operation operationObject);

    /**
     * Se validan resultados no esperados
     * @param operationId Identificador de la operacion seleccionada
     */
    protected abstract void resultValidation(String operationId);

    /**
     * Permite recolectar la informacion de la instancia modelo indicada segun la informacion requerida para realizar
     * la operacion
     * @param opAnnotation Instancia de la anotacion Operation 
     * @param modelObject Instancia de la clase modelo correspondiente
     * @return
     */
    private Object fillerSelection(Operation opAnnotation, T modelObject) {
        if (opAnnotation.inputType() == ModelInputEnum.OBJECT_PARAM) {
            return fillModelData();
        }
        System.out.println(CommonOutPrintControllerEnum.OPERATION_DATA.getValue());
        return fillModelData(modelObject, opAnnotation.id());
    }
}
