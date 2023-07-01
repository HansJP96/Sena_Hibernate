package org.biblioteca.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "editoriales", schema = "biblioteca")
public class EditorialesModel {
    @Id
    @Column(name = "nit", nullable = false, length = 20)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String nit;

    @Column(name = "nombre", unique = true, length = 100)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String nombre;

    @Column(name = "email", length = 100)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String email;

    @Column(name = "direccion", length = 100)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String direccion;

    @Column(name = "telefono", length = 15)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String telefono;

    @Column(name = "sitioweb", length = 100)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String sitioweb;

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