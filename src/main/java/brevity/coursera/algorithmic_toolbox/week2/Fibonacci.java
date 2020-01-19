package brevity.coursera.algorithmic_toolbox.week2;

import java.util.Scanner;

public class Fibonacci {
    private static long calc_fib(int n) {
        long fibN_2 = 0;
        long fibN_1 = 1;
        long fibN = n <= 1 ? n : 0;
        for (int i = 2; i <= n; i++) {
            fibN = fibN_1 + fibN_2;
            fibN_2 = fibN_1;
            fibN_1 = fibN;
        }
        return fibN;
    }

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();

        System.out.println(calc_fib(n));
    }
}
