CREATE DATABASE IF NOT EXISTS `big_city_library`
/*!40100 DEFAULT CHARACTER SET utf8 */
;
USE `big_city_library`;
-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: big_city_library
-- ------------------------------------------------------
-- Server version	8.0.15
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */
;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */
;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */
;
/*!50503 SET NAMES utf8 */
;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */
;
/*!40103 SET TIME_ZONE='+00:00' */
;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */
;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */
;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */
;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */
;
--
-- Table structure for table `author`
--
DROP TABLE IF EXISTS `author`;
/*!40101 SET @saved_cs_client     = @@character_set_client */
;
/*!50503 SET character_set_client = utf8mb4 */
;
CREATE TABLE `author` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 7 DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */
;
--
-- Dumping data for table `author`
--
LOCK TABLES `author` WRITE;
/*!40000 ALTER TABLE `author` DISABLE KEYS */
;
INSERT INTO `author`
VALUES (1, 'Vil Coyote'),
  (2, 'Sun Tzu'),
  (3, 'Nicolas Machiavel'),
  (4, 'Stephen King'),
  (5, 'Émile Erckmann'),
  (6, 'Alexandre Chatrian');
/*!40000 ALTER TABLE `author` ENABLE KEYS */
;
UNLOCK TABLES;
--
-- Table structure for table `book`
--
DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */
;
/*!50503 SET character_set_client = utf8mb4 */
;
CREATE TABLE `book` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `publication_date` date NOT NULL,
  `title` varchar(255) NOT NULL,
  `publisher_id` int(11) NOT NULL,
  `img_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgtvt7p649s4x80y6f4842pnfq` (`publisher_id`),
  CONSTRAINT `FKgtvt7p649s4x80y6f4842pnfq` FOREIGN KEY (`publisher_id`) REFERENCES `publisher` (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 6 DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */
;
--
-- Dumping data for table `book`
--
LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */
;
INSERT INTO `book`
VALUES (
    1,
    '2022-01-06',
    'Coyote céleste',
    1,
    'https://cdn.shopify.com/s/files/1/0517/5915/3314/products/0600571.jpg?v=1615541959'
  ),
  (
    2,
    '2017-04-05',
    'L’Art de la guerre',
    2,
    'https://static.fnac-static.com/multimedia/Images/FR/NR/f7/9a/7f/8362743/1507-1/tsp20211006072002/L-Art-de-la-guerre.jpg'
  ),
  (
    3,
    '2015-06-20',
    'Le Prince',
    3,
    'https://m.media-amazon.com/images/I/41IvgOw65SL._SL500_.jpg'
  ),
  (
    4,
    '2009-08-15',
    'Ca',
    5,
    'https://images.noosfere.org/couv/a/am03453-1988.jpg'
  ),
  (
    5,
    '1864-01-01',
    'L''Ami Fritz',
    4,
    'https://upload.wikimedia.org/wikipedia/commons/thumb/b/bf/Malassis_illustration_de_L_Ami_Fritz_couverture.jpeg/800px-Malassis_illustration_de_L_Ami_Fritz_couverture.jpeg'
  );
/*!40000 ALTER TABLE `book` ENABLE KEYS */
;
UNLOCK TABLES;
--
-- Table structure for table `book_author`
--
DROP TABLE IF EXISTS `book_author`;
/*!40101 SET @saved_cs_client     = @@character_set_client */
;
/*!50503 SET character_set_client = utf8mb4 */
;
CREATE TABLE `book_author` (
  `book_id` int(11) NOT NULL,
  `author_id` int(11) NOT NULL,
  PRIMARY KEY (`book_id`, `author_id`),
  KEY `FKbjqhp85wjv8vpr0beygh6jsgo` (`author_id`),
  CONSTRAINT `FKbjqhp85wjv8vpr0beygh6jsgo` FOREIGN KEY (`author_id`) REFERENCES `author` (`id`),
  CONSTRAINT `FKhwgu59n9o80xv75plf9ggj7xn` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */
;
--
-- Dumping data for table `book_author`
--
LOCK TABLES `book_author` WRITE;
/*!40000 ALTER TABLE `book_author` DISABLE KEYS */
;
INSERT INTO `book_author`
VALUES (1, 1),
  (2, 2),
  (3, 3),
  (4, 4),
  (5, 5),
  (5, 6);
/*!40000 ALTER TABLE `book_author` ENABLE KEYS */
;
UNLOCK TABLES;
--
-- Table structure for table `exemplary`
--
DROP TABLE IF EXISTS `exemplary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */
;
/*!50503 SET character_set_client = utf8mb4 */
;
CREATE TABLE `exemplary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `book_id` int(11) NOT NULL,
  `library_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1gta4pxe6qu1yq18atulr5o89` (`book_id`),
  KEY `FKr7evgj33r4iwtm3e1j1ysyyhl` (`library_id`),
  CONSTRAINT `FK1gta4pxe6qu1yq18atulr5o89` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`),
  CONSTRAINT `FKr7evgj33r4iwtm3e1j1ysyyhl` FOREIGN KEY (`library_id`) REFERENCES `library` (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 9 DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */
;
--
-- Dumping data for table `exemplary`
--
LOCK TABLES `exemplary` WRITE;
/*!40000 ALTER TABLE `exemplary` DISABLE KEYS */
;
INSERT INTO `exemplary`
VALUES (1, 1, 1),
  (2, 2, 2),
  (3, 3, 2),
  (4, 4, 1),
  (5, 2, 3),
  (6, 3, 3),
  (7, 3, 1),
  (8, 5, 1);
/*!40000 ALTER TABLE `exemplary` ENABLE KEYS */
;
UNLOCK TABLES;
--
-- Table structure for table `library`
--
DROP TABLE IF EXISTS `library`;
/*!40101 SET @saved_cs_client     = @@character_set_client */
;
/*!50503 SET character_set_client = utf8mb4 */
;
CREATE TABLE `library` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `phone` char(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 4 DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */
;
--
-- Dumping data for table `library`
--
LOCK TABLES `library` WRITE;
/*!40000 ALTER TABLE `library` DISABLE KEYS */
;
INSERT INTO `library`
VALUES (
    1,
    '42 rue de la Réponse',
    'Bibliothèque du centre-ville',
    '0611001100'
  ),
  (
    2,
    '19 avenue des Pages',
    'Bibliothèque du parc',
    '0622002200'
  ),
  (
    3,
    '7 impasse Victor Hugo',
    'Bibliothèque du musée',
    '0633003300'
  );
/*!40000 ALTER TABLE `library` ENABLE KEYS */
;
UNLOCK TABLES;
--
-- Table structure for table `loan`
--
DROP TABLE IF EXISTS `loan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */
;
/*!50503 SET character_set_client = utf8mb4 */
;
CREATE TABLE `loan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `extend` tinyint(1) NOT NULL DEFAULT '0',
  `loan_date` date NOT NULL,
  `return_date` date DEFAULT NULL,
  `exemplary_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnn8sxhac9hhlfysyxj8790o69` (`exemplary_id`),
  KEY `FKxxm1yc1xty3qn1pthgj8ac4f` (`user_id`),
  CONSTRAINT `FKnn8sxhac9hhlfysyxj8790o69` FOREIGN KEY (`exemplary_id`) REFERENCES `exemplary` (`id`),
  CONSTRAINT `FKxxm1yc1xty3qn1pthgj8ac4f` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 14 DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */
;
--
-- Dumping data for table `loan`
--
LOCK TABLES `loan` WRITE;
/*!40000 ALTER TABLE `loan` DISABLE KEYS */
;
INSERT INTO `loan`
VALUES (1, 1, '2022-01-15', '2022-03-14', 1, 3),
  (2, 1, '2022-05-15', NULL, 2, 3),
  (3, 0, '2022-03-15', '2022-03-28', 4, 3),
  (4, 0, '2022-05-15', NULL, 1, 4),
  (8, 0, '2022-03-15', '2022-03-28', 5, 4),
  (9, 0, '2022-03-28', '2022-03-28', 4, 4),
  (11, 1, '2022-06-01', NULL, 8, 3),
  (13, 0, '2022-07-01', '2022-07-15', 4, 3);
/*!40000 ALTER TABLE `loan` ENABLE KEYS */
;
UNLOCK TABLES;
--
-- Table structure for table `publisher`
--
DROP TABLE IF EXISTS `publisher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */
;
/*!50503 SET character_set_client = utf8mb4 */
;
CREATE TABLE `publisher` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 7 DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */
;
--
-- Dumping data for table `publisher`
--
LOCK TABLES `publisher` WRITE;
/*!40000 ALTER TABLE `publisher` DISABLE KEYS */
;
INSERT INTO `publisher`
VALUES (1, 'Coyote Corp.'),
  (2, 'Hachette'),
  (3, 'Gallimard'),
  (4, 'J''ai Lu'),
  (5, 'Albin Michel');
/*!40000 ALTER TABLE `publisher` ENABLE KEYS */
;
UNLOCK TABLES;
--
-- Table structure for table `user`
--
DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */
;
/*!50503 SET character_set_client = utf8mb4 */
;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(90) NOT NULL,
  `password` char(60) NOT NULL,
  `pseudo` varchar(45) NOT NULL,
  `role` varchar(20) NOT NULL DEFAULT 'USER',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`),
  UNIQUE KEY `UK_it5d8tethuijmhllwd27doaqv` (`pseudo`)
) ENGINE = InnoDB AUTO_INCREMENT = 7 DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */
;
--
-- Dumping data for table `user`
--
LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */
;
INSERT INTO `user`
VALUES (
    1,
    'Coyote-BCL@yopmail.com',
    '$2y$10$1FDqiU6gba1fSUbAkLnnh.bIizVcUWuNU3bBNV4WDGCyaRZuAn.ky',
    'Coyote',
    'ADMIN'
  ),
  (
    2,
    'Batch-BCL@yopmail.com',
    '$2y$10$sB56dGiwqwZux3gJLCyJQuCsPUCntBgoJ1VrUieS6w74/J9WBw4sy',
    'Batch',
    'BATCH'
  ),
  (
    3,
    'Anne-BCL@yopmail.fr',
    '$2y$10$TFpcZX.t8kVZTL942KI/pOlCnSAoeIq1Kw75aXQLk7sS.TujUyxda',
    'Anne',
    'USER'
  ),
  (
    4,
    'Bruno-BCL@yopmail.fr',
    '$2y$10$IG8UOLRzoUdlYJkR.otpLe/hcZie2Ptf7oJ.0SmHKa6wM6b6rrjbe',
    'Bruno',
    'USER'
  ),
  (
    5,
    'Charles-BCL@yomail.fr',
    '$2y$10$NeWjPaMXA5WEYOovsZicQ.W6GIolQD.wIFGrZpwZ6bXuCdPvRVH5i',
    'Charles',
    'USER'
  ),
  (
    6,
    'Anich-BCL@yopmail.com',
    '$2y$10$kB03q8UgB13KHa3pNfH0WuTwBcMOTgU9nNtntrOKYb7HBkK6YG4.K',
    'Anich',
    'EMPLOYEE'
  );
/*!40000 ALTER TABLE `user` ENABLE KEYS */
;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */
;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */
;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */
;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */
;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */
;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */
;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */
;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */
;
-- Dump completed on 2023-07-12 19:43:39
