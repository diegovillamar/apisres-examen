-- Creacion automatica de la tabla vacaciones
CREATE TABLE IF NOT EXISTS vacaciones (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_empleado VARCHAR(150) NOT NULL,
    fecha_inicio    DATE         NOT NULL,
    fecha_fin       DATE         NOT NULL,
    estado          CHAR(1)      NOT NULL DEFAULT 'A'
);
