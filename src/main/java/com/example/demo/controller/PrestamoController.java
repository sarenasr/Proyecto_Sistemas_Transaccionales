package com.example.demo.controller;

import com.example.demo.model.Prestamo;
import com.example.demo.repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class PrestamoController {

    @Autowired
    PrestamoRepository prestamoRepository;

    @GetMapping("/prestamos")
    public String getPrestamos(Model model) {
        List<Prestamo> prestamos = prestamoRepository.findAll();
        model.addAttribute("prestamos", prestamos);
        return "prestamos";
    }

    @PostMapping("/prestamos/toggleEstado")
    public String toggleEstado(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        // Retrieve the prestamo from the repository
        //System.out.println(id);
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid prestamo id:" + id));

        // Check if the saldo is 0 before toggling the estado
        if (prestamo.getSaldo() != 0.0) {
            // Add an error message to the redirect attributes
            //System.out.println(prestamo.getSaldo());
            redirectAttributes.addFlashAttribute("error", "El saldo del préstamo no es 0");
            return "redirect:/prestamos";
        }

        // Toggle the estado of the prestamo
        prestamo.setEstado(!prestamo.getEstado());

        // Save the updated prestamo in the repository
        prestamoRepository.save(prestamo);

        // Add a flash attribute to indicate successful estado toggle
        redirectAttributes.addFlashAttribute("message", "Estado del préstamo " + id + " cambiado");

        // Redirect back to the page displaying prestamos
        return "redirect:/prestamos";
    }

    @GetMapping("/prestamos/new")
    public String newPrestamo(Model model) {
        model.addAttribute("prestamo", new Prestamo());
        return "prestamoNuevo";
    }

    @PostMapping("/prestamos/save")
    public String insertPrestamo(@ModelAttribute("prestamo") Prestamo prestamo, RedirectAttributes redirectAttributes) {
        prestamo.setSaldo(prestamo.getMonto());
        prestamoRepository.save(prestamo);
        redirectAttributes.addFlashAttribute("prestamo", "The prestamo has been saved successfully");
        return "redirect:/prestamos/new";
    }
}
