package org.biblioteca.repository;

import org.biblioteca.enums.ModelInputEnum;
import org.biblioteca.interfaces.controller.Operation;
import org.biblioteca.interfaces.repository.CategoriasRepository;
import org.biblioteca.models.CategoriasModel;
import org.biblioteca.utils.SessionTransactionUtil;

import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;

public class CategoriasRepositoryImp extends SessionTransactionUtil implements CategoriasRepository {

    @Override
    @Operation(position = "1", inputType = ModelInputEnum.OBJECT_PARAM, selectable = "Obtener Categoria por Codigo", result = "La Categoria obtenida es:")
    public CategoriasModel getByPrimaryKey(Object key) {
        CategoriasModel getCategoria = null;
        try {
            IntFunction<CategoriasModel> findCategoria =
                    codigo -> session.get(CategoriasModel.class, codigo);
            getCategoria = doTransaction((Integer) key, findCategoria);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getCategoria;
    }

    @Override
    @Operation(position = "2", selectable = "Crear/Modificar Categoria", result = "La nueva Categoria es:")
    public CategoriasModel save(CategoriasModel modelObject) {
        CategoriasModel newCategoria = null;
        try {
            UnaryOperator<CategoriasModel> createUpdateCategoria = categoria -> {
                categoria = session.merge(categoria);
                return categoria;
            };
            newCategoria = doTransaction(modelObject, createUpdateCategoria);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newCategoria;
    }

    @Override
    @Operation(position = "3",inputType = ModelInputEnum.OBJECT_PARAM, selectable = "Eliminar Categoria por Codigo", result = "La Categoria eliminada fue:")
    public CategoriasModel delete(Object key) {
        CategoriasModel deletedCategoria = new CategoriasModel();
        deletedCategoria.setCodigo((Integer) key);
        try {
            Consumer<CategoriasModel> deleteCategoria = session::remove;
            doTransaction(deletedCategoria, deleteCategoria);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deletedCategoria;
    }
}
