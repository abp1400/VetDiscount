DROP DATABASE IF EXISTS `vetdiscountdb`;
CREATE DATABASE `vetdiscountdb`;
USE `vetdiscountdb`;

CREATE TABLE `user` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`username` VARCHAR(255) NOT NULL UNIQUE,
	`password` VARCHAR(255) NOT NULL,
	`email` VARCHAR(255) NOT NULL UNIQUE,
	PRIMARY KEY (`id`)
);

CREATE TABLE `store_type` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`id`)
);
CREATE TABLE `company` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(255) NOT NULL,
	`owner_id` INT(11),
	`type_id` INT(11),
	`store_url` VARCHAR(255),
	`chain_bool` TINYINT(1) DEFAULT 0,
	FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`),
	FOREIGN KEY (`type_id`) REFERENCES `store_type` (`id`),
	PRIMARY KEY (`id`)

);
CREATE TABLE `address` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`state` VARCHAR(2) NOT NULL,
	`city` VARCHAR(255) NOT NULL,
	`zip_code` INT(5) NOT NULL,
	`street` VARCHAR(255) NOT NULL,
	`lat` VARCHAR(255),
	`longitude` VARCHAR(255),
	PRIMARY KEY (`id`)
);

CREATE TABLE `location` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`hours` VARCHAR(255),
	`owner_id` INT(11),
	`phone_number` VARCHAR(255),
	`company_id` INT(11) NOT NULL,
	`address_id` INT(11) NOT NULL,
	FOREIGN KEY (`address_id`) REFERENCES `address` (`id`),
	FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
	FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`),
	PRIMARY KEY (`id`)
);



CREATE TABLE `discount` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`amount` VARCHAR(255) NOT NULL,
	`start_date` VARCHAR(255),
	`end_date` VARCHAR(255),
	`info` TEXT,
	`user_id` INT(11) NOT NULL,
	FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
	PRIMARY KEY (`id`)
);

CREATE TABLE `favorite_list` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`user_id` INT NOT NULL,
	`location_id` INT(11) NOT NULL,
	FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
	FOREIGN KEY (`location_id`) REFERENCES `location` (`id`),
	PRIMARY KEY (`id`)
);

CREATE TABLE `participating_locations` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`location_id` INT(11) NOT NULL,
	`discount_id` INT(11) NOT NULL,
	FOREIGN KEY (`location_id`) REFERENCES `location` (`id`),
	FOREIGN KEY (`discount_id`) REFERENCES `discount` (`id`),
	PRIMARY KEY (`id`)
);

GRANT ALL PRIVILEGES ON vetdiscountdb.* TO 'veteran'@'localhost' IDENTIFIED BY 'veteran';

INSERT INTO store_type (name) VALUES ('Education');
INSERT INTO store_type (name) VALUES ('Bookstore');
INSERT INTO store_type (name) VALUES ('Liquor');
INSERT INTO store_type (name) VALUES ('Coffee');
INSERT INTO store_type (name) VALUES ('Gym');
INSERT INTO store_type (name) VALUES ('Restaurant');
INSERT INTO store_type (name) VALUES ('Auto');
INSERT INTO store_type (name) VALUES ('Clothes');
INSERT INTO store_type (name) VALUES ('Hardware');
INSERT INTO store_type (name) VALUES ('Grocery');
INSERT INTO store_type (name) VALUES ('Drugstore');


INSERT INTO user (username, password, email) VALUES ('HunterK', '$2a$10$Fg.SjWBMTyvPqji9XeLWreAP7saCXJJX0ZCzyKqR2XG5qCblNo2xm', 'hunter@SD.com');
INSERT INTO company (name, owner_id, type_id) VALUES ('Skill Distillery', '1', '1');
INSERT INTO address (state, city, zip_code, street, lat, longitude) VALUES ('CO', 'Greenwood Village', '80111', '7400 East Orchard Road', '39.6088537', '-104.902828');
INSERT INTO location (company_id, address_id, owner_id) VALUES ('1', '1', '1');
INSERT INTO discount (amount, info, user_id) VALUES ('20% off', 'Vets get 20% off on all white board erasers!', '1');
INSERT INTO participating_locations (location_id, discount_id) VALUES ('1', '1');

INSERT INTO user (username, password, email) VALUES ('Aaron123', '$2a$10$9aWq9X4NP7DenMZ09dGzgOIsup0BOv81t2A6V.fgcfRMCP8kZqjJq', 'aaron@bn.com');
INSERT INTO company (name, owner_id, type_id) VALUES ('Barnes and Noble', '2', '2');
INSERT INTO address (state, city, zip_code, street, lat, longitude) VALUES ('FL', 'Lone Tree', '80124', '8374 Willow St', '39.564538', '-104.8864781');
INSERT INTO location (company_id, address_id, owner_id) VALUES ('2', '2', '2');
INSERT INTO discount (amount, info, start_date, end_date, user_id) VALUES ('10% off', '10% off all books.', '01/10/2018', '01/22/2018', '2');
INSERT INTO participating_locations (location_id, discount_id) VALUES ('2', '2');

INSERT INTO user (username, password, email) VALUES ('Jake123', '$2a$10$H8UEBL7QQG1iNmLhs1io9ugJG.7C7QlPu7WpfjhyGzBQJ1cW5KCXu', 'jake@starbucks.com');
INSERT INTO company (name, owner_id, type_id) VALUES ('Starbucks Coffee', '3', '4');
INSERT INTO address (state, city, zip_code, street, lat, longitude) VALUES ('CO', 'Greenwood Village', '80111', '9301 E Arapahoe Rd', '39.5955571', '-104.8802885');
INSERT INTO location (company_id, address_id, owner_id) VALUES ('3', '3', '3');
INSERT INTO discount (amount, info, start_date, end_date, user_id) VALUES ('Buy one, Get one.', 'Buy any small coffee, Get one for free', '01/10/2018', '01/30/2018', '3');
INSERT INTO participating_locations (location_id, discount_id) VALUES ('3', '3');

INSERT INTO user (username, password, email) VALUES ('Alex123', '$2a$10$9aWq9X4NP7DenMZ09dGzgOIsup0BOv81t2A6V.fgcfRMCP8kZqjJq', 'alex@lyonsliquor.com');
INSERT INTO company (name, owner_id, type_id) VALUES ('Lyons Den Liquor', '4', '3');
INSERT INTO address (state, city, zip_code, street, lat, longitude) VALUES ('CO', 'Greenwood Village', '80111', '5332 DTC Blvd', '39.6182514', '-104.8894243');
INSERT INTO location (company_id, address_id, owner_id) VALUES ('4', '4', '4');
INSERT INTO discount (amount, info, start_date, end_date, user_id) VALUES ('20% off', '20% off all domestic beer.', '03/1/2018', '03/30/2018', '4');
INSERT INTO participating_locations (location_id, discount_id) VALUES ('4', '4');

INSERT INTO company (name, owner_id, type_id) VALUES ('McDonald\'s', '1', '6');
INSERT INTO address (state, city, zip_code, street, lat, longitude) VALUES ('CO', 'Englewood', '80111', '6686 S Yosemite Ct', '39.5957548', '-104.8867951');
INSERT INTO location (company_id, address_id, owner_id) VALUES ('5', '5', '1');
INSERT INTO discount (amount, info, start_date, end_date, user_id) VALUES ('Free Hashbrown', 'Free Hashbrown with any purchase.', '01/1/2018', '04/30/2018', '1');
INSERT INTO participating_locations (location_id, discount_id) VALUES ('5', '5');

INSERT INTO company (name, owner_id, type_id) VALUES ('McDonald\'s', '1', '6');
INSERT INTO address (state, city, zip_code, street, lat, longitude) VALUES ('CO', 'Denver', '80237', '5090 S Quebec St', '39.6244532', '-104.9032913');
INSERT INTO location (company_id, address_id, owner_id) VALUES ('5', '6', '1');
INSERT INTO discount (amount, info, start_date, end_date, user_id) VALUES ('30% off', ' 30% off all breakfast meals.', '01/10/2018', '06/30/2018', '1');
INSERT INTO participating_locations (location_id, discount_id) VALUES ('6', '6');
INSERT INTO participating_locations (location_id, discount_id) VALUES ('6', '5');

INSERT INTO company (name, owner_id, type_id) VALUES ('McDonald\'s', '1', '6');
INSERT INTO address (state, city, zip_code, street, lat, longitude) VALUES ('CO', 'Englewood', '80112', '10555 E Briarwood Ave', '39.5930603', '-104.8570082');
INSERT INTO location (company_id, address_id, owner_id) VALUES ('5', '7', '1');
INSERT INTO discount (amount, info, start_date, end_date, user_id) VALUES ('10% off', '10% off all lunch items.', '02/1/2018', '03/30/2018', '1');
INSERT INTO participating_locations (location_id, discount_id) VALUES ('7', '7');
INSERT INTO participating_locations (location_id, discount_id) VALUES ('7', '5');

INSERT INTO company (name, owner_id, type_id) VALUES ('Barnes and Noble', '2', '2');
INSERT INTO address (state, city, zip_code, street, lat, longitude) VALUES ('CO', 'Glendale', '80246', '960 S Colorado Blvd', '39.6988336', '-104.9391188');
INSERT INTO location (company_id, address_id, owner_id) VALUES ('2', '8', '2');
INSERT INTO discount (amount, info, start_date, end_date, user_id) VALUES ('20% off', '20% off education books.', '04/1/2018', '07/30/2018', '2');
INSERT INTO participating_locations (location_id, discount_id) VALUES ('8', '8');
INSERT INTO participating_locations (location_id, discount_id) VALUES ('2', '2');

INSERT INTO company (name, owner_id, type_id) VALUES ('Barnes and Noble', '2', '2');
INSERT INTO address (state, city, zip_code, street, lat, longitude) VALUES ('CO', 'Aurora', '80012', '170 S Abilene St', '39.7132113', '-104.8243169');
INSERT INTO location (company_id, address_id, owner_id) VALUES ('2', '9', '2');
INSERT INTO discount (amount, info, start_date, end_date, user_id) VALUES ('Free Atlas', 'Free Atlas with each purchase over $50.', '01/10/2018', '01/22/2018', '2');
INSERT INTO participating_locations (location_id, discount_id) VALUES ('9', '9');
INSERT INTO participating_locations (location_id, discount_id) VALUES ('2', '2');

INSERT INTO company (name, owner_id, type_id) VALUES ('Starbucks Coffee', '3', '4');
INSERT INTO address (state, city, zip_code, street, lat, longitude) VALUES ('CO', 'Greenwood Village', '80111', '8745 E Orchard Rd', '39.6103767', '-104.8887861');
INSERT INTO location (company_id, address_id, owner_id) VALUES ('3', '10', '3');
INSERT INTO discount (amount, info, start_date, end_date, user_id) VALUES ('Free Espresso', 'Free Espresso with any purchase of $10 or more.', '01/10/2018', '01/30/2018', '3');
INSERT INTO participating_locations (location_id, discount_id) VALUES ('10', '10');
INSERT INTO participating_locations (location_id, discount_id) VALUES ('3', '3');

INSERT INTO company (name, owner_id, type_id) VALUES ('Starbucks Coffee', '3', '4');
INSERT INTO address (state, city, zip_code, street, lat, longitude) VALUES ('CO', 'Greenwood Village', '80111', '8000 E Belleview, Suite B10', '39.62308549999999', '-104.8962021');
INSERT INTO location (company_id, address_id, owner_id) VALUES ('3', '11', '3');
INSERT INTO discount (amount, info, start_date, end_date, user_id) VALUES ('Free Cookie', 'Free Cookie with any purchase of $20 or more.', '01/10/2018', '01/30/2018', '3');
INSERT INTO participating_locations (location_id, discount_id) VALUES ('11', '11');
INSERT INTO participating_locations (location_id, discount_id) VALUES ('10', '10');
INSERT INTO participating_locations (location_id, discount_id) VALUES ('3', '3');

INSERT INTO company (name, owner_id, type_id) VALUES ('Greenwood Village Wine & Spirits', '4', '3');
INSERT INTO address (state, city, zip_code, street, lat, longitude) VALUES ('CO', 'Greenwood Village', '80111', '9251 E Peakview Ave Unit E', '39.5990975', '-104.8816459');
INSERT INTO location (company_id, address_id, owner_id) VALUES ('12', '12', '4');
INSERT INTO discount (amount, info, start_date, end_date, user_id) VALUES ('10% off', '10% off all purchases over $70.', '03/1/2018', '03/30/2018', '4');
INSERT INTO participating_locations (location_id, discount_id) VALUES ('12', '12');
INSERT INTO participating_locations (location_id, discount_id) VALUES ('4', '4');

INSERT INTO company (name, owner_id, type_id) VALUES ('Orchards Wine & Spirits', '4', '3');
INSERT INTO address (state, city, zip_code, street, lat, longitude) VALUES ('CO', 'Greenwood Village', '80111', '5998 S Holly St', '39.6084148', '-104.9227704');
INSERT INTO location (company_id, address_id, owner_id) VALUES ('13', '13', '4');
INSERT INTO discount (amount, info, start_date, end_date, user_id) VALUES ('20% off', '20% off all purchases over $200.', '03/1/2018', '03/30/2018', '4');
INSERT INTO participating_locations (location_id, discount_id) VALUES ('13', '13');
INSERT INTO participating_locations (location_id, discount_id) VALUES ('4', '4');
