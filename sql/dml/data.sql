-- 插入用户
INSERT INTO `duchat`.`t_user` (`id`, `name`, `nickname`, `password`, `avatar`, `sex`, `open_id`, `active_status`, `last_opt_time`, `ip_info`, `item_id`, `status`, `create_time`, `update_time`) VALUES (1, 'admin', 'admin', '$2a$10$QvtuuzobNiOF11C/jdW2GuFbZDH/blZaz2m.moc7uwcZe/OiXrFou', 'https://avatars.githubusercontent.com/u/30753404?s=400&v=4', 1, '234', 2, '2024-07-22 21:29:01.789', NULL, NULL, 0, '2024-07-22 21:28:57.000', '2024-07-25 13:53:57.073');

-- 插入道具
INSERT INTO `t_item_config` VALUES (1, 1, NULL, '改名卡', '2024-07-24 22:13:00.000', '2024-07-24 22:13:00.000');
INSERT INTO `t_item_config` VALUES (2, 2, 'https://avatars.githubusercontent.com/u/30753405?s=400&v=4', '点赞达人，点赞超过 10 次', '2024-07-24 22:13:00.000', '2024-07-24 22:13:00.000');
INSERT INTO `t_item_config` VALUES (3, 2, 'https://avatars.githubusercontent.com/u/30753404?s=400&v=4', '前 100 注册用户专属徽章', '2024-07-24 22:13:00.000', '2024-07-24 22:13:00.000');
