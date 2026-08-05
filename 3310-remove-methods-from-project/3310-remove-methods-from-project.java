class Solution {
    public List<Integer> remainingMethods(int n, int k, int[][] invocations) {
        // Build adjacency list for method invocations
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        
        for (int[] inv : invocations) {
            graph[inv[0]].add(inv[1]);
        }
        
        // Find all suspicious methods (k and all methods invoked by k directly/indirectly)
        Set<Integer> suspicious = new HashSet<>();
        dfs(k, graph, suspicious);
        
        // Check if any non-suspicious method invokes a suspicious method
        for (int i = 0; i < n; i++) {
            if (!suspicious.contains(i)) {
                for (int invoked : graph[i]) {
                    if (suspicious.contains(invoked)) {
                        // Can't remove all suspicious methods
                        // Return all methods
                        List<Integer> result = new ArrayList<>();
                        for (int j = 0; j < n; j++) {
                            result.add(j);
                        }
                        return result;
                    }
                }
            }
        }
        
        // Can safely remove all suspicious methods
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (!suspicious.contains(i)) {
                result.add(i);
            }
        }
        return result;
    }
    
    private void dfs(int node, List<Integer>[] graph, Set<Integer> suspicious) {
        suspicious.add(node);
        for (int neighbor : graph[node]) {
            if (!suspicious.contains(neighbor)) {
                dfs(neighbor, graph, suspicious);
            }
        }
    }
}