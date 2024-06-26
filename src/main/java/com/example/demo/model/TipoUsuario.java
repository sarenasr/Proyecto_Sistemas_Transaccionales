package com.example.demo.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum TipoUsuario {
    @Enumerated(EnumType.STRING)
    gerente_general,
    @Enumerated(EnumType.STRING)
    gerente_oficina,
    @Enumerated(EnumType.STRING)
    cajero,
    @Enumerated(EnumType.STRING)
    cliente
}
