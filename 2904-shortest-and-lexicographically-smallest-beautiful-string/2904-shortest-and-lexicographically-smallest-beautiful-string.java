class Solution {
    public String shortestBeautifulSubstring(String s, int k) {
        int n = s.length();
        String best = "";
        int ones = 0;
        int left = 0;

        for (int right = 0; right < n; right++) {
            if (s.charAt(right) == '1') {
                ones++;
            }

            // Shrink from the left while it's safe (leading zeros don't matter)
            while (left <= right && ones > k) {
                if (s.charAt(left) == '1') {
                    ones--;
                }
                left++;
            }

            // Try to shrink further: drop leading zeros to minimize length
            int l = left;
            while (l <= right && s.charAt(l) == '0') {
                l++;
            }

            if (ones == k) {
                String candidate = s.substring(l, right + 1);
                if (best.isEmpty()
                        || candidate.length() < best.length()
                        || (candidate.length() == best.length() && candidate.compareTo(best) < 0)) {
                    best = candidate;
                }
            }
        }

        return best;
    }
}