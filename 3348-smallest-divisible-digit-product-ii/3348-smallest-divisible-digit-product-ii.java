class Solution {
    private int getDp(int[][] dp, int i, int j) {
        int r = Math.max(0, i);
        int c = Math.max(0, j);
        return dp[r][c];
    }

    public String smallestNumber(String num, long t) {
        long temp = t;
        int t2 = 0, t3 = 0, t5 = 0, t7 = 0;
        while (temp % 2 == 0) { t2++; temp /= 2; }
        while (temp % 3 == 0) { t3++; temp /= 3; }
        while (temp % 5 == 0) { t5++; temp /= 5; }
        while (temp % 7 == 0) { t7++; temp /= 7; }
        
        if (temp > 1) return "-1";
        
        int[][] dp = new int[60][40];
        for (int i = 0; i < 60; i++) {
            for (int j = 0; j < 40; j++) {
                if (i == 0 && j == 0) {
                    dp[i][j] = 0;
                    continue;
                }
                int min = Integer.MAX_VALUE / 2;
                
                // Only consider digits that make progress towards positive requirements.
                // This prevents cyclic dependency (where the current state refers to itself)
                // and correctly calculates the minimum digits required.
                if (i > 0) {
                    min = Math.min(min, 1 + getDp(dp, i - 1, j));     // digit 2
                    min = Math.min(min, 1 + getDp(dp, i - 2, j));     // digit 4
                    min = Math.min(min, 1 + getDp(dp, i - 3, j));     // digit 8
                }
                if (j > 0) {
                    min = Math.min(min, 1 + getDp(dp, i, j - 1));     // digit 3
                    min = Math.min(min, 1 + getDp(dp, i, j - 2));     // digit 9
                }
                if (i > 0 || j > 0) {
                    min = Math.min(min, 1 + getDp(dp, i - 1, j - 1)); // digit 6
                }
                
                dp[i][j] = min;
            }
        }
        
        int[] c2 = {0, 0, 1, 0, 2, 0, 1, 0, 3, 0};
        int[] c3 = {0, 0, 0, 1, 0, 0, 1, 0, 0, 2};
        int[] c5 = {0, 0, 0, 0, 0, 1, 0, 0, 0, 0};
        int[] c7 = {0, 0, 0, 0, 0, 0, 0, 1, 0, 0};
        
        int n = num.length();
        int[] req2 = new int[n + 1];
        int[] req3 = new int[n + 1];
        int[] req5 = new int[n + 1];
        int[] req7 = new int[n + 1];
        
        req2[0] = t2; req3[0] = t3; req5[0] = t5; req7[0] = t7;
        int firstZero = n;
        for (int i = 0; i < n; i++) {
            int d = num.charAt(i) - '0';
            if (d == 0) {
                firstZero = i;
                break;
            }
            req2[i + 1] = Math.max(0, req2[i] - c2[d]);
            req3[i + 1] = Math.max(0, req3[i] - c3[d]);
            req5[i + 1] = Math.max(0, req5[i] - c5[d]);
            req7[i + 1] = Math.max(0, req7[i] - c7[d]);
        }
        
        // Try replacing a digit at index i with a strictly larger one to find an optimal match with the same length
        for (int i = Math.min(n, firstZero); i >= 0; i--) {
            if (i == n) {
                if (req2[n] == 0 && req3[n] == 0 && req5[n] == 0 && req7[n] == 0) {
                    return num;
                }
                continue;
            }
            
            int startD = num.charAt(i) - '0' + 1;
            for (int d = startD; d <= 9; d++) {
                int r2 = Math.max(0, req2[i] - c2[d]);
                int r3 = Math.max(0, req3[i] - c3[d]);
                int r5 = Math.max(0, req5[i] - c5[d]);
                int r7 = Math.max(0, req7[i] - c7[d]);
                
                int remL = n - 1 - i;
                // If the remaining requirements can be met within the remaining length available
                if (r5 + r7 + dp[r2][r3] <= remL) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(num.substring(0, i));
                    sb.append((char)(d + '0'));
                    
                    int curr2 = r2, curr3 = r3, curr5 = r5, curr7 = r7;
                    for (int k = i + 1; k < n; k++) {
                        for (int nd = 1; nd <= 9; nd++) {
                            int nr2 = Math.max(0, curr2 - c2[nd]);
                            int nr3 = Math.max(0, curr3 - c3[nd]);
                            int nr5 = Math.max(0, curr5 - c5[nd]);
                            int nr7 = Math.max(0, curr7 - c7[nd]);
                            
                            // Greedily pick the smallest viable digit that still allows fulfillment
                            if (nr5 + nr7 + dp[nr2][nr3] <= n - 1 - k) {
                                sb.append((char)(nd + '0'));
                                curr2 = nr2; curr3 = nr3; curr5 = nr5; curr7 = nr7;
                                break;
                            }
                        }
                    }
                    return sb.toString();
                }
            }
        }
        
        // If we couldn't construct it with the current length 'n', compute minimum digits required to append.
        int reqLen = t5 + t7 + dp[t2][t3];
        int K = Math.max(n + 1, reqLen); // The length will be strictly larger than `n`
        StringBuilder sb = new StringBuilder();
        int curr2 = t2, curr3 = t3, curr5 = t5, curr7 = t7;
        
        for (int k = 0; k < K; k++) {
            for (int nd = 1; nd <= 9; nd++) {
                int nr2 = Math.max(0, curr2 - c2[nd]);
                int nr3 = Math.max(0, curr3 - c3[nd]);
                int nr5 = Math.max(0, curr5 - c5[nd]);
                int nr7 = Math.max(0, curr7 - c7[nd]);
                
                if (nr5 + nr7 + dp[nr2][nr3] <= K - 1 - k) {
                    sb.append((char)(nd + '0'));
                    curr2 = nr2; curr3 = nr3; curr5 = nr5; curr7 = nr7;
                    break;
                }
            }
        }
        
        return sb.toString();
    }
}