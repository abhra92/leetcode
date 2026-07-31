class Solution {
    public int minimumPushes(String word) {
        int[] freq = new int[26];
        for (char c : word.toCharArray()) freq[c - 'a']++;

        Integer[] idx = new Integer[26];
        for (int i = 0; i < 26; i++) idx[i] = i;
        Arrays.sort(idx, (a, b) -> freq[b] - freq[a]);

        int total = 0;
        for (int i = 0; i < 26; i++) {
            int f = freq[idx[i]];
            if (f == 0) break;
            int pushCount = i / 8 + 1;
            total += pushCount * f;
        }
        return total;
    }
}