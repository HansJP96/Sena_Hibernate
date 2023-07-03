package org.biblioteca.repository;

import org.biblioteca.interfaces.repository.EditorialesRepository;
import org.biblioteca.models.EditorialesModel;
import org.biblioteca.utils.SessionTransactionUtil;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class EditorialesRepositoryImp extends SessionTransactionUtil implements EditorialesRepository {
    @Override
    public EditorialesModel getByPrimaryKey(Object key) {
        EditorialesModel getEditorial= null;
        try {
            Function<String, EditorialesModel> findEditorial =
                    nit -> session.get(EditorialesModel.class, nit);
            getEditorial = doTransaction((String) key, findEditorial);
        } catch (Exception e){
            e.printStackTrace();
        }
        return  getEditorial;
    }

    @Override
    public EditorialesModel save(EditorialesModel modelObject) {
        EditorialesModel newEditorial = null;
        try {
            UnaryOperator<EditorialesModel> createUpdateEditorial = editorial -> {
                if (editorial.getNit() == null) {
                    session.persist(editorial);
                } else {
                    editorial = session.merge(editorial);
                }
                return editorial;
            };
            newEditorial = doTransaction(modelObject, createUpdateEditorial);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newEditorial;
    }

    @Override
    public EditorialesModel delete(Object key) {
        EditorialesModel deletedEditorial = new EditorialesModel();
        deletedEditorial.setNit((String) key);
        try {
            Consumer<EditorialesModel> deleteEditorial = session::remove;
            doTransaction(deletedEditorial, deleteEditorial);
//            System.out.printf("La editorial con nit:%s fue eliminada exitosamente.", modelObject.getNit());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deletedEditorial;
    }
}
