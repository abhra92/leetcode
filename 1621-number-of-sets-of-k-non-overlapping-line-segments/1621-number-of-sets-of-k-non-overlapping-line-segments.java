class Solution {
    public int numberOfSets(int n, int k) {
        final int MOD = 1_000_000_007;
        long[][][] dp = new long[n][k + 1][2];
        dp[0][0][0] = 1;

        for (int p = 1; p < n; p++) {
            for (int j = 0; j <= k; j++) {
                dp[p][j][0] = (dp[p - 1][j][0] + dp[p - 1][j][1]) % MOD;
                if (j >= 1) {
                    dp[p][j][1] = (dp[p - 1][j][1]
                                 + dp[p - 1][j - 1][0]
                                 + dp[p - 1][j - 1][1]) % MOD;
                }
            }
        }

        return (int) ((dp[n - 1][k][0] + dp[n - 1][k][1]) % MOD);
    }
}