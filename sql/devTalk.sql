# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 127.0.0.1 (MySQL 5.6.23)
# Database: devtalk
# Generation Time: 2015-04-09 01:01:04 +0000
# ************************************************************


-- -----------------------------------------------------
-- Schema devtalk
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `devtalk` ;
CREATE SCHEMA `devtalk` DEFAULT CHARACTER SET utf8;
USE `devtalk` ;

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;




# Dump of table users
# ------------------------------------------------------------

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `userID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userName` varchar(25) NOT NULL,
  `userFirstName` varchar(25) DEFAULT NULL,
  `userLastName` varchar(25) DEFAULT NULL,
  `userEmail` varchar(50) DEFAULT NULL,
  `userExtension` bigint(20) DEFAULT NULL,
  `userPassword` varchar(32) NOT NULL DEFAULT 'password',
  `userActive` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE UNIQUE INDEX `userName_UNIQUE` ON `users` (`userName` ASC);
CREATE UNIQUE INDEX `userEmail_UNIQUE` on `users` (`userEmail` ASC);

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;

INSERT INTO `users` (`userID`, `userName`, `userFirstName`, `userLastName`, `userEmail`, `userExtension`, `userPassword`, `userActive`)
VALUES
	(1,'superUser','Super','User',NULL,NULL,'password',1);

/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

# Dump of table permissions
# ------------------------------------------------------------

DROP TABLE IF EXISTS `permissions`;

CREATE TABLE `permissions` (
  `permissionID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `permissionDesc` varchar(50) NOT NULL,
  `permissionCode` varchar(10) NOT NULL,
  PRIMARY KEY (`permissionID`),
  UNIQUE KEY `permissionDesc_UNIQUE` (`permissionDesc`),
  UNIQUE KEY `permissionCode_UNIQUE` (`permissionCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `permissions` WRITE;
/*!40000 ALTER TABLE `permissions` DISABLE KEYS */;

INSERT INTO `permissions` (`permissionID`, `permissionDesc`, `permissionCode`)
VALUES
	(1,'Add Administrators','adminCre8'),
	(2,'Follow Conversations','thrdRead'),
	(4,'Add Permissions','permsCre8'),
	(5,'Edit Permissions','permsUpdt'),
	(6,'Delete Permissions','permsDel'),
	(7,'Add Roles','rolesCre8'),
	(8,'Edit Roles','rolesUpdt'),
	(9,'Delete Roles','rolesDel'),
	(10,'Add Project','projCre8'),
	(11,'Edit Project','projUpdt'),
	(14,'Add Users','userCre8'),
	(15,'Edit Users','userUpdt'),
	(20,'Start Conversations','thrdCre8'),
	(22,'Lock Conversations','thrdLock');

/*!40000 ALTER TABLE `permissions` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table projects
# ------------------------------------------------------------

DROP TABLE IF EXISTS `projects`;

CREATE TABLE `projects` (
  `projectID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `projectDesc` varchar(45) NOT NULL,
  `userID` int(10) unsigned NOT NULL COMMENT 'Project Lead',
  `projectActive` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`projectID`,`userID`),
  UNIQUE KEY `projectDesc_UNIQUE` (`projectDesc`),
  KEY `fk_projects_users1_idx` (`userID`),
  CONSTRAINT `fk_projects_users1` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# Dump of table roles
# ------------------------------------------------------------

DROP TABLE IF EXISTS `roles`;

CREATE TABLE `roles` (
  `roleID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `roleDesc` varchar(50) NOT NULL,
  `roleCode` varchar(10) NOT NULL,
  `subRoles` varchar(25) NOT NULL DEFAULT '0',
  PRIMARY KEY (`roleID`),
  UNIQUE KEY `roleDesc_UNIQUE` (`roleDesc`),
  UNIQUE KEY `roleCode_UNIQUE` (`roleCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


# Dump of table thread
# ------------------------------------------------------------

DROP TABLE IF EXISTS `thread`;

CREATE TABLE `thread` (
  `threadID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `projectID` int(10) unsigned NOT NULL,
  `userID` int(10) unsigned NOT NULL,
  `threadTitle` varchar(45) NOT NULL,
  `threadActive` tinyint(1) NOT NULL DEFAULT '1',
  `threadPublic` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`threadID`,`projectID`, `userID`),
  KEY `fk_thread_projects1_idx` (`projectID`),
  KEY `fk_thread_user1` (`userID`),
  CONSTRAINT `fk_thread_projects1` 
	FOREIGN KEY (`projectID`) 
    REFERENCES `projects` (`projectID`) 
    ON DELETE NO ACTION 
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_thread_user1` 
	FOREIGN KEY (`userID`) 
    REFERENCES `users` (`userID`) 
    ON DELETE NO ACTION 
    ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


# Dump of table posts
# ------------------------------------------------------------

DROP TABLE IF EXISTS `posts`;

CREATE TABLE `posts` (
  `postID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `threadID` int(10) unsigned NOT NULL COMMENT 'Associates all posts in a thread',
  `userID` int(10) unsigned NOT NULL COMMENT 'Creator of post',
  `postContent` varchar(20000) NOT NULL,
  `postTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `active` TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`postID`,`threadID`,`userID`),
  KEY `fk_posts_thread1_idx` (`threadID`),
  KEY `fk_posts_users1` (`userID`),
  CONSTRAINT `fk_posts_thread1` FOREIGN KEY (`threadID`) REFERENCES `thread` (`threadID`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_posts_users1` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;

INSERT INTO `roles` (`roleID`, `roleDesc`, `roleCode`, `subRoles`)
VALUES
	(1,'Super Admin','superAdmin', '1,2,3'),
	(2,'Administrator','admin', '2,3'),
	(3,'User','user','3'),
	(4,'Project Lead','prjctLead','3,4');

/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table pinnedposts
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pinnedposts`;


CREATE TABLE `pinnedposts` (
  `userID` int(10) unsigned NOT NULL,
  `projectID` int(10) unsigned NOT NULL,
  `postContent` varchar(20000) NOT NULL COMMENT 'Posts are physically deleted so we need the actual content',
  PRIMARY KEY (`userID`,`projectID`),
  CONSTRAINT `fk_pinnedposts_users1` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
# Dump of table projectUsers
# ------------------------------------------------------------

DROP TABLE IF EXISTS `projectUsers`;

CREATE TABLE `projectUsers` (
  `projectID` int(10) unsigned NOT NULL,
  `userID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`projectID`,`userID`),
  KEY `fk_projectUsers_users1_idx` (`userID`),
  CONSTRAINT `fk_projectUsers_projects1` FOREIGN KEY (`projectID`) REFERENCES `projects` (`projectID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_projectUsers_users1` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table roleperms
# ------------------------------------------------------------

DROP TABLE IF EXISTS `roleperms`;

CREATE TABLE `roleperms` (
  `roleID` int(10) unsigned NOT NULL,
  `permissionID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`roleID`,`permissionID`),
  KEY `rolePerm2` (`permissionID`),
  CONSTRAINT `rolePerm1` FOREIGN KEY (`roleID`) REFERENCES `roles` (`roleID`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `rolePerm2` FOREIGN KEY (`permissionID`) REFERENCES `permissions` (`permissionID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `roleperms` WRITE;
/*!40000 ALTER TABLE `roleperms` DISABLE KEYS */;

INSERT INTO `roleperms` (`roleID`, `permissionID`)
VALUES
	(1,1),
	(1,4),
	(1,5),
	(1,6),
	(1,7),
	(1,8),
	(1,9),
	(2,10),
	(2,11),
	(2,14),
	(2,15),
        (3,2),
	(3,20),
	(3,22);


/*!40000 ALTER TABLE `roleperms` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table userroles
# ------------------------------------------------------------

DROP TABLE IF EXISTS `userroles`;

CREATE TABLE `userroles` (
  `userID` int(10) unsigned NOT NULL,
  `roleID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`userID`,`roleID`),
  CONSTRAINT `userRole1` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `userRole2` FOREIGN KEY (`roleID`) REFERENCES `roles` (`roleID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `userroles` WRITE;
/*!40000 ALTER TABLE `userroles` DISABLE KEYS */;

INSERT INTO `userroles` (`userID`, `roleID`)
VALUES
	(1,1),
	(1,2),
    (1,3);

/*!40000 ALTER TABLE `userroles` ENABLE KEYS */;
UNLOCK TABLES;


-- -----------------------------------------------------
-- Table `devtalk`.`userThread`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `devtalk`.`userThread` (
  `threadID` INT(10) UNSIGNED NOT NULL,
  `userID` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`threadID`, `userID`),
  INDEX `userThreadFK2_idx` (`userID` ASC),
  CONSTRAINT `userThreadFK1`
    FOREIGN KEY (`threadID`)
    REFERENCES `devtalk`.`thread` (`threadID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `userThreadFK2`
    FOREIGN KEY (`userID`)
    REFERENCES `devtalk`.`users` (`userID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
