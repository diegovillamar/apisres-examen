package com.examen.vacaciones.controller;

import com.examen.vacaciones.dto.VacacionDTO;
import com.examen.vacaciones.service.VacacionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vacaciones")
public class VacacionController {

    private final VacacionService vacacionService;

    public VacacionController(VacacionService vacacionService) {
        this.vacacionService = vacacionService;
    }

    // POST /api/vacaciones - Registrar vacacion (HTTP 201)
    @PostMapping
    public ResponseEntity<VacacionDTO> crear(@Valid @RequestBody VacacionDTO dto) {
        VacacionDTO creada = vacacionService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    // GET /api/vacaciones - Listar todas (HTTP 200)
    @GetMapping
    public ResponseEntity<List<VacacionDTO>> obtenerTodas() {
        List<VacacionDTO> vacaciones = vacacionService.obtenerTodas();
        return ResponseEntity.ok(vacaciones);
    }

    // GET /api/vacaciones/{id} - Consultar por id (HTTP 200 / 404)
    @GetMapping("/{id}")
    public ResponseEntity<VacacionDTO> obtenerPorId(@PathVariable Long id) {
        VacacionDTO vacacion = vacacionService.obtenerPorId(id);
        return ResponseEntity.ok(vacacion);
    }

    // PUT /api/vacaciones/{id} - Editar vacacion (HTTP 200 / 404)
    @PutMapping("/{id}")
    public ResponseEntity<VacacionDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody VacacionDTO dto) {
        VacacionDTO actualizada = vacacionService.actualizar(id, dto);
        return ResponseEntity.ok(actualizada);
    }

    // PUT /api/vacaciones/{id}/inactivar - Baja logica (HTTP 200 / 404)
    @PutMapping("/{id}/inactivar")
    public ResponseEntity<VacacionDTO> inactivar(@PathVariable Long id) {
        VacacionDTO inactivada = vacacionService.inactivar(id);
        return ResponseEntity.ok(inactivada);
    }
}
