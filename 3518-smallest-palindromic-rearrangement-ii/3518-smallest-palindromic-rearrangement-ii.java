class Solution {
    static final long CAP = 2_000_000L;
    
    public String smallestPalindrome(String s, int k) {
        int n = s.length();
        int[] freq = new int[26];
        for (char c : s.toCharArray()) freq[c - 'a']++;
        
        int[] half = new int[26];
        int middle = -1;
        int halfLen = 0;
        for (int i = 0; i < 26; i++) {
            half[i] = freq[i] / 2;
            halfLen += half[i];
            if (freq[i] % 2 == 1) middle = i;
        }
        
        long total = countArrangements(half, halfLen);
        if (k > total) return "";
        
        StringBuilder halfSb = new StringBuilder();
        long kk = k;
        int remaining = halfLen;
        for (int pos = 0; pos < halfLen; pos++) {
            for (int c = 0; c < 26; c++) {
                if (half[c] == 0) continue;
                half[c]--;
                long cnt = countArrangements(half, remaining - 1);
                if (kk <= cnt) {
                    halfSb.append((char) ('a' + c));
                    remaining--;
                    break;
                } else {
                    kk -= cnt;
                    half[c]++;
                }
            }
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append(halfSb);
        if (middle != -1) sb.append((char) ('a' + middle));
        sb.append(halfSb.reverse());
        return sb.toString();
    }
    
    private long countArrangements(int[] counts, int totalLen) {
        long remaining = totalLen;
        long result = 1;
        for (int c = 0; c < 26; c++) {
            if (counts[c] == 0) continue;
            long comb = combCapped(remaining, counts[c]);
            result *= comb;
            if (result > CAP) result = CAP + 1;
            remaining -= counts[c];
        }
        return result;
    }
    
    private long combCapped(long n, int r) {
        if (r == 0 || r == n) return 1;
        int rr = (int) Math.min(r, n - r);
        long res = 1;
        for (int i = 0; i < rr; i++) {
            res = res * (n - i) / (i + 1);
            if (res > CAP) return CAP + 1;
        }
        return res;
    }
}