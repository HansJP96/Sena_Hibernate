package org.biblioteca.repository;

import org.biblioteca.enums.ModelInputEnum;
import org.biblioteca.interfaces.annotations.Operation;
import org.biblioteca.interfaces.repository.EditorialesRepository;
import org.biblioteca.interfaces.repository.MergeInstanceInterface;
import org.biblioteca.models.EditorialesModel;
import org.biblioteca.interfaces.hibernate.SessionTransactionUtil;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * Clase de implementacion de las operaciones del modelo Editoriales
 */
public class EditorialesRepositoryImp implements EditorialesRepository, SessionTransactionUtil, MergeInstanceInterface<EditorialesModel> {

    @Override
    @Operation(id = "1", inputType = ModelInputEnum.OBJECT_PARAM, selectable = "Obtener Editorial por Nit", result = "La Editorial obtenida es:")
    public EditorialesModel getByPrimaryKey(Object key) {
        EditorialesModel getEditorial = null;
        try {
            Function<String, EditorialesModel> findEditorial =
                    nit -> session.get(EditorialesModel.class, nit);
            getEditorial = doTransaction((String) key, findEditorial);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getEditorial;
    }

    @Override
    @Operation(id = "2", selectable = "Crear Editorial", result = "La Editorial creada es:")
    public EditorialesModel saveNew(EditorialesModel modelObject) {
        EditorialesModel newEditorial = null;
        try {
            UnaryOperator<EditorialesModel> createEditorial = editorial -> {
                session.persist(editorial);
                return editorial;
            };
            newEditorial = doTransaction(modelObject, createEditorial);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newEditorial;
    }

    @Override
    @Operation(id = "3", selectable = "Actualizar Editorial", result = "La nueva Editorial es:")
    public EditorialesModel update(EditorialesModel modelObject) {
        EditorialesModel updatedEditorial = null;
        try {
            UnaryOperator<EditorialesModel> updateEditorial = newEditorial -> {
                EditorialesModel currentEditorial = session.get(EditorialesModel.class, newEditorial.getNit());
                currentEditorial = session.merge(mergeObjects(newEditorial, currentEditorial));
                return currentEditorial;
            };
            updatedEditorial = doTransaction(modelObject, updateEditorial);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return updatedEditorial;
    }

    @Override
    @Operation(id = "4", inputType = ModelInputEnum.OBJECT_PARAM, selectable = "Eliminar Categoria por Codigo", result = "La Categoria eliminada fue:")
    public EditorialesModel delete(Object key) {
        EditorialesModel deletedEditorial = new EditorialesModel();
        deletedEditorial.setNit((String) key);
        try {
            Consumer<EditorialesModel> deleteEditorial = session::remove;
            doTransaction(deletedEditorial, deleteEditorial);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deletedEditorial;
    }
}
