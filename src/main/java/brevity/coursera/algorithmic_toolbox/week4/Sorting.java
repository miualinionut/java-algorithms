package brevity.coursera.algorithmic_toolbox.week4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.StringTokenizer;

public class Sorting {
    private static Random random = new Random();

    private static PartitionDto partition3(int[] a, int leftIndex, int initialPivotIndex, int rightIndex) {
        PartitionDto partitionDto = new PartitionDto();

        int leftPivotIndex = moveSmallerToTheLeftOfPivot(a, leftIndex, initialPivotIndex, rightIndex);
        int rightPivotIndex = moveEqualToTheLeftOfPivot(a, leftIndex, leftPivotIndex, rightIndex);

        partitionDto.smallerSublistLeftIndex = leftIndex;
        partitionDto.smallerSublistRightIndex = leftPivotIndex - 1;

        partitionDto.equalsSublistLeftIndex = leftPivotIndex;
        partitionDto.equalsSublistRightIndex = rightPivotIndex;

        partitionDto.biggerSublistLeftIndex = rightPivotIndex + 1;
        partitionDto.biggerSublistRightIndex = rightIndex;

        return partitionDto;
    }

    private static int moveEqualToTheLeftOfPivot(int[] a, int leftIndex, int leftPivotIndex, int rightIndex) {
        int pivot = a[leftPivotIndex];

        int pivotOffset = 0;
        for (int i = leftPivotIndex + 1; i <= rightIndex; i++) {
            if (pivot == a[i]) {
                pivotOffset++;
                swap(a, leftPivotIndex + pivotOffset, i);
            }
        }

        int rightPivotIndex = leftPivotIndex + pivotOffset;
        return rightPivotIndex;
    }

    private static int moveSmallerToTheLeftOfPivot(int[] a, int leftIndex, int initialPivotIndex, int rightIndex) {
        int pivot = a[initialPivotIndex];

        int tempPivotIndex = leftIndex;
        swap(a, tempPivotIndex, initialPivotIndex);

        int pivotOffset = 0;
        for (int i = tempPivotIndex + 1; i <= rightIndex; i++) {
            if (a[i] < pivot) {
                pivotOffset++;
                swap(a, tempPivotIndex + pivotOffset, i);
            }
        }

        int newPivotIndex = tempPivotIndex + pivotOffset;
        swap(a, tempPivotIndex, newPivotIndex);

        return newPivotIndex;
    }

    public static class PartitionDto {
        private int smallerSublistLeftIndex;
        private int smallerSublistRightIndex;

        private int equalsSublistLeftIndex;
        private int equalsSublistRightIndex;

        private int biggerSublistLeftIndex;
        private int biggerSublistRightIndex;

    }

    private static int partition2(int[] a, int leftIndex, int rightIndex) {
        int pivot = a[leftIndex];
        int pivotIndex = leftIndex;
        for (int i = leftIndex + 1; i <= rightIndex; i++) {
            if (a[i] <= pivot) {
                pivotIndex++;
                swap(a, pivotIndex, i);
            }
        }
        swap(a, pivotIndex, leftIndex);
        return pivotIndex;
    }

    private static void swap(int[] a, int j, int i) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static void randomizedQuickSort(int[] a, int leftIndex, int rightIndex) {
        if (leftIndex >= rightIndex) {
            return;
        }
        int pivotIndex = random.nextInt(rightIndex - leftIndex + 1) + leftIndex;

        /* use partition2
        swap(a, pivotIndex, leftIndex);
        int m = partition2(a, leftIndex, rightIndex);
        randomizedQuickSort(a, leftIndex, m - 1);
        randomizedQuickSort(a, m + 1, rightIndex);
        */
        /* use partition3 */
        PartitionDto partitionDto = partition3(a, leftIndex, pivotIndex, rightIndex);
        randomizedQuickSort(a, leftIndex, partitionDto.smallerSublistRightIndex);
        randomizedQuickSort(a, partitionDto.biggerSublistLeftIndex, rightIndex);

    }

    public static void main(String[] args) {
        FastScanner scanner = new FastScanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        randomizedQuickSort(a, 0, n - 1);
        for (int i = 0; i < n; i++) {
            System.out.print(a[i] + " ");
        }
    }

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream stream) {
            try {
                br = new BufferedReader(new InputStreamReader(stream));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }
}

