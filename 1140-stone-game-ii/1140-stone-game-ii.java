class Solution {
    public int stoneGameII(int[] piles) {
        int n = piles.length;
        
        // Precompute suffix sums for O(1) range queries
        int[] suffix = new int[n + 1];
        for (int i = n - 1; i >= 0; i--) {
            suffix[i] = suffix[i + 1] + piles[i];
        }
        
        // memo[i][m] = max stones current player can get from index i with M=m
        Integer[][] memo = new Integer[n][n + 1];
        
        return dfs(0, 1, piles, suffix, memo);
    }
    
    private int dfs(int idx, int m, int[] piles, int[] suffix, Integer[][] memo) {
        int n = piles.length;
        
        // Base case: no more piles
        if (idx == n) return 0;
        
        // Check memoization
        if (memo[idx][m] != null) return memo[idx][m];
        
        int maxStones = 0;
        int totalRemaining = suffix[idx]; // Total stones from current index to end
        
        // Try all valid moves: take X piles where 1 <= X <= min(2m, remaining_piles)
        int moveLimit = Math.min(2 * m, n - idx);
        for (int x = 1; x <= moveLimit; x++) {
            int newM = Math.max(m, x);
            // Stones we get = total - what opponent optimally gets
            int stonesThisTurn = totalRemaining - dfs(idx + x, newM, piles, suffix, memo);
            maxStones = Math.max(maxStones, stonesThisTurn);
        }
        
        return memo[idx][m] = maxStones;
    }
}