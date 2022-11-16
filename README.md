# 工程简介

# 一、需求分析(分析项目模块)  

## 后台功能(管理员)

### 1、系统管理(管理员功能、系统属性的设置)

* **管理员表**: id 、账户、密码、姓名、联系电话、状态

### 2、会员管理【查询、修改、删除】

* **会员表**:   id、账户、头像、昵称、密码、性别、生日、电话、邮箱、余额、状态

### 3、影片管理 【增、删、改、查:CURD】

* **影片表**：id、影片名称、封面图、电影类型、导演、主要演员、上映时间、电影评分、影片介绍、状态

### 4、影厅管理【增、删、改、查:CURD】

* **影厅表**：id、影厅名称、类型、座位数、座位布局、状态

### 5、排片管理 【增、删、改、查:CURD】

* **排片表**: id、电影id 、影厅id  、放映时间、票价、座位状态

### 6、订单管理

* **订单表**:id、订单号、用户id 、排片id 、座位号、订单状态、订单的时间、订单的金额

## 前台功能(普通用户)
### 1、首页的数据展示 
### 2、注册、登陆
### 3、买票、选座 、退票
### 4、创建订单
### 5、 我的信息

# 二、MySql数据库的建立
```sql
/*
Navicat MySQL Data Transfer

Source Server         : 张恒
Source Server Version : 50535
Source Host           : localhost:3306
Source Database       : cinema

Target Server Type    : MYSQL
Target Server Version : 50535
File Encoding         : 65001

Date: 2021-11-13 22:54:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for logger
-- ----------------------------
DROP TABLE IF EXISTS `logger`;
CREATE TABLE `logger` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '操作id',
  `account` varchar(255) DEFAULT NULL COMMENT '操作者账号',
  `name` varchar(255) DEFAULT NULL COMMENT '操作者名称',
  `tel` varchar(255) DEFAULT NULL COMMENT '操作者的手机号码',
  `time` varchar(255) DEFAULT NULL COMMENT '操作时间',
  `type` int(11) DEFAULT NULL COMMENT '操作类型(0:充值操作)',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_administrator
-- ----------------------------
DROP TABLE IF EXISTS `t_administrator`;
CREATE TABLE `t_administrator` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '管理员id',
  `account` varchar(255) DEFAULT NULL COMMENT '账户',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `phone_num` varchar(11) DEFAULT NULL COMMENT '电话号码',
  `name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `state` int(1) DEFAULT '0' COMMENT '账号状态(0-禁用 1-正常)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_film
-- ----------------------------
DROP TABLE IF EXISTS `t_film`;
CREATE TABLE `t_film` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '影片id',
  `film_name` varchar(255) DEFAULT NULL COMMENT '电影名称',
  `film_type` varchar(255) DEFAULT NULL COMMENT '电影类型',
  `country` varchar(255) DEFAULT NULL COMMENT '国家',
  `cover` varchar(1024) DEFAULT NULL COMMENT '影片封面',
  `director` varchar(255) DEFAULT NULL COMMENT '导演',
  `main_actor` varchar(1024) DEFAULT NULL COMMENT '主要演员',
  `movie_ratings` double(4,1) DEFAULT NULL COMMENT '电影评分',
  `release_time` varchar(20) DEFAULT NULL COMMENT '上映时间',
  `state` int(1) DEFAULT '0' COMMENT '影片状态（0-即将上映 1-上映中 2-下线）',
  `film_introduced` varchar(1000) DEFAULT NULL COMMENT '电影简介',
  PRIMARY KEY (`id`),
  KEY `film_name` (`film_name`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_members
-- ----------------------------
DROP TABLE IF EXISTS `t_members`;
CREATE TABLE `t_members` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '会员id',
  `account` varchar(255) DEFAULT NULL COMMENT '账户',
  `avatar` varchar(1024) DEFAULT NULL COMMENT '头像',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  `gender` int(1) DEFAULT '0' COMMENT '性别（0-不详 1-男 2-女）',
  `birthday` varchar(20) DEFAULT NULL COMMENT '生日',
  `phone_num` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `balance` double(10,2) DEFAULT NULL COMMENT '余额',
  `state` int(1) DEFAULT '0' COMMENT '状态（0-禁用 1-启用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `order_num` varchar(255) DEFAULT NULL COMMENT '订单编号',
  `account_id` int(11) DEFAULT NULL COMMENT '用户id',
  `rowpiece_id` int(11) DEFAULT NULL COMMENT '排片id',
  `sit_num` varchar(100) DEFAULT NULL COMMENT '座位号',
  `order_state` int(1) DEFAULT '1' COMMENT '订单状态（0-未完成 1-已完成 2-订单失败）',
  `order_time` varchar(20) DEFAULT NULL COMMENT '订单时间',
  `order_price` double(255,2) DEFAULT NULL COMMENT '订单价格',
  `order_info` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `t_order_ibfk_1` (`account_id`),
  KEY `t_order_ibfk_2` (`rowpiece_id`),
  CONSTRAINT `t_order_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `t_members` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `t_order_ibfk_2` FOREIGN KEY (`rowpiece_id`) REFERENCES `t_row_piece` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_row_piece
-- ----------------------------
DROP TABLE IF EXISTS `t_row_piece`;
CREATE TABLE `t_row_piece` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '排片id',
  `film_id` int(11) DEFAULT NULL COMMENT '电影id',
  `film_name` varchar(255) DEFAULT NULL,
  `screens_id` int(11) DEFAULT NULL COMMENT '影厅id',
  `screens_name` varchar(255) DEFAULT NULL,
  `playing_time` varchar(30) DEFAULT NULL COMMENT '播放时间',
  `fare` double(11,2) DEFAULT NULL COMMENT '票价',
  `sit_state` text COMMENT '座位状态',
  PRIMARY KEY (`id`),
  KEY `t_rowpiece_ibfk_1` (`film_id`),
  KEY `t_rowpiece_ibfk_2` (`screens_id`),
  KEY `film_name` (`film_name`),
  KEY `screens_name` (`screens_name`),
  CONSTRAINT `t_row_piece_ibfk_1` FOREIGN KEY (`film_id`) REFERENCES `t_film` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `t_row_piece_ibfk_2` FOREIGN KEY (`screens_id`) REFERENCES `t_screens` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `t_row_piece_ibfk_3` FOREIGN KEY (`film_name`) REFERENCES `t_film` (`film_name`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `t_row_piece_ibfk_4` FOREIGN KEY (`screens_name`) REFERENCES `t_screens` (`screens_name`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_screens
-- ----------------------------
DROP TABLE IF EXISTS `t_screens`;
CREATE TABLE `t_screens` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '影厅id',
  `type` varchar(255) DEFAULT NULL COMMENT '影厅类型',
  `screens_name` varchar(255) DEFAULT NULL COMMENT '影厅名称',
  `seating_num` int(11) DEFAULT NULL COMMENT '座位数',
  `seating_info` text COMMENT '座位状态',
  `state` int(1) DEFAULT '0' COMMENT '影厅状态（0-未启用 1-启用）',
  PRIMARY KEY (`id`),
  KEY `screens_name` (`screens_name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for verification_code
-- ----------------------------
DROP TABLE IF EXISTS `verification_code`;
CREATE TABLE `verification_code` (
  `id` varchar(255) NOT NULL COMMENT '验证id',
  `code` varchar(255) DEFAULT NULL COMMENT '验证码',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of verification_code
-- ----------------------------
INSERT INTO `verification_code` VALUES ('3742', '691837');
INSERT INTO `verification_code` VALUES ('3215', '789511');
INSERT INTO `verification_code` VALUES ('3942', '592167');
INSERT INTO `verification_code` VALUES ('1692', '773661');
INSERT INTO `verification_code` VALUES ('9511', '469922');
INSERT INTO `verification_code` VALUES ('1239', '687682');
INSERT INTO `verification_code` VALUES ('9623', '789631');
INSERT INTO `verification_code` VALUES ('8899', '123545');
INSERT INTO `verification_code` VALUES ('2634', '489622');
INSERT INTO `verification_code` VALUES ('1236', '773610');
INSERT INTO `verification_code` VALUES ('2456', '692178');
INSERT INTO `verification_code` VALUES ('2134', '110916');
INSERT INTO `verification_code` VALUES ('2236', '091613');
INSERT INTO `verification_code` VALUES ('1368', '132116');
INSERT INTO `verification_code` VALUES ('2399', '092115');
INSERT INTO `verification_code` VALUES ('2526', '161609');
INSERT INTO `verification_code` VALUES ('0742', '171121');
INSERT INTO `verification_code` VALUES ('0616', '170916');
INSERT INTO `verification_code` VALUES ('3052', '674209');
INSERT INTO `verification_code` VALUES ('2890', '717756');


```

