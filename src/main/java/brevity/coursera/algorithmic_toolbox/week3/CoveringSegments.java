package brevity.coursera.algorithmic_toolbox.week3;

import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class CoveringSegments {

    private static int[] optimalPoints(Segment[] segments) {

        List<Segment> resources = IntStream.range(0, segments.length)
                .mapToObj(i -> segments[i])
                .sorted(Comparator.comparingInt(Segment::getEnd))
                .collect(toList());

        Deque<Integer> solutionCandidate = greedy(resources);

        return solutionCandidate.stream()
                .mapToInt(Integer::intValue)
                .sorted()
                .toArray();
    }

    public static Deque<Integer> greedy(List<Segment> resources) {
        Deque<Integer> stack = new ArrayDeque<>();
        Integer nextValue = greedyChoice(stack, resources);

        while (isSafeMove(resources, nextValue)) {
            makeMove(stack, nextValue);
            reduceToSubproblem(resources, nextValue);
            nextValue = greedyChoice(stack, resources);
        }

        return stack;
    }

    public static void makeMove(Deque<Integer> stack, Integer greedyChoice) {
        stack.push(greedyChoice);
    }

    public static boolean isSafeMove(List<Segment> resources, Integer greedyChoice) {
        return Objects.nonNull(greedyChoice);
    }

    public static Integer greedyChoice(Deque<Integer> stack, List<Segment> resources) {
        Integer nonSafeValue = null;
        Integer greedyChoiceValue = nonSafeValue;

        Optional<Segment> segment = resources.stream().findFirst();
        if (segment.isPresent()) {
            greedyChoiceValue = getHighestValueResource(resources, segment.get());
        }

        return greedyChoiceValue;
    }

    private static Integer getHighestValueResource(List<Segment> resources, Segment current) {
        return current.end;
    }

    public static void reduceToSubproblem(List<Segment> resources, Integer greedyChoice) {
        List<Segment> outdatedResources = resources.stream()
                .filter(r -> r.start <= greedyChoice && greedyChoice <= r.end)
                .collect(toList());
        resources.removeAll(outdatedResources);
    }

    public static class Segment {
        int start, end;

        Segment(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public int getEnd() {
            return end;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Segment[] segments = new Segment[n];
        for (int i = 0; i < n; i++) {
            int start, end;
            start = scanner.nextInt();
            end = scanner.nextInt();
            segments[i] = new Segment(start, end);
        }
        int[] points = optimalPoints(segments);
        System.out.println(points.length);
        for (int point : points) {
            System.out.print(point + " ");
        }
    }
}
 
