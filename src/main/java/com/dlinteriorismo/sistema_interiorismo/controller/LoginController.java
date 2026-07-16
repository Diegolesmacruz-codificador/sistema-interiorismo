package com.dlinteriorismo.sistema_interiorismo.controller;
import com.dlinteriorismo.sistema_interiorismo.model.Usuario;
import com.dlinteriorismo.sistema_interiorismo.repository.UsuarioRepository;
import org.springframework.web.bind.annotation.*;
import com.dlinteriorismo.sistema_interiorismo.dto.LoginRequest;

import java.util.Optional;

@RestController
@RequestMapping("/api/login")
public class LoginController {
    private final UsuarioRepository usuarioRepository;

    public LoginController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    public String login(@RequestBody LoginRequest request) {

        Optional<Usuario> encontrado = usuarioRepository.findByUsuarioAndPassword(
                request.getUsuario(),
                request.getPassword()
        );

        if (encontrado.isPresent()) {
            return "Acceso correcto";
        } else {
            return "Usuario o contraseña incorrectos";
        }
    }
}
