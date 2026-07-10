class Solution {
    public int[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {
        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++) order[i] = i;
        Arrays.sort(order, (a, b) -> nums[a] - nums[b]);

        int[] sortedVal = new int[n];
        int[] pos = new int[n]; // origIdx -> sorted position
        for (int i = 0; i < n; i++) {
            sortedVal[i] = nums[order[i]];
            pos[order[i]] = i;
        }

        int[] comp = new int[n];
        comp[0] = 0;
        for (int i = 1; i < n; i++) {
            comp[i] = (sortedVal[i] - sortedVal[i - 1] <= maxDiff) ? comp[i - 1] : comp[i - 1] + 1;
        }

        int[] R = new int[n];
        int j = 0;
        for (int i = 0; i < n; i++) {
            if (j < i) j = i;
            while (j + 1 < n && sortedVal[j + 1] - sortedVal[i] <= maxDiff) j++;
            R[i] = j;
        }

        int LOG = 1;
        while ((1 << LOG) < n) LOG++;
        LOG++; // safety margin

        int[][] jump = new int[LOG][n];
        jump[0] = R;
        for (int k = 1; k < LOG; k++) {
            for (int i = 0; i < n; i++) {
                jump[k][i] = jump[k - 1][jump[k - 1][i]];
            }
        }

        int m = queries.length;
        int[] ans = new int[m];
        for (int qi = 0; qi < m; qi++) {
            int u = queries[qi][0], v = queries[qi][1];
            if (u == v) { ans[qi] = 0; continue; }
            int pu = pos[u], pv = pos[v];
            if (comp[pu] != comp[pv]) { ans[qi] = -1; continue; }
            int L = Math.min(pu, pv), Rr = Math.max(pu, pv);
            if (R[L] >= Rr) { ans[qi] = 1; continue; }
            int cur = L, steps = 0;
            for (int k = LOG - 1; k >= 0; k--) {
                if (jump[k][cur] < Rr) {
                    cur = jump[k][cur];
                    steps += (1 << k);
                }
            }
            ans[qi] = steps + 1;
        }
        return ans;
    }
}