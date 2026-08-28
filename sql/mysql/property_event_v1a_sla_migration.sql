-- 智慧物业 V1A 增量升级：事件分类与四段 SLA 规则
-- 适用：已经执行过未包含分类/SLA 的旧版 property_event_v1a.sql。
-- 已执行最新版 property_event_v1a.sql 或 property.sql 的数据库无需执行。

CREATE TABLE IF NOT EXISTS `property_event_category` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `project_id` bigint NOT NULL COMMENT '项目ID',
  `code` varchar(64) NOT NULL COMMENT '分类编码',
  `name` varchar(100) NOT NULL COMMENT '分类名称',
  `parent_id` bigint DEFAULT NULL COMMENT '父分类ID',
  `description` varchar(1000) DEFAULT NULL COMMENT '分类说明',
  `default_urgency_level` tinyint NOT NULL DEFAULT 2 COMMENT '默认紧急程度',
  `duplicate_window_minutes` int NOT NULL DEFAULT 0 COMMENT '相似事件提示时间窗（分钟）',
  `required_evidence_types` varchar(500) NOT NULL COMMENT '关闭证据类型列表',
  `customer_confirmation_required` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否要求客户确认',
  `sort` int NOT NULL DEFAULT 0 COMMENT '排序',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_project_code` (`tenant_id`, `project_id`, `code`),
  KEY `idx_project_status_sort` (`project_id`, `status`, `sort`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='物业事件分类表';

CREATE TABLE IF NOT EXISTS `property_event_sla_rule` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '规则ID',
  `project_id` bigint NOT NULL COMMENT '项目ID',
  `category_code` varchar(64) NOT NULL COMMENT '事件分类编码，*表示兜底',
  `urgency_level` tinyint NOT NULL COMMENT '紧急程度',
  `response_minutes` int NOT NULL COMMENT '响应时限（分钟）',
  `arrival_minutes` int NOT NULL COMMENT '到场时限（分钟）',
  `recovery_minutes` int NOT NULL COMMENT '恢复时限（分钟）',
  `close_minutes` int NOT NULL COMMENT '关闭时限（分钟）',
  `escalation_minutes` int NOT NULL COMMENT '提前升级阈值（分钟）',
  `allowed_pause_reasons` varchar(1000) DEFAULT NULL COMMENT '允许暂停关闭时钟的原因',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态',
  `version` int NOT NULL DEFAULT 0 COMMENT '规则版本',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_project_category_level` (`tenant_id`, `project_id`, `category_code`, `urgency_level`),
  KEY `idx_project_status` (`project_id`, `status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='物业事件四段 SLA 规则表';

ALTER TABLE `property_event`
  ADD COLUMN `sla_rule_id` bigint DEFAULT NULL COMMENT 'SLA规则ID快照' AFTER `collaborator_ids`,
  ADD COLUMN `sla_rule_version` int DEFAULT NULL COMMENT 'SLA规则版本快照' AFTER `sla_rule_id`;

ALTER TABLE `property_event_work_order`
  ADD COLUMN `parent_work_order_id` bigint DEFAULT NULL COMMENT '上一个工单ID' AFTER `event_id`,
  ADD COLUMN `transfer_reason` varchar(500) DEFAULT NULL COMMENT '转派原因' AFTER `reject_reason`,
  ADD INDEX `idx_parent_work_order_id` (`parent_work_order_id`);
