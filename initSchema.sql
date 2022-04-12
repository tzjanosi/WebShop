CREATE SCHEMA IF NOT EXISTS webshopDB
DEFAULT CHARACTER SET utf8
COLLATE utf8_hungarian_ci;
CREATE USER userForWebshop@localhost IDENTIFIED BY 'PassForWebshopDBUser';
GRANT ALL ON webshopDB.* TO userForWebshop@localhost;
USE webshopDB;
