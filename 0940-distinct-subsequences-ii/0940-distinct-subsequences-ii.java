class Solution {
    public int distinctSubseqII(String s) {
        int MOD = 1_000_000_007;
        int[] endsWith = new int[26];
        int total = 0;

        for (int i = 0; i < s.length(); i++) {
            int idx = s.charAt(i) - 'a';
            
            // New distinct subsequences ending with current char:
            // 1 (the single character itself) + total (appending to all current subsequences)
            int currentNew = (total + 1) % MOD;
            
            // Difference to add to total: currentNew - endsWith[idx]
            int diff = (currentNew - endsWith[idx] + MOD) % MOD;
            
            // Update total and endsWith
            total = (total + diff) % MOD;
            endsWith[idx] = currentNew;
        }

        return total;
    }
}