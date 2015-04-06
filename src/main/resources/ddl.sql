-- client database
CREATE DATABASE if not exists `lbyt` DEFAULT CHARACTER SET utf8;
use lbyt;
drop table if exists lbyt_client;
CREATE TABLE if not exists `lbyt_client` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT comment 'id',
  `card_no` varchar(16) NOT NULL comment '卡号',
  `register_date` datetime DEFAULT NULL comment '登记日期',
  `modify_date` datetime DEFAULT NULL  comment '最后修改日期',
  `client_name` varchar(30) DEFAULT NULL  comment '姓名',
  `address` varchar(255) DEFAULT NULL  comment '地址',
  `phone_number` varchar(20) DEFAULT NULL  comment '手机',
  `tel_number` varchar(20) DEFAULT NULL  comment '电话',
  `post_code` varchar(10) DEFAULT NULL  comment '邮编',
  `birthday` date DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `shop_name` varchar(100) DEFAULT NULL,
  `province` varchar(30) DEFAULT NULL,
  `city` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `card_no` (`card_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- area table 
drop table if exists area;
CREATE TABLE `area` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `long_name` varchar(100) NOT NULL,
  `prov_name` varchar(50) NOT NULL,
  `city_name` varchar(50) NOT NULL,
  `create_date` date DEFAULT NULL,
  `shop_state` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `long_name` (`long_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- gifts table 
drop table if exists gifts;
CREATE TABLE `gifts` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `c_id` int(11) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `c_name` varchar(40) DEFAULT NULL,
  `c_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone_UNIQUE` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- use lbyt database 连接数据库
use lbyt;
-- 没有省份的客户
select * from lbyt_client a where a.province is null;
-- 手动更新客户的省份 '?'自己填
update lbyt_client set province = '?' where city = '?';