INSERT INTO `admin` (`user_id`, `name`, `surname`, `email`, `password`, `profile`)
	VALUES (1002, 'usuario', 'admin', 'admin@wgle.org',  SHA2('admin',0), 'ADMIN');
	
INSERT INTO `empresa` (`user_id`, `name`, `surname`, `email`, `password`, `profile`,
	`company_name`, `description`, `city`, `province`)
	VALUES (1003, 'usuario', 'empresa', 'company@wgle.org', SHA2('company',0), 'COMPANY',
		'Empresa_Prueba','Usuario empresa de prueba', 'Madrid', 'Madrid'
	);
	
INSERT INTO `empresa` (`user_id`, `name`, `surname`, `email`, `password`, `profile`,
	`company_name`, `description`, `city`, `province`)
	VALUES (1004, 'usuario', 'empresa2', 'company2@wgle.org', SHA2('company',0), 'COMPANY',
		'Empresa_Prueba','Usuario empresa de prueba', 'Madrid', 'Madrid'
	);

INSERT INTO `empresa` (`user_id`, `name`, `surname`, `email`, `password`, `profile`,
	`company_name`, `description`, `city`, `province`)
	VALUES (1005, 'usuario', 'empresa3', 'company3@wgle.org', SHA2('company',0), 'COMPANY',
		'Empresa_Prueba','Usuario empresa de prueba', 'Madrid', 'Madrid'
	);
	
INSERT INTO `demandante` (`user_id`, `name`, `surname`, `email`, `password`, `profile`, `birthday`)
	VALUES (1006, 'usuario', 'demandante', 'user@wgle.org', SHA2('user',0), 'USER',
		str_to_date('21/02/1993','%d/%m/%Y')
	);
	
INSERT INTO `demandante` (`user_id`, `name`, `surname`, `email`, `password`, `profile`, `birthday`)
	VALUES (1007, 'usuario', 'demandante', 'user2@wgle.org', SHA2('user',0), 'USER',
		str_to_date('21/02/1993','%d/%m/%Y')
	);

INSERT INTO `demandante` (`user_id`, `name`, `surname`, `email`, `password`, `profile`, `birthday`)
	VALUES (1008, 'usuario', 'demandante', 'user3@wgle.org', SHA2('user',0), 'USER',
		str_to_date('21/02/1993','%d/%m/%Y')
	);

INSERT INTO `demandante` (`user_id`, `name`, `surname`, `email`, `password`, `profile`, `birthday`)
	VALUES (1009, 'usuario', 'demandante', 'user4@wgle.org', SHA2('user',0), 'USER',
		str_to_date('21/02/1993','%d/%m/%Y')
	);

INSERT INTO `demandante` (`user_id`, `name`, `surname`, `email`, `password`, `profile`, `birthday`)
	VALUES (1010, 'usuario', 'demandante', 'user0@wgle.org', SHA2('user',0), 'USER',
		str_to_date('21/02/1993','%d/%m/%Y')
	);	

INSERT INTO `oferta`(`offer_id`, `title`, `description`, `income`, `city`, `province`, `due_date`, `company_id`)
	VALUES (2001, 'Mecanico de camiones', 'Oficial de primera', 2300, 'Alcala de Henares', 'Madrid',
	str_to_date('31/12/2020','%d/%m/%Y'), 1003
);

INSERT INTO `oferta`(`offer_id`, `title`, `description`, `income`, `city`, `province`, `due_date`, `company_id`)
	VALUES (2002, 'Programador Java', 'En empresa de excelente reputacion', 1000, 'Madrid', 'Madrid',
	str_to_date('31/12/2020','%d/%m/%Y'), 1003
);

INSERT INTO `oferta`(`offer_id`, `title`, `description`, `income`, `city`, `province`, `due_date`, `company_id`)
	VALUES (2003, 'Programador C', 'En empresa startup', 1000, 'Madrid', 'Madrid',
	str_to_date('31/12/2020','%d/%m/%Y'), 1003
);

INSERT INTO `oferta`(`offer_id`, `title`, `description`, `income`, `city`, `province`, `due_date`, `company_id`)
	VALUES (2004, 'Analista', 'Empresa nueva startup', 1000, 'Madrid', 'Madrid',
	str_to_date('31/12/2020','%d/%m/%Y'), 1003
);

INSERT INTO `registro_oferta`(`register_id`, `user_id`, `offer_id`, `registered_at`)
	VALUES(3001, 1006, 2002, str_to_date('27/11/2020','%d/%m/%Y')
);

INSERT INTO `registro_oferta`(`register_id`, `user_id`, `offer_id`, `registered_at`)
	VALUES(3002, 1006, 2003, str_to_date('27/11/2020','%d/%m/%Y')
);

UPDATE `demandante` SET `num_offers` = 2 WHERE `user_id` = 1006;

INSERT INTO `registro_oferta`(`register_id`, `user_id`, `offer_id`, `registered_at`)
	VALUES(3003, 1007, 2001, str_to_date('27/11/2020','%d/%m/%Y')
);

UPDATE `demandante` SET `num_offers` = 1 WHERE `user_id` = 1007;

INSERT INTO `registro_oferta`(`register_id`, `user_id`, `offer_id`, `registered_at`)
	VALUES(3004, 1008, 2001, str_to_date('27/11/2020','%d/%m/%Y')
);

INSERT INTO `registro_oferta`(`register_id`, `user_id`, `offer_id`, `registered_at`)
	VALUES(3005, 1008, 2004, str_to_date('27/11/2020','%d/%m/%Y')
);

UPDATE `demandante` SET `num_offers` = 2 WHERE `user_id` = 1008;

UPDATE `oferta` SET `num_applicants` = 2 WHERE `offer_id` = 2001;
UPDATE `oferta` SET `num_applicants` = 1 WHERE `offer_id` = 2002;
UPDATE `oferta` SET `num_applicants` = 1 WHERE `offer_id` = 2003;
UPDATE `oferta` SET `num_applicants` = 1 WHERE `offer_id` = 2004;

UPDATE `empresa` SET `num_offers` = 4 WHERE `user_id` = 1003;