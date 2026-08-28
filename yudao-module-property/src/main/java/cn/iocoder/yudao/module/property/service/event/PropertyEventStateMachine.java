package cn.iocoder.yudao.module.property.service.event;

import cn.iocoder.yudao.module.property.enums.event.PropertyEventActionEnum;
import cn.iocoder.yudao.module.property.enums.event.PropertyEventStatusEnum;

import java.util.EnumMap;
import java.util.Map;

public final class PropertyEventStateMachine {

    private static final Map<PropertyEventActionEnum, Map<Integer, Integer>> TRANSITIONS = buildTransitions();

    private PropertyEventStateMachine() {
    }

    public static boolean canTransition(Integer currentStatus, PropertyEventActionEnum action) {
        return currentStatus != null && TRANSITIONS.getOrDefault(action, Map.of()).containsKey(currentStatus);
    }

    public static Integer nextStatus(Integer currentStatus, PropertyEventActionEnum action) {
        Integer nextStatus = TRANSITIONS.getOrDefault(action, Map.of()).get(currentStatus);
        if (nextStatus == null) {
            throw new IllegalArgumentException("Invalid property event transition: " + currentStatus + " -> " + action);
        }
        return nextStatus;
    }

    private static Map<PropertyEventActionEnum, Map<Integer, Integer>> buildTransitions() {
        Map<PropertyEventActionEnum, Map<Integer, Integer>> transitions =
                new EnumMap<>(PropertyEventActionEnum.class);
        add(transitions, PropertyEventActionEnum.CONFIRM,
                PropertyEventStatusEnum.PENDING_CONFIRM, PropertyEventStatusEnum.PENDING_DISPATCH);
        add(transitions, PropertyEventActionEnum.CANCEL,
                PropertyEventStatusEnum.PENDING_CONFIRM, PropertyEventStatusEnum.CANCELLED);
        add(transitions, PropertyEventActionEnum.DISPATCH,
                PropertyEventStatusEnum.PENDING_DISPATCH, PropertyEventStatusEnum.PENDING_ACCEPT);
        add(transitions, PropertyEventActionEnum.DISPATCH,
                PropertyEventStatusEnum.REOPENED, PropertyEventStatusEnum.PENDING_ACCEPT);
        add(transitions, PropertyEventActionEnum.ACCEPT,
                PropertyEventStatusEnum.PENDING_ACCEPT, PropertyEventStatusEnum.ACCEPTED);
        add(transitions, PropertyEventActionEnum.REJECT_ORDER,
                PropertyEventStatusEnum.PENDING_ACCEPT, PropertyEventStatusEnum.PENDING_DISPATCH);
        add(transitions, PropertyEventActionEnum.TRANSFER,
                PropertyEventStatusEnum.PENDING_ACCEPT, PropertyEventStatusEnum.PENDING_ACCEPT);
        add(transitions, PropertyEventActionEnum.TRANSFER,
                PropertyEventStatusEnum.ACCEPTED, PropertyEventStatusEnum.PENDING_ACCEPT);
        add(transitions, PropertyEventActionEnum.TRANSFER,
                PropertyEventStatusEnum.ARRIVED, PropertyEventStatusEnum.PENDING_ACCEPT);
        add(transitions, PropertyEventActionEnum.TRANSFER,
                PropertyEventStatusEnum.PROCESSING, PropertyEventStatusEnum.PENDING_ACCEPT);
        add(transitions, PropertyEventActionEnum.TRANSFER,
                PropertyEventStatusEnum.WAITING_COLLABORATION, PropertyEventStatusEnum.PENDING_ACCEPT);
        add(transitions, PropertyEventActionEnum.ARRIVE,
                PropertyEventStatusEnum.ACCEPTED, PropertyEventStatusEnum.ARRIVED);
        add(transitions, PropertyEventActionEnum.START,
                PropertyEventStatusEnum.ACCEPTED, PropertyEventStatusEnum.PROCESSING);
        add(transitions, PropertyEventActionEnum.START,
                PropertyEventStatusEnum.ARRIVED, PropertyEventStatusEnum.PROCESSING);
        add(transitions, PropertyEventActionEnum.COLLABORATE,
                PropertyEventStatusEnum.PROCESSING, PropertyEventStatusEnum.WAITING_COLLABORATION);
        add(transitions, PropertyEventActionEnum.RESUME,
                PropertyEventStatusEnum.WAITING_COLLABORATION, PropertyEventStatusEnum.PROCESSING);
        add(transitions, PropertyEventActionEnum.SUBMIT_ACCEPTANCE,
                PropertyEventStatusEnum.PROCESSING, PropertyEventStatusEnum.PENDING_ACCEPTANCE);
        add(transitions, PropertyEventActionEnum.APPROVE,
                PropertyEventStatusEnum.PENDING_ACCEPTANCE, PropertyEventStatusEnum.CLOSED);
        add(transitions, PropertyEventActionEnum.REJECT,
                PropertyEventStatusEnum.PENDING_ACCEPTANCE, PropertyEventStatusEnum.PROCESSING);
        add(transitions, PropertyEventActionEnum.REOPEN,
                PropertyEventStatusEnum.CLOSED, PropertyEventStatusEnum.REOPENED);
        return transitions;
    }

    private static void add(Map<PropertyEventActionEnum, Map<Integer, Integer>> transitions,
            PropertyEventActionEnum action, PropertyEventStatusEnum from, PropertyEventStatusEnum to) {
        transitions.computeIfAbsent(action, key -> new java.util.HashMap<>()).put(from.getStatus(), to.getStatus());
    }
}
