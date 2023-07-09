package org.biblioteca;

import org.biblioteca.controller.CategoriasController;
import org.biblioteca.controller.EditorialController;
import org.biblioteca.controller.LibrosController;
import org.biblioteca.models.CategoriasModel;
import org.biblioteca.models.EditorialesModel;
import org.biblioteca.models.LibrosModel;
import org.biblioteca.repository.CategoriasRepositoryImp;
import org.biblioteca.repository.EditorialesRepositoryImp;
import org.biblioteca.repository.LibrosRepositoryImp;

import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

/**
 * Clase principal para la ejecucion del programa del proyecto de Biblioteca para poder realizar operaciones (CRUD)
 * en tres tablas determinadas: Categorias, Editoriales y Libros
 *
 * @version : 1.1
 * @author: Hansee Jimenez Perez - Sena
 */
public class App {

    public static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        String option;
        System.out.println("Bienvenido");
        while (true) {
            showAvailableEntities();
            String selection = input.nextLine();
            switch (selection) {
                case "1":
                    CategoriasController categoriasController = new CategoriasController();
                    option = categoriasController.selectAvailableOperations(new CategoriasRepositoryImp());
                    categoriasController.executeOperationHandler(option, new CategoriasModel());
                    return;
                case "2":
                    EditorialController editorialController = new EditorialController();
                    option = editorialController.selectAvailableOperations(new EditorialesRepositoryImp());
                    editorialController.executeOperationHandler(option, new EditorialesModel());
                    return;
                case "3":
                    LibrosController librosController = new LibrosController();
                    option = librosController.selectAvailableOperations(new LibrosRepositoryImp());
                    librosController.executeOperationHandler(option, new LibrosModel());
                    return;
                default:
                    System.out.println("Por favor elija uno de los numeros indicados.");
            }
        }
    }

    private static void showAvailableEntities() {
        System.out.println("Digite el numero de la entidad en la que desea realizar operaciones:");
        System.out.println("1 => Categorias");
        System.out.println("2 => Editoriales");
        System.out.println("3 => Libros");
    }
}
