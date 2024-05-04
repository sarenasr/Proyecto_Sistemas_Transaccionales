package com.example.demo.repository;

import com.example.demo.model.OperacionBancaria;
//import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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

    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "INSERT INTO operaciones_bancarias (valor, fecha, tipo_operacion, cuenta_origen, cuenta_destino) " +
            "VALUES (:valor, :fecha, :tipoOperacion, :cuentaOrigen, :cuentaDestino)",
            nativeQuery = true)
    void insertRetiro(
            @Param("valor") Float valor,
            @Param("fecha") LocalDateTime fecha,
            @Param("tipoOperacion") String tipoOperacion,
            @Param("cuentaOrigen") Long cuentaOrigen,
            @Param("cuentaDestino") Long cuentaDestino
    );

    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "INSERT INTO operaciones_bancarias (valor, fecha, tipo_operacion, cuenta_origen, cuenta_destino) " +
            "VALUES (:valor, :fecha, :tipoOperacion, :cuentaOrigen, :cuentaDestino)",
            nativeQuery = true)
    void insertConsignacion(
            @Param("valor") Float valor,
            @Param("fecha") LocalDateTime fecha,
            @Param("tipoOperacion") String tipoOperacion,
            @Param("cuentaOrigen") Long cuentaOrigen,
            @Param("cuentaDestino") Long cuentaDestino
    );

    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "INSERT INTO operaciones_bancarias (valor, fecha, tipo_operacion, cuenta_origen, cuenta_destino) " +
            "VALUES (:valor, :fecha, :tipoOperacion, :cuentaOrigen, :cuentaDestino)",
            nativeQuery = true)
    void insertTransferencia(
            @Param("valor") Float valor,
            @Param("fecha") LocalDateTime fecha,
            @Param("tipoOperacion") String tipoOperacion,
            @Param("cuentaOrigen") Long cuentaOrigen,
            @Param("cuentaDestino") Long cuentaDestino
    );

        // Método para encontrar operaciones por el número de cuenta de origen
        @Query("SELECT ob FROM OperacionBancaria ob WHERE ob.cuentaOrigen.numeroDeCuenta = :numeroDeCuenta")
        List<OperacionBancaria> findByCuentaOrigenNumeroDeCuenta(@Param("numeroDeCuenta") Long numeroDeCuenta);
        
    @Transactional(isolation = Isolation.SERIALIZABLE)
    List<OperacionBancaria> findByCuentaOrigenNumeroDeCuentaAndFechaBetween(Long numeroDeCuenta, LocalDateTime localDateTime, LocalDateTime localDateTime1);

    @Transactional(isolation = Isolation.READ_COMMITTED)
    List<OperacionBancaria> findByFechaBetweenAndCuentaOrigenNumeroDeCuenta(LocalDateTime localDateTime, LocalDateTime localDateTime1,Long numeroDeCuenta);


}
