package com.example.demo;

import com.example.demo.model.OperacionBancaria;
import com.example.demo.service.OperacionBancariaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class ConcurrenciaTransaccionalTest2 {

    @Autowired
    private OperacionBancariaService operacionBancariaService;

    @Test
    void testConcurrenciaTransaccional() throws ExecutionException, InterruptedException {
        Long cuentaId = 1L; // Supongamos que esta es una cuenta existente con ID 1
        Float montoConsignacion = 1000.0f; // Monto a consignar

        // Iniciamos la consulta de operaciones con Read Committed
        CompletableFuture<List<OperacionBancaria>> consultaFuturo = CompletableFuture.supplyAsync(() ->
                operacionBancariaService.findOperacionesReadCommitted(cuentaId));

        // Simultáneamente, registramos una consignación
        CompletableFuture<Void> consignacionFuturo = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(3000); // Retardo para simular la concurrencia antes de que finalice la consulta
                operacionBancariaService.registrarConsignacion(cuentaId, montoConsignacion);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Esperar a que ambas tareas se completen
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(consultaFuturo, consignacionFuturo);
        allFutures.get(); // Bloquea hasta que todas las futuras tareas completen

        // Recuperamos las operaciones después de las transacciones
        List<OperacionBancaria> operaciones = consultaFuturo.get();

        // Imprimir los resultados para la verificación manual en caso necesario
        operaciones.forEach(op -> System.out.println("Operación: " + op.getTipoOperacion() + ", Valor: " + op.getValor()));

        // Verificaciones: por ejemplo, verificar que el número de operaciones recuperadas es el esperado
        assertNotNull(operaciones);
        assertFalse(operaciones.isEmpty());
        // Verifica si la nueva consignación aparece en el resultado (dependiendo del tiempo de la consulta y la consistencia)
        assertTrue(operaciones.stream().anyMatch(op -> op.getValor().equals(montoConsignacion) && op.getTipoOperacion().equals("consignacion")));
    }
}
