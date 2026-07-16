package com.dlinteriorismo.sistema_interiorismo.service;

import com.dlinteriorismo.sistema_interiorismo.model.Tarea;
import com.dlinteriorismo.sistema_interiorismo.repository.EmpleadoRepository;
import com.dlinteriorismo.sistema_interiorismo.repository.ProyectoRepository;
import com.dlinteriorismo.sistema_interiorismo.repository.TareaRepository;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.dlinteriorismo.sistema_interiorismo.dto.TareaRequest;
import java.util.List;

@Service
public class TareaService {

    private static final Logger logger =
            LoggerFactory.getLogger(TareaService.class);

    private final TareaRepository tareaRepository;
    private final ProyectoRepository proyectoRepository;
    private final EmpleadoRepository empleadoRepository;

    public TareaService(
            TareaRepository tareaRepository,
            ProyectoRepository proyectoRepository,
            EmpleadoRepository empleadoRepository) {
        this.tareaRepository = tareaRepository;
        this.proyectoRepository = proyectoRepository;
        this.empleadoRepository = empleadoRepository;
    }

    public List<Tarea> listar() {
        logger.info("Listando tareas");
        return tareaRepository.findAll();
    }

    public Tarea guardar(TareaRequest request) {

        Preconditions.checkNotNull(request.getIdProyecto(), "El proyecto es obligatorio");
        Preconditions.checkArgument(
                proyectoRepository.existsById(request.getIdProyecto()),
                "El proyecto no existe");

        Preconditions.checkNotNull(request.getIdEmpleado(), "El empleado es obligatorio");
        Preconditions.checkArgument(
                empleadoRepository.existsById(request.getIdEmpleado()),
                "El empleado no existe");

        if (Strings.isNullOrEmpty(request.getDescripcion())) {
            throw new RuntimeException("La descripción es obligatoria");
        }

        Tarea tarea = new Tarea();

        tarea.setIdProyecto(request.getIdProyecto());
        tarea.setIdEmpleado(request.getIdEmpleado());
        tarea.setDescripcion(request.getDescripcion());
        tarea.setFechaLimite(request.getFechaLimite());

        if (request.getEstado() == null || request.getEstado().isBlank()) {
            tarea.setEstado("Pendiente");
        } else {
            tarea.setEstado(request.getEstado());
        }

        logger.info("Tarea registrada para proyecto ID: {}", request.getIdProyecto());

        return tareaRepository.save(tarea);
    }

    public void eliminar(Integer id) {
        logger.info("Eliminando tarea ID: {}", id);
        tareaRepository.deleteById(id);
    }
}