package org.biblioteca.repository;

import org.biblioteca.interfaces.repository.LibrosRepository;
import org.biblioteca.models.LibrosModel;
import org.biblioteca.utils.SessionTransactionUtil;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class LibrosRepositoryImp extends SessionTransactionUtil implements LibrosRepository {
    @Override
    public LibrosModel getByPrimaryKey(Object key) {
        LibrosModel getLibro= null;
        try {
            Function<String, LibrosModel> findLibro =
                    isbn -> session.get(LibrosModel.class, isbn);
            getLibro = doTransaction((String) key, findLibro);
        } catch (Exception e){
            e.printStackTrace();
        }
        return  getLibro;
    }

    @Override
    public LibrosModel save(LibrosModel modelObject) {
        LibrosModel newLibro = null;
        try {
            UnaryOperator<LibrosModel> createUpdateLibro = libro -> {
                if (libro.getIsbn() == null) {
                    session.persist(libro);
                } else {
                    libro = session.merge(libro);
                }
                return libro;
            };
            newLibro = doTransaction(modelObject, createUpdateLibro);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newLibro;
    }

    @Override
    public LibrosModel delete(Object key) {
        LibrosModel deletedLibro = new LibrosModel();
        deletedLibro.setIsbn((String) key);
        try {
            Consumer<LibrosModel> deleteLibro = session::remove;
            doTransaction(deletedLibro, deleteLibro);
//            System.out.printf("El libro con nit:%s fue eliminada exitosamente.", modelObject.getIsbn());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deletedLibro;
    }
}
