use BlogEngine;

DROP TABLE IF EXISTS image;

DROP TABLE IF EXISTS permalink;

DROP TABLE IF EXISTS article;

DROP TABLE IF EXISTS userrole;

DROP TABLE IF EXISTS user;

CREATE TABLE user (
   id VARCHAR(45) NOT NULL PRIMARY KEY,
   username VARCHAR(32) NOT NULL UNIQUE,
   userpass VARCHAR(60) NOT NULL,
   useremail VARCHAR(64) NOT NULL,
   active TINYINT(1) NOT NULL DEFAULT 1,
   createdate DATETIME NOT NULL,
   updatedate DATETIME NOT NULL
);

CREATE TABLE userrole (
   id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
   userid VARCHAR(45) NOT NULL,
   rolename VARCHAR(64) NOT NULL,
   createdate DATETIME NOT NULL,
   
   FOREIGN KEY (userid) 
      REFERENCES user(id)  
);

CREATE TABLE article (
   id VARCHAR(45) NOT NULL PRIMARY KEY,
   title VARCHAR(128) NOT NULL,
   keywords VARCHAR(128) NULL,
   category VARCHAR(64) NOT NULL,
   summary VARCHAR(512) NULL,
   content MEDIUMTEXT NULL,
   authorid VARCHAR(45) NOT NULL,
   createdate DATETIME NOT NULL,
   updatedate DATETIME NOT NULL,
   articletype VARCHAR(16) NOT NULL DEFAULT 'post',
   published BIT(1) NOT NULL DEFAULT 0,
   
   FOREIGN KEY (authorid) REFERENCES user(id)
);

CREATE TABLE permalink(
   id VARCHAR(45) NOT NULL PRIMARY KEY,
   path VARCHAR(128) NOT NULL,
   articleid VARCHAR(45) NOT NULL,
   authorid VARCHAR(45) NOT NULL,
   pagereplacement BIT(1) NOT NULL DEFAULT 0,

   FOREIGN KEY (articleid) REFERENCES article(id)
      ON DELETE CASCADE,
   FOREIGN KEY (authorid) REFERENCES user(id)
);

CREATE UNIQUE INDEX permalink_pathidx ON permalink (path);

CREATE TABLE image (
   id VARCHAR(45) NOT NULL PRIMARY KEY,
   title VARCHAR(96) NULL,
   imgname VARCHAR(64) UNIQUE NOT NULL,
   filepath VARCHAR(256) NOT NULL, 
   thumbnailfilepath VARCHAR(256) NOT NULL, 
   uploaddate DATETIME NOT NULL,
   sizex INT NOT NULL,
   sizey INT NOT NULL,
   thumb_sizex INT NOT NULL,
   thumb_sizey INT NOT NULL,

   ownerId VARCHAR(45) NOT NULL,
   
   FOREIGN KEY (ownerId) REFERENCES user(id)
);

INSERT INTO user (
   id, username, userpass,
   useremail, createdate, updatedate, active
) VALUES (
   '5b5d046d-52de-4cae-9566-c2b1d7170783', 'hanbosun',
   '$2a$10$iTkSB2qrj56Aq./sS0lvWe9KHIndp94DnJf5tzxW6eZksu9ZJH6Gy', 'sunhanbo@gmail.com', NOW(), NOW(), 1 
);

INSERT INTO userrole (
   userid, rolename, createdate
) VALUES (
   '5b5d046d-52de-4cae-9566-c2b1d7170783',
   'ROLE_ADMIN', NOW() 
);