package org.biblioteca.controller;

import org.biblioteca.enums.ModelInputEnum;
import org.biblioteca.interfaces.controller.Operation;
import org.biblioteca.interfaces.repository.GeneralRepositoryInterface;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.biblioteca.App.input;


public abstract class GeneralController<T> {

    private GeneralRepositoryInterface<T> repositoryImp;
    private Class<?> repositoryClass;

    public <V extends GeneralRepositoryInterface<T>> String showAvailableOperations(V impObjectClass) {
        this.repositoryImp = impObjectClass;
        this.repositoryClass = impObjectClass.getClass();
        System.out.println("Por favor, elija una operacion entre las indicadas (digite numero):");
        Method[] definedMethods = repositoryClass.getDeclaredMethods();
        Collections.reverse(Arrays.asList(definedMethods));
        List<String> listedPosition;
        while (true) {
            listedPosition = printListedOperation(definedMethods);
            String selection = input.nextLine();
            if (listedPosition.contains(selection)) return selection;
            else System.out.println("Por favor digite solo el numero de la operacion que desea realizar:");
        }
    }

    private List<String> printListedOperation(Method[] definedMethods) {
        List<String> listOptions = new ArrayList<>();
        for (Method annotatedMethod : definedMethods) {
            if (annotatedMethod.isAnnotationPresent(Operation.class) && annotatedMethod.isBridge()) {
                Operation annotation = annotatedMethod.getAnnotation(Operation.class);
                System.out.printf("%s. %s%n", annotation.position(), annotation.selectable());
                listOptions.add(annotation.position());
            }
        }
        return listOptions;
    }

    public void selectedOperationHandler(String selectedOperation, T modelObject) throws InvocationTargetException, IllegalAccessException {
        Method[] metodosDefinidos = repositoryClass.getDeclaredMethods();
        for (Method metodo : metodosDefinidos) {
            if (metodo.isAnnotationPresent(Operation.class)) {
                Operation annotationData = metodo.getAnnotation(Operation.class);
                if (annotationData.position().equals(selectedOperation)) {
                    Object model = fillerSelection(annotationData.inputType(), modelObject);
                    printModel(metodo.invoke(repositoryImp, model), annotationData);
                    break;
                }
            }
        }
    }

    protected abstract Object fillModelData();

    protected abstract T fillModelData(T modelInstance);

    protected abstract void printModel(Object model, Operation operationObject);

    private Object fillerSelection(ModelInputEnum modelInputEnum, T modelObject) {
        if (modelInputEnum == ModelInputEnum.OBJECT_PARAM) {
            return fillModelData();
        }
        return fillModelData(modelObject);
    }

}
