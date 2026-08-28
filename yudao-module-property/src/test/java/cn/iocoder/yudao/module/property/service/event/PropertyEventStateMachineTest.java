package cn.iocoder.yudao.module.property.service.event;

import cn.iocoder.yudao.module.property.enums.event.PropertyEventActionEnum;
import cn.iocoder.yudao.module.property.enums.event.PropertyEventStatusEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PropertyEventStateMachineTest {

    @Test
    void shouldCoverMainEventLifecycle() {
        assertTransition(PropertyEventStatusEnum.PENDING_CONFIRM, PropertyEventActionEnum.CONFIRM,
                PropertyEventStatusEnum.PENDING_DISPATCH);
        assertTransition(PropertyEventStatusEnum.PENDING_DISPATCH, PropertyEventActionEnum.DISPATCH,
                PropertyEventStatusEnum.PENDING_ACCEPT);
        assertTransition(PropertyEventStatusEnum.PENDING_ACCEPT, PropertyEventActionEnum.ACCEPT,
                PropertyEventStatusEnum.ACCEPTED);
        assertTransition(PropertyEventStatusEnum.ACCEPTED, PropertyEventActionEnum.ARRIVE,
                PropertyEventStatusEnum.ARRIVED);
        assertTransition(PropertyEventStatusEnum.ARRIVED, PropertyEventActionEnum.START,
                PropertyEventStatusEnum.PROCESSING);
        assertTransition(PropertyEventStatusEnum.PROCESSING, PropertyEventActionEnum.SUBMIT_ACCEPTANCE,
                PropertyEventStatusEnum.PENDING_ACCEPTANCE);
        assertTransition(PropertyEventStatusEnum.PENDING_ACCEPTANCE, PropertyEventActionEnum.APPROVE,
                PropertyEventStatusEnum.CLOSED);
        assertTransition(PropertyEventStatusEnum.CLOSED, PropertyEventActionEnum.REOPEN,
                PropertyEventStatusEnum.REOPENED);
        assertTransition(PropertyEventStatusEnum.REOPENED, PropertyEventActionEnum.DISPATCH,
                PropertyEventStatusEnum.PENDING_ACCEPT);
    }

    @Test
    void shouldRejectSkippingRequiredLifecycleNodes() {
        assertFalse(PropertyEventStateMachine.canTransition(
                PropertyEventStatusEnum.PENDING_CONFIRM.getStatus(), PropertyEventActionEnum.APPROVE));
        assertFalse(PropertyEventStateMachine.canTransition(
                PropertyEventStatusEnum.PENDING_ACCEPT.getStatus(), PropertyEventActionEnum.SUBMIT_ACCEPTANCE));
        assertThrows(IllegalArgumentException.class, () -> PropertyEventStateMachine.nextStatus(
                PropertyEventStatusEnum.CLOSED.getStatus(), PropertyEventActionEnum.CANCEL));
    }

    @Test
    void shouldRequireCollaborationToResumeThroughProcessing() {
        assertTransition(PropertyEventStatusEnum.PROCESSING, PropertyEventActionEnum.COLLABORATE,
                PropertyEventStatusEnum.WAITING_COLLABORATION);
        assertTransition(PropertyEventStatusEnum.WAITING_COLLABORATION, PropertyEventActionEnum.RESUME,
                PropertyEventStatusEnum.PROCESSING);
        assertTrue(PropertyEventStateMachine.canTransition(
                PropertyEventStatusEnum.PENDING_ACCEPTANCE.getStatus(), PropertyEventActionEnum.REJECT));
    }

    @Test
    void shouldReturnRejectedOrderToDispatchPoolAndAllowControlledTransfer() {
        assertTransition(PropertyEventStatusEnum.PENDING_ACCEPT, PropertyEventActionEnum.REJECT_ORDER,
                PropertyEventStatusEnum.PENDING_DISPATCH);
        assertTransition(PropertyEventStatusEnum.PENDING_ACCEPT, PropertyEventActionEnum.TRANSFER,
                PropertyEventStatusEnum.PENDING_ACCEPT);
        assertTransition(PropertyEventStatusEnum.ACCEPTED, PropertyEventActionEnum.TRANSFER,
                PropertyEventStatusEnum.PENDING_ACCEPT);
        assertTransition(PropertyEventStatusEnum.ARRIVED, PropertyEventActionEnum.TRANSFER,
                PropertyEventStatusEnum.PENDING_ACCEPT);
        assertTransition(PropertyEventStatusEnum.PROCESSING, PropertyEventActionEnum.TRANSFER,
                PropertyEventStatusEnum.PENDING_ACCEPT);
        assertTransition(PropertyEventStatusEnum.WAITING_COLLABORATION, PropertyEventActionEnum.TRANSFER,
                PropertyEventStatusEnum.PENDING_ACCEPT);
    }

    private void assertTransition(PropertyEventStatusEnum from, PropertyEventActionEnum action,
            PropertyEventStatusEnum to) {
        assertTrue(PropertyEventStateMachine.canTransition(from.getStatus(), action));
        assertEquals(to.getStatus(), PropertyEventStateMachine.nextStatus(from.getStatus(), action));
    }
}
