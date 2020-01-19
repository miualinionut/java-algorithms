package brevity.coursera.algorithmic_toolbox.week3;

import java.util.*;
import java.util.stream.IntStream;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

public class DotProduct {
    private static long maxDotProduct(int[] a, int[] b) {
        List<Integer> listOfProfitPerClicks = IntStream.range(0, a.length)
                .mapToObj(i -> a[i])
                .collect(toList());
        List<Integer> listOfAvgNumberOfClicksPerDays = IntStream.range(0, b.length)
                .mapToObj(i -> b[i])
                .collect(toList());
        Resources resources = new Resources(listOfProfitPerClicks, listOfAvgNumberOfClicksPerDays);
        Deque<Pair> solutionCandidate = greedy(resources, null, null);

        long result = solutionCandidate.stream()
                .map(e -> ((long) e.getProfitPerClick()) * e.getAvgNumberOfClicksPerDay())
                .mapToLong(e -> e)
                .sum();

        return result;
    }

    public static Deque<Pair> greedy(Resources resources, Integer localMax, Integer globalMax) {
        Deque<Pair> stack = new ArrayDeque<>();

        Pair nextValue = greedyChoice(stack, resources, localMax, globalMax);
        while (isSafeMove(resources, nextValue, globalMax)) {
            makeMove(stack, nextValue);
            reduceToSubproblem(resources, nextValue);
            nextValue = greedyChoice(stack, resources, localMax, globalMax);
        }

        return stack;
    }

    public static void makeMove(Deque<Pair> stack, Pair greedyChoice) {
        stack.push(greedyChoice);
    }

    public static boolean isSafeMove(Resources resources, Pair greedyChoice, Integer globalMax) {
        return nonNull(greedyChoice);
    }

    public static Pair greedyChoice(Deque<Pair> stack, Resources resources, Integer localMax, Integer globalMax) {
        Long currentMax = stack.stream()
                .map(resource -> resource.getProfitPerClick() * resource.getAvgNumberOfClicksPerDay())
                .mapToLong(e -> e)
                .sum();
        return getHighestValueResource(resources, currentMax);
    }

    private static Pair getHighestValueResource(Resources resources, Long currentMax) {
        OptionalInt maxProfitPerClick = resources.listOfProfitPerClicks.stream()
                .mapToInt(e -> e)
                .max();
        OptionalInt maxAvgNumberOfClicksPerDay = resources.listOfAvgNumberOfClicksPerDays.stream()
                .mapToInt(e -> e)
                .max();
        if (maxAvgNumberOfClicksPerDay.isPresent() && maxProfitPerClick.isPresent()) {
            return new Pair(maxProfitPerClick.getAsInt(), maxAvgNumberOfClicksPerDay.getAsInt());
        } else {
            return null;
        }
    }

    public static void reduceToSubproblem(Resources resources, Pair greedyChoice) {
        resources.listOfProfitPerClicks.remove(greedyChoice.getProfitPerClick());
        resources.listOfAvgNumberOfClicksPerDays.remove(greedyChoice.getAvgNumberOfClicksPerDay());
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        int[] b = new int[n];
        for (int i = 0; i < n; i++) {
            b[i] = scanner.nextInt();
        }
        System.out.println(maxDotProduct(a, b));
    }
}

class Resources {
    List<Integer> listOfProfitPerClicks;
    List<Integer> listOfAvgNumberOfClicksPerDays;

    public Resources(List<Integer> listOfProfitPerClicks, List<Integer> listOfAvgNumberOfClicksPerDays) {
        this.listOfProfitPerClicks = listOfProfitPerClicks;
        this.listOfAvgNumberOfClicksPerDays = listOfAvgNumberOfClicksPerDays;
    }

    public List<Integer> getListOfProfitPerClicks() {
        return listOfProfitPerClicks;
    }

    public List<Integer> getListOfAvgNumberOfClicksPerDays() {
        return listOfAvgNumberOfClicksPerDays;
    }
}

class Pair {
    private Integer profitPerClick;
    private Integer avgNumberOfClicksPerDay;

    public Pair(Integer profitPerClick, Integer avgNumberOfClicksPerDay) {
        this.profitPerClick = profitPerClick;
        this.avgNumberOfClicksPerDay = avgNumberOfClicksPerDay;
    }

    public Integer getProfitPerClick() {
        return profitPerClick;
    }

    public Integer getAvgNumberOfClicksPerDay() {
        return avgNumberOfClicksPerDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return Objects.equals(profitPerClick, pair.profitPerClick) &&
                Objects.equals(avgNumberOfClicksPerDay, pair.avgNumberOfClicksPerDay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profitPerClick, avgNumberOfClicksPerDay);
    }
}