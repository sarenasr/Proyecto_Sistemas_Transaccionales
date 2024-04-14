package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "puntos_de_atencion")
public class PuntoDeAtencion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ubicacion;
    @Enumerated(EnumType.STRING)
    private TipoAtencion tipoAtencion;

    @OneToOne
    private Oficina oficina;

    public PuntoDeAtencion() {
    }

    public PuntoDeAtencion(String ubicacion, TipoAtencion tipoAtencion, Oficina oficina) {
        this.ubicacion = ubicacion;
        this.tipoAtencion = tipoAtencion;
        this.oficina = oficina;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public TipoAtencion getTipoAtencion() {
        return tipoAtencion;
    }

    public void setTipoAtencion(TipoAtencion tipoAtencion) {
        this.tipoAtencion = tipoAtencion;
    }

    public Oficina getOficina() {
        return oficina;
    }

    public void setOficina(Oficina oficina) {
        this.oficina = oficina;
    }

    public long getIdOficina(){
        return oficina.getId();
    }
}
