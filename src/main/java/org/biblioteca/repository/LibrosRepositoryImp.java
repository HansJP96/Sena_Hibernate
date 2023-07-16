package org.biblioteca.repository;

import org.biblioteca.enums.ModelInputEnum;
import org.biblioteca.interfaces.annotations.Operation;
import org.biblioteca.interfaces.hibernate.SessionTransactionInterface;
import org.biblioteca.interfaces.hibernate.validators.CreateValidation;
import org.biblioteca.interfaces.repository.LibrosRepository;
import org.biblioteca.interfaces.repository.MergeInstanceInterface;
import org.biblioteca.models.LibrosModel;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * Clase de implementacion de las operaciones del modelo Libros
 */
public class LibrosRepositoryImp implements LibrosRepository, SessionTransactionInterface, MergeInstanceInterface<LibrosModel> {

    @Override
    @Operation(id = "1", inputType = ModelInputEnum.OBJECT_PARAM, selectable = "Obtener Libro por ISBN", result = "El Libro obtenido es:")
    public LibrosModel getByPrimaryKey(LibrosModel key) {
        LibrosModel getLibro = null;
        try {
            Function<String, LibrosModel> findLibro =
                    isbn -> session.get(LibrosModel.class, isbn);
            getLibro = doTransaction(key.getIsbn(), findLibro);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getLibro;
    }

    @Override
    @Operation(id = "2", opClassRestrict = CreateValidation.class, selectable = "Crear Libro", result = "El Libro creado es:")
    public LibrosModel saveNew(LibrosModel modelObject) {
        LibrosModel newLibro = null;
        try {
            UnaryOperator<LibrosModel> createLibro = libro -> {
                session.persist(libro);
                return libro;
            };
            newLibro = doTransaction(modelObject, createLibro);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newLibro;
    }

    @Override
    @Operation(id = "3", selectable = "Actualizar Libro", result = "El nuevo Libro es:")
    public LibrosModel update(LibrosModel modelObject) {
        LibrosModel updatedLibro = null;
        try {
            UnaryOperator<LibrosModel> updateLibro = newLibro -> {
                LibrosModel currentLibro = session.get(LibrosModel.class, newLibro.getIsbn());
                currentLibro = session.merge(mergeObjects(newLibro, currentLibro));
                return currentLibro;
            };
            updatedLibro = doTransaction(modelObject, updateLibro);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return updatedLibro;
    }

    @Override
    @Operation(id = "4", inputType = ModelInputEnum.OBJECT_PARAM, selectable = "Eliminar Libro por ISBN", result = "El libro eliminado fue:")
    public LibrosModel delete(LibrosModel key) {
        LibrosModel deletedLibro = null;
        try {
            deletedLibro = getByPrimaryKey(key);
            Consumer<LibrosModel> deleteLibro = session::remove;
            if (deletedLibro != null) doTransaction(deletedLibro, deleteLibro);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deletedLibro;
    }
}
