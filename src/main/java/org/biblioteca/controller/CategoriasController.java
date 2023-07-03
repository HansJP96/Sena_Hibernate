package org.biblioteca.controller;

import org.biblioteca.interfaces.controller.Operation;
import org.biblioteca.models.CategoriasModel;

import static org.biblioteca.App.input;


public class CategoriasController extends GeneralController<CategoriasModel> {

    @Override
    public Object fillModelData() {
        System.out.println("Por favor ingrese el Codigo de la Categoria:");
        return input.nextInt();
    }

    @Override
    protected CategoriasModel fillModelData(CategoriasModel modelInstance) {
        String dato;
        System.out.println("Por favor ingrese los datos necesarios para su operación:");
        model_form:
        while (true) {
            showCategoriaCollectableData();
            dato = input.nextLine();
            switch (dato) {
                case "0":
                    enterCodigo(modelInstance);
                    break;
                case "1":
                    enterNombre(modelInstance);
                    break;
                case ":q":
                    break model_form;
                default:
                    System.out.println("Por favor digite primero la accion que desea realizar, luego podrá digitar el valor correspondiente.");
            }
            System.out.println("Si desea ingresar mas datos digite el numero indicado, si finalizo por favor ingrese ':q' para ejecutar la operacion.");
        }
        return modelInstance;
    }

    @Override
    protected void printModel(Object object, Operation operation) {
        if (object instanceof CategoriasModel) {
            System.out.println(operation.result());
            System.out.println(object);
        } else if (object == null) {
            resultValidation(operation.position());
        } else {
            throw new ClassCastException();
        }
    }

    private void resultValidation(String positionOperation) {
        switch (positionOperation) {
            case "1":
            case "3":
                System.out.println("No existe Categoria con ese Codigo");
                break;
            default:
                System.out.println("El resultado no se ha podido identifcar correctamente");
        }
    }

    private void showCategoriaCollectableData() {
        System.out.println("0. Escribir Codigo de Categoria (solo números)");
        System.out.println("1. Escribir Nombre de Categoria");
    }

    private void enterCodigo(CategoriasModel categoriasModel) {
        System.out.print("Digite el Codigo:\t");
        categoriasModel.setCodigo(Integer.parseInt(input.nextLine()));
    }

    private void enterNombre(CategoriasModel categoriasModel) {
        System.out.print("Digite el Nombre:\t");
        categoriasModel.setNombre(input.nextLine());
    }
}
