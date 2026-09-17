class Solution {
    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;
        int[] best = new int[n]; // best[i] = shortest valid subarray ending at or before i
        Arrays.fill(best, Integer.MAX_VALUE);

        int left = 0, sum = 0, ans = Integer.MAX_VALUE;

        for (int right = 0; right < n; right++) {
            sum += arr[right];

            while (sum > target) {
                sum -= arr[left];
                left++;
            }

            if (sum == target) {
                int len = right - left + 1;

                if (left > 0 && best[left - 1] != Integer.MAX_VALUE) {
                    ans = Math.min(ans, len + best[left - 1]);
                }

                best[right] = Math.min(right > 0 ? best[right - 1] : Integer.MAX_VALUE, len);
            } else {
                best[right] = right > 0 ? best[right - 1] : Integer.MAX_VALUE;
            }
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}