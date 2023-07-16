package org.biblioteca.enums;

/**
 * Enumerable que encapsula mensajes transversales entre los controladores especificos
 */
public enum CommonOutPrintControllerEnum {
    OPERATION_DATA("Por favor ingrese los datos necesarios para su operación:"),

    OUT_BOUND_OPTION("Por favor digite primero la accion que desea realizar (numero), luego podrá digitar el valor del campo correspondiente."),

    OPERATION_TO_FINISH("Si desea ingresar mas datos digite el numero indicado, si finalizo por favor ingrese ':q' para ejecutar la operacion."),
    UNEXPECTED_PRINT_RESULT("El resultado no se ha podido identificar correctamente.\nPor favor revise los datos ingresados e intentelo nuevamente.");

    private final String value;

    CommonOutPrintControllerEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
