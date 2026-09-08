class Solution {
    public int countCommas(int n) {
        int total = 0;
        for (int x = 1; x <= n; x++) {
            int digits = String.valueOf(x).length();
            total += (digits - 1) / 3;
        }
        return total;
    }
}