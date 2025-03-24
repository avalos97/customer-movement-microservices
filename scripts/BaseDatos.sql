
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
DROP TABLE IF EXISTS movimiento;
DROP TABLE IF EXISTS cuenta;
DROP TABLE IF EXISTS cliente;
DROP TABLE IF EXISTS persona;

CREATE TABLE persona (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nombre VARCHAR(100) NOT NULL,
    genero VARCHAR(10),
    edad INT,
    identificacion VARCHAR(50) NOT NULL UNIQUE,
    direccion VARCHAR(255),
    telefono VARCHAR(20)
);

CREATE TABLE cliente (
    persona_id UUID PRIMARY KEY,
    contrasena VARCHAR(100) NOT NULL,
    estado BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_persona FOREIGN KEY (persona_id) REFERENCES persona(id) ON DELETE CASCADE
);

CREATE TABLE cuenta (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    numero_cuenta BIGINT NOT NULL UNIQUE,
    cliente_id UUID NOT NULL,
    tipo_cuenta VARCHAR(50) NOT NULL,
    saldo_inicial NUMERIC(12,2) NOT NULL,
    estado BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE movimiento (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    cuenta_id UUID NOT NULL, 
    fecha TIMESTAMP NOT NULL,
    tipo_movimiento VARCHAR(50) NOT NULL,
    valor NUMERIC(12,2) NOT NULL, 
    saldo NUMERIC(12,2) NOT NULL,
    estado BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_cuenta FOREIGN KEY (cuenta_id) REFERENCES cuenta(id) ON DELETE CASCADE
);

INSERT INTO cuenta
(id, numero_cuenta, cliente_id, tipo_cuenta, saldo_inicial, estado)
VALUES('e20795b1-bb63-4f8d-936f-e6390db283ce'::uuid, 638493, '2746fef3-2155-470d-9c29-743a30f1550e'::uuid, 'AHORRO', 2000.00, true);
INSERT INTO cuenta
(id, numero_cuenta, cliente_id, tipo_cuenta, saldo_inicial, estado)
VALUES('c949ace2-fcc7-4590-b3e8-e0eb2fc04c12'::uuid, 927384, '99a16961-b0c6-49fe-9c3b-6937866ec991'::uuid, 'CORRIENTE', 1000.00, true);
INSERT INTO cliente
(persona_id, contrasena, estado)
VALUES('2746fef3-2155-470d-9c29-743a30f1550e'::uuid, '12345', true);
INSERT INTO cliente
(persona_id, contrasena, estado)
VALUES('99a16961-b0c6-49fe-9c3b-6937866ec991'::uuid, 'password', true);
INSERT INTO cuenta
(id, numero_cuenta, cliente_id, tipo_cuenta, saldo_inicial, estado)
VALUES('e20795b1-bb63-4f8d-936f-e6390db283ce'::uuid, 638493, '2746fef3-2155-470d-9c29-743a30f1550e'::uuid, 'AHORRO', 2000.00, true);
INSERT INTO cuenta
(id, numero_cuenta, cliente_id, tipo_cuenta, saldo_inicial, estado)
VALUES('c949ace2-fcc7-4590-b3e8-e0eb2fc04c12'::uuid, 927384, '99a16961-b0c6-49fe-9c3b-6937866ec991'::uuid, 'CORRIENTE', 1000.00, true);
INSERT INTO movimiento
(id, cuenta_id, fecha, tipo_movimiento, valor, saldo, estado)
VALUES('a317919d-1604-49d8-86bd-1854f59b425a'::uuid, 'c949ace2-fcc7-4590-b3e8-e0eb2fc04c12'::uuid, '2025-03-23 15:48:09.646', 'DEPOSITO', 100.00, 1100.00, true);
INSERT INTO movimiento
(id, cuenta_id, fecha, tipo_movimiento, valor, saldo, estado)
VALUES('cee47da2-8290-4096-bae5-03c73de260a1'::uuid, 'c949ace2-fcc7-4590-b3e8-e0eb2fc04c12'::uuid, '2025-03-23 15:48:31.862', 'RETIRO', -200.00, 900.00, true);
INSERT INTO movimiento
(id, cuenta_id, fecha, tipo_movimiento, valor, saldo, estado)
VALUES('dfbbdda3-0555-4295-8bce-40c52da1df6e'::uuid, 'e20795b1-bb63-4f8d-936f-e6390db283ce'::uuid, '2025-03-23 15:50:56.494', 'RETIRO', -200.00, 1800.00, true);