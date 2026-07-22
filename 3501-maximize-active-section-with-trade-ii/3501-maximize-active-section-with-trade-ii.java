import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public List<Integer> maxActiveSectionsAfterTrade(String s, int[][] queries) {
        int n = s.length();
        int totalOnes = 0;
        for (int i = 0; i < n; i++) if (s.charAt(i) == '1') totalOnes++;

        List<int[]> runs = new ArrayList<>();
        int i = 0;
        while (i < n) {
            int j = i;
            while (j < n && s.charAt(j) == s.charAt(i)) j++;
            runs.add(new int[]{i, j - 1, s.charAt(i) - '0'});
            i = j;
        }
        int R = runs.size();

        List<int[]> bl = new ArrayList<>();
        for (int idx = 1; idx < R - 1; idx++) {
            int[] cur = runs.get(idx);
            if (cur[2] == 1) {
                int[] z1 = runs.get(idx - 1);
                int[] z2 = runs.get(idx + 1);
                int a = z1[0], b = cur[0], c = cur[1] + 1, d = z2[1] + 1;
                bl.add(new int[]{a, b, c, d, b - a, d - c});
            }
        }
        int m = bl.size();
        int Q = queries.length;
        int[] ans = new int[Q];

        if (m == 0) {
            Arrays.fill(ans, totalOnes);
            List<Integer> res = new ArrayList<>();
            for (int v : ans) res.add(v);
            return res;
        }

        int[] A = new int[m], B = new int[m], C = new int[m], D = new int[m], LZ1 = new int[m], LZ2 = new int[m];
        for (int k = 0; k < m; k++) {
            int[] br = bl.get(k);
            A[k] = br[0]; B[k] = br[1]; C[k] = br[2]; D[k] = br[3]; LZ1[k] = br[4]; LZ2[k] = br[5];
        }

        final int NEG = Integer.MIN_VALUE / 2;
        int size = 1;
        while (size < m) size <<= 1;
        int[] seg1 = new int[2 * size], seg2 = new int[2 * size];
        Arrays.fill(seg1, NEG); Arrays.fill(seg2, NEG);

        List<List<Integer>> eventsAt = new ArrayList<>();
        for (int t = 0; t < n; t++) eventsAt.add(new ArrayList<>());
        for (int k = 0; k < m; k++)
            for (int t = C[k]; t < D[k]; t++) eventsAt.get(t).add(k);

        List<List<int[]>> qAt = new ArrayList<>();
        for (int t = 0; t < n; t++) qAt.add(new ArrayList<>());
        for (int qi = 0; qi < Q; qi++) {
            int l = queries[qi][0], r = queries[qi][1];
            qAt.get(r).add(new int[]{l, qi});
        }

        for (int r = 0; r < n; r++) {
            for (int k : eventsAt.get(r)) {
                int capped = r - C[k] + 1;
                int v1 = LZ1[k] + capped, v2 = B[k] + capped;
                int p = k + size;
                seg1[p] = v1;
                for (p >>= 1; p >= 1; p >>= 1) seg1[p] = Math.max(seg1[2 * p], seg1[2 * p + 1]);
                p = k + size;
                seg2[p] = v2;
                for (p >>= 1; p >= 1; p >>= 1) seg2[p] = Math.max(seg2[2 * p], seg2[2 * p + 1]);
            }
            for (int[] pr : qAt.get(r)) {
                int l = pr[0], qi = pr[1];
                
                int loB = 0, hiB = m;
                while (loB < hiB) { int mid = (loB + hiB) / 2; if (B[mid] > l) hiB = mid; else loB = mid + 1; }
                int posB = loB;

                int loA = 0, hiA = m;
                while (loA < hiA) { int mid = (loA + hiA) / 2; if (A[mid] >= l) hiA = mid; else loA = mid + 1; }
                int posA = loA;

                int cand1 = rangeMax(seg1, size, posA, m - 1);
                int cand2 = rangeMax(seg2, size, posB, posA - 1);
                
                if (cand2 != NEG) cand2 -= l;
                int best = Math.max(0, Math.max(cand1, cand2));
                ans[qi] = totalOnes + best;
            }
        }

        List<Integer> res = new ArrayList<>();
        for (int v : ans) res.add(v);
        return res;
    }

    private int rangeMax(int[] seg, int size, int l, int r) {
        final int NEG = Integer.MIN_VALUE / 2;
        if (l > r) return NEG;
        int res = NEG;
        l += size; r += size + 1;
        while (l < r) {
            if ((l & 1) == 1) res = Math.max(res, seg[l++]);
            if ((r & 1) == 1) res = Math.max(res, seg[--r]);
            l >>= 1; r >>= 1;
        }
        return res;
    }
}