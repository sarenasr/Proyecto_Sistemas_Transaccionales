package com.example.demo.controller;

import com.example.demo.model.OperacionPrestamo;
import com.example.demo.model.Prestamo;
import com.example.demo.repository.OperacionPrestamoRepository;
import com.example.demo.repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class OperacionPrestamoController {

    @Autowired
    private OperacionPrestamoRepository operacionPrestamoRepository;

    @Autowired
    private PrestamoRepository prestamoRepository;

    @GetMapping("/operaciones/prestamos/new")
    public String newOperacionPrestamo(Model model) {
        OperacionPrestamo operacionPrestamo = new OperacionPrestamo();
        List<Prestamo> prestamos = prestamoRepository.findAll();
        model.addAttribute("operacion", operacionPrestamo);
        model.addAttribute("prestamos", prestamos);
        return "newOperacionPrestamo";
    }

    @PostMapping("/operaciones/prestamos/save")
    public String insertOperacionPrestamo(@ModelAttribute("operacion") OperacionPrestamo operacion, RedirectAttributes redirectAttributes) {
        // Calculate new saldo based on the operation type
        Float monto = operacion.getMonto();
        Prestamo prestamo = prestamoRepository.findById(operacion.getPrestamo().getId()).orElse(null);

        if (prestamo != null) {
            Float saldo = prestamo.getSaldo() - monto;
            if (saldo < 0) {
                saldo = 0.0f; // Ensure saldo is not negative
            }
            prestamo.setSaldo(saldo);
            prestamoRepository.save(prestamo);
        }

        // Save the operation
        operacionPrestamoRepository.save(operacion);

        redirectAttributes.addFlashAttribute("message", "La operación de préstamo se ha guardado correctamente.");
        return "redirect:/operaciones/prestamos/new";
    }


}
