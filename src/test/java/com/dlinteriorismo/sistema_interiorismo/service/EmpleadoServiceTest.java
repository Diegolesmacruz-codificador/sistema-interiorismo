package com.dlinteriorismo.sistema_interiorismo.service;

import com.dlinteriorismo.sistema_interiorismo.dto.EmpleadoRequest;
import com.dlinteriorismo.sistema_interiorismo.model.Empleado;
import com.dlinteriorismo.sistema_interiorismo.repository.EmpleadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmpleadoServiceTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @InjectMocks
    private EmpleadoService empleadoService;

    private EmpleadoRequest request;

    @BeforeEach
    void setUp() {
        request = new EmpleadoRequest();
        request.setNombre("Juan Perez");
        request.setCargo("Diseñador");
        request.setTelefono("987654321");
        request.setCorreo("juan@gmail.com");
    }

    @Test
    void listarDebeRetornarLista() {

        Empleado empleado = new Empleado();
        empleado.setNombre("Juan Perez");

        when(empleadoRepository.findAll())
                .thenReturn(List.of(empleado));

        List<Empleado> resultado = empleadoService.listar();

        assertEquals(1, resultado.size());
        assertEquals("Juan Perez", resultado.get(0).getNombre());

        verify(empleadoRepository).findAll();
    }

    @Test
    void guardarDebeRegistrarEmpleadoCorrectamente() {

        Empleado empleadoGuardado = new Empleado();
        empleadoGuardado.setNombre(request.getNombre());
        empleadoGuardado.setCargo(request.getCargo());
        empleadoGuardado.setTelefono(request.getTelefono());
        empleadoGuardado.setCorreo(request.getCorreo());

        when(empleadoRepository.save(any(Empleado.class)))
                .thenReturn(empleadoGuardado);

        Empleado resultado = empleadoService.guardar(request);

        assertNotNull(resultado);
        assertEquals("Juan Perez", resultado.getNombre());

        ArgumentCaptor<Empleado> captor =
                ArgumentCaptor.forClass(Empleado.class);

        verify(empleadoRepository).save(captor.capture());

        assertEquals("Juan Perez", captor.getValue().getNombre());
        assertEquals("Diseñador", captor.getValue().getCargo());
    }

    @Test
    void guardarDebeLanzarExcepcionSiNombreEsVacio() {

        request.setNombre("");

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> empleadoService.guardar(request)
        );

        assertEquals("El nombre es obligatorio", ex.getMessage());

        verify(empleadoRepository, never()).save(any());
    }

    @Test
    void guardarDebeLanzarExcepcionSiCargoEsVacio() {

        request.setCargo("");

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> empleadoService.guardar(request)
        );

        assertEquals("El cargo es obligatorio", ex.getMessage());

        verify(empleadoRepository, never()).save(any());
    }

    @Test
    void guardarDebeLanzarExcepcionSiTelefonoEsInvalido() {

        request.setTelefono("123");

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> empleadoService.guardar(request)
        );

        assertEquals(
                "El teléfono debe tener exactamente 9 dígitos",
                ex.getMessage()
        );

        verify(empleadoRepository, never()).save(any());
    }

    @Test
    void guardarDebeLanzarExcepcionSiCorreoNoEsValido() {

        request.setCorreo("correo_invalido");

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> empleadoService.guardar(request)
        );

        assertEquals("Formato de correo inválido", ex.getMessage());

        verify(empleadoRepository, never()).save(any());
    }

    @Test
    void guardarDebeLanzarExcepcionSiCorreoNoEsGmail() {

        request.setCorreo("juan@hotmail.com");

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> empleadoService.guardar(request)
        );

        assertEquals(
                "El correo debe terminar en @gmail.com",
                ex.getMessage()
        );

        verify(empleadoRepository, never()).save(any());
    }

    @Test
    void eliminarDebeInvocarRepositorio() {

        empleadoService.eliminar(1);

        verify(empleadoRepository).deleteById(1);
    }
}