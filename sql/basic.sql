SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `sys_dictionary`;
CREATE TABLE `sys_dictionary`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父节点id',
  `level` tinyint(4) NULL DEFAULT 0 COMMENT '层级',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '代码',
  `value` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '值',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述信息',
  `sort` int(11) NULL DEFAULT 0 COMMENT '排序',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '状态：0正常、1停用',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '更新人id',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) NULL DEFAULT NULL COMMENT '乐观锁版本号',
  `deletion` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除状态：0正常、1删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '通用字典' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_dictionary
-- ----------------------------

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `original_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件原名称',
  `new_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件新名称（uuid随机）',
  `file_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件类型（识别是否是图片或文档...）',
  `thumb` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '缩略图BASE64',
  `relation_id` bigint(20) NULL DEFAULT NULL COMMENT '关联id',
  `relation_type` int(11) NULL DEFAULT NULL COMMENT '关联的业务类型（使用枚举）',
  `relation_child_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '关联业务子类型（一个类型下存在多种业务的时候使用）',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '更新人id',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) NULL DEFAULT NULL COMMENT '乐观锁版本号',
  `deletion` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除状态：0正常、1删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '文件信息表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_file
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父节点id：0表示一级节点',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `icon` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'html页面路径',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序序号',
  `status` tinyint(4) NULL DEFAULT 0 COMMENT '状态：0正常、1停用',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '更新人id',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) NULL DEFAULT NULL COMMENT '乐观锁版本号',
  `deletion` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除状态：0正常、1删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '功能菜单表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, '系统管理', 'setting', '/system', 1, 0, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (2, 1, '用户管理', NULL, '/system/user', 1, 0, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (3, 1, '角色管理', '', '/system/role', 2, 0, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (4, 1, '资源管理', NULL, '/system/resource', 3, 0, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (5, 1, '菜单管理', '', '/system/menu', 3, 0, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (6, 1, '字典管理', '', '/system/dictionary', 5, 0, NULL, NULL, NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for sys_menu_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_resource`;
CREATE TABLE `sys_menu_resource`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单id',
  `resource_id` bigint(20) NOT NULL COMMENT '资源id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单资源关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_menu_resource
-- ----------------------------

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源名称',
  `description` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '详细描述说明',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源路径',
  `method` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方法(GET查询、POST新增、PUT修改 、DELETE 删除)',
  `status` tinyint(4) NULL DEFAULT 0 COMMENT '状态：0正常、1停用',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '更新人id',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) NULL DEFAULT NULL COMMENT '乐观锁版本号',
  `deletion` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除状态：0正常、1删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'API资源表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
INSERT INTO `sys_resource` VALUES (1, 'SysMenu表控制器', '新增数据', NULL, '/sysMenu', 'post', 0, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_resource` VALUES (2, 'SysMenu表控制器', '修改数据', NULL, '/sysMenu', 'put', 0, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_resource` VALUES (3, 'SysMenu表控制器', '删除数据', NULL, '/sysMenu', 'delete', 0, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_resource` VALUES (4, 'SysMenu表控制器', '获取全部菜单', NULL, '/sysMenu/allMenu', 'get', 0, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_resource` VALUES (5, 'SysMenu表控制器', '获取当前用户菜单', NULL, '/sysMenu/currentUserMenu', 'get', 0, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_resource` VALUES (6, 'SysMenu表控制器', '通过主键查询单条数据', NULL, '/sysMenu/*', 'get', 0, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_resource` VALUES (7, 'SysResource表控制器', '按swagger文档初始化资源', NULL, '/sysResource/initBySwagger', 'get', 0, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_resource` VALUES (8, 'SysRole表控制器', '分页查询所有数据', NULL, '/sysRole', 'get', 0, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_resource` VALUES (9, 'SysRole表控制器', '新增数据', NULL, '/sysRole', 'post', 0, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_resource` VALUES (10, 'SysRole表控制器', '修改数据', NULL, '/sysRole', 'put', 0, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_resource` VALUES (11, 'SysRole表控制器', '删除数据', NULL, '/sysRole', 'delete', 0, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_resource` VALUES (12, 'SysRole表控制器', '通过主键查询单条数据', NULL, '/sysRole/*', 'get', 0, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_resource` VALUES (13, 'SysUser表控制器', '分页查询', NULL, '/sysUser', 'get', 0, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_resource` VALUES (14, 'SysUser表控制器', '新增数据', NULL, '/sysUser', 'post', 0, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_resource` VALUES (15, 'SysUser表控制器', '修改数据', NULL, '/sysUser', 'put', 0, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_resource` VALUES (16, 'SysUser表控制器', '删除数据', NULL, '/sysUser', 'delete', 0, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_resource` VALUES (17, 'SysUser表控制器', '获取当前用户信息', NULL, '/sysUser/currentUser', 'get', 0, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_resource` VALUES (18, 'SysUser表控制器', '用户登录', NULL, '/sysUser/login', 'post', 0, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_resource` VALUES (19, 'SysUser表控制器', '修改个人密码', NULL, '/sysUser/password', 'put', 0, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_resource` VALUES (20, 'SysUser表控制器', '刷新token', NULL, '/sysUser/refreshToken', 'get', 0, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_resource` VALUES (21, 'SysUser表控制器', '通过主键查询单条数据', NULL, '/sysUser/*', 'get', 0, NULL, NULL, NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `description` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `status` tinyint(4) NULL DEFAULT 0 COMMENT '状态：0正常、1停用',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '更新人id',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) NULL DEFAULT NULL COMMENT '乐观锁版本号',
  `deletion` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除状态：0正常、1删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', '拥有最大的操作权限', 0, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_role` VALUES (2, '普通角色', '普通角色', 0, NULL, NULL, NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色菜单关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (31, 1, 1);
INSERT INTO `sys_role_menu` VALUES (32, 1, 2);
INSERT INTO `sys_role_menu` VALUES (33, 1, 3);
INSERT INTO `sys_role_menu` VALUES (34, 1, 5);
INSERT INTO `sys_role_menu` VALUES (36, 1, 7);
INSERT INTO `sys_role_menu` VALUES (37, 2, 1);
INSERT INTO `sys_role_menu` VALUES (39, 2, 2);
INSERT INTO `sys_role_menu` VALUES (40, 2, 5);
INSERT INTO `sys_role_menu` VALUES (41, 1, 6);

-- ----------------------------
-- Table structure for sys_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resource`;
CREATE TABLE `sys_role_resource`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `resource_id` bigint(20) NOT NULL COMMENT '资源id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色资源关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role_resource
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色用户关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO `sys_role_user` VALUES (6, 2, 2);
INSERT INTO `sys_role_user` VALUES (8, 2, 5);
INSERT INTO `sys_role_user` VALUES (9, 2, 3);
INSERT INTO `sys_role_user` VALUES (11, 1, 6);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录名',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `full_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `status` tinyint(4) NULL DEFAULT 0 COMMENT '状态：0正常、1停用',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '更新人id',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) NULL DEFAULT NULL COMMENT '乐观锁版本号',
  `deletion` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除状态：0正常、1删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_name`(`user_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '207cf410532f92a47dee245ce9b11ff71f578ebd763eb3bbea44ebd043d018fb', '系统管理员', '18308986640', 0, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_user` VALUES (2, '001', '207cf410532f92a47dee245ce9b11ff71f578ebd763eb3bbea44ebd043d018fb', '普通用户', '18308986640', 0, NULL, NULL, NULL, NULL, NULL, 0);

SET FOREIGN_KEY_CHECKS = 1;
