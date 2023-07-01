package org.biblioteca.enums.controller;

import org.biblioteca.interfaces.controller.EnumContollerInterface;

public enum EnumGeneralController implements EnumContollerInterface {

    GET_BY_PK("Obtener %s por %s"),
    SAVE_OBJECT("Crear %s"),
    MODIFIED_OBJECT("Modificar %s"),
    DELETE_OBJECT_BY_PK("Eliminar %s por %s");

    private final String value;

    EnumGeneralController(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public int getPosition() {
        return this.ordinal();
    }
}
