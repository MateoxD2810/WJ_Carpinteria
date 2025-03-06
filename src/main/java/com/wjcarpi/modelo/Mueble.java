package com.wjcarpi.modelo;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "muebles")
public class Mueble {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombreMueble;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable = false)
    private BigDecimal precio; // Correcto: BigDecimal para mayor precisión financiera

    @Column(nullable = false)
    private Integer stock;

    @Column
    private String imagen;

    // Relación con el usuario que hizo el pedido (clave foránea)
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true) // Relación con 'users(id)'
    private User user;

    // Constructor vacío
    public Mueble() {}

    // Constructor con parámetros para inicializar
    public Mueble(String nombreMueble, String descripcion, BigDecimal precio, Integer stock, String imagen, User user) {
        this.nombreMueble = nombreMueble;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.imagen = imagen;
        this.user = user;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreMueble() {
        return nombreMueble;
    }

    public void setNombreMueble(String nombreMueble) {
        this.nombreMueble = nombreMueble;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Mueble{" +
                "id=" + id +
                ", nombreMueble='" + nombreMueble + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                ", imagen='" + imagen + '\'' +
                ", user=" + (user != null ? user.getUsername() : "N/A") +
                '}';
    }
}
