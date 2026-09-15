class Solution {
    public int maxPalindromes(String s, int k) {
        int n = s.length();
        if (n < k) return 0;

        boolean[][] isPal = new boolean[n][n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                if (s.charAt(i) == s.charAt(j) && (j - i < 2 || isPal[i + 1][j - 1])) {
                    isPal[i][j] = true;
                }
            }
        }

        int[] dp = new int[n + 1];
        for (int i = n - 1; i >= 0; i--) {
            dp[i] = dp[i + 1]; // skip s[i]

            if (i + k <= n && isPal[i][i + k - 1]) {
                dp[i] = Math.max(dp[i], 1 + dp[i + k]);
            }
            if (i + k + 1 <= n && isPal[i][i + k]) {
                dp[i] = Math.max(dp[i], 1 + dp[i + k + 1]);
            }
        }

        return dp[0];
    }
}