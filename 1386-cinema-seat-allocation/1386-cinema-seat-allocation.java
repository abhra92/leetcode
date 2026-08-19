class Solution {
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {
        Map<Integer, Integer> row = new HashMap<>();
        for (int[] r : reservedSeats) {
            int seat = r[1];
            if (seat < 2 || seat > 9) continue; // seat 1,10 don't matter
            int bit = 1 << (seat - 2); // bits 0..7 for seats 2..9
            row.merge(r[0], bit, (a, b) -> a | b);
        }

        int LEFT  = 0b00001111; // seats 2-5
        int MID   = 0b00111100; // seats 4-7
        int RIGHT = 0b11110000; // seats 6-9

        int result = (n - row.size()) * 2; // untouched rows get 2 groups free

        for (int mask : row.values()) {
            if ((mask & MID) == 0) {
                result += 1;
            } else {
                if ((mask & LEFT) == 0) result += 1;
                else if ((mask & RIGHT) == 0) result += 1;
            }
        }
        return result;
    }
}