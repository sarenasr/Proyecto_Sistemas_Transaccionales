package com.example.demo.controller;

import com.example.demo.model.Cuenta;
import com.example.demo.model.Usuario;
import com.example.demo.repository.CuentaRepository;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CuentaController {
    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/cuentas")
    public String getCuentas(Model model) {
        List<Cuenta> cuentas = cuentaRepository.findAll();
        model.addAttribute("cuentas", cuentas);
        return "cuentas";
    }

    @PostMapping("/cuentas/toggleEstado")
    public String toggleEstado(@RequestParam("numeroDeCuenta") Long numeroDeCuenta, Boolean estado, RedirectAttributes redirectAttributes) {

        cuentaRepository.updateEstado(numeroDeCuenta, estado);
        return "redirect:/cuentas";
    }

    @GetMapping("/cuentas/new")
    public String newCuenta(Model model) {
        Cuenta cuenta = new Cuenta();
        List<Usuario> usuarios = new ArrayList<>(usuarioRepository.findAll());
        model.addAttribute("cuenta", cuenta);
        model.addAttribute("usuarios", usuarios);
        return "cuentaNueva";
    }

    @PostMapping("/cuentas/save")
    public String insertCuenta(@ModelAttribute("cuenta") Cuenta cuenta, RedirectAttributes redirectAttributes) {
        cuentaRepository.insertCuenta(cuenta.getTipoCuenta().name(),cuenta.getSaldo(),
                cuenta.getUltimaTransaccion(),cuenta.getEstado(),cuenta.getUsuario().getId());
        //cuentaRepository.save(cuenta);
        redirectAttributes.addFlashAttribute("cuenta", "The cuenta has been saved successfully");
        return "redirect:/cuentas/new";
    }




}
