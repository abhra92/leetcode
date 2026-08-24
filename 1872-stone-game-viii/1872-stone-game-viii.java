class Solution {
    public int stoneGameVIII(int[] stones) {
        int n = stones.length;
        
        // Step 1: build prefix sums
        int[] prefix = new int[n];
        prefix[0] = stones[0];
        for (int i = 1; i < n; i++) {
            prefix[i] = prefix[i - 1] + stones[i];
        }
        
        // Step 2: dp starts at the last index (forced final move)
        int dp = prefix[n - 1];
        
        // Step 3: walk backwards, deciding "take this cut" vs "defer"
        for (int i = n - 2; i >= 1; i--) {
            dp = Math.max(dp, prefix[i] - dp);
        }
        
        // Step 4: dp[1] is the answer
        return dp;
    }
}