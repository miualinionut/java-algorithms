package brevity.coursera.algorithmic_toolbox.week3;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

public class CarFueling {

    static int computeMinRefills(int dist, int tank, int[] stops) {
        Integer localMax = tank;
        Integer globalMax = dist;
        List<Integer> resources = stream(stops).boxed().collect(toList());
        Deque<Integer> solutionCandidate = greedy(resources, localMax, globalMax);

        Integer maxCarPosition = solutionCandidate.element() + localMax;
        if (maxCarPosition < dist) {
            return -1;
        } else {
            int nrOfRefills = solutionCandidate.size() - 1;
            return nrOfRefills;
        }
    }

    public static Deque<Integer> greedy(List<Integer> resources, Integer localMax, Integer globalMax) {
        Deque<Integer> stack = new ArrayDeque<>();
        Integer nextValue = nextGreedyChoice(stack, resources, localMax, globalMax);

        while (isSafeMove(resources, nextValue)) {
            makeMove(stack, nextValue);
            reduceToSubproblem(resources, nextValue);
            nextValue = nextGreedyChoice(stack, resources, localMax, globalMax);
        }

        return stack;
    }

    public static void makeMove(Deque<Integer> stack, Integer greedyChoice) {
        stack.push(greedyChoice);
    }

    public static boolean isSafeMove(List<Integer> resources, Integer greedyChoice) {
        return greedyChoice == 0 || resources.contains(greedyChoice);
    }

    public static Integer nextGreedyChoice(Deque<Integer> stack, List<Integer> resources, Integer localMax, Integer globalMax) {
        if (stack.isEmpty()) {
            return 0;
        }
        Integer current = stack.element();
        Integer currentMax = current + localMax;
        if (currentMax >= globalMax) {
            return globalMax;
        } else {
            return resources.stream()
                    .filter(value -> value <= currentMax)
                    .mapToInt(e -> e)
                    .max()
                    .orElse(currentMax);
        }
    }

    public static void reduceToSubproblem(List<Integer> resources, Integer greedyChoice) {
        List<Integer> outdatedResources = resources.stream()
                .filter(resource -> resource <= greedyChoice)
                .collect(toList());
        resources.removeAll(outdatedResources);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int dist = scanner.nextInt();
        int tank = scanner.nextInt();
        int n = scanner.nextInt();
        int stops[] = new int[n];
        for (int i = 0; i < n; i++) {
            stops[i] = scanner.nextInt();
        }

        System.out.println(computeMinRefills(dist, tank, stops));
    }
}
