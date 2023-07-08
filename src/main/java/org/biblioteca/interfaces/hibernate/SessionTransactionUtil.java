package org.biblioteca.interfaces.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;

import static org.biblioteca.utils.SessionConfigUtil.getSessionFactory;

/**
 * Interfaz que define la estructura para las transacciones disponibles de la sesion de Hibernate
 */
public interface SessionTransactionUtil {

    Session session = getSessionFactory().getCurrentSession();

    /**
     * Ejecuta la transaccion de Hibernate mediante una expresion Consumer que devuelve una instancia modelo
     * @param returnType Instancia de la clase entidad-modelo que se retorna de la operacion
     * @param operation Expresion lambda con la operacion de Hibernate que retorna un objeto de la clase T
     * @param <T> Clase entidad-modelo
     */
    default  <T> void doTransaction(T returnType, Consumer<T> operation) {
        Transaction transaction = session.beginTransaction();
        operation.accept(returnType);
        transaction.commit();
    }

    /**
     * @param inputType Numero como valor de entrada requerido para la operacion de Hibernate
     * @param operation Expresion lambda que recibe como argumento un Entero para la operacion de Hibernate y
     *                  retorna un objeto de la clase R
     * @param <R> Clase entidad-modelo
     * @return Instancia de la clase R
     */
    default  <R> R doTransaction(Integer inputType, IntFunction<R> operation) {
        Transaction transaction = session.beginTransaction();
        R returnType = operation.apply(inputType);
        transaction.commit();
        return returnType;
    }

    /**
     * @param inputType Objeto de la clase T como argumento requerido para la operacion de Hibernate
     * @param operation Expresion lambda que recibe como argumento una clase T para la operacion de Hibernate
     *                  y retorna un objeto de la clase R
     * @param <T> Clase argumento de insumo para la expresion
     * @param <R> Clase entidad-modelo
     * @return Instancia de la clase R
     */
    default  <T, R> R doTransaction(T inputType, Function<T, R> operation) {
        Transaction transaction = session.beginTransaction();
        R returnType = operation.apply(inputType);
        transaction.commit();
        return returnType;
    }
}
