class Solution {
    public int uniqueXorTriplets(int[] nums) {
        final int DOMAIN = 2048; // nums[i] <= 1500 < 2^11, so any XOR of 3 values < 2048

        // Step 1: collect distinct values (duplicates don't create new XOR possibilities)
        boolean[] present = new boolean[DOMAIN];
        for (int v : nums) present[v] = true;

        int distinctCount = 0;
        for (boolean b : present) if (b) distinctCount++;
        int[] distinct = new int[distinctCount];
        int idx = 0;
        for (int v = 0; v < DOMAIN; v++) if (present[v]) distinct[idx++] = v;

        // Step 2: all pairwise XORs (a <= b in the distinct array, a can equal b)
        boolean[] pairXor = new boolean[DOMAIN];
        for (int i = 0; i < distinct.length; i++) {
            for (int j = i; j < distinct.length; j++) {
                pairXor[distinct[i] ^ distinct[j]] = true;
            }
        }

        // Step 3: XOR each pair-result with every distinct value (the third element)
        boolean[] result = new boolean[DOMAIN];
        int count = 0;
        for (int x = 0; x < DOMAIN; x++) {
            if (!pairXor[x]) continue;
            for (int v : distinct) {
                int r = x ^ v;
                if (!result[r]) {
                    result[r] = true;
                    count++;
                }
            }
        }

        return count;
    }
}