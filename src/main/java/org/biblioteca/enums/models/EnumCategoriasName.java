package org.biblioteca.enums.models;

public enum EnumCategoriasName {
    CATEGORIAS_NAME("Categoria"),
    CATEGORIAS_PK("Codigo");

    private final String value;

    EnumCategoriasName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
