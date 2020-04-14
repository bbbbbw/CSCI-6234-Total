/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80013
 Source Host           : localhost:3306
 Source Schema         : csci_6234

 Target Server Type    : MySQL
 Target Server Version : 80013
 File Encoding         : 65001

 Date: 14/04/2020 17:46:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for url_filter
-- ----------------------------
DROP TABLE IF EXISTS `url_filter`;
CREATE TABLE `url_filter`  (
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `type` int(11) NOT NULL COMMENT '0: data api, 1: web page',
  `auth` int(11) NOT NULL COMMENT '0: any visitor, 1: need login',
  PRIMARY KEY (`url`, `auth`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of url_filter
-- ----------------------------
INSERT INTO `url_filter` VALUES ('add-movie', 0, 1);
INSERT INTO `url_filter` VALUES ('add-review', 0, 1);
INSERT INTO `url_filter` VALUES ('add-vote-movie', 0, 1);
INSERT INTO `url_filter` VALUES ('add-vote-movie-list', 0, 1);
INSERT INTO `url_filter` VALUES ('cast-vote', 0, 1);
INSERT INTO `url_filter` VALUES ('check-code', 0, 0);
INSERT INTO `url_filter` VALUES ('create-group', 0, 1);
INSERT INTO `url_filter` VALUES ('create-voting-event', 0, 1);
INSERT INTO `url_filter` VALUES ('create-watching-event', 0, 1);
INSERT INTO `url_filter` VALUES ('detail', 1, 1);
INSERT INTO `url_filter` VALUES ('get-group', 0, 1);
INSERT INTO `url_filter` VALUES ('get-group-member', 0, 1);
INSERT INTO `url_filter` VALUES ('get-group-movie', 0, 1);
INSERT INTO `url_filter` VALUES ('get-local-review', 1, 1);
INSERT INTO `url_filter` VALUES ('get-moderate-group', 0, 1);
INSERT INTO `url_filter` VALUES ('get-vote-movie-list', 0, 1);
INSERT INTO `url_filter` VALUES ('get-voting-event', 0, 1);
INSERT INTO `url_filter` VALUES ('get-watching-event', 0, 1);
INSERT INTO `url_filter` VALUES ('index', 1, 1);
INSERT INTO `url_filter` VALUES ('join-group', 0, 1);
INSERT INTO `url_filter` VALUES ('login', 1, 0);
INSERT INTO `url_filter` VALUES ('logout', 1, 1);
INSERT INTO `url_filter` VALUES ('manage-group', 1, 1);
INSERT INTO `url_filter` VALUES ('no-auth', 1, 0);
INSERT INTO `url_filter` VALUES ('not-found', 1, 0);
INSERT INTO `url_filter` VALUES ('preview-movie', 1, 1);
INSERT INTO `url_filter` VALUES ('register', 1, 0);
INSERT INTO `url_filter` VALUES ('remove-movie', 0, 1);
INSERT INTO `url_filter` VALUES ('search-movie', 1, 1);
INSERT INTO `url_filter` VALUES ('unsubscribe', 0, 1);
INSERT INTO `url_filter` VALUES ('update-voting-event', 0, 1);
INSERT INTO `url_filter` VALUES ('user-login', 0, 0);
INSERT INTO `url_filter` VALUES ('user-register', 0, 0);
INSERT INTO `url_filter` VALUES ('voting-event', 1, 1);
INSERT INTO `url_filter` VALUES ('watching-event', 1, 1);

SET FOREIGN_KEY_CHECKS = 1;
