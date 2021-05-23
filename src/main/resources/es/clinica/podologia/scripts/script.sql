--
-- File generated with SQLiteStudio v3.3.2 on do. may. 23 13:34:09 2021
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

INSERT INTO acceso (
                       usuario,
                       contrasena
                   )
                   VALUES (
                       'prueba',
                       1234
                   );

INSERT INTO acceso (
                       usuario,
                       contrasena
                   )
                   VALUES (
                       'usuario',
                       'usuario'
                   );


-- Table: citas
DROP TABLE IF EXISTS citas;

CREATE TABLE citas (
    id_cita        INTEGER    PRIMARY KEY AUTOINCREMENT
                              NOT NULL,
    dni_paciente   TEXT (20)  CONSTRAINT dni_paciente REFERENCES pacientes (dni_paciente) 
                              NOT NULL,
    dni_sanitario  TEXT (20)  NOT NULL
                              CONSTRAINT dni_sanitario REFERENCES sanitarios (dni_sanitario),
    id_tratamiento INTEGER    CONSTRAINT identificador_tratamiento REFERENCES tratamientos (id_tratamiento) 
                              NOT NULL,
    fecha          DATE       NOT NULL,
    hora_inicio    TIME       NOT NULL,
    hora_fin       TIME       NOT NULL,
    observaciones  TEXT (100) 
);

INSERT INTO citas (
                      id_cita,
                      dni_paciente,
                      dni_sanitario,
                      id_tratamiento,
                      fecha,
                      hora_inicio,
                      hora_fin,
                      observaciones
                  )
                  VALUES (
                      1,
                      '10608076Q',
                      '50053553X',
                      1,
                      '07-05-2021',
                      '09:15:00',
                      '09:45:00',
                      'Muy alto'
                  );

INSERT INTO citas (
                      id_cita,
                      dni_paciente,
                      dni_sanitario,
                      id_tratamiento,
                      fecha,
                      hora_inicio,
                      hora_fin,
                      observaciones
                  )
                  VALUES (
                      2,
                      '88336204N',
                      '50053553X',
                      3,
                      '09-05-2021',
                      '10:00:00',
                      '10:30:00',
                      'Quizás llegue diez minutos tarde'
                  );

INSERT INTO citas (
                      id_cita,
                      dni_paciente,
                      dni_sanitario,
                      id_tratamiento,
                      fecha,
                      hora_inicio,
                      hora_fin,
                      observaciones
                  )
                  VALUES (
                      3,
                      '10608076Q',
                      '50053553X',
                      3,
                      '07-05-2021',
                      '11:00:00',
                      '11:30:00',
                      'Muy alto'
                  );

INSERT INTO citas (
                      id_cita,
                      dni_paciente,
                      dni_sanitario,
                      id_tratamiento,
                      fecha,
                      hora_inicio,
                      hora_fin,
                      observaciones
                  )
                  VALUES (
                      4,
                      '74298758H',
                      '50053553X',
                      2,
                      '09-05-2021',
                      '12:30:00',
                      '13:00:00',
                      'Muy alto'
                  );

INSERT INTO citas (
                      id_cita,
                      dni_paciente,
                      dni_sanitario,
                      id_tratamiento,
                      fecha,
                      hora_inicio,
                      hora_fin,
                      observaciones
                  )
                  VALUES (
                      5,
                      '97806017N',
                      '50053553X',
                      5,
                      '10-05-2021',
                      '12:30:00',
                      '13:00:00',
                      'Muy alto'
                  );

INSERT INTO citas (
                      id_cita,
                      dni_paciente,
                      dni_sanitario,
                      id_tratamiento,
                      fecha,
                      hora_inicio,
                      hora_fin,
                      observaciones
                  )
                  VALUES (
                      6,
                      '74298758H',
                      '50053553X',
                      8,
                      '11-05-2021',
                      '12:30:00',
                      '13:00:00',
                      'Muy alto'
                  );

INSERT INTO citas (
                      id_cita,
                      dni_paciente,
                      dni_sanitario,
                      id_tratamiento,
                      fecha,
                      hora_inicio,
                      hora_fin,
                      observaciones
                  )
                  VALUES (
                      7,
                      '10608076Q',
                      '50053553X',
                      6,
                      '12-05-2021',
                      '12:30:00',
                      '13:00:00',
                      'Muy alto'
                  );

INSERT INTO citas (
                      id_cita,
                      dni_paciente,
                      dni_sanitario,
                      id_tratamiento,
                      fecha,
                      hora_inicio,
                      hora_fin,
                      observaciones
                  )
                  VALUES (
                      8,
                      '10608076Q',
                      '60909365Y',
                      6,
                      '07-05-2021',
                      '09:15:00',
                      '09:45:00',
                      'Muy alto'
                  );

INSERT INTO citas (
                      id_cita,
                      dni_paciente,
                      dni_sanitario,
                      id_tratamiento,
                      fecha,
                      hora_inicio,
                      hora_fin,
                      observaciones
                  )
                  VALUES (
                      9,
                      '88336204N',
                      '60909365Y',
                      6,
                      '07-05-2021',
                      '09:15:00',
                      '09:45:00',
                      'Muy alto'
                  );

INSERT INTO citas (
                      id_cita,
                      dni_paciente,
                      dni_sanitario,
                      id_tratamiento,
                      fecha,
                      hora_inicio,
                      hora_fin,
                      observaciones
                  )
                  VALUES (
                      10,
                      '96791541C',
                      '60909365Y',
                      6,
                      '12-05-2021',
                      '09:15:00',
                      '09:45:00',
                      'Muy alto'
                  );

INSERT INTO citas (
                      id_cita,
                      dni_paciente,
                      dni_sanitario,
                      id_tratamiento,
                      fecha,
                      hora_inicio,
                      hora_fin,
                      observaciones
                  )
                  VALUES (
                      11,
                      '18364040N',
                      '60909365Y',
                      6,
                      '10-05-2021',
                      '09:15:00',
                      '09:45:00',
                      'Muy alto'
                  );

INSERT INTO citas (
                      id_cita,
                      dni_paciente,
                      dni_sanitario,
                      id_tratamiento,
                      fecha,
                      hora_inicio,
                      hora_fin,
                      observaciones
                  )
                  VALUES (
                      12,
                      '74298758H',
                      '60909365Y',
                      1,
                      '10-05-2021',
                      '09:15:00',
                      '09:45:00',
                      'Muy alto'
                  );

INSERT INTO citas (
                      id_cita,
                      dni_paciente,
                      dni_sanitario,
                      id_tratamiento,
                      fecha,
                      hora_inicio,
                      hora_fin,
                      observaciones
                  )
                  VALUES (
                      13,
                      '74298758H',
                      '60909365Y',
                      1,
                      '12-05-2021',
                      '09:15:00',
                      '09:45:00',
                      'Quizás llegue diez minutos tarde'
                  );

INSERT INTO citas (
                      id_cita,
                      dni_paciente,
                      dni_sanitario,
                      id_tratamiento,
                      fecha,
                      hora_inicio,
                      hora_fin,
                      observaciones
                  )
                  VALUES (
                      14,
                      '18364040N',
                      '50053553X',
                      7,
                      '13-05-2021',
                      '09:15:00',
                      '09:45:00',
                      'Quizás llegue diez minutos tarde'
                  );

INSERT INTO citas (
                      id_cita,
                      dni_paciente,
                      dni_sanitario,
                      id_tratamiento,
                      fecha,
                      hora_inicio,
                      hora_fin,
                      observaciones
                  )
                  VALUES (
                      15,
                      '96791541C',
                      '50053553X',
                      7,
                      '13-05-2021',
                      '13:15:00',
                      '13:45:00',
                      'Quizás llegue diez minutos tarde'
                  );

INSERT INTO citas (
                      id_cita,
                      dni_paciente,
                      dni_sanitario,
                      id_tratamiento,
                      fecha,
                      hora_inicio,
                      hora_fin,
                      observaciones
                  )
                  VALUES (
                      16,
                      '88336204N',
                      '50053553X',
                      4,
                      '11-05-2021',
                      '10:00:00',
                      '10:30:00',
                      'Quizás llegue diez minutos tarde'
                  );

INSERT INTO citas (
                      id_cita,
                      dni_paciente,
                      dni_sanitario,
                      id_tratamiento,
                      fecha,
                      hora_inicio,
                      hora_fin,
                      observaciones
                  )
                  VALUES (
                      17,
                      '10608076Q',
                      '50053553X',
                      2,
                      '11-05-2021',
                      '10:00:00',
                      '10:30:00',
                      'Quizás llegue diez minutos tarde'
                  );

INSERT INTO citas (
                      id_cita,
                      dni_paciente,
                      dni_sanitario,
                      id_tratamiento,
                      fecha,
                      hora_inicio,
                      hora_fin,
                      observaciones
                  )
                  VALUES (
                      18,
                      '97651891D',
                      '50053553X',
                      2,
                      '13-05-2021',
                      '10:00:00',
                      '10:30:00',
                      'Muy alto'
                  );

INSERT INTO citas (
                      id_cita,
                      dni_paciente,
                      dni_sanitario,
                      id_tratamiento,
                      fecha,
                      hora_inicio,
                      hora_fin,
                      observaciones
                  )
                  VALUES (
                      19,
                      '61534088A',
                      '50053553X',
                      8,
                      '09-05-2021',
                      '10:00:00',
                      '10:30:00',
                      'Muy alto'
                  );

INSERT INTO citas (
                      id_cita,
                      dni_paciente,
                      dni_sanitario,
                      id_tratamiento,
                      fecha,
                      hora_inicio,
                      hora_fin,
                      observaciones
                  )
                  VALUES (
                      20,
                      '61534088A',
                      '50053553X',
                      8,
                      '09-05-2021',
                      '11:00:00',
                      '11:30:00',
                      'Muy alto'
                  );

INSERT INTO citas (
                      id_cita,
                      dni_paciente,
                      dni_sanitario,
                      id_tratamiento,
                      fecha,
                      hora_inicio,
                      hora_fin,
                      observaciones
                  )
                  VALUES (
                      21,
                      '61534088A',
                      '50053553X',
                      8,
                      '09-05-2021',
                      '11:00:00',
                      '11:30:00',
                      'Quizás llegue diez minutos tarde'
                  );

INSERT INTO citas (
                      id_cita,
                      dni_paciente,
                      dni_sanitario,
                      id_tratamiento,
                      fecha,
                      hora_inicio,
                      hora_fin,
                      observaciones
                  )
                  VALUES (
                      22,
                      '61534088A',
                      '50053553X',
                      8,
                      '14-05-2021',
                      '13:15:00',
                      '13:45:00',
                      'Muy alto'
                  );

INSERT INTO citas (
                      id_cita,
                      dni_paciente,
                      dni_sanitario,
                      id_tratamiento,
                      fecha,
                      hora_inicio,
                      hora_fin,
                      observaciones
                  )
                  VALUES (
                      23,
                      '10608076Q',
                      '60909365Y',
                      3,
                      '15-05-2021',
                      '13:15:00',
                      '13:45:00',
                      'Muy alto'
                  );

INSERT INTO citas (
                      id_cita,
                      dni_paciente,
                      dni_sanitario,
                      id_tratamiento,
                      fecha,
                      hora_inicio,
                      hora_fin,
                      observaciones
                  )
                  VALUES (
                      24,
                      '64717899Q',
                      '60909365Y',
                      8,
                      '10-05-2021',
                      '10:00:00',
                      '10:30:00',
                      'Muy alto'
                  );

INSERT INTO citas (
                      id_cita,
                      dni_paciente,
                      dni_sanitario,
                      id_tratamiento,
                      fecha,
                      hora_inicio,
                      hora_fin,
                      observaciones
                  )
                  VALUES (
                      25,
                      '10608076Q',
                      '60909365Y',
                      1,
                      '10-05-2021',
                      '10:00:00',
                      '10:30:00',
                      'Quizás llegue cinco minutos tarde'
                  );

INSERT INTO citas (
                      id_cita,
                      dni_paciente,
                      dni_sanitario,
                      id_tratamiento,
                      fecha,
                      hora_inicio,
                      hora_fin,
                      observaciones
                  )
                  VALUES (
                      26,
                      '10608076Q',
                      '60909365Y',
                      1,
                      '15-05-2021',
                      '10:00:00',
                      '10:30:00',
                      'Quizás llegue cinco minutos tarde'
                  );

INSERT INTO citas (
                      id_cita,
                      dni_paciente,
                      dni_sanitario,
                      id_tratamiento,
                      fecha,
                      hora_inicio,
                      hora_fin,
                      observaciones
                  )
                  VALUES (
                      27,
                      '64717899Q',
                      '60909365Y',
                      1,
                      '15-05-2021',
                      '13:15:00',
                      '13:45:00',
                      'Quizás llegue cinco minutos tarde'
                  );

INSERT INTO citas (
                      id_cita,
                      dni_paciente,
                      dni_sanitario,
                      id_tratamiento,
                      fecha,
                      hora_inicio,
                      hora_fin,
                      observaciones
                  )
                  VALUES (
                      28,
                      '10608076Q',
                      '88643753M',
                      4,
                      '11-05-2021',
                      '11:00:00',
                      '11:30:00',
                      'Quizás llegue cinco minutos tarde'
                  );

INSERT INTO citas (
                      id_cita,
                      dni_paciente,
                      dni_sanitario,
                      id_tratamiento,
                      fecha,
                      hora_inicio,
                      hora_fin,
                      observaciones
                  )
                  VALUES (
                      29,
                      '97651891D',
                      '88643753M',
                      2,
                      '11-05-2021',
                      '11:00:00',
                      '11:30:00',
                      'Quizás llegue cinco minutos tarde'
                  );

INSERT INTO citas (
                      id_cita,
                      dni_paciente,
                      dni_sanitario,
                      id_tratamiento,
                      fecha,
                      hora_inicio,
                      hora_fin,
                      observaciones
                  )
                  VALUES (
                      30,
                      '10608076Q',
                      '88643753M',
                      8,
                      '15-05-2021',
                      '11:00:00',
                      '11:30:00',
                      'Quizás llegue cinco minutos tarde'
                  );


-- Table: pacientes
DROP TABLE IF EXISTS pacientes;

CREATE TABLE pacientes (
    dni_paciente TEXT (20)  PRIMARY KEY
                            NOT NULL,
    nombre       TEXT (50),
    apellidos    TEXT (50),
    direccion    TEXT (100),
    telefono     TEXT (20),
    adjunto      BLOB
);

INSERT INTO pacientes (
                          dni_paciente,
                          nombre,
                          apellidos,
                          direccion,
                          telefono,
                          adjunto
                      )
                      VALUES (
                          '10608076Q',
                          'LeBron',
                          'James',
                          'Calle Río Guadalquivir',
                          '600100100',
                          NULL
                      );

INSERT INTO pacientes (
                          dni_paciente,
                          nombre,
                          apellidos,
                          direccion,
                          telefono,
                          adjunto
                      )
                      VALUES (
                          '74298758H',
                          'Kawhi',
                          'Leonard',
                          'Calle Río Tajo',
                          '600200200',
                          NULL
                      );

INSERT INTO pacientes (
                          dni_paciente,
                          nombre,
                          apellidos,
                          direccion,
                          telefono,
                          adjunto
                      )
                      VALUES (
                          '88336204N',
                          'Giannis',
                          'Antetokunmpo',
                          'Calle Río Ebro',
                          '600300300',
                          NULL
                      );

INSERT INTO pacientes (
                          dni_paciente,
                          nombre,
                          apellidos,
                          direccion,
                          telefono,
                          adjunto
                      )
                      VALUES (
                          '03160917G',
                          'Donovan',
                          'Mitchell',
                          'Calle Río Guadiana',
                          '600400400',
                          NULL
                      );

INSERT INTO pacientes (
                          dni_paciente,
                          nombre,
                          apellidos,
                          direccion,
                          telefono,
                          adjunto
                      )
                      VALUES (
                          '96791541C',
                          'Nikola',
                          'Jokic',
                          'Calle Getafe',
                          '600500500',
                          NULL
                      );

INSERT INTO pacientes (
                          dni_paciente,
                          nombre,
                          apellidos,
                          direccion,
                          telefono,
                          adjunto
                      )
                      VALUES (
                          '97806017N',
                          'Stephen',
                          'Curry',
                          'Calle Leganés',
                          '600600600',
                          NULL
                      );

INSERT INTO pacientes (
                          dni_paciente,
                          nombre,
                          apellidos,
                          direccion,
                          telefono,
                          adjunto
                      )
                      VALUES (
                          '18364040N',
                          'Kevin',
                          'Durant',
                          'Calle El Cebadero',
                          '600700700',
                          NULL
                      );

INSERT INTO pacientes (
                          dni_paciente,
                          nombre,
                          apellidos,
                          direccion,
                          telefono,
                          adjunto
                      )
                      VALUES (
                          '97651891D',
                          'Jayson',
                          'Tatum',
                          'Calle Pinto',
                          '600800800',
                          NULL
                      );

INSERT INTO pacientes (
                          dni_paciente,
                          nombre,
                          apellidos,
                          direccion,
                          telefono,
                          adjunto
                      )
                      VALUES (
                          '61534088A',
                          'Luka',
                          'Doncic',
                          'Calle Acorcón',
                          '600900900',
                          NULL
                      );

INSERT INTO pacientes (
                          dni_paciente,
                          nombre,
                          apellidos,
                          direccion,
                          telefono,
                          adjunto
                      )
                      VALUES (
                          '64717899Q',
                          'Russell',
                          'Westbrook',
                          'Avenida de Jaun Carlos I',
                          '600110110',
                          NULL
                      );


-- Table: sanitarios
DROP TABLE IF EXISTS sanitarios;

CREATE TABLE sanitarios (
    dni_sanitario TEXT (20) PRIMARY KEY,
    nombre        TEXT (50),
    apellidos     TEXT (50),
    especialidad  TEXT (50) 
);

INSERT INTO sanitarios (
                           dni_sanitario,
                           nombre,
                           apellidos,
                           especialidad
                       )
                       VALUES (
                           '50053553X',
                           'Light',
                           'Yagami',
                           'Podólogo'
                       );

INSERT INTO sanitarios (
                           dni_sanitario,
                           nombre,
                           apellidos,
                           especialidad
                       )
                       VALUES (
                           '60909365Y',
                           'Kenshin',
                           'Himura',
                           'Fisioterapeuta'
                       );

INSERT INTO sanitarios (
                           dni_sanitario,
                           nombre,
                           apellidos,
                           especialidad
                       )
                       VALUES (
                           '88643753M',
                           'Clark',
                           'Kent',
                           'Nutricionista'
                       );


-- Table: tratamientos
DROP TABLE IF EXISTS tratamientos;

CREATE TABLE tratamientos (
    id_tratamiento INTEGER    PRIMARY KEY ASC AUTOINCREMENT,
    nombre         TEXT (50),
    descripcion    TEXT (100),
    identificador  INTEGER
);

INSERT INTO tratamientos (
                             id_tratamiento,
                             nombre,
                             descripcion,
                             identificador
                         )
                         VALUES (
                             1,
                             'Infecciones por Hongos',
                             'Son causadas por diferentes tipos de hongos, incluyendo a los dermatofitos y las levaduras.',
                             NULL
                         );

INSERT INTO tratamientos (
                             id_tratamiento,
                             nombre,
                             descripcion,
                             identificador
                         )
                         VALUES (
                             2,
                             'El pie de atleta (tinea pedis)',
                             'El pie de atleta es una de las infecciones fúngicas de la piel más frecuentes. Es causada por un hongo que crece en áreas cálidas y húmedas, por ejemplo, entre los dedos y planta del pie.',
                             NULL
                         );

INSERT INTO tratamientos (
                             id_tratamiento,
                             nombre,
                             descripcion,
                             identificador
                         )
                         VALUES (
                             3,
                             'Hongos en uñas (Onicomicosis)',
                             'Los hongos en uñas son infecciones que suelen comenzar en el borde de la uña y se extienden poco a poco hasta la base.',
                             NULL
                         );

INSERT INTO tratamientos (
                             id_tratamiento,
                             nombre,
                             descripcion,
                             identificador
                         )
                         VALUES (
                             4,
                             'Uña encarnada (Onicocriptosis)',
                             'La uña encarnada se presenta cuando el borde de la uña se incrusta dentro del borde interno o externo del dedo del pie, ocasionando dolor, enrojecimiento e inflamación en el borde de la uña.',
                             NULL
                         );

INSERT INTO tratamientos (
                             id_tratamiento,
                             nombre,
                             descripcion,
                             identificador
                         )
                         VALUES (
                             5,
                             'Durezas',
                             'Nos referimos a las durezas como hiperqueratosis, la cual es una lesión que consiste en el engro-samiento de la capa externa de la piel compuesta por queratina.',
                             NULL
                         );

INSERT INTO tratamientos (
                             id_tratamiento,
                             nombre,
                             descripcion,
                             identificador
                         )
                         VALUES (
                             6,
                             'EPI',
                             'Electrolisis Percutánea Intratisular.',
                             NULL
                         );

INSERT INTO tratamientos (
                             id_tratamiento,
                             nombre,
                             descripcion,
                             identificador
                         )
                         VALUES (
                             7,
                             'Punción seca',
                             'Utiliza las agujas de acupuntura, pero más largas (dependiendo de la profundidad del músculo a tratar) para pinchar directamente ese “punto gatillo”.',
                             NULL
                         );

INSERT INTO tratamientos (
                             id_tratamiento,
                             nombre,
                             descripcion,
                             identificador
                         )
                         VALUES (
                             8,
                             'Cita con nutricionista',
                             'Cita con nutricionista estándar.',
                             NULL
                         );


COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
