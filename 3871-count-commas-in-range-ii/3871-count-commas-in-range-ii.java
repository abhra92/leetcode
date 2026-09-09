class Solution {
    public long countCommas(long n) {
        long total = 0;
        long low = 1;   // 10^(d-1), starts at d = 1
        long high = 9;  // 10^d - 1

        for (int d = 1; d <= 16 && low <= n; d++) {
            long actualHigh = Math.min(high, n);
            long count = actualHigh - low + 1;
            long commasPerNumber = (d - 1) / 3;
            total += commasPerNumber * count;

            // move to next digit group, guarding overflow
            if (high > Long.MAX_VALUE / 10) break;
            low = high + 1;
            high = high * 10 + 9;
        }

        return total;
    }
}