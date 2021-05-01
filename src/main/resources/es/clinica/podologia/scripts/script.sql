--
-- File generated with SQLiteStudio v3.3.2 on do. may. 2 00:48:21 2021
--
-- Text encoding used: UTF-8
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: acceso
DROP TABLE IF EXISTS acceso;

CREATE TABLE acceso (
    usuario    STRING (20) PRIMARY KEY
                           UNIQUE,
    contrasena STRING (20) 
);

INSERT INTO acceso (
                       usuario,
                       contrasena
                   )
                   VALUES (
                       'administrador',
                       'administrador'
                   );

INSERT INTO acceso (
                       usuario,
                       contrasena
                   )
                   VALUES (
                       'admin',
                       'admin'
                   );


-- Table: citas
DROP TABLE IF EXISTS citas;

CREATE TABLE citas (
    identificador INTEGER    PRIMARY KEY AUTOINCREMENT
                             NOT NULL,
    paciente      TEXT (20)  CONSTRAINT dni_paciente REFERENCES pacientes (dni) MATCH SIMPLE
                             NOT NULL,
    sanitario     TEXT (20)  NOT NULL
                             CONSTRAINT dni_sanitario REFERENCES sanitarios (dni),
    tratamiento   INTEGER    CONSTRAINT identificador_tratamiento REFERENCES tratamientos (identificador) 
                             NOT NULL,
    fecha         DATE       NOT NULL,
    hora_inicio   TIME       NOT NULL,
    hora_fin      TIME       NOT NULL,
    observaciones TEXT (100) 
);


-- Table: pacientes
DROP TABLE IF EXISTS pacientes;

CREATE TABLE pacientes (
    dni       TEXT (20)  PRIMARY KEY
                         NOT NULL,
    nombre    TEXT (50),
    apellidos TEXT (50),
    direccion TEXT (100),
    telefono  TEXT (20),
    adjunto   BLOB
);


-- Table: sanitarios
DROP TABLE IF EXISTS sanitarios;

CREATE TABLE sanitarios (
    dni          TEXT (20) PRIMARY KEY,
    nombre       TEXT (50),
    apellidos    TEXT (50),
    especialidad TEXT (50) 
);


-- Table: tratamientos
DROP TABLE IF EXISTS tratamientos;

CREATE TABLE tratamientos (
    identificador INTEGER    PRIMARY KEY ASC AUTOINCREMENT,
    nombre        TEXT (50),
    descripcion   TEXT (100) 
);


COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
