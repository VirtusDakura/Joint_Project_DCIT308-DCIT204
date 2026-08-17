package org.ug.dsa.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("IndexParameters Unit Tests")
class IndexParametersTest {

    @Test
    @DisplayName("Verify parameter formulas derived from student index numbers")
    void testDerivedParameters() {
        assertTrue(IndexParameters.getDerivedHashTableBaseSize() >= 16);
        assertTrue(IndexParameters.getDerivedPriorityMultiplier() >= 1.0);
        assertEquals(3, IndexParameters.getDerivedBTreeMinDegree());
        assertTrue(IndexParameters.getDerivedTrafficPenaltyFactor() >= 1.0);
    }

    @Test
    @DisplayName("Sum of digits helper function")
    void testSumOfDigits() {
        assertEquals(10, IndexParameters.sumOfDigits(1234));
        assertEquals(0, IndexParameters.sumOfDigits(0));
        assertEquals(25, IndexParameters.sumOfDigits(22052950L));
    }
}
