import java.util.*;

class Solution {
    public List<String> maxNumOfSubstrings(String s) {
        int n = s.length();
        int[] first = new int[26];
        int[] last = new int[26];
        Arrays.fill(first, -1);

        for (int i = 0; i < n; i++) {
            int c = s.charAt(i) - 'a';
            if (first[c] == -1) first[c] = i;
            last[c] = i;
        }

        List<int[]> intervals = new ArrayList<>();

        for (int c = 0; c < 26; c++) {
            if (first[c] == -1) continue;

            int start = first[c];
            int end = last[c];
            boolean valid = true;

            int k = start;
            while (k <= end) {
                int c2 = s.charAt(k) - 'a';
                if (first[c2] < start) {
                    valid = false;
                    break;
                }
                if (last[c2] > end) {
                    end = last[c2]; // window grows, keep scanning
                }
                k++;
            }

            if (valid) {
                intervals.add(new int[]{start, end});
            }
        }

        // sort by end index (ties broken by start, doesn't matter)
        intervals.sort((a, b) -> a[1] - b[1]);

        List<String> result = new ArrayList<>();
        int prevEnd = -1;

        for (int[] iv : intervals) {
            if (iv[0] > prevEnd) {
                result.add(s.substring(iv[0], iv[1] + 1));
                prevEnd = iv[1];
            }
        }

        return result;
    }
}