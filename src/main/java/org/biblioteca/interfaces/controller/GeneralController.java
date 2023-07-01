package org.biblioteca.interfaces.controller;

import java.util.Arrays;
import java.util.List;

public interface  GeneralController {

    default void showCommonOperations2222(EnumContollerInterface[] modelOptions) {
        System.out.println("Elija una operacion:");
        Arrays.stream(modelOptions).forEach(enumContollerInterface ->
                System.out.printf("%d. %s%n", enumContollerInterface.getPosition(), enumContollerInterface.getValue()));
    }

    void selectedOperationHandler222(String eleccion);
}
