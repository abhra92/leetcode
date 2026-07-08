class Solution {
    static final int MOD = 1_000_000_007;
    int[] value; // segment tree: concatenated value mod MOD
    int[] cnt;   // segment tree: count of non-zero digits in segment
    long[] pow10;
    int n;

    public int[] sumAndMultiply(String s, int[][] queries) {
        n = s.length();
        pow10 = new long[n + 1];
        pow10[0] = 1;
        for (int i = 1; i <= n; i++) pow10[i] = pow10[i - 1] * 10 % MOD;

        int[] prefSum = new int[n + 1];
        int[] digits = new int[n];
        for (int i = 0; i < n; i++) {
            digits[i] = s.charAt(i) - '0';
            prefSum[i + 1] = prefSum[i] + digits[i];
        }

        value = new int[4 * n];
        cnt = new int[4 * n];
        if (n > 0) build(1, 0, n - 1, digits);

        int q = queries.length;
        int[] ans = new int[q];
        for (int i = 0; i < q; i++) {
            int l = queries[i][0], r = queries[i][1];
            long[] res = query(1, 0, n - 1, l, r); // {val mod, count}
            long xmod = res[0] % MOD;
            long sum = prefSum[r + 1] - prefSum[l];
            ans[i] = (int) ((xmod * (sum % MOD)) % MOD);
        }
        return ans;
    }

    private void build(int node, int lo, int hi, int[] digits) {
        if (lo == hi) {
            if (digits[lo] == 0) {
                value[node] = 0;
                cnt[node] = 0;
            } else {
                value[node] = digits[lo];
                cnt[node] = 1;
            }
            return;
        }
        int mid = (lo + hi) >>> 1;
        build(2 * node, lo, mid, digits);
        build(2 * node + 1, mid + 1, hi, digits);
        merge(node);
    }

    private void merge(int node) {
        int lCnt = cnt[2 * node];
        int rCnt = cnt[2 * node + 1];
        long lVal = value[2 * node];
        long rVal = value[2 * node + 1];
        value[node] = (int) ((lVal * pow10[rCnt] + rVal) % MOD);
        cnt[node] = lCnt + rCnt;
    }

    // returns {value mod, count} for overlap of [lo,hi] with [l,r]
    private long[] query(int node, int lo, int hi, int l, int r) {
        if (r < lo || hi < l) return new long[]{0, 0};
        if (l <= lo && hi <= r) return new long[]{value[node], cnt[node]};
        int mid = (lo + hi) >>> 1;
        long[] left = query(2 * node, lo, mid, l, r);
        long[] right = query(2 * node + 1, mid + 1, hi, l, r);
        long combVal = (left[0] * pow10[(int) right[1]] + right[0]) % MOD;
        long combCnt = left[1] + right[1];
        return new long[]{combVal, combCnt};
    }
}