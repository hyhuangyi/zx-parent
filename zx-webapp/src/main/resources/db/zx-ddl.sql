/*
Navicat MySQL Data Transfer

Source Server         : aliyun-zx
Source Server Version : 50728
Source Host           : 47.110.13.117:3306
Source Database       : zx

Target Server Type    : MYSQL
Target Server Version : 50728
File Encoding         : 65001

Date: 2019-11-20 10:53:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for auth_menu
-- ----------------------------
DROP TABLE IF EXISTS `auth_menu`;
CREATE TABLE `auth_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `parent_id` int(11) DEFAULT NULL COMMENT '上级菜单ID',
  `menu_url` varchar(100) DEFAULT NULL COMMENT '菜单URL',
  `type` int(11) DEFAULT '0' COMMENT '0菜单 1按钮',
  `icon` varchar(255) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL COMMENT '排序号',
  `is_del` int(11) DEFAULT '0' COMMENT '1 删除 0 未删除',
  `permission` varchar(50) DEFAULT NULL COMMENT '权限标识',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='菜单表';


-- ----------------------------
-- Table structure for auth_role
-- ----------------------------
DROP TABLE IF EXISTS `auth_role`;
CREATE TABLE `auth_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_code` varchar(50) DEFAULT NULL COMMENT '角色code',
  `role_name` varchar(20) DEFAULT NULL COMMENT '角色名称',
  `is_del` int(11) DEFAULT '0' COMMENT '删除 0:不删除  1:删除',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- ----------------------------
-- Table structure for auth_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `auth_role_menu`;
CREATE TABLE `auth_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='角色菜单表';

-- ----------------------------
-- Table structure for auth_user_role
-- ----------------------------
DROP TABLE IF EXISTS `auth_user_role`;
CREATE TABLE `auth_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '关系id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色id',
  `is_del` bigint(20) DEFAULT '0' COMMENT '删除 0:不删除  1:删除',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COMMENT='用户和角色关系表';

-- ----------------------------
-- Table structure for cd_city
-- ----------------------------
DROP TABLE IF EXISTS `cd_city`;
CREATE TABLE `cd_city` (
  `code` varchar(100) NOT NULL COMMENT 'code',
  `full_name` varchar(100) DEFAULT NULL COMMENT '全名',
  `short_name` varchar(100) DEFAULT NULL COMMENT '短名',
  `py` varchar(100) DEFAULT NULL COMMENT '全拼',
  `prov_code` varchar(100) DEFAULT NULL COMMENT '省份code',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user` int(11) DEFAULT '0' COMMENT '更新人',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='城市表';

-- ----------------------------
-- Table structure for sys_execute_time_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_execute_time_log`;
CREATE TABLE `sys_execute_time_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `execute_time` int(11) DEFAULT NULL COMMENT '执行时间',
  `execute_method` varchar(255) DEFAULT NULL COMMENT '执行方法',
  `create_date` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `operate_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='执行时间表';

-- ----------------------------
-- Table structure for sys_tree_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_tree_dict`;
CREATE TABLE `sys_tree_dict` (
  `dd_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dd_item` varchar(50) DEFAULT NULL COMMENT '字典项,一组字典值的唯一标识',
  `dd_text` varchar(500) DEFAULT NULL COMMENT '字典展示文本',
  `dd_value` varchar(50) DEFAULT NULL COMMENT '字典值',
  `dd_index` int(11) DEFAULT '0' COMMENT '排序字段',
  `parent_value` varchar(50) DEFAULT NULL COMMENT '父级字典值',
  `remark` varchar(20) DEFAULT '0' COMMENT '备注',
  `update_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_del` bit(1) DEFAULT b'0' COMMENT '删除标识',
  PRIMARY KEY (`dd_id`),
  KEY `reportIndex` (`dd_item`,`dd_value`,`is_del`)
) ENGINE=InnoDB AUTO_INCREMENT=1040 DEFAULT CHARSET=utf8 COMMENT='树状字典表';

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `phone` varchar(13) DEFAULT NULL COMMENT '电话',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `status` int(11) DEFAULT '0' COMMENT '状态 0启用 1禁用',
  `create_date` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='系统用户表';

-- ----------------------------
-- Table structure for sys_operate_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_operate_log`;
CREATE TABLE `sys_operate_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `operate_ip` varchar(50) DEFAULT NULL COMMENT 'ip',
  `operate_url` varchar(255) DEFAULT NULL COMMENT 'api url',
  `operate_user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `operate_user_name` varchar(50) DEFAULT NULL COMMENT '用户姓名',
  `operate_method` varchar(255) DEFAULT NULL COMMENT '方法',
  `operate_action` varchar(255) DEFAULT NULL COMMENT '操作内容',
  `operate_module` varchar(50) DEFAULT NULL COMMENT '模块',
  `operate_time` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='操作日志';

-- ----------------------------
-- Table structure for weibo
-- ----------------------------
DROP TABLE IF EXISTS `weibo`;
CREATE TABLE `weibo` (
  `id` varchar(50) NOT NULL COMMENT '微博id',
  `bid` varchar(30) DEFAULT NULL COMMENT '详情id',
  `user_id` varchar(20) DEFAULT NULL COMMENT '微博uid',
  `screen_name` varchar(30) DEFAULT NULL COMMENT '微博名称',
  `text` varchar(5000) DEFAULT NULL COMMENT '微博类容',
  `topics` varchar(200) DEFAULT NULL COMMENT '话题',
  `pics` varchar(3000) DEFAULT NULL COMMENT '图片地址',
  `created_at` varchar(30) DEFAULT NULL COMMENT '发布时间',
  `source` varchar(30) DEFAULT NULL COMMENT '发布来源',
  `attitudes_count` int(11) DEFAULT NULL COMMENT '点赞数',
  `comments_count` int(11) DEFAULT NULL COMMENT '评论数',
  `reposts_count` int(11) DEFAULT NULL COMMENT '转发数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微博话题搜索结果表';

-- ----------------------------
-- Table structure for fund
-- ----------------------------
DROP TABLE IF EXISTS `fund`;
CREATE TABLE `fund` (
  `code` varchar(20) NOT NULL COMMENT '基金代码',
  `short_py` varchar(30) NOT NULL COMMENT '短拼',
  `full_py` varchar(80) NOT NULL COMMENT '全拼',
  `name` varchar(40) NOT NULL COMMENT '基金名称',
  `type` varchar(20) NOT NULL COMMENT '基金类型',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='基金表';

-- ----------------------------
-- Table structure for fund_own
-- ----------------------------
CREATE TABLE `fund_own` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(50) NOT NULL COMMENT '基金代码',
  `name` varchar(50) DEFAULT NULL COMMENT '基金名称',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `hold_num` varchar(50) NOT NULL DEFAULT '0' COMMENT '持有份额',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户持有基金';

-- ----------------------------
-- Table structure for stock
-- ----------------------------
CREATE TABLE `stock` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `date` varchar(50) DEFAULT NULL COMMENT '日期',
  `turn_over` double(10,2) DEFAULT NULL COMMENT '成交额',
  `shangz` double(10,2) DEFAULT NULL COMMENT '上证',
  `shenz` double(10,2) DEFAULT NULL COMMENT '深证',
  `chuangy` double(10,2) DEFAULT NULL COMMENT '创业',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='大盘信息';

-- ----------------------------
-- Table structure for sys_task
-- ----------------------------
DROP TABLE IF EXISTS `sys_task`;
CREATE TABLE `sys_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(255) DEFAULT NULL COMMENT '任务名',
  `param` text COMMENT '参数  json字符串',
  `cron_expression` varchar(255) DEFAULT NULL COMMENT 'cron表达式',
  `bean_class` varchar(255) DEFAULT NULL COMMENT '任务执行时调用哪个类的方法 包名+类名',
  `job_status` varchar(20) DEFAULT '0' COMMENT '任务状态 0启用 1禁用',
  `job_group` varchar(255) DEFAULT NULL COMMENT '任务分组',
  `create_user` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_del` varchar(20) DEFAULT '0' COMMENT '是否删除 1删除 0未删除',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='定时任务表';

-- ----------------------------
-- Table structure for xq_data
-- ----------------------------
DROP TABLE IF EXISTS `xq_data`;
CREATE TABLE `xq_data` (
  `date` varchar(50) NOT NULL COMMENT '日期',
  `symbol` varchar(50) NOT NULL COMMENT '代码',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `current` decimal(11,4) DEFAULT NULL COMMENT '现价',
  `percent` decimal(11,4) DEFAULT NULL COMMENT '涨幅',
  `amplitude` decimal(11,4) DEFAULT NULL COMMENT '振幅',
  `amount` decimal(11,4) DEFAULT NULL COMMENT '成交额',
  `volume_ratio` decimal(11,4) DEFAULT NULL COMMENT '量比',
  `turnover_rate` decimal(11,4) DEFAULT NULL COMMENT '换手',
  `market_capital` decimal(11,4) DEFAULT NULL COMMENT '市值',
  `current_year_percent` decimal(11,4) DEFAULT NULL COMMENT '年初至今',
  `create_time` varchar(50) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`date`,`symbol`),
  KEY `date` (`date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='股票历史记录';
