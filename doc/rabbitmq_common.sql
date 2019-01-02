/*
Navicat MySQL Data Transfer

Source Server         : 120.79.151.179
Source Server Version : 50718
Source Host           : 120.79.151.179:3306
Source Database       : rabbitmq_common

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2019-01-02 21:10:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_mq_message_failed
-- ----------------------------
DROP TABLE IF EXISTS `t_mq_message_failed`;
CREATE TABLE `t_mq_message_failed` (
  `fail_id` bigint(20) NOT NULL COMMENT '处理失败唯一ID',
  `message_id` bigint(20) NOT NULL COMMENT '消息唯一ID',
  `fail_title` varchar(500) DEFAULT NULL COMMENT '失败原因-简略',
  `fail_desc` text COMMENT '失败原因-详细',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`fail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_mq_message_log
-- ----------------------------
DROP TABLE IF EXISTS `t_mq_message_log`;
CREATE TABLE `t_mq_message_log` (
  `message_id` bigint(20) NOT NULL COMMENT '消息唯一ID',
  `type` int(4) DEFAULT NULL COMMENT '业务类型',
  `message` text COMMENT '消息内容',
  `try_count` int(4) NOT NULL DEFAULT '0' COMMENT '重试次数',
  `status` int(4) NOT NULL COMMENT '消息投递状态  0 投递中 1 投递成功   2 投递失败  4处理中',
  `next_retry` datetime DEFAULT NULL COMMENT '--下一次重试时间 或 超时时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
