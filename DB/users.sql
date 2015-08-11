CREATE DATABASE BlogEngine;

CREATE USER 'dbuser'@'localhost' IDENTIFIED BY '123test321';

GRANT ALL PRIVILEGES ON BlogEngine.* TO 'dbuser'@'localhost';

FLUSH PRIVILEGES;
