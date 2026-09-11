class Solution {
    public int totalNumbers(int[] digits) {
        int[] freq = new int[10];
        for (int d : digits) {
            freq[d]++;
        }

        int[] evens = {0, 2, 4, 6, 8};
        int count = 0;

        for (int h = 1; h <= 9; h++) {
            for (int t = 0; t <= 9; t++) {
                for (int u : evens) {
                    if (isValid(h, t, u, freq)) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    private boolean isValid(int h, int t, int u, int[] freq) {
        int[] need = new int[10];
        need[h]++;
        need[t]++;
        need[u]++;

        for (int digit = 0; digit <= 9; digit++) {
            if (need[digit] > freq[digit]) {
                return false;
            }
        }
        return true;
    }
}