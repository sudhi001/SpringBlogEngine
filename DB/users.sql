CREATE DATABASE OldBlogEngine;

#CREATE USER 'dbuser'@'localhost' IDENTIFIED BY '123test321';

GRANT ALL PRIVILEGES ON OldBlogEngine.* TO 'dbuser'@'localhost';

FLUSH PRIVILEGES;
