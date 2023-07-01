package org.biblioteca.interfaces.controller;

import java.util.List;

public interface BindControllerRepositoryInterface<T> {

    List<T> bindRepository(T repositoryModel);
}
