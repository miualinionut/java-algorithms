package brevity.coursera.algorithmic_toolbox.week4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class MajorityElement {
    private static int getMajorityElement(int[] a, int left, int right) {
        int major = major(a, left, right - 1);
//        System.out.println("majority element: " + major);
        return major;
    }

    private static int major(int[] a, int left, int right) {
        if (left == right) {
            return a[left];
        } else {
            int mid = (left + right) / 2;
            int leftMajority = major(a, left, mid);
            int rightMajority = major(a, mid + 1, right);

            return merge(a, left, right, leftMajority, rightMajority);
        }
    }

    private static int merge(int[] a, int left, int right, int leftMajority, int rightMajority) {
        int occurrencesOfLeftMajority = leftMajority == -1 ? 0 : count(leftMajority, a, left, right);
        int occurrencesOfRightMajority = rightMajority == -1 ? 0 : count(rightMajority, a, left, right);

        int moreThenHalf = (int) Math.ceil((right - left) / 2.0);
        if (occurrencesOfLeftMajority > moreThenHalf) {
            return leftMajority;
        } else if (occurrencesOfRightMajority > moreThenHalf) {
            return rightMajority;
        } else {
            return -1;
        }
    }

    private static int count(int majority, int[] a, int left, int right) {
        int occurrences = 0;
        for (int i = left; i <= right; i++) {
            if (a[i] == majority) {
                occurrences++;
            }
        }
        return occurrences;
    }

    public static void main(String[] args) {
        FastScanner scanner = new FastScanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        if (getMajorityElement(a, 0, a.length) != -1) {
            System.out.println(1);
        } else {
            System.out.println(0);
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

