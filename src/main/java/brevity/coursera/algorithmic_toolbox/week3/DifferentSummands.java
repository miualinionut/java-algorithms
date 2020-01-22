package brevity.coursera.algorithmic_toolbox.week3;

import java.util.*;
import java.util.stream.Collectors;

public class DifferentSummands {
    private static List<Integer> optimalSummands(int n) {
        ProblemData problemData = new ProblemData();
        problemData.target = n;
        problemData.current = n;
        List<Integer> summands = greedy(problemData).stream().collect(Collectors.toList());
        summands.sort(Integer::compareTo);
        return summands;
    }

    public static Deque<Integer> greedy(ProblemData problemData) {
        Deque<Integer> stack = new ArrayDeque<>();
        Integer nextValue = greedyChoice(stack, problemData);
        while (isSafeMove(stack, problemData, nextValue)) {
            makeMove(stack, nextValue);
            reduceToSubprobem(stack, problemData);
            nextValue = greedyChoice(stack, problemData);
        }
        return stack;
    }

    private static void reduceToSubprobem(Deque<Integer> stack, ProblemData problemData) {
        problemData.current -= stack.element();
    }

    private static boolean isSafeMove(Deque<Integer> stack, ProblemData problemData, Integer nextValue) {
        return nextValue != 0;
    }

    private static Integer greedyChoice(Deque<Integer> stack, ProblemData problemData) {
        int next = stack.isEmpty() ? 1 : stack.element() + 1;
        if(next * 2 < problemData.current) {
            return next;
        } else {
            return problemData.current;
        }
    }

    private static void makeMove(Deque<Integer> stack, Integer nextValue) {
        stack.push(nextValue);
    }

    public static class ProblemData {
        Integer target;
        Integer current;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Integer> summands = optimalSummands(n);
        System.out.println(summands.size());
        for (Integer summand : summands) {
            System.out.print(summand + " ");
        }
    }
}

