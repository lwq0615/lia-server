/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : lia

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 04/01/2023 21:41:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_auth
-- ----------------------------
DROP TABLE IF EXISTS `sys_auth`;
CREATE TABLE `sys_auth`  (
  `auth_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限名称',
  `url` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限接口地址',
  `key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限标识',
  `router_id` int(0) NOT NULL COMMENT '所属路由',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`auth_id`) USING BTREE,
  UNIQUE INDEX `sys_auth-url`(`url`) USING BTREE,
  UNIQUE INDEX `sys_auth-key`(`key`) USING BTREE,
  INDEX `sys_auth-create_by`(`create_by`) USING BTREE,
  INDEX `sys_auth-router_id`(`router_id`) USING BTREE,
  CONSTRAINT `sys_auth-create_by` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `sys_auth-router_id` FOREIGN KEY (`router_id`) REFERENCES `sys_router` (`router_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 80 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_auth
-- ----------------------------
INSERT INTO `sys_auth` VALUES (1, '查询用户', '/system/user/getPage', 'system:user:getPage', 3, 1, '2022-05-06 17:25:00', NULL);
INSERT INTO `sys_auth` VALUES (3, '查询角色字典表', '/system/role/sysRoleDict', 'system:role:sysRoleDict', 41, 1, '2022-05-11 15:10:18', NULL);
INSERT INTO `sys_auth` VALUES (5, '添加或编辑用户', '/system/user/saveUser', 'system:user:saveUser', 3, 1, '2022-05-12 17:16:06', NULL);
INSERT INTO `sys_auth` VALUES (6, '批量删除用户', '/system/user/deleteUsers', 'system:user:deleteUsers', 3, 1, '2022-05-15 20:28:25', NULL);
INSERT INTO `sys_auth` VALUES (7, '获取创建人字典表', '/system/user/getCreateByDict', 'system:user:getCreateByDict', 3, 1, '2022-05-16 14:38:54', NULL);
INSERT INTO `sys_auth` VALUES (8, '查询路由树', '/system/router/getRouterTree', 'system:router:getRouterTree', 5, 1, '2022-05-30 14:55:21', NULL);
INSERT INTO `sys_auth` VALUES (9, '保存路由', '/system/router/saveRouter', 'system:router:saveRouter', 5, 1, '2022-05-30 15:19:25', NULL);
INSERT INTO `sys_auth` VALUES (10, '批量删除路由', '/system/router/deleteRouters', 'system:router:deleteRouters', 5, 1, '2022-05-30 15:19:50', NULL);
INSERT INTO `sys_auth` VALUES (11, '批量删除权限', '/system/auth/delete', 'system:auth:delete', 4, 1, '2022-06-11 11:51:33', NULL);
INSERT INTO `sys_auth` VALUES (12, '根据id查询路由', '/system/router/getRouterById', 'system:router:getRouterById', 5, 1, '2022-06-06 01:55:50', NULL);
INSERT INTO `sys_auth` VALUES (13, '查询角色列表', '/system/role/getPage', 'system:role:getPage', 41, 1, '2022-06-10 23:09:27', NULL);
INSERT INTO `sys_auth` VALUES (14, '新增或编辑角色', '/system/role/saveRole', 'system:role:saveRole', 41, 1, '2022-06-10 23:31:23', NULL);
INSERT INTO `sys_auth` VALUES (15, '批量删除角色', '/system/role/deleteRoles', 'system:role:deleteRoles', 41, 1, '2022-06-10 23:32:29', NULL);
INSERT INTO `sys_auth` VALUES (16, '获取权限字典表', '/system/auth/sysAuthDict', 'system:auth:sysAuthDict', 4, 1, '2022-06-11 01:20:20', NULL);
INSERT INTO `sys_auth` VALUES (17, '分页查询权限', '/system/auth/getPage', 'system:auth:getPage', 4, 1, '2022-06-11 11:50:18', NULL);
INSERT INTO `sys_auth` VALUES (18, '新增或编辑权限', '/system/auth/save', 'system:auth:save', 4, 1, '2022-06-11 11:50:58', NULL);
INSERT INTO `sys_auth` VALUES (59, '企业管理分页查询', '/system/company/getPage', 'system:company:getPage', 41, 1, '2022-09-20 15:51:20', NULL);
INSERT INTO `sys_auth` VALUES (60, '企业管理新增和编辑', '/system/company/save', 'system:company:save', 41, 1, '2022-09-20 15:51:51', NULL);
INSERT INTO `sys_auth` VALUES (61, '企业管理批量删除', '/system/company/delete', 'system:company:delete', 41, 1, '2022-09-20 15:52:15', NULL);
INSERT INTO `sys_auth` VALUES (62, '获取企业字典表', '/system/company/sysCompanyDict', 'system:company:sysCompanyDict', 41, 1, '2022-09-20 19:56:00', NULL);
INSERT INTO `sys_auth` VALUES (63, '获取某企业下的角色字典表', '/system/role/getRoleOfCompanyDict', 'system:role:getRoleOfCompanyDict', 41, 1, '2022-09-20 20:37:00', '根据企业ID获取某企业下的角色字典表');
INSERT INTO `sys_auth` VALUES (64, '字典类别分页查询', '/system/dictType/getPage', 'system:dictType:getPage', 6, 1, '2022-09-23 11:21:13', NULL);
INSERT INTO `sys_auth` VALUES (65, '字典类别新增和编辑', '/system/dictType/save', 'system:dictType:save', 6, 1, '2022-09-23 11:21:40', NULL);
INSERT INTO `sys_auth` VALUES (66, '批量删除字典类别', '/system/dictType/delete', 'system:dictType:delete', 6, 1, '2022-09-23 11:22:05', NULL);
INSERT INTO `sys_auth` VALUES (68, '字典数据分页查询', '/system/dictData/getPage', 'system:dictData:getPage', 6, 1, '2022-09-23 11:47:42', NULL);
INSERT INTO `sys_auth` VALUES (69, '字典数据新增或编辑', '/system/dictData/:save', 'system:dictData:save', 6, 1, '2022-09-23 11:48:28', NULL);
INSERT INTO `sys_auth` VALUES (70, '字典数据批量删除', '/system/dictData/delete', 'system:dictData:delete', 6, 1, '2022-09-23 11:48:55', NULL);
INSERT INTO `sys_auth` VALUES (72, '获取性别字典表', '/system/dictData/getSexDict', 'system:dictData:getSexDict', 6, 1, '2022-09-23 12:08:03', NULL);
INSERT INTO `sys_auth` VALUES (73, '代码生成添加或编辑记录', '/system/tool/code/save', 'system:tool:code:save', 40, 1, '2022-09-23 16:10:40', NULL);
INSERT INTO `sys_auth` VALUES (74, '代码生成分页查询', '/system/tool/code/getPage', 'system:tool:code:getPage', 40, 1, '2022-09-23 16:12:12', NULL);
INSERT INTO `sys_auth` VALUES (75, '代码生成批量删除', '/system/tool/code/delete', 'system:tool:code:delete', 40, 1, '2022-09-23 16:18:15', NULL);
INSERT INTO `sys_auth` VALUES (76, '角色状态字典表', '/system/dictData/getUserStatusDict', 'system:dictData:getUserStatusDict', 6, 1, '2022-09-28 21:51:47', NULL);
INSERT INTO `sys_auth` VALUES (77, '分页查询系统参数', '/system/param/getPage', 'system:param:getPage', 44, 1, '2022-11-30 16:55:39', NULL);
INSERT INTO `sys_auth` VALUES (78, '新增或编辑参数', '/system/param/save', 'system:param:save', 44, 1, '2022-11-30 16:56:13', NULL);
INSERT INTO `sys_auth` VALUES (79, '批量删除参数', '/system/param/delete', 'system:param:delete', 44, 1, '2022-11-30 16:56:37', NULL);

-- ----------------------------
-- Table structure for sys_company
-- ----------------------------
DROP TABLE IF EXISTS `sys_company`;
CREATE TABLE `sys_company`  (
  `company_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '企业名称',
  `principal` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '企业负责人',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '负责人联系方式',
  `address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '企业地址',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱地址',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`company_id`) USING BTREE,
  UNIQUE INDEX `sys_company-name`(`name`) USING BTREE COMMENT '企业名称唯一',
  INDEX `sys_company-create_by`(`create_by`) USING BTREE,
  CONSTRAINT `sys_company-create_by` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_company
-- ----------------------------
INSERT INTO `sys_company` VALUES (1, '泉州师范学院', '李伟强', '18150027197', '福建省泉州市丰泽区东海大街398号', '1072864729@qq.com', 1, '2022-09-20 16:06:00', '学校');

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `data_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典数据key',
  `label` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典数据值',
  `type_id` int(0) NOT NULL COMMENT '字典分类',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`data_id`) USING BTREE,
  INDEX `sys_dict_data-create_by`(`create_by`) USING BTREE,
  INDEX `sys_dict_data-type_id`(`type_id`) USING BTREE,
  CONSTRAINT `sys_dict_data-create_by` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `sys_dict_data-type_id` FOREIGN KEY (`type_id`) REFERENCES `sys_dict_type` (`type_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1, '0', '男', 1, 1, '2022-06-12 00:00:00', NULL);
INSERT INTO `sys_dict_data` VALUES (2, '1', '女', 1, 1, '2022-06-12 00:00:00', NULL);
INSERT INTO `sys_dict_data` VALUES (3, '2', '其他', 1, 1, '2022-06-11 00:00:00', NULL);
INSERT INTO `sys_dict_data` VALUES (23, '0', '正常', 5, 1, '2022-09-28 21:46:32', NULL);
INSERT INTO `sys_dict_data` VALUES (24, '1', '停用', 5, 1, '2022-09-28 21:46:57', NULL);

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `type_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '类别名',
  `key` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标识符',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`type_id`) USING BTREE,
  UNIQUE INDEX `sys_dict_type-key`(`key`) USING BTREE,
  INDEX `sys_dict_type-create_by`(`create_by`) USING BTREE,
  CONSTRAINT `sys_dict_type-create_by` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, '性别', 'sys:sex', NULL, 1, '2022-06-11 12:14:48');
INSERT INTO `sys_dict_type` VALUES (5, '账号状态', 'sys:user:status', NULL, 1, '2022-09-28 21:45:59');

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file`  (
  `file_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '原文件名',
  `path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件存储路径',
  `size` bigint(0) NULL DEFAULT NULL COMMENT '文件大小',
  `upload_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上传时间',
  `upload_user` bigint(0) NULL DEFAULT NULL COMMENT '上传者',
  PRIMARY KEY (`file_id`) USING BTREE,
  INDEX `sys_file-upload_user`(`upload_user`) USING BTREE,
  CONSTRAINT `sys_file-upload_user` FOREIGN KEY (`upload_user`) REFERENCES `sys_user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_file
-- ----------------------------
INSERT INTO `sys_file` VALUES (41, '微信图片_20220312191446.jpg', 'public/image/20221003/19afa398-9430-46c5-ac3a-997259d48f16.jpg', 1781778, '2022-10-03 21:27:02', 85);
INSERT INTO `sys_file` VALUES (45, '微信图片_202203121913362.jpg', 'public/image/20221003/f1881fa8-79a2-4a8f-89f4-6b22219d3f9f.jpg', 241151, '2022-10-03 21:52:39', 2);
INSERT INTO `sys_file` VALUES (56, '屏幕截图_20230101_190847.png', 'public/image/20230103/4790e857-e5cd-4ed1-9eba-b330158d5370.png', 2094083, '2023-01-03 20:26:39', 1);

-- ----------------------------
-- Table structure for sys_msg
-- ----------------------------
DROP TABLE IF EXISTS `sys_msg`;
CREATE TABLE `sys_msg`  (
  `msg_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '消息内容',
  `read` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '是否已读（0：未读，1：已读）',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '消息类型（0：普通消息，1：图片）',
  `send_by` bigint(0) NULL DEFAULT NULL COMMENT '发送人',
  `send_to` bigint(0) NULL DEFAULT NULL COMMENT '接收人',
  `send_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '发送时间',
  PRIMARY KEY (`msg_id`) USING BTREE,
  INDEX `sys_msg-send_by`(`send_by`) USING BTREE,
  INDEX `sys_msg-send_to`(`send_to`) USING BTREE,
  CONSTRAINT `sys_msg-send_by` FOREIGN KEY (`send_by`) REFERENCES `sys_user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `sys_msg-send_to` FOREIGN KEY (`send_to`) REFERENCES `sys_user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 151 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_msg
-- ----------------------------
INSERT INTO `sys_msg` VALUES (134, '在线开发', '1', '0', 2, 1, '2022-10-03 20:24:58');
INSERT INTO `sys_msg` VALUES (144, 'lia', '1', '0', 1, 2, '2022-10-04 12:52:57');
INSERT INTO `sys_msg` VALUES (148, '1', '1', '0', 1, 2, '2022-11-29 18:41:57');
INSERT INTO `sys_msg` VALUES (149, '123', '1', '0', 1, 2, '2022-11-29 20:02:55');
INSERT INTO `sys_msg` VALUES (150, '123123', '0', '0', 1, 2, '2022-12-01 20:51:06');

-- ----------------------------
-- Table structure for sys_param
-- ----------------------------
DROP TABLE IF EXISTS `sys_param`;
CREATE TABLE `sys_param`  (
  `param_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '参数名',
  `value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '参数值',
  `mean` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '说明',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`param_id`) USING BTREE,
  UNIQUE INDEX `sys_param-name`(`name`) USING BTREE,
  INDEX `sys_param-create_by`(`create_by`) USING BTREE,
  CONSTRAINT `sys_param-create_by` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_param
-- ----------------------------
INSERT INTO `sys_param` VALUES (1, 'enable_register', 'true', '是否开启注册', 1, '2022-11-30 16:38:12', '是否开启注册');
INSERT INTO `sys_param` VALUES (3, 'login_check_code', 'false', '登录时是否需要验证码', 1, '2022-12-10 22:50:59', '登录时是否需要验证码');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色权限字符串',
  `company_id` int(0) NOT NULL COMMENT '所属企业',
  `superior` int(0) NULL DEFAULT NULL COMMENT '上级',
  `root_router_id` int(0) NULL DEFAULT 0 COMMENT '指定用户的根目录',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`) USING BTREE,
  UNIQUE INDEX `sys_role-key`(`key`) USING BTREE,
  INDEX `sys_role-create_by`(`create_by`) USING BTREE,
  INDEX `sys_role-superior`(`superior`) USING BTREE,
  INDEX `sys_role-company_id`(`company_id`) USING BTREE,
  INDEX `sys_role-root_router_id`(`root_router_id`) USING BTREE,
  CONSTRAINT `sys_role-company_id` FOREIGN KEY (`company_id`) REFERENCES `sys_company` (`company_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sys_role-create_by` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `sys_role-root_router_id` FOREIGN KEY (`root_router_id`) REFERENCES `sys_router` (`router_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `sys_role-superior` FOREIGN KEY (`superior`) REFERENCES `sys_role` (`role_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 133 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '开发者', 'sys:admin', 1, 1, 1, 1, '2022-05-06 15:52:00', '开发者');
INSERT INTO `sys_role` VALUES (2, '测试', 'sys:test', 1, 1, 2, 1, '2022-05-11 14:46:00', NULL);
INSERT INTO `sys_role` VALUES (132, '普通员工', 'common', 1, 1, 2, 1, '2023-01-03 13:18:12', NULL);

-- ----------------------------
-- Table structure for sys_role_auth
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_auth`;
CREATE TABLE `sys_role_auth`  (
  `role_id` int(0) NOT NULL COMMENT '角色ID（外键）',
  `auth_id` int(0) NOT NULL COMMENT '权限ID（外键）',
  PRIMARY KEY (`role_id`, `auth_id`) USING BTREE,
  INDEX `sys_role_auth-role_id`(`role_id`) USING BTREE,
  INDEX `sys_role_auth-auth_id`(`auth_id`) USING BTREE,
  CONSTRAINT `sys_role_auth  -role_id` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sys_role_auth-auth_id` FOREIGN KEY (`auth_id`) REFERENCES `sys_auth` (`auth_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_auth
-- ----------------------------
INSERT INTO `sys_role_auth` VALUES (1, 1);
INSERT INTO `sys_role_auth` VALUES (1, 3);
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
INSERT INTO `sys_role_auth` VALUES (2, 1);
INSERT INTO `sys_role_auth` VALUES (2, 3);
INSERT INTO `sys_role_auth` VALUES (2, 5);
INSERT INTO `sys_role_auth` VALUES (2, 6);
INSERT INTO `sys_role_auth` VALUES (2, 7);
INSERT INTO `sys_role_auth` VALUES (2, 8);
INSERT INTO `sys_role_auth` VALUES (2, 9);
INSERT INTO `sys_role_auth` VALUES (2, 10);
INSERT INTO `sys_role_auth` VALUES (2, 11);
INSERT INTO `sys_role_auth` VALUES (2, 12);
INSERT INTO `sys_role_auth` VALUES (2, 13);
INSERT INTO `sys_role_auth` VALUES (2, 14);
INSERT INTO `sys_role_auth` VALUES (2, 15);
INSERT INTO `sys_role_auth` VALUES (2, 16);
INSERT INTO `sys_role_auth` VALUES (2, 17);
INSERT INTO `sys_role_auth` VALUES (2, 18);
INSERT INTO `sys_role_auth` VALUES (2, 62);
INSERT INTO `sys_role_auth` VALUES (2, 64);
INSERT INTO `sys_role_auth` VALUES (2, 65);
INSERT INTO `sys_role_auth` VALUES (2, 66);
INSERT INTO `sys_role_auth` VALUES (2, 68);
INSERT INTO `sys_role_auth` VALUES (2, 69);
INSERT INTO `sys_role_auth` VALUES (2, 70);
INSERT INTO `sys_role_auth` VALUES (2, 72);
INSERT INTO `sys_role_auth` VALUES (2, 76);

-- ----------------------------
-- Table structure for sys_role_router
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_router`;
CREATE TABLE `sys_role_router`  (
  `role_id` int(0) NOT NULL COMMENT '角色ID（外键）',
  `router_id` int(0) NOT NULL COMMENT '权限ID（外键）',
  PRIMARY KEY (`role_id`, `router_id`) USING BTREE,
  INDEX `sys_role_router-router_id`(`router_id`) USING BTREE,
  INDEX `sys_role_router-role_id`(`role_id`) USING BTREE,
  CONSTRAINT `sys_role_router-role_id` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sys_role_router-router_id` FOREIGN KEY (`router_id`) REFERENCES `sys_router` (`router_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_router
-- ----------------------------
INSERT INTO `sys_role_router` VALUES (1, 2);
INSERT INTO `sys_role_router` VALUES (2, 2);
INSERT INTO `sys_role_router` VALUES (1, 3);
INSERT INTO `sys_role_router` VALUES (2, 3);
INSERT INTO `sys_role_router` VALUES (1, 4);
INSERT INTO `sys_role_router` VALUES (2, 4);
INSERT INTO `sys_role_router` VALUES (1, 5);
INSERT INTO `sys_role_router` VALUES (2, 5);
INSERT INTO `sys_role_router` VALUES (1, 6);
INSERT INTO `sys_role_router` VALUES (2, 6);
INSERT INTO `sys_role_router` VALUES (1, 38);
INSERT INTO `sys_role_router` VALUES (2, 38);
INSERT INTO `sys_role_router` VALUES (1, 40);
INSERT INTO `sys_role_router` VALUES (2, 40);
INSERT INTO `sys_role_router` VALUES (1, 41);
INSERT INTO `sys_role_router` VALUES (1, 44);

-- ----------------------------
-- Table structure for sys_router
-- ----------------------------
DROP TABLE IF EXISTS `sys_router`;
CREATE TABLE `sys_router`  (
  `router_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '路由ID',
  `label` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '路由名称',
  `path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '路由地址',
  `element` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组件地址',
  `parent` int(0) NULL DEFAULT 0 COMMENT '父路由',
  `index` int(0) NULL DEFAULT NULL COMMENT '索引，决定路由展示的顺序',
  `icon` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '按钮图标',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`router_id`) USING BTREE,
  UNIQUE INDEX `sys_router-element`(`element`) USING BTREE,
  UNIQUE INDEX `sys_router-parent,path`(`path`, `parent`) USING BTREE,
  INDEX `sys_router-create_by`(`create_by`) USING BTREE,
  INDEX `sys_router-parent`(`parent`) USING BTREE,
  CONSTRAINT `sys_router-create_by` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `sys_router-parent` FOREIGN KEY (`parent`) REFERENCES `sys_router` (`router_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_router
-- ----------------------------
INSERT INTO `sys_router` VALUES (1, '根目录', '', NULL, NULL, 1, NULL, 1, '2022-06-05 02:55:58', NULL);
INSERT INTO `sys_router` VALUES (2, '系统管理', 'system', NULL, 1, 1, 'SettingOutlined', 1, '2022-05-09 10:51:00', NULL);
INSERT INTO `sys_router` VALUES (3, '用户管理', 'user', '/system/user/User', 2, 1, 'TeamOutlined', 1, '2022-05-09 10:55:00', NULL);
INSERT INTO `sys_router` VALUES (4, '权限管理', 'auth', '/system/auth/Auth', 2, 4, 'KeyOutlined', 1, '2022-05-09 11:01:00', NULL);
INSERT INTO `sys_router` VALUES (5, '路由管理', 'router', '/system/router/Router', 2, 5, 'GoldOutlined', 1, '2022-05-30 21:14:00', NULL);
INSERT INTO `sys_router` VALUES (6, '字典配置', 'dict', '/system/dict/Dict', 2, 6, 'PicRightOutlined', 1, '2022-06-11 11:25:44', NULL);
INSERT INTO `sys_router` VALUES (38, '系统工具', 'utils', NULL, 2, 8, 'CodeSandboxOutlined', 1, '2022-06-19 16:57:58', NULL);
INSERT INTO `sys_router` VALUES (40, '代码生成', 'codeGenerator', '/system/tool/codeGenerator/CodeGenerator', 38, 1, 'CopyrightOutlined', 1, '2022-06-19 17:00:51', NULL);
INSERT INTO `sys_router` VALUES (41, '企业管理', 'company', '/system/company/Company', 2, 2, 'VerifiedOutlined', 1, '2022-09-20 15:27:00', NULL);
INSERT INTO `sys_router` VALUES (44, '系统参数', 'param', 'system/param/Param', 2, 7, 'ProfileOutlined', 1, '2022-11-30 16:54:04', NULL);

-- ----------------------------
-- Table structure for sys_tool_code
-- ----------------------------
DROP TABLE IF EXISTS `sys_tool_code`;
CREATE TABLE `sys_tool_code`  (
  `code_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `columns` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '表格字段信息',
  `table_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '表格名',
  `primary_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键信息',
  `http_url` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '接口基本地址',
  `create_by_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '添加创建人字段(0:是，1:否)',
  `create_time_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '添加创建时间字段(0:是，1:否)',
  `update_time_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '添加更新时间字段(0:是，1:否)',
  `remark_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '添加备注字段(0:是，1:否)',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`code_id`) USING BTREE,
  INDEX `sys_tool_code-create_by`(`create_by`) USING BTREE,
  CONSTRAINT `sys_tool_code-create_by` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 87 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_tool_code
-- ----------------------------
INSERT INTO `sys_tool_code` VALUES (80, '[{\"name\":\"asd\",\"type\":\"String\",\"len\":0,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"\"}]', 'asd', '{\"name\":\"asdId\",\"type\":\"autoIncrement\"}', 'asd', '1', '1', '1', '1', 1, '2022-12-01 20:47:38');
INSERT INTO `sys_tool_code` VALUES (81, '[{\"name\":\"asd\",\"type\":\"String\",\"len\":0,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"\"}]', 'asd', '{\"name\":\"asdId\",\"type\":\"autoIncrement\"}', 'asd', '1', '1', '1', '1', 1, '2022-12-01 20:47:52');
INSERT INTO `sys_tool_code` VALUES (82, '[{\"name\":\"asd\",\"type\":\"String\",\"len\":0,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"\"}]', 'asd', '{\"name\":\"asdId\",\"type\":\"autoIncrement\"}', 'asd', '1', '1', '1', '1', 1, '2022-12-01 23:03:50');
INSERT INTO `sys_tool_code` VALUES (83, '[{\"name\":\"asd\",\"type\":\"String\",\"len\":0,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"\"}]', 'asd', '{\"name\":\"asdId\",\"type\":\"autoIncrement\"}', 'asd', '1', '1', '1', '1', 1, '2022-12-01 23:04:06');
INSERT INTO `sys_tool_code` VALUES (84, '[{\"name\":\"asd\",\"type\":\"String\",\"len\":0,\"notNull\":true,\"unique\":true,\"like\":true,\"remark\":\"\"}]', 'asd', '{\"name\":\"asdId\",\"type\":\"autoIncrement\"}', 'asd', '1', '1', '1', '1', 1, '2022-12-02 13:18:29');
INSERT INTO `sys_tool_code` VALUES (85, '[{\"name\":\"asd\",\"type\":\"String\",\"len\":0,\"notNull\":true,\"unique\":true,\"like\":true,\"remark\":\"\"}]', 'asd', '{\"name\":\"asdId\",\"type\":\"autoIncrement\"}', 'asd', '1', '1', '1', '1', 1, '2022-12-02 13:18:48');
INSERT INTO `sys_tool_code` VALUES (86, '[{\"name\":\"asd\",\"type\":\"String\",\"len\":0,\"notNull\":false,\"unique\":false,\"like\":false,\"remark\":\"\"}]', 'asd', '{\"name\":\"asdId\",\"type\":\"autoIncrement\"}', 'asd', '1', '1', '1', '1', 1, '2023-01-03 15:01:44');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `nick` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '昵称',
  `role_id` int(0) NULL DEFAULT NULL COMMENT '用户角色ID',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '性别（0男 1女 2其他）',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号码',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `head_img` bigint(0) NULL DEFAULT NULL COMMENT '头像地址',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`) USING BTREE,
  INDEX `sys_user-create_by`(`create_by`) USING BTREE,
  INDEX `sys_user-role_id`(`role_id`) USING BTREE,
  INDEX `sys_user-head_img`(`head_img`) USING BTREE,
  CONSTRAINT `sys_user-create_by` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `sys_user-head_img` FOREIGN KEY (`head_img`) REFERENCES `sys_file` (`file_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `sys_user-role_id` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`role_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 89 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '$2a$10$kf7PjaPF69ynXBXI3DMaWeKJB2a74Nw.coLbCAb4.JsUrUR5.2yd.', '开发者', 1, '0', '18150027197', '1072864729@qq.com', 56, '0', '0', 1, '2022-05-05 13:10:05', '最高权限，不可删除');
INSERT INTO `sys_user` VALUES (2, 'test', '$2a$10$kf7PjaPF69ynXBXI3DMaWeKJB2a74Nw.coLbCAb4.JsUrUR5.2yd.', '测试', 2, '0', '18150027197', '1072864729@qq.com', 45, '0', '0', 1, '2022-05-17 02:12:00', '测试账号，不可删除');
INSERT INTO `sys_user` VALUES (3, 'youke', '$2a$10$kf7PjaPF69ynXBXI3DMaWeKJB2a74Nw.coLbCAb4.JsUrUR5.2yd.', '游客', 2, '2', NULL, NULL, NULL, '0', '1', 1, '2022-06-14 13:56:00', NULL);
INSERT INTO `sys_user` VALUES (82, 'liweiqiang', '$2a$10$rddXSWtc.eox1Ey0yOqBp.bYEQD3ymcJeZSG5ad4j5O6TJjsm2Jui', '林肯', 2, '0', NULL, NULL, NULL, '0', '1', 1, '2022-09-29 19:04:15', NULL);
INSERT INTO `sys_user` VALUES (83, '123', '$2a$10$ohzXzV9dbBmUBIjCSuCF7uBwcpWpKuvHDyREMTOKTcUwik/n5LTi6', '李伟强', 2, '0', NULL, NULL, NULL, '0', '1', 1, '2022-09-29 19:35:45', NULL);
INSERT INTO `sys_user` VALUES (84, 'test2', '$2a$10$bykQah89xSzykWxHzGh2/OuOVOrnLLhX8ukLdxn.GRAaUZdzt5DTW', '测试2', 2, NULL, NULL, NULL, NULL, '0', '1', 1, '2022-10-03 21:02:42', NULL);
INSERT INTO `sys_user` VALUES (85, 'test3', '$2a$10$hSrMJFZqYglcfirClG.TUOhsIoUUkBwqFsHUIdEd1QEEIlL8TdgsS', '测试3', 2, NULL, NULL, NULL, 41, '0', '1', 1, '2022-10-03 21:02:55', NULL);
INSERT INTO `sys_user` VALUES (86, '1', '$2a$10$daTEHalH5wuf/2HRlJTx0ecIFlsnOQcKgbNAKbsxvXJ7uxIWBzO8G', '32131111', 2, NULL, NULL, NULL, NULL, '0', '1', 1, '2022-10-05 13:02:13', NULL);
INSERT INTO `sys_user` VALUES (87, '312', '$2a$10$kQ3SE1GJ3K6IsD0YfUWTpuWIdPUc2NXqxtf0UPWxi9ABjno3MV.6y', '123', 2, NULL, NULL, NULL, NULL, '0', '1', 1, '2022-10-05 13:03:38', NULL);
INSERT INTO `sys_user` VALUES (88, '123', '$2a$10$05R7IbIE9rzKY6lwBb70Wu7K0odkkqDTg9IG4aH/D6zGV9GzdbASC', '123', 1, NULL, NULL, NULL, NULL, '0', '1', 1, '2022-11-29 20:28:09', NULL);

SET FOREIGN_KEY_CHECKS = 1;