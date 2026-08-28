package cn.iocoder.yudao.module.property.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

public interface ErrorCodeConstants {

    // ========== 组织层级 1-012-000 ==========
    ErrorCode PROPERTY_PROJECT_NOT_EXISTS = new ErrorCode(1_012_000_001, "物业项目不存在");
    ErrorCode PROJECT_CODE_EXISTS = new ErrorCode(1_012_000_002, "物业项目编码已存在");
    ErrorCode COMMUNITY_NOT_EXISTS = new ErrorCode(1_012_000_003, "小区不存在");
    ErrorCode COMMUNITY_CODE_EXISTS = new ErrorCode(1_012_000_004, "小区编码已存在");

    // ========== 空间资产 1-012-010 ==========
    ErrorCode BUILDING_NOT_EXISTS = new ErrorCode(1_012_010_001, "楼栋不存在");
    ErrorCode BUILDING_CODE_EXISTS = new ErrorCode(1_012_010_002, "楼栋编码已存在");
    ErrorCode UNIT_NOT_EXISTS = new ErrorCode(1_012_010_003, "单元不存在");
    ErrorCode UNIT_CODE_EXISTS = new ErrorCode(1_012_010_004, "单元编码已存在");
    ErrorCode FLOOR_NOT_EXISTS = new ErrorCode(1_012_010_005, "楼层不存在");
    ErrorCode HOUSE_NOT_EXISTS = new ErrorCode(1_012_010_006, "房屋不存在");
    ErrorCode HOUSE_CODE_EXISTS = new ErrorCode(1_012_010_007, "房屋编码已存在");
    ErrorCode RESIDENT_NOT_EXISTS = new ErrorCode(1_012_010_008, "住户不存在");

    // ========== 收费规则 1-012-020 ==========
    ErrorCode CHARGE_ITEM_NOT_EXISTS = new ErrorCode(1_012_020_001, "收费项目不存在");
    ErrorCode CHARGE_ITEM_CODE_EXISTS = new ErrorCode(1_012_020_002, "收费项目编码已存在");
    ErrorCode CHARGE_RULE_NOT_EXISTS = new ErrorCode(1_012_020_003, "收费规则不存在");
    ErrorCode CHARGE_RULE_CODE_EXISTS = new ErrorCode(1_012_020_004, "收费规则编码已存在");
    ErrorCode CHARGE_RULE_APPLY_NOT_EXISTS = new ErrorCode(1_012_020_005, "规则应用不存在");
    ErrorCode CHARGE_RULE_HISTORY_NOT_EXISTS = new ErrorCode(1_012_020_006, "规则历史不存在");

    // ========== 账单体系 1-012-030 ==========
    ErrorCode RECEIVABLE_BILL_NOT_EXISTS = new ErrorCode(1_012_030_001, "应收账单不存在");
    ErrorCode BILL_DETAIL_NOT_EXISTS = new ErrorCode(1_012_030_002, "账单明细不存在");
    ErrorCode BILL_ALREADY_PAID = new ErrorCode(1_012_030_003, "账单已支付");
    ErrorCode BILL_ALREADY_CANCELLED = new ErrorCode(1_012_030_004, "账单已取消");

    // ========== 收银结算 1-012-040 ==========
    ErrorCode CASHIER_RECORD_NOT_EXISTS = new ErrorCode(1_012_040_001, "收银记录不存在");
    ErrorCode CASHIER_PAYMENT_NOT_EXISTS = new ErrorCode(1_012_040_002, "支付渠道明细不存在");
    ErrorCode WRITE_OFF_DETAIL_NOT_EXISTS = new ErrorCode(1_012_040_003, "核销明细不存在");

    // ========== 退款体系 1-012-050 ==========
    ErrorCode REFUND_RECORD_NOT_EXISTS = new ErrorCode(1_012_050_001, "退款单不存在");
    ErrorCode REFUND_DETAIL_NOT_EXISTS = new ErrorCode(1_012_050_002, "退款明细不存在");

    // ========== 优惠调价 1-012-060 ==========
    ErrorCode DISCOUNT_RECORD_NOT_EXISTS = new ErrorCode(1_012_060_001, "优惠记录不存在");
    ErrorCode ADJUST_RECORD_NOT_EXISTS = new ErrorCode(1_012_060_002, "调价记录不存在");

    // ========== 结算批次 1-012-070 ==========
    ErrorCode SETTLEMENT_BATCH_NOT_EXISTS = new ErrorCode(1_012_070_001, "结算批次不存在");

    // ========== 车位经营 1-012-080 ==========
    ErrorCode PARKING_LOT_NOT_EXISTS = new ErrorCode(1_012_080_001, "车场不存在");
    ErrorCode PARKING_SPOT_NOT_EXISTS = new ErrorCode(1_012_080_002, "车位不存在");
    ErrorCode PARKING_LEASE_NOT_EXISTS = new ErrorCode(1_012_080_003, "车位租赁不存在");
    ErrorCode VEHICLE_NOT_EXISTS = new ErrorCode(1_012_080_004, "车辆不存在");

    // ========== 审批配置 1-012-090 ==========
    ErrorCode APPROVAL_CONFIG_NOT_EXISTS = new ErrorCode(1_012_090_001, "审批阈值配置不存在");

    // ========== 事件中心 1-012-100 ==========
    ErrorCode EVENT_NOT_EXISTS = new ErrorCode(1_012_100_001, "物业事件不存在");
    ErrorCode EVENT_SOURCE_DUPLICATE = new ErrorCode(1_012_100_002, "来源记录已创建物业事件");
    ErrorCode EVENT_STATUS_TRANSITION_INVALID = new ErrorCode(1_012_100_003, "当前事件状态不允许执行该操作");
    ErrorCode EVENT_CONCURRENT_UPDATE = new ErrorCode(1_012_100_004, "事件已被其他人更新，请刷新后重试");
    ErrorCode EVENT_WORK_ORDER_NOT_EXISTS = new ErrorCode(1_012_100_005, "事件工单不存在");
    ErrorCode EVENT_EVIDENCE_REQUIRED = new ErrorCode(1_012_100_006, "提交验收前必须上传处置证据");
    ErrorCode EVENT_ACCEPTANCE_REQUIRED = new ErrorCode(1_012_100_007, "验收结论、根因和解决方案不能为空");
    ErrorCode EVENT_CATEGORY_NOT_EXISTS = new ErrorCode(1_012_100_008, "事件分类不存在");
    ErrorCode EVENT_CATEGORY_CODE_EXISTS = new ErrorCode(1_012_100_009, "当前项目的事件分类编码已存在");
    ErrorCode EVENT_SLA_RULE_NOT_EXISTS = new ErrorCode(1_012_100_010, "事件 SLA 规则不存在");
    ErrorCode EVENT_SLA_RULE_EXISTS = new ErrorCode(1_012_100_011, "当前项目、分类和紧急程度的 SLA 规则已存在");
    ErrorCode EVENT_SLA_RULE_INVALID = new ErrorCode(1_012_100_012, "SLA 时限必须满足响应不晚于到场、到场不晚于恢复、恢复不晚于关闭");
    ErrorCode EVENT_CATEGORY_EVIDENCE_INVALID = new ErrorCode(1_012_100_013, "关闭证据类型不正确；要求客户确认时必须包含客户确认证据");
    ErrorCode EVENT_SLA_PAUSE_REASON_INVALID = new ErrorCode(1_012_100_014, "仅客户预约、法定停工或外部审批可暂停关闭 SLA");
    ErrorCode EVENT_SLA_PAUSE_STATE_INVALID = new ErrorCode(1_012_100_015, "事件 SLA 暂停状态不正确，请刷新后重试");
    ErrorCode EVENT_MERGE_INVALID = new ErrorCode(1_012_100_016, "事件无法合并：主事件和来源事件必须属于同一项目且尚未取消或合并");
    ErrorCode EVENT_SPLIT_INVALID = new ErrorCode(1_012_100_017, "当前事件状态不允许拆分");
    ErrorCode SERVICE_RECOVERY_NOT_EXISTS = new ErrorCode(1_012_100_018, "服务恢复任务不存在");
    ErrorCode SERVICE_RECOVERY_ACTION_INVALID = new ErrorCode(1_012_100_019, "服务恢复任务状态或必填信息不正确");
    ErrorCode EVENT_NOTIFICATION_NOT_EXISTS = new ErrorCode(1_012_100_020, "事件外部消息不存在");
    ErrorCode EVENT_NOTIFICATION_PENDING = new ErrorCode(1_012_100_021, "仍有事件通知待发送或发送失败，暂不能关闭事件");

    // ========== 品质巡检 1-012-200 ==========
    ErrorCode INSPECTION_STANDARD_NOT_EXISTS = new ErrorCode(1_012_200_001, "巡检标准不存在");
    ErrorCode INSPECTION_STANDARD_PUBLISHED = new ErrorCode(1_012_200_002, "已发布的巡检标准版本不可修改");
    ErrorCode INSPECTION_POINT_NOT_EXISTS = new ErrorCode(1_012_200_003, "巡检点位不存在");
    ErrorCode INSPECTION_POINT_CODE_EXISTS = new ErrorCode(1_012_200_004, "当前项目的巡检点位编码已存在");
    ErrorCode INSPECTION_PLAN_NOT_EXISTS = new ErrorCode(1_012_200_005, "巡检计划不存在");
    ErrorCode INSPECTION_TASK_NOT_EXISTS = new ErrorCode(1_012_200_006, "巡检任务不存在");
    ErrorCode INSPECTION_TASK_STATE_INVALID = new ErrorCode(1_012_200_007, "巡检任务状态、版本或执行顺序不正确");
    ErrorCode INSPECTION_RESOURCE_INVALID = new ErrorCode(1_012_200_008, "巡检计划引用的标准或点位无效，必须属于当前项目且标准已发布");
    ErrorCode INSPECTION_ISSUE_NOT_EXISTS = new ErrorCode(1_012_200_009, "巡检异常不存在");
    ErrorCode INSPECTION_ISSUE_ACTION_INVALID = new ErrorCode(1_012_200_010, "整改或复查状态不正确，且整改人与复查人必须分离");
    ErrorCode INSPECTION_MAJOR_EVENT_CATEGORY_REQUIRED = new ErrorCode(1_012_200_011, "重大巡检标准必须配置有效的事件分类");
    ErrorCode INSPECTION_CONFLICT_NOT_EXISTS = new ErrorCode(1_012_200_012, "巡检弱网冲突不存在或已处理");
    ErrorCode INSPECTION_CONFLICT_RESOLUTION_INVALID = new ErrorCode(1_012_200_013, "冲突处理方式不正确");
    ErrorCode INSPECTION_QUALITY_SAMPLE_NOT_EXISTS = new ErrorCode(1_012_200_014, "品质抽检任务不存在");
    ErrorCode INSPECTION_QUALITY_ACTION_INVALID = new ErrorCode(1_012_200_015, "品质抽检状态不正确，抽检人不能是原执行人");

    // ========== 收费管理 1-012-001 ==========
    ErrorCode FEE_STANDARD_NOT_EXISTS = new ErrorCode(1_012_001_001, "费用标准不存在");
    ErrorCode FEE_RECORD_NOT_EXISTS = new ErrorCode(1_012_001_002, "费用记录不存在");
    ErrorCode FEE_ALREADY_PAID = new ErrorCode(1_012_001_003, "费用已缴清");
    ErrorCode FEE_ALREADY_PAID_OR_FREE = new ErrorCode(1_012_001_004, "费用已缴清或免缴");
    ErrorCode RELIEF_EXCEED_AMOUNT = new ErrorCode(1_012_001_005, "减免金额超过应收金额");
    ErrorCode RELIEF_FREQUENCY_LIMIT = new ErrorCode(1_012_001_006, "同一账单本月已减免过");
    ErrorCode READING_LESS_THAN_LAST = new ErrorCode(1_012_001_007, "本次读数不能低于上次读数");
    ErrorCode PROJECT_NOT_EXISTS = new ErrorCode(1_012_001_008, "物业项目不存在");

    // ========== 财务核算 1-012-002 ==========
    ErrorCode INCOME_NOT_EXISTS = new ErrorCode(1_012_002_001, "收入记录不存在");
    ErrorCode INCOME_ALREADY_CONFIRMED = new ErrorCode(1_012_002_002, "收入已确认");
    ErrorCode INCOME_NOT_CONFIRMABLE = new ErrorCode(1_012_002_003, "收入记录不可确认");
    ErrorCode INCOME_AMOUNT_INVALID = new ErrorCode(1_012_002_004, "收入金额必须大于0");
    ErrorCode EXPENSE_NOT_EXISTS = new ErrorCode(1_012_002_005, "支出记录不存在");
    ErrorCode EXPENSE_APPROVAL_NOT_PENDING = new ErrorCode(1_012_002_006, "支出审批状态不正确");

    // ========== 业主账户 1-012-003 ==========
    ErrorCode OWNER_ACCOUNT_NOT_EXISTS = new ErrorCode(1_012_003_001, "业主账户不存在");
    ErrorCode ACCOUNT_FROZEN = new ErrorCode(1_012_003_002, "账户已冻结");
    ErrorCode INSUFFICIENT_BALANCE = new ErrorCode(1_012_003_003, "账户余额不足");
    ErrorCode PAYMENT_DUPLICATE = new ErrorCode(1_012_003_004, "支付单重复提交");
    ErrorCode PREPAYMENT_AMOUNT_MIN = new ErrorCode(1_012_003_005, "充值金额不能低于100元");
    ErrorCode PREPAYMENT_AMOUNT_MAX = new ErrorCode(1_012_003_006, "单笔充值不能超过50000元");
    ErrorCode PREPAY_ACCOUNT_NOT_EXISTS = new ErrorCode(1_012_003_007, "预存款账户不存在");
    ErrorCode PREPAY_RECORD_NOT_EXISTS = new ErrorCode(1_012_003_008, "预存款流水不存在");

    // ========== 票据管理 1-012-004 ==========
    ErrorCode RECEIPT_NOT_EXISTS = new ErrorCode(1_012_004_001, "票据记录不存在");
    ErrorCode RECEIPT_ALREADY_PRINTED = new ErrorCode(1_012_004_002, "票据已打印");

    // ========== 报表统计 1-012-005 ==========
    ErrorCode REPORT_PARAM_INVALID = new ErrorCode(1_012_005_001, "报表查询参数无效");
    ErrorCode REPORT_DATE_RANGE_TOO_LARGE = new ErrorCode(1_012_005_002, "报表日期范围不能超过365天");
}
