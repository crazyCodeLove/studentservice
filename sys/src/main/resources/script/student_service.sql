CREATE DATABASE IF NOT EXISTS `student_service` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `student_service`;

/*Table structure for table `log` */

DROP TABLE IF EXISTS `log`;

CREATE TABLE `log` (
  `id` int(64) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `url` varchar(1024) COLLATE utf8_bin DEFAULT NULL COMMENT '请求 url',
  `method` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '请求方法 post/get/put/delete',
  `queryString` varchar(1024) COLLATE utf8_bin DEFAULT NULL COMMENT 'get 请求参数',
  `ip` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '请求的ip',
  `callClass` varchar(1024) COLLATE utf8_bin DEFAULT NULL COMMENT '处理类',
  `callMethod` varchar(1024) COLLATE utf8_bin DEFAULT NULL COMMENT '处理方法',
  `params` varchar(1024) COLLATE utf8_bin DEFAULT NULL COMMENT '处理方法接收的参数',
  `result` varchar(1024) COLLATE utf8_bin DEFAULT NULL COMMENT '响应结果',
  `code` int(32) DEFAULT NULL COMMENT '响应状态码',
  `message` varchar(1024) COLLATE utf8_bin DEFAULT NULL COMMENT '响应消息',
  `responseTime` datetime(3) DEFAULT NULL COMMENT '响应时间',
  `duration` int(64) DEFAULT NULL COMMENT '处理时间，单位 ms',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `user_info` */

DROP TABLE IF EXISTS `user_info`;

CREATE TABLE `user_info` (
  `uid` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(128) COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `password` varchar(256) COLLATE utf8_bin NOT NULL COMMENT '密码',
  `email` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `telphone` varchar(16) COLLATE utf8_bin DEFAULT NULL COMMENT '电话',
  `birthday` datetime(3) DEFAULT NULL COMMENT '生日',
  `createTime` datetime(3) DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime(3) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `ONLYONE` (`username`,`password`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
