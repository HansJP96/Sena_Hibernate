package org.biblioteca.interfaces.repository;

import java.lang.reflect.Field;

/**
 * Interfaz auxiliar para actualizar los atributos de un objeto de la misma clase
 * @param <T> Clase entidad modelo
 */
public interface MergeInstanceInterface<T> {

    /**
     * Entrecruza los valores de los atributos de una instancia actual con los nuevos valores asignados a los atributos
     * de una nueva instancia. Solo se actualizan los atributos correctamente asignados (se descartan los nuevos valores en null)
     * @param first Objeto con la nueva informacion
     * @param second Objeto actual
     * @return Objeto actual resultado de los cambios de la nueva informacion
     */
    default T mergeObjects(T first, T second) {
        Class<?> clas = first.getClass();
        Field[] fields = clas.getDeclaredFields();
        Object result = null;
        try {
            result = clas.getDeclaredConstructor().newInstance();
            for (Field field : fields) {
                field.trySetAccessible();
                field.setAccessible(true);
                Object value1 = field.get(first);
                Object value2 = field.get(second);
                Object value = (value1 != null) ? value1 : value2;
                field.set(result, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) result;
    }
}
