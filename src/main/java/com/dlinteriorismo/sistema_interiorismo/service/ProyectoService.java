package com.dlinteriorismo.sistema_interiorismo.service;

import com.dlinteriorismo.sistema_interiorismo.model.Proyecto;
import com.dlinteriorismo.sistema_interiorismo.repository.ProyectoRepository;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.dlinteriorismo.sistema_interiorismo.dto.ProyectoRequest;
import com.dlinteriorismo.sistema_interiorismo.model.Cliente;
import com.dlinteriorismo.sistema_interiorismo.model.TipoProyecto;
import com.dlinteriorismo.sistema_interiorismo.repository.ClienteRepository;
import com.dlinteriorismo.sistema_interiorismo.repository.TipoProyectoRepository;
import java.util.List;
@Service
public class ProyectoService {

    private static final Logger logger =
            LoggerFactory.getLogger(ProyectoService.class);

    private final ProyectoRepository proyectoRepository;
    private final ClienteRepository clienteRepository;
    private final TipoProyectoRepository tipoProyectoRepository;

    public ProyectoService(
            ProyectoRepository proyectoRepository,
            ClienteRepository clienteRepository,
            TipoProyectoRepository tipoProyectoRepository) {

        this.proyectoRepository = proyectoRepository;
        this.clienteRepository = clienteRepository;
        this.tipoProyectoRepository = tipoProyectoRepository;
    }

    public List<Proyecto> listar() {
        logger.info("Listando proyectos");
        return proyectoRepository.findAll();
    }

    public Proyecto guardar(ProyectoRequest request) {

        Cliente cliente = clienteRepository.findById(request.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        TipoProyecto tipoProyecto = tipoProyectoRepository.findById(request.getIdTipoProyecto())
                .orElseThrow(() -> new RuntimeException("Tipo de proyecto no encontrado"));

        if (Strings.isNullOrEmpty(request.getNombreProyecto())) {
            throw new RuntimeException("El nombre del proyecto es obligatorio");
        }

        if (StringUtils.isBlank(request.getEstado())) {
            throw new RuntimeException("El estado es obligatorio");
        }

        if (request.getFechaInicio() == null) {
            throw new RuntimeException("La fecha de inicio es obligatoria");
        }

        if (!request.getEstado().equals("Pendiente")
                && !request.getEstado().equals("En Proceso")
                && !request.getEstado().equals("Finalizado")) {

            throw new RuntimeException(
                    "Estado inválido. Solo se permite Pendiente, En Proceso o Finalizado");
        }

        Proyecto proyecto = new Proyecto();

        proyecto.setCliente(cliente);
        proyecto.setTipoProyecto(tipoProyecto);
        proyecto.setNombreProyecto(request.getNombreProyecto());
        proyecto.setFechaInicio(request.getFechaInicio());
        proyecto.setFechaFin(request.getFechaFin());
        proyecto.setEstado(request.getEstado());
        proyecto.setDescripcion(request.getDescripcion());

        logger.info("Proyecto registrado correctamente: {}", request.getNombreProyecto());

        return proyectoRepository.save(proyecto);
    }

    public void eliminar(Integer id) {
        proyectoRepository.deleteById(id);
    }
}