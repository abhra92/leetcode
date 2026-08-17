class Solution {
    public int stoneGameV(int[] stoneValue) {
        int n = stoneValue.length;
        int[] prefix = new int[n + 1];
        for (int i = 0; i < n; i++) prefix[i + 1] = prefix[i] + stoneValue[i];

        int[][] dp = new int[n][n];
        for (int[] row : dp) java.util.Arrays.fill(row, -1);

        return solve(0, n - 1, prefix, dp);
    }

    private int solve(int i, int j, int[] prefix, int[][] dp) {
        if (i == j) return 0;
        if (dp[i][j] != -1) return dp[i][j];

        int best = 0;
        for (int k = i; k < j; k++) {
            int left = prefix[k + 1] - prefix[i];
            int right = prefix[j + 1] - prefix[k + 1];

            int candidate;
            if (left < right) {
                candidate = left + solve(i, k, prefix, dp);
            } else if (left > right) {
                candidate = right + solve(k + 1, j, prefix, dp);
            } else {
                candidate = left + Math.max(solve(i, k, prefix, dp), solve(k + 1, j, prefix, dp));
            }
            best = Math.max(best, candidate);
        }

        dp[i][j] = best;
        return best;
    }
}