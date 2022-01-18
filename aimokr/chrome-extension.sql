/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : office

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 14/01/2022 19:19:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `article_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '帖子ID',
  `author` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '帖子作者',
  `article_title` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '帖子标题',
  `article_url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '帖子地址',
  `team_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分组地址',
  `team_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分组名称',
  `community_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '圈子地址',
  `publish_date` datetime(0) NULL DEFAULT NULL COMMENT '发帖日期',
  `community_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '圈子名称',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '最后更新日期',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '采集时间',
  `last_update_time` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  `operate_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '采集用户IP',
  PRIMARY KEY (`article_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5139800 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '帖子交付列表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES (4138300, '武春宏', '结项审计要求', 'http://eip.teamshub.com/t/4138300', NULL, NULL, NULL, '2019-08-16 16:16:00', NULL, '2020-04-01 15:17:00', '2021-01-31 14:09:10', '2021-01-31 14:09:10', '0:0:0:0:0:0:0:1');
INSERT INTO `article` VALUES (4975672, '马鹏飞（AI）', 'AI能力开放平台@数据模型设计说明书', 'http://eip.teamshub.com/t/4975672', NULL, NULL, NULL, '2020-10-29 14:10:00', NULL, '2020-10-29 14:28:00', '2021-01-30 00:03:28', '2021-01-30 00:03:28', '0:0:0:0:0:0:0:1');
INSERT INTO `article` VALUES (5136403, '武春宏', '研发项目发起结项流程时配置管理需要完成的工作说明', 'http://eip.teamshub.com/t/5136403', NULL, NULL, NULL, '2021-01-28 11:25:00', NULL, '2021-01-28 11:30:00', '2021-01-31 14:08:14', '2021-01-31 14:08:14', '0:0:0:0:0:0:0:1');
INSERT INTO `article` VALUES (5139799, '吕宁', '【会议纪要】特征识别中心研发迭代总结会', 'http://eip.teamshub.com/t/5139799', NULL, NULL, NULL, '2021-01-29 16:37:00', NULL, '2021-01-29 16:37:00', '2021-01-31 23:56:35', '2021-01-31 23:56:35', '0:0:0:0:0:0:0:1');

-- ----------------------------
-- Table structure for attachment
-- ----------------------------
DROP TABLE IF EXISTS `attachment`;
CREATE TABLE `attachment`  (
  `attach_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '附件ID',
  `article_id` bigint(20) NOT NULL COMMENT '帖子ID',
  `attach_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附件名称',
  `attach_url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附件地址',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '采集时间',
  `last_update_time` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  `operate_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '采集用户IP',
  PRIMARY KEY (`attach_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 70673278 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '帖子附件列表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of attachment
-- ----------------------------
INSERT INTO `attachment` VALUES (1121, 1121, 'attachName', '13123', '2021-01-29 23:40:46', '2021-01-29 23:40:46', '0:0:0:0:0:0:0:1');
INSERT INTO `attachment` VALUES (70673276, 4975672, 'AI能力开放平台@数据模型设计说明书.docx 								\n								', 'http://eip.teamshub.com:80/dtopicfile/4963842/70673275', '2021-01-30 00:03:28', '2021-01-30 00:03:28', '0:0:0:0:0:0:0:1');
INSERT INTO `attachment` VALUES (70673277, 4975672, '智能交互平台表结构设计@mapf@20201028修改.xls 								\n								', 'http://eip.teamshub.com:80/dtopicfile/4963862/70673275', '2021-01-30 00:03:28', '2021-01-30 00:03:28', '0:0:0:0:0:0:0:1');

-- ----------------------------
-- Table structure for schedule
-- ----------------------------
DROP TABLE IF EXISTS `schedule`;
CREATE TABLE `schedule`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `uid` bigint(20) NULL DEFAULT NULL COMMENT '任务归属用户ID',
  `leader` bigint(20) NULL DEFAULT NULL COMMENT '分配任务用户ID',
  `schedule_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务名称',
  `start_date` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '开始日期',
  `end_date` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '结束日期',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_update_time` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  `opeation_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建用户IP',
  `status` int(2) NULL DEFAULT NULL COMMENT '任务状态 0 未分配 1 处理中 2 完成',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `dispatcher` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '派发任务用户ID',
  `tasker` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '执行任务用户ID',
  `task_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务名称',
  `start_date` datetime(0) NOT NULL COMMENT '开始日期',
  `end_date` datetime(0) NOT NULL COMMENT '结束日期',
  `task_type` int(2) NOT NULL COMMENT '任务类型 1:开发编码 2:会议讨论 3:设计相关 4:文档编写 5:bug处处理 6:环境部署 ',
  `notice_type` int(2) NOT NULL COMMENT '通知类型 1 不提醒 2 邮件提醒 3 易信提醒',
  `topping` tinyint(1) NOT NULL COMMENT '是否置顶',
  `state` int(2) NULL DEFAULT NULL COMMENT '任务状态 1:新增 2 处理中 3 完成',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_update_time` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  `operate_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建用户IP',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of task
-- ----------------------------
INSERT INTO `task` VALUES (1, 'wangjn_bj@112.com', 'wangjn_bj', '日程功能代码设计52', '2020-05-24 00:00:00', '2020-05-27 00:00:00', 1, 1, 1, 3, '2020-06-03 00:00:00', '2020-06-03 22:42:21', '0:0:0:0:0:0:0:1');
INSERT INTO `task` VALUES (2, 'wangjn_bj', 'wangjn_bj', '产品规划任务', '2020-06-01 00:00:00', '2020-06-18 00:00:00', 3, 2, 0, 3, '2020-06-03 22:42:03', '2020-06-03 22:42:03', '127.0.0.1');
INSERT INTO `task` VALUES (3, 'wangjn_bj', 'wangjn_bj', '上海华铭测试验证', '2020-06-08 00:00:00', '2020-06-12 00:00:00', 2, 1, 0, 3, '2020-06-04 00:00:00', '2020-06-04 00:32:05', '127.0.0.1');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `leader` bigint(20) NOT NULL COMMENT '直属领导',
  `user_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户编码',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名称',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户邮箱',
  `phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户电话',
  `salt` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '盐',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_update_time` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  `operate_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建用户IP',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_code`(`user_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 0, 'wangjn_bj', 'wangjn_bj', 'bf796d125dd199a3764381e6df8ed6d3', 'wangjn_bj@si-tech.com.cn', '13180879819', '2874478700329850', '2021-01-31 18:15:00', '2021-01-31 18:15:00', '0:0:0:0:0:0:0:1');

SET FOREIGN_KEY_CHECKS = 1;
