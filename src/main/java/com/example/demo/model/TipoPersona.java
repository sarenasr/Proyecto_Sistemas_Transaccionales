package com.example.demo.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum TipoPersona {
    @Enumerated(EnumType.STRING)
    persona_natural,
    @Enumerated(EnumType.STRING)
    persona_juridica
}
