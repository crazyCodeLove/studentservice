CREATE DATABASE /*!32312 IF NOT EXISTS*/`student_service` /*!40100 DEFAULT CHARACTER SET utf8 */;

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
  `responseTime` datetime(3) DEFAULT NULL COMMENT '响应时间',
  `duration` int(64) DEFAULT NULL COMMENT '处理时间，单位 ms',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
