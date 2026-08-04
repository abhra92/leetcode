class Solution {
    public List<Integer> findMissingElements(int[] nums) {
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        Set<Integer> present = new HashSet<>();
        for (int n : nums) {
            min = Math.min(min, n);
            max = Math.max(max, n);
            present.add(n);
        }
        List<Integer> result = new ArrayList<>();
        for (int i = min; i <= max; i++) {
            if (!present.contains(i)) result.add(i);
        }
        return result;
    }
}