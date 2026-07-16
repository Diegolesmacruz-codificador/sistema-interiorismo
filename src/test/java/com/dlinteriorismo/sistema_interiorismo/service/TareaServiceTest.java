package com.dlinteriorismo.sistema_interiorismo.service;

import com.dlinteriorismo.sistema_interiorismo.dto.TareaRequest;
import com.dlinteriorismo.sistema_interiorismo.model.Tarea;
import com.dlinteriorismo.sistema_interiorismo.repository.EmpleadoRepository;
import com.dlinteriorismo.sistema_interiorismo.repository.ProyectoRepository;
import com.dlinteriorismo.sistema_interiorismo.repository.TareaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TareaServiceTest {

    @Mock
    private TareaRepository tareaRepository;

    @Mock
    private ProyectoRepository proyectoRepository;

    @Mock
    private EmpleadoRepository empleadoRepository;

    @InjectMocks
    private TareaService tareaService;

    private TareaRequest request;

    @BeforeEach
    void setUp() {

        request = new TareaRequest();

        request.setIdProyecto(1);
        request.setIdEmpleado(1);
        request.setDescripcion("Instalar muebles");
        request.setFechaLimite(LocalDate.now().plusDays(5));
        request.setEstado("Pendiente");
    }

    @Test
    void listarDebeRetornarLista() {

        when(tareaRepository.findAll())
                .thenReturn(List.of(new Tarea()));

        List<Tarea> lista = tareaService.listar();

        assertEquals(1, lista.size());

        verify(tareaRepository).findAll();
    }

    @Test
    void guardarDebeRegistrarTarea() {

        when(proyectoRepository.existsById(1)).thenReturn(true);
        when(empleadoRepository.existsById(1)).thenReturn(true);

        when(tareaRepository.save(any(Tarea.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        Tarea tarea = tareaService.guardar(request);

        assertNotNull(tarea);

        assertEquals("Instalar muebles", tarea.getDescripcion());

        verify(tareaRepository).save(any(Tarea.class));
    }

    @Test
    void guardarDebeFallarSiProyectoNoExiste() {

        when(proyectoRepository.existsById(1)).thenReturn(false);

        assertThrows(IllegalArgumentException.class,
                () -> tareaService.guardar(request));
    }

    @Test
    void guardarDebeFallarSiEmpleadoNoExiste() {

        when(proyectoRepository.existsById(1)).thenReturn(true);
        when(empleadoRepository.existsById(1)).thenReturn(false);

        assertThrows(IllegalArgumentException.class,
                () -> tareaService.guardar(request));
    }

    @Test
    void guardarDebeFallarSiDescripcionEsVacia() {

        when(proyectoRepository.existsById(1)).thenReturn(true);
        when(empleadoRepository.existsById(1)).thenReturn(true);

        request.setDescripcion("");

        assertThrows(RuntimeException.class,
                () -> tareaService.guardar(request));
    }

    @Test
    void guardarDebeAsignarEstadoPendiente() {

        when(proyectoRepository.existsById(1)).thenReturn(true);
        when(empleadoRepository.existsById(1)).thenReturn(true);

        request.setEstado("");

        when(tareaRepository.save(any(Tarea.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        Tarea tarea = tareaService.guardar(request);

        assertEquals("Pendiente", tarea.getEstado());
    }

    @Test
    void eliminarDebeInvocarRepositorio() {

        tareaService.eliminar(1);

        verify(tareaRepository).deleteById(1);
    }

}