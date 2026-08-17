package org.ug.dsa.util;

/**
 * Utility class to derive algorithm parameters from team member index numbers.
 *
 * Conforms to Section 2.iii of the DCIT 204/308 project specification:
 * "Each team must derive at least three algorithm parameters from member index numbers,
 * for example priority weight, route penalty, hash-table size, random seed or budget constraint."
 */
public final class IndexParameters {

    // Team Index Numbers
    public static final long GROUP_LEADER_INDEX = 22_052_950L; // Virtus Dakura
    public static final long JESSICA_INDEX = 22_120_404L;      // Jessica Zunuo Puozaa
    public static final long ALFRED_INDEX = 22_380_272L;       // Alfred Forson
    public static final long TIMOTHY_INDEX = 22_397_906L;      // Timothy Eli Abotsikpuia

    private IndexParameters() {
        // Prevent instantiation
    }

    /**
     * Parameter 1: Derived Hash Table Base Size.
     * Formula: 16 + (Sum of digits of Group Leader Index) % 17
     * (2+2+0+5+2+9+5+0 = 25; 25 % 17 = 8; 16 + 8 = 24)
     */
    public static int getDerivedHashTableBaseSize() {
        int sum = sumOfDigits(GROUP_LEADER_INDEX);
        return 16 + (sum % 17);
    }

    /**
     * Parameter 2: Derived Priority Urgency Multiplier.
     * Formula: 1.0 + (Alfred Index % 7) * 0.15
     * (22380272 % 7 = 3; 1.0 + 3 * 0.15 = 1.45)
     */
    public static double getDerivedPriorityMultiplier() {
        long rem = ALFRED_INDEX % 7;
        return 1.0 + (rem * 0.15);
    }

    /**
     * Parameter 3: Derived Seed / B-Tree Minimum Degree (t).
     * Formula: 3 + (Jessica Index % 3)
     * (22120404 % 3 = 0; t = 3 + 0 = 3)
     */
    public static int getDerivedBTreeMinDegree() {
        return 3 + (int) (JESSICA_INDEX % 3);
    }

    /**
     * Parameter 4: Derived Traffic Penalty Factor.
     * Formula: 1.0 + (Timothy Index % 5) * 0.2
     */
    public static double getDerivedTrafficPenaltyFactor() {
        long rem = TIMOTHY_INDEX % 5;
        return 1.0 + (rem * 0.2);
    }

    /**
     * Helper to compute the sum of digits of a given number.
     */
    public static int sumOfDigits(long number) {
        int sum = 0;
        long n = Math.abs(number);
        while (n > 0) {
            sum += (int) (n % 10);
            n /= 10;
        }
        return sum;
    }
}
