-- 智慧物业 V1A 增量升级：事件外部消息、失败补偿与送达回执

CREATE TABLE IF NOT EXISTS `property_event_notification` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `message_no` varchar(40) NOT NULL COMMENT '消息编号',
  `project_id` bigint NOT NULL COMMENT '项目ID',
  `event_id` bigint NOT NULL COMMENT '事件ID',
  `resident_id` bigint NOT NULL COMMENT '接收住户ID',
  `recipient_name` varchar(100) DEFAULT NULL COMMENT '接收人姓名快照',
  `recipient_mobile` varchar(32) NOT NULL COMMENT '接收手机号快照',
  `notification_type` varchar(40) NOT NULL COMMENT '事件通知节点',
  `channel` varchar(20) NOT NULL COMMENT '发送渠道',
  `template_code` varchar(100) NOT NULL COMMENT '渠道模板编码',
  `content` varchar(1000) NOT NULL COMMENT '客户可见内容',
  `idempotency_key` varchar(200) NOT NULL COMMENT '业务幂等键',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态 0待发送 10已发送 20已送达 30失败',
  `retry_count` tinyint NOT NULL DEFAULT 0 COMMENT '重试次数',
  `next_retry_time` datetime DEFAULT NULL COMMENT '下次重试时间',
  `sent_time` datetime DEFAULT NULL COMMENT '发送时间',
  `delivered_time` datetime DEFAULT NULL COMMENT '送达时间',
  `external_message_id` varchar(100) DEFAULT NULL COMMENT '渠道消息ID',
  `failure_reason` varchar(1000) DEFAULT NULL COMMENT '失败原因',
  `version` int NOT NULL DEFAULT 0 COMMENT '并发版本',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_message_no` (`tenant_id`, `message_no`),
  UNIQUE KEY `uk_tenant_idempotency` (`tenant_id`, `idempotency_key`),
  KEY `idx_project_status_retry` (`project_id`, `status`, `next_retry_time`),
  KEY `idx_event_id` (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='物业事件外部消息与送达回执表';

INSERT INTO `infra_job`
  (`name`, `status`, `handler_name`, `handler_param`, `cron_expression`, `retry_count`, `retry_interval`, `monitor_timeout`, `creator`, `updater`)
SELECT '物业事件外部消息发送', 1, 'propertyEventNotificationJob', NULL, '0 0/1 * * * ?', 0, 0, 60000, '1', '1'
WHERE NOT EXISTS (
  SELECT 1 FROM `infra_job` WHERE `handler_name` = 'propertyEventNotificationJob' AND `deleted` = b'0'
);

-- 发送前需在系统短信管理中配置编码 property-event-status 的通知模板，
-- 模板参数为 eventNo、status、content，并绑定当前环境可用的短信渠道。
