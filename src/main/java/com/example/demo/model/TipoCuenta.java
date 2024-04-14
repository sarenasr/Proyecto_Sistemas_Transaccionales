package com.example.demo.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum TipoCuenta {
    @Enumerated(EnumType.STRING)
    ahorros,
    @Enumerated(EnumType.STRING)
    afc,
    @Enumerated(EnumType.STRING)
    corriente;
}
