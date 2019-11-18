/*
Navicat MySQL Data Transfer

Source Server         : aliyun-zx
Source Server Version : 50728
Source Host           : 47.110.13.117:3306
Source Database       : zx

Target Server Type    : MYSQL
Target Server Version : 50728
File Encoding         : 65001

Date: 2019-11-18 21:26:41
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of auth_menu
-- ----------------------------

-- ----------------------------
-- Table structure for auth_role
-- ----------------------------
DROP TABLE IF EXISTS `auth_role`;
CREATE TABLE `auth_role` (
  `id` bigint(20) NOT NULL COMMENT '角色id',
  `role_code` varchar(11) DEFAULT NULL COMMENT '角色code',
  `role_name` varchar(20) DEFAULT NULL COMMENT '角色名称',
  `is_del` int(11) DEFAULT '0' COMMENT '删除 0:不删除  1:删除',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- ----------------------------
-- Records of auth_role
-- ----------------------------
INSERT INTO `auth_role` VALUES ('1', 'ROLE_ADMIN', '超级管理员', '0', '2019-11-08 15:16:25', '2019-11-18 13:41:32');

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色菜单表';

-- ----------------------------
-- Records of auth_role_menu
-- ----------------------------

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户和角色关系表';

-- ----------------------------
-- Records of auth_user_role
-- ----------------------------

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
-- Records of cd_city
-- ----------------------------
INSERT INTO `cd_city` VALUES ('110000', '北京市', '北京市', 'BJS', '110000', '2018-12-28 09:50:43', '2018-12-28 09:50:43', null, null);
INSERT INTO `cd_city` VALUES ('120000', '天津市', '天津市', 'TJS', '120000', '2018-12-28 09:50:43', '2018-12-28 09:50:43', null, null);
INSERT INTO `cd_city` VALUES ('130100', '河北省石家庄市', '石家庄市', 'HBSSJZS', '130000', '2018-12-28 09:50:43', '2018-12-28 09:50:43', null, null);
INSERT INTO `cd_city` VALUES ('130200', '河北省唐山市', '唐山市', 'HBSTSS', '130000', '2018-12-28 09:50:43', '2018-12-28 09:50:43', null, null);
INSERT INTO `cd_city` VALUES ('130300', '河北省秦皇岛市', '秦皇岛市', 'HBSQHDS', '130000', '2018-12-28 09:50:43', '2018-12-28 09:50:43', null, null);
INSERT INTO `cd_city` VALUES ('130400', '河北省邯郸市', '邯郸市', 'HBSHDS', '130000', '2018-12-28 09:50:43', '2018-12-28 09:50:43', null, null);
INSERT INTO `cd_city` VALUES ('130500', '河北省邢台市', '邢台市', 'HBSXTS', '130000', '2018-12-28 09:50:43', '2018-12-28 09:50:43', null, null);
INSERT INTO `cd_city` VALUES ('130600', '河北省保定市', '保定市', 'HBSBDS', '130000', '2018-12-28 09:50:43', '2018-12-28 09:50:43', null, null);
INSERT INTO `cd_city` VALUES ('130700', '河北省张家口市', '张家口市', 'HBSZJKS', '130000', '2018-12-28 09:50:43', '2018-12-28 09:50:43', null, null);
INSERT INTO `cd_city` VALUES ('130800', '河北省承德市', '承德市', 'HBSCDS', '130000', '2018-12-28 09:50:44', '2018-12-28 09:50:44', null, null);
INSERT INTO `cd_city` VALUES ('130900', '河北省沧州市', '沧州市', 'HBSCZS', '130000', '2018-12-28 09:50:44', '2018-12-28 09:50:44', null, null);
INSERT INTO `cd_city` VALUES ('131000', '河北省廊坊市', '廊坊市', 'HBSLFS', '130000', '2018-12-28 09:50:44', '2018-12-28 09:50:44', null, null);
INSERT INTO `cd_city` VALUES ('131100', '河北省衡水市', '衡水市', 'HBSHSS', '130000', '2018-12-28 09:50:44', '2018-12-28 09:50:44', null, null);
INSERT INTO `cd_city` VALUES ('140100', '山西省太原市', '太原市', 'SXSTYS', '140000', '2018-12-28 09:50:44', '2018-12-28 09:50:44', null, null);
INSERT INTO `cd_city` VALUES ('140200', '山西省大同市', '大同市', 'SXSDTS', '140000', '2018-12-28 09:50:44', '2018-12-28 09:50:44', null, null);
INSERT INTO `cd_city` VALUES ('140300', '山西省阳泉市', '阳泉市', 'SXSYQS', '140000', '2018-12-28 09:50:44', '2018-12-28 09:50:44', null, null);
INSERT INTO `cd_city` VALUES ('140400', '山西省长治市', '长治市', 'SXSCZS', '140000', '2018-12-28 09:50:44', '2018-12-28 09:50:44', null, null);
INSERT INTO `cd_city` VALUES ('140500', '山西省晋城市', '晋城市', 'SXSJCS', '140000', '2018-12-28 09:50:44', '2018-12-28 09:50:44', null, null);
INSERT INTO `cd_city` VALUES ('140600', '山西省朔州市', '朔州市', 'SXSSZS', '140000', '2018-12-28 09:50:44', '2018-12-28 09:50:44', null, null);
INSERT INTO `cd_city` VALUES ('140700', '山西省晋中市', '晋中市', 'SXSJZS', '140000', '2018-12-28 09:50:44', '2018-12-28 09:50:44', null, null);
INSERT INTO `cd_city` VALUES ('140800', '山西省运城市', '运城市', 'SXSYCS', '140000', '2018-12-28 09:50:44', '2018-12-28 09:50:44', null, null);
INSERT INTO `cd_city` VALUES ('140900', '山西省忻州市', '忻州市', 'SXSXZS', '140000', '2018-12-28 09:50:44', '2018-12-28 09:50:44', null, null);
INSERT INTO `cd_city` VALUES ('141000', '山西省临汾市', '临汾市', 'SXSLFS', '140000', '2018-12-28 09:50:44', '2018-12-28 09:50:44', null, null);
INSERT INTO `cd_city` VALUES ('142300', '山西省吕梁地区', '吕梁地区', 'SXSLLDQ', '140000', '2018-12-28 09:50:45', '2018-12-28 09:50:45', null, null);
INSERT INTO `cd_city` VALUES ('150100', '内蒙古自治区呼和浩特市', '呼和浩特 市', 'NMGZZQHHHTS', '150000', '2018-12-28 09:50:45', '2018-12-28 09:50:45', null, null);
INSERT INTO `cd_city` VALUES ('150200', '内蒙古自治区包头市', '包头 市', 'NMGZZQBTS', '150000', '2018-12-28 09:50:45', '2018-12-28 09:50:45', null, null);
INSERT INTO `cd_city` VALUES ('150300', '内蒙古自治区乌海市', '乌海 市', 'NMGZZQWHS', '150000', '2018-12-28 09:50:45', '2018-12-28 09:50:45', null, null);
INSERT INTO `cd_city` VALUES ('150400', '内蒙古自治区赤峰市', '赤峰 市', 'NMGZZQCFS', '150000', '2018-12-28 09:50:45', '2018-12-28 09:50:45', null, null);
INSERT INTO `cd_city` VALUES ('150500', '内蒙古自治区通辽市', '通辽 市', 'NMGZZQTLS', '150000', '2018-12-28 09:50:45', '2018-12-28 09:50:45', null, null);
INSERT INTO `cd_city` VALUES ('150600', '内蒙古自治区鄂尔多斯市', '鄂尔多斯 市', 'NMGZZQEEDSS', '150000', '2018-12-28 09:50:45', '2018-12-28 09:50:45', null, null);
INSERT INTO `cd_city` VALUES ('150700', '内蒙古自治区呼伦贝尔市', '呼伦贝尔 市', 'NMGZZQHLBES', '150000', '2018-12-28 09:50:45', '2018-12-28 09:50:45', null, null);
INSERT INTO `cd_city` VALUES ('152200', '内蒙古自治区兴安盟', '兴安 盟', 'NMGZZQXAM', '150000', '2018-12-28 09:50:45', '2018-12-28 09:50:45', null, null);
INSERT INTO `cd_city` VALUES ('152500', '内蒙古自治区锡林郭勒盟', '锡林郭勒 盟', 'NMGZZQXLGLM', '150000', '2018-12-28 09:50:45', '2018-12-28 09:50:45', null, null);
INSERT INTO `cd_city` VALUES ('152600', '内蒙古自治区乌兰察布盟', '乌兰察布 盟', 'NMGZZQWLCBM', '150000', '2018-12-28 09:50:45', '2018-12-28 09:50:45', null, null);
INSERT INTO `cd_city` VALUES ('152800', '内蒙古自治区巴彦淖尔盟', '巴彦淖尔 盟', 'NMGZZQBYNEM', '150000', '2018-12-28 09:50:45', '2018-12-28 09:50:45', null, null);
INSERT INTO `cd_city` VALUES ('152900', '内蒙古自治区阿拉善盟', '阿拉善 盟', 'NMGZZQALSM', '150000', '2018-12-28 09:50:45', '2018-12-28 09:50:45', null, null);
INSERT INTO `cd_city` VALUES ('210100', '辽宁省沈阳市', '沈阳市', 'LNSSYS', '210000', '2018-12-28 09:50:45', '2018-12-28 09:50:45', null, null);
INSERT INTO `cd_city` VALUES ('210200', '辽宁省大连市', '大连市', 'LNSDLS', '210000', '2018-12-28 09:50:45', '2018-12-28 09:50:45', null, null);
INSERT INTO `cd_city` VALUES ('210300', '辽宁省鞍山市', '鞍山市', 'LNSASS', '210000', '2018-12-28 09:50:45', '2018-12-28 09:50:45', null, null);
INSERT INTO `cd_city` VALUES ('210400', '辽宁省抚顺市', '抚顺市', 'LNSFSS', '210000', '2018-12-28 09:50:45', '2018-12-28 09:50:45', null, null);
INSERT INTO `cd_city` VALUES ('210500', '辽宁省本溪市', '本溪市', 'LNSBXS', '210000', '2018-12-28 09:50:46', '2018-12-28 09:50:46', null, null);
INSERT INTO `cd_city` VALUES ('210600', '辽宁省丹东市', '丹东市', 'LNSDDS', '210000', '2018-12-28 09:50:46', '2018-12-28 09:50:46', null, null);
INSERT INTO `cd_city` VALUES ('210700', '辽宁省锦州市', '锦州市', 'LNSJZS', '210000', '2018-12-28 09:50:46', '2018-12-28 09:50:46', null, null);
INSERT INTO `cd_city` VALUES ('210800', '辽宁省营口市', '营口市', 'LNSYKS', '210000', '2018-12-28 09:50:46', '2018-12-28 09:50:46', null, null);
INSERT INTO `cd_city` VALUES ('210900', '辽宁省阜新市', '阜新市', 'LNSFXS', '210000', '2018-12-28 09:50:46', '2018-12-28 09:50:46', null, null);
INSERT INTO `cd_city` VALUES ('211000', '辽宁省辽阳市', '辽阳市', 'LNSLYS', '210000', '2018-12-28 09:50:46', '2018-12-28 09:50:46', null, null);
INSERT INTO `cd_city` VALUES ('211100', '辽宁省盘锦市', '盘锦市', 'LNSPJS', '210000', '2018-12-28 09:50:46', '2018-12-28 09:50:46', null, null);
INSERT INTO `cd_city` VALUES ('211200', '辽宁省铁岭市', '铁岭市', 'LNSTLS', '210000', '2018-12-28 09:50:46', '2018-12-28 09:50:46', null, null);
INSERT INTO `cd_city` VALUES ('211300', '辽宁省朝阳市', '朝阳市', 'LNSCYS', '210000', '2018-12-28 09:50:46', '2018-12-28 09:50:46', null, null);
INSERT INTO `cd_city` VALUES ('211400', '辽宁省葫芦岛市', '葫芦岛市', 'LNSHLDS', '210000', '2018-12-28 09:50:46', '2018-12-28 09:50:46', null, null);
INSERT INTO `cd_city` VALUES ('220100', '吉林省长春市', '长春市', 'JLSCCS', '220000', '2018-12-28 09:50:46', '2018-12-28 09:50:46', null, null);
INSERT INTO `cd_city` VALUES ('220200', '吉林省吉林市', '吉林市', 'JLSJLS', '220000', '2018-12-28 09:50:46', '2018-12-28 09:50:46', null, null);
INSERT INTO `cd_city` VALUES ('220300', '吉林省四平市', '四平市', 'JLSSPS', '220000', '2018-12-28 09:50:46', '2018-12-28 09:50:46', null, null);
INSERT INTO `cd_city` VALUES ('220400', '吉林省辽源市', '辽源市', 'JLSLYS', '220000', '2018-12-28 09:50:46', '2018-12-28 09:50:46', null, null);
INSERT INTO `cd_city` VALUES ('220500', '吉林省通化市', '通化市', 'JLSTHS', '220000', '2018-12-28 09:50:46', '2018-12-28 09:50:46', null, null);
INSERT INTO `cd_city` VALUES ('220600', '吉林省白山市', '白山市', 'JLSBSS', '220000', '2018-12-28 09:50:46', '2018-12-28 09:50:46', null, null);
INSERT INTO `cd_city` VALUES ('220700', '吉林省松原市', '松原市', 'JLSSYS', '220000', '2018-12-28 09:50:46', '2018-12-28 09:50:46', null, null);
INSERT INTO `cd_city` VALUES ('220800', '吉林省白城市', '白城市', 'JLSBCS', '220000', '2018-12-28 09:50:47', '2018-12-28 09:50:47', null, null);
INSERT INTO `cd_city` VALUES ('222400', '吉林省延边朝鲜族自治州', '延边朝鲜族自治 州', 'JLSYBCXZZZZ', '220000', '2018-12-28 09:50:47', '2018-12-28 09:50:47', null, null);
INSERT INTO `cd_city` VALUES ('230100', '黑龙江省哈尔滨市', '哈尔滨 市', 'HLJSHEBS', '230000', '2018-12-28 09:50:47', '2018-12-28 09:50:47', null, null);
INSERT INTO `cd_city` VALUES ('230200', '黑龙江省齐齐哈尔市', '齐齐哈尔 市', 'HLJSQQHES', '230000', '2018-12-28 09:50:47', '2018-12-28 09:50:47', null, null);
INSERT INTO `cd_city` VALUES ('230300', '黑龙江省鸡西市', '鸡西市', 'HLJSJXS', '230000', '2018-12-28 09:50:47', '2018-12-28 09:50:47', null, null);
INSERT INTO `cd_city` VALUES ('230400', '黑龙江省鹤岗市', '鹤岗市', 'HLJSHGS', '230000', '2018-12-28 09:50:47', '2018-12-28 09:50:47', null, null);
INSERT INTO `cd_city` VALUES ('230500', '黑龙江省双鸭山市', '双鸭山 市', 'HLJSSYSS', '230000', '2018-12-28 09:50:47', '2018-12-28 09:50:47', null, null);
INSERT INTO `cd_city` VALUES ('230600', '黑龙江省大庆市', '大庆市', 'HLJSDQS', '230000', '2018-12-28 09:50:47', '2018-12-28 09:50:47', null, null);
INSERT INTO `cd_city` VALUES ('230700', '黑龙江省伊春市', '伊春市', 'HLJSYCS', '230000', '2018-12-28 09:50:47', '2018-12-28 09:50:47', null, null);
INSERT INTO `cd_city` VALUES ('230800', '黑龙江省佳木斯市', '佳木斯 市', 'HLJSJMSS', '230000', '2018-12-28 09:50:47', '2018-12-28 09:50:47', null, null);
INSERT INTO `cd_city` VALUES ('230900', '黑龙江省七台河市', '七台河 市', 'HLJSQTHS', '230000', '2018-12-28 09:50:47', '2018-12-28 09:50:47', null, null);
INSERT INTO `cd_city` VALUES ('231000', '黑龙江省牡丹江市', '牡丹江 市', 'HLJSMDJS', '230000', '2018-12-28 09:50:47', '2018-12-28 09:50:47', null, null);
INSERT INTO `cd_city` VALUES ('231100', '黑龙江省黑河市', '黑河市', 'HLJSHHS', '230000', '2018-12-28 09:50:47', '2018-12-28 09:50:47', null, null);
INSERT INTO `cd_city` VALUES ('231200', '黑龙江省绥化市', '绥化市', 'HLJSSHS', '230000', '2018-12-28 09:50:47', '2018-12-28 09:50:47', null, null);
INSERT INTO `cd_city` VALUES ('232700', '黑龙江省大兴安岭地区', '大兴安岭地 区', 'HLJSDXALDQ', '230000', '2018-12-28 09:50:47', '2018-12-28 09:50:47', null, null);
INSERT INTO `cd_city` VALUES ('310000', '上海市', '上海市', 'SHS', '310000', '2018-12-28 09:50:47', '2018-12-28 09:50:47', null, null);
INSERT INTO `cd_city` VALUES ('320100', '江苏省南京市', '南京市', 'JSSNJS', '320000', '2018-12-28 09:50:47', '2018-12-28 09:50:47', null, null);
INSERT INTO `cd_city` VALUES ('320200', '江苏省无锡市', '无锡市', 'JSSWXS', '320000', '2018-12-28 09:50:48', '2018-12-28 09:50:48', null, null);
INSERT INTO `cd_city` VALUES ('320300', '江苏省徐州市', '徐州市', 'JSSXZS', '320000', '2018-12-28 09:50:48', '2018-12-28 09:50:48', null, null);
INSERT INTO `cd_city` VALUES ('320400', '江苏省常州市', '常州市', 'JSSCZS', '320000', '2018-12-28 09:50:48', '2018-12-28 09:50:48', null, null);
INSERT INTO `cd_city` VALUES ('320500', '江苏省苏州市', '苏州市', 'JSSSZS', '320000', '2018-12-28 09:50:48', '2018-12-28 09:50:48', null, null);
INSERT INTO `cd_city` VALUES ('320600', '江苏省南通市', '南通市', 'JSSNTS', '320000', '2018-12-28 09:50:48', '2018-12-28 09:50:48', null, null);
INSERT INTO `cd_city` VALUES ('320700', '江苏省连云港市', '连云港市', 'JSSLYGS', '320000', '2018-12-28 09:50:48', '2018-12-28 09:50:48', null, null);
INSERT INTO `cd_city` VALUES ('320800', '江苏省淮安市', '淮安市', 'JSSHAS', '320000', '2018-12-28 09:50:48', '2018-12-28 09:50:48', null, null);
INSERT INTO `cd_city` VALUES ('320900', '江苏省盐城市', '盐城市', 'JSSYCS', '320000', '2018-12-28 09:50:48', '2018-12-28 09:50:48', null, null);
INSERT INTO `cd_city` VALUES ('321000', '江苏省扬州市', '扬州市', 'JSSYZS', '320000', '2018-12-28 09:50:48', '2018-12-28 09:50:48', null, null);
INSERT INTO `cd_city` VALUES ('321100', '江苏省镇江市', '镇江市', 'JSSZJS', '320000', '2018-12-28 09:50:48', '2018-12-28 09:50:48', null, null);
INSERT INTO `cd_city` VALUES ('321200', '江苏省泰州市', '泰州市', 'JSSTZS', '320000', '2018-12-28 09:50:48', '2018-12-28 09:50:48', null, null);
INSERT INTO `cd_city` VALUES ('321300', '江苏省宿迁市', '宿迁市', 'JSSSQS', '320000', '2018-12-28 09:50:48', '2018-12-28 09:50:48', null, null);
INSERT INTO `cd_city` VALUES ('330100', '浙江省杭州市', '杭州市', 'ZJSHZS', '330000', '2018-12-28 09:50:48', '2018-12-28 09:50:48', null, null);
INSERT INTO `cd_city` VALUES ('330200', '浙江省宁波市', '宁波市', 'ZJSNBS', '330000', '2018-12-28 09:50:48', '2018-12-28 09:50:48', null, null);
INSERT INTO `cd_city` VALUES ('330300', '浙江省温州市', '温州市', 'ZJSWZS', '330000', '2018-12-28 09:50:48', '2018-12-28 09:50:48', null, null);
INSERT INTO `cd_city` VALUES ('330400', '浙江省嘉兴市', '嘉兴市', 'ZJSJXS', '330000', '2018-12-28 09:50:48', '2018-12-28 09:50:48', null, null);
INSERT INTO `cd_city` VALUES ('330500', '浙江省湖州市', '湖州市', 'ZJSHZS', '330000', '2018-12-28 09:50:48', '2018-12-28 09:50:48', null, null);
INSERT INTO `cd_city` VALUES ('330600', '浙江省绍兴市', '绍兴市', 'ZJSSXS', '330000', '2018-12-28 09:50:48', '2018-12-28 09:50:48', null, null);
INSERT INTO `cd_city` VALUES ('330700', '浙江省金华市', '金华市', 'ZJSJHS', '330000', '2018-12-28 09:50:49', '2018-12-28 09:50:49', null, null);
INSERT INTO `cd_city` VALUES ('330800', '浙江省衢州市', '衢州市', 'ZJSQZS', '330000', '2018-12-28 09:50:49', '2018-12-28 09:50:49', null, null);
INSERT INTO `cd_city` VALUES ('330900', '浙江省舟山市', '舟山市', 'ZJSZSS', '330000', '2018-12-28 09:50:49', '2018-12-28 09:50:49', null, null);
INSERT INTO `cd_city` VALUES ('331000', '浙江省台州市', '台州市', 'ZJSTZS', '330000', '2018-12-28 09:50:49', '2018-12-28 09:50:49', null, null);
INSERT INTO `cd_city` VALUES ('331100', '浙江省丽水市', '丽水市', 'ZJSLSS', '330000', '2018-12-28 09:50:49', '2018-12-28 09:50:49', null, null);
INSERT INTO `cd_city` VALUES ('340100', '安徽省合肥市', '合肥市', 'AHSHFS', '340000', '2018-12-28 09:50:49', '2018-12-28 09:50:49', null, null);
INSERT INTO `cd_city` VALUES ('340200', '安徽省芜湖市', '芜湖市', 'AHSWHS', '340000', '2018-12-28 09:50:49', '2018-12-28 09:50:49', null, null);
INSERT INTO `cd_city` VALUES ('340300', '安徽省蚌埠市', '蚌埠市', 'AHSBBS', '340000', '2018-12-28 09:50:49', '2018-12-28 09:50:49', null, null);
INSERT INTO `cd_city` VALUES ('340400', '安徽省淮南市', '淮南市', 'AHSHNS', '340000', '2018-12-28 09:50:49', '2018-12-28 09:50:49', null, null);
INSERT INTO `cd_city` VALUES ('340500', '安徽省马鞍山市', '马鞍山市', 'AHSMASS', '340000', '2018-12-28 09:50:49', '2018-12-28 09:50:49', null, null);
INSERT INTO `cd_city` VALUES ('340600', '安徽省淮北市', '淮北市', 'AHSHBS', '340000', '2018-12-28 09:50:49', '2018-12-28 09:50:49', null, null);
INSERT INTO `cd_city` VALUES ('340700', '安徽省铜陵市', '铜陵市', 'AHSTLS', '340000', '2018-12-28 09:50:49', '2018-12-28 09:50:49', null, null);
INSERT INTO `cd_city` VALUES ('340800', '安徽省安庆市', '安庆市', 'AHSAQS', '340000', '2018-12-28 09:50:49', '2018-12-28 09:50:49', null, null);
INSERT INTO `cd_city` VALUES ('341000', '安徽省黄山市', '黄山市', 'AHSHSS', '340000', '2018-12-28 09:50:49', '2018-12-28 09:50:49', null, null);
INSERT INTO `cd_city` VALUES ('341100', '安徽省滁州市', '滁州市', 'AHSCZS', '340000', '2018-12-28 09:50:49', '2018-12-28 09:50:49', null, null);
INSERT INTO `cd_city` VALUES ('341200', '安徽省阜阳市', '阜阳市', 'AHSFYS', '340000', '2018-12-28 09:50:49', '2018-12-28 09:50:49', null, null);
INSERT INTO `cd_city` VALUES ('341300', '安徽省宿州市', '宿州市', 'AHSSZS', '340000', '2018-12-28 09:50:49', '2018-12-28 09:50:49', null, null);
INSERT INTO `cd_city` VALUES ('341400', '安徽省巢湖市', '巢湖市', 'AHSCHS', '340000', '2018-12-28 09:50:50', '2018-12-28 09:50:50', null, null);
INSERT INTO `cd_city` VALUES ('341500', '安徽省六安市', '六安市', 'AHSLAS', '340000', '2018-12-28 09:50:50', '2018-12-28 09:50:50', null, null);
INSERT INTO `cd_city` VALUES ('341600', '安徽省亳州市', '亳州市', 'AHSBZS', '340000', '2018-12-28 09:50:50', '2018-12-28 09:50:50', null, null);
INSERT INTO `cd_city` VALUES ('341700', '安徽省池州市', '池州市', 'AHSCZS', '340000', '2018-12-28 09:50:50', '2018-12-28 09:50:50', null, null);
INSERT INTO `cd_city` VALUES ('341800', '安徽省宣城市', '宣城市', 'AHSXCS', '340000', '2018-12-28 09:50:50', '2018-12-28 09:50:50', null, null);
INSERT INTO `cd_city` VALUES ('350100', '福建省福州市', '福州市', 'FJSFZS', '350000', '2018-12-28 09:50:50', '2018-12-28 09:50:50', null, null);
INSERT INTO `cd_city` VALUES ('350200', '福建省厦门市', '厦门市', 'FJSXMS', '350000', '2018-12-28 09:50:50', '2018-12-28 09:50:50', null, null);
INSERT INTO `cd_city` VALUES ('350300', '福建省莆田市', '莆田市', 'FJSPTS', '350000', '2018-12-28 09:50:50', '2018-12-28 09:50:50', null, null);
INSERT INTO `cd_city` VALUES ('350400', '福建省三明市', '三明市', 'FJSSMS', '350000', '2018-12-28 09:50:50', '2018-12-28 09:50:50', null, null);
INSERT INTO `cd_city` VALUES ('350500', '福建省泉州市', '泉州市', 'FJSQZS', '350000', '2018-12-28 09:50:50', '2018-12-28 09:50:50', null, null);
INSERT INTO `cd_city` VALUES ('350600', '福建省漳州市', '漳州市', 'FJSZZS', '350000', '2018-12-28 09:50:50', '2018-12-28 09:50:50', null, null);
INSERT INTO `cd_city` VALUES ('350700', '福建省南平市', '南平市', 'FJSNPS', '350000', '2018-12-28 09:50:50', '2018-12-28 09:50:50', null, null);
INSERT INTO `cd_city` VALUES ('350800', '福建省龙岩市', '龙岩市', 'FJSLYS', '350000', '2018-12-28 09:50:50', '2018-12-28 09:50:50', null, null);
INSERT INTO `cd_city` VALUES ('350900', '福建省宁德市', '宁德市', 'FJSNDS', '350000', '2018-12-28 09:50:50', '2018-12-28 09:50:50', null, null);
INSERT INTO `cd_city` VALUES ('360100', '江西省南昌市', '南昌市', 'JXSNCS', '360000', '2018-12-28 09:50:50', '2018-12-28 09:50:50', null, null);
INSERT INTO `cd_city` VALUES ('360200', '江西省景德镇市', '景德镇市', 'JXSJDZS', '360000', '2018-12-28 09:50:51', '2018-12-28 09:50:51', null, null);
INSERT INTO `cd_city` VALUES ('360300', '江西省萍乡市', '萍乡市', 'JXSPXS', '360000', '2018-12-28 09:50:51', '2018-12-28 09:50:51', null, null);
INSERT INTO `cd_city` VALUES ('360400', '江西省九江市', '九江市', 'JXSJJS', '360000', '2018-12-28 09:50:51', '2018-12-28 09:50:51', null, null);
INSERT INTO `cd_city` VALUES ('360500', '江西省新余市', '新余市', 'JXSXYS', '360000', '2018-12-28 09:50:51', '2018-12-28 09:50:51', null, null);
INSERT INTO `cd_city` VALUES ('360600', '江西省鹰潭市', '鹰潭市', 'JXSYTS', '360000', '2018-12-28 09:50:51', '2018-12-28 09:50:51', null, null);
INSERT INTO `cd_city` VALUES ('360700', '江西省赣州市', '赣州市', 'JXSGZS', '360000', '2018-12-28 09:50:51', '2018-12-28 09:50:51', null, null);
INSERT INTO `cd_city` VALUES ('360800', '江西省吉安市', '吉安市', 'JXSJAS', '360000', '2018-12-28 09:50:51', '2018-12-28 09:50:51', null, null);
INSERT INTO `cd_city` VALUES ('360900', '江西省宜春市', '宜春市', 'JXSYCS', '360000', '2018-12-28 09:50:51', '2018-12-28 09:50:51', null, null);
INSERT INTO `cd_city` VALUES ('361000', '江西省抚州市', '抚州市', 'JXSFZS', '360000', '2018-12-28 09:50:51', '2018-12-28 09:50:51', null, null);
INSERT INTO `cd_city` VALUES ('361100', '江西省上饶市', '上饶市', 'JXSSRS', '360000', '2018-12-28 09:50:51', '2018-12-28 09:50:51', null, null);
INSERT INTO `cd_city` VALUES ('370100', '山东省济南市', '济南市', 'SDSJNS', '370000', '2018-12-28 09:50:51', '2018-12-28 09:50:51', null, null);
INSERT INTO `cd_city` VALUES ('370200', '山东省青岛市', '青岛市', 'SDSQDS', '370000', '2018-12-28 09:50:51', '2018-12-28 09:50:51', null, null);
INSERT INTO `cd_city` VALUES ('370300', '山东省淄博市', '淄博市', 'SDSZBS', '370000', '2018-12-28 09:50:51', '2018-12-28 09:50:51', null, null);
INSERT INTO `cd_city` VALUES ('370400', '山东省枣庄市', '枣庄市', 'SDSZZS', '370000', '2018-12-28 09:50:51', '2018-12-28 09:50:51', null, null);
INSERT INTO `cd_city` VALUES ('370500', '山东省东营市', '东营市', 'SDSDYS', '370000', '2018-12-28 09:50:51', '2018-12-28 09:50:51', null, null);
INSERT INTO `cd_city` VALUES ('370600', '山东省烟台市', '烟台市', 'SDSYTS', '370000', '2018-12-28 09:50:51', '2018-12-28 09:50:51', null, null);
INSERT INTO `cd_city` VALUES ('370700', '山东省潍坊市', '潍坊市', 'SDSWFS', '370000', '2018-12-28 09:50:51', '2018-12-28 09:50:51', null, null);
INSERT INTO `cd_city` VALUES ('370800', '山东省济宁市', '济宁市', 'SDSJNS', '370000', '2018-12-28 09:50:52', '2018-12-28 09:50:52', null, null);
INSERT INTO `cd_city` VALUES ('370900', '山东省泰安市', '泰安市', 'SDSTAS', '370000', '2018-12-28 09:50:52', '2018-12-28 09:50:52', null, null);
INSERT INTO `cd_city` VALUES ('371000', '山东省威海市', '威海市', 'SDSWHS', '370000', '2018-12-28 09:50:52', '2018-12-28 09:50:52', null, null);
INSERT INTO `cd_city` VALUES ('371100', '山东省日照市', '日照市', 'SDSRZS', '370000', '2018-12-28 09:50:52', '2018-12-28 09:50:52', null, null);
INSERT INTO `cd_city` VALUES ('371200', '山东省莱芜市', '莱芜市', 'SDSLWS', '370000', '2018-12-28 09:50:52', '2018-12-28 09:50:52', null, null);
INSERT INTO `cd_city` VALUES ('371300', '山东省临沂市', '临沂市', 'SDSLYS', '370000', '2018-12-28 09:50:52', '2018-12-28 09:50:52', null, null);
INSERT INTO `cd_city` VALUES ('371400', '山东省德州市', '德州市', 'SDSDZS', '370000', '2018-12-28 09:50:52', '2018-12-28 09:50:52', null, null);
INSERT INTO `cd_city` VALUES ('371500', '山东省聊城市', '聊城市', 'SDSLCS', '370000', '2018-12-28 09:50:52', '2018-12-28 09:50:52', null, null);
INSERT INTO `cd_city` VALUES ('371600', '山东省滨州市', '滨州市', 'SDSBZS', '370000', '2018-12-28 09:50:52', '2018-12-28 09:50:52', null, null);
INSERT INTO `cd_city` VALUES ('371700', '山东省荷泽市', '荷泽市', 'SDSHZS', '370000', '2018-12-28 09:50:52', '2018-12-28 09:50:52', null, null);
INSERT INTO `cd_city` VALUES ('410100', '河南省郑州市', '郑州市', 'HNSZZS', '410000', '2018-12-28 09:50:52', '2018-12-28 09:50:52', null, null);
INSERT INTO `cd_city` VALUES ('410200', '河南省开封市', '开封市', 'HNSKFS', '410000', '2018-12-28 09:50:52', '2018-12-28 09:50:52', null, null);
INSERT INTO `cd_city` VALUES ('410300', '河南省洛阳市', '洛阳市', 'HNSLYS', '410000', '2018-12-28 09:50:52', '2018-12-28 09:50:52', null, null);
INSERT INTO `cd_city` VALUES ('410400', '河南省平顶山市', '平顶山市', 'HNSPDSS', '410000', '2018-12-28 09:50:52', '2018-12-28 09:50:52', null, null);
INSERT INTO `cd_city` VALUES ('410500', '河南省安阳市', '安阳市', 'HNSAYS', '410000', '2018-12-28 09:50:52', '2018-12-28 09:50:52', null, null);
INSERT INTO `cd_city` VALUES ('410600', '河南省鹤壁市', '鹤壁市', 'HNSHBS', '410000', '2018-12-28 09:50:52', '2018-12-28 09:50:52', null, null);
INSERT INTO `cd_city` VALUES ('410700', '河南省新乡市', '新乡市', 'HNSXXS', '410000', '2018-12-28 09:50:53', '2018-12-28 09:50:53', null, null);
INSERT INTO `cd_city` VALUES ('410800', '河南省焦作市', '焦作市', 'HNSJZS', '410000', '2018-12-28 09:50:53', '2018-12-28 09:50:53', null, null);
INSERT INTO `cd_city` VALUES ('410900', '河南省濮阳市', '濮阳市', 'HNSPYS', '410000', '2018-12-28 09:50:53', '2018-12-28 09:50:53', null, null);
INSERT INTO `cd_city` VALUES ('411000', '河南省许昌市', '许昌市', 'HNSXCS', '410000', '2018-12-28 09:50:53', '2018-12-28 09:50:53', null, null);
INSERT INTO `cd_city` VALUES ('411100', '河南省漯河市', '漯河市', 'HNSLHS', '410000', '2018-12-28 09:50:53', '2018-12-28 09:50:53', null, null);
INSERT INTO `cd_city` VALUES ('411200', '河南省三门峡市', '三门峡市', 'HNSSMXS', '410000', '2018-12-28 09:50:53', '2018-12-28 09:50:53', null, null);
INSERT INTO `cd_city` VALUES ('411300', '河南省南阳市', '南阳市', 'HNSNYS', '410000', '2018-12-28 09:50:53', '2018-12-28 09:50:53', null, null);
INSERT INTO `cd_city` VALUES ('411400', '河南省商丘市', '商丘市', 'HNSSQS', '410000', '2018-12-28 09:50:53', '2018-12-28 09:50:53', null, null);
INSERT INTO `cd_city` VALUES ('411500', '河南省信阳市', '信阳市', 'HNSXYS', '410000', '2018-12-28 09:50:53', '2018-12-28 09:50:53', null, null);
INSERT INTO `cd_city` VALUES ('411600', '河南省周口市', '周口市', 'HNSZKS', '410000', '2018-12-28 09:50:53', '2018-12-28 09:50:53', null, null);
INSERT INTO `cd_city` VALUES ('411700', '河南省驻马店市', '驻马店市', 'HNSZMDS', '410000', '2018-12-28 09:50:53', '2018-12-28 09:50:53', null, null);
INSERT INTO `cd_city` VALUES ('420100', '湖北省武汉市', '武汉市', 'HBSWHS', '420000', '2018-12-28 09:50:53', '2018-12-28 09:50:53', null, null);
INSERT INTO `cd_city` VALUES ('420200', '湖北省黄石市', '黄石市', 'HBSHSS', '420000', '2018-12-28 09:50:53', '2018-12-28 09:50:53', null, null);
INSERT INTO `cd_city` VALUES ('420300', '湖北省十堰市', '十堰市', 'HBSSYS', '420000', '2018-12-28 09:50:53', '2018-12-28 09:50:53', null, null);
INSERT INTO `cd_city` VALUES ('420500', '湖北省宜昌市', '宜昌市', 'HBSYCS', '420000', '2018-12-28 09:50:53', '2018-12-28 09:50:53', null, null);
INSERT INTO `cd_city` VALUES ('420600', '湖北省襄樊市', '襄樊市', 'HBSXFS', '420000', '2018-12-28 09:50:53', '2018-12-28 09:50:53', null, null);
INSERT INTO `cd_city` VALUES ('420700', '湖北省鄂州市', '鄂州市', 'HBSEZS', '420000', '2018-12-28 09:50:54', '2018-12-28 09:50:54', null, null);
INSERT INTO `cd_city` VALUES ('420800', '湖北省荆门市', '荆门市', 'HBSJMS', '420000', '2018-12-28 09:50:54', '2018-12-28 09:50:54', null, null);
INSERT INTO `cd_city` VALUES ('420900', '湖北省孝感市', '孝感市', 'HBSXGS', '420000', '2018-12-28 09:50:54', '2018-12-28 09:50:54', null, null);
INSERT INTO `cd_city` VALUES ('421000', '湖北省荆州市', '荆州市', 'HBSJZS', '420000', '2018-12-28 09:50:54', '2018-12-28 09:50:54', null, null);
INSERT INTO `cd_city` VALUES ('421100', '湖北省黄冈市', '黄冈市', 'HBSHGS', '420000', '2018-12-28 09:50:54', '2018-12-28 09:50:54', null, null);
INSERT INTO `cd_city` VALUES ('421200', '湖北省咸宁市', '咸宁市', 'HBSXNS', '420000', '2018-12-28 09:50:54', '2018-12-28 09:50:54', null, null);
INSERT INTO `cd_city` VALUES ('421300', '湖北省随州市', '随州市', 'HBSSZS', '420000', '2018-12-28 09:50:54', '2018-12-28 09:50:54', null, null);
INSERT INTO `cd_city` VALUES ('422800', '湖北省恩施土家族苗族自治州', '恩施土家族苗族自治 州', 'HBSESTJZMZZZZ', '420000', '2018-12-28 09:50:54', '2018-12-28 09:50:54', null, null);
INSERT INTO `cd_city` VALUES ('429000', '湖北省省直辖行政单位', '省直辖行政单 位', 'HBSSZXXZDW', '420000', '2018-12-28 09:50:54', '2018-12-28 09:50:54', null, null);
INSERT INTO `cd_city` VALUES ('430100', '湖南省长沙市', '长沙市', 'HNSCSS', '430000', '2018-12-28 09:50:54', '2018-12-28 09:50:54', null, null);
INSERT INTO `cd_city` VALUES ('430200', '湖南省株洲市', '株洲市', 'HNSZZS', '430000', '2018-12-28 09:50:54', '2018-12-28 09:50:54', null, null);
INSERT INTO `cd_city` VALUES ('430300', '湖南省湘潭市', '湘潭市', 'HNSXTS', '430000', '2018-12-28 09:50:54', '2018-12-28 09:50:54', null, null);
INSERT INTO `cd_city` VALUES ('430400', '湖南省衡阳市', '衡阳市', 'HNSHYS', '430000', '2018-12-28 09:50:54', '2018-12-28 09:50:54', null, null);
INSERT INTO `cd_city` VALUES ('430500', '湖南省邵阳市', '邵阳市', 'HNSSYS', '430000', '2018-12-28 09:50:54', '2018-12-28 09:50:54', null, null);
INSERT INTO `cd_city` VALUES ('430600', '湖南省岳阳市', '岳阳市', 'HNSYYS', '430000', '2018-12-28 09:50:54', '2018-12-28 09:50:54', null, null);
INSERT INTO `cd_city` VALUES ('430700', '湖南省常德市', '常德市', 'HNSCDS', '430000', '2018-12-28 09:50:54', '2018-12-28 09:50:54', null, null);
INSERT INTO `cd_city` VALUES ('430800', '湖南省张家界市', '张家界市', 'HNSZJJS', '430000', '2018-12-28 09:50:54', '2018-12-28 09:50:54', null, null);
INSERT INTO `cd_city` VALUES ('430900', '湖南省益阳市', '益阳市', 'HNSYYS', '430000', '2018-12-28 09:50:54', '2018-12-28 09:50:54', null, null);
INSERT INTO `cd_city` VALUES ('431000', '湖南省郴州市', '郴州市', 'HNSCZS', '430000', '2018-12-28 09:50:54', '2018-12-28 09:50:54', null, null);
INSERT INTO `cd_city` VALUES ('431100', '湖南省永州市', '永州市', 'HNSYZS', '430000', '2018-12-28 09:50:55', '2018-12-28 09:50:55', null, null);
INSERT INTO `cd_city` VALUES ('431200', '湖南省怀化市', '怀化市', 'HNSHHS', '430000', '2018-12-28 09:50:55', '2018-12-28 09:50:55', null, null);
INSERT INTO `cd_city` VALUES ('431300', '湖南省娄底市', '娄底市', 'HNSLDS', '430000', '2018-12-28 09:50:55', '2018-12-28 09:50:55', null, null);
INSERT INTO `cd_city` VALUES ('433100', '湖南省湘西土家族苗族自治州', '湘西土家族苗族自治 州', 'HNSXXTJZMZZZZ', '430000', '2018-12-28 09:50:55', '2018-12-28 09:50:55', null, null);
INSERT INTO `cd_city` VALUES ('440100', '广东省广州市', '广州市', 'GDSGZS', '440000', '2018-12-28 09:50:55', '2018-12-28 09:50:55', null, null);
INSERT INTO `cd_city` VALUES ('440200', '广东省韶关市', '韶关市', 'GDSSGS', '440000', '2018-12-28 09:50:55', '2018-12-28 09:50:55', null, null);
INSERT INTO `cd_city` VALUES ('440300', '广东省深圳市', '深圳市', 'GDSSZS', '440000', '2018-12-28 09:50:55', '2018-12-28 09:50:55', null, null);
INSERT INTO `cd_city` VALUES ('440400', '广东省珠海市', '珠海市', 'GDSZHS', '440000', '2018-12-28 09:50:55', '2018-12-28 09:50:55', null, null);
INSERT INTO `cd_city` VALUES ('440500', '广东省汕头市', '汕头市', 'GDSSTS', '440000', '2018-12-28 09:50:55', '2018-12-28 09:50:55', null, null);
INSERT INTO `cd_city` VALUES ('440600', '广东省佛山市', '佛山市', 'GDSFSS', '440000', '2018-12-28 09:50:55', '2018-12-28 09:50:55', null, null);
INSERT INTO `cd_city` VALUES ('440700', '广东省江门市', '江门市', 'GDSJMS', '440000', '2018-12-28 09:50:55', '2018-12-28 09:50:55', null, null);
INSERT INTO `cd_city` VALUES ('440800', '广东省湛江市', '湛江市', 'GDSZJS', '440000', '2018-12-28 09:50:55', '2018-12-28 09:50:55', null, null);
INSERT INTO `cd_city` VALUES ('440900', '广东省茂名市', '茂名市', 'GDSMMS', '440000', '2018-12-28 09:50:55', '2018-12-28 09:50:55', null, null);
INSERT INTO `cd_city` VALUES ('441200', '广东省肇庆市', '肇庆市', 'GDSZQS', '440000', '2018-12-28 09:50:55', '2018-12-28 09:50:55', null, null);
INSERT INTO `cd_city` VALUES ('441300', '广东省惠州市', '惠州市', 'GDSHZS', '440000', '2018-12-28 09:50:55', '2018-12-28 09:50:55', null, null);
INSERT INTO `cd_city` VALUES ('441400', '广东省梅州市', '梅州市', 'GDSMZS', '440000', '2018-12-28 09:50:55', '2018-12-28 09:50:55', null, null);
INSERT INTO `cd_city` VALUES ('441500', '广东省汕尾市', '汕尾市', 'GDSSWS', '440000', '2018-12-28 09:50:55', '2018-12-28 09:50:55', null, null);
INSERT INTO `cd_city` VALUES ('441600', '广东省河源市', '河源市', 'GDSHYS', '440000', '2018-12-28 09:50:55', '2018-12-28 09:50:55', null, null);
INSERT INTO `cd_city` VALUES ('441700', '广东省阳江市', '阳江市', 'GDSYJS', '440000', '2018-12-28 09:50:55', '2018-12-28 09:50:55', null, null);
INSERT INTO `cd_city` VALUES ('441800', '广东省清远市', '清远市', 'GDSQYS', '440000', '2018-12-28 09:50:55', '2018-12-28 09:50:55', null, null);
INSERT INTO `cd_city` VALUES ('441900', '广东省东莞市', '东莞市', 'GDSDWS', '440000', '2018-12-28 09:50:55', '2018-12-28 09:50:55', null, null);
INSERT INTO `cd_city` VALUES ('442000', '广东省中山市', '中山市', 'GDSZSS', '440000', '2018-12-28 09:50:56', '2018-12-28 09:50:56', null, null);
INSERT INTO `cd_city` VALUES ('445100', '广东省潮州市', '潮州市', 'GDSCZS', '440000', '2018-12-28 09:50:56', '2018-12-28 09:50:56', null, null);
INSERT INTO `cd_city` VALUES ('445200', '广东省揭阳市', '揭阳市', 'GDSJYS', '440000', '2018-12-28 09:50:56', '2018-12-28 09:50:56', null, null);
INSERT INTO `cd_city` VALUES ('445300', '广东省云浮市', '云浮市', 'GDSYFS', '440000', '2018-12-28 09:50:56', '2018-12-28 09:50:56', null, null);
INSERT INTO `cd_city` VALUES ('450100', '广西壮族自治区南宁市', '南宁 市', 'GXZZZZQNNS', '450000', '2018-12-28 09:50:56', '2018-12-28 09:50:56', null, null);
INSERT INTO `cd_city` VALUES ('450200', '广西壮族自治区柳州市', '柳州 市', 'GXZZZZQLZS', '450000', '2018-12-28 09:50:56', '2018-12-28 09:50:56', null, null);
INSERT INTO `cd_city` VALUES ('450300', '广西壮族自治区桂林市', '桂林 市', 'GXZZZZQGLS', '450000', '2018-12-28 09:50:56', '2018-12-28 09:50:56', null, null);
INSERT INTO `cd_city` VALUES ('450400', '广西壮族自治区梧州市', '梧州 市', 'GXZZZZQWZS', '450000', '2018-12-28 09:50:56', '2018-12-28 09:50:56', null, null);
INSERT INTO `cd_city` VALUES ('450500', '广西壮族自治区北海市', '北海 市', 'GXZZZZQBHS', '450000', '2018-12-28 09:50:56', '2018-12-28 09:50:56', null, null);
INSERT INTO `cd_city` VALUES ('450600', '广西壮族自治区防城港市', '防城港 市', 'GXZZZZQFCGS', '450000', '2018-12-28 09:50:56', '2018-12-28 09:50:56', null, null);
INSERT INTO `cd_city` VALUES ('450700', '广西壮族自治区钦州市', '钦州 市', 'GXZZZZQQZS', '450000', '2018-12-28 09:50:56', '2018-12-28 09:50:56', null, null);
INSERT INTO `cd_city` VALUES ('450800', '广西壮族自治区贵港市', '贵港 市', 'GXZZZZQGGS', '450000', '2018-12-28 09:50:56', '2018-12-28 09:50:56', null, null);
INSERT INTO `cd_city` VALUES ('450900', '广西壮族自治区玉林市', '玉林 市', 'GXZZZZQYLS', '450000', '2018-12-28 09:50:56', '2018-12-28 09:50:56', null, null);
INSERT INTO `cd_city` VALUES ('452100', '广西壮族自治区南宁地区', '南宁地 区', 'GXZZZZQNNDQ', '450000', '2018-12-28 09:50:56', '2018-12-28 09:50:56', null, null);
INSERT INTO `cd_city` VALUES ('452200', '广西壮族自治区柳州地区', '柳州地 区', 'GXZZZZQLZDQ', '450000', '2018-12-28 09:50:56', '2018-12-28 09:50:56', null, null);
INSERT INTO `cd_city` VALUES ('452400', '广西壮族自治区贺州地区', '贺州地 区', 'GXZZZZQHZDQ', '450000', '2018-12-28 09:50:56', '2018-12-28 09:50:56', null, null);
INSERT INTO `cd_city` VALUES ('452600', '广西壮族自治区百色地区', '百色地 区', 'GXZZZZQBSDQ', '450000', '2018-12-28 09:50:56', '2018-12-28 09:50:56', null, null);
INSERT INTO `cd_city` VALUES ('452700', '广西壮族自治区河池地区', '河池地 区', 'GXZZZZQHCDQ', '450000', '2018-12-28 09:50:56', '2018-12-28 09:50:56', null, null);
INSERT INTO `cd_city` VALUES ('460100', '海南省海口市', '海口市', 'HNSHKS', '460000', '2018-12-28 09:50:56', '2018-12-28 09:50:56', null, null);
INSERT INTO `cd_city` VALUES ('460200', '海南省三亚市', '三亚市', 'HNSSYS', '460000', '2018-12-28 09:50:56', '2018-12-28 09:50:56', null, null);
INSERT INTO `cd_city` VALUES ('500000', '重庆市', '重庆市', 'CQS', '500000', '2018-12-28 09:50:56', '2018-12-28 09:50:56', null, null);
INSERT INTO `cd_city` VALUES ('510100', '四川省成都市', '成都市', 'SCSCDS', '510000', '2018-12-28 09:50:56', '2018-12-28 09:50:56', null, null);
INSERT INTO `cd_city` VALUES ('510300', '四川省自贡市', '自贡市', 'SCSZGS', '510000', '2018-12-28 09:50:57', '2018-12-28 09:50:57', null, null);
INSERT INTO `cd_city` VALUES ('510400', '四川省攀枝花市', '攀枝花市', 'SCSPZHS', '510000', '2018-12-28 09:50:57', '2018-12-28 09:50:57', null, null);
INSERT INTO `cd_city` VALUES ('510500', '四川省泸州市', '泸州市', 'SCSLZS', '510000', '2018-12-28 09:50:57', '2018-12-28 09:50:57', null, null);
INSERT INTO `cd_city` VALUES ('510600', '四川省德阳市', '德阳市', 'SCSDYS', '510000', '2018-12-28 09:50:57', '2018-12-28 09:50:57', null, null);
INSERT INTO `cd_city` VALUES ('510700', '四川省绵阳市', '绵阳市', 'SCSMYS', '510000', '2018-12-28 09:50:57', '2018-12-28 09:50:57', null, null);
INSERT INTO `cd_city` VALUES ('510800', '四川省广元市', '广元市', 'SCSGYS', '510000', '2018-12-28 09:50:57', '2018-12-28 09:50:57', null, null);
INSERT INTO `cd_city` VALUES ('510900', '四川省遂宁市', '遂宁市', 'SCSSNS', '510000', '2018-12-28 09:50:57', '2018-12-28 09:50:57', null, null);
INSERT INTO `cd_city` VALUES ('511000', '四川省内江市', '内江市', 'SCSNJS', '510000', '2018-12-28 09:50:57', '2018-12-28 09:50:57', null, null);
INSERT INTO `cd_city` VALUES ('511100', '四川省乐山市', '乐山市', 'SCSLSS', '510000', '2018-12-28 09:50:57', '2018-12-28 09:50:57', null, null);
INSERT INTO `cd_city` VALUES ('511300', '四川省南充市', '南充市', 'SCSNCS', '510000', '2018-12-28 09:50:57', '2018-12-28 09:50:57', null, null);
INSERT INTO `cd_city` VALUES ('511400', '四川省眉山市', '眉山市', 'SCSMSS', '510000', '2018-12-28 09:50:57', '2018-12-28 09:50:57', null, null);
INSERT INTO `cd_city` VALUES ('511500', '四川省宜宾市', '宜宾市', 'SCSYBS', '510000', '2018-12-28 09:50:57', '2018-12-28 09:50:57', null, null);
INSERT INTO `cd_city` VALUES ('511600', '四川省广安市', '广安市', 'SCSGAS', '510000', '2018-12-28 09:50:57', '2018-12-28 09:50:57', null, null);
INSERT INTO `cd_city` VALUES ('511700', '四川省达州市', '达州市', 'SCSDZS', '510000', '2018-12-28 09:50:57', '2018-12-28 09:50:57', null, null);
INSERT INTO `cd_city` VALUES ('511800', '四川省雅安市', '雅安市', 'SCSYAS', '510000', '2018-12-28 09:50:57', '2018-12-28 09:50:57', null, null);
INSERT INTO `cd_city` VALUES ('511900', '四川省巴中市', '巴中市', 'SCSBZS', '510000', '2018-12-28 09:50:57', '2018-12-28 09:50:57', null, null);
INSERT INTO `cd_city` VALUES ('512000', '四川省资阳市', '资阳市', 'SCSZYS', '510000', '2018-12-28 09:50:57', '2018-12-28 09:50:57', null, null);
INSERT INTO `cd_city` VALUES ('513200', '四川省阿坝藏族羌族自治州', '阿坝藏族羌族自治 州', 'SCSABCZQZZZZ', '510000', '2018-12-28 09:50:57', '2018-12-28 09:50:57', null, null);
INSERT INTO `cd_city` VALUES ('513300', '四川省甘孜藏族自治州', '甘孜藏族自治 州', 'SCSGZCZZZZ', '510000', '2018-12-28 09:50:58', '2018-12-28 09:50:58', null, null);
INSERT INTO `cd_city` VALUES ('513400', '四川省凉山彝族自治州', '凉山彝族自治 州', 'SCSLSYZZZZ', '510000', '2018-12-28 09:50:58', '2018-12-28 09:50:58', null, null);
INSERT INTO `cd_city` VALUES ('520100', '贵州省贵阳市', '贵阳市', 'GZSGYS', '520000', '2018-12-28 09:50:58', '2018-12-28 09:50:58', null, null);
INSERT INTO `cd_city` VALUES ('520200', '贵州省六盘水市', '六盘水市', 'GZSLPSS', '520000', '2018-12-28 09:50:58', '2018-12-28 09:50:58', null, null);
INSERT INTO `cd_city` VALUES ('520300', '贵州省遵义市', '遵义市', 'GZSZYS', '520000', '2018-12-28 09:50:58', '2018-12-28 09:50:58', null, null);
INSERT INTO `cd_city` VALUES ('520400', '贵州省安顺市', '安顺市', 'GZSASS', '520000', '2018-12-28 09:50:58', '2018-12-28 09:50:58', null, null);
INSERT INTO `cd_city` VALUES ('522200', '贵州省铜仁地区', '铜仁地区', 'GZSTRDQ', '520000', '2018-12-28 09:50:58', '2018-12-28 09:50:58', null, null);
INSERT INTO `cd_city` VALUES ('522300', '贵州省黔西南布依族苗族自治州', '黔西南布依族苗族 自治州', 'GZSQXNBYZMZZZZ', '520000', '2018-12-28 09:50:58', '2018-12-28 09:50:58', null, null);
INSERT INTO `cd_city` VALUES ('522400', '贵州省毕节地区', '毕节地区', 'GZSBJDQ', '520000', '2018-12-28 09:50:58', '2018-12-28 09:50:58', null, null);
INSERT INTO `cd_city` VALUES ('522600', '贵州省黔东南苗族侗族自治州', '黔东南苗族侗族自治 州', 'GZSQDNMZDZZZZ', '520000', '2018-12-28 09:50:58', '2018-12-28 09:50:58', null, null);
INSERT INTO `cd_city` VALUES ('522700', '贵州省黔南布依族苗族自治州', '黔南布依族苗族自治 州', 'GZSQNBYZMZZZZ', '520000', '2018-12-28 09:50:58', '2018-12-28 09:50:58', null, null);
INSERT INTO `cd_city` VALUES ('530100', '云南省昆明市', '昆明市', 'YNSKMS', '530000', '2018-12-28 09:50:58', '2018-12-28 09:50:58', null, null);
INSERT INTO `cd_city` VALUES ('530300', '云南省曲靖市', '曲靖市', 'YNSQJS', '530000', '2018-12-28 09:50:58', '2018-12-28 09:50:58', null, null);
INSERT INTO `cd_city` VALUES ('530400', '云南省玉溪市', '玉溪市', 'YNSYXS', '530000', '2018-12-28 09:50:58', '2018-12-28 09:50:58', null, null);
INSERT INTO `cd_city` VALUES ('530500', '云南省保山市', '保山市', 'YNSBSS', '530000', '2018-12-28 09:50:58', '2018-12-28 09:50:58', null, null);
INSERT INTO `cd_city` VALUES ('530600', '云南省昭通市', '昭通市', 'YNSZTS', '530000', '2018-12-28 09:50:58', '2018-12-28 09:50:58', null, null);
INSERT INTO `cd_city` VALUES ('532300', '云南省楚雄彝族自治州', '楚雄彝族自治 州', 'YNSCXYZZZZ', '530000', '2018-12-28 09:50:58', '2018-12-28 09:50:58', null, null);
INSERT INTO `cd_city` VALUES ('532500', '云南省红河哈尼族彝族自治州', '红河哈尼族彝族自治 州', 'YNSHHHNZYZZZZ', '530000', '2018-12-28 09:50:58', '2018-12-28 09:50:58', null, null);
INSERT INTO `cd_city` VALUES ('532600', '云南省文山壮族苗族自治州', '文山壮族苗族自治 州', 'YNSWSZZMZZZZ', '530000', '2018-12-28 09:50:58', '2018-12-28 09:50:58', null, null);
INSERT INTO `cd_city` VALUES ('532700', '云南省思茅地区', '思茅地区', 'YNSSMDQ', '530000', '2018-12-28 09:50:58', '2018-12-28 09:50:58', null, null);
INSERT INTO `cd_city` VALUES ('532800', '云南省西双版纳傣族自治州', '西双版纳傣族自治 州', 'YNSXSBNDZZZZ', '530000', '2018-12-28 09:50:58', '2018-12-28 09:50:58', null, null);
INSERT INTO `cd_city` VALUES ('532900', '云南省大理白族自治州', '大理白族自治 州', 'YNSDLBZZZZ', '530000', '2018-12-28 09:50:58', '2018-12-28 09:50:58', null, null);
INSERT INTO `cd_city` VALUES ('533100', '云南省德宏傣族景颇族自治州', '德宏傣族景颇族自治 州', 'YNSDHDZJPZZZZ', '530000', '2018-12-28 09:50:58', '2018-12-28 09:50:58', null, null);
INSERT INTO `cd_city` VALUES ('533200', '云南省丽江地区', '丽江地区', 'YNSLJDQ', '530000', '2018-12-28 09:50:58', '2018-12-28 09:50:58', null, null);
INSERT INTO `cd_city` VALUES ('533300', '云南省怒江傈僳族自治州', '怒江傈僳族自治 州', 'YNSNJLSZZZZ', '530000', '2018-12-28 09:50:59', '2018-12-28 09:50:59', null, null);
INSERT INTO `cd_city` VALUES ('533400', '云南省迪庆藏族自治州', '迪庆藏族自治 州', 'YNSDQCZZZZ', '530000', '2018-12-28 09:50:59', '2018-12-28 09:50:59', null, null);
INSERT INTO `cd_city` VALUES ('533500', '云南省临沧地区', '临沧地区', 'YNSLCDQ', '530000', '2018-12-28 09:50:59', '2018-12-28 09:50:59', null, null);
INSERT INTO `cd_city` VALUES ('540100', '西藏自治区拉萨市', '拉萨 市', 'XCZZQLSS', '540000', '2018-12-28 09:50:59', '2018-12-28 09:50:59', null, null);
INSERT INTO `cd_city` VALUES ('542100', '西藏自治区昌都地区', '昌都地 区', 'XCZZQCDDQ', '540000', '2018-12-28 09:50:59', '2018-12-28 09:50:59', null, null);
INSERT INTO `cd_city` VALUES ('542200', '西藏自治区山南地区', '山南地 区', 'XCZZQSNDQ', '540000', '2018-12-28 09:50:59', '2018-12-28 09:50:59', null, null);
INSERT INTO `cd_city` VALUES ('542300', '西藏自治区日喀则地区', '日喀则地 区', 'XCZZQRKZDQ', '540000', '2018-12-28 09:50:59', '2018-12-28 09:50:59', null, null);
INSERT INTO `cd_city` VALUES ('542400', '西藏自治区那曲地区', '那曲地 区', 'XCZZQNQDQ', '540000', '2018-12-28 09:50:59', '2018-12-28 09:50:59', null, null);
INSERT INTO `cd_city` VALUES ('542500', '西藏自治区阿里地区', '阿里地 区', 'XCZZQALDQ', '540000', '2018-12-28 09:50:59', '2018-12-28 09:50:59', null, null);
INSERT INTO `cd_city` VALUES ('542600', '西藏自治区林芝地区', '林芝地 区', 'XCZZQLZDQ', '540000', '2018-12-28 09:50:59', '2018-12-28 09:50:59', null, null);
INSERT INTO `cd_city` VALUES ('610100', '陕西省西安市', '西安市', 'SXSXAS', '610000', '2018-12-28 09:50:59', '2018-12-28 09:50:59', null, null);
INSERT INTO `cd_city` VALUES ('610200', '陕西省铜川市', '铜川市', 'SXSTCS', '610000', '2018-12-28 09:50:59', '2018-12-28 09:50:59', null, null);
INSERT INTO `cd_city` VALUES ('610300', '陕西省宝鸡市', '宝鸡市', 'SXSBJS', '610000', '2018-12-28 09:50:59', '2018-12-28 09:50:59', null, null);
INSERT INTO `cd_city` VALUES ('610400', '陕西省咸阳市', '咸阳市', 'SXSXYS', '610000', '2018-12-28 09:50:59', '2018-12-28 09:50:59', null, null);
INSERT INTO `cd_city` VALUES ('610500', '陕西省渭南市', '渭南市', 'SXSWNS', '610000', '2018-12-28 09:50:59', '2018-12-28 09:50:59', null, null);
INSERT INTO `cd_city` VALUES ('610600', '陕西省延安市', '延安市', 'SXSYAS', '610000', '2018-12-28 09:50:59', '2018-12-28 09:50:59', null, null);
INSERT INTO `cd_city` VALUES ('610700', '陕西省汉中市', '汉中市', 'SXSHZS', '610000', '2018-12-28 09:50:59', '2018-12-28 09:50:59', null, null);
INSERT INTO `cd_city` VALUES ('610800', '陕西省榆林市', '榆林市', 'SXSYLS', '610000', '2018-12-28 09:50:59', '2018-12-28 09:50:59', null, null);
INSERT INTO `cd_city` VALUES ('610900', '陕西省安康市', '安康市', 'SXSAKS', '610000', '2018-12-28 09:50:59', '2018-12-28 09:50:59', null, null);
INSERT INTO `cd_city` VALUES ('611000', '陕西省商洛市', '商洛市', 'SXSSLS', '610000', '2018-12-28 09:50:59', '2018-12-28 09:50:59', null, null);
INSERT INTO `cd_city` VALUES ('620100', '甘肃省兰州市', '兰州市', 'GSSLZS', '620000', '2018-12-28 09:50:59', '2018-12-28 09:50:59', null, null);
INSERT INTO `cd_city` VALUES ('620200', '甘肃省嘉峪关市', '嘉峪关市', 'GSSJYGS', '620000', '2018-12-28 09:50:59', '2018-12-28 09:50:59', null, null);
INSERT INTO `cd_city` VALUES ('620300', '甘肃省金昌市', '金昌市', 'GSSJCS', '620000', '2018-12-28 09:50:59', '2018-12-28 09:50:59', null, null);
INSERT INTO `cd_city` VALUES ('620400', '甘肃省白银市', '白银市', 'GSSBYS', '620000', '2018-12-28 09:50:59', '2018-12-28 09:50:59', null, null);
INSERT INTO `cd_city` VALUES ('620500', '甘肃省天水市', '天水市', 'GSSTSS', '620000', '2018-12-28 09:51:00', '2018-12-28 09:51:00', null, null);
INSERT INTO `cd_city` VALUES ('620600', '甘肃省武威市', '武威市', 'GSSWWS', '620000', '2018-12-28 09:51:00', '2018-12-28 09:51:00', null, null);
INSERT INTO `cd_city` VALUES ('622100', '甘肃省酒泉地区', '酒泉地区', 'GSSJQDQ', '620000', '2018-12-28 09:51:00', '2018-12-28 09:51:00', null, null);
INSERT INTO `cd_city` VALUES ('622200', '甘肃省张掖地区', '张掖地区', 'GSSZYDQ', '620000', '2018-12-28 09:51:00', '2018-12-28 09:51:00', null, null);
INSERT INTO `cd_city` VALUES ('622400', '甘肃省定西地区', '定西地区', 'GSSDXDQ', '620000', '2018-12-28 09:51:00', '2018-12-28 09:51:00', null, null);
INSERT INTO `cd_city` VALUES ('622600', '甘肃省陇南地区', '陇南地区', 'GSSLNDQ', '620000', '2018-12-28 09:51:00', '2018-12-28 09:51:00', null, null);
INSERT INTO `cd_city` VALUES ('622700', '甘肃省平凉地区', '平凉地区', 'GSSPLDQ', '620000', '2018-12-28 09:51:00', '2018-12-28 09:51:00', null, null);
INSERT INTO `cd_city` VALUES ('622800', '甘肃省庆阳地区', '庆阳地区', 'GSSQYDQ', '620000', '2018-12-28 09:51:00', '2018-12-28 09:51:00', null, null);
INSERT INTO `cd_city` VALUES ('622900', '甘肃省临夏回族自治州', '临夏回族自治 州', 'GSSLXHZZZZ', '620000', '2018-12-28 09:51:00', '2018-12-28 09:51:00', null, null);
INSERT INTO `cd_city` VALUES ('623000', '甘肃省甘南藏族自治州', '甘南藏族自治 州', 'GSSGNCZZZZ', '620000', '2018-12-28 09:51:00', '2018-12-28 09:51:00', null, null);
INSERT INTO `cd_city` VALUES ('630100', '青海省西宁市', '西宁市', 'QHSXNS', '630000', '2018-12-28 09:51:00', '2018-12-28 09:51:00', null, null);
INSERT INTO `cd_city` VALUES ('632100', '青海省海东地区', '海东地区', 'QHSHDDQ', '630000', '2018-12-28 09:51:00', '2018-12-28 09:51:00', null, null);
INSERT INTO `cd_city` VALUES ('632200', '青海省海北藏族自治州', '海北藏族自治 州', 'QHSHBCZZZZ', '630000', '2018-12-28 09:51:00', '2018-12-28 09:51:00', null, null);
INSERT INTO `cd_city` VALUES ('632300', '青海省黄南藏族自治州', '黄南藏族自治 州', 'QHSHNCZZZZ', '630000', '2018-12-28 09:51:00', '2018-12-28 09:51:00', null, null);
INSERT INTO `cd_city` VALUES ('632500', '青海省海南藏族自治州', '海南藏族自治 州', 'QHSHNCZZZZ', '630000', '2018-12-28 09:51:00', '2018-12-28 09:51:00', null, null);
INSERT INTO `cd_city` VALUES ('632600', '青海省果洛藏族自治州', '果洛藏族自治 州', 'QHSGLCZZZZ', '630000', '2018-12-28 09:51:00', '2018-12-28 09:51:00', null, null);
INSERT INTO `cd_city` VALUES ('632700', '青海省玉树藏族自治州', '玉树藏族自治 州', 'QHSYSCZZZZ', '630000', '2018-12-28 09:51:00', '2018-12-28 09:51:00', null, null);
INSERT INTO `cd_city` VALUES ('632800', '青海省海西蒙古族藏族自治州', '海西蒙古族藏族自治 州', 'QHSHXMGZCZZZZ', '630000', '2018-12-28 09:51:00', '2018-12-28 09:51:00', null, null);
INSERT INTO `cd_city` VALUES ('640100', '宁夏回族自治区银川市', '银川 市', 'NXHZZZQYCS', '640000', '2018-12-28 09:51:00', '2018-12-28 09:51:00', null, null);
INSERT INTO `cd_city` VALUES ('640200', '宁夏回族自治区石嘴山市', '石嘴山 市', 'NXHZZZQSZSS', '640000', '2018-12-28 09:51:00', '2018-12-28 09:51:00', null, null);
INSERT INTO `cd_city` VALUES ('640300', '宁夏回族自治区吴忠市', '吴忠 市', 'NXHZZZQWZS', '640000', '2018-12-28 09:51:00', '2018-12-28 09:51:00', null, null);
INSERT INTO `cd_city` VALUES ('640400', '宁夏回族自治区固原市', '固原 市', 'NXHZZZQGYS', '640000', '2018-12-28 09:51:00', '2018-12-28 09:51:00', null, null);
INSERT INTO `cd_city` VALUES ('650100', '新疆维吾尔自治区乌鲁木齐市', '乌鲁木齐 市', 'XJWWEZZQWLMQS', '650000', '2018-12-28 09:51:00', '2018-12-28 09:51:00', null, null);
INSERT INTO `cd_city` VALUES ('650200', '新疆维吾尔自治区克拉玛依市', '克拉玛依 市', 'XJWWEZZQKLMYS', '650000', '2018-12-28 09:51:00', '2018-12-28 09:51:00', null, null);
INSERT INTO `cd_city` VALUES ('652100', '新疆维吾尔自治区吐鲁番地区', '吐鲁番地 区', 'XJWWEZZQTLFDQ', '650000', '2018-12-28 09:51:00', '2018-12-28 09:51:00', null, null);
INSERT INTO `cd_city` VALUES ('652200', '新疆维吾尔自治区哈密地区', '哈密地 区', 'XJWWEZZQHMDQ', '650000', '2018-12-28 09:51:00', '2018-12-28 09:51:00', null, null);
INSERT INTO `cd_city` VALUES ('652300', '新疆维吾尔自治区昌吉回族自治州', '昌吉回族自治州 ', 'XJWWEZZQCJHZZZZ', '650000', '2018-12-28 09:51:01', '2018-12-28 09:51:01', null, null);
INSERT INTO `cd_city` VALUES ('652700', '新疆维吾尔自治区博尔塔拉蒙古自治州', '博尔塔拉蒙 古自治州', 'XJWWEZZQBETLMGZZZ', '650000', '2018-12-28 09:51:01', '2018-12-28 09:51:01', null, null);
INSERT INTO `cd_city` VALUES ('652800', '新疆维吾尔自治区巴音郭楞蒙古自治州', '巴音郭楞蒙 古自治州', 'XJWWEZZQBYGLMGZZZ', '650000', '2018-12-28 09:51:01', '2018-12-28 09:51:01', null, null);
INSERT INTO `cd_city` VALUES ('652900', '新疆维吾尔自治区阿克苏地区', '阿克苏地 区', 'XJWWEZZQAKSDQ', '650000', '2018-12-28 09:51:01', '2018-12-28 09:51:01', null, null);
INSERT INTO `cd_city` VALUES ('653000', '新疆维吾尔自治区克孜勒苏柯尔克孜自治州', '克孜勒 苏柯尔克孜自治州', 'XJWWEZZQKZLSKEKZZZZ', '650000', '2018-12-28 09:51:01', '2018-12-28 09:51:01', null, null);
INSERT INTO `cd_city` VALUES ('653100', '新疆维吾尔自治区喀什地区', '喀什地 区', 'XJWWEZZQKSDQ', '650000', '2018-12-28 09:51:01', '2018-12-28 09:51:01', null, null);
INSERT INTO `cd_city` VALUES ('653200', '新疆维吾尔自治区和田地区', '和田地 区', 'XJWWEZZQHTDQ', '650000', '2018-12-28 09:51:01', '2018-12-28 09:51:01', null, null);
INSERT INTO `cd_city` VALUES ('654000', '新疆维吾尔自治区伊犁哈萨克自治州', '伊犁哈萨克自 治州', 'XJWWEZZQYLHSKZZZ', '650000', '2018-12-28 09:51:01', '2018-12-28 09:51:01', null, null);
INSERT INTO `cd_city` VALUES ('654200', '新疆维吾尔自治区塔城地区', '塔城地 区', 'XJWWEZZQTCDQ', '650000', '2018-12-28 09:51:01', '2018-12-28 09:51:01', null, null);
INSERT INTO `cd_city` VALUES ('654300', '新疆维吾尔自治区阿勒泰地区', '阿勒泰地 区', 'XJWWEZZQALTDQ', '650000', '2018-12-28 09:51:01', '2018-12-28 09:51:01', null, null);
INSERT INTO `cd_city` VALUES ('659000', '新疆维吾尔自治区省直辖行政单位', '省直辖行政单位 ', 'XJWWEZZQSZXXZDW', '650000', '2018-12-28 09:51:01', '2018-12-28 09:51:01', null, null);

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
-- Records of sys_execute_time_log
-- ----------------------------

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
-- Records of sys_tree_dict
-- ----------------------------
INSERT INTO `sys_tree_dict` VALUES ('1022', 'sxlx', '授信类型', '-999', '-999', '0', '0', '2019-04-19 18:15:20', '\0');
INSERT INTO `sys_tree_dict` VALUES ('1023', 'sxlx', '抵押', '1', '1', '0', '0', '2019-04-19 18:15:20', '\0');
INSERT INTO `sys_tree_dict` VALUES ('1024', 'sxlx', '质押', '2', '2', '0', '0', '2019-04-19 18:15:20', '\0');
INSERT INTO `sys_tree_dict` VALUES ('1025', 'sxlx', '信用', '3', '3', '0', '0', '2019-04-19 18:15:20', '\0');
INSERT INTO `sys_tree_dict` VALUES ('1026', 'sxlx', '担保', '4', '4', '0', '0', '2019-04-19 18:15:20', '\0');
INSERT INTO `sys_tree_dict` VALUES ('1027', 'sxlx', '自然人持有的不动产', '5', '1', '1', '0', '2019-04-19 18:15:20', '\0');
INSERT INTO `sys_tree_dict` VALUES ('1028', 'sxlx', '商品住宅', '6', '2', '1', '0', '2019-04-19 18:15:20', '\0');
INSERT INTO `sys_tree_dict` VALUES ('1029', 'sxlx', '公司法人持有的不动产', '7', '3', '1', '0', '2019-04-19 18:15:20', '\0');
INSERT INTO `sys_tree_dict` VALUES ('1030', 'sxlx', '标准厂房', '8', '4', '1', '0', '2019-04-19 18:15:20', '\0');
INSERT INTO `sys_tree_dict` VALUES ('1031', 'sxlx', '机器设备', '9', '5', '1', '0', '2019-04-19 18:15:20', '\0');
INSERT INTO `sys_tree_dict` VALUES ('1032', 'sxlx', '车辆', '10', '6', '1', '0', '2019-04-19 18:15:20', '\0');
INSERT INTO `sys_tree_dict` VALUES ('1033', 'sxlx', '其他', '11', '7', '1', '0', '2019-04-19 18:15:20', '\0');
INSERT INTO `sys_tree_dict` VALUES ('1034', 'sxlx', '存贷', '12', '1', '2', '0', '2019-04-19 18:15:20', '\0');
INSERT INTO `sys_tree_dict` VALUES ('1035', 'sxlx', '国债', '13', '2', '2', '0', '2019-04-19 18:15:20', '\0');
INSERT INTO `sys_tree_dict` VALUES ('1036', 'sxlx', '保证基金', '14', '3', '2', '0', '2019-04-19 18:15:20', '\0');
INSERT INTO `sys_tree_dict` VALUES ('1037', 'sxlx', '银行承兑汇票', '15', '4', '2', '0', '2019-04-19 18:15:20', '\0');
INSERT INTO `sys_tree_dict` VALUES ('1038', 'sxlx', '标准仓单', '16', '5', '2', '0', '2019-04-19 18:15:20', '\0');
INSERT INTO `sys_tree_dict` VALUES ('1039', 'sxlx', '其他', '17', '6', '2', '0', '2019-04-19 18:16:13', '\0');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `phone` varchar(13) DEFAULT NULL COMMENT '电话',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `create_date` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='系统用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
