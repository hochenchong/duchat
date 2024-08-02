DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`
(
    `id`            bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户id',
    `name`          varchar(20) COLLATE utf8mb4_unicode_ci  DEFAULT NULL COMMENT '用户名',
    `nickname`      varchar(20) COLLATE utf8mb4_unicode_ci  DEFAULT NULL COMMENT '用户昵称',
    `password`      varchar(255)  NOT NULL COMMENT '用户密码',
    `avatar`        varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户头像',
    `sex`           int(11) DEFAULT NULL COMMENT '性别 1为男性，2为女性',
    `open_id`       char(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '微信openid用户标识',
    `active_status` int(11) DEFAULT '2' COMMENT '在线状态 1在线 2离线',
    `last_opt_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3) COMMENT '最后上下线时间',
    `ip_info`       json                                    DEFAULT NULL COMMENT 'ip信息',
    `item_id`       int(11) DEFAULT NULL COMMENT '佩戴的徽章id',
    `status`        int(11) DEFAULT '0' COMMENT '使用状态 0.正常 1拉黑',
    `create_time`   datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3) COMMENT '创建时间',
    `update_time`   datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3) ON UPDATE CURRENT_TIMESTAMP (3) COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uniq_open_id` (`open_id`) USING BTREE,
    UNIQUE KEY `uniq_name` (`name`) USING BTREE,
    KEY             `idx_create_time` (`create_time`) USING BTREE,
    KEY             `idx_update_time` (`update_time`) USING BTREE,
    KEY             `idx_active_status_last_opt_time` (`active_status`,`last_opt_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='用户表';

DROP TABLE IF EXISTS `t_item_config`;
CREATE TABLE `t_item_config` (
  `id` int unsigned NOT NULL COMMENT 'id',
  `type` int NOT NULL COMMENT '道具类型 1改名卡 2徽章',
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '道具图片',
  `item_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '道具功能描述',
  `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE,
  KEY `idx_update_time` (`update_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='道具配置表';

DROP TABLE IF EXISTS `t_user_backpack`;
CREATE TABLE `t_user_backpack` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` bigint NOT NULL COMMENT 'uid',
  `item_id` int NOT NULL COMMENT '道具 id',
  `status` int NOT NULL COMMENT '使用状态 0.待使用 1已使用',
  `idempotent` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '幂等号',
  `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uniq_idempotent` (`idempotent`) USING BTREE,
  KEY `idx_uid` (`uid`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE,
  KEY `idx_update_time` (`update_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='用户背包表';


DROP TABLE IF EXISTS `t_black`;
CREATE TABLE `t_black` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `type` int NOT NULL COMMENT '拉黑目标类型 1.ip 2uid',
  `target` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '拉黑目标',
  `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_type_target` (`type`,`target`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='黑名单';

DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `role_id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色名称',
  `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间',
  PRIMARY KEY (`role_id`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE,
  KEY `idx_update_time` (`update_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色表';

DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` bigint NOT NULL COMMENT 'uid',
  `role_id` int NOT NULL COMMENT '角色id',
  `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_uid` (`uid`) USING BTREE,
  KEY `idx_role_id` (`role_id`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE,
  KEY `idx_update_time` (`update_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户角色关系表';

-- 联系人
DROP TABLE IF EXISTS `t_user_apply`;
CREATE TABLE `t_user_apply` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` bigint(20) NOT NULL COMMENT '申请人uid',
  `type` int(11) NOT NULL COMMENT '申请类型 1加好友',
  `target_id` bigint(20) NOT NULL COMMENT '接收人uid',
  `msg` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '申请信息',
  `status` int(11) NOT NULL COMMENT '申请状态 1待审批 2同意',
  `read_status` int(11) NOT NULL COMMENT '阅读状态 1未读 2已读',
  `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_uid_target_id` (`uid`,`target_id`) USING BTREE,
  KEY `idx_target_id_read_status` (`target_id`,`read_status`) USING BTREE,
  KEY `idx_target_id` (`target_id`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE,
  KEY `idx_update_time` (`update_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户申请表';

DROP TABLE IF EXISTS `t_user_friend`;
CREATE TABLE `t_user_friend` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` bigint(20) NOT NULL COMMENT 'uid',
  `friend_uid` bigint(20) NOT NULL COMMENT '好友uid',
  `delete_status` int(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除(0-正常,1-删除)',
  `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_uid_friend_uid` (`uid`,`friend_uid`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE,
  KEY `idx_update_time` (`update_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户联系人表';

-- 聊天相关的表
DROP TABLE IF EXISTS `t_room`;
CREATE TABLE `t_room` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `type` int(11) NOT NULL COMMENT '房间类型 1群聊 2单聊',
  `last_msg_id` bigint(20) DEFAULT NULL COMMENT '房间中的最后一条消息id',
  `ext_json` json DEFAULT NULL COMMENT '额外信息（根据不同类型房间有不同存储的东西）',
  `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE,
  KEY `idx_update_time` (`update_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='房间表';

DROP TABLE IF EXISTS `t_room_friend`;
CREATE TABLE `t_room_friend` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `room_id` bigint(20) NOT NULL COMMENT '房间id',
  `uid1` bigint(20) NOT NULL COMMENT 'uid1（更小的uid）',
  `uid2` bigint(20) NOT NULL COMMENT 'uid2（更大的uid）',
  `room_key` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '房间key由两个uid拼接，先做排序uid1_uid2',
  `delete_status` int(11) NOT NULL COMMENT '房间状态 0正常 1禁用(删好友了禁用)',
  `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `room_key` (`room_key`) USING BTREE,
  KEY `idx_room_id` (`room_id`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE,
  KEY `idx_update_time` (`update_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='单聊房间表';

DROP TABLE IF EXISTS `t_room_group`;
CREATE TABLE `t_room_group` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `room_id` bigint(20) NOT NULL COMMENT '房间id',
  `name` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '群名称',
  `avatar` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '群头像',
  `ext_json` json DEFAULT NULL COMMENT '额外信息（根据不同类型房间有不同存储的东西）',
  `delete_status` int(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除(0-正常,1-删除)',
  `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_room_i d` (`room_id`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE,
  KEY `idx_update_time` (`update_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='群聊房间表';

DROP TABLE IF EXISTS `t_group_member`;
CREATE TABLE `t_group_member` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `group_id` bigint(20) NOT NULL COMMENT '群主id',
  `uid` bigint(20) NOT NULL COMMENT '成员uid',
  `role` int(11) NOT NULL COMMENT '成员角色 1群主 2管理员 3普通成员',
  `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_group_id_role` (`group_id`,`role`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE,
  KEY `idx_update_time` (`update_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='群成员表';

DROP TABLE IF EXISTS `t_contact`;
CREATE TABLE `t_contact` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` bigint(20) NOT NULL COMMENT 'uid',
  `room_id` bigint(20) NOT NULL COMMENT '房间id',
  `read_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '阅读到的时间',
  `active_time` datetime(3) DEFAULT NULL COMMENT '会话内消息最后更新的时间',
  `last_msg_id` bigint(20) DEFAULT NULL COMMENT '会话最新消息id',
  `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uniq_uid_room_id` (`uid`,`room_id`) USING BTREE,
  KEY `idx_room_id_read_time` (`room_id`,`read_time`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE,
  KEY `idx_update_time` (`update_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='会话列表';

DROP TABLE IF EXISTS `t_message`;
CREATE TABLE `t_message`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `room_id` bigint(20) NOT NULL COMMENT '房间id',
  `from_uid` bigint(20) NOT NULL COMMENT '消息发送者uid',
  `content` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '消息内容',
  `reply_msg_id` bigint(20) NULL DEFAULT NULL COMMENT '回复的消息内容',
  `status` int(11) NOT NULL COMMENT '消息状态 0正常 1删除',
  `gap_count` int(11) NULL DEFAULT NULL COMMENT '与回复的消息间隔多少条',
  `type` int(11) NULL DEFAULT 1 COMMENT '消息类型',
  `extra` json DEFAULT NULL COMMENT '扩展信息',
  `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_room_id`(`room_id`) USING BTREE,
  INDEX `idx_from_uid`(`from_uid`) USING BTREE,
  INDEX `idx_create_time`(`create_time`) USING BTREE,
  INDEX `idx_update_time`(`update_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '消息表' ROW_FORMAT = Dynamic;
