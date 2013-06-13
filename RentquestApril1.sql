-- MySQL dump 10.13  Distrib 5.1.61, for debian-linux-gnu (i686)
--
-- Host: localhost    Database: Rentquest
-- ------------------------------------------------------
-- Server version	5.1.61-0ubuntu0.11.10.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Accounts`
--

DROP TABLE IF EXISTS `Accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Accounts` (
  `Plot_No` varchar(50) NOT NULL,
  `Total_Income` double(50,2) NOT NULL,
  `Total_Expenses` double(50,2) NOT NULL,
  `Date` date NOT NULL,
  `Account_ID` int(50) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`Account_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Accounts`
--

LOCK TABLES `Accounts` WRITE;
/*!40000 ALTER TABLE `Accounts` DISABLE KEYS */;
/*!40000 ALTER TABLE `Accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Bills`
--

DROP TABLE IF EXISTS `Bills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Bills` (
  `Tenant_ID` int(50) NOT NULL,
  `Fixed_Charge` double(50,2) NOT NULL,
  `Units` double(50,2) NOT NULL,
  `Unit_Charge` double(50,2) NOT NULL,
  `Utility` tinytext NOT NULL,
  `Bill_Payment_Status` tinytext NOT NULL,
  `Date` date NOT NULL,
  `Total_Bill` double(50,2) NOT NULL,
  `Bill_ID` int(50) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`Bill_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Bills`
--

LOCK TABLES `Bills` WRITE;
/*!40000 ALTER TABLE `Bills` DISABLE KEYS */;
/*!40000 ALTER TABLE `Bills` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Expenses`
--

DROP TABLE IF EXISTS `Expenses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Expenses` (
  `Plot_No` varchar(50) NOT NULL,
  `Amount` double(50,2) NOT NULL,
  `Expense_Description` tinytext NOT NULL,
  `Date` date NOT NULL,
  `Expense_ID` int(50) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`Expense_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Expenses`
--

LOCK TABLES `Expenses` WRITE;
/*!40000 ALTER TABLE `Expenses` DISABLE KEYS */;
/*!40000 ALTER TABLE `Expenses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LandLords`
--

DROP TABLE IF EXISTS `LandLords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LandLords` (
  `First_Name` varchar(50) NOT NULL,
  `Last_Name` varchar(50) NOT NULL,
  `National_ID_No` bigint(50) NOT NULL,
  `No_Of_Plots` int(50) NOT NULL,
  `Telephone_No` varchar(50) NOT NULL,
  `Gender` tinytext NOT NULL,
  `LandLord_ID` int(50) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`LandLord_ID`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LandLords`
--

LOCK TABLES `LandLords` WRITE;
/*!40000 ALTER TABLE `LandLords` DISABLE KEYS */;
/*!40000 ALTER TABLE `LandLords` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PayRent`
--

DROP TABLE IF EXISTS `PayRent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PayRent` (
  `Tenant_ID` int(50) NOT NULL,
  `Amount` double(50,2) NOT NULL,
  `Date_Of_Payment` date NOT NULL,
  `Rent_Type` varchar(50) NOT NULL,
  `PayRent_ID` int(50) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`PayRent_ID`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PayRent`
--

LOCK TABLES `PayRent` WRITE;
/*!40000 ALTER TABLE `PayRent` DISABLE KEYS */;
/*!40000 ALTER TABLE `PayRent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PayRentAll`
--

DROP TABLE IF EXISTS `PayRentAll`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PayRentAll` (
  `Tenant_ID` int(50) NOT NULL,
  `Amount` double(50,2) NOT NULL,
  `Date_Of_Payment` date NOT NULL,
  `Rent_Type` varchar(50) NOT NULL,
  `PayRent_ID` int(50) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`PayRent_ID`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PayRentAll`
--

LOCK TABLES `PayRentAll` WRITE;
/*!40000 ALTER TABLE `PayRentAll` DISABLE KEYS */;
/*!40000 ALTER TABLE `PayRentAll` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Plots`
--

DROP TABLE IF EXISTS `Plots`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Plots` (
  `Plot_Name` varchar(50) NOT NULL,
  `Plot_No` varchar(50) NOT NULL,
  `No_Of_Rooms` int(50) NOT NULL,
  `Location` tinytext NOT NULL,
  `LandLord_ID` int(50) NOT NULL,
  PRIMARY KEY (`Plot_No`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Plots`
--

LOCK TABLES `Plots` WRITE;
/*!40000 ALTER TABLE `Plots` DISABLE KEYS */;
/*!40000 ALTER TABLE `Plots` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Rooms`
--

DROP TABLE IF EXISTS `Rooms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Rooms` (
  `Plot_No` varchar(50) NOT NULL,
  `Room_Type` varchar(50) NOT NULL,
  `Room_Status` tinytext NOT NULL,
  `Monthly_Rent` double(50,2) NOT NULL,
  `Room_No` varchar(50) NOT NULL,
  `Room_ID` int(50) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`Room_ID`),
  UNIQUE KEY `no_same_plot_and_room_no` (`Plot_No`,`Room_No`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Rooms`
--

LOCK TABLES `Rooms` WRITE;
/*!40000 ALTER TABLE `Rooms` DISABLE KEYS */;
/*!40000 ALTER TABLE `Rooms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Settings`
--

DROP TABLE IF EXISTS `Settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Settings` (
  `Overdue_Days` int(50) NOT NULL,
  `Bonus_Days` int(50) NOT NULL,
  `Plot_No` varchar(50) NOT NULL,
  PRIMARY KEY (`Plot_No`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Settings`
--

LOCK TABLES `Settings` WRITE;
/*!40000 ALTER TABLE `Settings` DISABLE KEYS */;
/*!40000 ALTER TABLE `Settings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Tenants`
--

DROP TABLE IF EXISTS `Tenants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Tenants` (
  `First_Name` varchar(50) NOT NULL,
  `Last_Name` varchar(50) NOT NULL,
  `National_ID_No` bigint(50) NOT NULL,
  `Room_No` varchar(50) NOT NULL,
  `Plot_No` varchar(50) NOT NULL,
  `Gender` tinytext NOT NULL,
  `Telephone_No` varchar(50) NOT NULL,
  `Tenant_ID` int(50) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`Tenant_ID`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Tenants`
--

LOCK TABLES `Tenants` WRITE;
/*!40000 ALTER TABLE `Tenants` DISABLE KEYS */;
/*!40000 ALTER TABLE `Tenants` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Users`
--

DROP TABLE IF EXISTS `Users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Users` (
  `Username` varchar(50) NOT NULL,
  `Password` varbinary(512) NOT NULL,
  `First_Name` varchar(50) NOT NULL,
  `Last_Name` varchar(50) NOT NULL,
  `National_ID_No` bigint(50) NOT NULL,
  PRIMARY KEY (`Username`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Users`
--

LOCK TABLES `Users` WRITE;
/*!40000 ALTER TABLE `Users` DISABLE KEYS */;
INSERT INTO `Users` VALUES ('Admin','–y8Xìôrg¬“\"Òy[	óèuëS=;•vùš*ó¿‚C\'ø>¸ò9¿Ô‘]áä\\É‡’QŒD!g0ìî\nõµ','Gideon','Kitili',28591268);
/*!40000 ALTER TABLE `Users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Vacation_Notice`
--

DROP TABLE IF EXISTS `Vacation_Notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Vacation_Notice` (
  `Tenant_ID` int(50) NOT NULL,
  `Plot_No` varchar(50) NOT NULL,
  `Expected_Leaving_Date` date NOT NULL,
  PRIMARY KEY (`Tenant_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Vacation_Notice`
--

LOCK TABLES `Vacation_Notice` WRITE;
/*!40000 ALTER TABLE `Vacation_Notice` DISABLE KEYS */;
/*!40000 ALTER TABLE `Vacation_Notice` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-04-01 16:46:28
