package org.biblioteca.models;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Entidad-modelo Categorias representacion de la tabla "categorias" de la Base de Datos Biblioteca
 */
@Entity
@Table(name = "categorias", schema = "biblioteca")
public class CategoriasModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo", nullable = false)
    @JdbcTypeCode(SqlTypes.INTEGER)
    private Integer codigo;

    @Column(name = "nombre", length = 50)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "codigo = " + codigo + ", " +
                "nombre = " + nombre + ")";
    }
}