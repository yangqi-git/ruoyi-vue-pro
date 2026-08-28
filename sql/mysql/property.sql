-- =============================================
-- 智慧物业系统建表SQL脚本
-- 生成时间: 2026-06-09
-- =============================================

-- ========== 组织层级模块 ==========

-- 物业项目表
CREATE TABLE IF NOT EXISTS `property_project` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '项目ID',
  `name` varchar(100) NOT NULL COMMENT '项目名称',
  `code` varchar(50) DEFAULT NULL COMMENT '项目编码',
  `parent_id` bigint DEFAULT NULL COMMENT '父项目ID',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
  `address` varchar(200) DEFAULT NULL COMMENT '地址',
  `contact_name` varchar(50) DEFAULT NULL COMMENT '联系人',
  `contact_phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `start_date` date DEFAULT NULL COMMENT '开始日期',
  `end_date` date DEFAULT NULL COMMENT '结束日期',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_code` (`code`),
  KEY `idx_parent_id` (`parent_id`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='物业项目表';

-- 小区表
CREATE TABLE IF NOT EXISTS `property_community` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '小区ID',
  `name` varchar(100) NOT NULL COMMENT '小区名称',
  `code` varchar(50) DEFAULT NULL COMMENT '小区编码',
  `project_id` bigint NOT NULL COMMENT '项目ID',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
  `address` varchar(200) DEFAULT NULL COMMENT '地址',
  `total_area` double DEFAULT NULL COMMENT '总面积',
  `building_count` int DEFAULT 0 COMMENT '楼栋数',
  `unit_count` int DEFAULT 0 COMMENT '单元数',
  `house_count` int DEFAULT 0 COMMENT '房屋数',
  `contact_name` varchar(50) DEFAULT NULL COMMENT '联系人',
  `contact_phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `open_date` date DEFAULT NULL COMMENT '开业日期',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_code` (`code`),
  KEY `idx_project_id` (`project_id`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='小区表';

-- ========== 空间资产模块 ==========

-- 楼栋表
CREATE TABLE IF NOT EXISTS `property_building` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '楼栋ID',
  `name` varchar(100) NOT NULL COMMENT '楼栋名称',
  `code` varchar(50) DEFAULT NULL COMMENT '楼栋编码',
  `project_id` bigint NOT NULL COMMENT '项目ID',
  `community_id` bigint DEFAULT NULL COMMENT '小区ID',
  `building_type` tinyint DEFAULT NULL COMMENT '楼栋类型',
  `floor_count` int DEFAULT 0 COMMENT '楼层数',
  `unit_count` int DEFAULT 0 COMMENT '单元数',
  `house_count` int DEFAULT 0 COMMENT '房屋数',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
  `address` varchar(200) DEFAULT NULL COMMENT '地址',
  `build_date` date DEFAULT NULL COMMENT '建造日期',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_code` (`code`),
  KEY `idx_project_id` (`project_id`),
  KEY `idx_community_id` (`community_id`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='楼栋表';

-- 兼容已创建的楼栋表：小区调整为可选关联
ALTER TABLE `property_building`
  MODIFY COLUMN `community_id` bigint DEFAULT NULL COMMENT '小区ID';

-- 单元表
CREATE TABLE IF NOT EXISTS `property_unit` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '单元ID',
  `name` varchar(100) NOT NULL COMMENT '单元名称',
  `code` varchar(50) DEFAULT NULL COMMENT '单元编码',
  `project_id` bigint NOT NULL COMMENT '项目ID',
  `building_id` bigint NOT NULL COMMENT '楼栋ID',
  `community_id` bigint DEFAULT NULL COMMENT '小区ID',
  `floor_count` int DEFAULT 0 COMMENT '楼层数',
  `house_count` int DEFAULT 0 COMMENT '房屋数',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_code` (`code`),
  KEY `idx_project_id` (`project_id`),
  KEY `idx_building_id` (`building_id`),
  KEY `idx_community_id` (`community_id`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='单元表';

-- 楼层表
CREATE TABLE IF NOT EXISTS `property_floor` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '楼层ID',
  `name` varchar(100) NOT NULL COMMENT '楼层名称',
  `code` varchar(50) DEFAULT NULL COMMENT '楼层编码',
  `project_id` bigint NOT NULL COMMENT '项目ID',
  `unit_id` bigint NOT NULL COMMENT '单元ID',
  `building_id` bigint DEFAULT NULL COMMENT '楼栋ID',
  `community_id` bigint DEFAULT NULL COMMENT '小区ID',
  `floor_no` int DEFAULT NULL COMMENT '楼层号',
  `house_count` int DEFAULT 0 COMMENT '房屋数',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_code` (`code`),
  KEY `idx_project_id` (`project_id`),
  KEY `idx_unit_id` (`unit_id`),
  KEY `idx_building_id` (`building_id`),
  KEY `idx_community_id` (`community_id`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='楼层表';

-- 房屋表
CREATE TABLE IF NOT EXISTS `property_house` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '房屋ID',
  `name` varchar(100) NOT NULL COMMENT '房屋名称',
  `code` varchar(50) DEFAULT NULL COMMENT '房屋编码',
  `floor_id` bigint NOT NULL COMMENT '楼层ID',
  `unit_id` bigint DEFAULT NULL COMMENT '单元ID',
  `building_id` bigint DEFAULT NULL COMMENT '楼栋ID',
  `community_id` bigint DEFAULT NULL COMMENT '小区ID',
  `project_id` bigint NOT NULL COMMENT '项目ID',
  `house_type` tinyint DEFAULT NULL COMMENT '房屋类型',
  `house_status` tinyint DEFAULT NULL COMMENT '房屋状态（0空置 1自住 2出租）',
  `build_area` decimal(10,2) DEFAULT NULL COMMENT '建筑面积',
  `inner_area` decimal(10,2) DEFAULT NULL COMMENT '套内面积',
  `shared_area` decimal(10,2) DEFAULT NULL COMMENT '公摊面积',
  `bedroom_count` int DEFAULT NULL COMMENT '卧室数',
  `living_room_count` int DEFAULT NULL COMMENT '客厅数',
  `bathroom_count` int DEFAULT NULL COMMENT '卫生间数',
  `kitchen_count` int DEFAULT NULL COMMENT '厨房数',
  `orientation` varchar(20) DEFAULT NULL COMMENT '朝向',
  `build_date` date DEFAULT NULL COMMENT '建造日期',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_code` (`code`),
  KEY `idx_floor_id` (`floor_id`),
  KEY `idx_unit_id` (`unit_id`),
  KEY `idx_building_id` (`building_id`),
  KEY `idx_community_id` (`community_id`),
  KEY `idx_project_id` (`project_id`),
  KEY `idx_house_status` (`house_status`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='房屋表';

-- 住户表
CREATE TABLE IF NOT EXISTS `property_resident` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '住户ID',
  `name` varchar(100) NOT NULL COMMENT '住户姓名',
  `id_card` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `project_id` bigint NOT NULL COMMENT '项目ID',
  `house_id` bigint NOT NULL COMMENT '房屋ID',
  `community_id` bigint DEFAULT NULL COMMENT '小区ID',
  `resident_type` tinyint NOT NULL COMMENT '住户类型（0业主 1租客 2家属）',
  `gender` tinyint DEFAULT NULL COMMENT '性别（0男 1女）',
  `birth_date` date DEFAULT NULL COMMENT '出生日期',
  `move_in_date` date DEFAULT NULL COMMENT '入住日期',
  `move_out_date` date DEFAULT NULL COMMENT '搬出日期',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_id_card` (`id_card`),
  KEY `idx_project_id` (`project_id`),
  KEY `idx_house_id` (`house_id`),
  KEY `idx_community_id` (`community_id`),
  KEY `idx_resident_type` (`resident_type`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='住户表';

-- ========== 收费规则模块 ==========

-- 收费项目表
CREATE TABLE IF NOT EXISTS `property_charge_item` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '项目ID',
  `name` varchar(100) NOT NULL COMMENT '项目名称',
  `code` varchar(50) DEFAULT NULL COMMENT '项目编码',
  `project_id` bigint NOT NULL COMMENT '物业项目ID',
  `item_type` tinyint NOT NULL COMMENT '项目类型（0物业费 1水电费 2车位费）',
  `charge_type` tinyint NOT NULL COMMENT '收费类型（0周期性 1一次性）',
  `calc_type` tinyint NOT NULL COMMENT '计算类型（0按面积 1按户数 2固定金额）',
  `unit` varchar(20) DEFAULT NULL COMMENT '单位',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_code` (`code`),
  KEY `idx_project_id` (`project_id`),
  KEY `idx_item_type` (`item_type`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='收费项目表';

-- 收费规则表
CREATE TABLE IF NOT EXISTS `property_charge_rule` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '规则ID',
  `name` varchar(100) NOT NULL COMMENT '规则名称',
  `code` varchar(50) DEFAULT NULL COMMENT '规则编码',
  `item_id` bigint NOT NULL COMMENT '收费项目ID',
  `project_id` bigint NOT NULL COMMENT '物业项目ID',
  `community_id` bigint DEFAULT NULL COMMENT '小区ID',
  `price` decimal(10,4) NOT NULL COMMENT '价格',
  `calc_type` tinyint NOT NULL COMMENT '计算类型',
  `unit` varchar(20) DEFAULT NULL COMMENT '单位',
  `billing_cycle` int NOT NULL COMMENT '账期周期（月）',
  `effective_date` date DEFAULT NULL COMMENT '生效日期',
  `expiry_date` date DEFAULT NULL COMMENT '失效日期',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_code` (`code`),
  KEY `idx_item_id` (`item_id`),
  KEY `idx_project_id` (`project_id`),
  KEY `idx_community_id` (`community_id`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='收费规则表';

-- 规则应用表
CREATE TABLE IF NOT EXISTS `property_charge_rule_apply` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '应用ID',
  `rule_id` bigint NOT NULL COMMENT '规则ID',
  `item_id` bigint DEFAULT NULL COMMENT '收费项目ID',
  `house_id` bigint NOT NULL COMMENT '房屋ID',
  `community_id` bigint DEFAULT NULL COMMENT '小区ID',
  `project_id` bigint NOT NULL COMMENT '项目ID',
  `apply_type` tinyint DEFAULT NULL COMMENT '应用类型',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_rule_id` (`rule_id`),
  KEY `idx_item_id` (`item_id`),
  KEY `idx_house_id` (`house_id`),
  KEY `idx_community_id` (`community_id`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='规则应用表';

-- 规则版本历史表
CREATE TABLE IF NOT EXISTS `property_charge_rule_history` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '历史ID',
  `rule_id` bigint NOT NULL COMMENT '规则ID',
  `version` int NOT NULL COMMENT '版本号',
  `price` decimal(10,4) NOT NULL COMMENT '价格',
  `calc_type` tinyint NOT NULL COMMENT '计算类型',
  `unit` varchar(20) DEFAULT NULL COMMENT '单位',
  `billing_cycle` int NOT NULL COMMENT '账期周期',
  `effective_date` date DEFAULT NULL COMMENT '生效日期',
  `expiry_date` date DEFAULT NULL COMMENT '失效日期',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_rule_id` (`rule_id`),
  KEY `idx_version` (`version`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='规则版本历史表';

-- ========== 账单体系模块 ==========

-- 应收账单主表
CREATE TABLE IF NOT EXISTS `property_receivable_bill` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '账单ID',
  `bill_no` varchar(50) DEFAULT NULL COMMENT '账单编号',
  `house_id` bigint NOT NULL COMMENT '房屋ID',
  `community_id` bigint DEFAULT NULL COMMENT '小区ID',
  `project_id` bigint NOT NULL COMMENT '项目ID',
  `resident_id` bigint DEFAULT NULL COMMENT '住户ID',
  `bill_type` tinyint NOT NULL COMMENT '账单类型',
  `bill_period` int NOT NULL COMMENT '账期',
  `bill_date` date NOT NULL COMMENT '账单日期',
  `due_date` date DEFAULT NULL COMMENT '到期日期',
  `total_amount` decimal(12,2) NOT NULL COMMENT '总金额',
  `paid_amount` decimal(12,2) DEFAULT 0 COMMENT '已付金额',
  `outstanding_amount` decimal(12,2) DEFAULT NULL COMMENT '欠款金额',
  `pay_status` tinyint NOT NULL DEFAULT 0 COMMENT '支付状态（0未支付 1部分支付 2已支付）',
  `bill_status` tinyint NOT NULL DEFAULT 0 COMMENT '账单状态（0正常 1取消）',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_bill_no` (`bill_no`),
  KEY `idx_house_id` (`house_id`),
  KEY `idx_community_id` (`community_id`),
  KEY `idx_project_id` (`project_id`),
  KEY `idx_pay_status` (`pay_status`),
  KEY `idx_bill_status` (`bill_status`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='应收账单主表';

-- 账单明细表
CREATE TABLE IF NOT EXISTS `property_bill_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '明细ID',
  `bill_id` bigint NOT NULL COMMENT '账单ID',
  `item_id` bigint DEFAULT NULL COMMENT '收费项目ID',
  `rule_id` bigint DEFAULT NULL COMMENT '规则ID',
  `item_name` varchar(100) DEFAULT NULL COMMENT '项目名称',
  `charge_type` tinyint NOT NULL COMMENT '收费类型',
  `calc_type` tinyint NOT NULL COMMENT '计算类型',
  `price` decimal(10,4) NOT NULL COMMENT '价格',
  `quantity` decimal(10,2) NOT NULL COMMENT '数量',
  `amount` decimal(12,2) NOT NULL COMMENT '金额',
  `paid_amount` decimal(12,2) DEFAULT 0 COMMENT '已付金额',
  `pay_status` tinyint NOT NULL DEFAULT 0 COMMENT '支付状态',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_bill_id` (`bill_id`),
  KEY `idx_item_id` (`item_id`),
  KEY `idx_pay_status` (`pay_status`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='账单明细表';

-- ========== 收银结算模块 ==========

-- 收银记录主表
CREATE TABLE IF NOT EXISTS `property_cashier_record` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `record_no` varchar(50) DEFAULT NULL COMMENT '记录编号',
  `project_id` bigint NOT NULL COMMENT '项目ID',
  `bill_id` bigint NOT NULL COMMENT '账单ID',
  `house_id` bigint DEFAULT NULL COMMENT '房屋ID',
  `community_id` bigint DEFAULT NULL COMMENT '小区ID',
  `resident_id` bigint DEFAULT NULL COMMENT '住户ID',
  `pay_type` tinyint NOT NULL COMMENT '支付类型（0现金 1银行卡 2微信 3支付宝）',
  `total_amount` decimal(12,2) NOT NULL COMMENT '总金额',
  `paid_amount` decimal(12,2) DEFAULT 0 COMMENT '已付金额',
  `pay_status` tinyint NOT NULL DEFAULT 0 COMMENT '支付状态',
  `record_status` tinyint NOT NULL DEFAULT 0 COMMENT '记录状态',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_record_no` (`record_no`),
  KEY `idx_project_id` (`project_id`),
  KEY `idx_bill_id` (`bill_id`),
  KEY `idx_house_id` (`house_id`),
  KEY `idx_community_id` (`community_id`),
  KEY `idx_pay_status` (`pay_status`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='收银记录主表';

-- 支付渠道明细表
CREATE TABLE IF NOT EXISTS `property_cashier_payment` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '支付ID',
  `record_id` bigint NOT NULL COMMENT '收银记录ID',
  `payment_no` varchar(50) DEFAULT NULL COMMENT '支付编号',
  `pay_channel` varchar(20) NOT NULL COMMENT '支付渠道',
  `amount` decimal(12,2) NOT NULL COMMENT '金额',
  `pay_status` tinyint NOT NULL DEFAULT 0 COMMENT '支付状态',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_record_id` (`record_id`),
  KEY `idx_pay_channel` (`pay_channel`),
  KEY `idx_pay_status` (`pay_status`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='支付渠道明细表';

-- 核销明细表
CREATE TABLE IF NOT EXISTS `property_write_off_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '核销ID',
  `record_id` bigint NOT NULL COMMENT '收银记录ID',
  `payment_id` bigint DEFAULT NULL COMMENT '支付ID',
  `bill_id` bigint NOT NULL COMMENT '账单ID',
  `bill_detail_id` bigint DEFAULT NULL COMMENT '账单明细ID',
  `amount` decimal(12,2) NOT NULL COMMENT '金额',
  `write_off_type` tinyint NOT NULL COMMENT '核销类型',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态',
  `write_off_time` datetime DEFAULT NULL COMMENT '核销时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_record_id` (`record_id`),
  KEY `idx_bill_id` (`bill_id`),
  KEY `idx_status` (`status`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='核销明细表';

-- ========== 退款体系模块 ==========

-- 退款单主表
CREATE TABLE IF NOT EXISTS `property_refund_record` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '退款ID',
  `refund_no` varchar(50) DEFAULT NULL COMMENT '退款编号',
  `project_id` bigint NOT NULL COMMENT '项目ID',
  `cashier_record_id` bigint NOT NULL COMMENT '收银记录ID',
  `bill_id` bigint NOT NULL COMMENT '账单ID',
  `house_id` bigint DEFAULT NULL COMMENT '房屋ID',
  `community_id` bigint DEFAULT NULL COMMENT '小区ID',
  `refund_type` tinyint NOT NULL COMMENT '退款类型',
  `total_amount` decimal(12,2) NOT NULL COMMENT '总金额',
  `refunded_amount` decimal(12,2) DEFAULT 0 COMMENT '已退金额',
  `refund_status` tinyint NOT NULL DEFAULT 0 COMMENT '退款状态',
  `approval_status` tinyint NOT NULL DEFAULT 0 COMMENT '审批状态',
  `refund_time` datetime DEFAULT NULL COMMENT '退款时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_refund_no` (`refund_no`),
  KEY `idx_project_id` (`project_id`),
  KEY `idx_cashier_record_id` (`cashier_record_id`),
  KEY `idx_bill_id` (`bill_id`),
  KEY `idx_house_id` (`house_id`),
  KEY `idx_refund_status` (`refund_status`),
  KEY `idx_approval_status` (`approval_status`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='退款单主表';

-- 退款明细表
CREATE TABLE IF NOT EXISTS `property_refund_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '退款明细ID',
  `refund_id` bigint NOT NULL COMMENT '退款单ID',
  `bill_detail_id` bigint DEFAULT NULL COMMENT '账单明细ID',
  `item_name` varchar(100) DEFAULT NULL COMMENT '项目名称',
  `amount` decimal(12,2) NOT NULL COMMENT '金额',
  `refunded_amount` decimal(12,2) DEFAULT 0 COMMENT '已退金额',
  `refund_status` tinyint NOT NULL DEFAULT 0 COMMENT '退款状态',
  `refund_time` datetime DEFAULT NULL COMMENT '退款时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_refund_id` (`refund_id`),
  KEY `idx_bill_detail_id` (`bill_detail_id`),
  KEY `idx_refund_status` (`refund_status`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='退款明细表';

-- ========== 优惠调价模块 ==========

-- 优惠记录表
CREATE TABLE IF NOT EXISTS `property_discount_record` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '优惠ID',
  `discount_no` varchar(50) DEFAULT NULL COMMENT '优惠编号',
  `project_id` bigint NOT NULL COMMENT '项目ID',
  `bill_id` bigint NOT NULL COMMENT '账单ID',
  `bill_detail_id` bigint DEFAULT NULL COMMENT '账单明细ID',
  `house_id` bigint DEFAULT NULL COMMENT '房屋ID',
  `community_id` bigint DEFAULT NULL COMMENT '小区ID',
  `discount_type` tinyint NOT NULL COMMENT '优惠类型',
  `discount_amount` decimal(12,2) NOT NULL COMMENT '优惠金额',
  `discount_reason` varchar(200) DEFAULT NULL COMMENT '优惠原因',
  `approval_status` tinyint NOT NULL DEFAULT 0 COMMENT '审批状态',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_project_id` (`project_id`),
  KEY `idx_bill_id` (`bill_id`),
  KEY `idx_house_id` (`house_id`),
  KEY `idx_approval_status` (`approval_status`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='优惠记录表';

-- 调价记录表
CREATE TABLE IF NOT EXISTS `property_adjust_record` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '调价ID',
  `adjust_no` varchar(50) DEFAULT NULL COMMENT '调价编号',
  `bill_id` bigint NOT NULL COMMENT '账单ID',
  `bill_detail_id` bigint DEFAULT NULL COMMENT '账单明细ID',
  `house_id` bigint DEFAULT NULL COMMENT '房屋ID',
  `community_id` bigint DEFAULT NULL COMMENT '小区ID',
  `adjust_type` tinyint NOT NULL COMMENT '调价类型（0上调 1下调）',
  `original_amount` decimal(12,2) NOT NULL COMMENT '原金额',
  `adjust_amount` decimal(12,2) NOT NULL COMMENT '调整金额',
  `new_amount` decimal(12,2) NOT NULL COMMENT '新金额',
  `adjust_reason` varchar(200) DEFAULT NULL COMMENT '调价原因',
  `approval_status` tinyint NOT NULL DEFAULT 0 COMMENT '审批状态',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_bill_id` (`bill_id`),
  KEY `idx_house_id` (`house_id`),
  KEY `idx_approval_status` (`approval_status`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='调价记录表';

-- ========== 结算批次模块 ==========

-- 结算批次表
CREATE TABLE IF NOT EXISTS `property_settlement_batch` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '批次ID',
  `batch_no` varchar(50) DEFAULT NULL COMMENT '批次编号',
  `project_id` bigint NOT NULL COMMENT '项目ID',
  `community_id` bigint DEFAULT NULL COMMENT '小区ID',
  `batch_type` tinyint NOT NULL COMMENT '批次类型',
  `settlement_period` int NOT NULL COMMENT '结算周期',
  `total_amount` decimal(14,2) NOT NULL COMMENT '总金额',
  `settled_amount` decimal(14,2) DEFAULT 0 COMMENT '已结算金额',
  `record_count` int DEFAULT 0 COMMENT '记录数',
  `settlement_status` tinyint NOT NULL DEFAULT 0 COMMENT '结算状态',
  `settlement_time` datetime DEFAULT NULL COMMENT '结算时间',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_batch_no` (`batch_no`),
  KEY `idx_project_id` (`project_id`),
  KEY `idx_community_id` (`community_id`),
  KEY `idx_settlement_status` (`settlement_status`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='结算批次表';

-- ========== 车位经营模块 ==========

-- 车场表
CREATE TABLE IF NOT EXISTS `property_parking_lot` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '车场ID',
  `name` varchar(100) NOT NULL COMMENT '车场名称',
  `code` varchar(50) DEFAULT NULL COMMENT '车场编码',
  `project_id` bigint NOT NULL COMMENT '项目ID',
  `community_id` bigint NOT NULL COMMENT '小区ID',
  `lot_type` tinyint DEFAULT NULL COMMENT '车场类型',
  `total_spots` int DEFAULT 0 COMMENT '总车位数',
  `available_spots` int DEFAULT 0 COMMENT '可用车位数',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_project_id` (`project_id`),
  KEY `idx_community_id` (`community_id`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='车场表';

-- 车位表
CREATE TABLE IF NOT EXISTS `property_parking_spot` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '车位ID',
  `spot_no` varchar(50) NOT NULL COMMENT '车位编号',
  `project_id` bigint NOT NULL COMMENT '项目ID',
  `lot_id` bigint NOT NULL COMMENT '车场ID',
  `community_id` bigint DEFAULT NULL COMMENT '小区ID',
  `spot_type` tinyint DEFAULT NULL COMMENT '车位类型',
  `spot_status` tinyint DEFAULT NULL COMMENT '车位状态（0空闲 1已租 2已售）',
  `area` decimal(10,2) DEFAULT NULL COMMENT '面积',
  `floor_no` int DEFAULT NULL COMMENT '楼层',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_project_id` (`project_id`),
  KEY `idx_lot_id` (`lot_id`),
  KEY `idx_community_id` (`community_id`),
  KEY `idx_spot_no` (`spot_no`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='车位表';

-- 车位租赁表
CREATE TABLE IF NOT EXISTS `property_parking_lease` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '租赁ID',
  `lease_no` varchar(50) DEFAULT NULL COMMENT '租赁编号',
  `project_id` bigint NOT NULL COMMENT '项目ID',
  `spot_id` bigint NOT NULL COMMENT '车位ID',
  `vehicle_id` bigint DEFAULT NULL COMMENT '车辆ID',
  `resident_id` bigint DEFAULT NULL COMMENT '住户ID',
  `house_id` bigint DEFAULT NULL COMMENT '房屋ID',
  `community_id` bigint DEFAULT NULL COMMENT '小区ID',
  `lease_type` tinyint NOT NULL COMMENT '租赁类型',
  `start_date` date NOT NULL COMMENT '开始日期',
  `end_date` date DEFAULT NULL COMMENT '结束日期',
  `monthly_fee` decimal(10,2) NOT NULL COMMENT '月租费用',
  `lease_status` tinyint NOT NULL DEFAULT 0 COMMENT '租赁状态',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_project_id` (`project_id`),
  KEY `idx_spot_id` (`spot_id`),
  KEY `idx_vehicle_id` (`vehicle_id`),
  KEY `idx_resident_id` (`resident_id`),
  KEY `idx_house_id` (`house_id`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='车位租赁表';

-- 车辆表
CREATE TABLE IF NOT EXISTS `property_vehicle` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '车辆ID',
  `plate_no` varchar(20) NOT NULL COMMENT '车牌号',
  `project_id` bigint NOT NULL COMMENT '项目ID',
  `resident_id` bigint DEFAULT NULL COMMENT '住户ID',
  `house_id` bigint DEFAULT NULL COMMENT '房屋ID',
  `community_id` bigint DEFAULT NULL COMMENT '小区ID',
  `vehicle_type` tinyint DEFAULT NULL COMMENT '车辆类型',
  `vehicle_color` varchar(20) DEFAULT NULL COMMENT '车辆颜色',
  `owner_name` varchar(50) DEFAULT NULL COMMENT '车主姓名',
  `owner_phone` varchar(20) DEFAULT NULL COMMENT '车主电话',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_project_id` (`project_id`),
  KEY `idx_plate_no` (`plate_no`),
  KEY `idx_resident_id` (`resident_id`),
  KEY `idx_house_id` (`house_id`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='车辆表';

-- ========== 审批配置模块 ==========

-- 审批阈值配置表
CREATE TABLE IF NOT EXISTS `property_approval_config` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '配置ID',
  `project_id` bigint NOT NULL COMMENT '项目ID',
  `community_id` bigint DEFAULT NULL COMMENT '小区ID',
  `config_type` tinyint NOT NULL COMMENT '配置类型（0退款 1优惠 2调价）',
  `min_amount` decimal(12,2) DEFAULT NULL COMMENT '最小金额',
  `max_amount` decimal(12,2) DEFAULT NULL COMMENT '最大金额',
  `approval_level` tinyint NOT NULL COMMENT '审批层级',
  `approver_ids` varchar(500) DEFAULT NULL COMMENT '审批人ID列表',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_project_id` (`project_id`),
  KEY `idx_community_id` (`community_id`),
  KEY `idx_config_type` (`config_type`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='审批阈值配置表';

-- ========== 统一事件与工单中心 ==========

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

-- 智慧物业 V1B：通用巡检标准版本与现场点位

CREATE TABLE IF NOT EXISTS `property_inspection_standard` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `project_id` bigint NOT NULL COMMENT '项目ID',
  `standard_code` varchar(64) NOT NULL COMMENT '稳定标准编码',
  `name` varchar(200) NOT NULL,
  `specialty` varchar(40) NOT NULL COMMENT '安消/设备/秩序/环境/保洁等',
  `check_method` varchar(1000) NOT NULL,
  `pass_criteria` varchar(1000) NOT NULL,
  `risk_level` tinyint NOT NULL COMMENT '风险等级1-4',
  `evidence_types` varchar(100) NOT NULL COMMENT '必填证据类型',
  `rectification_hours` int NOT NULL COMMENT '整改时限小时',
  `sop_url` varchar(1000) DEFAULT NULL,
  `event_category_code` varchar(64) DEFAULT NULL COMMENT '重大异常转事件分类',
  `standard_version` int NOT NULL,
  `status` tinyint NOT NULL DEFAULT 0,
  `published` bit(1) NOT NULL DEFAULT b'0',
  `version` int NOT NULL DEFAULT 0,
  `tenant_id` bigint NOT NULL DEFAULT 0,
  `creator` varchar(64) DEFAULT '',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updater` varchar(64) DEFAULT '',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_project_code_version` (`tenant_id`, `project_id`, `standard_code`, `standard_version`),
  KEY `idx_project_specialty_status` (`project_id`, `specialty`, `status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='物业巡检标准版本表';

CREATE TABLE IF NOT EXISTS `property_inspection_point` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `project_id` bigint NOT NULL COMMENT '项目ID',
  `community_id` bigint DEFAULT NULL,
  `space_id` bigint DEFAULT NULL,
  `asset_id` bigint DEFAULT NULL,
  `point_code` varchar(64) NOT NULL,
  `name` varchar(200) NOT NULL,
  `point_type` varchar(40) NOT NULL,
  `qr_code` varchar(200) DEFAULT NULL,
  `nfc_code` varchar(200) DEFAULT NULL,
  `risk_level` tinyint NOT NULL,
  `sequence_required` bit(1) NOT NULL DEFAULT b'0',
  `status` tinyint NOT NULL DEFAULT 0,
  `disable_reason` varchar(500) DEFAULT NULL,
  `version` int NOT NULL DEFAULT 0,
  `tenant_id` bigint NOT NULL DEFAULT 0,
  `creator` varchar(64) DEFAULT '',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updater` varchar(64) DEFAULT '',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_project_point_code` (`tenant_id`, `project_id`, `point_code`),
  UNIQUE KEY `uk_tenant_qr_code` (`tenant_id`, `qr_code`),
  KEY `idx_project_type_status` (`project_id`, `point_type`, `status`),
  KEY `idx_space_id` (`space_id`),
  KEY `idx_asset_id` (`asset_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='物业巡检现场点位表';


CREATE TABLE IF NOT EXISTS `property_inspection_plan` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `project_id` bigint NOT NULL,
  `name` varchar(200) NOT NULL,
  `specialty` varchar(40) NOT NULL,
  `standard_ids` varchar(1000) NOT NULL COMMENT '已发布标准ID列表',
  `point_ids` varchar(2000) NOT NULL COMMENT '有序点位ID列表',
  `frequency_type` varchar(20) NOT NULL COMMENT 'DAILY/WEEKLY/MONTHLY',
  `interval_value` int NOT NULL DEFAULT 1,
  `window_minutes` int NOT NULL,
  `effective_start` date NOT NULL,
  `effective_end` date DEFAULT NULL,
  `skip_holidays` bit(1) NOT NULL DEFAULT b'0',
  `supplement_policy` varchar(20) NOT NULL COMMENT '补检策略',
  `inspector_user_id` bigint NOT NULL,
  `status` tinyint NOT NULL DEFAULT 0,
  `next_generate_time` datetime NOT NULL,
  `version` int NOT NULL DEFAULT 0,
  `tenant_id` bigint NOT NULL DEFAULT 0,
  `creator` varchar(64) DEFAULT '', `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updater` varchar(64) DEFAULT '', `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`), KEY `idx_project_status_next` (`project_id`, `status`, `next_generate_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='物业巡检计划表';

CREATE TABLE IF NOT EXISTS `property_inspection_task` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `task_no` varchar(40) NOT NULL,
  `project_id` bigint NOT NULL,
  `plan_id` bigint NOT NULL,
  `plan_name` varchar(200) NOT NULL,
  `specialty` varchar(40) NOT NULL,
  `standard_snapshot` varchar(2000) NOT NULL COMMENT '标准ID与版本快照',
  `point_ids` varchar(2000) NOT NULL COMMENT '有序点位快照',
  `planned_start_time` datetime NOT NULL,
  `planned_end_time` datetime NOT NULL,
  `original_inspector_user_id` bigint NOT NULL,
  `inspector_user_id` bigint NOT NULL,
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '0待执行 10执行中 20已提交 30已关闭 90终止',
  `started_time` datetime DEFAULT NULL,
  `submitted_time` datetime DEFAULT NULL,
  `check_count` int NOT NULL DEFAULT 0,
  `abnormal_count` int NOT NULL DEFAULT 0,
  `on_time` bit(1) DEFAULT NULL,
  `terminate_reason` varchar(500) DEFAULT NULL,
  `version` int NOT NULL DEFAULT 0,
  `tenant_id` bigint NOT NULL DEFAULT 0,
  `creator` varchar(64) DEFAULT '', `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updater` varchar(64) DEFAULT '', `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`), UNIQUE KEY `uk_tenant_task_no` (`tenant_id`, `task_no`),
  KEY `idx_project_inspector_status` (`project_id`, `inspector_user_id`, `status`), KEY `idx_plan_id` (`plan_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='物业巡检任务表';

CREATE TABLE IF NOT EXISTS `property_inspection_record` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `project_id` bigint NOT NULL,
  `task_id` bigint NOT NULL,
  `point_id` bigint NOT NULL,
  `standard_id` bigint NOT NULL,
  `standard_version` int NOT NULL,
  `sequence_no` int NOT NULL,
  `verification_method` varchar(30) NOT NULL COMMENT 'QR/NFC/PHOTO/TEMP_CODE',
  `verification_code` varchar(200) DEFAULT NULL,
  `result` tinyint NOT NULL COMMENT '0正常 10异常 20例外',
  `evidence_urls` varchar(4000) NOT NULL,
  `remark` varchar(1000) DEFAULT NULL,
  `exception_reason_code` varchar(64) DEFAULT NULL,
  `client_operation_id` varchar(100) NOT NULL COMMENT '离线操作幂等键',
  `submitted_user_id` bigint DEFAULT NULL,
  `submitted_time` datetime NOT NULL,
  `suspicious` bit(1) NOT NULL DEFAULT b'0',
  `suspicious_reason` varchar(500) DEFAULT NULL,
  `conflict_id` bigint DEFAULT NULL,
  `retained_after_conflict` bit(1) NOT NULL DEFAULT b'0',
  `tenant_id` bigint NOT NULL DEFAULT 0,
  `creator` varchar(64) DEFAULT '', `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updater` varchar(64) DEFAULT '', `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`), UNIQUE KEY `uk_tenant_client_operation` (`tenant_id`, `client_operation_id`),
  KEY `idx_task_sequence` (`task_id`, `sequence_no`), KEY `idx_project_point` (`project_id`, `point_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='物业巡检现场记录表';

CREATE TABLE IF NOT EXISTS `property_inspection_issue` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `issue_no` varchar(40) NOT NULL,
  `project_id` bigint NOT NULL,
  `task_id` bigint NOT NULL,
  `record_id` bigint NOT NULL,
  `point_id` bigint NOT NULL,
  `standard_id` bigint NOT NULL,
  `risk_level` tinyint NOT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `temporary_control` varchar(1000) NOT NULL,
  `rectifier_user_id` bigint NOT NULL,
  `reviewer_user_id` bigint NOT NULL,
  `rectification_deadline` datetime NOT NULL,
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '0待整改 10待复查 20关闭',
  `before_evidence_urls` varchar(4000) NOT NULL,
  `rectification_result` varchar(2000) DEFAULT NULL,
  `after_evidence_urls` varchar(4000) DEFAULT NULL,
  `review_result` varchar(1000) DEFAULT NULL,
  `review_count` int NOT NULL DEFAULT 0,
  `suspected_unresolved` bit(1) NOT NULL DEFAULT b'0',
  `event_id` bigint DEFAULT NULL,
  `event_status` tinyint DEFAULT NULL COMMENT '关联事件状态回写',
  `event_closed_time` datetime DEFAULT NULL,
  `event_result_snapshot` varchar(4000) DEFAULT NULL COMMENT '关联事件关闭结果快照',
  `rectified_time` datetime DEFAULT NULL,
  `reviewed_time` datetime DEFAULT NULL,
  `version` int NOT NULL DEFAULT 0,
  `tenant_id` bigint NOT NULL DEFAULT 0,
  `creator` varchar(64) DEFAULT '', `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updater` varchar(64) DEFAULT '', `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`), UNIQUE KEY `uk_tenant_issue_no` (`tenant_id`, `issue_no`),
  KEY `idx_project_status_deadline` (`project_id`, `status`, `rectification_deadline`), KEY `idx_event_id` (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='物业巡检异常整改复查表';

CREATE TABLE IF NOT EXISTS `property_inspection_task_assignment_log` (
  `id` bigint NOT NULL AUTO_INCREMENT, `project_id` bigint NOT NULL, `task_id` bigint NOT NULL,
  `action` varchar(20) NOT NULL, `from_user_id` bigint DEFAULT NULL, `to_user_id` bigint DEFAULT NULL,
  `from_status` tinyint NOT NULL, `to_status` tinyint NOT NULL, `reason` varchar(500) NOT NULL,
  `operator_user_id` bigint DEFAULT NULL, `task_version` int NOT NULL,
  `tenant_id` bigint NOT NULL DEFAULT 0, `creator` varchar(64) DEFAULT '', `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updater` varchar(64) DEFAULT '', `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` bit(1) NOT NULL DEFAULT b'0', PRIMARY KEY (`id`), KEY `idx_task_id` (`task_id`), KEY `idx_project_action` (`project_id`, `action`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='巡检任务代班转派终止责任链';

CREATE TABLE IF NOT EXISTS `property_inspection_conflict` (
  `id` bigint NOT NULL AUTO_INCREMENT, `conflict_no` varchar(40) NOT NULL, `project_id` bigint NOT NULL,
  `task_id` bigint NOT NULL, `client_task_version` int NOT NULL, `server_task_version` int NOT NULL,
  `server_task_status` tinyint NOT NULL, `point_id` bigint NOT NULL, `standard_id` bigint NOT NULL,
  `sequence_no` int NOT NULL, `verification_method` varchar(30) NOT NULL, `verification_code` varchar(200) DEFAULT NULL,
  `result` tinyint NOT NULL, `evidence_urls` varchar(4000) NOT NULL, `remark` varchar(1000) DEFAULT NULL,
  `exception_reason_code` varchar(64) DEFAULT NULL, `client_operation_id` varchar(100) NOT NULL,
  `temporary_control` varchar(1000) DEFAULT NULL, `rectifier_user_id` bigint DEFAULT NULL, `reviewer_user_id` bigint DEFAULT NULL,
  `conflict_reason` varchar(100) NOT NULL, `status` tinyint NOT NULL DEFAULT 0,
  `resolution` varchar(40) DEFAULT NULL, `resolution_remark` varchar(1000) DEFAULT NULL,
  `replacement_task_id` bigint DEFAULT NULL, `resolver_user_id` bigint DEFAULT NULL, `resolved_time` datetime DEFAULT NULL,
  `version` int NOT NULL DEFAULT 0, `tenant_id` bigint NOT NULL DEFAULT 0,
  `creator` varchar(64) DEFAULT '', `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updater` varchar(64) DEFAULT '', `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` bit(1) NOT NULL DEFAULT b'0', PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_conflict_no` (`tenant_id`, `conflict_no`),
  UNIQUE KEY `uk_tenant_client_operation` (`tenant_id`, `client_operation_id`),
  KEY `idx_project_status` (`project_id`, `status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='巡检弱网数据冲突队列';

CREATE TABLE IF NOT EXISTS `property_inspection_quality_sample` (
  `id` bigint NOT NULL AUTO_INCREMENT, `sample_no` varchar(40) NOT NULL, `project_id` bigint NOT NULL,
  `task_id` bigint NOT NULL, `sample_type` varchar(30) NOT NULL, `reviewer_user_id` bigint NOT NULL,
  `record_count` int NOT NULL, `suspicious_count` int NOT NULL DEFAULT 0, `score` int DEFAULT NULL,
  `passed` bit(1) DEFAULT NULL, `findings` varchar(2000) DEFAULT NULL, `improvement_actions` varchar(2000) DEFAULT NULL,
  `status` tinyint NOT NULL DEFAULT 0, `completed_time` datetime DEFAULT NULL, `version` int NOT NULL DEFAULT 0,
  `tenant_id` bigint NOT NULL DEFAULT 0, `creator` varchar(64) DEFAULT '', `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updater` varchar(64) DEFAULT '', `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` bit(1) NOT NULL DEFAULT b'0', PRIMARY KEY (`id`), UNIQUE KEY `uk_tenant_sample_no` (`tenant_id`, `sample_no`),
  KEY `idx_project_status` (`project_id`, `status`), KEY `idx_task_id` (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='物业品质抽检记录';

INSERT INTO `infra_job`
  (`name`, `status`, `handler_name`, `handler_param`, `cron_expression`, `retry_count`, `retry_interval`, `monitor_timeout`, `creator`, `updater`)
SELECT '物业巡检计划生成任务', 1, 'propertyInspectionTaskGenerateJob', NULL, '0 0/1 * * * ?', 0, 0, 60000, '1', '1'
WHERE NOT EXISTS (SELECT 1 FROM `infra_job` WHERE `handler_name` = 'propertyInspectionTaskGenerateJob' AND `deleted` = b'0');

CREATE TABLE IF NOT EXISTS `property_event` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '事件ID',
  `event_no` varchar(40) NOT NULL COMMENT '事件编号',
  `project_id` bigint NOT NULL COMMENT '项目ID',
  `community_id` bigint DEFAULT NULL COMMENT '小区ID',
  `space_id` bigint DEFAULT NULL COMMENT '空间ID',
  `house_id` bigint DEFAULT NULL COMMENT '房屋ID',
  `asset_id` bigint DEFAULT NULL COMMENT '资产ID',
  `source_type` tinyint NOT NULL COMMENT '来源类型',
  `source_system` varchar(64) DEFAULT NULL COMMENT '来源系统',
  `source_record_id` varchar(100) DEFAULT NULL COMMENT '来源记录ID',
  `category_code` varchar(64) NOT NULL COMMENT '事件分类编码',
  `title` varchar(200) NOT NULL COMMENT '事件标题',
  `description` text NOT NULL COMMENT '事件描述',
  `urgency_level` tinyint NOT NULL COMMENT '紧急程度 1低 2一般 3紧急 4重大',
  `impact_level` tinyint NOT NULL COMMENT '影响程度 1低 2一般 3较大 4重大',
  `safety_level` tinyint NOT NULL DEFAULT 0 COMMENT '安全等级 0无 1低 2高 3重大',
  `confidence` decimal(5,4) DEFAULT NULL COMMENT '识别置信度',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '事件状态',
  `responsible_dept_id` bigint DEFAULT NULL COMMENT '当前责任部门ID',
  `responsible_user_id` bigint DEFAULT NULL COMMENT '当前责任人ID',
  `collaborator_ids` varchar(500) DEFAULT NULL COMMENT '协同方ID列表',
  `sla_rule_id` bigint DEFAULT NULL COMMENT 'SLA规则ID快照',
  `sla_rule_version` int DEFAULT NULL COMMENT 'SLA规则版本快照',
  `sla_arrival_minutes` int NOT NULL DEFAULT 120 COMMENT '到场时限快照（分钟）',
  `sla_start_time` datetime NOT NULL COMMENT '事件 SLA 有效起点',
  `response_deadline` datetime NOT NULL COMMENT '响应SLA截止时间',
  `arrival_deadline` datetime NOT NULL COMMENT '到场SLA截止时间',
  `recovery_deadline` datetime NOT NULL COMMENT '恢复SLA截止时间',
  `close_deadline` datetime NOT NULL COMMENT '关闭SLA截止时间',
  `responded_time` datetime DEFAULT NULL COMMENT '首次人工响应时间',
  `first_dispatched_time` datetime DEFAULT NULL COMMENT '首次派单时间',
  `arrived_time` datetime DEFAULT NULL COMMENT '到场时间',
  `recovered_time` datetime DEFAULT NULL COMMENT '影响恢复时间',
  `closed_time` datetime DEFAULT NULL COMMENT '关闭时间',
  `block_reason` varchar(500) DEFAULT NULL COMMENT '阻塞原因',
  `sla_paused` bit(1) NOT NULL DEFAULT b'0' COMMENT '关闭 SLA 是否暂停',
  `sla_pause_reason_code` varchar(64) DEFAULT NULL COMMENT '当前 SLA 暂停原因编码',
  `sla_pause_started_time` datetime DEFAULT NULL COMMENT '当前 SLA 暂停开始时间',
  `sla_paused_seconds` bigint NOT NULL DEFAULT 0 COMMENT '累计 SLA 暂停秒数',
  `sla_escalation_minutes` int NOT NULL DEFAULT 15 COMMENT '提前升级阈值快照（分钟）',
  `sla_escalation_stage` varchar(32) DEFAULT NULL COMMENT '最近升级时钟阶段',
  `sla_escalation_level` tinyint NOT NULL DEFAULT 0 COMMENT '升级等级 0无 1即将超时 2已超时',
  `sla_escalated_time` datetime DEFAULT NULL COMMENT '最近升级时间',
  `main_event_id` bigint DEFAULT NULL COMMENT '合并后的主事件ID',
  `parent_event_id` bigint DEFAULT NULL COMMENT '拆分来源事件ID',
  `split_reason` varchar(500) DEFAULT NULL COMMENT '拆分依据',
  `cancel_reason` varchar(500) DEFAULT NULL COMMENT '取消原因',
  `recovery_summary` varchar(1000) DEFAULT NULL COMMENT '恢复说明',
  `acceptance_result` varchar(1000) DEFAULT NULL COMMENT '验收结论',
  `root_cause` varchar(1000) DEFAULT NULL COMMENT '根因',
  `solution` varchar(2000) DEFAULT NULL COMMENT '解决方案',
  `reopen_count` int NOT NULL DEFAULT 0 COMMENT '复开次数',
  `version` int NOT NULL DEFAULT 0 COMMENT '并发版本',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_event_no` (`tenant_id`, `event_no`),
  UNIQUE KEY `uk_tenant_source` (`tenant_id`, `source_system`, `source_record_id`),
  KEY `idx_project_status` (`project_id`, `status`),
  KEY `idx_project_close_deadline` (`project_id`, `close_deadline`),
  KEY `idx_responsible_user` (`responsible_user_id`),
  KEY `idx_space_id` (`space_id`),
  KEY `idx_asset_id` (`asset_id`),
  KEY `idx_main_event_id` (`main_event_id`),
  KEY `idx_parent_event_id` (`parent_event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='物业统一事件表';

CREATE TABLE IF NOT EXISTS `property_event_work_order` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '工单ID',
  `work_order_no` varchar(40) NOT NULL COMMENT '工单编号',
  `event_id` bigint NOT NULL COMMENT '事件ID',
  `parent_work_order_id` bigint DEFAULT NULL COMMENT '上一个工单ID',
  `project_id` bigint NOT NULL COMMENT '项目ID',
  `assigned_dept_id` bigint DEFAULT NULL COMMENT '执行部门ID',
  `assignee_user_id` bigint NOT NULL COMMENT '执行人ID',
  `supplier_id` bigint DEFAULT NULL COMMENT '供应商ID',
  `status` tinyint NOT NULL COMMENT '工单状态',
  `planned_arrival_time` datetime DEFAULT NULL COMMENT '计划到场时间',
  `accepted_time` datetime DEFAULT NULL COMMENT '接单时间',
  `arrived_time` datetime DEFAULT NULL COMMENT '到场时间',
  `completed_time` datetime DEFAULT NULL COMMENT '完成时间',
  `reject_reason` varchar(500) DEFAULT NULL COMMENT '拒单原因',
  `transfer_reason` varchar(500) DEFAULT NULL COMMENT '转派原因',
  `process_result` varchar(2000) DEFAULT NULL COMMENT '处置结果',
  `version` int NOT NULL DEFAULT 0 COMMENT '并发版本',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_work_order_no` (`tenant_id`, `work_order_no`),
  KEY `idx_event_id` (`event_id`),
  KEY `idx_parent_work_order_id` (`parent_work_order_id`),
  KEY `idx_project_assignee_status` (`project_id`, `assignee_user_id`, `status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='物业事件执行工单表';

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

CREATE TABLE IF NOT EXISTS `property_event_timeline` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '时间线ID',
  `event_id` bigint NOT NULL COMMENT '事件ID',
  `project_id` bigint NOT NULL COMMENT '项目ID',
  `action` varchar(50) NOT NULL COMMENT '动作',
  `from_status` tinyint DEFAULT NULL COMMENT '原状态',
  `to_status` tinyint DEFAULT NULL COMMENT '新状态',
  `operator_user_id` bigint DEFAULT NULL COMMENT '操作人ID',
  `reason` varchar(500) DEFAULT NULL COMMENT '操作原因',
  `detail` varchar(2000) DEFAULT NULL COMMENT '操作详情',
  `event_version` int NOT NULL DEFAULT 0 COMMENT '事件版本快照',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_event_id` (`event_id`),
  KEY `idx_project_id` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='物业事件审计时间线表';

CREATE TABLE IF NOT EXISTS `property_service_recovery_task` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '恢复任务ID',
  `recovery_no` varchar(40) NOT NULL COMMENT '恢复任务编号',
  `project_id` bigint NOT NULL COMMENT '项目ID',
  `event_id` bigint NOT NULL COMMENT '关联事件ID',
  `trigger_type` tinyint NOT NULL COMMENT '触发类型 1超时 2差评 3重复投诉',
  `trigger_detail` varchar(1000) NOT NULL COMMENT '触发详情',
  `original_rating` tinyint DEFAULT NULL COMMENT '原始评价，不可覆盖',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态 0待分配 10已联系 20计划执行 30已完成',
  `responsible_user_id` bigint DEFAULT NULL COMMENT '更高一级恢复负责人',
  `contact_time` datetime DEFAULT NULL COMMENT '联系时间',
  `contact_result` varchar(1000) DEFAULT NULL COMMENT '联系结果',
  `recovery_plan` varchar(2000) DEFAULT NULL COMMENT '恢复计划',
  `plan_due_time` datetime DEFAULT NULL COMMENT '计划完成时间',
  `completed_time` datetime DEFAULT NULL COMMENT '完成时间',
  `recovered_satisfaction` tinyint DEFAULT NULL COMMENT '恢复后满意度 1-5',
  `version` int NOT NULL DEFAULT 0 COMMENT '并发版本',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_recovery_no` (`tenant_id`, `recovery_no`),
  UNIQUE KEY `uk_event_trigger` (`tenant_id`, `event_id`, `trigger_type`),
  KEY `idx_project_status` (`project_id`, `status`),
  KEY `idx_responsible_user` (`responsible_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='物业服务恢复任务表';

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


CREATE TABLE IF NOT EXISTS `property_event_evidence` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '证据ID',
  `event_id` bigint NOT NULL COMMENT '事件ID',
  `project_id` bigint NOT NULL COMMENT '项目ID',
  `evidence_type` tinyint NOT NULL COMMENT '证据类型',
  `file_url` varchar(1000) NOT NULL COMMENT '证据文件地址',
  `description` varchar(1000) DEFAULT NULL COMMENT '证据说明',
  `submitted_user_id` bigint DEFAULT NULL COMMENT '提交人ID',
  `submitted_time` datetime NOT NULL COMMENT '提交时间',
  `verified` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否验真',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_event_id` (`event_id`),
  KEY `idx_project_id` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='物业事件处置证据表';

INSERT INTO `infra_job`
  (`name`, `status`, `handler_name`, `handler_param`, `cron_expression`, `retry_count`, `retry_interval`, `monitor_timeout`, `creator`, `updater`)
SELECT '物业事件 SLA 自动升级', 1, 'propertyEventSlaEscalationJob', NULL, '0 0/1 * * * ?', 0, 0, 60000, '1', '1'
WHERE NOT EXISTS (
  SELECT 1 FROM `infra_job` WHERE `handler_name` = 'propertyEventSlaEscalationJob' AND `deleted` = b'0'
);
