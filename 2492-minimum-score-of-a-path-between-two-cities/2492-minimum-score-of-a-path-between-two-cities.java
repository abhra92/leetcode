class Solution {
    int[] parent, rank_;
    int[] minEdge;

    int find(int x) {
        while (parent[x] != x) {
            parent[x] = parent[parent[x]];
            x = parent[x];
        }
        return x;
    }

    void union(int a, int b, int w) {
        int ra = find(a), rb = find(b);
        if (ra != rb) {
            if (rank_[ra] < rank_[rb]) { int t = ra; ra = rb; rb = t; }
            parent[rb] = ra;
            if (rank_[ra] == rank_[rb]) rank_[ra]++;
            minEdge[ra] = Math.min(minEdge[ra], minEdge[rb]);
        }
        minEdge[ra] = Math.min(minEdge[ra], w);
    }

    public int minScore(int n, int[][] roads) {
        parent = new int[n + 1];
        rank_ = new int[n + 1];
        minEdge = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            parent[i] = i;
            minEdge[i] = Integer.MAX_VALUE;
        }

        for (int[] r : roads) {
            union(r[0], r[1], r[2]);
        }

        return minEdge[find(1)];
    }
}