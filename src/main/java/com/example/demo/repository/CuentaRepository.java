package com.example.demo.repository;

import com.example.demo.model.Cuenta;
//import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
@Transactional
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO cuentas (tipo_cuenta, saldo, ultima_transaccion, estado, usuario_id) " +
            "VALUES (:tipoCuenta, :saldo, :ultimaTransaccion, :estado, :usuarioId)",
            nativeQuery = true)
    void insertCuenta(@Param("tipoCuenta") String tipoCuenta,
                      @Param("saldo") Float saldo,
                      @Param("ultimaTransaccion") LocalDateTime ultimaTransaccion,
                      @Param("estado") Boolean estado,
                      @Param("usuarioId") Long usuarioId);

    @Modifying
    @Transactional
    @Query("UPDATE Cuenta c SET c.estado = :estado WHERE c.numeroDeCuenta = :numeroDeCuenta")
    void updateEstado(@Param("numeroDeCuenta") Long numeroDeCuenta, @Param("estado") Boolean estado);

    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query("UPDATE Cuenta c SET c.saldo = :saldo WHERE c.numeroDeCuenta = :numeroDeCuenta")
    void updateSaldo(@Param("numeroDeCuenta") Long numeroDeCuenta, @Param("saldo") Float saldo);

    Cuenta findByNumeroDeCuenta(Long numeroDeCuenta);

}
