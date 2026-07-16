package com.dlinteriorismo.sistema_interiorismo.controller;

import com.dlinteriorismo.sistema_interiorismo.dto.ClienteRequest;
import com.dlinteriorismo.sistema_interiorismo.model.Cliente;
import com.dlinteriorismo.sistema_interiorismo.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteControllerTest {

    private ClienteService clienteService;
    private ClienteController clienteController;

    @BeforeEach
    void setUp() {
        clienteService = Mockito.mock(ClienteService.class);
        clienteController = new ClienteController(clienteService);
    }

    @Test
    void listarDebeRetornarLista() {

        Cliente cliente = new Cliente();
        cliente.setNombre("Juan");

        when(clienteService.listar())
                .thenReturn(List.of(cliente));

        List<Cliente> resultado = clienteController.listar();

        assertEquals(1, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());

        verify(clienteService).listar();
    }

    @Test
    void guardarDebeLlamarAlServicio() {

        ClienteRequest request = new ClienteRequest();
        request.setNombre("Carlos");
        request.setTelefono("999999999");
        request.setDireccion("Lima");

        Cliente cliente = new Cliente();
        cliente.setNombre("Carlos");

        when(clienteService.guardar(request))
                .thenReturn(cliente);

        Cliente resultado = clienteController.guardar(request);

        assertNotNull(resultado);
        assertEquals("Carlos", resultado.getNombre());

        verify(clienteService).guardar(request);
    }

    @Test
    void eliminarDebeLlamarAlServicio() {

        doNothing().when(clienteService).eliminar(1);

        clienteController.eliminar(1);

        verify(clienteService).eliminar(1);
    }
}