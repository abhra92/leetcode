class Solution {
    public int minimumDeletions(int[] nums) {
        int n = nums.length;
        int minIdx = 0, maxIdx = 0;

        for (int idx = 1; idx < n; idx++) {
            if (nums[idx] < nums[minIdx]) minIdx = idx;
            if (nums[idx] > nums[maxIdx]) maxIdx = idx;
        }

        int i = Math.min(minIdx, maxIdx);
        int j = Math.max(minIdx, maxIdx);

        int fromFront = j + 1;                 // remove both from the front
        int fromBack = n - i;                   // remove both from the back
        int fromBoth = (i + 1) + (n - j);        // one from each side

        return Math.min(fromFront, Math.min(fromBack, fromBoth));
    }
}