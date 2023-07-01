package org.biblioteca.interfaces.repository;

public interface GeneralTransactionsRepositoryInterface<T> {

    T getByPrimaryKey(Object key);
    T save(T modelObject);
    T delete(T modelObject);
}
