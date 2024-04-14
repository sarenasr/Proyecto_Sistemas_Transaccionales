package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name="oficinas")
public class Oficina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String direccion;
    private int numeroPuntosAtencion;

    @OneToOne
    private Usuario gerente;


    public Oficina(String nombre, String direccion, int numeroPuntosAtencion, Usuario gerente) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.numeroPuntosAtencion = numeroPuntosAtencion;
        this.gerente = gerente;
    }

    public Oficina() {
    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getNumeroPuntosAtencion() {
        return numeroPuntosAtencion;
    }

    public void setNumeroPuntosAtencion(int numeroPuntosAtencion) {
        this.numeroPuntosAtencion = numeroPuntosAtencion;
    }

    public Usuario getGerente() {
        return gerente;
    }

    public void setGerente(Usuario gerente) {
        this.gerente = gerente;
    }

    public Long getIdGerente(){
        return gerente.getId();
    }

}
