package com.example.demo.controller;

import com.example.demo.model.TipoUsuario;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @GetMapping("/usuarios")
    public String getAll(@RequestParam(name = "nombre", required = false) String nombre,
                         @RequestParam(name = "tipoDeUsuario", required = false) String tipoDeUsuario,
                         Model model) {
        List<Usuario> usuarios;

        if (nombre != null && !nombre.isEmpty() && tipoDeUsuario != null && !tipoDeUsuario.isEmpty()) {
            // Convert tipoDeUsuario to TipoUsuario enum and filter by both name and user type
            TipoUsuario tipoUsuarioEnum = TipoUsuario.valueOf(tipoDeUsuario);
            usuarios = usuarioRepository.findByNombreContainingAndTipoUsuario(nombre, tipoUsuarioEnum);
        } else if (nombre != null && !nombre.isEmpty()) {
            // Filter by name only
            usuarios = usuarioRepository.findByNombreContaining(nombre);
        } else if (tipoDeUsuario != null && !tipoDeUsuario.isEmpty()) {
            // Convert tipoDeUsuario to TipoUsuario enum and filter by user type only
            TipoUsuario tipoUsuarioEnum = TipoUsuario.valueOf(tipoDeUsuario);
            usuarios = usuarioRepository.findByTipoUsuario(tipoUsuarioEnum);
        } else {
            // If no search criteria provided, fetch all users
            usuarios = usuarioRepository.findAll();
        }

        model.addAttribute("usuarios", usuarios);
        return "usuarios"; // Return the view name for displaying filtered users
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

