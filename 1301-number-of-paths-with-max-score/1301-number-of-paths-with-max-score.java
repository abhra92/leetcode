class Solution {
    public int[] pathsWithMaxScore(List<String> board) {
        int n = board.size();
        int MOD = 1_000_000_007;
        long[][] dp = new long[n][n];
        long[][] cnt = new long[n][n];
        for (long[] row : dp) Arrays.fill(row, -1);

        dp[n-1][n-1] = 0;
        cnt[n-1][n-1] = 1;

        for (int i = n-1; i >= 0; i--) {
            for (int j = n-1; j >= 0; j--) {
                char c = board.get(i).charAt(j);
                if (c == 'X' || (i==n-1 && j==n-1)) continue;
                long best = -1, ways = 0;
                int[][] dirs = {{1,0},{0,1},{1,1}};
                for (int[] d : dirs) {
                    int ni = i+d[0], nj = j+d[1];
                    if (ni < n && nj < n && dp[ni][nj] != -1) {
                        if (dp[ni][nj] > best) {
                            best = dp[ni][nj];
                            ways = cnt[ni][nj];
                        } else if (dp[ni][nj] == best) {
                            ways = (ways + cnt[ni][nj]) % MOD;
                        }
                    }
                }
                if (best == -1) continue;
                int val = (c=='E'||c=='S') ? 0 : c - '0';
                dp[i][j] = best + val;
                cnt[i][j] = ways;
            }
        }

        if (dp[0][0] == -1) return new int[]{0,0};
        return new int[]{(int) dp[0][0], (int) cnt[0][0]};
    }
}