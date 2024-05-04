package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.demo.model.Cuenta;
import com.example.demo.model.OperacionBancaria;
import com.example.demo.service.OperacionBancariaService;
import com.example.demo.repository.CuentaRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@SpringBootTest
public class ConcurrenciaTransaccionalTests {

    @Autowired
    private OperacionBancariaService operacionBancariaService;

    @Autowired
    private CuentaRepository cuentaRepository;

    private Long numeroDeCuenta;

    @BeforeEach
    void setUp() {
        // Crear y guardar una cuenta con un saldo inicial
        Cuenta cuenta = new Cuenta();
        cuenta.setSaldo(10000.0f);  // Saldo inicial
        cuenta = cuentaRepository.save(cuenta);
        numeroDeCuenta = cuenta.getNumeroDeCuenta();
    }

    @Test
    void testConcurrentSerializableTransaction() throws InterruptedException, ExecutionException {
        CompletableFuture<List<OperacionBancaria>> rfc4 = CompletableFuture.supplyAsync(() -> 
            operacionBancariaService.findOperacionesSerializable(numeroDeCuenta)
        );

        Thread.sleep(5000);  // Esperar para simular la demora durante la consulta de RFC4

        CompletableFuture<Void> rf6 = CompletableFuture.runAsync(() -> 
            operacionBancariaService.registrarConsignacion(numeroDeCuenta, 2000.0f)  // Registrar una consignación de 2000
        );

        CompletableFuture.allOf(rfc4, rf6).join();

        // Obtener y validar los resultados
        List<OperacionBancaria> operaciones = rfc4.get();
        Assertions.assertTrue(operaciones.stream().noneMatch(op -> op.getTipoOperacion().equals("consignacion") && op.getValor().equals(2000.0f)),
                "La consignación no debe aparecer en los resultados de RFC4 debido al nivel de aislamiento SERIALIZABLE.");

        Cuenta updatedCuenta = cuentaRepository.findById(numeroDeCuenta).orElseThrow();
        Assertions.assertEquals(12000.0, updatedCuenta.getSaldo(), 0.01, "El saldo de la cuenta debe incluir la consignación después de que RFC4 y RF6 se completen.");
    }
}
