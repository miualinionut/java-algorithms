package com.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Solution {

    // Complete the arrayManipulation function below.
    static long arrayManipulation(int n, int[][] queries) {
        long max = 0;
        long[] a = new long[n + 1];
        for (int i = 0; i < queries.length; i++) {
            int[] q = queries[i];
            int left = q[0];
            int right = q[1];
            long value = q[2];
            a[left] += value;
            if (right + 1 <= n) {
                a[right + 1] -= value;
            }
        }
        long x = 0;
        for (int i = 1; i <= n; i++) {
            x = x + a[i];
            if (max < x) {
                max = x;
            }

        }
        return max;
    }

    private static Scanner scanner;

    static {
        try {
            scanner = new Scanner(new File("C:\\development\\workspace\\java-algorithms\\_1_arrays\\src\\main\\resources\\small.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        String[] nm = scanner.nextLine().split(" ");

        int n = Integer.parseInt(nm[0]);

        int m = Integer.parseInt(nm[1]);

        int[][] queries = new int[m][3];

        for (int i = 0; i < m; i++) {
            String[] queriesRowItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int j = 0; j < 3; j++) {
                int queriesItem = Integer.parseInt(queriesRowItems[j]);
                queries[i][j] = queriesItem;
            }
        }

        long result = arrayManipulation(n, queries);

        System.out.println(String.valueOf(result));

        scanner.close();
    }
}
