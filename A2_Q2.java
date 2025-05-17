import java.math.BigInteger;

public class A2_Q2 {
    public static BigInteger num_swaps(int[] numbers){
        return sort(numbers, 0, numbers.length -1);
    }
    // keep in mind you can only swap two students if they're adjacent
    // but this holds for mergesort

    private static BigInteger sort (int[] nums, int left, int right){
        if (left >= right) {
            return BigInteger.ZERO; // list done
        }
        else{
            BigInteger total = BigInteger.ZERO;
            int mid = (left + right) / 2;
            total = sort(nums, left, mid).add(sort(nums, mid + 1, right)); // have to add the return of all the swaps
            return total.add(merge(nums,left, mid, right)); // merges A[p,q] with A[q+1,r] and returns the BigInteger with the number of swaps
        }

    }
    // implementation taken from textbook! - specifically intro to algorithms 4th ed.
    private static BigInteger merge(int[] nums, int left, int mid, int right) {
        int leftIndex = mid - left + 1; //length of first sub array
        int rightIndex = right - mid; // length of second let array
        int[] leftA = new int[leftIndex];
        int[] rightA = new int[rightIndex];
        BigInteger num = BigInteger.ZERO;

        for (int i = 0; i < leftIndex; i++) { // makes the copies of the arrays
            leftA[i] = nums[left + i ] ;
        }
        for (int j = 0; j < rightIndex; j++) {
            rightA[j] = nums[mid + 1 + j];
        }
        int i = 0;
        int j = 0;
        int k = left;
        while(i < leftIndex && j < rightIndex){
            if (leftA[i] <= rightA[j]) {
                nums[k] = leftA[i];
                //num = num.add( BigInteger.ONE);
                i++;

            } else {
                nums[k] = rightA[j];
                num = num.add(BigInteger.valueOf(leftIndex - i)); // I thought it would be one, but when testing,
                // it swaps the length of the left array minus the values that are greater, so i
                j++;
            }
            k++;
        }
        // adds any remaining values
        while (i < leftIndex){
            nums[k] = leftA[i];
            i++;
            k++;
        }
        while(j < rightIndex){
            nums[k] = rightA[j];
            j++;
            k++;
        }

        return num;
    }

    public static void main(String[] args) {
        System.out.println("Running test cases for num_swaps...\n");

        // Test Case 1: Already sorted list (no swaps needed)
        int[] sortedQueue = {1, 2, 3, 4, 5};
        System.out.println("Test Case 1: " + num_swaps(sortedQueue) + " (Expected: 0)");

        // Test Case 2: Reverse sorted list (maximum swaps needed)
        int[] reverseQueue = {5, 4, 3, 2, 1};
        System.out.println("Test Case 2: " + num_swaps(reverseQueue) + " (Expected: 10)");

        // Test Case 3: Random unsorted queue
        int[] randomQueue = {3, 1, 4, 2, 5};
        System.out.println("Test Case 3: " + num_swaps(randomQueue) + " (Expected: 3)");

        // Test Case 4: Single element queue (no swaps needed)
        int[] singleElementQueue = {42};
        System.out.println("Test Case 4: " + num_swaps(singleElementQueue) + " (Expected: 0)");

        // Test Case 5: Empty queue (no swaps needed)
        int[] emptyQueue = {};
        System.out.println("Test Case 5: " + num_swaps(emptyQueue) + " (Expected: 0)");

        System.out.println("\nAll test cases executed!");
    }
}
