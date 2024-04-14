package com.example.demo.repository;

import com.example.demo.model.OperacionBancaria;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@Transactional
public interface OperacionBancariaRepository extends JpaRepository<OperacionBancaria, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO operaciones_bancarias (valor, fecha, tipo_operacion, cuenta_origen, cuenta_destino) " +
            "VALUES (:valor, :fecha, :tipoOperacion, :cuentaOrigen, :cuentaDestino)",
            nativeQuery = true)
    void insertOperacion(
            @Param("valor") Float valor,
            @Param("fecha") LocalDateTime fecha,
            @Param("tipoOperacion") String tipoOperacion,
            @Param("cuentaOrigen") Long cuentaOrigen,
            @Param("cuentaDestino") Long cuentaDestino
    );
}