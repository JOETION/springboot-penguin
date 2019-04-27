/*
Navicat MySQL Data Transfer

Source Server         : 阿里云
Source Server Version : 50643
Source Host           : 106.15.178.137:3306
Source Database       : db_qexz_penguin

Target Server Type    : MYSQL
Target Server Version : 50643
File Encoding         : 65001

Date: 2019-04-20 11:00:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_penguin_account
-- ----------------------------
DROP TABLE IF EXISTS `t_penguin_account`;
CREATE TABLE `t_penguin_account` (
  `id` int(8) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(63) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '姓名',
  `username` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账号,一般为学号或者教工号',
  `password` varchar(60) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `qq` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'QQ',
  `phone` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '电话',
  `email` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `description` varchar(63) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '个人描述',
  `avatar_img_url` varchar(63) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像',
  `state` int(8) NOT NULL DEFAULT '0' COMMENT '当前账号状态,0表示正常,1表示封禁',
  `level` int(8) NOT NULL DEFAULT '0' COMMENT '0表示学生,1表示教师,2表示管理员',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_penguin_account
-- ----------------------------
INSERT INTO `t_penguin_account` VALUES ('2', '管理员', 'admin', '5FFFCE11572B59C9E5256949F90D1D9D', '1322874562', '13882946', 'qq@foxmail.com', '只会HelloWorld', 'adf3e76e-38a8-43de-a9f3-a4ba17dae0fe.jpg', '0', '2', '2018-01-30 09:47:38', '2019-04-20 10:56:44');
INSERT INTO `t_penguin_account` VALUES ('3', '方老师', '119', '5FFFCE11572B59C9E5256949F90D1D9D', '1111111111', '11111111111', 'qq@foxmail.com', '只会HelloWorld的程序员', 'headimg_placeholder.png', '0', '1', '2018-03-02 15:08:44', '2019-04-17 16:19:56');
INSERT INTO `t_penguin_account` VALUES ('4', '方同学', '110', '5FFFCE11572B59C9E5256949F90D1D9D', '987654321', '123654', '1987654321@qq.com', '呵呵', '867ba6c8-fdf5-4df3-8c02-dd64e6178c7a.png', '1', '0', '2018-03-02 15:18:35', '2019-04-19 12:51:23');

-- ----------------------------
-- Table structure for t_penguin_answer
-- ----------------------------
DROP TABLE IF EXISTS `t_penguin_answer`;
CREATE TABLE `t_penguin_answer` (
  `student_id` int(10) NOT NULL COMMENT '学生编号',
  `contest_id` int(10) NOT NULL COMMENT '考试编号',
  `answer_json` varchar(1024) DEFAULT NULL COMMENT '答案',
  `state` int(2) NOT NULL DEFAULT '0' COMMENT '0表示已交卷未评卷，1表示已交卷已评卷',
  `start_time` datetime NOT NULL COMMENT '考试开始时间',
  `finish_time` datetime NOT NULL COMMENT '考试完成时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`student_id`,`contest_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_penguin_answer
-- ----------------------------
INSERT INTO `t_penguin_answer` VALUES ('2', '13', '[{\"questionType\":0,\"questionId\":7,\"answer\":\"B\"},{\"questionType\":0,\"questionId\":8,\"answer\":\"D\"},{\"questionType\":0,\"questionId\":10,\"answer\":\"C\"},{\"questionType\":0,\"questionId\":11,\"answer\":\"D\"},{\"questionType\":2,\"questionId\":12,\"answer\":\"123\"},{\"questionType\":2,\"questionId\":13,\"answer\":\"2131\"}]', '1', '2019-04-16 11:48:19', '2019-04-16 11:48:35', '2019-04-16 11:49:53', '2019-04-17 12:49:20');
INSERT INTO `t_penguin_answer` VALUES ('3', '9', '[{\r\n	\"questionType\": 0,\r\n	\"questionId\": 10,\r\n	\"answer\": \"D\"\r\n}, {\r\n	\"questionType\": 0,\r\n	\"questionId\": 8,\r\n	\"answer\": \"B\"\r\n}, {\r\n	\"questionType\": 0,\r\n	\"questionId\": 7,\r\n	\"answer\": \"A\"\r\n}, {\r\n	\"questionType\": 2,\r\n	\"questionId\": 12,\r\n	\"answer\": \"1. 实体完整性约束 要求候选码非空且唯一。2. 参照完整性约束 要求外码要么取空值要么所取的值在对应的候选码中出现。3. 用户自定义完整性。包括非空约束，唯一约束，检查约束，缺省值约束等。\"\r\n}, {\r\n	\"questionType\": 2,\r\n	\"questionId\": 13,\r\n	\"answer\": \"1. 丢失修改2. 读“脏”数据3. 不可重复读通过基于锁的协议实现对事务并发控制。\"\r\n}]', '1', '2019-03-10 11:02:15', '2019-03-10 11:02:18', '2019-03-24 11:04:03', '2019-03-28 16:46:16');
INSERT INTO `t_penguin_answer` VALUES ('4', '9', '[{\r\n	\"questionType\": 0,\r\n	\"questionId\": 10,\r\n	\"answer\": \"B\"\r\n}, {\r\n	\"questionType\": 0,\r\n	\"questionId\": 8,\r\n	\"answer\": \"B\"\r\n}, {\r\n	\"questionType\": 0,\r\n	\"questionId\": 7,\r\n	\"answer\": \"A\"\r\n}, {\r\n	\"questionType\": 2,\r\n	\"questionId\": 12,\r\n	\"answer\": \"1. 实体完整性约束 要求候选码非空且唯一。2. 参照完整性约束 要求外码要么取空值要么所取的值在对应的候选码中出现。3. 用户自定义完整性。包括非空约束，唯一约束，检查约束，缺省值约束等。\"\r\n}, {\r\n	\"questionType\": 2,\r\n	\"questionId\": 13,\r\n	\"answer\": \"1. 丢失修改2. 读“脏”数据3. 不可重复读通过基于锁的协议实现对事务并发控制。\"\r\n}]', '1', '2019-03-10 11:02:15', '2019-03-10 11:02:18', '2019-03-26 21:58:42', '2019-03-30 22:08:54');
INSERT INTO `t_penguin_answer` VALUES ('4', '12', '[{\"questionType\":0,\"questionId\":7,\"answer\":\"A\"},{\"questionType\":0,\"questionId\":8,\"answer\":\"B\"},{\"questionType\":2,\"questionId\":24,\"answer\":\"无法上嗯嗯上而出的深V2地方一百一十一\"},{\"questionType\":2,\"questionId\":25,\"answer\":\"鹅3 23对象3得唔得带我从\"}]', '1', '2019-04-01 15:58:54', '2019-04-01 15:59:14', '2019-04-01 16:00:25', '2019-04-17 10:13:05');
INSERT INTO `t_penguin_answer` VALUES ('4', '14', '[{\"questionType\":0,\"questionId\":7,\"answer\":\"C\"},{\"questionType\":0,\"questionId\":10,\"answer\":\"A\"},{\"questionType\":2,\"questionId\":22,\"answer\":\"5经历过\"},{\"questionType\":2,\"questionId\":34,\"answer\":\"\"}]', '1', '2019-04-17 16:52:33', '2019-04-17 16:53:31', '2019-04-17 16:53:31', '2019-04-17 16:55:33');
INSERT INTO `t_penguin_answer` VALUES ('4', '15', '[{\"questionType\":0,\"questionId\":7,\"answer\":\"A\"},{\"questionType\":0,\"questionId\":8,\"answer\":\"\"},{\"questionType\":0,\"questionId\":10,\"answer\":\"\"},{\"questionType\":0,\"questionId\":11,\"answer\":\"\"},{\"questionType\":2,\"questionId\":12,\"answer\":\"\"},{\"questionType\":2,\"questionId\":13,\"answer\":\"\"}]', '0', '2019-04-19 14:55:14', '2019-04-19 15:00:00', '2019-04-19 15:01:20', '2019-04-19 15:01:20');

-- ----------------------------
-- Table structure for t_penguin_comment
-- ----------------------------
DROP TABLE IF EXISTS `t_penguin_comment`;
CREATE TABLE `t_penguin_comment` (
  `id` int(8) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` int(8) DEFAULT NULL COMMENT '用户ID',
  `post_id` int(8) DEFAULT NULL COMMENT '帖子id',
  `content` text COLLATE utf8mb4_unicode_ci COMMENT '内容',
  `deleted` int(1) DEFAULT '0' COMMENT '是否删除',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_penguin_comment
-- ----------------------------
INSERT INTO `t_penguin_comment` VALUES ('6', '4', '2', '哦哦，看你自由自在的装逼', '1', '2019-04-03 12:35:03');
INSERT INTO `t_penguin_comment` VALUES ('12', '4', '4', '呵呵哒', '0', '2019-04-04 20:25:12');
INSERT INTO `t_penguin_comment` VALUES ('17', '4', '8', '可以哟', '0', '2019-04-07 11:52:00');
INSERT INTO `t_penguin_comment` VALUES ('25', '4', '10', '加班一时爽，一直加班一直爽', '1', '2019-04-15 17:31:54');
INSERT INTO `t_penguin_comment` VALUES ('28', '2', '20', '12wq', '1', '2019-04-16 23:06:04');
INSERT INTO `t_penguin_comment` VALUES ('29', '4', '20', '3242', '1', '2019-04-16 23:06:46');
INSERT INTO `t_penguin_comment` VALUES ('30', '2', '21', 'dewfwef', '1', '2019-04-16 23:54:18');
INSERT INTO `t_penguin_comment` VALUES ('31', '2', '21', '123123', '1', '2019-04-17 16:09:42');
INSERT INTO `t_penguin_comment` VALUES ('32', '2', '21', '鹅34让', '1', '2019-04-17 16:09:50');
INSERT INTO `t_penguin_comment` VALUES ('33', '4', '2', '呵呵哒', '0', '2019-04-17 16:22:57');
INSERT INTO `t_penguin_comment` VALUES ('34', '4', '2', '123324', '1', '2019-04-17 16:27:30');
INSERT INTO `t_penguin_comment` VALUES ('35', '4', '2', '435345', '1', '2019-04-17 16:27:52');
INSERT INTO `t_penguin_comment` VALUES ('36', '4', '2', 'uh6yth', '1', '2019-04-17 16:28:04');
INSERT INTO `t_penguin_comment` VALUES ('37', '4', '8', '逗比', '0', '2019-04-17 16:44:07');

-- ----------------------------
-- Table structure for t_penguin_complaint
-- ----------------------------
DROP TABLE IF EXISTS `t_penguin_complaint`;
CREATE TABLE `t_penguin_complaint` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_id` int(11) NOT NULL COMMENT '投诉者编号',
  `complaint_which` int(1) NOT NULL COMMENT '投诉哪个：0投诉用户，1投诉帖子，2投诉评论，3投诉回复',
  `which_id` int(11) NOT NULL COMMENT '投诉哪个的编号',
  `complaint_type` int(1) NOT NULL COMMENT '投诉类型：0广告，1色情，2言语辱骂，3其他',
  `complaint_content` varchar(255) DEFAULT NULL COMMENT '投诉内容',
  `complaint_state` int(1) NOT NULL COMMENT '投诉状态：0未审阅，1投诉失败，2投诉成功',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_penguin_complaint
-- ----------------------------
INSERT INTO `t_penguin_complaint` VALUES ('9', '2', '2', '25', '0', '3224', '1', '2019-04-16 22:59:32', '2019-04-16 23:11:13');
INSERT INTO `t_penguin_complaint` VALUES ('10', '4', '2', '28', '0', '1334', '1', '2019-04-16 23:06:40', '2019-04-16 23:11:16');
INSERT INTO `t_penguin_complaint` VALUES ('11', '2', '2', '29', '0', '32eew', '1', '2019-04-16 23:07:12', '2019-04-16 23:11:18');
INSERT INTO `t_penguin_complaint` VALUES ('12', '4', '2', '30', '0', '我插尼玛', '1', '2019-04-16 23:55:22', '2019-04-16 23:56:25');
INSERT INTO `t_penguin_complaint` VALUES ('13', '2', '2', '37', '0', '言语辱骂', '2', '2019-04-17 16:44:37', '2019-04-17 16:45:30');

-- ----------------------------
-- Table structure for t_penguin_contest
-- ----------------------------
DROP TABLE IF EXISTS `t_penguin_contest`;
CREATE TABLE `t_penguin_contest` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `total_score` int(8) DEFAULT NULL COMMENT '考试总分',
  `subject_id` int(8) DEFAULT NULL COMMENT '学科ID',
  `state` int(8) DEFAULT '0' COMMENT '进行状态:0表示未开始,1表示进行中,2表示考试已经结束,3表示该考试已经完成批卷',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `start_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '考试开始时间',
  `end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '考试结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_penguin_contest
-- ----------------------------
INSERT INTO `t_penguin_contest` VALUES ('4', '四川理工学院2017年数据库原理与应用考试试题A卷', '0', '8', '2', '2018-02-16 14:19:51', '2019-04-17 16:22:02', '2019-03-15 12:26:00', '2019-03-15 17:00:00');
INSERT INTO `t_penguin_contest` VALUES ('8', '四川理工学院C语言程序设计考试', '0', '2', '3', '2019-03-18 13:38:19', '2019-03-21 16:40:59', '2019-03-18 14:55:00', '2019-03-18 15:00:00');
INSERT INTO `t_penguin_contest` VALUES ('9', '四川理工学院测试', '100', '4', '3', '2019-03-18 14:24:44', '2019-04-06 20:13:30', '2019-03-18 17:20:00', '2019-03-18 22:25:00');
INSERT INTO `t_penguin_contest` VALUES ('11', '四川理工学院软件工程考试', '100', '7', '3', '2019-03-24 11:22:24', '2019-04-16 11:17:55', '2019-03-24 11:25:00', '2019-03-25 11:30:00');
INSERT INTO `t_penguin_contest` VALUES ('12', '四川轻化工大学人品测试', '75', '1', '3', '2019-03-31 21:02:09', '2019-04-16 11:52:05', '2019-03-31 21:00:00', '2019-04-01 17:00:00');
INSERT INTO `t_penguin_contest` VALUES ('13', '四川理工学院java基础测试', '100', '1', '3', '2019-04-15 16:53:46', '2019-04-17 10:18:51', '2019-04-15 20:30:00', '2019-04-16 20:35:00');
INSERT INTO `t_penguin_contest` VALUES ('14', '四川理工学院人品测试', '100', '7', '3', '2019-04-17 16:51:18', '2019-04-17 16:55:40', '2019-04-17 16:50:00', '2019-04-16 16:55:00');
INSERT INTO `t_penguin_contest` VALUES ('15', '四川轻化工测试考试', '100', '7', '2', '2019-04-19 14:44:23', '2019-04-20 01:22:59', '2019-04-19 14:40:00', '2019-04-19 16:00:00');
INSERT INTO `t_penguin_contest` VALUES ('16', 'aaa', '0', '1', '2', '2019-04-19 14:50:39', '2019-04-19 14:56:19', '2019-04-19 14:49:00', '2019-04-19 14:50:39');

-- ----------------------------
-- Table structure for t_penguin_contest_content
-- ----------------------------
DROP TABLE IF EXISTS `t_penguin_contest_content`;
CREATE TABLE `t_penguin_contest_content` (
  `contest_id` int(10) NOT NULL COMMENT '考试编号',
  `question_id` int(10) NOT NULL COMMENT '问题编号',
  `score` int(2) NOT NULL COMMENT '试题分数',
  `diffculty` int(2) NOT NULL COMMENT '试题难度',
  `state` varchar(2) NOT NULL DEFAULT '0' COMMENT '考试试题状态，0表示未做，1表示已做',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`contest_id`,`question_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_penguin_contest_content
-- ----------------------------
INSERT INTO `t_penguin_contest_content` VALUES ('13', '13', '30', '1', '0', '2019-04-15 16:54:28', '2019-04-15 17:21:44');
INSERT INTO `t_penguin_contest_content` VALUES ('13', '12', '30', '1', '0', '2019-04-15 16:54:22', '2019-04-15 17:21:40');
INSERT INTO `t_penguin_contest_content` VALUES ('13', '11', '10', '1', '0', '2019-04-15 16:54:17', '2019-04-15 17:21:33');
INSERT INTO `t_penguin_contest_content` VALUES ('13', '10', '10', '1', '0', '2019-04-15 16:54:06', '2019-04-15 17:21:28');
INSERT INTO `t_penguin_contest_content` VALUES ('13', '8', '10', '2', '0', '2019-04-15 16:54:01', '2019-04-15 17:21:22');
INSERT INTO `t_penguin_contest_content` VALUES ('13', '7', '10', '3', '0', '2019-04-15 16:53:56', '2019-04-15 17:21:14');
INSERT INTO `t_penguin_contest_content` VALUES ('12', '24', '25', '5', '0', '2019-03-31 21:02:45', '2019-03-31 21:03:30');
INSERT INTO `t_penguin_contest_content` VALUES ('12', '8', '25', '2', '0', '2019-03-31 21:02:41', '2019-03-31 21:03:22');
INSERT INTO `t_penguin_contest_content` VALUES ('12', '7', '25', '3', '0', '2019-03-31 21:02:35', '2019-04-03 11:43:22');
INSERT INTO `t_penguin_contest_content` VALUES ('9', '13', '50', '5', '0', '2019-03-25 20:02:05', '2019-03-25 20:02:05');
INSERT INTO `t_penguin_contest_content` VALUES ('9', '12', '50', '5', '0', '2019-03-25 14:50:05', '2019-03-25 14:50:05');
INSERT INTO `t_penguin_contest_content` VALUES ('11', '12', '50', '5', '0', '2019-03-24 11:22:50', '2019-03-24 11:23:14');
INSERT INTO `t_penguin_contest_content` VALUES ('11', '7', '50', '5', '0', '2019-03-24 11:22:43', '2019-03-24 11:23:05');
INSERT INTO `t_penguin_contest_content` VALUES ('9', '10', '0', '0', '0', '2019-03-21 17:13:57', '2019-03-21 17:13:57');
INSERT INTO `t_penguin_contest_content` VALUES ('9', '8', '0', '0', '0', '2019-03-21 17:13:53', '2019-03-21 17:13:53');
INSERT INTO `t_penguin_contest_content` VALUES ('9', '7', '0', '0', '0', '2019-03-21 17:13:48', '2019-03-21 17:13:48');
INSERT INTO `t_penguin_contest_content` VALUES ('12', '10', '0', '0', '0', '2019-04-16 11:18:55', '2019-04-16 11:18:55');
INSERT INTO `t_penguin_contest_content` VALUES ('14', '7', '25', '1', '0', '2019-04-17 16:51:30', '2019-04-17 16:51:58');
INSERT INTO `t_penguin_contest_content` VALUES ('14', '10', '25', '1', '0', '2019-04-17 16:51:34', '2019-04-17 16:52:05');
INSERT INTO `t_penguin_contest_content` VALUES ('14', '22', '25', '1', '0', '2019-04-17 16:51:38', '2019-04-17 16:52:10');
INSERT INTO `t_penguin_contest_content` VALUES ('14', '34', '25', '1', '0', '2019-04-17 16:51:45', '2019-04-17 16:52:14');
INSERT INTO `t_penguin_contest_content` VALUES ('15', '7', '10', '1', '0', '2019-04-19 14:45:44', '2019-04-19 14:46:29');
INSERT INTO `t_penguin_contest_content` VALUES ('15', '8', '10', '1', '0', '2019-04-19 14:45:49', '2019-04-19 14:46:36');
INSERT INTO `t_penguin_contest_content` VALUES ('15', '10', '10', '1', '0', '2019-04-19 14:45:54', '2019-04-19 14:46:42');
INSERT INTO `t_penguin_contest_content` VALUES ('15', '11', '10', '1', '0', '2019-04-19 14:46:00', '2019-04-19 14:46:47');
INSERT INTO `t_penguin_contest_content` VALUES ('15', '12', '30', '1', '0', '2019-04-19 14:46:05', '2019-04-19 14:46:59');
INSERT INTO `t_penguin_contest_content` VALUES ('15', '13', '30', '1', '0', '2019-04-19 14:46:10', '2019-04-19 14:47:08');
INSERT INTO `t_penguin_contest_content` VALUES ('16', '8', '0', '0', '0', '2019-04-19 14:54:03', '2019-04-19 14:54:03');

-- ----------------------------
-- Table structure for t_penguin_grade
-- ----------------------------
DROP TABLE IF EXISTS `t_penguin_grade`;
CREATE TABLE `t_penguin_grade` (
  `student_id` int(8) NOT NULL COMMENT '考生主键ID',
  `contest_id` int(8) NOT NULL COMMENT '考试主键ID',
  `result` int(8) DEFAULT '0' COMMENT '最终分数',
  `auto_result` int(8) DEFAULT '0' COMMENT '电脑自动评判选择题分数',
  `manul_result` int(8) DEFAULT NULL COMMENT '人工手动评判分数',
  `manul_reason` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '主观题所给分数原因',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`student_id`,`contest_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_penguin_grade
-- ----------------------------
INSERT INTO `t_penguin_grade` VALUES ('2', '13', '1', '0', '1', '[{\"questionId\":\"12\",\"questionType\":2,\"manulReason\":\"0\"},{\"questionId\":\"13\",\"questionType\":2,\"manulReason\":\"1\"}]', '2019-04-16 11:49:53', '2019-04-17 10:18:37');
INSERT INTO `t_penguin_grade` VALUES ('3', '9', '349', '0', '349', '[{\r\n	\"questionId\": \"12\",\r\n	\"questionType\": 2,\r\n	\"manulReason\": \"2rdfe\"\r\n}, {\r\n	\"questionId\": \"13\",\r\n	\"questionType\": 2,\r\n	\"manulReason\": \"rfee\"\r\n}]', '2018-02-23 21:01:41', '2019-04-04 17:41:38');
INSERT INTO `t_penguin_grade` VALUES ('4', '9', '55', '0', '55', '[{\"questionId\":\"12\",\"questionType\":2,\"manulReason\":\"eww\"},{\"questionId\":\"13\",\"questionType\":2,\"manulReason\":\"eefr\"}]', '2019-03-26 22:24:36', '2019-03-28 15:56:10');
INSERT INTO `t_penguin_grade` VALUES ('4', '14', '22', '0', '22', '[{\"questionId\":\"22\",\"questionType\":2,\"manulReason\":\"haha\"},{\"questionId\":\"34\",\"questionType\":2,\"manulReason\":\"hahah1\"}]', '2019-04-17 16:53:31', '2019-04-17 16:55:33');
INSERT INTO `t_penguin_grade` VALUES ('4', '15', '10', '10', '0', null, '2019-04-19 15:01:20', '2019-04-19 15:01:20');

-- ----------------------------
-- Table structure for t_penguin_message
-- ----------------------------
DROP TABLE IF EXISTS `t_penguin_message`;
CREATE TABLE `t_penguin_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '消息自增编号',
  `user_id` int(11) NOT NULL COMMENT '用户编号',
  `message_title` varchar(255) NOT NULL COMMENT '消息标题',
  `message_content` varchar(255) NOT NULL COMMENT '消息内容',
  `message_url` varchar(255) DEFAULT NULL COMMENT '消息连接',
  `message_state` int(1) NOT NULL COMMENT '消息状态：0表位未读，1表示已读',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_penguin_message
-- ----------------------------
INSERT INTO `t_penguin_message` VALUES ('52', '2', '投诉成功', '你于：2019-04-17 16:44:37举报帖子《大学生有必要考研吗》的相关信息 2', 'http:www.baidu.com', '0', '2019-04-17 16:45:30', '2019-04-17 16:45:30');
INSERT INTO `t_penguin_message` VALUES ('58', '2', '账户信息更新', '你的账户信息已由管理员在 2019-04-17 21:03:01 更新，详情请查看个人主页，如有疑问请联系管理员！', 'http://www.baidu.com', '0', '2019-04-17 21:04:20', '2019-04-17 21:04:20');
INSERT INTO `t_penguin_message` VALUES ('74', '2', '账户信息更新', '你的账户信息已由管理员在 2019-04-19 12:50:45 更新，详情请查看个人主页，如有疑问请联系管理员！', 'http://www.baidu.com', '0', '2019-04-19 12:50:45', '2019-04-19 12:50:45');
INSERT INTO `t_penguin_message` VALUES ('75', '4', '账户信息更新', '你的账户信息已由管理员在 2019-04-19 12:51:23 更新，详情请查看个人主页，如有疑问请联系管理员！', 'http://www.baidu.com', '0', '2019-04-19 12:51:23', '2019-04-19 12:51:23');
INSERT INTO `t_penguin_message` VALUES ('76', '2', '账户信息更新', '你的账户信息已由管理员在 2019-04-20 10:55:21 更新，详情请查看个人主页，如有疑问请联系管理员！', 'http://www.baidu.com', '0', '2019-04-20 10:56:44', '2019-04-20 10:56:44');

-- ----------------------------
-- Table structure for t_penguin_notice
-- ----------------------------
DROP TABLE IF EXISTS `t_penguin_notice`;
CREATE TABLE `t_penguin_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `notice_type` int(1) NOT NULL COMMENT '公告类型，0表示系统公告，1表示用户违禁公告',
  `notice_content` varchar(255) NOT NULL COMMENT '公告内容',
  `notice_url` varchar(255) DEFAULT NULL COMMENT '公告连接',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_penguin_notice
-- ----------------------------
INSERT INTO `t_penguin_notice` VALUES ('20', '1', '用户：曾大熙 于2019-04-04 20:25:12在帖子《金山WPS服务端开发工程师面试经历》的评论中有违规言行，以封禁', 'http:www.baidu.com', '2019-04-14 22:48:32', '2019-04-14 22:48:32');
INSERT INTO `t_penguin_notice` VALUES ('22', '0', '公告：系统将于15分钟后进行维护更新', 'http:www.baidu.com', '2019-04-15 12:36:08', '2019-04-15 12:36:08');
INSERT INTO `t_penguin_notice` VALUES ('23', '1', '用户：方同学 于2019-04-17 16:44:07在帖子《大学生有必要考研吗》的评论中有违规言行，以封禁', 'http:www.baidu.com', '2019-04-17 16:45:30', '2019-04-17 16:45:30');
INSERT INTO `t_penguin_notice` VALUES ('24', '0', '公告：网站将于下午5点开始进行维护，期间可能引起网站不稳定，属于正常现象，请各位玩家不要紧张！', 'http:www.baidu.com', '2019-04-19 13:06:50', '2019-04-19 13:06:50');

-- ----------------------------
-- Table structure for t_penguin_post
-- ----------------------------
DROP TABLE IF EXISTS `t_penguin_post`;
CREATE TABLE `t_penguin_post` (
  `id` int(8) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `author_id` int(8) NOT NULL COMMENT '作者ID',
  `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标题',
  `html_content` longtext COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'html源代码',
  `text_content` longtext COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文本内容',
  `type` int(1) NOT NULL COMMENT '文章类型：0笔试面经，1我要提问，2技术交流，3产品运营，4留学生，5职业发展，6招聘信息，7资源分享，8猿生活',
  `last_reply_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后一次回复时间',
  `reply_num` int(8) NOT NULL DEFAULT '0' COMMENT '回复数',
  `deleted` int(1) DEFAULT '0' COMMENT '是否删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发帖时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '帖子最后编辑时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_penguin_post
-- ----------------------------
INSERT INTO `t_penguin_post` VALUES ('2', '2', '人人研发工程师实习生面试经历（Java）', '<h3>一面50分钟(2017.3.27)</h3><ol><li>聊了自己博客（聊天）</li><li>聊了自己做的一些小项目（聊天）</li><li>socket编程(自己项目上的坑)</li><li>int i = 1和Integer i = new Integer(1)区别</li><li>听说过轮询吗？</li><li>算法题：全排列</li><li>数据库查询语句编写</li><li>http状态码：200,202,301,302,304,404,500等</li><li>常用的集合框架(LinkedList, ArrayList, HashMap等等)</li><li>哈希表处理冲突的方法</li><li>HashMap的实现原理</li><li>最近看了什么书</li></ol><h3>二面40分钟(2017.4.10)</h3><ol><li>说说写一个登录需要注意什么问题（聊天）</li><li>说说微博失效问题(重新登录)怎么实现（聊天）</li><li>http和https区别</li><li>sliding window（滑动窗口）</li><li>算法题：找出字符串的最长不重复连续子串的长度（输入：abcabcab,输出3, 输入：pwwkew，输出：3）</li><li>LinkedList与ArrayList区别</li><li>HashMap, HashTable, CurrentHashMap区别</li><li>怎么实现ArrayList安全访问(聊了synchronized实现等待/通知机制)</li><li>有什么问题想问</li></ol><h3>面试感觉：两轮的面试官都挺好的，难度也不大，主要还是聊了很多</h3><h3>收到电话通知(2017.4.13)，选了武汉。。。</h3>', '一面50分钟(2017.3.27)聊了自己博客（聊天）聊了自己做的一些小项目（聊天）socket编程(自己项目上的坑)int i = 1和Integer i = new Integer(1)区别听说过轮询吗？算法题：全排列数据库查询语句编写http状态码：200,202,301,302,304,404,500等常用的集合框架(LinkedList, ArrayList, HashMap等等)哈希表处理冲突的方法HashMap的实现原理最近看了什么书二面40分钟(2017.4.10)说说写一个登录需要注意什么问题（聊天）说说微博失效问题(重新登录)怎么实现（聊天）http和https区别sliding window（滑动窗口）算法题：找出字符串的最长不重复连续子串的长度（输入：abcabcab,输出3, 输入：pwwkew，输出：3）LinkedList与ArrayList区别HashMap, HashTable, CurrentHashMap区别怎么实现ArrayList安全访问(聊了synchronized实现等待/通知机制)有什么问题想问面试感觉：两轮的面试官都挺好的，难度也不大，主要还是聊了很多收到电话通知(2017.4.13)，选了武汉。。。', '1', '2019-04-17 16:28:05', '6', '0', '2018-03-02 21:05:50', '2019-04-17 16:28:04');
INSERT INTO `t_penguin_post` VALUES ('4', '2', '金山WPS服务端开发工程师面试经历', '<h3><strong>一面</strong></h3><ol><li>写自己知道的Linux命令；</li><li>你对数据库进行查询，发现查询很慢，对代码排查，代码没问题，你怎么对数据库进行排查；（聊了索引）</li><li>给你一个数据库，数据库里面数据很大（TB级），你怎么解决查询慢（性能优化）的问题；（分区技术）</li><li>分区的类型；</li><li>加密算法(md5、base64等等)</li><li>你用的是多进程还是多线程；（多进程和多线程的区别）</li><li>socket编程，怎么实现一个多人聊天室；（怎么设计、怎么实现）</li><li>http和https区别；(https = http + ssl)</li><li>查找算法（顺序查找、二分查找、二叉排序树、平衡二叉树、哈希法等等）</li></ol><h3><strong>二面</strong></h3><ol><li>自我介绍（个人擅长的领域、个人突出的地方）</li><li>（笔试里面的一道题）数据库里有10000000条用户信息，需要给每位用户发送信息（必须发送成功），要求节省内存（主键索引、分区技术、异步处理）</li><li>HTTP请求方法（GET、HEAD、POST、PUT、DELETE、CONNECT、OPTIONS、TRACE）</li><li>GET与POST请求区别（根据笔试题的回答提问），POST请求运用，GET幂等的理解，GET请求URL显示，GET请求URL中为什么有“？”（例如：<a target=\"_blank\">https://www.nowcoder.com/discuss/post?type=2），访问“http://www.docer.com/?from=nav</a>&nbsp;_wps”后怎么显示，也就是空格的显示（出现“<a target=\"_blank\">http://www.docer.com/?from=nav%20_wps”）</a></li><li>说说RESTful架构</li><li>说说字典树</li><li>平时怎么写数据库的模糊查询（由字典树扯到模糊查询，前缀查询，例如“abc%”，还是索引策略的问题）</li><li>面向对象编程的理解</li><li>平时怎么写面向对象编程（聊了面向接口编程）</li><li>socket编程，怎么实现信息传输，还是多人聊天室的问题（项目的坑）</li><li>MySQL事务隔离等级，MySQL默认的事务隔离等级</li><li>MySQL事务特性<h3><strong>PS：金山WPS的服务端开发主要是GO语言嗯嗯呢</strong></h3></li></ol>', '一面写自己知道的Linux命令；你对数据库进行查询，发现查询很慢，对代码排查，代码没问题，你怎么对数据库进行排查；（聊了索引）给你一个数据库，数据库里面数据很大（TB级），你怎么解决查询慢（性能优化）的问题；（分区技术）分区的类型；加密算法(md5、base64等等)你用的是多进程还是多线程；（多进程和多线程的区别）socket编程，怎么实现一个多人聊天室；（怎么设计、怎么实现）http和https区别；(https = http + ssl)查找算法（顺序查找、二分查找、二叉排序树、平衡二叉树、哈希法等等）二面自我介绍（个人擅长的领域、个人突出的地方）（笔试里面的一道题）数据库里有10000000条用户信息，需要给每位用户发送信息（必须发送成功），要求节省内存（主键索引、分区技术、异步处理）HTTP请求方法（GET、HEAD、POST、PUT、DELETE、CONNECT、OPTIONS、TRACE）GET与POST请求区别（根据笔试题的回答提问），POST请求运用，GET幂等的理解，GET请求URL显示，GET请求URL中为什么有“？”（例如：https://www.nowcoder.com/discuss/post?type=2），访问“http://www.docer.com/?from=nav&nbsp;_wps”后怎么显示，也就是空格的显示（出现“http://www.docer.com/?from=nav%20_wps”）说说RESTful架构说说字典树平时怎么写数据库的模糊查询（由字典树扯到模糊查询，前缀查询，例如“abc%”，还是索引策略的问题）面向对象编程的理解平时怎么写面向对象编程（聊了面向接口编程）socket编程，怎么实现信息传输，还是多人聊天室的问题（项目的坑）MySQL事务隔离等级，MySQL默认的事务隔离等级MySQL事务特性PS：金山WPS的服务端开发主要是GO语言嗯嗯呢', '0', '2019-04-16 23:02:51', '1', '0', '2018-03-03 10:19:29', '2019-04-16 23:02:51');
INSERT INTO `t_penguin_post` VALUES ('8', '4', '大学生有必要考研吗', '<p>一：有必要</p><p>研究生可以增强自己的知识见闻，多花时间在学习上，满足自己内心的需求</p><p>二：没有必要</p><p>俗话说：读万卷书，不如行万里路，读再多的书，以后出了社会也不一定用的到</p><p><br></p><p>专家：各有利弊吧，主要是跟随自己的内心，做到无愧于心就好。</p><p><br></p><p>读者认为呢？</p><p>还好吧</p><p><br></p><p><br></p>', '一：有必要研究生可以增强自己的知识见闻，多花时间在学习上，满足自己内心的需求二：没有必要俗话说：读万卷书，不如行万里路，读再多的书，以后出了社会也不一定用的到专家：各有利弊吧，主要是跟随自己的内心，做到无愧于心就好。读者认为呢？还好吧', '1', '2019-04-17 16:44:07', '10', '0', '2019-04-05 15:09:47', '2019-04-17 16:44:07');
INSERT INTO `t_penguin_post` VALUES ('9', '2', 'only you  are more your limitation and your dream will change being true ', '<p>Today is best day,i will go abroad for traveling with my lover who is very beauity,if you are more than your limitation &nbsp;for your destination, you will get success ,it is not my saying,is a famous star named MAYUN,i always grate go head for my destionation,ultimately,my dream was changed being true,i am so happy.</p>', 'Today is best day,i will go abroad for traveling with my lover who is very beauity,if you are more than your limitation &nbsp;for your destination, you will get success ,it is not my saying,is a famous star named MAYUN,i always grate go head for my destionation,ultimately,my dream was changed being true,i am so happy.', '7', '2019-04-16 23:02:47', '0', '0', '2019-04-15 16:06:27', '2019-04-16 23:02:47');
INSERT INTO `t_penguin_post` VALUES ('10', '2', '程序员的生活是怎样的？', '<p>每天加班，夜夜加班，日日加班，时时加班</p>', '每天加班，夜夜加班，日日加班，时时加班', '8', '2019-04-15 17:31:54', '1', '1', '2019-04-15 16:26:41', '2019-04-15 17:31:54');
INSERT INTO `t_penguin_post` VALUES ('20', '2', '12312', '<p>321312</p>', '321312', '4', '2019-04-16 23:07:16', '2', '1', '2019-04-16 23:05:57', '2019-04-16 23:07:16');
INSERT INTO `t_penguin_post` VALUES ('21', '2', 'qwe', '<p>dadfew</p>', 'dadfew', '3', '2019-04-17 16:10:26', '3', '1', '2019-04-16 23:54:10', '2019-04-17 16:10:26');
INSERT INTO `t_penguin_post` VALUES ('22', '2', '12', '<p>3r3r</p>', '3r3r', '4', '2019-04-17 16:11:47', '0', '1', '2019-04-17 16:11:29', '2019-04-17 16:11:47');
INSERT INTO `t_penguin_post` VALUES ('23', '2', ' 如果，我们只是过客', '\n<p>岁月的匆匆流逝，一如昨日烟云。望眼空怅奈何去，付诸流水已枉然。</p>\n<p>不知该从何记忆，也不知那个季节在哪里？其实记忆已经涂满了思绪，浩渺茫茫，漫无边际。岁月的年轮在日复一年的更替着，可风花雪月的红尘里催老了谁的容颜？</p>\n<p>季节的转换让我干涸的枯笔蘸着泪眼的潮水，和着那昨日未曾干涸的滴血，祭奠着我随风已逝的流年岁月，为你抒写着记忆里苦涩的念白。</p>\n<p>昨日，记忆里曾是绿柳下清溪边，依然回荡着呓语在梦乡里的缠绕。今夕，回忆已是如此酸楚和艰辛，月色里踽踽独行已找不到来时的归路。</p>\n<p>如果，我们只是过客。不想心痛，为什么会有离愁沉重的怅然。如果，我们只是过客。不想流泪，为什么会有雨季淋漓的潮汐。如果，我们只是过客。不想眷恋，为什么会有依依难舍的情怀。如果，我们只是过客。不想期待，为什么会有风清月瘦的花影。</p>\n<p>那明澈的记忆，被落寞的夜色弥漫成一片苍白，在白天与黑夜的轮回中，一次次被颠覆成斑驳陆离的碎片。</p>\n<p>我那还带有余温的泪滴，被你悲凉的目光击碎成万千飞絮，而只愿潜伏于你暮色的冥冥中，哪怕这是一份空幻的期待。</p>\n<p>难道注定只是过客，那满山殷红的杜鹃花如今已是枯萎零落，而不知来年的季节，花开是否为了等待，还是早已错过这个时节。</p>\n<p>如果，我们只是过客。我愿再轮回一世邂逅，铸就三生等待，换取五百年的擦肩而过，修来千年的后世因缘。</p>\n<p>如果，我们只是过客……</p>\n\n', '\n岁月的匆匆流逝，一如昨日烟云。望眼空怅奈何去，付诸流水已枉然。\n不知该从何记忆，也不知那个季节在哪里？其实记忆已经涂满了思绪，浩渺茫茫，漫无边际。岁月的年轮在日复一年的更替着，可风花雪月的红尘里催老了谁的容颜？\n季节的转换让我干涸的枯笔蘸着泪眼的潮水，和着那昨日未曾干涸的滴血，祭奠着我随风已逝的流年岁月，为你抒写着记忆里苦涩的念白。\n昨日，记忆里曾是绿柳下清溪边，依然回荡着呓语在梦乡里的缠绕。今夕，回忆已是如此酸楚和艰辛，月色里踽踽独行已找不到来时的归路。\n如果，我们只是过客。不想心痛，为什么会有离愁沉重的怅然。如果，我们只是过客。不想流泪，为什么会有雨季淋漓的潮汐。如果，我们只是过客。不想眷恋，为什么会有依依难舍的情怀。如果，我们只是过客。不想期待，为什么会有风清月瘦的花影。\n那明澈的记忆，被落寞的夜色弥漫成一片苍白，在白天与黑夜的轮回中，一次次被颠覆成斑驳陆离的碎片。\n我那还带有余温的泪滴，被你悲凉的目光击碎成万千飞絮，而只愿潜伏于你暮色的冥冥中，哪怕这是一份空幻的期待。\n难道注定只是过客，那满山殷红的杜鹃花如今已是枯萎零落，而不知来年的季节，花开是否为了等待，还是早已错过这个时节。\n如果，我们只是过客。我愿再轮回一世邂逅，铸就三生等待，换取五百年的擦肩而过，修来千年的后世因缘。\n如果，我们只是过客……\n\n', '8', '2019-04-19 13:04:56', '0', '0', '2019-04-19 13:04:56', '2019-04-19 13:04:56');

-- ----------------------------
-- Table structure for t_penguin_question
-- ----------------------------
DROP TABLE IF EXISTS `t_penguin_question`;
CREATE TABLE `t_penguin_question` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '题目标题',
  `content` text COLLATE utf8mb4_unicode_ci COMMENT '题目内容',
  `question_type` int(8) DEFAULT NULL COMMENT '题目类型,0表示单项选择题,1表示多项选择题,2表示问答题,3表示编程题',
  `option_a` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '选项A',
  `option_b` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '选项B',
  `option_c` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '选项C',
  `option_d` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '选项D',
  `answer` text COLLATE utf8mb4_unicode_ci COMMENT '答案',
  `parse` text COLLATE utf8mb4_unicode_ci COMMENT '答案解析',
  `subject_id` int(8) DEFAULT NULL COMMENT '学科ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_penguin_question
-- ----------------------------
INSERT INTO `t_penguin_question` VALUES ('7', '在关系数据库设计中，设计关系模式（二维表）是数据库设计中哪个阶段的任务。（）', '在关系数据库设计中，设计关系模式（二维表）是数据库设计中哪个阶段的任务。（）', '0', '逻辑设计阶段', '概念设计阶段', '物理设计阶段', '需求分析阶段', 'A', '逻辑设计的目的是从概念模型导出特定的DBMS可以处理的数据库的逻辑结构(数据库的模式和外模式)。在关系数据库设计中，设计关系模式是在数据库的逻辑设计阶段。', '7', '2018-02-20 18:00:48', '2019-04-17 16:51:58');
INSERT INTO `t_penguin_question` VALUES ('8', '（） 是存储在计算机内有结构的数据的集合。', '（） 是存储在计算机内有结构的数据的集合。', '0', '数据库系统', '数据库', '数据库管理系统', '数据结构', 'B', '1、数据库系统（Database System）是由数据库及其管理软件组成的系统。\n\n2、数据库(Database)是按照数据结构来组织、存储和管理数据的建立在计算机存储设备上的仓库。\n\n3、数据库管理系统(Database Management System)是一种操纵和管理数据库的大型软件，用于建立、使用和维护数据库，简称DBMS。\n\n4、数据结构是计算机存储、组织数据的方式。', '7', '2018-02-20 18:27:38', '2019-04-19 14:46:36');
INSERT INTO `t_penguin_question` VALUES ('10', '实体是信息世界中的术语，与之对应的数据库术语为。 （）', '实体是信息世界中的术语，与之对应的数据库术语为。 （）', '0', '文件', '数据库', '字段', '记录', 'D', '略', '7', '2018-02-20 18:32:05', '2019-04-17 16:52:05');
INSERT INTO `t_penguin_question` VALUES ('11', '表 test1 中包含两列： c1 为整型， c2 为 8 位长的字符串类型，使用如下语句创建视图......', '表 test1 中包含两列： c1 为整型， c2 为 8 位长的字符串类型，使用如下语句创建视图：\n\nCREATE VIEW v1 AS SELECT c1,c2 FROM test1 WHERE c1>30 WITH CHECK OPTION\n\n以下语句能够成功执行的有几条？ （ ）\n\nINSERT INTO v1 VALUES(1, \' 赵六 \')\n\nINSERT INTO v1 VALUES(101, \' 李四 \')\n\nINSERT INTO t1 VALUES(20, \' 王五 \')', '0', '0', '1', '2', '3', 'C', '略', '7', '2018-02-20 18:36:14', '2019-04-19 14:46:47');
INSERT INTO `t_penguin_question` VALUES ('12', '简述关系模型的三类完整性约束。', '简述关系模型的三类完整性约束。', '2', '', '', '', '', '1. 实体完整性约束 要求候选码非空且唯一。\n\n2. 参照完整性约束 要求外码要么取空值要么所取的值在对应的候选码中出现。\n\n3. 用户自定义完整性。包括非空约束，唯一约束，检查约束，缺省值约束等。', '1. 实体完整性约束 要求候选码非空且唯一。\n\n2. 参照完整性约束 要求外码要么取空值要么所取的值在对应的候选码中出现。\n\n3. 用户自定义完整性。包括非空约束，唯一约束，检查约束，缺省值约束等。', '7', '2018-02-20 18:38:10', '2019-04-19 14:46:59');
INSERT INTO `t_penguin_question` VALUES ('13', '如果对数据库的并发性不加以任何控制，可能造成哪些不良现象？怎样控制才能防止这些现象的产生？', '如果对数据库的并发性不加以任何控制，可能造成哪些不良现象？怎样控制才能防止这些现象的产生？', '2', '', '', '', '', '1. 丢失修改\n\n2. 读“脏”数据\n\n3. 不可重复读\n\n通过基于锁的协议实现对事务并发控制。', '1. 丢失修改\n\n2. 读“脏”数据\n\n3. 不可重复读\n\n通过基于锁的协议实现对事务并发控制。', '7', '2018-02-20 18:47:11', '2019-04-19 14:47:08');
INSERT INTO `t_penguin_question` VALUES ('22', '你这人真的坏得很', 'mdzz是什么意思', '2', '', '', '', '', '妈的智障OK', '见拼音首字母', '7', '2019-03-21 10:31:56', '2019-04-17 16:52:10');
INSERT INTO `t_penguin_question` VALUES ('24', '你是猪吗？', '没错，你是猪', '2', '', '', '', '', '确实是猪', '很明显', '1', '2019-03-21 11:16:37', '2019-03-21 11:16:37');
INSERT INTO `t_penguin_question` VALUES ('25', '你是猪吗111', '没错，你是猪', '2', '', '', '', '', '12232', '3234', '1', '2019-03-21 12:13:11', '2019-03-29 16:36:11');
INSERT INTO `t_penguin_question` VALUES ('34', 'mdzz是什么意思', 'mdzz是什么意思', '2', '', '', '', '', '妈的智障', '见拼音首字母', '7', '2019-04-16 14:50:56', '2019-04-17 16:52:14');
INSERT INTO `t_penguin_question` VALUES ('35', '王俊霖是傻逼吗？', '王俊霖这个人是傻逼吗？', '0', '是', '不是', '既是又不是', '一定程度上是', 'A', '很明显呀', '1', '2019-04-16 14:50:56', '2019-04-16 14:50:56');
INSERT INTO `t_penguin_question` VALUES ('36', 'mdzz是什么意思', 'mdzz是什么意思', '2', '', '', '', '', '妈的智障', '见拼音首字母', '1', '2019-04-16 14:52:01', '2019-04-16 14:52:01');
INSERT INTO `t_penguin_question` VALUES ('37', '王俊霖是傻逼吗？', '王俊霖这个人是傻逼吗？', '0', '是', '不是', '既是又不是', '一定程度上是', 'A', '很明显呀', '1', '2019-04-16 14:52:01', '2019-04-16 14:52:01');
INSERT INTO `t_penguin_question` VALUES ('38', 'mdzz是什么意思', 'mdzz是什么意思', '2', '', '', '', '', '妈的智障', '见拼音首字母', '1', '2019-04-16 20:45:21', '2019-04-16 20:45:21');
INSERT INTO `t_penguin_question` VALUES ('39', '王俊霖是傻逼吗？', '王俊霖这个人是傻逼吗？', '0', '是', '不是', '既是又不是', '一定程度上是', 'A', '很明显呀', '1', '2019-04-16 20:45:21', '2019-04-16 20:45:21');
INSERT INTO `t_penguin_question` VALUES ('40', '11', '21', '0', '12', '12', '12', '12', '', '2112', '7', '2019-04-16 20:45:52', '2019-04-16 20:45:52');
INSERT INTO `t_penguin_question` VALUES ('41', '11', '21', '2', '', '', '', '', '1eeea', 'qsqwd', '6', '2019-04-16 20:49:46', '2019-04-16 20:49:46');
INSERT INTO `t_penguin_question` VALUES ('42', '33', '123', '1', '', '', '', '', '32', '12', '1', '2019-04-16 20:53:37', '2019-04-16 20:53:37');
INSERT INTO `t_penguin_question` VALUES ('43', '324', '32', '0', '23', '342', '432', '423', '', 'A', '12', '2019-04-16 20:54:28', '2019-04-16 20:54:28');
INSERT INTO `t_penguin_question` VALUES ('44', 'mdzz是什么意思', 'mdzz是什么意思', '2', '', '', '', '', '妈的智障', '见拼音首字母', '1', '2019-04-16 20:55:06', '2019-04-16 20:55:06');
INSERT INTO `t_penguin_question` VALUES ('45', '王俊霖是傻逼吗？', '王俊霖这个人是傻逼吗？', '0', '是', '不是', '既是又不是', '一定程度上是', 'A', '很明显呀', '1', '2019-04-16 20:55:06', '2019-04-16 20:55:06');
INSERT INTO `t_penguin_question` VALUES ('46', '11', '11', '0', '1', '1', '1', '1', '', '1', '4', '2019-04-16 22:58:37', '2019-04-16 22:58:37');
INSERT INTO `t_penguin_question` VALUES ('47', 'mdzz是什么意思', 'mdzz是什么意思', '2', '', '', '', '', '妈的智障', '见拼音首字母', '1', '2019-04-16 22:59:04', '2019-04-16 22:59:04');
INSERT INTO `t_penguin_question` VALUES ('48', '王俊霖是傻逼吗？', '王俊霖这个人是傻逼吗？', '0', '是', '不是', '既是又不是', '一定程度上是', 'A', '很明显呀', '1', '2019-04-16 22:59:04', '2019-04-16 22:59:04');

-- ----------------------------
-- Table structure for t_penguin_reply
-- ----------------------------
DROP TABLE IF EXISTS `t_penguin_reply`;
CREATE TABLE `t_penguin_reply` (
  `id` int(8) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` int(8) DEFAULT NULL COMMENT '用户id',
  `atuser_id` int(8) DEFAULT NULL COMMENT '被回复用户id',
  `post_id` int(8) DEFAULT NULL COMMENT '帖子id',
  `comment_id` int(8) DEFAULT NULL COMMENT '评论id',
  `content` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '内容',
  `deleted` int(1) DEFAULT '0' COMMENT '是否删除',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_penguin_reply
-- ----------------------------
INSERT INTO `t_penguin_reply` VALUES ('4', '4', '0', '2', '6', '呵呵，你是来搞笑的哇', '1', '2019-04-03 12:35:57');
INSERT INTO `t_penguin_reply` VALUES ('15', '4', '2', '2', '6', '呵呵哒', '1', '2019-04-04 22:04:30');
INSERT INTO `t_penguin_reply` VALUES ('33', '2', '0', '21', '30', '1332', '1', '2019-04-17 16:02:29');
INSERT INTO `t_penguin_reply` VALUES ('34', '2', '0', '21', '32', '4让日', '1', '2019-04-17 16:09:55');
INSERT INTO `t_penguin_reply` VALUES ('35', '4', '0', '2', '33', '111', '0', '2019-04-17 16:23:41');
INSERT INTO `t_penguin_reply` VALUES ('36', '4', '0', '2', '34', '呵呵呵哒', '1', '2019-04-17 16:27:45');

-- ----------------------------
-- Table structure for t_penguin_subject
-- ----------------------------
DROP TABLE IF EXISTS `t_penguin_subject`;
CREATE TABLE `t_penguin_subject` (
  `id` int(8) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '学科名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `question_num` int(11) DEFAULT '0' COMMENT '题目数量',
  `img_url` varchar(63) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图片url',
  `state` int(4) DEFAULT '0' COMMENT '课程状态,0表示正常,1表示弃用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_penguin_subject
-- ----------------------------
INSERT INTO `t_penguin_subject` VALUES ('1', '计算机组成原理', '2018-01-18 21:56:29', '2019-04-16 22:59:04', '5', 'problemset_default.jpg', '0');
INSERT INTO `t_penguin_subject` VALUES ('2', 'C语言程序设计', '2018-01-18 22:32:54', '2018-01-18 22:32:54', '0', 'problemset_c.jpg', '0');
INSERT INTO `t_penguin_subject` VALUES ('3', 'Java语言程序设计', '2018-01-18 22:32:54', '2018-01-18 22:32:54', '0', 'problemset_java.jpg', '0');
INSERT INTO `t_penguin_subject` VALUES ('4', 'C++语言程序设计', '2018-01-18 22:32:54', '2019-04-16 22:58:37', '1', 'problemset_c++.jpg', '0');
INSERT INTO `t_penguin_subject` VALUES ('5', 'Python语言程序设计', '2018-01-18 22:32:54', '2018-01-18 22:32:54', '0', 'problemset_python.jpg', '0');
INSERT INTO `t_penguin_subject` VALUES ('6', '数据结构与算法', '2018-01-18 22:32:54', '2019-04-16 20:52:57', '0', 'problemset_datastructures.jpg', '0');
INSERT INTO `t_penguin_subject` VALUES ('7', '软件测试', '2018-01-18 22:32:54', '2019-04-16 20:52:59', '0', 'problemset_softwareTest.jpg', '0');
INSERT INTO `t_penguin_subject` VALUES ('8', '数据库概论', '2018-01-18 22:32:54', '2018-01-18 22:32:54', '0', 'problemset_database.jpg', '0');
INSERT INTO `t_penguin_subject` VALUES ('9', '大学计算机', '2018-01-26 15:59:21', '2018-01-26 15:59:37', '0', 'problemset_computer.jpg', '0');
INSERT INTO `t_penguin_subject` VALUES ('10', '人工智能原理', '2018-01-26 16:01:29', '2018-01-26 16:01:29', '0', 'problemset_ai.png', '0');
INSERT INTO `t_penguin_subject` VALUES ('11', '大数据算法', '2018-01-26 16:02:40', '2018-01-26 16:02:40', '0', 'problemset_bigdata.jpg', '0');
INSERT INTO `t_penguin_subject` VALUES ('12', '物联网概论', '2018-01-26 16:05:06', '2019-04-16 20:54:28', '1', 'problemset_internetofthings.jpg', '0');
INSERT INTO `t_penguin_subject` VALUES ('13', '计算机操作系统', '2018-01-26 16:06:13', '2018-01-26 16:06:13', '0', 'problemset_os.jpg', '0');
INSERT INTO `t_penguin_subject` VALUES ('14', '计算机网络', '2018-01-26 16:07:25', '2018-01-26 16:08:35', '0', 'problemset_networld.jpg', '0');
INSERT INTO `t_penguin_subject` VALUES ('15', '算法设计与分析入门', '2018-01-26 16:09:47', '2018-01-26 16:09:47', '0', 'problemset_algorithm.jpg', '0');
INSERT INTO `t_penguin_subject` VALUES ('16', '计算机图形学', '2018-01-26 16:10:17', '2018-01-26 16:10:17', '0', 'problemset_graphic.jpg', '0');
INSERT INTO `t_penguin_subject` VALUES ('17', '数字电路与系统', '2018-01-26 16:26:31', '2018-01-26 16:26:31', '0', 'problemset_digitalcircuits.jpg', '0');
INSERT INTO `t_penguin_subject` VALUES ('19', '线性代数', '2018-03-04 20:40:10', '2018-03-04 20:40:10', '0', 'problemset_default.jpg', '0');
INSERT INTO `t_penguin_subject` VALUES ('20', 'C语言程序设计', '2018-04-13 19:33:14', '2018-04-13 19:33:14', '0', 'problemset_c.jpg', '0');
INSERT INTO `t_penguin_subject` VALUES ('21', 'Java语言程序设计', '2018-04-13 19:33:14', '2018-04-13 19:33:14', '0', 'problemset_java.jpg', '0');
INSERT INTO `t_penguin_subject` VALUES ('22', 'C++语言程序设计', '2018-04-13 19:33:14', '2018-04-13 19:33:14', '0', 'problemset_c++.jpg', '0');
INSERT INTO `t_penguin_subject` VALUES ('23', 'Python语言程序设计', '2018-04-13 19:33:14', '2018-04-13 19:33:14', '0', 'problemset_python.jpg', '0');
INSERT INTO `t_penguin_subject` VALUES ('24', '数据结构与算法', '2018-04-13 19:33:14', '2018-04-13 19:33:14', '0', 'problemset_datastructures.jpg', '0');
INSERT INTO `t_penguin_subject` VALUES ('25', '数据结构与算法', '2018-04-13 19:33:14', '2018-04-13 19:33:14', '0', 'problemset_datastructures.jpg', '0');
INSERT INTO `t_penguin_subject` VALUES ('26', '数据库概论', '2018-04-13 19:33:14', '2018-04-13 19:33:14', '0', 'problemset_database.jpg', '0');
