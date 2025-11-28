CREATE DATABASE sosdrink_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE sosdrink_db;

-- Crear tablas

CREATE TABLE categoria_blog (
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL
);

CREATE TABLE blog (
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    contenido MEDIUMTEXT NOT NULL,
    fecha DATETIME NOT NULL,
    imagen_url VARCHAR(255) DEFAULT NULL,
    resumen VARCHAR(255) NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    categoria_blog_id BIGINT NOT NULL,
    FOREIGN KEY (categoria_blog_id) REFERENCES categoria_blog(id)
);

CREATE TABLE tipo_usuario (
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    rol VARCHAR(255) NOT NULL,
    nombre VARCHAR(255) DEFAULT NULL
);

CREATE TABLE usuario (
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    correo VARCHAR(255) UNIQUE NOT NULL,
    clave VARCHAR(255) NOT NULL,
    run VARCHAR(255) UNIQUE NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    apellidos VARCHAR(255) NOT NULL,
    direccion VARCHAR(255) DEFAULT NULL,
    tipo_usuario_id BIGINT NOT NULL,
    FOREIGN KEY (tipo_usuario_id) REFERENCES tipo_usuario(id)
);

CREATE TABLE tipo_producto (
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(255) DEFAULT NULL
);

CREATE TABLE producto(
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    detalle VARCHAR(255) NOT NULL,
    imagen VARCHAR(255) NOT NULL,
    valor INT NOT NULL,
    iva INT NOT NULL,
    stock INT NOT NULL,
    critical_stock INT NOT NULL,
    tipo_producto_id BIGINT NOT NULL,
    FOREIGN KEY (tipo_producto_id) REFERENCES tipo_producto(id)
);

CREATE TABLE item_carrito (
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    cantidad INT NOT NULL,
    producto_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    FOREIGN KEY (producto_id) REFERENCES producto(id) ON DELETE CASCADE,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE,
    UNIQUE KEY carrito_unico (producto_id, usuario_id)
);

CREATE TABLE folio_generator ( 
    `year` INT PRIMARY KEY NOT NULL,
    current_number INT DEFAULT 1
);

CREATE TABLE boleta (
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    cliente_nombre VARCHAR(255) NOT NULL,
    cliente_run VARCHAR(255) NOT NULL,
    fecha_emision DATETIME NOT NULL,
    folio VARCHAR(255) NOT NULL,
    iva INT NOT NULL,
    subtotal INT NOT NULL
);

CREATE TABLE linea_boleta (
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    cantidad INT NOT NULL,
    detalle VARCHAR(255) NOT NULL,
    iva_unitario INT NOT NULL,
    valor_unitario INT NOT NULL,
    boleta_id BIGINT NOT NULL,
    FOREIGN KEY (boleta_id) REFERENCES boleta(id)
);

-- Insertar datos

INSERT INTO categoria_blog (nombre) VALUES
('Productos'),
('Regulación'),
('Salud Pública');

INSERT INTO blog (contenido, fecha, imagen_url, resumen, titulo, categoria_blog_id) VALUES
('Estamos emocionados de anunciar el lanzamiento de nuestro nuevo sabor \"Tropical Power\". Esta bebida combina lo mejor de la piña, mango y un toque de maracuyá, ideal para quienes buscan energía y frescura en cada sorbo. Nuestro equipo de desarrollo trabajó cuidadosamente para equilibrar sabor, energía y seguridad en el consumo.\n\nAdemás, se han implementado estrictos controles de calidad y etiquetado, garantizando que cada botella cumpla con las normas vigentes de salud pública y consumo responsable. Recomendamos disfrutarla con moderación y acompañada de hidratación adecuada.\n\nPróximamente habrá degustaciones y promociones especiales en tiendas seleccionadas, así como información educativa sobre hábitos seguros de consumo.','2025-09-15 00:00:00.000000','api/img/nuevoSaborEnergetico2025.jpg','Descubre \'Tropical Power\', la nueva bebida que combina energía y frescura frutal.','¡Lanzamos nuestro nuevo sabor de bebida energética + alcohol!',1),
('El Ministerio de Salud anunció nuevas **regulaciones de etiquetado para bebidas alcohólicas**. Todas las botellas deberán incluir advertencias sobre consumo responsable y riesgos para la salud.\n\n- Etiquetas visibles con contenido alcohólico y advertencias sanitarias.\n- Información obligatoria sobre consumo recomendado por día.\n- Entrada en vigor: 1 de octubre de 2025.\n\nEstas medidas buscan reducir el consumo excesivo y proteger la salud de la población.','2025-09-10 00:00:00.000000','api/img/leyEtiquetadoAlcohol2025.jpg','El gobierno establece advertencias obligatorias en envases de alcohol.','Nuevos requisitos de etiquetado para bebidas alcohólicas',2),
('Estamos orgullosos de presentar nuestro licor cítrico premium, elaborado con limón, maracuyá y un toque secreto de hierbas aromáticas. Ideal para preparar cócteles sofisticados o disfrutar solo con hielo.\n\nCada botella pasa por un riguroso control de calidad y sigue los estándares de etiquetado responsable. Su sabor equilibrado permite múltiples combinaciones en la mixología, haciendo de esta bebida un imprescindible en bares y hogares.\n\nDisponible en tiendas especializadas y con promociones de lanzamiento para quienes deseen probar esta novedad.','2025-04-18 00:00:00.000000','api/img/llegoLicorCitrico2025.jpg','Licor con notas de limón y maracuyá, perfecto para cócteles y mixología.','¡Llegó el nuevo licor cítrico de la línea premium!',1),
('La Secretaría de Salud lanzó una **campaña de consumo responsable** dirigida a jóvenes y adultos. El objetivo es informar sobre riesgos y fomentar decisiones conscientes.\n\n- Charlas y talleres en colegios y universidades.\n- Material educativo sobre efectos del alcohol y tabaco.\n- Plataforma online con recursos interactivos para la comunidad.\n\nLa campaña estará activa durante todo el segundo semestre de 2025.','2025-07-18 00:00:00.000000','api/img/campanaConsumoResponsable2025.jpg','Iniciativa nacional promueve hábitos seguros y conscientes.','Campaña de consumo responsable de alcohol y tabaco',3),
('El gobierno implementará un **aumento en impuestos al tabaco** para desincentivar su consumo y financiar programas de prevención y tratamiento de enfermedades relacionadas.\n\n- Incremento de 15% en cigarrillos y tabaco de liar.\n- Fondos destinados a campañas educativas y clínicas de cesación.\n- Medida entra en vigencia el 1 de junio de 2025.\n\nSe espera una reducción progresiva del consumo especialmente entre jóvenes.','2025-05-12 00:00:00.000000','api/img/impuestoTabaco2025.jpg','Nuevo gravamen busca reducir consumo y financiar programas de salud.','Aumento de impuestos a productos de tabaco',2),
('El Ministerio de Salud anunció **restricciones estrictas para la publicidad de bebidas alcohólicas**, incluyendo redes sociales y televisión.\n\n- Prohibición de anuncios dirigidos a menores de 18 años.\n- Límites en horarios de transmisión y formatos digitales.\n- Multas para empresas que incumplan las normas.\n\nEstas medidas entrarán en vigor a partir de abril de 2025.','2025-03-05 00:00:00.000000','api/img/restriccionesPublicidadAlcohol2025.jpg','Limitaciones en medios y redes sociales buscan proteger a menores.','Nuevas restricciones a la publicidad de alcohol',2),
('La Dirección de Salud emitió un **informe sobre los riesgos del consumo de tabaco** destacando la relación con enfermedades respiratorias y cardiovasculares.\n\n- Se recomiendan programas de cesación para fumadores activos.\n- Importancia de evitar el tabaquismo pasivo, especialmente en niños.\n- Difusión de información preventiva a través de medios y escuelas.\n\nEl objetivo es concientizar sobre los daños y promover hábitos saludables.','2025-02-15 00:00:00.000000','api/img/alertaSaludTabaco2025.jpg','Informe sobre riesgos del consumo de tabaco y enfermedades asociadas.','Alerta sanitaria: efectos del tabaco en la salud',3);

INSERT INTO tipo_producto (nombre) VALUES
('Alcohol'),
('Tabaco');

INSERT INTO producto (nombre, detalle, imagen, valor, iva, stock, critical_stock, tipo_producto_id) VALUES
('Pisco Mistral 35° 750ml', 'Pisco chileno suave y aromático, ideal para preparar piscolas.', 'api/img/pisco-mistral-35.jpg', 8990, 1700, 50, 1, 1),
('Ron Havana Club 7 Años 750ml', 'Ron cubano añejado, con notas de caramelo y especias.', 'api/img/ron-havana-club.jpg', 13990, 2660, 30, 1, 1),
('Cerveza Artesanal Kuntsmann 330ml', 'Cerveza lager chilena, fresca y equilibrada.', 'api/img/cerveza-artesanal.jpg', 1990, 380, 120, 1, 1),
('Vino Tinto Casillero del Diablo Cabernet Sauvignon 750ml', 'Vino chileno con cuerpo, aroma frutal y notas de roble.', 'api/img/vino-casillero.jpg', 6490, 1230, 40, 1, 1),
('Cigarrillos Marlboro Gold 20 unidades', 'Clásico sabor Marlboro en su versión más suave.', 'api/img/cigarros-marlboro.jpg', 5790, 1100, 200, 2, 2),
('Tequila José Cuervo Especial 700ml', 'Tequila mexicano añejo, con sabor suave y notas de agave.', 'api/img/tequila-jose-cuervo.jpg', 11990, 2280, 25, 1, 1),
('Whisky Johnnie Walker Black Label 750ml', 'Whisky escocés de malta y grano, 12 años de maduración.', 'api/img/whisky-jw-black.jpg', 24990, 4740, 35, 1, 1),
('Cerveza Corona 355ml', 'Cerveza lager mexicana, refrescante y ligera.', 'api/img/cerveza-corona.jpg', 1590, 300, 150, 1, 1),
('Ron Bacardi Carta Blanca 750ml', 'Ron cubano blanco, ideal para cocteles y mezclas.', 'api/img/ron-bacardi.jpg', 12990, 2470, 40, 1, 1),
('Cigarros Marlboro Red 20 unidades', 'Clásico sabor Marlboro fuerte y tradicional.', 'api/img/cigarros-marlboro-red.jpg', 5990, 1130, 180, 2, 2),
('Vino Blanco Concha y Toro Sauvignon Blanc 750ml', 'Vino chileno fresco, con aromas cítricos y acidez equilibrada.', 'api/img/vino-sauvignon.jpg', 6990, 1330, 50, 1, 1);

INSERT INTO tipo_usuario (rol, nombre) VALUES
('ADMIN', 'Administrador'),
('CLIENT', 'Cliente'),
('VENDOR', 'Vendedor');

INSERT INTO usuario (correo, clave, run, nombre, apellidos, direccion, tipo_usuario_id) VALUES
('camila.gonzalez@gmail.com', '$2a$10$nAuLy9ZUBDLLk/iGkBA2De3Cm8e8CQWoway1ZgiucAgLAojm1VD72', '12.345.678-9', 'Camila', 'González Pérez', 'Av. Providencia 1234, Depto. 12', 1),
('mariajose.soto@profesor.duoc.cl', '$2a$10$yPBZXtgJVZdfOUe/LZOSf.0thju8GA3atzRZWb8oa33yR4bykV2WC', '15.432.109-7', 'María José', 'Soto López', 'Calle O’Higgins 890, Oficina 3', 3),
('valentina.vargas@duoc.cl', '$2a$10$p5Nx0h3nYQQamDnH52YkYu4kAimgf1vNlrrjwyKHGXzkbSYV8VeQO', '18.765.432-1', 'Valentina', 'Vargas Muñoz', 'Av. Libertad 234, Local 2', 3),
('jorge.torres@gmail.com', '$2a$10$LnUrRKjx.EfskqElU60HBugrCHLssTdEh1zs1duk0E.PF5mPxkY5q', '7.654.321-0', 'Jorge', 'Torres Fernández', 'Pasaje Los Pinos 45, Depto. 5', 2),
('felipe.rojas@duoc.cl', '$2a$10$Q7SnAS7KPK0RUNvzdTrfKuE4Xnxjm3x8XhAhrM/5AszdRiP28uKIW', '9.876.543-2', 'Felipe', 'Rojas Martínez', 'Calle Quillota 567, Casa B', 2);

INSERT INTO boleta (cliente_nombre, cliente_run, fecha_emision, folio, iva, subtotal) VALUES
('Jorge Torres Fernández', '7.654.321-0', '2025-11-28 10:15:00', '2025-00001', 2080, 19970),
('Jorge Torres Fernández', '7.654.321-0', '2025-11-28 11:00:00', '2025-00002', 8250, 43470);

INSERT INTO boleta (cliente_nombre, cliente_run, fecha_emision, folio, iva, subtotal) 
VALUES 
('Felipe Rojas Martínez', '9.876.543-2', '2025-11-28 14:00:00', '2025-00003', 5130, 26970);

INSERT INTO linea_boleta (cantidad, detalle, iva_unitario, valor_unitario, boleta_id) VALUES
(2, 'Pisco Mistral 35° 750ml', 1700, 8990, 1),
(1, 'Cerveza Artesanal Kuntsmann 330ml', 380, 1990, 1);

INSERT INTO linea_boleta (cantidad, detalle, iva_unitario, valor_unitario, boleta_id) VALUES
(1, 'Vino Tinto Casillero del Diablo Cabernet Sauvignon 750ml', 1230, 6490, 2),
(1, 'Tequila José Cuervo Especial 700ml', 2280, 11990, 2),
(1, 'Whisky Johnnie Walker Black Label 750ml', 4740, 24990, 2);

INSERT INTO linea_boleta (cantidad, detalle, iva_unitario, valor_unitario, boleta_id) 
VALUES
(1, 'Ron Bacardi Carta Blanca 750ml', 2470, 12990, 3),
(2, 'Vino Blanco Concha y Toro Sauvignon Blanc 750ml', 1330, 6990, 3);


COMMIT;
