-- 智慧物业 V1A 增量升级：关闭 SLA 白名单暂停与恢复审计
-- 适用：已经执行过 property_event_v1a.sql 的数据库。

ALTER TABLE `property_event`
  ADD COLUMN `sla_arrival_minutes` int NOT NULL DEFAULT 120 COMMENT '到场时限快照（分钟）' AFTER `sla_rule_version`,
  ADD COLUMN `first_dispatched_time` datetime DEFAULT NULL COMMENT '首次派单时间' AFTER `responded_time`,
  ADD COLUMN `sla_paused` bit(1) NOT NULL DEFAULT b'0' COMMENT '关闭 SLA 是否暂停' AFTER `block_reason`,
  ADD COLUMN `sla_pause_reason_code` varchar(64) DEFAULT NULL COMMENT '当前 SLA 暂停原因编码' AFTER `sla_paused`,
  ADD COLUMN `sla_pause_started_time` datetime DEFAULT NULL COMMENT '当前 SLA 暂停开始时间' AFTER `sla_pause_reason_code`,
  ADD COLUMN `sla_paused_seconds` bigint NOT NULL DEFAULT 0 COMMENT '累计 SLA 暂停秒数' AFTER `sla_pause_started_time`,
  ADD COLUMN `sla_escalation_minutes` int NOT NULL DEFAULT 15 COMMENT '提前升级阈值快照（分钟）' AFTER `sla_paused_seconds`,
  ADD COLUMN `sla_escalation_stage` varchar(32) DEFAULT NULL COMMENT '最近升级时钟阶段' AFTER `sla_escalation_minutes`,
  ADD COLUMN `sla_escalation_level` tinyint NOT NULL DEFAULT 0 COMMENT '升级等级 0无 1即将超时 2已超时' AFTER `sla_escalation_stage`,
  ADD COLUMN `sla_escalated_time` datetime DEFAULT NULL COMMENT '最近升级时间' AFTER `sla_escalation_level`;

CREATE TABLE IF NOT EXISTS `property_event_sla_pause` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '暂停记录ID',
  `event_id` bigint NOT NULL COMMENT '事件ID',
  `project_id` bigint NOT NULL COMMENT '项目ID',
  `reason_code` varchar(64) NOT NULL COMMENT '白名单暂停原因编码',
  `reason` varchar(500) NOT NULL COMMENT '暂停说明',
  `started_time` datetime NOT NULL COMMENT '暂停开始时间',
  `resumed_time` datetime DEFAULT NULL COMMENT '恢复时间',
  `duration_seconds` bigint DEFAULT NULL COMMENT '本次暂停秒数',
  `pause_user_id` bigint DEFAULT NULL COMMENT '暂停操作人',
  `resume_user_id` bigint DEFAULT NULL COMMENT '恢复操作人',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_event_id` (`event_id`),
  KEY `idx_project_started_time` (`project_id`, `started_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='物业事件 SLA 暂停审计表';

INSERT INTO `infra_job`
  (`name`, `status`, `handler_name`, `handler_param`, `cron_expression`, `retry_count`, `retry_interval`, `monitor_timeout`, `creator`, `updater`)
SELECT '物业事件 SLA 自动升级', 1, 'propertyEventSlaEscalationJob', NULL, '0 0/1 * * * ?', 0, 0, 60000, '1', '1'
WHERE NOT EXISTS (
  SELECT 1 FROM `infra_job` WHERE `handler_name` = 'propertyEventSlaEscalationJob' AND `deleted` = b'0'
);
