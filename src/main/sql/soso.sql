/*
Navicat MySQL Data Transfer

Source Server         : LocalHost
Source Server Version : 50527
Source Host           : localhost:3306
Source Database       : soso

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2019-12-03 23:30:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tab_consume`
-- ----------------------------
DROP TABLE IF EXISTS `tab_consume`;
CREATE TABLE `tab_consume` (
  `cardNumber` char(11) NOT NULL,
  `type` char(11) NOT NULL,
  `consumData` double(11,0) NOT NULL,
  KEY `cardNumber` (`cardNumber`),
  CONSTRAINT `cardNumber` FOREIGN KEY (`cardNumber`) REFERENCES `tab_user` (`cardNumber`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tab_consume
-- ----------------------------

-- ----------------------------
-- Table structure for `tab_user`
-- ----------------------------
DROP TABLE IF EXISTS `tab_user`;
CREATE TABLE `tab_user` (
  `cardNumber` char(11) NOT NULL,
  `userName` char(15) NOT NULL,
  `passWord` char(50) NOT NULL,
  `money` double(11,0) NOT NULL,
  `serPackage` int(11) NOT NULL,
  `realTalkTime` int(11) DEFAULT NULL,
  `realSMSCount` int(11) DEFAULT NULL,
  `realFlow` int(11) DEFAULT NULL,
  `consumAmount` double(11,0) NOT NULL,
  PRIMARY KEY (`cardNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tab_user
-- ----------------------------
