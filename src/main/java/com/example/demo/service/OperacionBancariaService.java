package com.example.demo.service;

import com.example.demo.model.Cuenta;
import com.example.demo.model.OperacionBancaria;
import com.example.demo.repository.CuentaRepository;
import com.example.demo.repository.OperacionBancariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;



import java.time.LocalDateTime;
import java.util.List;

@Service
public class OperacionBancariaService {
    @Autowired
    OperacionBancariaRepository operacionBancariaRepository;
    @Autowired
    CuentaRepository cuentaRepository;

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public List<OperacionBancaria> findOperacionesSerializable(long numeroDeCuenta){
        try{
            LocalDateTime date = LocalDateTime.now();
            Cuenta cuenta = cuentaRepository.findById(numeroDeCuenta).get();
            Thread.sleep(30000);
            return operacionBancariaRepository.findByCuentaOrigenNumeroDeCuentaAndFechaBetween(numeroDeCuenta,
                    date.minusDays(30),
                    date);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return List.of();
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public List<OperacionBancaria> findOperacionesRead(long numeroDeCuenta){
        try{
            LocalDateTime date = LocalDateTime.now();
            Cuenta cuenta = cuentaRepository.findById(numeroDeCuenta).get();
            Thread.sleep(30000);
            return operacionBancariaRepository.findByFechaBetweenAndCuentaOrigenNumeroDeCuenta(date.minusDays(30), date,numeroDeCuenta);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return List.of();
        }
    }

    @Transactional
public void registrarConsignacion(Long numeroDeCuenta, Float monto) {
    Cuenta cuenta = cuentaRepository.findById(numeroDeCuenta).orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
    cuenta.setSaldo(cuenta.getSaldo() + monto);  // Asumiendo que se incrementa el saldo
    cuentaRepository.save(cuenta);

    OperacionBancaria operacion = new OperacionBancaria();
    operacion.setCuentaOrigen(cuenta);
    operacion.setValor(monto);
    operacion.setTipoOperacion("consignacion");
    operacion.setFecha(LocalDateTime.now());

    operacionBancariaRepository.save(operacion);
}

    // Método para obtener el saldo actual de una cuenta
    public Float getSaldoActual(Long numeroDeCuenta) {
        return cuentaRepository.findById(numeroDeCuenta)
                               .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"))
                               .getSaldo();
    }

    public List<OperacionBancaria> getRecentOperations(Long numeroDeCuenta) {
        LocalDateTime now = LocalDateTime.now();
        return operacionBancariaRepository.findByCuentaOrigenNumeroDeCuentaAndFechaBetween(numeroDeCuenta, now.minusDays(1), now);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public List<OperacionBancaria> findOperacionesReadCommitted(Long cuentaId) {
        return operacionBancariaRepository.findByCuentaOrigenNumeroDeCuenta(cuentaId);
    }

}
