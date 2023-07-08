package org.biblioteca.utils;

import java.time.format.DateTimeFormatter;

/**
 * Clase de utilidades referentes a formatos para la representacion de fechas
 */
public class DateFormatsUtil {

    /**
     * Formato de fecha Dia/Mes/Año
     * <p>
     * Para el caso de los primeros 9 dias/mes se debe anteponer el 0 para tener 2 cifras
     * <p>
     * El año debe ser siempre de 4 digitos
     * @example: 01/10/2014 || 15/05/2020 || 09/08/2001
     */
    public static final DateTimeFormatter DD_MM_YYYY_SLASH_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private DateFormatsUtil() {
    }
}
