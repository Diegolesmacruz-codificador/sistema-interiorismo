package com.dlinteriorismo.sistema_interiorismo.service;

import com.dlinteriorismo.sistema_interiorismo.dto.ProyectoRequest;
import com.dlinteriorismo.sistema_interiorismo.model.Cliente;
import com.dlinteriorismo.sistema_interiorismo.model.Proyecto;
import com.dlinteriorismo.sistema_interiorismo.model.TipoProyecto;
import com.dlinteriorismo.sistema_interiorismo.repository.ClienteRepository;
import com.dlinteriorismo.sistema_interiorismo.repository.ProyectoRepository;
import com.dlinteriorismo.sistema_interiorismo.repository.TipoProyectoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProyectoServiceTest {

    @Mock
    private ProyectoRepository proyectoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private TipoProyectoRepository tipoProyectoRepository;

    @InjectMocks
    private ProyectoService proyectoService;

    private ProyectoRequest request;

    @BeforeEach
    void setUp() {

        request = new ProyectoRequest();

        request.setIdCliente(1);
        request.setIdTipoProyecto(1);
        request.setNombreProyecto("Cocina Integral");
        request.setFechaInicio(LocalDate.now());
        request.setFechaFin(LocalDate.now().plusDays(30));
        request.setEstado("Pendiente");
        request.setDescripcion("Proyecto de prueba");
    }

    @Test
    void listarDebeRetornarLista() {

        when(proyectoRepository.findAll())
                .thenReturn(List.of(new Proyecto()));

        List<Proyecto> lista = proyectoService.listar();

        assertEquals(1, lista.size());

        verify(proyectoRepository).findAll();
    }

    @Test
    void guardarDebeRegistrarProyecto() {

        Cliente cliente = new Cliente();
        TipoProyecto tipo = new TipoProyecto();

        when(clienteRepository.findById(1))
                .thenReturn(Optional.of(cliente));

        when(tipoProyectoRepository.findById(1))
                .thenReturn(Optional.of(tipo));

        when(proyectoRepository.save(any(Proyecto.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        Proyecto proyecto = proyectoService.guardar(request);

        assertNotNull(proyecto);

        assertEquals("Cocina Integral", proyecto.getNombreProyecto());

        verify(proyectoRepository).save(any(Proyecto.class));
    }

    @Test
    void guardarDebeFallarSiClienteNoExiste() {

        when(clienteRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> proyectoService.guardar(request));
    }

    @Test
    void guardarDebeFallarSiTipoProyectoNoExiste() {

        Cliente cliente = new Cliente();

        when(clienteRepository.findById(1))
                .thenReturn(Optional.of(cliente));

        when(tipoProyectoRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> proyectoService.guardar(request));
    }

    @Test
    void guardarDebeFallarSiNombreEsVacio() {

        Cliente cliente = new Cliente();
        TipoProyecto tipo = new TipoProyecto();

        when(clienteRepository.findById(1))
                .thenReturn(Optional.of(cliente));

        when(tipoProyectoRepository.findById(1))
                .thenReturn(Optional.of(tipo));

        request.setNombreProyecto("");

        assertThrows(RuntimeException.class,
                () -> proyectoService.guardar(request));
    }

    @Test
    void guardarDebeFallarSiEstadoEsVacio() {

        Cliente cliente = new Cliente();
        TipoProyecto tipo = new TipoProyecto();

        when(clienteRepository.findById(1))
                .thenReturn(Optional.of(cliente));

        when(tipoProyectoRepository.findById(1))
                .thenReturn(Optional.of(tipo));

        request.setEstado("");

        assertThrows(RuntimeException.class,
                () -> proyectoService.guardar(request));
    }

    @Test
    void guardarDebeFallarSiFechaInicioEsNula() {

        Cliente cliente = new Cliente();
        TipoProyecto tipo = new TipoProyecto();

        when(clienteRepository.findById(1))
                .thenReturn(Optional.of(cliente));

        when(tipoProyectoRepository.findById(1))
                .thenReturn(Optional.of(tipo));

        request.setFechaInicio(null);

        assertThrows(RuntimeException.class,
                () -> proyectoService.guardar(request));
    }

    @Test
    void guardarDebeFallarSiEstadoEsInvalido() {

        Cliente cliente = new Cliente();
        TipoProyecto tipo = new TipoProyecto();

        when(clienteRepository.findById(1))
                .thenReturn(Optional.of(cliente));

        when(tipoProyectoRepository.findById(1))
                .thenReturn(Optional.of(tipo));

        request.setEstado("Cancelado");

        assertThrows(RuntimeException.class,
                () -> proyectoService.guardar(request));
    }

    @Test
    void eliminarDebeInvocarRepositorio() {

        proyectoService.eliminar(1);

        verify(proyectoRepository).deleteById(1);
    }

}