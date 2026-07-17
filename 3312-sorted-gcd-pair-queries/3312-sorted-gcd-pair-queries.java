class Solution {
    public int[] gcdValues(int[] nums, long[] queries) {
        int maxV = 0;
        for (int x : nums) maxV = Math.max(maxV, x);

        int[] freq = new int[maxV + 1];
        for (int x : nums) freq[x]++;

        long[] cntMultiple = new long[maxV + 1];
        for (int d = 1; d <= maxV; d++) {
            long c = 0;
            for (int m = d; m <= maxV; m += d) c += freq[m];
            cntMultiple[d] = c;
        }

        long[] exact = new long[maxV + 1];
        for (int d = maxV; d >= 1; d--) {
            long c = cntMultiple[d];
            long atLeast = c * (c - 1) / 2;
            long sub = 0;
            for (int m = 2 * d; m <= maxV; m += d) sub += exact[m];
            exact[d] = atLeast - sub;
        }

        long[] prefix = new long[maxV + 1]; // prefix[d] = count of pairs with gcd <= d
        long run = 0;
        for (int d = 1; d <= maxV; d++) {
            run += exact[d];
            prefix[d] = run;
        }

        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            long target = queries[i] + 1; // 1-indexed rank
            int lo = 1, hi = maxV, res = maxV;
            while (lo <= hi) {
                int mid = (lo + hi) / 2;
                if (prefix[mid] >= target) {
                    res = mid;
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            }
            ans[i] = res;
        }
        return ans;
    }
}