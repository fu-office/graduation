-- client database
CREATE DATABASE if not exists `water` DEFAULT CHARACTER SET utf8;
use water;
drop table if exists water_client;
CREATE TABLE if not exists `water_client` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT comment 'id',
  `regist_date` datetime DEFAULT NULL comment '注册时间',
  `regist_name` varchar(12) NOT NULL comment '注册名（学号）',
  `password` varchar(12) NOT NULL comment '密码',
  `client_name` varchar(30) DEFAULT NULL  comment '姓名',
  `address` varchar(255) DEFAULT NULL  comment '地址',
  `phone_number` varchar(20) DEFAULT NULL  comment '手机',
  `remark` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `regist_name` (`regist_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- area table 
drop table if exists water_area;
CREATE TABLE `water_area` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `long_name` varchar(100) NOT NULL,
  `create_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `long_name` (`long_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- order table 
drop table if exists water_order;
CREATE TABLE `water_order` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `client_id` int(11) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `client_name` varchar(40) DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `order_status` char(1) DEFAULT '0',
  `order_address` varchar(50) DEFAULT '0',
  `delivery_time` date DEFAULT NULL,
  `pay_status` char(1) DEFAULT '0',
  `pay_method` char(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- delivery set table 
drop table if exists delivery_set;
CREATE TABLE `delivery_set` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `s_time` char(5) NOT NULL,
  `e_time` char(5) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- wx_client table 
drop table if exists client_addr;
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

-- wx_client table 
drop table if exists wx_client;
CREATE TABLE `wx_client` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `uuid` varchar(64) NOT NULL,
  `e_time` char(5) NOT NULL,
  `regist_name` varchar(12) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

