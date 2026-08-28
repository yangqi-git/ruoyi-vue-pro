-- 智慧物业按项目录入迁移脚本
-- 适用场景：已经执行过旧版 property.sql 的数据库。
-- 新安装环境直接执行最新版 property.sql，无需执行本脚本。

ALTER TABLE `property_building`
  ADD COLUMN `project_id` bigint NULL COMMENT '项目ID' AFTER `code`,
  ADD INDEX `idx_project_id` (`project_id`);

ALTER TABLE `property_unit`
  ADD COLUMN `project_id` bigint NULL COMMENT '项目ID' AFTER `code`,
  ADD INDEX `idx_project_id` (`project_id`);

ALTER TABLE `property_floor`
  ADD COLUMN `project_id` bigint NULL COMMENT '项目ID' AFTER `code`,
  ADD INDEX `idx_project_id` (`project_id`);

ALTER TABLE `property_resident`
  ADD COLUMN `project_id` bigint NULL COMMENT '项目ID' AFTER `phone`,
  ADD INDEX `idx_project_id` (`project_id`);

ALTER TABLE `property_cashier_record`
  ADD COLUMN `project_id` bigint NULL COMMENT '项目ID' AFTER `record_no`,
  ADD INDEX `idx_project_id` (`project_id`);

ALTER TABLE `property_refund_record`
  ADD COLUMN `project_id` bigint NULL COMMENT '项目ID' AFTER `refund_no`,
  ADD INDEX `idx_project_id` (`project_id`);

ALTER TABLE `property_discount_record`
  ADD COLUMN `project_id` bigint NULL COMMENT '项目ID' AFTER `discount_no`,
  ADD INDEX `idx_project_id` (`project_id`);

ALTER TABLE `property_parking_lot`
  ADD COLUMN `project_id` bigint NULL COMMENT '项目ID' AFTER `code`,
  ADD INDEX `idx_project_id` (`project_id`);

ALTER TABLE `property_parking_spot`
  ADD COLUMN `project_id` bigint NULL COMMENT '项目ID' AFTER `spot_no`,
  ADD INDEX `idx_project_id` (`project_id`);

ALTER TABLE `property_parking_lease`
  ADD COLUMN `project_id` bigint NULL COMMENT '项目ID' AFTER `lease_no`,
  ADD INDEX `idx_project_id` (`project_id`);

ALTER TABLE `property_vehicle`
  ADD COLUMN `project_id` bigint NULL COMMENT '项目ID' AFTER `plate_no`,
  ADD INDEX `idx_project_id` (`project_id`);

-- 根据已有小区、房屋和主单关系回填历史数据。
UPDATE `property_building` t
JOIN `property_community` c ON c.id = t.community_id
SET t.project_id = c.project_id
WHERE t.project_id IS NULL;

UPDATE `property_unit` t
JOIN `property_building` b ON b.id = t.building_id
SET t.project_id = b.project_id
WHERE t.project_id IS NULL;

UPDATE `property_floor` t
JOIN `property_unit` u ON u.id = t.unit_id
SET t.project_id = u.project_id
WHERE t.project_id IS NULL;

UPDATE `property_resident` t
JOIN `property_house` h ON h.id = t.house_id
SET t.project_id = h.project_id
WHERE t.project_id IS NULL;

UPDATE `property_cashier_record` t
JOIN `property_receivable_bill` b ON b.id = t.bill_id
SET t.project_id = b.project_id
WHERE t.project_id IS NULL;

UPDATE `property_refund_record` t
JOIN `property_receivable_bill` b ON b.id = t.bill_id
SET t.project_id = b.project_id
WHERE t.project_id IS NULL;

UPDATE `property_discount_record` t
JOIN `property_receivable_bill` b ON b.id = t.bill_id
SET t.project_id = b.project_id
WHERE t.project_id IS NULL;

UPDATE `property_parking_lot` t
JOIN `property_community` c ON c.id = t.community_id
SET t.project_id = c.project_id
WHERE t.project_id IS NULL;

UPDATE `property_parking_spot` t
JOIN `property_parking_lot` l ON l.id = t.lot_id
SET t.project_id = l.project_id
WHERE t.project_id IS NULL;

UPDATE `property_parking_lease` t
JOIN `property_parking_spot` s ON s.id = t.spot_id
SET t.project_id = s.project_id
WHERE t.project_id IS NULL;

UPDATE `property_vehicle` t
JOIN `property_community` c ON c.id = t.community_id
SET t.project_id = c.project_id
WHERE t.project_id IS NULL;

-- 历史脏数据可能没有可追溯的小区/房屋，请先补齐下列查询返回的数据，
-- 再根据实际数据库版本与上线窗口将 project_id 调整为 NOT NULL。
SELECT 'property_building' AS table_name, COUNT(*) AS missing_count FROM property_building WHERE project_id IS NULL
UNION ALL SELECT 'property_unit', COUNT(*) FROM property_unit WHERE project_id IS NULL
UNION ALL SELECT 'property_floor', COUNT(*) FROM property_floor WHERE project_id IS NULL
UNION ALL SELECT 'property_resident', COUNT(*) FROM property_resident WHERE project_id IS NULL
UNION ALL SELECT 'property_cashier_record', COUNT(*) FROM property_cashier_record WHERE project_id IS NULL
UNION ALL SELECT 'property_refund_record', COUNT(*) FROM property_refund_record WHERE project_id IS NULL
UNION ALL SELECT 'property_discount_record', COUNT(*) FROM property_discount_record WHERE project_id IS NULL
UNION ALL SELECT 'property_parking_lot', COUNT(*) FROM property_parking_lot WHERE project_id IS NULL
UNION ALL SELECT 'property_parking_spot', COUNT(*) FROM property_parking_spot WHERE project_id IS NULL
UNION ALL SELECT 'property_parking_lease', COUNT(*) FROM property_parking_lease WHERE project_id IS NULL
UNION ALL SELECT 'property_vehicle', COUNT(*) FROM property_vehicle WHERE project_id IS NULL;
