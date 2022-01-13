SET NAMES utf8mb4;

-- ----------------------------
-- 总用户表
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
                         `id` bigint(20) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT COMMENT '用户id',
                         `username` varchar(255) NOT NULL COMMENT '用户姓名',
                         `password` varchar(255) NOT NULL COMMENT '用户密码',
                         `status` tinyint DEFAULT 0 NOT NULL COMMENT '用户状态 -0 离线 -1 登录 -2 注销',
                         `create_time` int UNSIGNED NOT NULL COMMENT '创建时间',
                         `login_time` int UNSIGNED NOT NULL COMMENT '最近登录时间',
                         UNIQUE INDEX (username),
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 ROW_FORMAT = Dynamic;

-- ----------------------------
-- 活动表
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity`  (
                         `id` bigint(20) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT COMMENT '抽奖活动id',
                         `activity_name` varchar(255) NOT NULL COMMENT '抽奖活动名称',
                         `start_time` int UNSIGNED NOT NULL COMMENT '起始时间',
                         `end_time` int UNSIGNED NOT NULL COMMENT '截止时间',
                         `create_time`int UNSIGNED NOT NULL COMMENT '创建时间',
                         `alter_time` int UNSIGNED NOT NULL COMMENT '最近修改时间',
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 ROW_FORMAT = Dynamic;

-- ----------------------------
-- 奖项表
-- ----------------------------
DROP TABLE IF EXISTS `lottery`;
CREATE TABLE `lottery`  (
                         `id` bigint(20) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT COMMENT '奖项id',
                         `activity_id` bigint(20) UNSIGNED NOT NULL COMMENT '活动id',
                         `lottery_content` varchar(255) NOT NULL COMMENT '奖项内容',
                         `sum` int NOT NULL COMMENT '总数',
                         `inventory` int NOT NULL COMMENT '库存数量',
                         `prob` int NOT NULL COMMENT '概率',
                         `create_time` int UNSIGNED NOT NULL COMMENT '创建时间',
                         `alter_time` int UNSIGNED NOT NULL COMMENT '最近修改时间',
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 ROW_FORMAT = Dynamic;

-- ----------------------------
-- 抽奖记录表
-- ----------------------------
DROP TABLE IF EXISTS `lottery_record`;
CREATE TABLE `lottery_record`  (
                         `id` bigint(20) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT COMMENT '记录id',
                         `user_id` bigint(20) UNSIGNED NOT NULL COMMENT '用户id',
                         `activity_id` bigint(20) UNSIGNED NOT NULL COMMENT '活动id',
                         `create_time` int UNSIGNED NOT NULL COMMENT '创建时间',
                         `alter_time` int UNSIGNED NOT NULL COMMENT '最近修改时间',
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 ROW_FORMAT = Dynamic;

-- ----------------------------
-- 中奖用户表
-- ----------------------------
DROP TABLE IF EXISTS `lottery_user`;
CREATE TABLE `lottery_user`  (
                         `id` bigint(20) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT COMMENT '中奖id',
                         `user_id` bigint(20) UNSIGNED NOT NULL COMMENT '用户id',
                         `lottery_id` bigint(20) UNSIGNED NOT NULL COMMENT '奖项id',
                         `lottery_content` varchar(255) NOT NULL COMMENT '奖项内容',
                         `create_time` int UNSIGNED NOT NULL COMMENT '创建时间',
                         `alter_time` int UNSIGNED NOT NULL COMMENT '最近修改时间',
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 ROW_FORMAT = Dynamic;
