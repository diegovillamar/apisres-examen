package com.examen.vacaciones.service;

import com.examen.vacaciones.dto.VacacionDTO;
import com.examen.vacaciones.entity.Vacacion;
import com.examen.vacaciones.repository.VacacionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VacacionService {

    private final VacacionRepository vacacionRepository;

    public VacacionService(VacacionRepository vacacionRepository) {
        this.vacacionRepository = vacacionRepository;
    }

    // TODO EXAMEN: Registrar una nueva vacacion
    public VacacionDTO crear(VacacionDTO dto) {
        // Validacion: fechaFin >= fechaInicio
        if (dto.getFechaFin().isBefore(dto.getFechaInicio())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "La fecha de fin no puede ser anterior a la fecha de inicio"
            );
        }
        // Convertir DTO -> Entity
        Vacacion vacacion = new Vacacion();
        vacacion.setNombreEmpleado(dto.getNombreEmpleado());
        vacacion.setFechaInicio(dto.getFechaInicio());
        vacacion.setFechaFin(dto.getFechaFin());
        vacacion.setEstado("A"); // Estado activo por defecto

        // Persistir
        Vacacion guardada = vacacionRepository.save(vacacion);

        // Convertir Entity -> DTO y retornar
        return toDTO(guardada);
    }

    // TODO EXAMEN: Listar todas las vacaciones
    public List<VacacionDTO> obtenerTodas() {
        return vacacionRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // TODO EXAMEN: Obtener una vacacion por id
    public VacacionDTO obtenerPorId(Long id) {
        Vacacion vacacion = vacacionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Vacacion no encontrada con id: " + id
                ));
        return toDTO(vacacion);
    }

    // TODO EXAMEN: Editar una vacacion existente
    public VacacionDTO actualizar(Long id, VacacionDTO dto) {
        Vacacion vacacion = vacacionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Vacacion no encontrada con id: " + id
                ));

        // Validacion: fechaFin >= fechaInicio
        if (dto.getFechaFin().isBefore(dto.getFechaInicio())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "La fecha de fin no puede ser anterior a la fecha de inicio"
            );
        }

        // Actualizar campos
        vacacion.setNombreEmpleado(dto.getNombreEmpleado());
        vacacion.setFechaInicio(dto.getFechaInicio());
        vacacion.setFechaFin(dto.getFechaFin());

        // Persistir
        Vacacion actualizada = vacacionRepository.save(vacacion);

        return toDTO(actualizada);
    }

    // TODO EXAMEN: Inactivar una vacacion (baja logica)
    public VacacionDTO inactivar(Long id) {
        Vacacion vacacion = vacacionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Vacacion no encontrada con id: " + id
                ));

        // Cambiar estado a 'I' (inactivo) - no eliminar registro
        vacacion.setEstado("I");
        Vacacion inactivada = vacacionRepository.save(vacacion);

        return toDTO(inactivada);
    }

    // Conversion Entity -> DTO
    private VacacionDTO toDTO(Vacacion vacacion) {
        VacacionDTO dto = new VacacionDTO();
        dto.setId(vacacion.getId());
        dto.setNombreEmpleado(vacacion.getNombreEmpleado());
        dto.setFechaInicio(vacacion.getFechaInicio());
        dto.setFechaFin(vacacion.getFechaFin());
        dto.setEstado(vacacion.getEstado());
        return dto;
    }
}
