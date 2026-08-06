class Solution {
    public int smallestNumber(int n, int t) {
        for (int num = n; ; num++) {
            if (isValid(num, t)) {
                return num;
            }
        }
    }
    
    private boolean isValid(int num, int t) {
        long product = 1;
        int temp = num;
        while (temp > 0) {
            product *= (temp % 10);
            temp /= 10;
        }
        return product % t == 0;
    }
}