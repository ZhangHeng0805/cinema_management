# 工程简介

# hello success

# 延伸阅读
# 数据库的建立
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

Date: 2021-11-04 16:41:56
*/

SET FOREIGN_KEY_CHECKS=0;

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `order_num` varchar(255) DEFAULT NULL COMMENT '订单编号',
  `account_id` int(11) DEFAULT NULL COMMENT '用户id',
  `rowpiece_id` int(11) DEFAULT NULL COMMENT '排片id',
  `sit_num` int(10) DEFAULT NULL COMMENT '座位号',
  `order_state` int(1) DEFAULT '1' COMMENT '订单状态（0-未完成 1-已完成 2-订单失败）',
  `order_time` varchar(20) DEFAULT NULL COMMENT '订单时间',
  `order_price` double(255,2) DEFAULT NULL COMMENT '订单价格',
  PRIMARY KEY (`id`),
  KEY `t_order_ibfk_1` (`account_id`),
  KEY `t_order_ibfk_2` (`rowpiece_id`),
  CONSTRAINT `t_order_ibfk_2` FOREIGN KEY (`rowpiece_id`) REFERENCES `t_row_piece` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `t_order_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `t_members` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_row_piece
-- ----------------------------
DROP TABLE IF EXISTS `t_row_piece`;
CREATE TABLE `t_row_piece` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '排片id',
  `film_id` int(11) DEFAULT NULL COMMENT '电影id',
  `screens_id` int(11) DEFAULT NULL COMMENT '影厅id',
  `playing_time` varchar(20) DEFAULT NULL COMMENT '播放时间',
  `fare` double(11,2) DEFAULT NULL COMMENT '票价',
  `sit_state` text COMMENT '座位状态',
  PRIMARY KEY (`id`),
  KEY `t_rowpiece_ibfk_1` (`film_id`),
  KEY `t_rowpiece_ibfk_2` (`screens_id`),
  CONSTRAINT `t_row_piece_ibfk_1` FOREIGN KEY (`film_id`) REFERENCES `t_film` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `t_row_piece_ibfk_2` FOREIGN KEY (`screens_id`) REFERENCES `t_screens` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

```

