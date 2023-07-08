package org.biblioteca.controller;

import org.biblioteca.enums.CommonOutPrintControllerEnum;
import org.biblioteca.interfaces.annotations.Operation;
import org.biblioteca.models.EditorialesModel;

import java.util.List;

import static org.biblioteca.App.input;

public class EditorialController extends GeneralConsoleController<EditorialesModel> {

    private final List<String> opRequireNit = List.of("2","3");

    @Override
    protected Object fillModelData() {
        System.out.println("Por favor ingrese el Nit de la Editorial:");
        return input.next();
    }

    @Override
    protected EditorialesModel fillModelData(EditorialesModel modelInstance, String opId) {
        String inputValue;
        model_form:
        while (true) {
            showEditorialCollectableData();
            inputValue = input.nextLine();
            switch (inputValue) {
                case "1":
                    enterNit(modelInstance);
                    break;
                case "2":
                    enterNombre(modelInstance);
                    break;
                case "3":
                    enterEmail(modelInstance);
                    break;
                case "4":
                    enterTelefono(modelInstance);
                    break;
                case "5":
                    enterDireccion(modelInstance);
                    break;
                case "6":
                    enterSitioWeb(modelInstance);
                    break;
                case FINISH_OPERATION_KEY:
                    break model_form;
                default:
                    System.out.println(CommonOutPrintControllerEnum.OUT_BOUND_OPTION.getValue());
            }
            System.out.println(CommonOutPrintControllerEnum.OPERATION_TO_FINISH.getValue());
        }
        if (modelInstance.getNit() == null && opRequireNit.contains(opId)) {
            System.out.println("Se requiere colocar un Nit de Editorial para completar la Operacion.");
            fillModelData(modelInstance, opId);
        }
        return modelInstance;
    }

    @Override
    protected void printModel(Object model, Operation operationObject) {
        if (model instanceof EditorialesModel) {
            System.out.println(operationObject.result());
            System.out.println(model);
        } else if (model == null) {
            resultValidation(operationObject.id());
        } else {
            throw new ClassCastException();
        }
    }

    protected void resultValidation(String operationId) {
        switch (operationId) {
            case "1":
            case "4":
                System.out.println("No existe la Editorial con ese Nit");
                break;
            default:
                System.out.println(CommonOutPrintControllerEnum.UNEXPECTED_PRINT_RESULT.getValue());
        }
    }

    /**
     * Muestra la disponibilidad de datos que puede recibir el modelo de Editorial
     */
    private void showEditorialCollectableData() {
        System.out.println("1. Escribir Nit de Editorial");
        System.out.println("2. Escribir Nombre de Editorial");
        System.out.println("3. Escribir Email de Editorial");
        System.out.println("4. Escribir Telefono de Editorial");
        System.out.println("5. Escribir Direccion de Editorial");
        System.out.println("6. Escribir enlace Sitio Web de Editorial");
    }

    private void enterSitioWeb(EditorialesModel modelInstance) {
        System.out.print("Digite el enlace del Sitio Web de la Editorial:\t");
        modelInstance.setSitioweb(input.nextLine());
    }

    private void enterDireccion(EditorialesModel modelInstance) {
        System.out.print("Digite la Direccion:\t");
        modelInstance.setDireccion(input.nextLine());
    }

    private void enterTelefono(EditorialesModel modelInstance) {
        System.out.print("Digite el Telefono:\t");
        modelInstance.setTelefono(input.nextLine());
    }

    private void enterEmail(EditorialesModel modelInstance) {
        System.out.print("Digite el Email:\t");
        modelInstance.setEmail(input.nextLine());
    }

    private void enterNombre(EditorialesModel modelInstance) {
        System.out.print("Digite el Nombre de la Editorial:\t");
        modelInstance.setNombre(input.nextLine());
    }

    private void enterNit(EditorialesModel modelInstance) {
        System.out.print("Digite el Nit:\t");
        modelInstance.setNit(input.nextLine());
    }
}
