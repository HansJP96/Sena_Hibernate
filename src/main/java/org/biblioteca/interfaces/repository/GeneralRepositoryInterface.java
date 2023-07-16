package org.biblioteca.interfaces.repository;

/**
 * Interfaz generica con las operaciones comunes hacia la Base de Datos
 * @param <T> Clase correspondiente al modelo que implementa la interfaz especifica
 */
public interface GeneralRepositoryInterface<T> {

    /**
     * Trae el registro de la entidad correspondiente
     * @param key Instancia de la entidad modelo que contiene el atributo correspondiente a la llave primaria que representa la tabla
     * @return Instancia de la clase modelo especifica
     */
    T getByPrimaryKey(T key);

    /**
     * Crea un nuevo registro de la entidad correspondiente
     * @param modelObject Instancia de la entidad modelo con la informacion a guardar
     * @return Instancia de la clase modelo especifica
     */
    T saveNew(T modelObject);

    /**
     * Actualiza un registro de la entidad correspondiente
     * @param modelObject Instancia de la entidad modelo con la informacion para actualizar
     * @return Instancia de la clase modelo especifica
     */
    T update(T modelObject);

    /**
     * Elimina un registro de la entidad correspondiente
     * @param key Instancia de la entidad modelo que contiene el atributo correspondiente a la llave primaria que representa la tabla
     * @return Instancia de la entidad modelo unicamente con el valor de la llave primaria
     */
    T delete(T key);
}
