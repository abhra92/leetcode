class Solution {
    public long findKthSmallest(int[] coins, int k) {
        int n = coins.length;
        int minCoin = Integer.MAX_VALUE;
        for (int c : coins) minCoin = Math.min(minCoin, c);

        long lo = 1, hi = (long) k * minCoin;

        while (lo < hi) {
            long mid = lo + (hi - lo) / 2;
            if (countUpTo(coins, mid) >= k) hi = mid;
            else lo = mid + 1;
        }
        return lo;
    }

    // count of numbers in [1, x] divisible by at least one coin (inclusion-exclusion)
    private long countUpTo(int[] coins, long x) {
        int n = coins.length;
        long count = 0;

        for (int mask = 1; mask < (1 << n); mask++) {
            long l = 1;
            boolean overflow = false;

            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    l = lcm(l, coins[i], x);
                    if (l > x) { overflow = true; break; }
                }
            }
            if (overflow) continue;

            int bits = Integer.bitCount(mask);
            if (bits % 2 == 1) count += x / l;
            else count -= x / l;
        }
        return count;
    }

    private long lcm(long a, long b, long cap) {
        long g = gcd(a, b);
        long factor = a / g;
        if (factor > cap / b) return cap + 1; // would overflow past cap, treat as "too big"
        return factor * b;
    }

    private long gcd(long a, long b) {
        while (b != 0) { long t = b; b = a % b; a = t; }
        return a;
    }
}