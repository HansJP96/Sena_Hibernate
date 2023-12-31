package org.biblioteca.controller;

import org.biblioteca.enums.CommonOutPrintControllerEnum;
import org.biblioteca.interfaces.annotations.Operation;
import org.biblioteca.models.CategoriasModel;

import static org.biblioteca.App.input;

/**
 * La clase CategoriasController funciona como punto de acceso a las operaciones que se pueden llevar a cabo para el
 * modelo de Categoria asi como de proporcionar la interfaz de interaccion mediante consola entre usuario y acciones disponibles
 */
public class CategoriasController extends GeneralConsoleController<CategoriasModel> {

    @Override
    protected CategoriasModel fillModelData() {
        CategoriasModel model = new CategoriasModel();
        System.out.println("Por favor ingrese el Codigo de la Categoria:");
        model.setCodigo(input.nextInt());
        return model;
    }

    @Override
    protected CategoriasModel fillModelData(CategoriasModel modelInstance) {
        String inputValue;
        model_form:
        while (true) {
            showCategoriaCollectableData();
            inputValue = input.nextLine();
            switch (inputValue) {
                case "1":
                    enterCodigo(modelInstance);
                    break;
                case "2":
                    enterNombre(modelInstance);
                    break;
                case FINISH_OPERATION_KEY:
                    break model_form;
                default:
                    System.out.println(CommonOutPrintControllerEnum.OUT_BOUND_OPTION.getValue());
            }
            System.out.println(CommonOutPrintControllerEnum.OPERATION_TO_FINISH.getValue());
        }
        return modelInstance;
    }

    @Override
    protected boolean printModel(Object model, Operation operationObject) {
        if (model instanceof CategoriasModel) {
            System.out.println(operationObject.result());
            System.out.println(model);
            return false;
        } else if (model == null) {
            resultValidation(operationObject.id());
            return true;
        } else {
            throw new ClassCastException();
        }
    }

    protected void resultValidation(String operationId) {
        switch (operationId) {
            case "1":
            case "4":
                System.out.println("No existe Categoria con ese Codigo");
                break;
            default:
                System.out.println(CommonOutPrintControllerEnum.UNEXPECTED_PRINT_RESULT.getValue());
        }
    }

    /**
     * Muestra la disponibilidad de datos que puede recibir el modelo de Categoria
     */
    private void showCategoriaCollectableData() {
        System.out.println("1. Escribir Codigo de Categoria (solo números)");
        System.out.println("2. Escribir Nombre de Categoria");
    }

    private void enterNombre(CategoriasModel modelInstance) {
        System.out.print("Digite el Nombre:\t");
        modelInstance.setNombre(input.nextLine());
    }

    private void enterCodigo(CategoriasModel modelInstance) {
        System.out.print("Digite el Codigo:\t");
        modelInstance.setCodigo(Integer.parseInt(input.nextLine()));
    }
}
