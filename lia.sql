/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : localhost:3306
 Source Schema         : lia

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 24/02/2023 22:08:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_auth
-- ----------------------------
DROP TABLE IF EXISTS `sys_auth`;
CREATE TABLE `sys_auth`  (
  `auth_id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限名称',
  `url` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限接口地址',
  `key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限标识',
  `router_id` int NOT NULL COMMENT '所属路由',
  `type` enum('0','1') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '权限类型(0：接口，1：组件)',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`auth_id`) USING BTREE,
  UNIQUE INDEX `sys_auth-key`(`key` ASC) USING BTREE,
  UNIQUE INDEX `sys_auth-url`(`url` ASC) USING BTREE,
  INDEX `sys_auth-create_by`(`create_by` ASC) USING BTREE,
  INDEX `sys_auth-router_id`(`router_id` ASC) USING BTREE,
  CONSTRAINT `sys_auth-create_by` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `sys_auth-router_id` FOREIGN KEY (`router_id`) REFERENCES `sys_router` (`router_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 96 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_auth
-- ----------------------------
INSERT INTO `sys_auth` VALUES (1, '查询用户', '/system/user/getPage', 'system:user:getPage', 4, '0', 1, '2022-05-06 17:25:00', NULL);
INSERT INTO `sys_auth` VALUES (5, '添加或编辑用户', '/system/user/saveUser', 'system:user:saveUser', 4, '0', 1, '2022-05-12 17:16:06', NULL);
INSERT INTO `sys_auth` VALUES (6, '批量删除用户', '/system/user/deleteUsers', 'system:user:deleteUsers', 4, '0', 1, '2022-05-15 20:28:25', NULL);
INSERT INTO `sys_auth` VALUES (7, '获取创建人字典表', '/system/user/getCreateByDict', 'system:user:getCreateByDict', 4, '0', 1, '2022-05-16 14:38:54', NULL);
INSERT INTO `sys_auth` VALUES (8, '查询路由树', '/system/router/getRouterTree', 'system:router:getRouterTree', 6, '0', 1, '2022-05-30 14:55:21', NULL);
INSERT INTO `sys_auth` VALUES (9, '保存路由', '/system/router/saveRouter', 'system:router:saveRouter', 6, '0', 1, '2022-05-30 15:19:25', NULL);
INSERT INTO `sys_auth` VALUES (10, '批量删除路由', '/system/router/deleteRouters', 'system:router:deleteRouters', 6, '0', 1, '2022-05-30 15:19:50', NULL);
INSERT INTO `sys_auth` VALUES (11, '批量删除权限', '/system/auth/delete', 'system:auth:delete', 5, '0', 1, '2022-06-11 11:51:33', NULL);
INSERT INTO `sys_auth` VALUES (12, '根据id查询路由', '/system/router/getRouterById', 'system:router:getRouterById', 6, '0', 1, '2022-06-06 01:55:50', NULL);
INSERT INTO `sys_auth` VALUES (13, '查询角色列表', '/system/role/getPage', 'system:role:getPage', 41, '0', 1, '2022-06-10 23:09:27', NULL);
INSERT INTO `sys_auth` VALUES (14, '新增或编辑角色', '/system/role/saveRole', 'system:role:saveRole', 41, '0', 1, '2022-06-10 23:31:23', NULL);
INSERT INTO `sys_auth` VALUES (15, '批量删除角色', '/system/role/deleteRoles', 'system:role:deleteRoles', 41, '0', 1, '2022-06-10 23:32:29', NULL);
INSERT INTO `sys_auth` VALUES (16, '获取权限字典表', '/system/auth/sysAuthDict', 'system:auth:sysAuthDict', 5, '0', 1, '2022-06-11 01:20:20', NULL);
INSERT INTO `sys_auth` VALUES (17, '分页查询权限', '/system/auth/getPage', 'system:auth:getPage', 5, '0', 1, '2022-06-11 11:50:18', NULL);
INSERT INTO `sys_auth` VALUES (18, '新增或编辑权限', '/system/auth/save', 'system:auth:save', 5, '0', 1, '2022-06-11 11:50:58', NULL);
INSERT INTO `sys_auth` VALUES (59, '企业管理分页查询', '/system/company/getPage', 'system:company:getPage', 41, '0', 1, '2022-09-20 15:51:20', NULL);
INSERT INTO `sys_auth` VALUES (60, '企业管理新增和编辑', '/system/company/save', 'system:company:save', 41, '0', 1, '2022-09-20 15:51:51', NULL);
INSERT INTO `sys_auth` VALUES (61, '企业管理批量删除', '/system/company/delete', 'system:company:delete', 41, '0', 1, '2022-09-20 15:52:15', NULL);
INSERT INTO `sys_auth` VALUES (62, '获取企业字典表', '/system/company/sysCompanyDict', 'system:company:sysCompanyDict', 41, '0', 1, '2022-09-20 19:56:00', NULL);
INSERT INTO `sys_auth` VALUES (63, '获取某企业下的角色字典表', '/system/role/getRoleOfCompanyDict', 'system:role:getRoleOfCompanyDict', 41, '0', 1, '2022-09-20 20:37:00', '根据企业ID获取某企业下的角色字典表');
INSERT INTO `sys_auth` VALUES (64, '字典类别分页查询', '/system/dictType/getPage', 'system:dictType:getPage', 7, '0', 1, '2022-09-23 11:21:13', NULL);
INSERT INTO `sys_auth` VALUES (65, '字典类别新增和编辑', '/system/dictType/save', 'system:dictType:save', 7, '0', 1, '2022-09-23 11:21:40', NULL);
INSERT INTO `sys_auth` VALUES (66, '批量删除字典类别', '/system/dictType/delete', 'system:dictType:delete', 7, '0', 1, '2022-09-23 11:22:05', NULL);
INSERT INTO `sys_auth` VALUES (68, '字典数据分页查询', '/system/dictData/getPage', 'system:dictData:getPage', 7, '0', 1, '2022-09-23 11:47:42', NULL);
INSERT INTO `sys_auth` VALUES (69, '字典数据新增或编辑', '/system/dictData/save', 'system:dictData:save', 7, '0', 1, '2022-09-23 11:48:28', NULL);
INSERT INTO `sys_auth` VALUES (70, '字典数据批量删除', '/system/dictData/delete', 'system:dictData:delete', 7, '0', 1, '2022-09-23 11:48:55', NULL);
INSERT INTO `sys_auth` VALUES (72, '获取性别字典表', '/system/dictData/getSexDict', 'system:dictData:getSexDict', 7, '0', 1, '2022-09-23 12:08:03', NULL);
INSERT INTO `sys_auth` VALUES (73, '代码生成添加或编辑记录', '/system/tool/code/save', 'system:tool:code:save', 40, '0', 1, '2022-09-23 16:10:40', NULL);
INSERT INTO `sys_auth` VALUES (74, '代码生成分页查询', '/system/tool/code/getPage', 'system:tool:code:getPage', 40, '0', 1, '2022-09-23 16:12:12', NULL);
INSERT INTO `sys_auth` VALUES (75, '代码生成批量删除', '/system/tool/code/delete', 'system:tool:code:delete', 40, '0', 1, '2022-09-23 16:18:15', NULL);
INSERT INTO `sys_auth` VALUES (76, '角色状态字典表', '/system/dictData/getUserStatusDict', 'system:dictData:getUserStatusDict', 7, '0', 1, '2022-09-28 21:51:47', NULL);
INSERT INTO `sys_auth` VALUES (77, '分页查询系统参数', '/system/param/getPage', 'system:param:getPage', 44, '0', 1, '2022-11-30 16:55:39', NULL);
INSERT INTO `sys_auth` VALUES (78, '新增或编辑参数', '/system/param/save', 'system:param:save', 44, '0', 1, '2022-11-30 16:56:13', NULL);
INSERT INTO `sys_auth` VALUES (79, '批量删除参数', '/system/param/delete', 'system:param:delete', 44, '0', 1, '2022-11-30 16:56:37', NULL);
INSERT INTO `sys_auth` VALUES (81, '批量移动权限', '/system/auth/moveToRouter', 'system:auth:moveToRouter', 5, '0', 1, '2023-01-10 13:34:56', '批量移动权限到某路由');
INSERT INTO `sys_auth` VALUES (82, '分页查询', '/system/register/code/getPage', 'system:register:code:getPage', 47, '0', 1, '2023-01-10 21:31:40', NULL);
INSERT INTO `sys_auth` VALUES (83, '编辑', '/system/register/code/edit', 'system:register:code:edit', 47, '0', 1, '2023-01-10 21:32:07', NULL);
INSERT INTO `sys_auth` VALUES (84, '批量删除', '/system/register/code/delete', 'system:register:code:delete', 47, '0', 1, '2023-01-10 21:32:27', NULL);
INSERT INTO `sys_auth` VALUES (85, '批量生成注册码', '/system/register/code/create', 'system:register:code:create', 47, '0', 1, '2023-01-11 00:40:12', NULL);
INSERT INTO `sys_auth` VALUES (91, '路由重新排序', '/system/router/reloadIndex', 'system:router:reloadIndex', 6, '0', 1, '2023-02-22 00:10:57', NULL);
INSERT INTO `sys_auth` VALUES (92, '根据key获取字典', '/system/dictData/getByKey', 'system:dictData:getByKey', 7, '0', 1, '2023-02-23 16:13:25', NULL);
INSERT INTO `sys_auth` VALUES (95, '发布系统公告', '/system/notice/add', 'system:notice:add', 2, '0', 1, '2023-02-24 17:08:02', NULL);

-- ----------------------------
-- Table structure for sys_company
-- ----------------------------
DROP TABLE IF EXISTS `sys_company`;
CREATE TABLE `sys_company`  (
  `company_id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '企业名称',
  `principal` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '企业负责人',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '负责人联系方式',
  `address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '企业地址',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱地址',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`company_id`) USING BTREE,
  UNIQUE INDEX `sys_company-name`(`name` ASC) USING BTREE COMMENT '企业名称唯一',
  INDEX `sys_company-create_by`(`create_by` ASC) USING BTREE,
  CONSTRAINT `sys_company-create_by` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '企业表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_company
-- ----------------------------
INSERT INTO `sys_company` VALUES (1, '泉州师范学院', '李伟强', '18150027197', '福建省泉州市丰泽区东海大街398号', '1072864729@qq.com', 1, '2022-09-20 16:06:00', '学校');

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `data_id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典数据key',
  `label` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典数据值',
  `type_id` int NOT NULL COMMENT '字典分类',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`data_id`) USING BTREE,
  UNIQUE INDEX `sys_dict_data-type_id,value`(`value` ASC, `type_id` ASC) USING BTREE,
  INDEX `sys_dict_data-create_by`(`create_by` ASC) USING BTREE,
  INDEX `sys_dict_data-type_id`(`type_id` ASC) USING BTREE,
  CONSTRAINT `sys_dict_data-create_by` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `sys_dict_data-type_id` FOREIGN KEY (`type_id`) REFERENCES `sys_dict_type` (`type_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1, '0', '男', 1, 1, '2022-06-12 00:00:00', NULL);
INSERT INTO `sys_dict_data` VALUES (2, '1', '女', 1, 1, '2022-06-12 00:00:00', NULL);
INSERT INTO `sys_dict_data` VALUES (3, '2', '其他', 1, 1, '2022-06-11 00:00:00', NULL);
INSERT INTO `sys_dict_data` VALUES (23, '0', '正常', 5, 1, '2022-09-28 21:46:32', NULL);
INSERT INTO `sys_dict_data` VALUES (24, '1', '停用', 5, 1, '2022-09-28 21:46:57', NULL);
INSERT INTO `sys_dict_data` VALUES (27, '0', '接口', 7, 1, '2023-02-23 16:07:39', NULL);
INSERT INTO `sys_dict_data` VALUES (28, '1', '组件', 7, 1, '2023-02-23 16:07:45', NULL);

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `type_id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '类别名',
  `key` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标识符',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`type_id`) USING BTREE,
  UNIQUE INDEX `sys_dict_type-key`(`key` ASC) USING BTREE,
  INDEX `sys_dict_type-create_by`(`create_by` ASC) USING BTREE,
  CONSTRAINT `sys_dict_type-create_by` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典类型表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, '性别', 'sys:sex', NULL, 1, '2022-06-11 12:14:48');
INSERT INTO `sys_dict_type` VALUES (5, '账号状态', 'sys:user:status', NULL, 1, '2022-09-28 21:45:59');
INSERT INTO `sys_dict_type` VALUES (7, '权限类型', 'sys:auth:type', '权限类型', 1, '2023-02-23 16:07:26');

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file`  (
  `file_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '原文件名',
  `path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件存储路径',
  `size` bigint NULL DEFAULT NULL COMMENT '文件大小',
  `upload_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上传时间',
  `upload_user` bigint NULL DEFAULT NULL COMMENT '上传者',
  PRIMARY KEY (`file_id`) USING BTREE,
  INDEX `sys_file-upload_user`(`upload_user` ASC) USING BTREE,
  CONSTRAINT `sys_file-upload_user` FOREIGN KEY (`upload_user`) REFERENCES `sys_user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 92 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统文件表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_file
-- ----------------------------
INSERT INTO `sys_file` VALUES (41, '微信图片_20220312191446.jpg', 'public/image/20221003/19afa398-9430-46c5-ac3a-997259d48f16.jpg', 1781778, '2022-10-03 21:27:02', NULL);
INSERT INTO `sys_file` VALUES (84, 'O1CN01xQBnfA1jFkcVnJ3Ku_!!0-item_pic.jpg_300x300q90.jpg', 'public/image/20230224/9adf6db1-f120-4008-8753-f384dc3b8d9f.jpg', 40553, '2023-02-24 10:55:53', 2);
INSERT INTO `sys_file` VALUES (91, 'O1CN01bQLyCg221WqJc3tbA_!!903257060.jpg_300x300q90.jpg', 'public/image/20230224/9d41ebeb-66ac-4bb1-a9ea-4de9764f0790.jpg', 36247, '2023-02-24 16:10:40', 1);

-- ----------------------------
-- Table structure for sys_msg
-- ----------------------------
DROP TABLE IF EXISTS `sys_msg`;
CREATE TABLE `sys_msg`  (
  `msg_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '消息内容',
  `read` enum('0','1') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '是否已读（0：未读，1：已读）',
  `type` enum('0','1') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '消息类型（0：普通消息，1：图片）',
  `send_by` bigint NULL DEFAULT NULL COMMENT '发送人',
  `send_to` bigint NULL DEFAULT NULL COMMENT '接收人',
  `send_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  PRIMARY KEY (`msg_id`) USING BTREE,
  INDEX `sys_msg-send_by`(`send_by` ASC) USING BTREE,
  INDEX `sys_msg-send_to`(`send_to` ASC) USING BTREE,
  CONSTRAINT `sys_msg-send_by` FOREIGN KEY (`send_by`) REFERENCES `sys_user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `sys_msg-send_to` FOREIGN KEY (`send_to`) REFERENCES `sys_user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 180 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '消息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_msg
-- ----------------------------
INSERT INTO `sys_msg` VALUES (134, '在线开发', '1', '0', 2, 1, '2022-10-03 20:24:58');
INSERT INTO `sys_msg` VALUES (144, 'lia', '1', '0', 1, 2, '2022-10-04 12:52:57');
INSERT INTO `sys_msg` VALUES (148, '1', '1', '0', 1, 2, '2022-11-29 18:41:57');
INSERT INTO `sys_msg` VALUES (149, '123', '1', '0', 1, 2, '2022-11-29 20:02:55');
INSERT INTO `sys_msg` VALUES (150, '123123', '1', '0', 1, 2, '2022-12-01 20:51:06');
INSERT INTO `sys_msg` VALUES (151, '1', '1', '0', 89, 1, '2023-01-05 11:12:50');
INSERT INTO `sys_msg` VALUES (152, '1', '1', '0', 89, 1, '2023-01-05 11:17:25');
INSERT INTO `sys_msg` VALUES (153, '123', '1', '0', 1, 2, '2023-01-17 23:01:07');
INSERT INTO `sys_msg` VALUES (154, '321', '1', '0', 2, 1, '2023-01-17 23:01:10');
INSERT INTO `sys_msg` VALUES (155, '1', '1', '0', 1, 2, '2023-01-18 16:39:32');
INSERT INTO `sys_msg` VALUES (156, '322', '1', '0', 1, 2, '2023-01-19 01:39:14');
INSERT INTO `sys_msg` VALUES (157, '123123', '1', '0', 2, 1, '2023-01-19 03:00:40');
INSERT INTO `sys_msg` VALUES (158, 'ceshi', '1', '0', 2, 1, '2023-02-05 22:20:36');
INSERT INTO `sys_msg` VALUES (159, '111', '1', '0', 2, 1, '2023-02-05 22:25:40');
INSERT INTO `sys_msg` VALUES (160, '222aa', '1', '0', 2, 1, '2023-02-05 22:27:05');
INSERT INTO `sys_msg` VALUES (161, '1212', '1', '0', 2, 1, '2023-02-06 20:55:53');
INSERT INTO `sys_msg` VALUES (162, '111', '1', '0', 1, 2, '2023-02-06 20:59:20');
INSERT INTO `sys_msg` VALUES (163, '1', '1', '0', 2, 1, '2023-02-06 21:00:54');
INSERT INTO `sys_msg` VALUES (164, '111', '1', '0', 2, 1, '2023-02-06 21:01:23');
INSERT INTO `sys_msg` VALUES (165, '222', '1', '0', 2, 1, '2023-02-06 21:01:25');
INSERT INTO `sys_msg` VALUES (166, '1', '1', '0', 1, 2, '2023-02-06 21:08:42');
INSERT INTO `sys_msg` VALUES (167, '1', '1', '0', 1, 2, '2023-02-06 21:09:28');
INSERT INTO `sys_msg` VALUES (168, '2', '1', '0', 1, 2, '2023-02-06 21:09:41');
INSERT INTO `sys_msg` VALUES (169, '3', '1', '0', 1, 2, '2023-02-06 21:09:42');
INSERT INTO `sys_msg` VALUES (170, '2', '0', '0', 1, 2, '2023-02-17 00:04:37');
INSERT INTO `sys_msg` VALUES (171, '3', '0', '0', 1, 2, '2023-02-17 00:04:38');
INSERT INTO `sys_msg` VALUES (172, '1', '0', '0', 1, 2, '2023-02-17 00:04:39');
INSERT INTO `sys_msg` VALUES (173, '2', '0', '0', 1, 2, '2023-02-17 00:05:08');
INSERT INTO `sys_msg` VALUES (174, '3', '0', '0', 1, 2, '2023-02-17 00:05:10');
INSERT INTO `sys_msg` VALUES (175, '4', '0', '0', 1, 2, '2023-02-17 00:05:11');
INSERT INTO `sys_msg` VALUES (176, '5', '0', '0', 1, 2, '2023-02-17 00:05:11');
INSERT INTO `sys_msg` VALUES (177, '1', '0', '0', 1, 2, '2023-02-17 00:05:12');
INSERT INTO `sys_msg` VALUES (178, '321', '0', '0', 1, 2, '2023-02-19 14:51:59');
INSERT INTO `sys_msg` VALUES (179, '11', '0', '0', 1, 2, '2023-02-21 22:18:11');

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标题',
  `content` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '公告正文',
  `top_flag` enum('0','1') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '是否置顶(0：否，1：是)',
  `level` enum('0','1','2') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '重要程度(0：普通，1：重要，2：紧急)',
  `del_flag` enum('0','1') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '是否已删除(0：否，1：是)',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '通知公告' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO `sys_notice` VALUES (1, '11213123', '123', '0', '0', '0', 1, '2023-02-24 17:14:24', '2023-02-24 17:14:24');
INSERT INTO `sys_notice` VALUES (2, '11213123', '123', '0', '0', '0', 1, '2023-02-24 17:15:15', '2023-02-24 17:15:15');
INSERT INTO `sys_notice` VALUES (3, '11213123', '123', '0', '0', '0', 1, '2023-02-24 17:22:02', '2023-02-24 17:22:02');
INSERT INTO `sys_notice` VALUES (4, '1', NULL, '0', '0', '0', 1, '2023-02-24 21:58:50', '2023-02-24 21:58:50');
INSERT INTO `sys_notice` VALUES (5, '2', NULL, '0', '0', '0', 1, '2023-02-24 21:59:14', '2023-02-24 21:59:14');
INSERT INTO `sys_notice` VALUES (6, '1', NULL, '0', '0', '0', 1, '2023-02-24 22:00:57', '2023-02-24 22:00:57');

-- ----------------------------
-- Table structure for sys_param
-- ----------------------------
DROP TABLE IF EXISTS `sys_param`;
CREATE TABLE `sys_param`  (
  `param_id` int NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '参数名',
  `value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '参数值',
  `mean` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '说明',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`param_id`) USING BTREE,
  UNIQUE INDEX `sys_param-name`(`name` ASC) USING BTREE,
  INDEX `sys_param-create_by`(`create_by` ASC) USING BTREE,
  CONSTRAINT `sys_param-create_by` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统参数表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_param
-- ----------------------------
INSERT INTO `sys_param` VALUES (1, 'enable_register', 'true', '是否开启注册', 1, '2022-11-30 16:38:12', '是否开启注册');
INSERT INTO `sys_param` VALUES (3, 'login_check_code', 'false', '登录时是否需要验证码', 1, '2022-12-10 22:50:59', '登录时是否需要验证码');
INSERT INTO `sys_param` VALUES (5, 'register_check_code', 'true', '注册时是否需要验证码', 1, '2023-01-17 18:53:35', NULL);

-- ----------------------------
-- Table structure for sys_register_code
-- ----------------------------
DROP TABLE IF EXISTS `sys_register_code`;
CREATE TABLE `sys_register_code`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '注册码',
  `role_id` int NOT NULL COMMENT '该注册码可激活的角色',
  `use_by` bigint NULL DEFAULT NULL COMMENT '使用该注册码的用户ID',
  `use_time` datetime NULL DEFAULT NULL COMMENT '注册码被使用的时间',
  `create_by` bigint NOT NULL COMMENT '创建该注册码的用户ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `sys_register_code-code`(`code` ASC) USING BTREE,
  INDEX `sys_register_code-role_id`(`role_id` ASC) USING BTREE,
  INDEX `sys_register_code-use_by`(`use_by` ASC) USING BTREE,
  INDEX `sys_register_code-create_by`(`create_by` ASC) USING BTREE,
  CONSTRAINT `sys_register_code_ibfk_1` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sys_register_code_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sys_register_code_ibfk_3` FOREIGN KEY (`use_by`) REFERENCES `sys_user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '注册码' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_register_code
-- ----------------------------
INSERT INTO `sys_register_code` VALUES (1, 'Ndy6dNwdgwdh7s0UzZmO', 2, 99, '2023-01-17 19:19:24', 1, '2023-01-17 15:41:30');
INSERT INTO `sys_register_code` VALUES (2, 'v8v1ifVmyk0uRqMTBky9', 3, NULL, NULL, 1, '2023-01-17 15:41:38');
INSERT INTO `sys_register_code` VALUES (3, 'l5u6YdIxFYs3ZaCHoRBS', 3, 109, '2023-02-06 20:46:30', 1, '2023-01-17 15:41:38');
INSERT INTO `sys_register_code` VALUES (4, 'BHGEoqf2rnvZPoUy1grO', 1, NULL, NULL, 1, '2023-01-17 15:41:51');
INSERT INTO `sys_register_code` VALUES (5, 'k5Qh0x3WPAIbpZeswi5M', 3, 108, '2023-02-06 20:45:19', 1, '2023-01-17 15:41:51');
INSERT INTO `sys_register_code` VALUES (6, 'ybpkW1IQh8JZHWbDWYsg', 3, NULL, NULL, 1, '2023-01-17 15:41:51');
INSERT INTO `sys_register_code` VALUES (7, 'FmcaAgQ2JQUdGxsNwUMd', 1, 112, '2023-02-06 20:49:37', 1, '2023-01-17 15:41:51');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` int NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色权限字符串',
  `company_id` int NOT NULL COMMENT '所属企业',
  `superior` int NULL DEFAULT NULL COMMENT '上级',
  `root_router_id` int NULL DEFAULT 0 COMMENT '指定用户的根目录',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`) USING BTREE,
  UNIQUE INDEX `sys_role-key`(`key` ASC) USING BTREE,
  INDEX `sys_role-create_by`(`create_by` ASC) USING BTREE,
  INDEX `sys_role-superior`(`superior` ASC) USING BTREE,
  INDEX `sys_role-company_id`(`company_id` ASC) USING BTREE,
  INDEX `sys_role-root_router_id`(`root_router_id` ASC) USING BTREE,
  CONSTRAINT `sys_role-company_id` FOREIGN KEY (`company_id`) REFERENCES `sys_company` (`company_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sys_role-create_by` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `sys_role-root_router_id` FOREIGN KEY (`root_router_id`) REFERENCES `sys_router` (`router_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `sys_role-superior` FOREIGN KEY (`superior`) REFERENCES `sys_role` (`role_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 135 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '开发者', 'sys:user:admin', 1, NULL, 1, 1, '2022-05-06 15:52:00', '开发者');
INSERT INTO `sys_role` VALUES (2, '测试', 'sys:user:test', 1, 1, 3, 1, '2022-05-11 14:46:00', '测试');
INSERT INTO `sys_role` VALUES (3, '普通用户', 'sys:user:common', 1, 1, 1, 1, '2023-01-17 15:41:11', '默认用户角色');

-- ----------------------------
-- Table structure for sys_role_auth
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_auth`;
CREATE TABLE `sys_role_auth`  (
  `role_id` int NOT NULL COMMENT '角色ID（外键）',
  `auth_id` int NOT NULL COMMENT '权限ID（外键）',
  PRIMARY KEY (`role_id`, `auth_id`) USING BTREE,
  INDEX `sys_role_auth-role_id`(`role_id` ASC) USING BTREE,
  INDEX `sys_role_auth-auth_id`(`auth_id` ASC) USING BTREE,
  CONSTRAINT `sys_role_auth  -role_id` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sys_role_auth-auth_id` FOREIGN KEY (`auth_id`) REFERENCES `sys_auth` (`auth_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色-权限关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_auth
-- ----------------------------
INSERT INTO `sys_role_auth` VALUES (1, 1);
INSERT INTO `sys_role_auth` VALUES (1, 5);
INSERT INTO `sys_role_auth` VALUES (1, 6);
INSERT INTO `sys_role_auth` VALUES (1, 7);
INSERT INTO `sys_role_auth` VALUES (1, 8);
INSERT INTO `sys_role_auth` VALUES (1, 9);
INSERT INTO `sys_role_auth` VALUES (1, 10);
INSERT INTO `sys_role_auth` VALUES (1, 11);
INSERT INTO `sys_role_auth` VALUES (1, 12);
INSERT INTO `sys_role_auth` VALUES (1, 13);
INSERT INTO `sys_role_auth` VALUES (1, 14);
INSERT INTO `sys_role_auth` VALUES (1, 15);
INSERT INTO `sys_role_auth` VALUES (1, 16);
INSERT INTO `sys_role_auth` VALUES (1, 17);
INSERT INTO `sys_role_auth` VALUES (1, 18);
INSERT INTO `sys_role_auth` VALUES (1, 59);
INSERT INTO `sys_role_auth` VALUES (1, 60);
INSERT INTO `sys_role_auth` VALUES (1, 61);
INSERT INTO `sys_role_auth` VALUES (1, 62);
INSERT INTO `sys_role_auth` VALUES (1, 63);
INSERT INTO `sys_role_auth` VALUES (1, 64);
INSERT INTO `sys_role_auth` VALUES (1, 65);
INSERT INTO `sys_role_auth` VALUES (1, 66);
INSERT INTO `sys_role_auth` VALUES (1, 68);
INSERT INTO `sys_role_auth` VALUES (1, 69);
INSERT INTO `sys_role_auth` VALUES (1, 70);
INSERT INTO `sys_role_auth` VALUES (1, 72);
INSERT INTO `sys_role_auth` VALUES (1, 73);
INSERT INTO `sys_role_auth` VALUES (1, 74);
INSERT INTO `sys_role_auth` VALUES (1, 75);
INSERT INTO `sys_role_auth` VALUES (1, 76);
INSERT INTO `sys_role_auth` VALUES (1, 77);
INSERT INTO `sys_role_auth` VALUES (1, 78);
INSERT INTO `sys_role_auth` VALUES (1, 79);
INSERT INTO `sys_role_auth` VALUES (1, 81);
INSERT INTO `sys_role_auth` VALUES (1, 82);
INSERT INTO `sys_role_auth` VALUES (1, 83);
INSERT INTO `sys_role_auth` VALUES (1, 84);
INSERT INTO `sys_role_auth` VALUES (1, 85);
INSERT INTO `sys_role_auth` VALUES (1, 91);
INSERT INTO `sys_role_auth` VALUES (1, 92);
INSERT INTO `sys_role_auth` VALUES (1, 95);

-- ----------------------------
-- Table structure for sys_role_router
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_router`;
CREATE TABLE `sys_role_router`  (
  `role_id` int NOT NULL COMMENT '角色ID（外键）',
  `router_id` int NOT NULL COMMENT '权限ID（外键）',
  PRIMARY KEY (`role_id`, `router_id`) USING BTREE,
  INDEX `sys_role_router-router_id`(`router_id` ASC) USING BTREE,
  INDEX `sys_role_router-role_id`(`role_id` ASC) USING BTREE,
  CONSTRAINT `sys_role_router-role_id` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sys_role_router-router_id` FOREIGN KEY (`router_id`) REFERENCES `sys_router` (`router_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色-路由关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_router
-- ----------------------------
INSERT INTO `sys_role_router` VALUES (1, 2);
INSERT INTO `sys_role_router` VALUES (1, 3);
INSERT INTO `sys_role_router` VALUES (2, 3);
INSERT INTO `sys_role_router` VALUES (1, 4);
INSERT INTO `sys_role_router` VALUES (2, 4);
INSERT INTO `sys_role_router` VALUES (1, 5);
INSERT INTO `sys_role_router` VALUES (2, 5);
INSERT INTO `sys_role_router` VALUES (1, 6);
INSERT INTO `sys_role_router` VALUES (2, 6);
INSERT INTO `sys_role_router` VALUES (1, 7);
INSERT INTO `sys_role_router` VALUES (2, 7);
INSERT INTO `sys_role_router` VALUES (1, 38);
INSERT INTO `sys_role_router` VALUES (2, 38);
INSERT INTO `sys_role_router` VALUES (1, 40);
INSERT INTO `sys_role_router` VALUES (1, 41);
INSERT INTO `sys_role_router` VALUES (2, 41);
INSERT INTO `sys_role_router` VALUES (1, 44);
INSERT INTO `sys_role_router` VALUES (2, 44);
INSERT INTO `sys_role_router` VALUES (1, 47);
INSERT INTO `sys_role_router` VALUES (2, 47);

-- ----------------------------
-- Table structure for sys_router
-- ----------------------------
DROP TABLE IF EXISTS `sys_router`;
CREATE TABLE `sys_router`  (
  `router_id` int NOT NULL AUTO_INCREMENT COMMENT '路由ID',
  `label` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '路由名称',
  `path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '路由地址',
  `element` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组件地址',
  `parent` int NOT NULL DEFAULT 0 COMMENT '父路由',
  `index` int NULL DEFAULT NULL COMMENT '索引，决定路由展示的顺序',
  `icon` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '按钮图标',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`router_id`) USING BTREE,
  UNIQUE INDEX `sys_router-parent,path`(`path` ASC, `parent` ASC) USING BTREE,
  UNIQUE INDEX `sys_router-element`(`element` ASC) USING BTREE,
  INDEX `sys_router-create_by`(`create_by` ASC) USING BTREE,
  INDEX `sys_router-parent`(`parent` ASC) USING BTREE,
  CONSTRAINT `sys_router-create_by` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `sys_router-parent` FOREIGN KEY (`parent`) REFERENCES `sys_router` (`router_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '路由' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_router
-- ----------------------------
INSERT INTO `sys_router` VALUES (1, '根目录', '*', NULL, 1, 1, NULL, 1, '2022-06-05 02:55:58', NULL);
INSERT INTO `sys_router` VALUES (2, '首页', 'index', '/index/Index', 1, 0, 'BankOutlined', 1, '2023-02-24 10:33:54', NULL);
INSERT INTO `sys_router` VALUES (3, '系统管理', 'system', NULL, 1, 1, 'SettingOutlined', 1, '2022-05-09 10:51:00', NULL);
INSERT INTO `sys_router` VALUES (4, '用户管理', 'user', '/system/user/User', 3, 0, 'TeamOutlined', 1, '2022-05-09 10:55:00', NULL);
INSERT INTO `sys_router` VALUES (5, '权限管理', 'auth', '/system/auth/Auth', 3, 2, 'KeyOutlined', 1, '2022-05-09 11:01:00', NULL);
INSERT INTO `sys_router` VALUES (6, '路由管理', 'router', '/system/router/Router', 3, 3, 'GoldOutlined', 1, '2022-05-30 21:14:00', NULL);
INSERT INTO `sys_router` VALUES (7, '字典配置', 'dict', '/system/dict/Dict', 3, 5, 'PicRightOutlined', 1, '2022-06-11 11:25:44', NULL);
INSERT INTO `sys_router` VALUES (38, '系统工具', 'utils', NULL, 3, 7, 'CodeSandboxOutlined', 1, '2022-06-19 16:57:58', NULL);
INSERT INTO `sys_router` VALUES (40, '代码生成', 'codeGenerator', '/system/tool/codeGenerator/CodeGenerator', 38, 0, 'CopyrightOutlined', 1, '2022-06-19 17:00:51', NULL);
INSERT INTO `sys_router` VALUES (41, '企业管理', 'company', '/system/company/Company', 3, 1, 'VerifiedOutlined', 1, '2022-09-20 15:27:00', NULL);
INSERT INTO `sys_router` VALUES (44, '系统参数', 'param', '/system/param/Param', 3, 6, 'ProfileOutlined', 1, '2022-11-30 16:54:04', NULL);
INSERT INTO `sys_router` VALUES (47, '注册码', 'registerCode', '/system/registerCode/registerCode', 3, 4, 'FieldStringOutlined', 1, '2023-01-10 21:29:01', NULL);

-- ----------------------------
-- Table structure for sys_tool_code
-- ----------------------------
DROP TABLE IF EXISTS `sys_tool_code`;
CREATE TABLE `sys_tool_code`  (
  `code_id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `columns` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '表格字段信息',
  `module` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '包路径',
  `table_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '表格名',
  `primary_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键信息',
  `http_url` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '接口基本地址',
  `create_by_flag` enum('0','1') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '添加创建人字段(0:是，1:否)',
  `create_time_flag` enum('0','1') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '添加创建时间字段(0:是，1:否)',
  `update_time_flag` enum('0','1') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '添加更新时间字段(0:是，1:否)',
  `remark_flag` enum('0','1') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '添加备注字段(0:是，1:否)',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`code_id`) USING BTREE,
  INDEX `sys_tool_code-create_by`(`create_by` ASC) USING BTREE,
  CONSTRAINT `sys_tool_code-create_by` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 103 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '代码生成历史记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_tool_code
-- ----------------------------
INSERT INTO `sys_tool_code` VALUES (80, '[{\"name\":\"asd\",\"type\":\"String\",\"len\":0,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"\"}]', '', 'asd', '{\"name\":\"asdId\",\"type\":\"autoIncrement\"}', 'asd', '1', '1', '1', '1', 1, '2022-12-01 20:47:38');
INSERT INTO `sys_tool_code` VALUES (81, '[{\"name\":\"asd\",\"type\":\"String\",\"len\":0,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"\"}]', '', 'asd', '{\"name\":\"asdId\",\"type\":\"autoIncrement\"}', 'asd', '1', '1', '1', '1', 1, '2022-12-01 20:47:52');
INSERT INTO `sys_tool_code` VALUES (82, '[{\"name\":\"asd\",\"type\":\"String\",\"len\":0,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"\"}]', '', 'asd', '{\"name\":\"asdId\",\"type\":\"autoIncrement\"}', 'asd', '1', '1', '1', '1', 1, '2022-12-01 23:03:50');
INSERT INTO `sys_tool_code` VALUES (83, '[{\"name\":\"asd\",\"type\":\"String\",\"len\":0,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"\"}]', '', 'asd', '{\"name\":\"asdId\",\"type\":\"autoIncrement\"}', 'asd', '1', '1', '1', '1', 1, '2022-12-01 23:04:06');
INSERT INTO `sys_tool_code` VALUES (84, '[{\"name\":\"asd\",\"type\":\"String\",\"len\":0,\"notNull\":true,\"unique\":true,\"like\":true,\"remark\":\"\"}]', '', 'asd', '{\"name\":\"asdId\",\"type\":\"autoIncrement\"}', 'asd', '1', '1', '1', '1', 1, '2022-12-02 13:18:29');
INSERT INTO `sys_tool_code` VALUES (85, '[{\"name\":\"asd\",\"type\":\"String\",\"len\":0,\"notNull\":true,\"unique\":true,\"like\":true,\"remark\":\"\"}]', '', 'asd', '{\"name\":\"asdId\",\"type\":\"autoIncrement\"}', 'asd', '1', '1', '1', '1', 1, '2022-12-02 13:18:48');
INSERT INTO `sys_tool_code` VALUES (86, '[{\"name\":\"asd\",\"type\":\"String\",\"len\":0,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"\"}]', '', 'asd', '{\"name\":\"asdId\",\"type\":\"autoIncrement\"}', 'asd', '1', '1', '1', '1', 1, '2023-01-03 15:01:44');
INSERT INTO `sys_tool_code` VALUES (87, '[{\"name\":\"code\",\"type\":\"String\",\"len\":50,\"notNull\":true,\"unique\":true,\"like\":false,\"remark\":\"注册码\"},{\"name\":\"used\",\"type\":\"Character\",\"len\":1,\"notNull\":true,\"unique\":false,\"like\":false,\"remark\":\"是否已使用（0：否，1：是）\"},{\"name\":\"roleId\",\"type\":\"Integer\",\"len\":0,\"notNull\":true,\"unique\":false,\"like\":false,\"remark\":\"该注册码可激活的角色\"},{\"name\":\"useBy\",\"type\":\"Long\",\"len\":0,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"使用该注册码的用户ID\"},{\"name\":\"useTime\",\"type\":\"datetime\",\"len\":0,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"注册码被使用的时间\"}]', '', 'sys_register_code', '{\"name\":\"sysRegisterCodeId\",\"type\":\"autoIncrement\"}', '/system/register/code', '1', '1', '0', '0', 1, '2023-01-10 20:35:38');
INSERT INTO `sys_tool_code` VALUES (88, '[{\"name\":\"code\",\"type\":\"String\",\"len\":50,\"notNull\":true,\"unique\":true,\"like\":false,\"remark\":\"注册码\"},{\"name\":\"used\",\"type\":\"Character\",\"len\":1,\"notNull\":true,\"unique\":false,\"like\":false,\"remark\":\"是否已使用（0：否，1：是）\"},{\"name\":\"roleId\",\"type\":\"Integer\",\"len\":0,\"notNull\":true,\"unique\":false,\"like\":false,\"remark\":\"该注册码可激活的角色\"},{\"name\":\"useBy\",\"type\":\"Long\",\"len\":0,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"使用该注册码的用户ID\"},{\"name\":\"useTime\",\"type\":\"datetime\",\"len\":0,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"注册码被使用的时间\"}]', '', 'sys_register_code', '{\"name\":\"sysRegisterCodeId\",\"type\":\"autoIncrement\"}', '/system/register/code', '1', '1', '0', '1', 1, '2023-01-10 20:40:03');
INSERT INTO `sys_tool_code` VALUES (89, '[{\"name\":\"code\",\"type\":\"String\",\"len\":50,\"notNull\":true,\"unique\":true,\"like\":false,\"remark\":\"注册码\"},{\"name\":\"used\",\"type\":\"Character\",\"len\":1,\"notNull\":true,\"unique\":false,\"like\":false,\"remark\":\"是否已使用（0：否，1：是）\"},{\"name\":\"roleId\",\"type\":\"Integer\",\"len\":0,\"notNull\":true,\"unique\":false,\"like\":false,\"remark\":\"该注册码可激活的角色\"},{\"name\":\"useBy\",\"type\":\"Long\",\"len\":0,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"使用该注册码的用户ID\"},{\"name\":\"useTime\",\"type\":\"datetime\",\"len\":0,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"注册码被使用的时间\"}]', '', 'sys_register_code', '{\"name\":\"sysRegisterCodeId\",\"type\":\"autoIncrement\"}', '/system/register/code', '1', '1', '0', '0', 1, '2023-01-10 20:40:11');
INSERT INTO `sys_tool_code` VALUES (90, '[{\"name\":\"code\",\"type\":\"String\",\"len\":50,\"notNull\":true,\"unique\":true,\"like\":false,\"remark\":\"注册码\"},{\"name\":\"used\",\"type\":\"Character\",\"len\":1,\"notNull\":true,\"unique\":false,\"like\":false,\"remark\":\"是否已使用（0：否，1：是）\"},{\"name\":\"roleId\",\"type\":\"Integer\",\"len\":0,\"notNull\":true,\"unique\":false,\"like\":false,\"remark\":\"该注册码可激活的角色\"},{\"name\":\"useBy\",\"type\":\"Long\",\"len\":0,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"使用该注册码的用户ID\"},{\"name\":\"useTime\",\"type\":\"datetime\",\"len\":0,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"注册码被使用的时间\"}]', '', 'sys_register_code', '{\"name\":\"sysRegisterCodeId\",\"type\":\"autoIncrement\"}', '/system/register/code', '1', '1', '0', '0', 1, '2023-01-10 20:40:17');
INSERT INTO `sys_tool_code` VALUES (91, '[{\"name\":\"code\",\"type\":\"String\",\"len\":50,\"notNull\":true,\"unique\":true,\"like\":false,\"remark\":\"注册码\"},{\"name\":\"used\",\"type\":\"Character\",\"len\":1,\"notNull\":true,\"unique\":false,\"like\":false,\"remark\":\"是否已使用（0：否，1：是）\"},{\"name\":\"roleId\",\"type\":\"Integer\",\"len\":0,\"notNull\":true,\"unique\":false,\"like\":false,\"remark\":\"该注册码可激活的角色\"},{\"name\":\"useBy\",\"type\":\"Long\",\"len\":0,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"使用该注册码的用户ID\"},{\"name\":\"useTime\",\"type\":\"datetime\",\"len\":0,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"注册码被使用的时间\"}]', '', 'sys_register_code', '{\"name\":\"sysRegisterCodeId\",\"type\":\"autoIncrement\"}', '/system/register/code', '1', '1', '0', '0', 1, '2023-01-10 20:42:57');
INSERT INTO `sys_tool_code` VALUES (92, '[{\"name\":\"code\",\"type\":\"String\",\"len\":50,\"notNull\":true,\"unique\":true,\"like\":false,\"remark\":\"注册码\"},{\"name\":\"used\",\"type\":\"Character\",\"len\":1,\"notNull\":true,\"unique\":false,\"like\":false,\"remark\":\"是否已使用（0：否，1：是）\"},{\"name\":\"roleId\",\"type\":\"Integer\",\"len\":0,\"notNull\":true,\"unique\":false,\"like\":false,\"remark\":\"该注册码可激活的角色\"},{\"name\":\"useBy\",\"type\":\"Long\",\"len\":0,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"使用该注册码的用户ID\"},{\"name\":\"useTime\",\"type\":\"datetime\",\"len\":0,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"注册码被使用的时间\"}]', '', 'sys_register_code', '{\"name\":\"sysRegisterCodeId\",\"type\":\"autoIncrement\"}', '/system/register/code', '1', '1', '0', '0', 1, '2023-01-10 20:44:49');
INSERT INTO `sys_tool_code` VALUES (93, '[{\"name\":\"code\",\"type\":\"String\",\"len\":50,\"notNull\":true,\"unique\":true,\"like\":false,\"remark\":\"注册码\"},{\"name\":\"used\",\"type\":\"Character\",\"len\":1,\"notNull\":true,\"unique\":false,\"like\":false,\"remark\":\"是否已使用（0：否，1：是）\"},{\"name\":\"roleId\",\"type\":\"Integer\",\"len\":0,\"notNull\":true,\"unique\":false,\"like\":false,\"remark\":\"该注册码可激活的角色\"},{\"name\":\"useBy\",\"type\":\"Long\",\"len\":0,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"使用该注册码的用户ID\"},{\"name\":\"useTime\",\"type\":\"datetime\",\"len\":0,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"注册码被使用的时间\"}]', '', 'sys_register_code', '{\"name\":\"sysRegisterCodeId\",\"type\":\"autoIncrement\"}', '/system/register/code', '1', '1', '0', '0', 1, '2023-01-10 20:45:48');
INSERT INTO `sys_tool_code` VALUES (94, '[{\"name\":\"code\",\"type\":\"String\",\"len\":50,\"notNull\":true,\"unique\":true,\"like\":false,\"remark\":\"注册码\"},{\"name\":\"used\",\"type\":\"Character\",\"len\":1,\"notNull\":true,\"unique\":false,\"like\":false,\"remark\":\"是否已使用（0：否，1：是）\"},{\"name\":\"roleId\",\"type\":\"Integer\",\"len\":0,\"notNull\":true,\"unique\":false,\"like\":false,\"remark\":\"该注册码可激活的角色\"},{\"name\":\"useBy\",\"type\":\"Long\",\"len\":0,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"使用该注册码的用户ID\"},{\"name\":\"useTime\",\"type\":\"datetime\",\"len\":0,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"注册码被使用的时间\"}]', '', 'sys_register_code', '{\"name\":\"sysRegisterCodeId\",\"type\":\"autoIncrement\"}', '/system/register/code', '1', '1', '0', '0', 1, '2023-01-10 20:47:25');
INSERT INTO `sys_tool_code` VALUES (95, '[{\"name\":\"code\",\"type\":\"String\",\"len\":50,\"notNull\":true,\"unique\":true,\"like\":false,\"remark\":\"注册码\"},{\"name\":\"used\",\"type\":\"Character\",\"len\":1,\"notNull\":true,\"unique\":false,\"like\":false,\"remark\":\"是否已使用（0：否，1：是）\"},{\"name\":\"roleId\",\"type\":\"Integer\",\"len\":0,\"notNull\":true,\"unique\":false,\"like\":false,\"remark\":\"该注册码可激活的角色\"},{\"name\":\"useBy\",\"type\":\"Long\",\"len\":0,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"使用该注册码的用户ID\"},{\"name\":\"useTime\",\"type\":\"datetime\",\"len\":0,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"注册码被使用的时间\"}]', 'asd', 'sys_register_code', '{\"name\":\"id\",\"type\":\"autoIncrement\"}', '/system/register/code', '1', '1', '0', '1', 1, '2023-01-10 20:47:39');
INSERT INTO `sys_tool_code` VALUES (97, '[{\"name\":\"asdasd\",\"type\":\"String\",\"len\":0,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"\"}]', 'com.as', 'asd', '{\"name\":\"id\",\"type\":\"autoIncrement\"}', NULL, '1', '1', '1', '1', 1, '2023-02-07 20:57:21');
INSERT INTO `sys_tool_code` VALUES (98, '[{\"name\":\"title\",\"type\":\"String\",\"len\":100,\"notNull\":true,\"unique\":false,\"like\":true,\"remark\":\"标题\"},{\"name\":\"content\",\"type\":\"String\",\"len\":2000,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"公告正文\"},{\"name\":\"topFlag\",\"type\":\"Character\",\"len\":1,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"是否置顶\"},{\"name\":\"level\",\"type\":\"Character\",\"len\":1,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"重要程度\"}]', 'com.lia.system', 'sys_notice', '{\"name\":\"id\",\"type\":\"autoIncrement\"}', '/system/notice', '1', '1', '1', '0', 1, '2023-02-23 15:40:19');
INSERT INTO `sys_tool_code` VALUES (99, '[{\"name\":\"title\",\"type\":\"String\",\"len\":100,\"notNull\":true,\"unique\":false,\"like\":true,\"remark\":\"标题\"},{\"name\":\"content\",\"type\":\"String\",\"len\":2000,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"公告正文\"},{\"name\":\"topFlag\",\"type\":\"Character\",\"len\":1,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"是否置顶\"},{\"name\":\"level\",\"type\":\"Character\",\"len\":1,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"重要程度\"}]', 'com.lia.system', 'sys_notice', '{\"name\":\"id\",\"type\":\"autoIncrement\"}', '/system/notice', '1', '1', '1', '0', 1, '2023-02-23 15:43:33');
INSERT INTO `sys_tool_code` VALUES (100, '[{\"name\":\"title\",\"type\":\"String\",\"len\":100,\"notNull\":true,\"unique\":false,\"like\":true,\"remark\":\"标题\"},{\"name\":\"content\",\"type\":\"String\",\"len\":2000,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"公告正文\"},{\"name\":\"topFlag\",\"type\":\"Character\",\"len\":1,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"是否置顶\"},{\"name\":\"level\",\"type\":\"Character\",\"len\":1,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"重要程度\"}]', 'com.lia.system', 'sys_notice', '{\"name\":\"id\",\"type\":\"autoIncrement\"}', '/system/notice', '1', '1', '1', '0', 1, '2023-02-23 15:46:34');
INSERT INTO `sys_tool_code` VALUES (101, '[{\"name\":\"title\",\"type\":\"String\",\"len\":100,\"notNull\":true,\"unique\":false,\"like\":true,\"remark\":\"标题\"},{\"name\":\"content\",\"type\":\"String\",\"len\":2000,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"公告正文\"},{\"name\":\"topFlag\",\"type\":\"Character\",\"len\":1,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"是否置顶\"},{\"name\":\"level\",\"type\":\"Character\",\"len\":1,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"重要程度\"},{\"name\":\"delFlag\",\"type\":\"Character\",\"len\":1,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"是否删除\"}]', 'com.lia.system', 'sys_notice', '{\"name\":\"id\",\"type\":\"autoIncrement\"}', '/system/notice', '1', '1', '1', '0', 1, '2023-02-23 15:47:21');
INSERT INTO `sys_tool_code` VALUES (102, '[{\"name\":\"title\",\"type\":\"String\",\"len\":100,\"notNull\":true,\"unique\":false,\"like\":true,\"remark\":\"标题\"},{\"name\":\"content\",\"type\":\"String\",\"len\":2000,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"公告正文\"},{\"name\":\"topFlag\",\"type\":\"Character\",\"len\":1,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"是否置顶(0：否，1：是)\"},{\"name\":\"level\",\"type\":\"Character\",\"len\":1,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"重要程度(0：普通，1：重要，2：紧急)\"},{\"name\":\"delFlag\",\"type\":\"Character\",\"len\":1,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"是否删除(0：否，1：是)\"}]', 'com.lia.system', 'sys_notice', '{\"name\":\"id\",\"type\":\"autoIncrement\"}', '/system/notice', '1', '1', '1', '0', 1, '2023-02-23 15:49:39');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `nick` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '昵称',
  `role_id` int NULL DEFAULT NULL COMMENT '用户角色ID',
  `sex` enum('0','1','2') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '性别（0男 1女 2其他）',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号码',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `head_img` bigint NULL DEFAULT NULL COMMENT '头像地址',
  `status` enum('0','1') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` enum('0','1') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`) USING BTREE,
  INDEX `sys_user-create_by`(`create_by` ASC) USING BTREE,
  INDEX `sys_user-role_id`(`role_id` ASC) USING BTREE,
  INDEX `sys_user-head_img`(`head_img` ASC) USING BTREE,
  CONSTRAINT `sys_user-create_by` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `sys_user-head_img` FOREIGN KEY (`head_img`) REFERENCES `sys_file` (`file_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `sys_user-role_id` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`role_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 114 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '$2a$10$kf7PjaPF69ynXBXI3DMaWeKJB2a74Nw.coLbCAb4.JsUrUR5.2yd.', '开发者', 1, '0', '18150027197', '1072864729@qq.com', 91, '0', '0', 1, '2022-05-05 13:10:05', '最高权限，不可删除');
INSERT INTO `sys_user` VALUES (2, 'test', '$2a$10$kf7PjaPF69ynXBXI3DMaWeKJB2a74Nw.coLbCAb4.JsUrUR5.2yd.', '测试', 2, '0', '18150027197', '1072864729@qq.com', 84, '0', '0', 1, '2022-05-17 02:12:00', '测试账号，不可删除');
INSERT INTO `sys_user` VALUES (89, '1', '$2a$10$30cfIcdCW/iM.mSEI5FQpejfBPjLsltvOXMGtxWgg3B2NL4f2wSBq', '2', NULL, '0', NULL, NULL, NULL, '0', '1', 2, '2023-01-05 11:12:37', NULL);
INSERT INTO `sys_user` VALUES (90, '1', '$2a$10$a5ubpT8n2sGATK3jtBSc5.PxegJx4lDqvtlGNWkFk.dVUCJJOoaZy', '1', 1, NULL, NULL, NULL, NULL, '0', '1', 1, '2023-01-10 20:21:33', NULL);
INSERT INTO `sys_user` VALUES (91, 'test1', '$2a$10$2zzNf4qt58fgCskHm43Iv.RjtjdQdv0bryX.C9lpeAoHW3iv2b6FC', '123', 1, NULL, NULL, NULL, NULL, '0', '1', 1, '2023-01-17 17:53:31', NULL);
INSERT INTO `sys_user` VALUES (92, '123', '$2a$10$agdBeFVNVqsPBKRyadYrP.atghSf7d1.8/hSOnQgV9H.5EpUAKFwK', '123', 3, NULL, NULL, NULL, NULL, '0', '1', 1, '2023-01-17 18:24:35', NULL);
INSERT INTO `sys_user` VALUES (93, '12333', '$2a$10$De.rZ7nke6d0z3Eh.oOCOedqwCrEcVzkEddzpmMiZJYO4NIsnEkge', '333', 3, NULL, NULL, NULL, NULL, '0', '1', 1, '2023-01-17 18:25:15', NULL);
INSERT INTO `sys_user` VALUES (94, '1', '$2a$10$KnjeCUy0snKmZset3jRgN.wCfBA9JJdsH0xKuOfBYtYitSKKK4p0C', '1', 3, NULL, NULL, NULL, NULL, '0', '1', NULL, '2023-01-17 19:07:59', NULL);
INSERT INTO `sys_user` VALUES (95, '2', '$2a$10$WNShXe4TuX4dwUk6R6LHcev.SFNUS8LXwAUyg9UFRsaKB99Sq71Ri', '2', 3, NULL, NULL, NULL, NULL, '0', '1', NULL, '2023-01-17 19:10:08', NULL);
INSERT INTO `sys_user` VALUES (96, '3', '$2a$10$SvZFPH5/ymEOVFO.i0LSROZyOWOjfTcDVfIPjJLeJm.1NvwG4Wf.6', '2', 1, NULL, NULL, NULL, NULL, '0', '1', NULL, '2023-01-17 19:11:30', NULL);
INSERT INTO `sys_user` VALUES (97, '4', '$2a$10$PPHRzeUSHLqJNGSCW8HquuFQ9dsHaZrOnEtyOAxjUv87fmSV.JVla', '2', 1, NULL, NULL, NULL, NULL, '0', '1', NULL, '2023-01-17 19:17:05', NULL);
INSERT INTO `sys_user` VALUES (98, '5', '$2a$10$VXXvEMmegllKwW2DIE28GOrVjgrU/QA/galqXzDu6hKqcqyoHgGAS', '2', 1, NULL, NULL, NULL, NULL, '0', '1', NULL, '2023-01-17 19:18:11', NULL);
INSERT INTO `sys_user` VALUES (99, '6', '$2a$10$li7bwJGFOVrF48QUGjGeJ.YkDLb3ubx/YhQ4TGIsU7Fpb/OYb6ceK', '2', 1, NULL, NULL, NULL, NULL, '0', '1', NULL, '2023-01-17 19:19:24', NULL);
INSERT INTO `sys_user` VALUES (101, '1111', '$2a$10$TmgHz1rCrqhhzJtzSulGveLE9Gcbx.Q/nbNQYvnfs6499Rf6X3Eti', '1', 3, NULL, NULL, NULL, NULL, '0', '1', NULL, '2023-01-17 19:22:51', NULL);
INSERT INTO `sys_user` VALUES (102, '321', '$2a$10$xAga9.PUHvJ0hcyfbQGPveI88QoRXVAjPWPtCpRRFyEknRrIkFosi', '1', 3, NULL, NULL, NULL, NULL, '0', '1', NULL, '2023-01-17 19:23:05', NULL);
INSERT INTO `sys_user` VALUES (103, '3', '$2a$10$lPQNe8l9qlKiV3sMQ8WFkePLj4LGv5GFQY2k77zC0bLCyjYV/zONa', '3', 3, NULL, NULL, NULL, NULL, '0', '1', 1, '2023-01-17 22:36:35', NULL);
INSERT INTO `sys_user` VALUES (104, '11111111', '$2a$10$w.Ag79GqpOPRh2rI9y4Uj.t0nBHpKb4/eNqMst.7KGr1KLW.BcLR2', '1', 3, NULL, NULL, NULL, NULL, '0', '1', NULL, '2023-01-18 16:58:32', NULL);
INSERT INTO `sys_user` VALUES (105, '1111111111', '$2a$10$4AZFS6WvrZXms3gBOdsdx.dO95CvcwdcAUuj6j3cyyBSykJ2pahs6', '1111111111', 3, NULL, NULL, NULL, NULL, '0', '1', 1, '2023-01-19 00:47:14', NULL);
INSERT INTO `sys_user` VALUES (106, '33333333333', '$2a$10$Kc3XpYEjNuX7Say2ML7Cp.444PEIZPwka2/OtYz4Km2/W/XTXCC6u', '1', 3, NULL, NULL, NULL, NULL, '0', '1', 1, '2023-01-19 01:16:25', NULL);
INSERT INTO `sys_user` VALUES (108, 'commonnn', '$2a$10$gD/vsGC0PoHeNRKbbtEna.mFGq/DMzU6c8QlHOXQsgx5vWREKi9Ou', '普通用户', 3, NULL, NULL, NULL, NULL, '0', '1', NULL, '2023-02-06 20:45:19', NULL);
INSERT INTO `sys_user` VALUES (109, 'asdasdasdasd', '$2a$10$0LI7UIrPlBfk4OGkC1ts9evtSHP1qlct9U5mKO8bQT29msDn1G342', 'asdasdsad', 3, NULL, NULL, NULL, NULL, '0', '1', NULL, '2023-02-06 20:46:30', NULL);
INSERT INTO `sys_user` VALUES (110, 'asdssssss', '$2a$10$5ZjD.WUj8dl6oX9pDLIUkO78ienHJLoeuvyI.RKdHAlfhXB/7dIMO', 'asdasd', 3, NULL, NULL, NULL, NULL, '0', '1', NULL, '2023-02-06 20:48:15', NULL);
INSERT INTO `sys_user` VALUES (111, 'asdssssss1', '$2a$10$fmfyML.BgGCGN3PdVGv7we/mhPoSKUqZQkLEh2T2o7.27OrGDBMM.', 'asdasd', 3, NULL, NULL, NULL, NULL, '0', '1', NULL, '2023-02-06 20:48:37', NULL);
INSERT INTO `sys_user` VALUES (112, 'kkkkkkkk', '$2a$10$3//.8roSB1Uw5mRo6KGv0O6U0gHp/fB0JZBNpyrG0zSMVjQFBbqhC', 'kkk', 1, NULL, NULL, NULL, NULL, '0', '1', NULL, '2023-02-06 20:49:37', NULL);
INSERT INTO `sys_user` VALUES (113, 'aaaaaaaaaaaaaa', '$2a$10$VxfstUG36n7/aMf7NaRhQuXX91at3iTlWOozw.3lFW6yluOfbqg0O', '啊啊啊啊啊啊啊啊啊啊', 3, NULL, NULL, NULL, NULL, '0', '1', NULL, '2023-02-07 23:45:10', NULL);

SET FOREIGN_KEY_CHECKS = 1;
