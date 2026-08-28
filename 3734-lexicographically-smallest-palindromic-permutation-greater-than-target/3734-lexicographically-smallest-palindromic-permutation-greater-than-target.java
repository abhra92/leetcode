class Solution {
    public String lexPalindromicPermutation(String s, String target) {
        int n = s.length();
        int[] count = new int[26];
        for (char ch : s.toCharArray()) count[ch - 'a']++;

        int oddCount = 0, oddChar = -1;
        for (int i = 0; i < 26; i++) {
            if (count[i] % 2 == 1) { oddCount++; oddChar = i; }
        }
        boolean feasible = (n % 2 == 0) ? (oddCount == 0) : (oddCount == 1);
        if (!feasible) return "";

        int h = n / 2;
        int[] halfCounts = new int[26];
        for (int i = 0; i < 26; i++) halfCounts[i] = count[i] / 2;
        char mid = (n % 2 == 1) ? (char) ('a' + oddChar) : 0;

        String targetFirst = target.substring(0, h);
        String targetSecond = target.substring(h + (n % 2), n);
        char targetMid = (n % 2 == 1) ? target.charAt(h) : 0;

        // ---- Try B1: first half == targetFirst exactly ----
        int[] tfCount = new int[26];
        for (char ch : targetFirst.toCharArray()) tfCount[ch - 'a']++;
        boolean isPerm = true;
        for (int i = 0; i < 26; i++) {
            if (tfCount[i] != halfCounts[i]) { isPerm = false; break; }
        }

        if (isPerm) {
            boolean valid;
            String revTF = new StringBuilder(targetFirst).reverse().toString();
            if (n % 2 == 1) {
                if (mid > targetMid) valid = true;
                else if (mid == targetMid) valid = revTF.compareTo(targetSecond) > 0;
                else valid = false;
            } else {
                valid = revTF.compareTo(targetSecond) > 0;
            }
            if (valid) {
                StringBuilder sb = new StringBuilder();
                sb.append(targetFirst);
                if (n % 2 == 1) sb.append(mid);
                sb.append(revTF);
                return sb.toString();
            }
        }

        // ---- B2: find smallest H > targetFirst using the half-multiset ----
        String H = findSmallestGreater(targetFirst, halfCounts.clone(), h);
        if (H == null) return "";

        StringBuilder sb = new StringBuilder();
        sb.append(H);
        if (n % 2 == 1) sb.append(mid);
        sb.append(new StringBuilder(H).reverse());
        return sb.toString();
    }

    // Finds the lexicographically smallest permutation of the given multiset
    // (counts) of length h that is strictly greater than targetFirst.
    private String findSmallestGreater(String targetFirst, int[] counts, int h) {
        if (h == 0) return null;

        char[] prefix = new char[h];
        int matchLength = 0;
        for (int i = 0; i < h; i++) {
            char c = targetFirst.charAt(i);
            int idx = c - 'a';
            if (counts[idx] > 0) {
                counts[idx]--;
                prefix[i] = c;
                matchLength = i + 1;
            } else {
                break;
            }
        }

        int i = matchLength;
        if (i >= h) {
            i = h - 1;
            counts[prefix[i] - 'a']++; // roll back position h-1
        }

        while (i >= 0) {
            char tChar = targetFirst.charAt(i);
            int foundIdx = -1;
            for (int c = tChar - 'a' + 1; c < 26; c++) {
                if (counts[c] > 0) { foundIdx = c; break; }
            }
            if (foundIdx != -1) {
                counts[foundIdx]--;
                StringBuilder sb = new StringBuilder();
                sb.append(prefix, 0, i);
                sb.append((char) ('a' + foundIdx));
                for (int c = 0; c < 26; c++) {
                    for (int k = 0; k < counts[c]; k++) sb.append((char) ('a' + c));
                }
                return sb.toString();
            } else {
                if (i == 0) break;
                counts[prefix[i - 1] - 'a']++; // roll back position i-1
                i--;
            }
        }
        return null;
    }
}