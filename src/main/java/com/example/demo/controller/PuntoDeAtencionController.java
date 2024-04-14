package com.example.demo.controller;

import com.example.demo.model.Oficina;
import com.example.demo.model.PuntoDeAtencion;
import com.example.demo.repository.OficinaRepository;
import com.example.demo.repository.PuntoDeAtencionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PuntoDeAtencionController {
    @Autowired
    private PuntoDeAtencionRepository puntoDeAtencionRepository;

    @Autowired
    private OficinaRepository oficinaRepository;

    @GetMapping("/puntos/edit")
    public String editPuntos(Model model) {
        PuntoDeAtencion punto = new PuntoDeAtencion();
        List<Oficina> oficinas = new ArrayList<>(oficinaRepository.findAll());
        List<PuntoDeAtencion> puntos = new ArrayList<>(puntoDeAtencionRepository.findAll());

        model.addAttribute("oficinas", oficinas);
        model.addAttribute("punto", punto);
        model.addAttribute("puntos", puntos);
        return "puntoEdit";
    }

    @PostMapping("/puntos/save")
    public String savePunto(Model model, RedirectAttributes redirectAttributes, PuntoDeAtencion punto) {
        //puntoDeAtencionRepository.save(punto);
        puntoDeAtencionRepository.insertPunto(punto.getTipoAtencion(),punto.getUbicacion(),
                punto.getIdOficina());
        redirectAttributes.addFlashAttribute("message","The punto has been saved");
        return "redirect:/puntos/edit";
    }

    @PostMapping("/puntos/delete")
    public String deletePunto(@RequestParam Integer id, Model model, RedirectAttributes redirectAttributes) {
        //puntoDeAtencionRepository.deleteById(id);
        puntoDeAtencionRepository.deletePunto(id);
        redirectAttributes.addFlashAttribute("message","The punto has been deleted");
        return "redirect:/puntos/edit";
    }
}
