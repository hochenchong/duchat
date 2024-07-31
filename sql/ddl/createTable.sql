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