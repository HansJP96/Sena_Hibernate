package org.biblioteca.interfaces.repository;

public interface GeneralRepositoryInterface<T> {

    T getByPrimaryKey(Object key);
    T save(T modelObject);
    T delete(Object key);
}
