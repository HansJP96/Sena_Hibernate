package org.biblioteca.models;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * Entidad-modelo Libros representacion de la tabla "libros" de la Base de Datos Biblioteca
 */
@Entity
@DynamicUpdate
@Table(name = "libros", schema = "biblioteca")
public class LibrosModel {
    @Id
    @Column(name = "isbn", nullable = false, length = 20)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String isbn;

    @Column(name = "titulo", length = 100)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String titulo;

    @Column(name = "nombre_autor", length = 100)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String nombreAutor;

    @Column(name = "descripcion")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String descripcion;

    @Column(name = "publicacion")
    @JdbcTypeCode(SqlTypes.DATE)
    private LocalDate publicacion;

    @Column(name = "fecha_registro")
    @JdbcTypeCode(SqlTypes.TIMESTAMP)
    private Timestamp fechaRegistro;

    @ManyToOne(optional = false)
    @JoinColumn(name = "codigo_categoria", nullable = false)
    private CategoriasModel categoria;

    @ManyToOne(optional = false)
    @JoinColumn(name = "nit_editorial", nullable = false)
    private EditorialesModel editorial;

    public EditorialesModel getEditorial() {
        return editorial;
    }

    public void setEditorial(EditorialesModel editorial) {
        this.editorial = editorial;
    }

    public CategoriasModel getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriasModel categoria) {
        this.categoria = categoria;
    }

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public LocalDate getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(LocalDate publicacion) {
        this.publicacion = publicacion;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "isbn = " + isbn + ", " +
                "titulo = " + titulo + ", " +
                "nombreAutor = " + nombreAutor + ", " +
                "descripcion = " + descripcion + ", " +
                "publicacion = " + publicacion + ", " +
                "fechaRegistro = " + fechaRegistro + ", " +
                "categoria = " + categoria + ", " +
                "editorial = " + editorial + ")";
    }
}