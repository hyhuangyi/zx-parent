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
  `sort` int(11) DEFAULT NULL COMMENT '排序号',
  `is_del` int(11) DEFAULT NULL COMMENT '1 删除 0 未删除',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='菜单表';

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
  `update_user` int(11) DEFAULT NULL COMMENT '编辑人',
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
