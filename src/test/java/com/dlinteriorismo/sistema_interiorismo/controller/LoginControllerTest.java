package com.dlinteriorismo.sistema_interiorismo.controller;

import com.dlinteriorismo.sistema_interiorismo.dto.LoginRequest;
import com.dlinteriorismo.sistema_interiorismo.model.Usuario;
import com.dlinteriorismo.sistema_interiorismo.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LoginControllerTest {

    private UsuarioRepository usuarioRepository;
    private LoginController loginController;

    @BeforeEach
    void setUp() {
        usuarioRepository = Mockito.mock(UsuarioRepository.class);
        loginController = new LoginController(usuarioRepository);
    }

    @Test
    void loginDebeRetornarAccesoCorrecto() {

        LoginRequest request = new LoginRequest();
        request.setUsuario("admin");
        request.setPassword("1234");

        Usuario usuario = new Usuario();

        when(usuarioRepository.findByUsuarioAndPassword(
                request.getUsuario(),
                request.getPassword()))
                .thenReturn(Optional.of(usuario));

        String resultado = loginController.login(request);

        assertEquals("Acceso correcto", resultado);

        verify(usuarioRepository)
                .findByUsuarioAndPassword(
                        request.getUsuario(),
                        request.getPassword());
    }

    @Test
    void loginDebeRetornarCredencialesIncorrectas() {

        LoginRequest request = new LoginRequest();
        request.setUsuario("admin");
        request.setPassword("incorrecta");

        when(usuarioRepository.findByUsuarioAndPassword(
                request.getUsuario(),
                request.getPassword()))
                .thenReturn(Optional.empty());

        String resultado = loginController.login(request);

        assertEquals("Usuario o contraseña incorrectos", resultado);

        verify(usuarioRepository)
                .findByUsuarioAndPassword(
                        request.getUsuario(),
                        request.getPassword());
    }
}