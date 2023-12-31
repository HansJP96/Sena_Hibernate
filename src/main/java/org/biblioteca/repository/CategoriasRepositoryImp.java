package org.biblioteca.repository;

import org.biblioteca.enums.ModelInputEnum;
import org.biblioteca.interfaces.annotations.Operation;
import org.biblioteca.interfaces.hibernate.SessionTransactionInterface;
import org.biblioteca.interfaces.hibernate.validators.UpdateValidation;
import org.biblioteca.interfaces.repository.CategoriasRepository;
import org.biblioteca.models.CategoriasModel;

import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;

/**
 * Clase de implementacion de las operaciones del modelo Categorias
 */
public class CategoriasRepositoryImp implements CategoriasRepository, SessionTransactionInterface {

    @Override
    @Operation(id = "1", inputType = ModelInputEnum.OBJECT_PARAM, selectable = "Obtener Categoria por Codigo", result = "La Categoria obtenida es:")
    public CategoriasModel getByPrimaryKey(CategoriasModel key) {
        CategoriasModel getCategoria = null;
        try {
            IntFunction<CategoriasModel> findCategoria =
                    codigo -> session.get(CategoriasModel.class, codigo);
            getCategoria = doTransaction(key.getCodigo(), findCategoria);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getCategoria;
    }

    @Override
    @Operation(id = "2", selectable = "Crear Categoria", result = "La Categoria creada es:")
    public CategoriasModel saveNew(CategoriasModel modelObject) {
        CategoriasModel newCategoria = null;
        try {
            UnaryOperator<CategoriasModel> createCategoria = categoria -> {
                session.persist(categoria);
                return categoria;
            };
            newCategoria = doTransaction(modelObject, createCategoria);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newCategoria;
    }

    @Override
    @Operation(id = "3", opClassRestrict = {UpdateValidation.class}, selectable = "Actualizar Categoria", result = "La nueva Categoria es:")
    public CategoriasModel update(CategoriasModel modelObject) {
        CategoriasModel updatedCategoria = null;
        try {
            UnaryOperator<CategoriasModel> updateCategoria = categoria -> {
                categoria = session.merge(categoria);
                return categoria;
            };
            updatedCategoria = doTransaction(modelObject, updateCategoria);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return updatedCategoria;
    }

    @Override
    @Operation(id = "4", inputType = ModelInputEnum.OBJECT_PARAM, selectable = "Eliminar Categoria por Codigo", result = "La Categoria eliminada fue:")
    public CategoriasModel delete(CategoriasModel key) {
        CategoriasModel deletedCategoria = null;
        try {
            deletedCategoria = getByPrimaryKey(key);
            Consumer<CategoriasModel> deleteCategoria = session::remove;
            if (deletedCategoria != null) doTransaction(deletedCategoria, deleteCategoria);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deletedCategoria;
    }
}
