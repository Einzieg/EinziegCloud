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

 Date: 09/11/2023 17:35:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `ID` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `EMAIL` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `PASSWORD` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ROLE` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `REGISTER_DATE` datetime NULL DEFAULT NULL,
  `LAST_LOGIN_DATE` datetime NULL DEFAULT NULL,
  `REGISTER_IP` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `LAST_LOGIN_IP` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `STATE` tinyint NULL DEFAULT NULL COMMENT '1=TRUE/0=FALSE（0为正常）',
  `LOCKED` tinyint NULL DEFAULT NULL COMMENT '1=TRUE/0=FALSE（0为正常）',
  `TOKEN` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('0', 'admin', '$2a$10$PeDFu.W8JJ3t0ifESyB0Pu2rzxxy/e3LrD4o9R09sZCE5ukzegwja', 'SUPER_ADMIN', '2023-08-02 17:58:31', '2023-11-09 10:15:48', NULL, '192.168.30.154', 0, 0, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY5OTQ5NjE0NywiZXhwIjoxNzAwOTY3Mzc2fQ.JNKGDNIiPuMMJpJSwUzbDVXkpLXCbeh_f6gnyrJCWodb7ExVfGgMaKRBPx2c1D1bVjJa9lalZtwEmBg-JGS_7w');

SET FOREIGN_KEY_CHECKS = 1;
