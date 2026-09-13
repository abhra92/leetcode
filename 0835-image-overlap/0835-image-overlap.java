class Solution {
    public int largestOverlap(int[][] img1, int[][] img2) {
        int n = img1.length;
        List<int[]> A = new ArrayList<>();
        List<int[]> B = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (img1[i][j] == 1) A.add(new int[]{i, j});
                if (img2[i][j] == 1) B.add(new int[]{i, j});
            }
        }

        if (A.isEmpty() || B.isEmpty()) return 0;

        Map<Integer, Integer> freq = new HashMap<>();
        int best = 0;

        for (int[] a : A) {
            for (int[] b : B) {
                int dx = b[0] - a[0];
                int dy = b[1] - a[1];
                int key = (dx + n) * 200 + (dy + n); // safe encoding, n <= 30
                int count = freq.merge(key, 1, Integer::sum);
                best = Math.max(best, count);
            }
        }

        return best;
    }
}