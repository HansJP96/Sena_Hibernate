package org.biblioteca;

import org.biblioteca.controller.CategoriasController;
import org.biblioteca.controller.LibrosController;
import org.biblioteca.enums.controller.EnumCategoriasController;
import org.biblioteca.interfaces.controller.GeneralController;
import org.hibernate.Session;

import java.util.Scanner;


/**
 * Hello world!
 *
 */
public class App {

    private static Session session;
    public static final Scanner input = new Scanner(System.in);

            public static void main( String[] args )
    {
        System.out.println("Bienvenido");
        System.out.println("Por favor digite el numero de la tabla en la que desea realizar operaciones:");
        System.out.println("1 => Categorias");
        System.out.println("2 => Editoriales");
        System.out.println("3 => Libros");
        String selection = input.nextLine();
        GeneralController controller;
        switch (selection) {
            case "1":
//                CategoriasRepositoryImp categoriasRepositoryImp = new CategoriasRepositoryImp();
//                CategoriasController categoriasController = new CategoriasController();
                controller = new CategoriasController();
                controller.showCommonOperations2222(EnumCategoriasController.values());
                String opcion = input.nextLine();
                controller.selectedOperationHandler222(opcion);
//                prueba.selectTransaction(categoriasController);
//                prueba.selectTransaction22222(categoriasRepositoryImp);
                return;
            case "2":
                LibrosController librosController = new LibrosController();
//                prueba.selectTransaction(librosController);
                return;
            case "3":
                return;
            default:
                System.out.println("Por favor elija uno de los numeros indicados.");
        }
    }
//    LibrosModel librosModel111 = new LibrosModel();
//        librosModel111.setIsbn("123-3456-7789");
//        librosModel111.setTitulo("La complexia");
//        librosModel111.setDescripcion("Algo normal");
//        librosModel111.setNombreAutor("Ikki seiya");
//        librosModel111.setFechaRegistro(new Timestamp(System.currentTimeMillis()));
//        librosModel111.setPublicacion(LocalDate.of(2001,12, 1));
//    CategoriasModel categoriasModel = new CategoriasModel();
//        categoriasModel.setCodigo(10);
//        librosModel111.setCategoria(categoriasModel);
//    EditorialesModel editorialesModel = new EditorialesModel();
//        editorialesModel.setNit("12325");
//        librosModel111.setEditorial(editorialesModel);
//    LibrosModel librosModel222 = librosRepositoryImp.save(librosModel111);
//        System.out.println(librosModel222);
}
