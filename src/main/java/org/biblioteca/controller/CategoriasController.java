package org.biblioteca.controller;

import org.biblioteca.enums.controller.EnumCategoriasController;
import org.biblioteca.enums.controller.EnumGeneralController;
import org.biblioteca.interfaces.controller.CollectModelDataInterface;
import org.biblioteca.interfaces.controller.GeneralController;
import org.biblioteca.models.CategoriasModel;

import java.util.List;

import static org.biblioteca.App.input;


public class CategoriasController implements GeneralController, CollectModelDataInterface<CategoriasModel> {

    @Override
    public void selectedOperationHandler222(String eleccion) {
        CategoriasModel categoriasModel = fillModelData();
        List<CategoriasModel> response = EnumCategoriasController.values()[Integer.parseInt(eleccion)].bindRepository(categoriasModel);
        response.forEach(System.out::println);
    }

    @Override
    public CategoriasModel fillModelData() {
        String dato;
        CategoriasModel categoriasModel = new CategoriasModel();
        System.out.println("Por favor ingrese los datos necesarios para su operación:");
        model_form:
        while (true) {
            showCategoriaCollectableData();
            dato = input.nextLine();
            switch (dato) {
                case "0":
                    enterCodigo(categoriasModel);
                    break;
                case "1":
                    enterNombre(categoriasModel);
                    break;
                case ":wq":
                    break model_form;
                default:
                    System.out.println("Por favor digite primero la accion que desea realizar, luego podrá digitar el valor correspondiente.");
            }
            System.out.println("Si desea ingresar mas datos digite el numero indicado, si finalizo por favor ingrese ':wq' para ejecutar la operacion.");
        }
        return categoriasModel;
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
