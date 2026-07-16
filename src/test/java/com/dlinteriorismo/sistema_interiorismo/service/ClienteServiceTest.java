package com.dlinteriorismo.sistema_interiorismo.service;

import com.dlinteriorismo.sistema_interiorismo.dto.ClienteRequest;
import com.dlinteriorismo.sistema_interiorismo.model.Cliente;
import com.dlinteriorismo.sistema_interiorismo.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private ClienteRequest request;
    private Cliente cliente;

    @BeforeEach
    void setUp() {

        request = new ClienteRequest();
        request.setNombre("Juan Perez");
        request.setDni("12345678");
        request.setTelefono("987654321");
        request.setCorreo("juan@gmail.com");
        request.setDireccion("Lima");

        cliente = new Cliente();
        cliente.setIdCliente(1);
        cliente.setNombre(request.getNombre());
        cliente.setDni(request.getDni());
        cliente.setTelefono(request.getTelefono());
        cliente.setCorreo(request.getCorreo());
        cliente.setDireccion(request.getDireccion());
    }

    @Test
    void listarDebeRetornarLista() {

        when(clienteRepository.findAll()).thenReturn(List.of(cliente));

        List<Cliente> resultado = clienteService.listar();

        assertEquals(1, resultado.size());
        assertEquals("Juan Perez", resultado.get(0).getNombre());

        verify(clienteRepository).findAll();
    }

    @Test
    void guardarClienteCorrectamente() {

        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente resultado = clienteService.guardar(request);

        assertNotNull(resultado);
        assertEquals("Juan Perez", resultado.getNombre());

        verify(clienteRepository).save(any(Cliente.class));
    }

    @Test
    void nombreVacioDebeLanzarExcepcion() {

        request.setNombre("");

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> clienteService.guardar(request));

        assertEquals("El nombre es obligatorio", ex.getMessage());
    }

    @Test
    void dniConMenosDigitosDebeLanzarExcepcion() {

        request.setDni("123");

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> clienteService.guardar(request));

        assertEquals("El DNI debe tener 8 dígitos", ex.getMessage());
    }

    @Test
    void dniConLetrasDebeLanzarExcepcion() {

        request.setDni("12AB5678");

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> clienteService.guardar(request));

        assertEquals("El DNI debe tener 8 dígitos", ex.getMessage());
    }

    @Test
    void telefonoInvalidoDebeLanzarExcepcion() {

        request.setTelefono("12345");

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> clienteService.guardar(request));

        assertEquals("El teléfono debe tener 9 dígitos", ex.getMessage());
    }

    @Test
    void correoFormatoIncorrectoDebeLanzarExcepcion() {

        request.setCorreo("correo");

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> clienteService.guardar(request));

        assertEquals("Formato de correo inválido", ex.getMessage());
    }

    @Test
    void correoNoGmailDebeLanzarExcepcion() {

        request.setCorreo("juan@hotmail.com");

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> clienteService.guardar(request));

        assertEquals("El correo debe terminar en @gmail.com", ex.getMessage());
    }

    @Test
    void eliminarCliente() {

        doNothing().when(clienteRepository).deleteById(1);

        clienteService.eliminar(1);

        verify(clienteRepository).deleteById(1);
    }

}