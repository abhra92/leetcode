class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int n = s.length();
        int[] count = new int[26];
        for (char c : s.toCharArray()) count[c - 'a']++;

        StringBuilder prefix = new StringBuilder();
        String best = null;

        for (int i = 0; i < n; i++) {
            int tc = target.charAt(i) - 'a';

            // Try placing the smallest available character > tc at position i
            for (int c = tc + 1; c < 26; c++) {
                if (count[c] > 0) {
                    StringBuilder sb = new StringBuilder(n);
                    sb.append(prefix);
                    sb.append((char) ('a' + c));

                    count[c]--;
                    for (int k = 0; k < 26; k++) {
                        for (int cnt = 0; cnt < count[k]; cnt++) {
                            sb.append((char) ('a' + k));
                        }
                    }
                    count[c]++; // restore, this was just a probe

                    best = sb.toString();
                    break; // smallest valid c found for this i
                }
            }

            // Try to extend the exact-match prefix
            if (count[tc] > 0) {
                count[tc]--;
                prefix.append(target.charAt(i));
            } else {
                break; // can't match target[i] exactly, no longer prefix possible
            }
        }

        return best == null ? "" : best;
    }
}