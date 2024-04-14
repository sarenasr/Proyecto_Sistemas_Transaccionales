package com.example.demo.controller;

import com.example.demo.model.Oficina;
import com.example.demo.model.Usuario;
import com.example.demo.repository.OficinaRepository;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OficinaController {

    @Autowired
    private OficinaRepository oficinaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/oficinas/new")
    public String addOficina(Model model) {
        Oficina oficina = new Oficina();
        List<Usuario> usuarios = new ArrayList<>(usuarioRepository.findAll());


        model.addAttribute("oficina",oficina);
        model.addAttribute("usuarios", usuarios);
        return "oficinaNueva";
    }

    @PostMapping("/oficinas/save")
    public String saveOficina(Oficina oficina, RedirectAttributes redirectAttributes) {
        oficinaRepository.insertOficina(oficina.getDireccion(),oficina.getNombre(),
                oficina.getNumeroPuntosAtencion(), oficina.getIdGerente());
        //oficinaRepository.save(oficina);
        redirectAttributes.addFlashAttribute("message","The oficina has been saved");
        return "redirect:/oficinas/new";
    }
}
