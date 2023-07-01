package org.biblioteca.enums.controller;

import org.biblioteca.interfaces.controller.BindControllerRepositoryInterface;
import org.biblioteca.interfaces.controller.EnumContollerInterface;
import org.biblioteca.models.CategoriasModel;
import org.biblioteca.repository.CategoriasRepositoryImp;

import java.util.List;

import static org.biblioteca.enums.models.EnumCategoriasName.CATEGORIAS_NAME;
import static org.biblioteca.enums.models.EnumCategoriasName.CATEGORIAS_PK;
import static org.biblioteca.utils.EnumControllerUtil.modelReference;
import static org.biblioteca.utils.EnumControllerUtil.listResponse;

public enum EnumCategoriasController implements EnumContollerInterface, BindControllerRepositoryInterface<CategoriasModel> {

    GET_BY_PK(modelReference(EnumGeneralController.GET_BY_PK, CATEGORIAS_NAME.getValue(), CATEGORIAS_PK.getValue())) {
        @Override
        public List<CategoriasModel> bindRepository(CategoriasModel repositoryModel) {
            return listResponse(categoriaImp.getByPrimaryKey(repositoryModel.getCodigo()));
        }
    },
    SAVE_OBJECT(modelReference(EnumGeneralController.SAVE_OBJECT, CATEGORIAS_NAME.getValue())) {
        @Override
        public List<CategoriasModel> bindRepository(CategoriasModel repositoryModel) {
            return listResponse(categoriaImp.save(repositoryModel));
        }
    },
    MODIFIED_OBJECT(modelReference(EnumGeneralController.MODIFIED_OBJECT, CATEGORIAS_NAME.getValue())) {
        @Override
        public List<CategoriasModel> bindRepository(CategoriasModel repositoryModel) {
            return listResponse(categoriaImp.save(repositoryModel));
        }
    },
    DELETE_OBJECT_BY_PK(modelReference(EnumGeneralController.DELETE_OBJECT_BY_PK, CATEGORIAS_NAME.getValue(), CATEGORIAS_PK.getValue())) {
        @Override
        public List<CategoriasModel> bindRepository(CategoriasModel repositoryModel) {
            return listResponse(categoriaImp.delete(repositoryModel));
        }
    };

    private static final CategoriasRepositoryImp categoriaImp = new CategoriasRepositoryImp();
    private final String value;

    EnumCategoriasController(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public int getPosition() {
        return this.ordinal();
    }
}
