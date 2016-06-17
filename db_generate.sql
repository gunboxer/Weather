CREATE DATABASE  IF NOT EXISTS `weatherdb`;
USE `weatherdb`;


DROP TABLE IF EXISTS `history`;
CREATE TABLE `history` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `UserName` varchar(64) DEFAULT NULL,
  `SendDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Filter` varchar(64) NOT NULL,
  `City` varchar(128) DEFAULT NULL,
  `Temperature` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Id` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;

--
-- Table structure for table `users`
-- `UserName` - имя пользователя.
-- `Password` - MD5 от пароля.
-- `Administrator` - флаг админа.
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `UserName` varchar(32) NOT NULL,
  `Password` varchar(256) NOT NULL,
  `Administrator` tinyint(1) NOT NULL DEFAULT '0',
  UNIQUE KEY `UserName` (`UserName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

-- Два начальных пользователя, пароль "test" у обоих.
INSERT INTO USERS (UserName, Password, Administrator) values
('User', '74657374', '0'),
('Administrator', '74657374', '1');