package brevity.coursera.algorithmic_toolbox.week3;

import java.util.*;

import static java.lang.Math.min;
import static java.util.stream.Collectors.toList;

public class FractionalKnapsack {

    private static double getOptimalValue2(int capacity, int[] values, int[] weights) {
        List<LootDto> resources = LootDto.of(values, weights);
        Deque<LootDto> solutionCandidate = greedy(resources, capacity, capacity);

        return solutionCandidate.stream()
                .map(e -> e.getValuePerUnit() * e.getCapacity())
                .mapToDouble(e -> e)
                .sum();
    }


    public static Deque<LootDto> greedy(List<LootDto> resources, Integer localMax, Integer globalMax) {
        Deque<LootDto> stack = new ArrayDeque<>();
        LootDto nextValue = greedyChoice(stack, resources, localMax, globalMax);

        while (isSafeMove(resources, nextValue, globalMax)) {
            makeMove(stack, nextValue);
            reduceToSubproblem(resources, nextValue);
            nextValue = greedyChoice(stack, resources, localMax, globalMax);
        }

        return stack;
    }

    public static void makeMove(Deque<LootDto> stack, LootDto greedyChoice) {
        stack.push(greedyChoice);
    }

    public static boolean isSafeMove(List<LootDto> resources, LootDto greedyChoice, Integer globalMax) {
        return greedyChoice.getCapacity() > 0 && resources.contains(greedyChoice);
    }

    public static LootDto greedyChoice(Deque<LootDto> stack, List<LootDto> resources, int localMax, int globalMax) {
        Integer currentFilledCapacity = stack.stream()
                .mapToInt(LootDto::getCapacity)
                .sum();

        LootDto loot = getHighestValueResource(resources);
        Integer availableCapacity = globalMax - currentFilledCapacity;
        Integer lootCapacity = min(availableCapacity, loot.getWeight());
        return new LootDto(loot.getValue(), loot.getWeight(), lootCapacity);
    }

    public static void reduceToSubproblem(List<LootDto> resources, LootDto greedyChoice) {
        List<LootDto> outdatedResources = resources.stream()
                .filter(resource -> resource.equals(greedyChoice))
                .collect(toList());
        resources.removeAll(outdatedResources);
    }

    private static LootDto getHighestValueResource(List<LootDto> loots) {
        return loots.stream()
                .max(LootDto.compareByValuePerUnit)
                .orElse(new LootDto(0, 0, 0));
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int capacity = scanner.nextInt();
        int[] values = new int[n];
        int[] weights = new int[n];
        for (int i = 0; i < n; i++) {
            values[i] = scanner.nextInt();
            weights[i] = scanner.nextInt();
        }
        System.out.println(getOptimalValue2(capacity, values, weights));
    }

}

class LootDto {
    private final int value;
    private final int weight;
    private final int capacity;

    public LootDto(int value, int weight, int capacity) {
        this.value = value;
        this.weight = weight;
        this.capacity = capacity;
    }

    public int getValue() {
        return value;
    }

    public int getWeight() {
        return weight;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getValuePerUnit() {
        return ((double) value) / weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LootDto lootDto = (LootDto) o;
        return value == lootDto.value &&
                weight == lootDto.weight;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, weight);
    }

    public static List<LootDto> of(int[] values, int[] weights) {
        List<LootDto> list = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            int initialCapacity = weights[i];
            list.add(new LootDto(values[i], weights[i], initialCapacity));
        }
        return list;
    }

    public static Comparator<LootDto> compareByValuePerUnit = (t0, t1) -> {
        if (t0.getValuePerUnit() < t1.getValuePerUnit()) {
            return -1;
        }
        if (t0.getValuePerUnit() > t1.getValuePerUnit()) {
            return 1;
        }
        return 0;
    };
}
