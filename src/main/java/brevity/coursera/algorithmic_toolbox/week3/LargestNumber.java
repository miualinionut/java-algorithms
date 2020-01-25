package brevity.coursera.algorithmic_toolbox.week3;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static brevity.coursera.algorithmic_toolbox.week3.LargestNumber.ProblemData.largestFirst;
import static java.util.stream.Collectors.toList;

public class LargestNumber {

    private static String largestNumber(String[] a) {
        List<String> resources = Stream.of(a).sorted(largestFirst).collect(toList());
        ProblemData problemData = new ProblemData(resources);
        Deque<String> largestNumber = greedy(problemData);

        return largestNumber.stream()
                .collect(Collectors.joining());
    }

    private static Deque<String> greedy(ProblemData problemData) {
        Deque<String> stack = new ArrayDeque<>();
        String nextValue = greedyChoice(stack, problemData);
        while (isSafeMove(stack, problemData, nextValue)) {
            makeMove(stack, nextValue);
            reduceToSubproblem(stack, problemData);
            nextValue = greedyChoice(stack, problemData);
        }
        return stack;
    }

    private static void reduceToSubproblem(Deque<String> stack, ProblemData problemData) {
        String current = stack.element();
        problemData.resources.remove(current);
    }

    private static void makeMove(Deque<String> stack, String nextValue) {
        stack.push(nextValue);
    }

    private static boolean isSafeMove(Deque<String> stack, ProblemData problemData, String nextValue) {
        return Objects.nonNull(nextValue);
    }

    private static String greedyChoice(Deque<String> stack, ProblemData problemData) {
        return problemData.resources.stream()
                .findFirst()
                .orElse(null);
    }

    public static class ProblemData {
        private List<String> resources;

        public ProblemData(List<String> resources) {
            this.resources = resources;
        }

        public List<String> getResources() {
            return resources;
        }

        public static Comparator<String> largestFirst = (t0, t1) -> {
            String t0t1 = t0 + t1;
            String t1t0 = t1 + t0;
            return t0t1.compareTo(t1t0);
        };
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        String[] a = new String[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.next();
        }
        System.out.println(largestNumber(a));
    }
}

