package com.example.demo.model;

import jakarta.persistence.*;


import java.time.LocalDateTime;


@Entity
@Table(name = "cuentas")
public class Cuenta{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numeroDeCuenta;

    @Enumerated(EnumType.STRING)
    private TipoCuenta tipoCuenta;
    private Float saldo;
    private LocalDateTime ultimaTransaccion;
    private Boolean estado = true;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Cuenta() {
    }

    public Cuenta(TipoCuenta tipoCuenta, Float saldo, LocalDateTime ultimaTransaccion, Boolean estado, Usuario usuario) {
        this.tipoCuenta = tipoCuenta;
        this.saldo = saldo;
        this.ultimaTransaccion = ultimaTransaccion;
        this.estado = estado;
        this.usuario = usuario;
    }

    public void setNumeroDeCuenta(Long numeroDeCuenta) {
        this.numeroDeCuenta = numeroDeCuenta;
    }

    public Long getNumeroDeCuenta() {
        return numeroDeCuenta;
    }

    public Float getSaldo() {
        return saldo;
    }

    public void setSaldo(Float saldo) {
        this.saldo = saldo;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public LocalDateTime getUltimaTransaccion() {
        return ultimaTransaccion;
    }

    public void setUltimaTransaccion(LocalDateTime ultimaTransaccion) {
        this.ultimaTransaccion = ultimaTransaccion;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
