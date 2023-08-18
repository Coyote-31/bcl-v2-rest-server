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
-- Table structure for table `reservation`
--
DROP TABLE IF EXISTS `reservation`;

/*!40101 SET @saved_cs_client     = @@character_set_client */;

/*!50503 SET character_set_client = utf8mb4 */;

CREATE TABLE
  `reservation` (
    `book_id` int (11) NOT NULL,
    `user_id` int (11) NOT NULL,
    `created_at` timestamp(3) NOT NULL,
    `notified_at` timestamp(3) NULL DEFAULT NULL,
    `exemplary_id` int (11) DEFAULT NULL,
    PRIMARY KEY (`book_id`, `user_id`),
    UNIQUE KEY `exemplary_id_UNIQUE` (`exemplary_id`),
    KEY `reservation_user_id_FK_idx` (`user_id`),
    KEY `reservation_book_id_FK_idx` (`book_id`),
    CONSTRAINT `reservation_book_id_FK` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `reservation_exemplary_id_FK` FOREIGN KEY (`exemplary_id`) REFERENCES `exemplary` (`id`),
    CONSTRAINT `reservation_user_id_FK` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8 COLLATE = utf8_bin;

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation`
--
LOCK TABLES `reservation` WRITE;

/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;

INSERT INTO
  `reservation`
VALUES
  (1, 3, '2023-07-27 16:19:48.376', NULL, NULL),
  (3, 3, '2023-08-18 08:05:35.969', NULL, NULL),
  (
    3,
    4,
    '2023-07-16 12:31:53.666',
    '2023-08-17 07:23:11.883',
    7
  );

/*!40000 ALTER TABLE `reservation` ENABLE KEYS */;

UNLOCK TABLES;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;

/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;

/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-18 10:42:30
