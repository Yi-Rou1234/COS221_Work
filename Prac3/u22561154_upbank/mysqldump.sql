-- MariaDB dump 10.19  Distrib 10.11.2-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: u22561154_upbank
-- ------------------------------------------------------
-- Server version	10.11.2-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `Aunique_num` varchar(20) NOT NULL,
  `account_type` varchar(50) NOT NULL,
  PRIMARY KEY (`Aunique_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES
('0078781382','Forex'),
('1357924680','Savings'),
('2468013579','Cheque');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `atm`
--

DROP TABLE IF EXISTS `atm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `atm` (
  `Aatm_code` varchar(20) NOT NULL,
  `Abranch_code` varchar(50) DEFAULT NULL,
  `Cash_available` double DEFAULT NULL,
  `Adate` date DEFAULT NULL,
  `Atime` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`Aatm_code`),
  KEY `Abranch_code` (`Abranch_code`),
  CONSTRAINT `atm_ibfk_1` FOREIGN KEY (`Abranch_code`) REFERENCES `branch` (`Bbranch_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `atm`
--

LOCK TABLES `atm` WRITE;
/*!40000 ALTER TABLE `atm` DISABLE KEYS */;
INSERT INTO `atm` VALUES
('111','7988',1000000,'2023-04-03','7:30'),
('112','5621',1500100,'2023-03-30','17:30'),
('113','7988',800000,'2023-03-03','8:00'),
('114','5621',1000000,'2023-02-27','9:00'),
('115','5621',250000,'2023-02-27','07:30'),
('116','1379',1450000,'2023-04-04','10;00');
/*!40000 ALTER TABLE `atm` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `branch`
--

DROP TABLE IF EXISTS `branch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `branch` (
  `Bbranch_code` varchar(50) NOT NULL,
  `Bnumber` int(10) NOT NULL,
  `Bstreet` varchar(100) NOT NULL,
  `Bcity` varchar(50) NOT NULL,
  PRIMARY KEY (`Bbranch_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branch`
--

LOCK TABLES `branch` WRITE;
/*!40000 ALTER TABLE `branch` DISABLE KEYS */;
INSERT INTO `branch` VALUES
('1379',3241,'Kingsberg Avenue','Hillcrest'),
('2467',0,'','Randburg'),
('5621',21,'Duxbury Road','Hatfield'),
('7988',1324,'Maybell Street','Groenkloof');
/*!40000 ALTER TABLE `branch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cheque`
--

DROP TABLE IF EXISTS `cheque`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cheque` (
  `unique_num` varchar(20) NOT NULL,
  `Cclient_num` varchar(20) DEFAULT NULL,
  KEY `fk_unique_num` (`unique_num`),
  KEY `Cheque_num` (`Cclient_num`),
  CONSTRAINT `cheque_ibfk_1` FOREIGN KEY (`Cclient_num`) REFERENCES `client` (`Cunique_num`),
  CONSTRAINT `fk_unique_num` FOREIGN KEY (`unique_num`) REFERENCES `account` (`Aunique_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cheque`
--

LOCK TABLES `cheque` WRITE;
/*!40000 ALTER TABLE `cheque` DISABLE KEYS */;
INSERT INTO `cheque` VALUES
('2468013579','0284923649'),
('2468013579','0938495831'),
('2468013579','9087657767');
/*!40000 ALTER TABLE `cheque` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cit`
--

DROP TABLE IF EXISTS `cit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cit` (
  `Catm_code` varchar(20) NOT NULL,
  `Cstart` date DEFAULT NULL,
  `Cend` date DEFAULT NULL,
  PRIMARY KEY (`Catm_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cit`
--

LOCK TABLES `cit` WRITE;
/*!40000 ALTER TABLE `cit` DISABLE KEYS */;
INSERT INTO `cit` VALUES
('10','2022-09-01','2023-09-01'),
('11','2023-01-01','2024-01-01'),
('12','2023-02-27','2024-02-27'),
('7','2022-01-31','2023-01-31'),
('8','2022-02-02','2023-02-02'),
('9','2022-07-31','2023-07-31');
/*!40000 ALTER TABLE `cit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `client` (
  `Cunique_num` varchar(20) NOT NULL,
  `Cbranch_code` varchar(50) NOT NULL,
  `Address_proof` tinyint(1) NOT NULL,
  `F_name` varchar(50) NOT NULL,
  `M_name` varchar(50) NOT NULL,
  `L_name` varchar(50) NOT NULL,
  `sex` char(1) NOT NULL,
  `Ccontact_detail` varchar(100) NOT NULL,
  `Cnumber` int(10) NOT NULL,
  `Cstreet` varchar(100) NOT NULL,
  `Ccity` varchar(50) NOT NULL,
  `Birth_date` date NOT NULL,
  PRIMARY KEY (`Cunique_num`),
  KEY `fk_Cbranch_code` (`Cbranch_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES
('0284923649','1379',0,'Yi-Rou','Monica','Hung','F','0821342233',111,'Josque Road','Pretoria','2003-07-13'),
('0938495831','7988',1,'Shinn-ru','Austin','Hung','M','0607711338',111,'Josque Road','Pretoria','2004-02-12'),
('0987654321','1379',0,'Alonzo','Joshua','Ismail','M','0829393893',278,'Penguin Street','Centurion','2003-06-28'),
('1234567890','2467',0,'Maria','Jodie','James','F','0834657893',213,'Jean Avenue','Centurion','1999-12-30'),
('1385968473','5621',0,'John','Sophie','Smith','M','0823462876',33,'Prospect Street','Hatfield','2000-01-11'),
('2143658709','2467',1,'Yuting','Flower','Lei','F','0823334756',100,'Duxbury Avenue','Hatfield','2002-05-23'),
('2938475886','1379',0,'Po-Chuan','Ellen','Hung','F','0823311234',4,'Duncan Avenue','Boksburg','1978-10-21'),
('9087657767','7988',1,'Kylie','Christian','Jenner','F','0712834588',23,'Rose Street','Johannesburg','2000-12-21');
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact`
--

DROP TABLE IF EXISTS `contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact` (
  `CTbranch_code` varchar(50) NOT NULL,
  `contact` varchar(100) NOT NULL,
  PRIMARY KEY (`contact`),
  KEY `fk_CTbranch_code` (`CTbranch_code`),
  CONSTRAINT `fk_CTbranch_code` FOREIGN KEY (`CTbranch_code`) REFERENCES `branch` (`Bbranch_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact`
--

LOCK TABLES `contact` WRITE;
/*!40000 ALTER TABLE `contact` DISABLE KEYS */;
INSERT INTO `contact` VALUES
('1379','0601232211'),
('1379','0723674876'),
('2467','0123221433'),
('2467','0829087763'),
('5621','0123321232'),
('7988','0129098811'),
('7988','0612319900');
/*!40000 ALTER TABLE `contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `forex`
--

DROP TABLE IF EXISTS `forex`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `forex` (
  `Forex_code` varchar(30) NOT NULL,
  `Rate_of_exchange` double NOT NULL,
  `Currency_type` varchar(50) NOT NULL,
  `Funique_num` varchar(20) NOT NULL,
  `Fclient_num` varchar(20) DEFAULT NULL,
  KEY `fk_Funique_num` (`Funique_num`),
  KEY `Forex_num` (`Fclient_num`),
  CONSTRAINT `fk_Funique_num` FOREIGN KEY (`Funique_num`) REFERENCES `account` (`Aunique_num`),
  CONSTRAINT `forex_ibfk_1` FOREIGN KEY (`Fclient_num`) REFERENCES `client` (`Cunique_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forex`
--

LOCK TABLES `forex` WRITE;
/*!40000 ALTER TABLE `forex` DISABLE KEYS */;
INSERT INTO `forex` VALUES
('1345',17.82,'USD','0078781382','0987654321'),
('1346',17.82,'USD','0078781382','1385968473'),
('1357',2.59,'Chinese Yuan','0078781382','0284923649'),
('1358',19.41,'Euro','0078781382','2938475886');
/*!40000 ALTER TABLE `forex` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refills`
--

DROP TABLE IF EXISTS `refills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `refills` (
  `Ratm_code` varchar(20) NOT NULL,
  `RDatm_code` varchar(20) NOT NULL,
  KEY `Ratm_code` (`Ratm_code`),
  KEY `RDatm_code` (`RDatm_code`),
  CONSTRAINT `refills_ibfk_1` FOREIGN KEY (`Ratm_code`) REFERENCES `atm` (`Aatm_code`),
  CONSTRAINT `refills_ibfk_2` FOREIGN KEY (`RDatm_code`) REFERENCES `cit` (`Catm_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refills`
--

LOCK TABLES `refills` WRITE;
/*!40000 ALTER TABLE `refills` DISABLE KEYS */;
INSERT INTO `refills` VALUES
('115','7'),
('115','8'),
('112','9'),
('114','10'),
('113','11'),
('116','12'),
('111','11');
/*!40000 ALTER TABLE `refills` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `savings`
--

DROP TABLE IF EXISTS `savings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `savings` (
  `Sunique_num` varchar(50) NOT NULL,
  `Sclient_num` varchar(200) DEFAULT NULL,
  KEY `fk_Sunique_num` (`Sunique_num`),
  KEY `Saving_num` (`Sclient_num`),
  CONSTRAINT `fk_Sunique_num` FOREIGN KEY (`Sunique_num`) REFERENCES `account` (`Aunique_num`),
  CONSTRAINT `savings_ibfk_1` FOREIGN KEY (`Sclient_num`) REFERENCES `client` (`Cunique_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `savings`
--

LOCK TABLES `savings` WRITE;
/*!40000 ALTER TABLE `savings` DISABLE KEYS */;
INSERT INTO `savings` VALUES
('1357924680','0987654321'),
('1357924680','1385968473'),
('1357924680','2938475886'),
('1357924680','9087657767');
/*!40000 ALTER TABLE `savings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trading_hour`
--

DROP TABLE IF EXISTS `trading_hour`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trading_hour` (
  `Tbranch_code` varchar(50) NOT NULL,
  `duration` varchar(50) NOT NULL DEFAULT '',
  `day` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`duration`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trading_hour`
--

LOCK TABLES `trading_hour` WRITE;
/*!40000 ALTER TABLE `trading_hour` DISABLE KEYS */;
INSERT INTO `trading_hour` VALUES
('5621','08:30~17:00','Mon~Fri'),
('2467','09:00~16:30','Mon~Fri'),
('1379','09:00~17:00','Mon~Fri'),
('7988','09:00~17:30','Mon~Fri');
/*!40000 ALTER TABLE `trading_hour` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `work_for`
--

DROP TABLE IF EXISTS `work_for`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `work_for` (
  `Wbranch_code` varchar(50) NOT NULL,
  `Watm_code` varchar(20) NOT NULL,
  KEY `Wbranch_code` (`Wbranch_code`),
  KEY `Watm_code` (`Watm_code`),
  CONSTRAINT `work_for_ibfk_1` FOREIGN KEY (`Wbranch_code`) REFERENCES `branch` (`Bbranch_code`),
  CONSTRAINT `work_for_ibfk_2` FOREIGN KEY (`Watm_code`) REFERENCES `atm` (`Aatm_code`),
  CONSTRAINT `work_for_ibfk_3` FOREIGN KEY (`Watm_code`) REFERENCES `cit` (`Catm_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `work_for`
--

LOCK TABLES `work_for` WRITE;
/*!40000 ALTER TABLE `work_for` DISABLE KEYS */;
INSERT INTO `work_for` VALUES
('2467','7'),
('2467','8'),
('5621','9'),
('5621','10'),
('7988','11'),
('1379','12');
/*!40000 ALTER TABLE `work_for` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-04 22:14:49
