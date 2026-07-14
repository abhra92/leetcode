class Solution {
    public int subsequencePairCount(int[] nums) {
        int MOD = 1_000_000_007;
        int V = 200;
        long[][] dp = new long[V + 1][V + 1];
        dp[0][0] = 1;

        for (int num : nums) {
            long[][] ndp = new long[V + 1][V + 1];
            for (int g1 = 0; g1 <= V; g1++) {
                for (int g2 = 0; g2 <= V; g2++) {
                    long c = dp[g1][g2];
                    if (c == 0) continue;

                    // skip num
                    ndp[g1][g2] = (ndp[g1][g2] + c) % MOD;

                    // add num to seq1
                    int ng1 = (g1 == 0) ? num : gcd(g1, num);
                    ndp[ng1][g2] = (ndp[ng1][g2] + c) % MOD;

                    // add num to seq2
                    int ng2 = (g2 == 0) ? num : gcd(g2, num);
                    ndp[g1][ng2] = (ndp[g1][ng2] + c) % MOD;
                }
            }
            dp = ndp;
        }

        long ans = 0;
        for (int g = 1; g <= V; g++) {
            ans = (ans + dp[g][g]) % MOD;
        }
        return (int) ans;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int t = b;
            b = a % b;
            a = t;
        }
        return a;
    }
}