package brevity.coursera.algorithmic_toolbox.week3;

import java.util.*;

import static java.lang.Math.min;

public class FractionalKnapsack {
    private static double getOptimalValue(int capacity, int[] values, int[] weights) {
        double value = 0;

        int availableCapacity = capacity;
        List<LootDto> loots = LootDto.getSortedByValuePerUnit(values, weights);
        while (knapsackIsNotFull(availableCapacity) && moreLootsAvailable(loots)) {
            LootDto lootWithHighestValue = getHighestValueLoot(loots);
            int lootCapacity = min(availableCapacity, lootWithHighestValue.weight);
            double lootValue = lootWithHighestValue.valuePerUnit * lootCapacity;

            value += lootValue;
            availableCapacity -= lootCapacity;
            loots.remove(lootWithHighestValue);
        }

        return value;
    }

    private static boolean moreLootsAvailable(List<LootDto> loots) {
        return !loots.isEmpty();
    }

    private static boolean knapsackIsNotFull(int availableCapacity) {
        return availableCapacity > 0;
    }

    private static LootDto getHighestValueLoot(List<LootDto> loots) {
        return loots.get(0);
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
        System.out.println(getOptimalValue(capacity, values, weights));
    }

    public static class LootDto {
        int value;
        int weight;
        double valuePerUnit;

        public LootDto(int value, int weight, double valuePerUnit) {
            this.value = value;
            this.weight = weight;
            this.valuePerUnit = valuePerUnit;
        }

        public static List<LootDto> getSortedByValuePerUnit(int[] values, int[] weights) {
            List<LootDto> list = new ArrayList<>();
            for (int i = 0; i < values.length; i++) {
                double valuePerUnit = ((double) values[i]) / weights[i];
                list.add(new LootDto(values[i], weights[i], valuePerUnit));
            }
            Collections.sort(list, Collections.reverseOrder((t0, t1) -> {
                if (t0.valuePerUnit < t1.valuePerUnit) {
                    return -1;
                }
                if (t0.valuePerUnit > t1.valuePerUnit) {
                    return 1;
                }
                return 0;
            }));
            return list;
        }
    }
} 
