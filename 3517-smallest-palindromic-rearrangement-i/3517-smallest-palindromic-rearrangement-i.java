class Solution {
    public String smallestPalindrome(String s) {
        int[] cnt = new int[26];
        for (char c : s.toCharArray()) cnt[c - 'a']++;

        char mid = 0;
        StringBuilder half = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            if (cnt[i] % 2 == 1) mid = (char) ('a' + i);
            int half_cnt = cnt[i] / 2;
            for (int j = 0; j < half_cnt; j++) half.append((char) ('a' + i));
        }

        StringBuilder res = new StringBuilder(half);
        if (mid != 0) res.append(mid);
        res.append(half.reverse());
        return res.toString();
    }
}