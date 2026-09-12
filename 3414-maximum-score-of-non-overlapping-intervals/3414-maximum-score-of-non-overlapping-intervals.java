import java.util.*;

class Solution {
    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();

        // Indices sorted by right endpoint (stable order for ties is fine)
        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++) order[i] = i;
        Arrays.sort(order, (a, b) -> intervals.get(a).get(1) - intervals.get(b).get(1));

        long[] L = new long[n];
        long[] R = new long[n];
        long[] W = new long[n];
        int[] origIdx = new int[n];

        for (int i = 0; i < n; i++) {
            List<Integer> iv = intervals.get(order[i]);
            L[i] = iv.get(0);
            R[i] = iv.get(1);
            W[i] = iv.get(2);
            origIdx[i] = order[i];
        }

        // dp[i][k] = best (score, sorted indices) using first i sorted intervals, at most k picks
        long[][] dpScore = new long[n + 1][5];
        int[][][] dpIdx = new int[n + 1][5][];

        for (int k = 0; k <= 4; k++) {
            dpScore[0][k] = 0L;
            dpIdx[0][k] = new int[0];
        }

        for (int i = 1; i <= n; i++) {
            dpScore[i][0] = 0L;
            dpIdx[i][0] = new int[0];

            int cur = i - 1;               // position in sorted arrays
            long curL = L[cur];
            long curW = W[cur];
            int curOrig = origIdx[cur];

            // p = count of intervals with R < curL  (compatible prefix length)
            int p = lowerBound(R, curL);

            for (int k = 1; k <= 4; k++) {
                // Option A: skip current interval
                long scoreA = dpScore[i - 1][k];
                int[] idxA = dpIdx[i - 1][k];

                // Option B: take current interval
                long scoreB = dpScore[p][k - 1] + curW;
                int[] idxB = insertSorted(dpIdx[p][k - 1], curOrig);

                if (scoreB > scoreA || (scoreB == scoreA && lexSmaller(idxB, idxA))) {
                    dpScore[i][k] = scoreB;
                    dpIdx[i][k] = idxB;
                } else {
                    dpScore[i][k] = scoreA;
                    dpIdx[i][k] = idxA;
                }
            }
        }

        return dpIdx[n][4];
    }

    // Returns count of elements in sorted array arr strictly less than target
    private int lowerBound(long[] arr, long target) {
        int lo = 0, hi = arr.length;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (arr[mid] < target) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }

    // Inserts val into sorted array arr, returns new sorted array
    private int[] insertSorted(int[] arr, int val) {
        int[] res = new int[arr.length + 1];
        int i = 0;
        while (i < arr.length && arr[i] < val) {
            res[i] = arr[i];
            i++;
        }
        res[i] = val;
        for (int j = i; j < arr.length; j++) {
            res[j + 1] = arr[j];
        }
        return res;
    }

    // True if a is lexicographically smaller than b (shorter prefix counts as smaller)
    private boolean lexSmaller(int[] a, int[] b) {
        int len = Math.min(a.length, b.length);
        for (int i = 0; i < len; i++) {
            if (a[i] != b[i]) return a[i] < b[i];
        }
        return a.length < b.length;
    }
}