-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: big_city_library
-- ------------------------------------------------------
-- Server version	8.0.15
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;

/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;

/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;

/*!50503 SET NAMES utf8 */;

/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;

/*!40103 SET TIME_ZONE='+00:00' */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;

/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `author`
--
DROP TABLE IF EXISTS `author`;

/*!40101 SET @saved_cs_client     = @@character_set_client */;

/*!50503 SET character_set_client = utf8mb4 */;

CREATE TABLE
  `author` (
    `id` int (11) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
  ) ENGINE = InnoDB AUTO_INCREMENT = 7 DEFAULT CHARSET = utf8;

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `book`
--
DROP TABLE IF EXISTS `book`;

/*!40101 SET @saved_cs_client     = @@character_set_client */;

/*!50503 SET character_set_client = utf8mb4 */;

CREATE TABLE
  `book` (
    `id` int (11) NOT NULL AUTO_INCREMENT,
    `publication_date` date NOT NULL,
    `title` varchar(255) NOT NULL,
    `publisher_id` int (11) NOT NULL,
    `img_url` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKgtvt7p649s4x80y6f4842pnfq` (`publisher_id`),
    CONSTRAINT `FKgtvt7p649s4x80y6f4842pnfq` FOREIGN KEY (`publisher_id`) REFERENCES `publisher` (`id`)
  ) ENGINE = InnoDB AUTO_INCREMENT = 6 DEFAULT CHARSET = utf8;

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `book_author`
--
DROP TABLE IF EXISTS `book_author`;

/*!40101 SET @saved_cs_client     = @@character_set_client */;

/*!50503 SET character_set_client = utf8mb4 */;

CREATE TABLE
  `book_author` (
    `book_id` int (11) NOT NULL,
    `author_id` int (11) NOT NULL,
    PRIMARY KEY (`book_id`, `author_id`),
    KEY `FKbjqhp85wjv8vpr0beygh6jsgo` (`author_id`),
    CONSTRAINT `FKbjqhp85wjv8vpr0beygh6jsgo` FOREIGN KEY (`author_id`) REFERENCES `author` (`id`),
    CONSTRAINT `FKhwgu59n9o80xv75plf9ggj7xn` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`)
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8;

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `exemplary`
--
DROP TABLE IF EXISTS `exemplary`;

/*!40101 SET @saved_cs_client     = @@character_set_client */;

/*!50503 SET character_set_client = utf8mb4 */;

CREATE TABLE
  `exemplary` (
    `id` int (11) NOT NULL AUTO_INCREMENT,
    `book_id` int (11) NOT NULL,
    `library_id` int (11) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FK1gta4pxe6qu1yq18atulr5o89` (`book_id`),
    KEY `FKr7evgj33r4iwtm3e1j1ysyyhl` (`library_id`),
    CONSTRAINT `FK1gta4pxe6qu1yq18atulr5o89` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`),
    CONSTRAINT `FKr7evgj33r4iwtm3e1j1ysyyhl` FOREIGN KEY (`library_id`) REFERENCES `library` (`id`)
  ) ENGINE = InnoDB AUTO_INCREMENT = 9 DEFAULT CHARSET = utf8;

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `library`
--
DROP TABLE IF EXISTS `library`;

/*!40101 SET @saved_cs_client     = @@character_set_client */;

/*!50503 SET character_set_client = utf8mb4 */;

CREATE TABLE
  `library` (
    `id` int (11) NOT NULL AUTO_INCREMENT,
    `address` varchar(255) NOT NULL,
    `name` varchar(255) NOT NULL,
    `phone` char(10) NOT NULL,
    PRIMARY KEY (`id`)
  ) ENGINE = InnoDB AUTO_INCREMENT = 4 DEFAULT CHARSET = utf8;

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `loan`
--
DROP TABLE IF EXISTS `loan`;

/*!40101 SET @saved_cs_client     = @@character_set_client */;

/*!50503 SET character_set_client = utf8mb4 */;

CREATE TABLE
  `loan` (
    `id` int (11) NOT NULL AUTO_INCREMENT,
    `extend` tinyint (1) NOT NULL DEFAULT '0',
    `loan_date` date NOT NULL,
    `return_date` date DEFAULT NULL,
    `exemplary_id` int (11) NOT NULL,
    `user_id` int (11) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FKnn8sxhac9hhlfysyxj8790o69` (`exemplary_id`),
    KEY `FKxxm1yc1xty3qn1pthgj8ac4f` (`user_id`),
    CONSTRAINT `FKnn8sxhac9hhlfysyxj8790o69` FOREIGN KEY (`exemplary_id`) REFERENCES `exemplary` (`id`),
    CONSTRAINT `FKxxm1yc1xty3qn1pthgj8ac4f` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
  ) ENGINE = InnoDB AUTO_INCREMENT = 31 DEFAULT CHARSET = utf8;

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `publisher`
--
DROP TABLE IF EXISTS `publisher`;

/*!40101 SET @saved_cs_client     = @@character_set_client */;

/*!50503 SET character_set_client = utf8mb4 */;

CREATE TABLE
  `publisher` (
    `id` int (11) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
  ) ENGINE = InnoDB AUTO_INCREMENT = 7 DEFAULT CHARSET = utf8;

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--
DROP TABLE IF EXISTS `user`;

/*!40101 SET @saved_cs_client     = @@character_set_client */;

/*!50503 SET character_set_client = utf8mb4 */;

CREATE TABLE
  `user` (
    `id` int (11) NOT NULL AUTO_INCREMENT,
    `email` varchar(90) NOT NULL,
    `password` char(60) NOT NULL,
    `pseudo` varchar(45) NOT NULL,
    `role` varchar(20) NOT NULL DEFAULT 'USER',
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`),
    UNIQUE KEY `UK_it5d8tethuijmhllwd27doaqv` (`pseudo`)
  ) ENGINE = InnoDB AUTO_INCREMENT = 7 DEFAULT CHARSET = utf8;

/*!40101 SET character_set_client = @saved_cs_client */;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;

/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;

/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-18 10:44:46
