class Solution {
    private int[] parent, rank_;

    public int countCompleteComponents(int n, int[][] edges) {
        parent = new int[n];
        rank_ = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;

        for (int[] e : edges) {
            union(e[0], e[1]);
        }

        int[] vertexCount = new int[n];
        int[] edgeCount = new int[n];

        for (int i = 0; i < n; i++) {
            vertexCount[find(i)]++;
        }

        for (int[] e : edges) {
            int root = find(e[0]);
            edgeCount[root]++;
        }

        int complete = 0;
        for (int i = 0; i < n; i++) {
            if (find(i) == i) { // i is a root
                long needed = (long) vertexCount[i] * (vertexCount[i] - 1) / 2;
                if (edgeCount[i] == needed) complete++;
            }
        }

        return complete;
    }

    private int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]);
        return parent[x];
    }

    private void union(int a, int b) {
        int ra = find(a), rb = find(b);
        if (ra == rb) return;
        if (rank_[ra] < rank_[rb]) { int t = ra; ra = rb; rb = t; }
        parent[rb] = ra;
        if (rank_[ra] == rank_[rb]) rank_[ra]++;
    }
}