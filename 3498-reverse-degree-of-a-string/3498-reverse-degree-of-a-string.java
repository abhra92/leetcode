class Solution {
    public int reverseDegree(String s) {
        int sum = 0;
        for (int i = 0; i < s.length(); i++) {
            int revAlphaPos = 26 - (s.charAt(i) - 'a');
            int strPos = i + 1;
            sum += revAlphaPos * strPos;
        }
        return sum;
    }
}