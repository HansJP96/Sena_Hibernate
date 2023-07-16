package org.biblioteca.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import org.biblioteca.interfaces.hibernate.validators.CreateValidation;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Entidad-modelo Editoriales representacion de la tabla "editoriales" de la Base de Datos Biblioteca
 */
@Entity
@DynamicUpdate
@Table(name = "editoriales", schema = "biblioteca")
public class EditorialesModel {
    @Id
    @Column(name = "nit", nullable = false, length = 20)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @NotNull(message = "{editoriales.nit.NOT_NULL}")
    @Size(max = 20, message = "{editoriales.nit.SIZE}")
    private String nit;

    @Column(name = "nombre", unique = true, length = 100)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @NotBlank(message = "{editoriales.nombre.NOT_BLANK}", groups = CreateValidation.class)
    @Size(min = 2, max = 100, message = "{editoriales.nombre.SIZE}")
    private String nombre;

    @Column(name = "email", length = 100)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Email(message = "{editoriales.email.EMAIL_PATTERN}")
    private String email;

    @Column(name = "direccion", length = 100)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Size(max = 100, message = "{editoriales.direccion.SIZE}")
    private String direccion;

    @Column(name = "telefono", length = 15)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Size(min = 7, max = 10, message = "{editoriales.telefono.SIZE}")
    @Pattern(regexp = "^[0-9]+$", message = "{editoriales.telefono.NUMBER_PATTERN}")
    private String telefono;

    @Column(name = "sitioweb", length = 100)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Pattern(regexp = "(www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()!@:%_+.~#?&/=]*)",
            message = "{editoriales.sitioweb.WEB_PATTERN}")
    private String sitioweb;

    public EditorialesModel() {
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getSitioweb() {
        return sitioweb;
    }

    public void setSitioweb(String sitioweb) {
        this.sitioweb = sitioweb;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "nit = " + nit + ", " +
                "nombre = " + nombre + ", " +
                "email = " + email + ", " +
                "direccion = " + direccion + ", " +
                "telefono = " + telefono + ", " +
                "sitioweb = " + sitioweb + ")";
    }
}