class Solution {
    public int removeCoveredIntervals(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> a[0] != b[0] ? a[0] - b[0] : b[1] - a[1]);
        int count = 0, maxEnd = 0;
        for (int[] iv : intervals) {
            if (iv[1] > maxEnd) {
                count++;
                maxEnd = iv[1];
            }
        }
        return count;
    }
}