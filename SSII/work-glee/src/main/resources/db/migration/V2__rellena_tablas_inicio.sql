INSERT INTO `admin` (`user_id`, `name`, `surname`, `email`, `password`, `profile`)
	VALUES (1, 'Pedro', 'Garcia Martinez', 'pgarcia@wgle.org',  SHA2('contrasena',0), 'ADMIN');

INSERT INTO `empresa` (`user_id`, `name`, `surname`, `email`, `password`, `profile`,
	`company_name`, `description`, `city`, `province`)
	VALUES (2, 'Maria', 'Guerrero Hernandez', 'guerrerohernandez@telefonica.com', SHA2('contrasena',0), 'COMPANY',
		'Adecco','Empresa temporal de empleo', 'Barcelona', 'Barcelona'
	);

INSERT INTO `empresa` (`user_id`, `name`, `surname`, `email`, `password`, `profile`,
	`company_name`, `description`, `city`, `province`)
	VALUES (3, 'Laura', 'Perez Fernandez', 'lperezfer@adecco.com', SHA2('contrasena',0), 'COMPANY',
		'Telefonica', 'Empresa de telecomunicaciones', 'Madrid', 'Madrid'
	);

INSERT INTO `demandante` (`user_id`, `name`, `surname`, `email`, `password`, `profile`, `birthday`)
	VALUES (4, 'Fernando', 'Ramirez Alvarado', 'fernando952t@gmail.com', SHA2('contrasena',0), 'USER',
		str_to_date('19/09/1995','%d/%m/%Y')
	);

INSERT INTO `demandante` (`user_id`, `name`, `surname`, `email`, `password`, `profile`, `birthday`)
	VALUES (5, 'Teresa', 'Campos Martinez', 'teresacampos87@gmail.com', SHA2('contrasena',0), 'USER',
		str_to_date('3/11/1987','%d/%m/%Y')
	);

INSERT INTO `oferta`(`offer_id`, `title`, `description`, `income`, `city`, `province`, `due_date`, `company_id`)
	VALUES (101, 'Mecanico de camiones', 'Oficial de primera', 2300, 'Alcala de Henares', 'Madrid',
	str_to_date('31/12/2020','%d/%m/%Y'), 2
);

INSERT INTO `oferta`(`offer_id`, `title`, `description`, `income`, `city`, `province`, `due_date`, `company_id`)
	VALUES (102, 'Programador Java Junior', 'En empresa de excelente reputacion', 1000, 'Madrid', 'Madrid',
	str_to_date('31/12/2020','%d/%m/%Y'), 3
);

INSERT INTO `registro_oferta`(`register_id`, `user_id`, `offer_id`, `registered_at`)
	VALUES(1, 4, 101, str_to_date('27/11/2020','%d/%m/%Y')
);

UPDATE `oferta`
	SET `num_applicants` = 1
	WHERE `offer_id` = 101;

UPDATE `demandante`
	SET `num_offers` = 1
	WHERE `user_id` = 4;

UPDATE `empresa`
	SET `num_offers` = 1
	WHERE `user_id` = 2;

UPDATE `empresa`
	SET `num_offers` = 1
	WHERE `user_id` = 3;