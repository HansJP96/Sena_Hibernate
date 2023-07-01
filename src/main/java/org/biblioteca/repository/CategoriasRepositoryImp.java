package org.biblioteca.repository;

import org.biblioteca.interfaces.repository.CategoriasRepository;
import org.biblioteca.models.CategoriasModel;
import org.biblioteca.utils.SessionTransactionUtil;

import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;

public class CategoriasRepositoryImp extends SessionTransactionUtil implements CategoriasRepository {

    @Override
    public CategoriasModel getByPrimaryKey(Object key) {
        CategoriasModel getCategoria= null;
        try {
            IntFunction<CategoriasModel> findCategoria =
                    codigo -> session.get(CategoriasModel.class, codigo);
            getCategoria = doTransaction((Integer) key, findCategoria);
        } catch (Exception e){
            e.printStackTrace();
        }
        return  getCategoria;
    }

    @Override
    public CategoriasModel save(CategoriasModel modelObject) {
        CategoriasModel newCategoria = null;
        try {
            UnaryOperator<CategoriasModel> createUpdateCategoria = categoria -> {
                if (categoria.getCodigo() == null) {
                    session.persist(categoria);
                } else {
                    categoria = session.merge(categoria);
                }
                return categoria;
            };
            newCategoria = doTransaction(modelObject, createUpdateCategoria);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newCategoria;
    }

    @Override
    public CategoriasModel delete(CategoriasModel modelObject) {
        CategoriasModel deletedCategoria = null;
        try {
            Consumer<CategoriasModel> deleteCategoria = session::remove;
            doTransaction(modelObject, deleteCategoria);
            deletedCategoria = modelObject;
            System.out.printf("La categoria con codigo:%s fue eliminada exitosamente.", modelObject.getCodigo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deletedCategoria;
    }
}
