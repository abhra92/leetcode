class Solution {
    public long gcdSum(int[] nums) {
        int n = nums.length;
        long[] p = new long[n];
        int mx = 0;
        for (int i = 0; i < n; i++) {
            mx = Math.max(mx, nums[i]);
            p[i] = gcd(nums[i], mx);
        }
        Arrays.sort(p);
        long sum = 0;
        int l = 0, r = n - 1;
        while (l < r) {
            sum += gcd(p[l], p[r]);
            l++;
            r--;
        }
        return sum;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long t = b;
            b = a % b;
            a = t;
        }
        return a;
    }
}