package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name="operaciones_prestamos")
public class OperacionPrestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float monto;
    @ManyToOne
    @JoinColumn(name="prestamo")
    private Prestamo prestamo;
    private String tipo;

    public OperacionPrestamo() {}

    public OperacionPrestamo(Float monto, Prestamo prestamo, String tipo) {
        this.monto = monto;
        this.prestamo = prestamo;
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getMonto() {
        return monto;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }

    public Prestamo getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
