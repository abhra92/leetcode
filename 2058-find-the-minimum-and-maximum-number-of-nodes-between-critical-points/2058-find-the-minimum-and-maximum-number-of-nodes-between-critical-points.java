/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public int[] nodesBetweenCriticalPoints(ListNode head) {
        int idx = 0;
        int prevVal = head.val;
        ListNode curr = head.next;
        idx = 1;

        int firstIdx = -1;
        int lastCriticalIdx = -1;
        int minDistance = Integer.MAX_VALUE;

        while (curr.next != null) {
            int currVal = curr.val;
            int nextVal = curr.next.val;

            boolean isMaxima = currVal > prevVal && currVal > nextVal;
            boolean isMinima = currVal < prevVal && currVal < nextVal;

            if (isMaxima || isMinima) {
                if (firstIdx == -1) {
                    firstIdx = idx;
                    lastCriticalIdx = idx;
                } else {
                    minDistance = Math.min(minDistance, idx - lastCriticalIdx);
                    lastCriticalIdx = idx;
                }
            }

            prevVal = currVal;
            curr = curr.next;
            idx++;
        }

        if (firstIdx == -1 || firstIdx == lastCriticalIdx) {
            return new int[]{-1, -1};
        }

        int maxDistance = lastCriticalIdx - firstIdx;
        return new int[]{minDistance, maxDistance};
    }
}