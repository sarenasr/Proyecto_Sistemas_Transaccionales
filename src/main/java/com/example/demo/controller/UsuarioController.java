package com.example.demo.controller;

import com.example.demo.model.Usuario;
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
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @GetMapping("/usuarios")
    public String getAll(Model model) {

            List<Usuario> usuarios = new ArrayList<>(usuarioRepository.findAll());

            model.addAttribute("usuarios", usuarios);

        return "usuarios";
    }
    @GetMapping("/usuarios/new")
    public String addUsuario(Model model) {
        Usuario usuario = new Usuario();

        model.addAttribute("usuario", usuario);
        return "usuarioNuevo";
    }

    @PostMapping("/usuarios/save")
    public String saveUsuario(Usuario usuario, RedirectAttributes redirectAttributes) {
        try{
            usuarioRepository.insertUsuario(usuario.getLogin(),usuario.getContrasena(),
                    usuario.getNombre(),usuario.getNacionalidad(), usuario.getDireccionFisica(),
                    usuario.getDireccionElectronica(),usuario.getTelefono(),usuario.getCiudad(),
                    usuario.getDepartamento(), usuario.getCodigoPostal(), usuario.getTipoId(),
                    usuario.getTipoUsuario(), usuario.getTipoPersona());
            //usuarioRepository.save(usuario);
            redirectAttributes.addFlashAttribute("message", "The usuario has been saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
        }
        return "redirect:/usuarios";
    }
}

