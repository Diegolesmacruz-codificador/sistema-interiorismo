package com.dlinteriorismo.sistema_interiorismo.controller;

import com.dlinteriorismo.sistema_interiorismo.dto.EmpleadoRequest;
import com.dlinteriorismo.sistema_interiorismo.model.Empleado;
import com.dlinteriorismo.sistema_interiorismo.service.EmpleadoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping
    public List<Empleado> listar() {
        return empleadoService.listar();
    }

    @PostMapping
    public Empleado guardar(@RequestBody EmpleadoRequest request) {
        return empleadoService.guardar(request);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        empleadoService.eliminar(id);
    }
}