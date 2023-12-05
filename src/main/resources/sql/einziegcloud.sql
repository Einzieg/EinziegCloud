/*
 Navicat Premium Data Transfer

 Source Server         : MySql
 Source Server Type    : MySQL
 Source Server Version : 80032 (8.0.32)
 Source Host           : localhost:3306
 Source Schema         : einziegcloud

 Target Server Type    : MySQL
 Target Server Version : 80032 (8.0.32)
 File Encoding         : 65001

 Date: 05/12/2023 17:58:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cloud_log
-- ----------------------------
DROP TABLE IF EXISTS `cloud_log`;
CREATE TABLE `cloud_log`
(
    `ID`           bigint                                                        NOT NULL AUTO_INCREMENT,
    `OUTCOME`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '运行结果',
    `CREATE_TIME`  datetime                                                      NULL DEFAULT NULL COMMENT '创建时间',
    `TAKE_TIME`    bigint                                                        NULL DEFAULT NULL COMMENT '耗时(ms)',
    `HTTP_METHOD`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT 'http请求方法',
    `CLASS_METHOD` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '类方法',
    `REQUEST_URL`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求URL',
    `IP`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '请求IP',
    `USER`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户',
    `MODEL`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '模块',
    `DETAIL`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '说明',
    `RETURN_BODY`  text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci         NULL COMMENT '方法返回体/异常信息',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 6102
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cloud_log
-- ----------------------------
INSERT INTO `cloud_log`
VALUES (8, 'success', '2023-11-15 17:54:41', 815, 'POST', 'com.cloud.controller.AuthenticationController.login', '/cloud/auth/login', '192.168.30.160', 'admin',
        '用户模块', '用户登录',
        'Msg(code=200, msg=请求成功, data=AuthenticationResponse(token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcwMDA0MjA4MSwiZXhwIjoxNzAwMTI4NDgxfQ.eC-Zg9GQV188SpkqkfdeHUrkA1QT7oFujsoZj2vtyVy4MjJJxZGedCCS7x-6muwTkZAv-c97Efz5kraZ1IA5NQ), timestamp=1700042081128)');
INSERT INTO `cloud_log`
VALUES (9, 'success', '2023-11-15 17:55:30', 386, 'POST', 'com.cloud.controller.AuthenticationController.register', '/cloud/auth/register', '192.168.30.160',
        '1936343575@qq.com', '用户模块', '用户注册', 'Msg(code=405, msg=验证码已过期, data=null, timestamp=1700042130312)');
INSERT INTO `cloud_log`
VALUES (10, 'success', '2023-11-15 17:58:14', 1064, 'POST', 'com.cloud.controller.AuthenticationController.register', '/cloud/auth/register', '192.168.30.160',
        '1936343575@qq.com', '用户模块', '用户注册',
        'Msg(code=200, msg=请求成功, data=AuthenticationResponse(token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxOTM2MzQzNTc1QHFxLmNvbSIsImlhdCI6MTcwMDA0MjI5NCwiZXhwIjoxNzAwMTI4Njk0fQ.vVeNhwn4nUK-phPyo881gVIC6IKeV66zJ6-Z0-u9RYastUxTsXyiqAwA55BnYkLQfYJ1nNpyxI405d9QdJMYhw), timestamp=1700042294082)');
INSERT INTO `cloud_log`
VALUES (11, 'success', '2023-11-15 18:00:05', 5519, 'GET', 'com.cloud.controller.AuthenticationController.mail', '/cloud/auth/mail', '192.168.30.160',
        '1936343575@qq.com', '用户模块', '邮件-注册', 'Msg(code=200, msg=请求成功, data=邮件发送成功, timestamp=1700042404704)');
INSERT INTO `cloud_log`
VALUES (12, 'success', '2023-11-16 09:33:17', 455, 'POST', 'com.cloud.controller.AuthenticationController.login', '/cloud/auth/login', '192.168.30.160',
        '1936343575@qq.com', '用户模块', '用户登录',
        'Msg(code=200, msg=请求成功, data=AuthenticationResponse(token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxOTM2MzQzNTc1QHFxLmNvbSIsImlhdCI6MTcwMDA5ODM5NiwiZXhwIjoxNzAwMTg0Nzk2fQ.WF1wlhMTlZBd-rRlnMOdPB5cPxwt417T36suB--m1RqSryujziHinT-0CkZ4sH6WWINf-Q4nfrXIAYXi6QsZuA), timestamp=1700098396603)');
INSERT INTO `cloud_log`
VALUES (16, 'error', '2023-11-16 09:36:46', 378, 'POST', 'com.cloud.controller.AuthenticationController.login', '/cloud/auth/login', '192.168.30.160', '',
        '用户模块', '用户登录', 'org.springframework.security.authentication.BadCredentialsException: Bad credentials');
INSERT INTO `cloud_log`
VALUES (17, 'error', '2023-11-16 09:39:55', 67, 'POST', 'com.cloud.controller.AuthenticationController.login', '/cloud/auth/login', '192.168.30.160', '',
        '用户模块', '用户登录', 'org.springframework.security.authentication.BadCredentialsException: Bad credentials');
INSERT INTO `cloud_log`
VALUES (18, 'error', '2023-11-16 09:40:16', 124, 'POST', 'com.cloud.controller.AuthenticationController.login', '/cloud/auth/login', '192.168.30.160', '',
        '用户模块', '用户登录', 'org.springframework.security.authentication.BadCredentialsException: Bad credentials');
INSERT INTO `cloud_log`
VALUES (19, 'success', '2023-11-16 10:17:04', 0, 'GET', 'com.cloud.controller.UserController.hello', '/cloud/user/hello', '192.168.30.160', '1936343575@qq.com',
        '用户模块', '测试', 'Msg(code=200, msg=请求成功, data=Hello World, timestamp=1700101023609)');
INSERT INTO `cloud_log`
VALUES (6101, 'success', '2023-12-05 15:22:10', 531, 'GET', 'com.cloud.controller.LogController.getLog', '/cloud/log/get', '192.168.30.167', 'Test', '日志模块',
        '获取日志', 'Msg(code=200, msg=请求成功, data=com.baomidou.mybatisplus.extension.plugins.pagination.Page@51de664, timestamp=1701760930373)');

-- ----------------------------
-- Table structure for cloud_role
-- ----------------------------
DROP TABLE IF EXISTS `cloud_role`;
CREATE TABLE `cloud_role`
(
    `ID`         bigint                                                       NOT NULL AUTO_INCREMENT,
    `ROLE_CODE`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `ROLE_NAME`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `ROLE_LEVEL` int                                                          NULL DEFAULT NULL,
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `INDEX idx_cloud_role_id` (`ID` ASC) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cloud_role
-- ----------------------------
INSERT INTO `cloud_role`
VALUES (1, 'SUPER_ADMIN', '超级管理员', 9);
INSERT INTO `cloud_role`
VALUES (2, 'ADMIN', '管理员', 6);
INSERT INTO `cloud_role`
VALUES (3, 'USER', '用户', 3);
INSERT INTO `cloud_role`
VALUES (4, 'VISITOR', '游客', 1);

-- ----------------------------
-- Table structure for cloud_user
-- ----------------------------
DROP TABLE IF EXISTS `cloud_user`;
CREATE TABLE `cloud_user`
(
  `ID` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `EMAIL` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `NAME`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `PASSWORD` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `REGISTER_DATE` datetime NULL DEFAULT NULL,
  `LAST_LOGIN_DATE` datetime NULL DEFAULT NULL,
  `REGISTER_IP` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `LAST_LOGIN_IP` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `STATE`  tinyint                                                      NOT NULL COMMENT '1=TRUE/0=FALSE（0为正常）',
  `LOCKED` tinyint                                                      NOT NULL COMMENT '1=TRUE/0=FALSE（0为正常）',
  `TOKEN` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `idx_cloud_user_email` (`EMAIL` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cloud_user
-- ----------------------------
INSERT INTO `cloud_user`
VALUES ('0', 'admin', 'Einzieg', '$2a$10$PeDFu.W8JJ3t0ifESyB0Pu2rzxxy/e3LrD4o9R09sZCE5ukzegwja', '2023-08-02 17:58:31', '2023-11-24 10:56:01', '192.168.0.1',
        '192.168.30.167', 0, 0,
        'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJFaW56aWVnIiwiaWF0IjoxNzAwNzk0NTYwLCJleHAiOjE3MDA4ODA5NjB9.Idh3FEI_pu4T_NvKmDv73uHjhCadc5G_iIzQHyb1noPX7Q-hV7NkYGd-D0KabfrQ761AdR-8WaX4NEH9iUN6Uw');
INSERT INTO `cloud_user`
VALUES ('1731936527707373570', '1936343575@qq.com', 'Test', '$2a$10$VAoAj8Xi4oUnf6Nwv4uj2OpDuTtBsb6xFbfc.m6rl19lhR6Mfrfj2', '2023-12-05 15:20:21', NULL,
        '192.168.30.167', NULL, 0, 0, NULL);

-- ----------------------------
-- Table structure for cloud_user_role
-- ----------------------------
DROP TABLE IF EXISTS `cloud_user_role`;
CREATE TABLE `cloud_user_role`
(
    `ID`      bigint                                                       NOT NULL AUTO_INCREMENT,
    `USER_ID` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `ROLE_ID` bigint                                                       NOT NULL,
    PRIMARY KEY (`ID`) USING BTREE,
    INDEX `USER_ID` (`USER_ID` ASC) USING BTREE,
    INDEX `ROLE_ID` (`ROLE_ID` ASC) USING BTREE,
    CONSTRAINT `cloud_user_role_ibfk_1` FOREIGN KEY (`USER_ID`) REFERENCES `cloud_user` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `cloud_user_role_ibfk_2` FOREIGN KEY (`ROLE_ID`) REFERENCES `cloud_role` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 19
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cloud_user_role
-- ----------------------------
INSERT INTO `cloud_user_role`
VALUES (1, '0', 1);
INSERT INTO `cloud_user_role`
VALUES (18, '1731936527707373570', 3);

SET FOREIGN_KEY_CHECKS = 1;
