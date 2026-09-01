class Solution {
    public int minMoves(String[] classroom, int energy) {
        int rows = classroom.length;
        int cols = classroom[0].length();

        Map<Integer, Integer> litterIndex = new HashMap<>();
        int sr = -1, sc = -1;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                char ch = classroom[r].charAt(c);
                if (ch == 'S') {
                    sr = r;
                    sc = c;
                } else if (ch == 'L') {
                    litterIndex.put(r * cols + c, litterIndex.size());
                }
            }
        }

        int k = litterIndex.size();
        int fullMask = (1 << k) - 1;
        if (k == 0) return 0;

        int E = energy;
        int numEnergy = E + 1;
        int numMask = 1 << k;

        // state id = ((r * cols + c) * numEnergy + e) * numMask + mask
        int totalStates = rows * cols * numEnergy * numMask;
        boolean[] visited = new boolean[totalStates];

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        // Encode queue entries as long: r, c, e, mask, dist packed, or use int arrays
        ArrayDeque<int[]> queue = new ArrayDeque<>();
        int startId = ((sr * cols + sc) * numEnergy + E) * numMask + 0;
        visited[startId] = true;
        queue.offer(new int[]{sr, sc, E, 0, 0});

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int r = cur[0], c = cur[1], e = cur[2], mask = cur[3], dist = cur[4];

            if (e == 0) continue;

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];
                if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) continue;

                char cell = classroom[nr].charAt(nc);
                if (cell == 'X') continue;

                int newE = (cell == 'R') ? E : e - 1;

                int newMask = mask;
                if (cell == 'L') {
                    int bit = litterIndex.get(nr * cols + nc);
                    newMask = mask | (1 << bit);
                }

                if (newMask == fullMask) {
                    return dist + 1;
                }

                int sid = ((nr * cols + nc) * numEnergy + newE) * numMask + newMask;
                if (!visited[sid]) {
                    visited[sid] = true;
                    queue.offer(new int[]{nr, nc, newE, newMask, dist + 1});
                }
            }
        }

        return -1;
    }
}