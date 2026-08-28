-- 智慧物业 V1A 增量升级：事件合并与拆分

ALTER TABLE `property_event`
  ADD COLUMN `sla_start_time` datetime DEFAULT NULL COMMENT '事件 SLA 有效起点' AFTER `sla_arrival_minutes`,
  ADD COLUMN `main_event_id` bigint DEFAULT NULL COMMENT '合并后的主事件ID' AFTER `sla_escalated_time`,
  ADD COLUMN `parent_event_id` bigint DEFAULT NULL COMMENT '拆分来源事件ID' AFTER `main_event_id`,
  ADD COLUMN `split_reason` varchar(500) DEFAULT NULL COMMENT '拆分依据' AFTER `parent_event_id`,
  ADD INDEX `idx_main_event_id` (`main_event_id`),
  ADD INDEX `idx_parent_event_id` (`parent_event_id`);

UPDATE `property_event`
SET `sla_start_time` = `create_time`
WHERE `sla_start_time` IS NULL;

ALTER TABLE `property_event`
  MODIFY COLUMN `sla_start_time` datetime NOT NULL COMMENT '事件 SLA 有效起点';

CREATE TABLE IF NOT EXISTS `property_event_relation` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '关系ID',
  `project_id` bigint NOT NULL COMMENT '项目ID',
  `main_event_id` bigint NOT NULL COMMENT '主事件或来源事件ID',
  `related_event_id` bigint NOT NULL COMMENT '合并来源或拆分子事件ID',
  `relation_type` varchar(20) NOT NULL COMMENT '关系类型 MERGED/SPLIT',
  `reason` varchar(500) NOT NULL COMMENT '操作依据',
  `operator_user_id` bigint DEFAULT NULL COMMENT '操作人',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_relation` (`tenant_id`, `main_event_id`, `related_event_id`, `relation_type`),
  KEY `idx_related_event_id` (`related_event_id`),
  KEY `idx_project_id` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='物业事件合并拆分关系表';
