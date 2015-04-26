# Host: 127.0.0.1  (Version: 5.5.20)
# Date: 2015-04-20 20:13:23
# Generator: MySQL-Front 5.3  (Build 4.205)
#
# Structure for table "client_addr"
#
use water;
DROP TABLE IF EXISTS `client_addr`;
CREATE TABLE `client_addr` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `client_id` int(10) unsigned NOT NULL,
  `area` varchar(64) NOT NULL,
  `department` char(5) NOT NULL,
  `floor` varchar(12) NOT NULL,
  `room` varchar(12) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `client_id` (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "delivery_range"
#

DROP TABLE IF EXISTS `delivery_range`;
CREATE TABLE `delivery_range` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` char(5) NOT NULL,
  `parent` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "delivery_set"
#

DROP TABLE IF EXISTS `delivery_set`;
CREATE TABLE `delivery_set` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `s_time` char(5) NOT NULL,
  `e_time` char(5) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "product"
#

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `p_name` varchar(64) NOT NULL,
  `create_date` date DEFAULT NULL,
  `price` float(8,2) DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "stock"
#

DROP TABLE IF EXISTS `stock`;
CREATE TABLE `stock` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `num` varchar(64) NOT NULL,
  `p_id` char(5) NOT NULL,
  `p_name` varchar(20),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "stock_order"
#

DROP TABLE IF EXISTS `stock_order`;
CREATE TABLE `stock_order` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `stock_id` int(10) unsigned NOT NULL,
  `p_id` int(10) unsigned NOT NULL,
  `p_name` varchar(64) NOT NULL,
  `num` int(8) NOT NULL,
  `type` int(1) NOT NULL,
  `create_date` date,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "water_area"
#

DROP TABLE IF EXISTS `water_area`;
CREATE TABLE `water_area` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `long_name` varchar(100) NOT NULL,
  `create_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `long_name` (`long_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "water_client"
#

DROP TABLE IF EXISTS `water_client`;
CREATE TABLE `water_client` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `regist_date` datetime DEFAULT NULL COMMENT '注册时间',
  `regist_name` varchar(12) NOT NULL COMMENT '注册名（学号）',
  `password` varchar(12) NOT NULL COMMENT '密码',
  `client_name` varchar(30) DEFAULT NULL COMMENT '姓名',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `phone_number` varchar(20) DEFAULT NULL COMMENT '手机',
  `remark` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `regist_name` (`regist_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "water_order"
#

DROP TABLE IF EXISTS `water_order`;
CREATE TABLE `water_order` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `client_id` int(11) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `client_name` varchar(40) DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `order_status` char(1) DEFAULT '0',
  `order_address` varchar(50) DEFAULT '0',
  `delivery_date` date DEFAULT NULL,
  `delivery_time` varchar(12) DEFAULT NULL,
  `pay_status` char(1) DEFAULT '0',
  `pay_method` char(1) DEFAULT '0',
  `department` varchar(30) DEFAULT '',
  `area` varchar(30) DEFAULT '',
  `total` float(10,2) DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "water_order"
#
DROP TABLE IF EXISTS `water_order_items`;
CREATE TABLE `water_order_items` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `order_id` int(11) unsigned,
  `p_id` int(11) unsigned NOT NULL,
  `p_name` varchar(60),
  `p_price` int(8),
  `num` int(5),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "wx_client"
#

DROP TABLE IF EXISTS `wx_client`;
CREATE TABLE `wx_client` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `uuid` varchar(64) NOT NULL,
  `e_time` char(5) NOT NULL,
  `regist_name` varchar(12) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into product (p_name, create_date, price)  values ("桶装水", '2015-04-30', 16.00);