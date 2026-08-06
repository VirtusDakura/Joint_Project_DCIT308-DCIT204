package org.ug.dsa.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ug.dsa.models.ServiceRequest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SchedulingService Unit Tests")
class SchedulingServiceTest {

    private SchedulingService scheduler;
    private ServiceRequest req1;
    private ServiceRequest req2;
    private ServiceRequest reqUrgent;

    @BeforeEach
    void setUp() {
        scheduler = new SchedulingService();
        LocalDateTime now = LocalDateTime.now();

        req1 = new ServiceRequest("REQ-1", "Standard Order", "Papaye", "Legon Hall", 2, now, now.plusMinutes(45), "NEW");
        req2 = new ServiceRequest("REQ-2", "Medium Order", "Bush Canteen", "Akuafo Hall", 4, now, now.plusMinutes(30), "NEW");
        reqUrgent = new ServiceRequest("REQ-URGENT", "Emergency VIP Order", "Gobə Joint", "Night Market", 5, now, now.plusMinutes(15), "NEW");
    }

    @Test
    @DisplayName("Should submit orders and report pending count")
    void testSubmitOrder() {
        assertTrue(scheduler.isEmpty());
        scheduler.submitOrder(req1);
        scheduler.submitOrder(req2);

        assertEquals(2, scheduler.getPendingCount());
        assertFalse(scheduler.isEmpty());
    }

    @Test
    @DisplayName("Should dispatch orders in FIFO sequence")
    void testDispatchFIFO() {
        scheduler.submitOrder(req1);
        scheduler.submitOrder(req2);

        assertEquals("REQ-1", scheduler.dispatchFIFO().requestId());
        assertEquals("REQ-2", scheduler.dispatchFIFO().requestId());
        assertNull(scheduler.dispatchFIFO());
    }

    @Test
    @DisplayName("Should dispatch highest priority order first")
    void testDispatchPriority() {
        scheduler.submitOrder(req1); // urgency 2
        scheduler.submitOrder(req2); // urgency 4
        scheduler.submitOrder(reqUrgent); // urgency 5

        assertEquals("REQ-URGENT", scheduler.dispatchPriority().requestId());
        assertEquals("REQ-2", scheduler.dispatchPriority().requestId());
        assertEquals("REQ-1", scheduler.dispatchPriority().requestId());
    }

    @Test
    @DisplayName("Should insert urgent orders at front of dispatch queue")
    void testInsertUrgentOrder() {
        scheduler.submitOrder(req1);
        scheduler.submitOrder(req2);

        scheduler.insertUrgentOrder(reqUrgent);

        assertEquals("REQ-URGENT", scheduler.dispatchFIFO().requestId());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when submitting null order")
    void testNullOrderValidation() {
        assertThrows(IllegalArgumentException.class, () -> scheduler.submitOrder(null));
        assertThrows(IllegalArgumentException.class, () -> scheduler.insertUrgentOrder(null));
    }
}
