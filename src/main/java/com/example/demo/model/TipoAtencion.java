package com.example.demo.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum TipoAtencion {
    @Enumerated(EnumType.STRING)
    atencion_personalizada,
    @Enumerated(EnumType.STRING)
    cajero_automatico,
    @Enumerated(EnumType.STRING)
    digital;

}
