package com.example.demo.controller;

import com.example.demo.model.Cuenta;
import com.example.demo.model.OperacionBancaria;
import com.example.demo.repository.CuentaRepository;
import com.example.demo.repository.OperacionBancariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class OperacionBancariaController {
    @Autowired
    private OperacionBancariaRepository operacionBancariaRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @GetMapping("/operaciones/new")
    public String newOperacionBancaria(Model model){
        OperacionBancaria operacion = new OperacionBancaria();
        List<Cuenta> cuentas = cuentaRepository.findAll();
        model.addAttribute("cuentas", cuentas);
        model.addAttribute("operacion", operacion);
        return "newOperacionBancaria";
    }

    @PostMapping("/operaciones/save")
    public String insertOperacionBancaria(OperacionBancaria operacionBancaria, RedirectAttributes redirectAttributes) {
        Cuenta cuentaOrigen = operacionBancaria.getCuentaOrigen();
        Cuenta cuentaDestino = operacionBancaria.getCuentaDestino();
        String tipoOperacion = operacionBancaria.getTipoOperacion();

        switch (tipoOperacion) {
            case "consignacion":
                if (!cuentaOrigen.equals(cuentaDestino)) {
                    redirectAttributes.addFlashAttribute("error", "For consignacion, origen and destino should be the same");
                    return "redirect:/operaciones/new";
                }
                cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() + operacionBancaria.getValor());
                cuentaRepository.updateSaldo(cuentaOrigen.getNumeroDeCuenta(), cuentaOrigen.getSaldo());
                operacionBancariaRepository.insertConsignacion( operacionBancaria.getValor(),
                        operacionBancaria.getFecha(),
                        operacionBancaria.getTipoOperacion(),
                        cuentaOrigen.getNumeroDeCuenta(),
                        cuentaDestino.getNumeroDeCuenta());
                break;
            case "retiro":
                if (!cuentaOrigen.equals(cuentaDestino)) {
                    redirectAttributes.addFlashAttribute("error", "For retiro, origen and destino should be the same");
                    return "redirect:/operaciones/new";
                }
                if (cuentaOrigen.getSaldo() < operacionBancaria.getValor()) {
                    redirectAttributes.addFlashAttribute("error", "Origin account does not have enough saldo for retiro");
                    return "redirect:/operaciones/new";
                }
                cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - operacionBancaria.getValor());
                cuentaRepository.updateSaldo(cuentaOrigen.getNumeroDeCuenta(), cuentaOrigen.getSaldo());
                operacionBancariaRepository.insertRetiro( operacionBancaria.getValor(),
                        operacionBancaria.getFecha(),
                        operacionBancaria.getTipoOperacion(),
                        cuentaOrigen.getNumeroDeCuenta(),
                        cuentaDestino.getNumeroDeCuenta());
                break;
            case "transferencia":
                if (cuentaOrigen.equals(cuentaDestino)) {
                    redirectAttributes.addFlashAttribute("error", "For transferencia, origen and destino should be different");
                    return "redirect:/operaciones/new";
                }
                if (cuentaOrigen.getSaldo() < operacionBancaria.getValor()) {
                    redirectAttributes.addFlashAttribute("error", "Origin account does not have enough saldo for transferencia");
                    return "redirect:/operaciones/new";
                }
                cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - operacionBancaria.getValor());
                cuentaRepository.updateSaldo(cuentaOrigen.getNumeroDeCuenta(), cuentaOrigen.getSaldo());
                cuentaDestino.setSaldo(cuentaDestino.getSaldo() + operacionBancaria.getValor());
                cuentaRepository.updateSaldo(cuentaDestino.getNumeroDeCuenta(), cuentaDestino.getSaldo());
                operacionBancariaRepository.insertTransferencia( operacionBancaria.getValor(),
                        operacionBancaria.getFecha(),
                        operacionBancaria.getTipoOperacion(),
                        cuentaOrigen.getNumeroDeCuenta(),
                        cuentaDestino.getNumeroDeCuenta());
                break;
            default:
                redirectAttributes.addFlashAttribute("error", "Unknown tipoOperacion: " + tipoOperacion);
                return "redirect:/operaciones/new";
        }

        /*operacionBancariaRepository.insertOperacion(
                operacionBancaria.getValor(),
                operacionBancaria.getFecha(),
                operacionBancaria.getTipoOperacion(),
                cuentaOrigen.getNumeroDeCuenta(),
                cuentaDestino.getNumeroDeCuenta()
        );*/

        return "redirect:/operaciones/new";
    }




}
