package com.example.demo.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum TipoId {
    @Enumerated(EnumType.STRING)
    pasaporte,
    @Enumerated(EnumType.STRING)
    cedula,
    @Enumerated(EnumType.STRING)
    tarjeta_de_identidad

}