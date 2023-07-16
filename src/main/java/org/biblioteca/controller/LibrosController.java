package org.biblioteca.controller;

import org.biblioteca.enums.CommonOutPrintControllerEnum;
import org.biblioteca.interfaces.annotations.Operation;
import org.biblioteca.models.CategoriasModel;
import org.biblioteca.models.EditorialesModel;
import org.biblioteca.models.LibrosModel;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import static org.biblioteca.App.input;
import static org.biblioteca.utils.DateFormatsUtil.DD_MM_YYYY_SLASH_FORMAT;

public class LibrosController extends GeneralConsoleController<LibrosModel> {

    private final List<String> opRequireCurrentDate = List.of("2");

    @Override
    protected LibrosModel fillModelData() {
        LibrosModel model = new LibrosModel();
        System.out.println("Por favor ingrese el ISBN del Libro:");
        model.setIsbn(input.next());
        return model;
    }

    @Override
    protected LibrosModel fillModelData(LibrosModel modelInstance) {
        String inputValue;
        model_form:
        while (true) {
            showLibroCollectableData();
            inputValue = input.nextLine();
            switch (inputValue) {
                case "1":
                    enterIsbn(modelInstance);
                    break;
                case "2":
                    enterTitulo(modelInstance);
                    break;
                case "3":
                    enterDescripcion(modelInstance);
                    break;
                case "4":
                    enterNombreAutor(modelInstance);
                    break;
                case "5":
                    enterFechaPublicacion(modelInstance);
                    break;
                case "6":
                    enterNitEditorial(modelInstance);
                    break;
                case "7":
                    enterCodigoCategoria(modelInstance);
                    break;
                case FINISH_OPERATION_KEY:
                    break model_form;
                default:
                    System.out.println(CommonOutPrintControllerEnum.OUT_BOUND_OPTION.getValue());
            }
            System.out.println(CommonOutPrintControllerEnum.OPERATION_TO_FINISH.getValue());
        }
        if (opRequireCurrentDate.contains(operationId)) addFechaRegistro(modelInstance);
        return modelInstance;
    }

    @Override
    protected boolean printModel(Object model, Operation operationObject) {
        if (model instanceof LibrosModel) {
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
                System.out.println("No existe la Libro con ese ISBN");
                break;
            default:
                System.out.println(CommonOutPrintControllerEnum.UNEXPECTED_PRINT_RESULT.getValue());
        }
    }

    /**
     * Muestra la disponibilidad de datos que puede recibir el modelo de Libro
     */
    private void showLibroCollectableData() {
        System.out.println("1. Escribir ISBN de Libro");
        System.out.println("2. Escribir Titulo del Libro");
        System.out.println("3. Escribir Descripcion del Libro");
        System.out.println("4. Escribir Nombre del Autor del Libro");
        System.out.println("5. Escribir Fecha de Publicacion del Libro (formato: DD/MM/YYYY)");
        System.out.println("6. Escribir Nit de la Editorial a la que pertenece el Libro");
        System.out.println("7. Escribir Codigo de Categoria del Libro (solo números)");
    }

    private void enterIsbn(LibrosModel modelInstance) {
        System.out.print("Digite el ISBN:\t");
        modelInstance.setIsbn(input.nextLine());
    }

    private void enterTitulo(LibrosModel modelInstance) {
        System.out.print("Digite el Titulo:\t");
        modelInstance.setTitulo(input.nextLine());
    }

    private void enterDescripcion(LibrosModel modelInstance) {
        System.out.print("Digite una Descripcion:\t");
        modelInstance.setDescripcion(input.nextLine());
    }

    private void enterNombreAutor(LibrosModel modelInstance) {
        System.out.print("Digite el Nombre del Autor:\t");
        modelInstance.setNombreAutor(input.nextLine());
    }

    private void enterFechaPublicacion(LibrosModel modelInstance) {
        try {
            System.out.print("Digite la Fecha de Publicacion:\t");
            String fecha = input.nextLine();
            modelInstance.setPublicacion(LocalDate.parse(fecha, DD_MM_YYYY_SLASH_FORMAT));
        } catch (DateTimeParseException e){
            System.out.println("Por favor digite una Fecha con el formato indicado Ej: 01/01/2010");
            enterFechaPublicacion(modelInstance);
        }
    }

    private void enterNitEditorial(LibrosModel modelInstance) {
        System.out.print("Digite el Nit de la Editorial:\t");
        EditorialesModel editorialesModel = new EditorialesModel();
        editorialesModel.setNit(input.nextLine());
        modelInstance.setEditorial(editorialesModel);
    }

    private void enterCodigoCategoria(LibrosModel modelInstance) {
        System.out.print("Digite el Codigo de la Categoria:\t");
        CategoriasModel categoriasModel = new CategoriasModel();
        categoriasModel.setCodigo(Integer.parseInt(input.nextLine()));
        modelInstance.setCategoria(categoriasModel);
    }

    private void addFechaRegistro(LibrosModel modelInstance) {
        modelInstance.setFechaRegistro(new Timestamp(System.currentTimeMillis()));
    }
}
