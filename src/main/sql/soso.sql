/*
Navicat MySQL Data Transfer

Source Server         : LocalHost
Source Server Version : 50527
Source Host           : localhost:3306
Source Database       : soso

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2019-12-03 20:59:54
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
INSERT INTO `tab_consume` VALUES ('13947298967', '上网', '3072');
INSERT INTO `tab_consume` VALUES ('13947298967', '通话', '50');

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
INSERT INTO `tab_user` VALUES ('13901069759', 'zhangsan', 'ba9b95cf46f4264620d3bfa39a5f6278', '122', '2', '0', '0', '0', '78');
INSERT INTO `tab_user` VALUES ('13904894098', '1', '0c761d513096010eb1eef6ebbc255002', '33', '2', '0', '0', '0', '78');
INSERT INTO `tab_user` VALUES ('13910626480', '张三', 'ba9b95cf46f4264620d3bfa39a5f6278', '2144', '2', '0', '0', '0', '78');
INSERT INTO `tab_user` VALUES ('13913062067', 'asd', '07341d4fb66ca902ce88da5ab893ac68', '33', '2', '0', '0', '0', '78');
INSERT INTO `tab_user` VALUES ('13923990985', 'a', '0c761d513096010eb1eef6ebbc255002', '33', '2', '0', '0', '0', '78');
INSERT INTO `tab_user` VALUES ('13924999123', '张三', 'ba9b95cf46f4264620d3bfa39a5f6278', '144', '2', '0', '0', '0', '78');
INSERT INTO `tab_user` VALUES ('13947298967', '张三', 'ba9b95cf46f4264620d3bfa39a5f6278', '1828', '2', '50', '0', '3072', '283');
INSERT INTO `tab_user` VALUES ('13952927883', '嗷嗷', 'ba9b95cf46f4264620d3bfa39a5f6278', '2144', '2', '0', '0', '0', '78');
INSERT INTO `tab_user` VALUES ('13956834281', '嗷嗷', '0c761d513096010eb1eef6ebbc255002', '33', '2', '0', '0', '0', '78');
INSERT INTO `tab_user` VALUES ('13959805149', 'asd', '0c761d513096010eb1eef6ebbc255002', '33', '2', '0', '0', '0', '78');
INSERT INTO `tab_user` VALUES ('13981792636', 'aa', '0c761d513096010eb1eef6ebbc255002', '33', '2', '0', '0', '0', '78');
INSERT INTO `tab_user` VALUES ('13995739341', '张三', 'ba9b95cf46f4264620d3bfa39a5f6278', '422', '2', '0', '0', '0', '78');
INSERT INTO `tab_user` VALUES ('13996315549', 'aa', '0c761d513096010eb1eef6ebbc255002', '1033', '2', '0', '0', '0', '78');
