class Solution {
    int[] treeMax;
    int[] treePref;
    int[] treeSuff;
    char[] arr;

    public int[] longestRepeating(String s, String queryCharacters, int[] queryIndices) {
        int n = s.length();
        treeMax = new int[4 * n + 1];
        treePref = new int[4 * n + 1];
        treeSuff = new int[4 * n + 1];
        arr = s.toCharArray();

        // Build the initial segment tree
        build(1, 0, n - 1);

        int k = queryIndices.length;
        int[] ans = new int[k];
        
        // Process each query
        for (int i = 0; i < k; i++) {
            update(1, 0, n - 1, queryIndices[i], queryCharacters.charAt(i));
            // The max repeating character of the entire string will be at the root (node 1)
            ans[i] = treeMax[1]; 
        }
        
        return ans;
    }

    private void build(int node, int L, int R) {
        if (L == R) {
            treeMax[node] = 1;
            treePref[node] = 1;
            treeSuff[node] = 1;
            return;
        }
        
        int mid = L + (R - L) / 2;
        build(2 * node, L, mid);
        build(2 * node + 1, mid + 1, R);
        merge(node, L, R, mid);
    }

    private void update(int node, int L, int R, int idx, char c) {
        if (L == R) {
            arr[idx] = c; // Update the character in our base array
            return;
        }
        
        int mid = L + (R - L) / 2;
        if (idx <= mid) {
            update(2 * node, L, mid, idx, c);
        } else {
            update(2 * node + 1, mid + 1, R, idx, c);
        }
        
        // Bubble up the changes
        merge(node, L, R, mid);
    }

    private void merge(int node, int L, int R, int mid) {
        int leftChild = 2 * node;
        int rightChild = 2 * node + 1;

        // Base max properties from children
        treeMax[node] = Math.max(treeMax[leftChild], treeMax[rightChild]);
        treePref[node] = treePref[leftChild];
        treeSuff[node] = treeSuff[rightChild];

        int leftSize = mid - L + 1;
        int rightSize = R - mid;

        // If characters cross the split boundary identical, we can bridge them
        if (arr[mid] == arr[mid + 1]) {
            treeMax[node] = Math.max(treeMax[node], treeSuff[leftChild] + treePref[rightChild]);

            // If the entire left child is one single identical character sequence
            if (treePref[leftChild] == leftSize) {
                treePref[node] = leftSize + treePref[rightChild];
            }
            
            // If the entire right child is one single identical character sequence
            if (treeSuff[rightChild] == rightSize) {
                treeSuff[node] = rightSize + treeSuff[leftChild];
            }
        }
    }
}