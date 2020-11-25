-- CREATE TABLE `usuario` (
--	`user_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
--	`name` varchar(255) NOT NULL,
--	`surname` varchar(255) DEFAULT NULL,
--	`email` varchar(255) NOT NULL UNIQUE,
--	`password` varchar(255) NOT NULL,
--	`profile` enum ('USER', 'COMPANY', 'ADMIN') NOT NULL,
--	PRIMARY KEY (`user_id`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- to_date('31/05/2015', 'DD/MM/YYYY'),

CREATE TABLE `demandante` (
	`user_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
	`name` varchar(255) NOT NULL,
	`surname` varchar(255) DEFAULT NULL,
	`birthday` date NOT NULL,
	`email` varchar(255) NOT NULL UNIQUE,
	`password` varchar(255) NOT NULL,
	`city` varchar(255) NOT NULL DEFAULT 'Madrid',
	`province` varchar(255) NOT NULL DEFAULT 'Madrid',
	`num_offers` int(11) unsigned DEFAULT 0,
	`profile` enum ('USER', 'COMPANY', 'ADMIN') DEFAULT 'USER',
	 PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `empresa` (
	`user_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
	`name` varchar(255) NOT NULL,
	`company_name` varchar(255) NOT NULL,
	`surname` varchar(255) DEFAULT NULL,
	`description` varchar(255) NOT NULL,
	`email` varchar(255) NOT NULL UNIQUE,
	`password` varchar(255) NOT NULL,
	`city` varchar(255) NOT NULL DEFAULT 'Madrid',
	`province` varchar(255) NOT NULL DEFAULT 'Madrid',
	`num_offers` int(11) unsigned DEFAULT 0,
	`profile` enum ('USER', 'COMPANY', 'ADMIN') DEFAULT 'COMPANY',
	 PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `admin` (
	`user_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
	`name` varchar(255) NOT NULL,
	`surname` varchar(255) DEFAULT NULL,
	`email` varchar(255) NOT NULL UNIQUE,
	`password` varchar(255) NOT NULL,
	`city` varchar(255) NOT NULL DEFAULT 'Madrid',
	`province` varchar(255) NOT NULL DEFAULT 'Madrid',
	`profile` enum ('USER', 'COMPANY', 'ADMIN') DEFAULT 'ADMIN',
	 PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `oferta` (
	`offer_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
	`title` varchar(255) NOT NULL,
	`description` varchar(255) DEFAULT NULL,
	`city` varchar(255) NOT NULL DEFAULT 'Madrid',
	`province` varchar(255) NOT NULL DEFAULT 'Madrid',
	`income` int(11) DEFAULT NULL,
	`due_date` date NOT NULL,
	`visible` bit(1) DEFAULT 0,
	`company_id` int(11) unsigned NOT NULL,
	`num_applicants` int(11) unsigned DEFAULT 0,
	PRIMARY KEY (`offer_id`),
	CONSTRAINT `company_id` FOREIGN KEY (`company_id`)
		REFERENCES `empresa` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `registro_oferta`(
	`register_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
	`user_id` int(11) unsigned NOT NULL,
	`offer_id` int(11) unsigned NOT NULL,
	`expired` bit(1) DEFAULT 0,
	`selected_for_interview` bit(1) DEFAULT 0,
	`registered_at` date NOT NULL,
	KEY (`user_id`),
	KEY (`offer_id`),
	CONSTRAINT `user_id` FOREIGN KEY (`user_id`)
		REFERENCES `demandante` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT `offer_id` FOREIGN KEY (`offer_id`)
		REFERENCES `oferta` (`offer_id`) ON DELETE CASCADE ON UPDATE CASCADE,
	PRIMARY KEY (`register_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
