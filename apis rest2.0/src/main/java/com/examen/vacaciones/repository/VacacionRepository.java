package com.examen.vacaciones.repository;

import com.examen.vacaciones.entity.Vacacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacacionRepository extends JpaRepository<Vacacion, Long> {
}
