class Solution {
    public int[] validSequence(String word1, String word2) {
        int n = word1.length(), m = word2.length();
        ArrayList<Integer>[] pos = new ArrayList[26];
        for (int i = 0; i < 26; i++) pos[i] = new ArrayList<>();
        for (int i = 0; i < n; i++) pos[word1.charAt(i) - 'a'].add(i);

        int[] E = new int[m + 1];
        int[] V = new int[m + 1];
        E[m] = n;
        V[m] = n;

        for (int k = m - 1; k >= 0; k--) {
            E[k] = prevOcc(pos[word2.charAt(k) - 'a'], E[k + 1] - 1);
        }

        for (int k = m - 1; k >= 0; k--) {
            int exact = prevOcc(pos[word2.charAt(k) - 'a'], V[k + 1] - 1);
            int mis = E[k + 1] == -1 ? -1 : E[k + 1] - 1;
            V[k] = Math.max(exact, mis);
        }

        if (V[0] == -1) return new int[0];

        int[] ans = new int[m];
        int prev = -1;
        boolean used = false;

        for (int k = 0; k < m; k++) {
            if (used) {
                int i = nextOcc(pos[word2.charAt(k) - 'a'], prev);
                if (i == -1 || i >= E[k + 1]) return new int[0];
                ans[k] = i;
                prev = i;
            } else {
                int exact = nextOcc(pos[word2.charAt(k) - 'a'], prev);
                if (exact == -1 || exact >= V[k + 1]) exact = -1;

                int mis = (prev + 1 < E[k + 1]) ? prev + 1 : -1;

                if (exact == -1 && mis == -1) return new int[0];

                if (mis != -1 && (exact == -1 || mis < exact)) {
                    ans[k] = mis;
                    prev = mis;
                    used = true;
                } else {
                    ans[k] = exact;
                    prev = exact;
                }
            }
        }

        return ans;
    }

    private int prevOcc(ArrayList<Integer> list, int limit) {
        int l = 0, r = list.size();
        while (l < r) {
            int mid = (l + r) >>> 1;
            if (list.get(mid) <= limit) l = mid + 1;
            else r = mid;
        }
        return l == 0 ? -1 : list.get(l - 1);
    }

    private int nextOcc(ArrayList<Integer> list, int prev) {
        int l = 0, r = list.size();
        while (l < r) {
            int mid = (l + r) >>> 1;
            if (list.get(mid) <= prev) l = mid + 1;
            else r = mid;
        }
        return l == list.size() ? -1 : list.get(l);
    }
}