package com.dlinteriorismo.sistema_interiorismo.controller;

import com.dlinteriorismo.sistema_interiorismo.model.Cliente;
import com.dlinteriorismo.sistema_interiorismo.model.Empleado;
import com.dlinteriorismo.sistema_interiorismo.model.Proyecto;
import com.dlinteriorismo.sistema_interiorismo.model.TipoProyecto;
import com.dlinteriorismo.sistema_interiorismo.model.Cotizacion;
import com.dlinteriorismo.sistema_interiorismo.model.Factura;
import com.dlinteriorismo.sistema_interiorismo.model.Pago;
import com.dlinteriorismo.sistema_interiorismo.model.Tarea;
import com.dlinteriorismo.sistema_interiorismo.repository.*;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class ReporteControllerTest {

    private ClienteRepository clienteRepository;
    private EmpleadoRepository empleadoRepository;
    private ProyectoRepository proyectoRepository;
    private CotizacionRepository cotizacionRepository;
    private FacturaRepository facturaRepository;
    private PagoRepository pagoRepository;
    private TareaRepository tareaRepository;

    private ReporteController reporteController;

    @BeforeEach
    void setUp() {

        clienteRepository = mock(ClienteRepository.class);
        empleadoRepository = mock(EmpleadoRepository.class);
        proyectoRepository = mock(ProyectoRepository.class);
        cotizacionRepository = mock(CotizacionRepository.class);
        facturaRepository = mock(FacturaRepository.class);
        pagoRepository = mock(PagoRepository.class);
        tareaRepository = mock(TareaRepository.class);

        reporteController = new ReporteController(
                clienteRepository,
                empleadoRepository,
                proyectoRepository,
                cotizacionRepository,
                facturaRepository,
                pagoRepository,
                tareaRepository
        );
    }
    @Test
    void exportarClientesExcelDebeGenerarArchivo() throws Exception {

        Cliente cliente = new Cliente();
        cliente.setIdCliente(1);
        cliente.setNombre("Juan");
        cliente.setDni("12345678");
        cliente.setTelefono("999999999");
        cliente.setCorreo("juan@gmail.com");
        cliente.setDireccion("Lima");

        when(clienteRepository.findAll())
                .thenReturn(List.of(cliente));

        HttpServletResponse response = mock(HttpServletResponse.class);

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        when(response.getOutputStream())
                .thenReturn(new ServletOutputStream() {

                    @Override
                    public void write(int b) {
                        output.write(b);
                    }

                    @Override
                    public boolean isReady() {
                        return true;
                    }

                    @Override
                    public void setWriteListener(WriteListener writeListener) {
                    }
                });

        assertDoesNotThrow(() ->
                reporteController.exportarClientesExcel(response));

        verify(clienteRepository).findAll();
    }
    @Test
    void exportarEmpleadosExcelDebeGenerarArchivo() throws Exception {

        Empleado empleado = new Empleado();
        empleado.setIdEmpleado(1);
        empleado.setNombre("Carlos");
        empleado.setCargo("Diseñador");
        empleado.setTelefono("999999999");
        empleado.setCorreo("carlos@gmail.com");

        when(empleadoRepository.findAll())
                .thenReturn(List.of(empleado));

        HttpServletResponse response = mock(HttpServletResponse.class);

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        when(response.getOutputStream())
                .thenReturn(new ServletOutputStream() {

                    @Override
                    public void write(int b) {
                        output.write(b);
                    }

                    @Override
                    public boolean isReady() {
                        return true;
                    }

                    @Override
                    public void setWriteListener(WriteListener listener) {
                    }
                });

        assertDoesNotThrow(() ->
                reporteController.exportarEmpleadosExcel(response));

        verify(empleadoRepository).findAll();
    }
    @Test
    void exportarProyectosExcelDebeGenerarArchivo() throws Exception {

        Cliente cliente = new Cliente();
        cliente.setNombre("Juan");

        TipoProyecto tipo = new TipoProyecto();
        tipo.setNombreTipo("Cocina");

        Proyecto proyecto = new Proyecto();
        proyecto.setIdProyecto(1);
        proyecto.setNombreProyecto("Proyecto 1");
        proyecto.setCliente(cliente);
        proyecto.setTipoProyecto(tipo);
        proyecto.setEstado("Pendiente");

        when(proyectoRepository.findAll())
                .thenReturn(List.of(proyecto));

        HttpServletResponse response = mock(HttpServletResponse.class);

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        when(response.getOutputStream())
                .thenReturn(new ServletOutputStream() {

                    @Override
                    public void write(int b) {
                        output.write(b);
                    }

                    @Override
                    public boolean isReady() {
                        return true;
                    }

                    @Override
                    public void setWriteListener(WriteListener listener) {
                    }
                });

        assertDoesNotThrow(() ->
                reporteController.exportarProyectosExcel(response));

        verify(proyectoRepository).findAll();
    }
    @Test
    void exportarCotizacionesExcelDebeGenerarArchivo() throws Exception {

        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setIdCotizacion(1);
        cotizacion.setIdProyecto(1);
        cotizacion.setMetraje(new BigDecimal("5"));
        cotizacion.setSubtotal(new BigDecimal("1000"));
        cotizacion.setGanancia(new BigDecimal("800"));
        cotizacion.setTotal(new BigDecimal("1800"));
        cotizacion.setEstado("Pendiente");
        cotizacion.setFecha(LocalDate.now());

        when(cotizacionRepository.findAll())
                .thenReturn(List.of(cotizacion));

        HttpServletResponse response = mock(HttpServletResponse.class);

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        when(response.getOutputStream())
                .thenReturn(new ServletOutputStream() {

                    @Override
                    public void write(int b) {
                        output.write(b);
                    }

                    @Override
                    public boolean isReady() {
                        return true;
                    }

                    @Override
                    public void setWriteListener(WriteListener listener) {
                    }
                });

        assertDoesNotThrow(() ->
                reporteController.exportarCotizacionesExcel(response));

        verify(cotizacionRepository).findAll();
    }
    @Test
    void exportarFacturasExcelDebeGenerarArchivo() throws Exception {

        Factura factura = new Factura();
        factura.setIdFactura(1);
        factura.setNumeroFactura("F-001");
        factura.setFecha(LocalDate.now());
        factura.setTotal(new BigDecimal("2500"));
        factura.setEstado("Pendiente");

        when(facturaRepository.findAll())
                .thenReturn(List.of(factura));

        HttpServletResponse response = mock(HttpServletResponse.class);

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        when(response.getOutputStream())
                .thenReturn(new ServletOutputStream() {

                    @Override
                    public void write(int b) {
                        output.write(b);
                    }

                    @Override
                    public boolean isReady() {
                        return true;
                    }

                    @Override
                    public void setWriteListener(WriteListener listener) {
                    }
                });

        assertDoesNotThrow(() ->
                reporteController.exportarFacturasExcel(response));

        verify(facturaRepository).findAll();
    }
    @Test
    void exportarPagosExcelDebeGenerarArchivo() throws Exception {

        Pago pago = new Pago();
        pago.setIdPago(1);
        pago.setIdFactura(1);
        pago.setFechaPago(LocalDate.now());
        pago.setMetodoPago("Transferencia");
        pago.setMonto(new BigDecimal("1500"));
        pago.setReferencia("REF001");

        when(pagoRepository.findAll())
                .thenReturn(List.of(pago));

        HttpServletResponse response = mock(HttpServletResponse.class);

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        when(response.getOutputStream())
                .thenReturn(new ServletOutputStream() {

                    @Override
                    public void write(int b) {
                        output.write(b);
                    }

                    @Override
                    public boolean isReady() {
                        return true;
                    }

                    @Override
                    public void setWriteListener(WriteListener listener) {
                    }
                });

        assertDoesNotThrow(() ->
                reporteController.exportarPagosExcel(response));

        verify(pagoRepository).findAll();
    }
    @Test
    void exportarTareasExcelDebeGenerarArchivo() throws Exception {

        Tarea tarea = new Tarea();
        tarea.setIdTarea(1);
        tarea.setIdProyecto(1);
        tarea.setIdEmpleado(1);
        tarea.setDescripcion("Instalación de muebles");
        tarea.setFechaLimite(LocalDate.now());
        tarea.setEstado("Pendiente");

        when(tareaRepository.findAll())
                .thenReturn(List.of(tarea));

        HttpServletResponse response = mock(HttpServletResponse.class);

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        when(response.getOutputStream())
                .thenReturn(new ServletOutputStream() {

                    @Override
                    public void write(int b) {
                        output.write(b);
                    }

                    @Override
                    public boolean isReady() {
                        return true;
                    }

                    @Override
                    public void setWriteListener(WriteListener listener) {
                    }
                });

        assertDoesNotThrow(() ->
                reporteController.exportarTareasExcel(response));

        verify(tareaRepository).findAll();
    }
}