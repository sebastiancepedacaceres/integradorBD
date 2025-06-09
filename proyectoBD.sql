-- Ejercicio 3

create database proyectoBD;
use proyectoBD;

create table if not exists padrinos ( 
	dni varchar(10) primary key,
    nom_padrino varchar(50) not null,
    direccion varchar(40),
    cod_postal varchar(10),
    fecha_nacimiento date,
    telefono varchar(20), 
    celular varchar(20) not null,
    facebook varchar(20)
);

create table if not exists donantes (
	dni_donante varchar(10) primary key,
    cuil varchar(14) unique, 
    ocupacion varchar(40),
    foreign key (dni_donante) references padrinos(dni) on delete cascade on update cascade
); 

create table if not exists contactos (
	dni_contactos varchar(10) primary key,
    estado varchar(20) not null,
    fecha_primer_contacto date not null,
    fecha_alta date,
    fecha_baja date,
    fecha_rechazo date,
    foreign key (dni_contactos) references padrinos(dni) on delete cascade on update cascade
);

create table if not exists programas (
	id_programa int auto_increment primary key, 
    nom_programa varchar(50) not null, 
    descripcion varchar(200) 
);

create table if not exists mediospagos (
	id_mp int auto_increment primary key,
    nom_titular varchar(50)
);
    
create table if not exists aportes (
	dni varchar(10),
    id_programa int,
    id_mp int,
    monto float,
    frecuencia varchar(15),
    primary key (dni,id_programa),
    foreign key (dni) references donantes(dni_donante) on delete cascade,
    foreign key (id_programa) references programas(id_programa) on delete cascade,
    foreign key (id_mp) references mediospagos(id_mp)
);

create table if not exists cuentas (
	cbu varchar(40) primary key,
    nro_cuenta varchar(40) ,
    tipo varchar(30),
    nom_banco varchar(40),
    num_sucursal varchar(10)
);

create table if not exists debitosbancarios (
	id_mp int primary key,
    cbu varchar(40),
    foreign key (id_mp) references mediospagos(id_mp) on delete cascade,
    foreign key (cbu) references cuentas(cbu) on delete cascade
);
	
create table if not exists transferencias (
	id_mp int primary key,
    cbu varchar(40),
    foreign key (id_mp) references mediospagos(id_mp) on delete cascade,
    foreign key (cbu) references cuentas(cbu) on delete cascade
);

create table if not exists tarjetascredito (
	id_mp int primary key,
    fecha_vencimiento varchar(6),
    nom_tarjeta varchar(50),
    num_tarjeta varchar(20),
    foreign key (id_mp) references mediospagos(id_mp) on delete cascade
);

-- Auditoría de eliminación de donantes
create table if not exists auditoria_eliminacion_donantes (
    dni_donante varchar(10),
    fecha_eliminacion datetime,
    usuario varchar(100)
);


DELIMITER $$

CREATE TRIGGER trg_delete_donante_auditoria
AFTER DELETE ON donantes
FOR EACH ROW
BEGIN
    INSERT INTO auditoria_eliminacion_donantes (dni_donante, fecha_eliminacion, usuario)
    VALUES (OLD.dni_donante, NOW(), CURRENT_USER());
END$$

DELIMITER ;


-- Ejercicio 4

INSERT INTO padrinos VALUES 
('1001', 'Ana Gómez', 'Calle 1', '1000', '1980-05-10', '123456789', '111111111', 'ana.fb'),
('1002', 'Luis Pérez', 'Calle 2', '1001', '1975-07-22', '234567890', '222222222', 'luis.fb'),
('1003', 'Carla Díaz', 'Calle 3', '1002', '1990-03-15', '345678901', '333333333', 'carla.fb'),
('1004', 'Mario Ruiz', 'Calle 4', '1003', '1985-08-12', '456789012', '444444444', 'mario.fb'),
('1005', 'Lucía Torres', 'Calle 5', '1004', '1992-11-20', '567890123', '555555555', 'lucia.fb'),
('1006', 'Jorge Blanco', 'Calle 6', '1005', '1970-09-09', '678901234', '666666666', 'jorge.fb');

INSERT INTO donantes VALUES 
('1001', '20-1001-1', 'Abogada'),
('1002', '20-1002-2', 'Ingeniero'),
('1003', '20-1003-3', 'Docente'),
('1004', '20-1004-4', 'Médico');

INSERT INTO contactos VALUES 
('1001', 'Adherido', '2022-01-01', '2022-02-01', NULL, NULL),
('1002', 'Amigo', '2022-01-15', '2022-03-01', NULL, NULL),
('1003', 'Adherido', '2022-02-01','2022-04-03' , NULL, NULL),
('1004', 'Amigo', '2022-03-01', '2022-04-01', NULL, NULL),
('1005', 'Sin llamar', '2022-04-01', NULL, NULL, NULL),
('1006', 'No acepta', '2022-05-01', NULL, NULL, '2022-05-02');

INSERT INTO programas (nom_programa, descripcion) VALUES 
('Niñez', 'Programa de ayuda a la niñez'),
('Salud', 'Programa de asistencia médica'),
('Educación', 'Programa de becas educativas');


INSERT INTO mediospagos (nom_titular) VALUES 
('Ana Gómez'),
('Luis Pérez'),
('Carla Díaz'),
('Mario Ruiz');

INSERT INTO aportes VALUES 
('1001', 1, 1, 1500, 'Mensual'),
('1002', 2, 2, 2000, 'Mensual'),
('1003', 3, 3, 1800, 'Semestral'),
('1004', 1, 4, 1000, 'Mensual');

INSERT INTO cuentas VALUES 
('CBU0001', '001', 'Caja de Ahorro', 'Banco Nación', '001'),
('CBU0002', '002', 'Cuenta Corriente', 'Banco Provincia', '002'),
('CBU0003', '003', 'Caja de Ahorro', 'Banco Ciudad', '003');

INSERT INTO debitosbancarios VALUES 
(1, 'CBU0001'),
(2, 'CBU0002');

INSERT INTO transferencias VALUES 
(3, 'CBU0003');

INSERT INTO tarjetascredito VALUES 
(4, '03-27', 'Mario Ruiz', '4111111111111122');
