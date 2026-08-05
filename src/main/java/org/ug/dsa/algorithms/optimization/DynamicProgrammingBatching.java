package org.ug.dsa.algorithms.optimization;
import org.ug.dsa.models.Resource;
import org.ug.dsa.models.ServiceRequest;

/**
 * 0/1 Knapsack Dynamic Programming solver for delivery order batching.
 */

public class DynamicProgrammingBatching {

    /** Index-number-derived demo parameter (3+ index-based parameters). */
    public static final long OWNER_INDEX_NUMBER = 22_380_272L;

    /** Digit-sum of the index number, used to derive a demo capacity/weight scale. */
    public static final int INDEX_DIGIT_SUM = digitSum(OWNER_INDEX_NUMBER);

    /**
     * Pluggable weight function so callers can supply real order weights once
     * the data model has one, without changing the DP engine itself.
     */
    @FunctionalInterface
    public interface WeightFunction {
        int weightOf(ServiceRequest request);
    }

    /** Result bundle: selected orders, totals, and the raw DP table for tracing. */
    public static final class BatchingResult {
        public final ServiceRequest[] selectedRequests;
        public final int selectedCount;
        public final int totalValue;
        public final int totalWeightUsed;
        public final int[][] dpTable;
        public final int[] weights;
        public final int[] values;
        public final int capacity;

        BatchingResult(ServiceRequest[] selectedRequests, int selectedCount, int totalValue,
                        int totalWeightUsed, int[][] dpTable, int[] weights, int[] values, int capacity) {
            this.selectedRequests = selectedRequests;
            this.selectedCount = selectedCount;
            this.totalValue = totalValue;
            this.totalWeightUsed = totalWeightUsed;
            this.dpTable = dpTable;
            this.weights = weights;
            this.values = values;
            this.capacity = capacity;
        }
    }

    /**
     * Placeholder weight rule: "family/party" style orders occupy 2 capacity
     * units, everything else occupies 1. Replace once ServiceRequest gains a
     * real load-size field.
     */
    public static int defaultWeight(ServiceRequest request) {
        String category = request.category() == null ? "" : request.category().toLowerCase();
        if (category.contains("family") || category.contains("party")) {
            return 2;
        }
        return 1;
    }

    /** Value function: urgency (1-5) directly, higher urgency = higher priority to batch. */
    public static int defaultValue(ServiceRequest request) {
        return request.urgency();
    }

    /** Convenience overload: batch against a Resource's capacity using default weight/value rules. */
    public BatchingResult solve(ServiceRequest[] requests, Resource resource) {
        return solve(requests, resource.capacity(), DynamicProgrammingBatching::defaultWeight,
                DynamicProgrammingBatching::defaultValue);
    }

    /** Full control overload: explicit capacity and pluggable weight/value functions. */
    public BatchingResult solve(ServiceRequest[] requests, int capacity,WeightFunction weightFn, java.util.function.ToIntFunction<ServiceRequest> valueFn) {
        int n = requests.length;
        int[] weight = new int[n];
        int[] value = new int[n];
        for (int i = 0; i < n; i++) {
            weight[i] = weightFn.weightOf(requests[i]);
            value[i] = valueFn.applyAsInt(requests[i]);
        }

        // dp[i][w] = best total value achievable using the first i requests
        //            with total weight budget w.
        int[][] dp = new int[n + 1][capacity + 1];

        for (int i = 1; i <= n; i++) {
            int wt = weight[i - 1];
            int val = value[i - 1];
            for (int w = 0; w <= capacity; w++) {
                if (wt <= w) {
                    int includeValue = val + dp[i - 1][w - wt];
                    int excludeValue = dp[i - 1][w];
                    dp[i][w] = Math.max(includeValue, excludeValue);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        // Backtrack from dp[n][capacity] to recover which requests were selected.
        ServiceRequest[] selected = new ServiceRequest[n];
        int selectedCount = 0;
        int w = capacity;
        for (int i = n; i >= 1 && w >= 0; i--) {
            if (dp[i][w] != dp[i - 1][w]) {
                selected[selectedCount++] = requests[i - 1];
                w -= weight[i - 1];
            }
        }

        // Reverse in place so selection reads in original request order.
        for (int left = 0, right = selectedCount - 1; left < right; left++, right--) {
            ServiceRequest temp = selected[left];
            selected[left] = selected[right];
            selected[right] = temp;
        }

        int totalWeightUsed = 0;
        for (int i = 0; i < selectedCount; i++) {
            totalWeightUsed += weightFn.weightOf(selected[i]);
        }

        return new BatchingResult(selected, selectedCount, dp[n][capacity], totalWeightUsed,
                dp, weight, value, capacity);
    }

    /**
     * Renders the dp[][] table as a plain-text grid — use this output directly
     * as the source of truth for DP Tabulation Trace #6 so the written trace
     * always matches what the code actually computes.
     */
    public static String renderTable(BatchingResult result) {
        StringBuilder sb = new StringBuilder();
        sb.append("w ->      ");
        for (int w = 0; w <= result.capacity; w++) {
            sb.append(String.format("%4d", w));
        }
        sb.append(System.lineSeparator());

        for (int i = 0; i < result.dpTable.length; i++) {
            sb.append(String.format("i=%-6d", i));
            for (int w = 0; w <= result.capacity; w++) {
                sb.append(String.format("%4d", result.dpTable[i][w]));
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    private static int digitSum(long n) {
        int sum = 0;
        while (n > 0) {
            sum += (int) (n % 10);
            n /= 10;
        }
        return sum;
    }

    /**
     * Small self-contained demo so this class can be run/verified independently
     * before Main.java's console menu wires it in. Not a substitute for the
     * team's shared demo data (data/service_requests.csv, data/resources.csv).
     */
    
}
