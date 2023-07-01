package org.biblioteca.utils;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;

import static org.biblioteca.utils.SessionConfigUtil.getSessionFactory;

public class SessionTransactionUtil {

    protected static final Session session = getSessionFactory().getCurrentSession();

    protected <T> void doTransaction(T inputType, Consumer<T> operation) {
        Transaction transaction = session.beginTransaction();
        operation.accept(inputType);
        transaction.commit();
    }

    protected <R> R doTransaction(Integer inputType, IntFunction<R> operation) {
        Transaction transaction = session.beginTransaction();
        R returnType = operation.apply(inputType);
        transaction.commit();
        return returnType;
    }

    public <T, R> R doTransaction(T inputType, Function<T, R> operation) {
        Transaction transaction = session.beginTransaction();
        R returnType = operation.apply(inputType);
        transaction.commit();
        return returnType;
    }
}
