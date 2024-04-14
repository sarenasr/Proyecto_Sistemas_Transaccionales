package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name="prestamos")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float monto;
    private Float saldo;
    private Float interes;
    private int cuotas;
    private int diaDePago;
    private Long valorCuota;
    private Boolean estado = true;
    private String tipo;

    public Prestamo() {
    }

    public Prestamo(Float monto,Float saldo,Float interes, int cuotas, int diaDePago, Long valorCuota, Boolean estado, String tipo) {
        this.monto = monto;
        this.saldo = saldo;
        this.interes = interes;
        this.cuotas = cuotas;
        this.diaDePago = diaDePago;
        this.valorCuota = valorCuota;
        this.estado = estado;
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

    public Float getInteres() {
        return interes;
    }

    public void setInteres(Float interes) {
        this.interes = interes;
    }

    public int getCuotas() {
        return cuotas;
    }

    public void setCuotas(int cuotas) {
        this.cuotas = cuotas;
    }

    public int getDiaDePago() {
        return diaDePago;
    }

    public void setDiaDePago(int diaDePago) {
        this.diaDePago = diaDePago;
    }

    public Long getValorCuota() {
        return valorCuota;
    }

    public void setValorCuota(Long valorCuota) {
        this.valorCuota = valorCuota;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Float getSaldo() {
        return saldo;
    }

    public void setSaldo(Float saldo) {
        this.saldo = saldo;
    }
}
