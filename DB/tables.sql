use OldBlogEngine;

DROP TABLE IF EXISTS imagetogallery;

DROP TABLE IF EXISTS gallery;

DROP TABLE IF EXISTS image;

DROP TABLE IF EXISTS articleicon;

DROP TABLE IF EXISTS fileresource;

DROP TABLE IF EXISTS textresource;

DROP TABLE IF EXISTS resource;

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
   summary VARCHAR(2048) NULL,
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

CREATE TABLE resource(
   id VARCHAR(45) NOT NULL PRIMARY KEY,
   name VARCHAR(96) NOT NULL,
   restype VARCHAR(24) NOT NULL,
   createdate DATETIME NOT NULL,
   updatedate DATETIME NOT NULL,
   ownerid VARCHAR(45) NOT NULL,

   FOREIGN KEY (ownerid) REFERENCES user(id)
);

CREATE TABLE textresource(
   id VARCHAR(45) NOT NULL PRIMARY KEY,
   subtype VARCHAR(16) NOT NULL,
   value TEXT NOT NULL,
   length INT NOT NULL DEFAULT 0
);

CREATE TABLE fileresource(
   id VARCHAR(45) NOT NULL PRIMARY KEY,
   subtype VARCHAR(16) NOT NULL,
   filename VARCHAR(256) NOT NULL,
   imgwidth INT NULL,
   imgheight INT NULL
);

CREATE TABLE articleicon (
   id VARCHAR(45) NOT NULL PRIMARY KEY,
   articleid VARCHAR(45) NOT NULL,
   resourceid VARCHAR(45) NOT NULL,
   
   FOREIGN KEY (articleid) REFERENCES article(id),
   FOREIGN KEY (resourceid) REFERENCES resource(id)
);

CREATE TABLE image (
   id VARCHAR(45) NOT NULL PRIMARY KEY,
   title VARCHAR(96) NULL,
   keywords VARCHAR(128) NULL, 
   imgname VARCHAR(64) UNIQUE NOT NULL,
   filepath VARCHAR(256) NOT NULL, 
   thumbnailfilepath VARCHAR(256) NOT NULL,
   active BIT(1) NOT NULL DEFAULT 1,
   uploaddate DATETIME NOT NULL,
   sizex INT NOT NULL,
   sizey INT NOT NULL,
   thumb_sizex INT NOT NULL,
   thumb_sizey INT NOT NULL,
   snapshot_sizex INT NULL,
   snapshot_sizey INT NULL,   

   ownerId VARCHAR(45) NOT NULL,
   
   FOREIGN KEY (ownerId) REFERENCES user(id)
);

CREATE TABLE gallery (
   id VARCHAR(45) NOT NULL PRIMARY KEY,
   title VARCHAR(96) NOT NULL,
   description VARCHAR(3072) NULL,
   active BIT(1) NOT NULL DEFAULT 1,
   visible BIT(1) NOT NULL DEFAULT 1,
   keywords VARCHAR(128) NULL,
   createdate DATETIME NOT NULL,

   ownerId VARCHAR(45) NOT NULL,
   FOREIGN KEY (ownerId) REFERENCES user(id)
);

CREATE TABLE imagetogallery (
   id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   galleryid VARCHAR(45) NOT NULL,
   imageid VARCHAR(45) NOT NULL,   

   FOREIGN KEY (galleryid) REFERENCES gallery(id)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
   FOREIGN KEY (imageid) REFERENCES image(id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
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