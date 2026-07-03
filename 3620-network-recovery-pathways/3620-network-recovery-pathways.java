class Solution {
    public int findMaxPathScore(int[][] edges, boolean[] online, long k) {
        int n = online.length;
        List<int[]>[] adj = new ArrayList[n];
        int[] inDeg = new int[n];
        
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }
        
        for (int[] e : edges) {
            adj[e[0]].add(new int[]{e[1], e[2]});
            inDeg[e[1]]++;
        }
        
        int[] topo = new int[n];
        int idx = 0;
        int[] q = new int[n];
        int head = 0, tail = 0;
        
        for (int i = 0; i < n; i++) {
            if (inDeg[i] == 0) q[tail++] = i;
        }
        
        while (head < tail) {
            int u = q[head++];
            topo[idx++] = u;
            for (int[] e : adj[u]) {
                if (--inDeg[e[0]] == 0) q[tail++] = e[0];
            }
        }
        
        int low = 0, high = 1_000_000_000, ans = -1;
        long[] dist = new long[n];
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            Arrays.fill(dist, Long.MAX_VALUE);
            dist[0] = 0;
            
            for (int i = 0; i < n; i++) {
                int u = topo[i];
                if (dist[u] == Long.MAX_VALUE || !online[u]) continue;
                
                for (int[] e : adj[u]) {
                    int v = e[0], w = e[1];
                    if (online[v] && w >= mid) {
                        if (dist[u] + w < dist[v]) {
                            dist[v] = dist[u] + w;
                        }
                    }
                }
            }
            
            if (dist[n - 1] <= k) {
                ans = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        
        return ans;
    }
}