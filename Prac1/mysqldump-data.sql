-- MariaDB dump 10.19  Distrib 10.11.2-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: u22561154_bookshopsystem
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
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book` (
  `ISBN` varchar(13) NOT NULL DEFAULT '',
  `book_name` varchar(100) NOT NULL,
  `quality` char(2) NOT NULL,
  `genre` varchar(50) NOT NULL,
  `author` varchar(50) NOT NULL,
  `remaining` int(20) DEFAULT NULL,
  PRIMARY KEY (`ISBN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES
('9780143128571','Romeo and Juliet','C','Tragedy,Romance','William Shakespeare',4),
('9780143128595','Twelfth Night','B','Romance,Comedy,Classic','William Shakespeare',2),
('9780307245304','The Lightning Thief','A','Greek,Fantasy','Rick Riordan',3),
('9780340951453','IT','B','Triller,Horror','Stephan King',1),
('9780415046411','Philosophy of Money','B','Antiques,Collectables','Georg Simmel,David Frisby',2),
('9780786221868','Holes','C','Adventure,Mystery,Fantasy','Louis Sachar',4),
('9780786271535','The Giver','B','Dystopian,Science Fiction,Young Adult 12+','Lois Lowry',2),
('9780804834391','The Art of Shaolin Kung Fu','A','Chinese martial art,Fantasy,Magic Realism','Wong Kiew Kit',2),
('9780807286005','Harry Potter and the Philosophers Stone','B','Fantasy','J.K.Rowling',1),
('9780812969160','Macbeth','C','Tragedy','William Shakespeare',3),
('9780820720110','Annabelle','B','Horror,Fiction','Ruby Jean Jensen',1),
('9781137429292','Poverty in Contemporary Literature','B','Poverty,Lifestyle','Britney Korte',2),
('9781504653657','Dynasty:The Rise and Fall of the House Caesar','B','Roman Fiction','Tom Holland,Dereck Perkins',3),
('9781504786010','The Exorcist','A','Horror','William Peter Blatty',2),
('9781524858148','Floriography: An Illustrated Guide to the Victorian Language of Flowers','A','Nature,Social Science,Craft&Hobbies','Jessics Roux',2),
('9781685540753','Money Heist','A','Literature,Crime,Drama','Vinay Dolase',1),
('9781699365595','The Hate U Give','B','Young Adult Novel,Fiction','Angie Thomas',2),
('9784567414784','Harry Potter and the Chamber of Secrets','A','Fantasy,Adventure','J.K.Rowling',3),
('9784789514530','Frankenstein','A','Horror','Mary Shelley',2),
('9788147941831','Atomic Habits','A','Leisure,Hobby,Lifestyle','James Clear',4);
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `borrow`
--

DROP TABLE IF EXISTS `borrow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `borrow` (
  `student_num` int(11) NOT NULL,
  `ISBN_num` varchar(13) NOT NULL DEFAULT '',
  KEY `fk_student_num` (`student_num`),
  KEY `fk_ISBN_num` (`ISBN_num`),
  CONSTRAINT `fk_ISBN_num` FOREIGN KEY (`ISBN_num`) REFERENCES `book` (`ISBN`),
  CONSTRAINT `fk_student_num` FOREIGN KEY (`student_num`) REFERENCES `student` (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `borrow`
--

LOCK TABLES `borrow` WRITE;
/*!40000 ALTER TABLE `borrow` DISABLE KEYS */;
INSERT INTO `borrow` VALUES
(1,'9780415046411'),
(1,'9784567414784'),
(2,'9780786221868'),
(2,'9781699365595'),
(3,'9780143128571'),
(4,'9780143128595'),
(5,'9780340951453'),
(6,'9780415046411'),
(7,'9780786271535'),
(8,'9780804834391'),
(10,'9780807286005'),
(10,'9780812969160'),
(12,'9780812969160'),
(13,'9780820720110'),
(15,'9781504653657'),
(16,'9781504786010'),
(16,'9781524858148'),
(17,'9781685540753'),
(18,'9784567414784'),
(18,'9788147941831'),
(19,'9788147941831'),
(21,'9788147941831');
/*!40000 ALTER TABLE `borrow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student` (
  `student_id` int(11) NOT NULL AUTO_INCREMENT,
  `last_name` varchar(30) NOT NULL,
  `first_name` varchar(30) NOT NULL,
  `major` varchar(30) NOT NULL,
  `phone_no` varchar(15) NOT NULL,
  PRIMARY KEY (`student_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES
(1,'Hung','Yi-Rou','CS','0823872587'),
(2,'Hung','Shinn-Ru','CS','0607711664'),
(3,'Ismail','Alonzo','Med','0825353953'),
(4,'Chen','Jong','MEng','0824567512'),
(5,'Hui','Jared','Fin','0614237456'),
(6,'Huang','Michelle','Med','0823147915'),
(7,'Hui','Kelly','Fin','0715587432'),
(8,'Govender','Sayuri','IR','0603441576'),
(9,'Persad','Ashmehra','BIS','0829754412'),
(10,'Andrews','Kaitlyn','MEng','0715124435'),
(11,'Smith','Jaden','CS','0619714567'),
(12,'Chou','Jay','Music','0821554390'),
(13,'Zhong','Zelin','CS','0605541278'),
(14,'Creignton','Lloyd','CS','0714297315'),
(15,'Ford','Jessica','Law','0821446832'),
(16,'Voster','Hilton','INF','0714386794'),
(17,'Chen','Ellen','Art','0824494876'),
(18,'Pillay','Lakesha','ChemEng','0601779468'),
(19,'Govender','Darin','Education','0712263478'),
(20,'Hui','Kailan','Law','0820412368'),
(21,'Smith','Jordan','Music','0820741578');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-03-07  9:28:21
