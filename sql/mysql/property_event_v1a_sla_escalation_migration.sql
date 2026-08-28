-- 智慧物业 V1A 增量升级：四段 SLA 自动升级
-- 已执行 property_event_v1a_sla_pause_migration.sql 最新版本的数据库无需执行。

ALTER TABLE `property_event`
  ADD COLUMN `sla_arrival_minutes` int NOT NULL DEFAULT 120 COMMENT '到场时限快照（分钟）' AFTER `sla_rule_version`,
  ADD COLUMN `first_dispatched_time` datetime DEFAULT NULL COMMENT '首次派单时间' AFTER `responded_time`,
  ADD COLUMN `sla_escalation_minutes` int NOT NULL DEFAULT 15 COMMENT '提前升级阈值快照（分钟）' AFTER `sla_paused_seconds`,
  ADD COLUMN `sla_escalation_stage` varchar(32) DEFAULT NULL COMMENT '最近升级时钟阶段' AFTER `sla_escalation_minutes`,
  ADD COLUMN `sla_escalation_level` tinyint NOT NULL DEFAULT 0 COMMENT '升级等级 0无 1即将超时 2已超时' AFTER `sla_escalation_stage`,
  ADD COLUMN `sla_escalated_time` datetime DEFAULT NULL COMMENT '最近升级时间' AFTER `sla_escalation_level`;

INSERT INTO `infra_job`
  (`name`, `status`, `handler_name`, `handler_param`, `cron_expression`, `retry_count`, `retry_interval`, `monitor_timeout`, `creator`, `updater`)
SELECT '物业事件 SLA 自动升级', 1, 'propertyEventSlaEscalationJob', NULL, '0 0/1 * * * ?', 0, 0, 60000, '1', '1'
WHERE NOT EXISTS (
  SELECT 1 FROM `infra_job` WHERE `handler_name` = 'propertyEventSlaEscalationJob' AND `deleted` = b'0'
);
