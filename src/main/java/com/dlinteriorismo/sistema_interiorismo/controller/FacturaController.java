package com.dlinteriorismo.sistema_interiorismo.controller;

import com.dlinteriorismo.sistema_interiorismo.model.Factura;
import com.dlinteriorismo.sistema_interiorismo.service.FacturaService;
import org.springframework.web.bind.annotation.*;
import com.dlinteriorismo.sistema_interiorismo.dto.FacturaRequest;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    private final FacturaService facturaService;

    public FacturaController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @GetMapping
    public List<Factura> listar() {
        return facturaService.listar();
    }

    @PostMapping
    public Factura guardar(@RequestBody FacturaRequest request) {
        return facturaService.guardar(request);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        facturaService.eliminar(id);
    }
}