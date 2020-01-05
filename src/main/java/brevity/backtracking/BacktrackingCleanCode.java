package brevity.backtracking;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class BacktrackingCleanCode {

    void backtracking(int stackSize) {
        Deque<Integer> stack = new ArrayDeque<>();
        Integer maxValidValue = stackSize;
        increase(stack);

        do {
            incrementCurrentHead(stack);
            if (isCurrentHeadValid(stack, maxValidValue)) {
                consumePartialSolution(stack, stackSize);
            } else {
                decrease(stack);
            }
        } while (!stack.isEmpty());

    }

    void consumePartialSolution(Deque<Integer> stack, int stackSize) {
        if (isValidPartialSolution(stack)) {
            consumeCompleteSolution(stack, stackSize);
        }
    }

    void consumeCompleteSolution(Deque<Integer> stack, int stackSize) {
        if (isCompleteSolution(stack, stackSize)) {
            display(stack);
        } else {
            increase(stack);
        }
    }

    private void display(Deque<Integer> stack) {
        stack.forEach(e -> System.out.print(e + " "));
        System.out.println();
    }

    void increase(Deque<Integer> stack) { stack.addLast(0); }
    void decrease(Deque<Integer> stack) { stack.removeLast(); }

    void incrementCurrentHead(Deque<Integer> stack) {
        stack.addLast(stack.removeLast() + 1);
    }

    boolean isCurrentHeadValid(Deque<Integer> stack, int maxValidValue) {
        return stack.getLast() <= maxValidValue;
    }

    boolean isCompleteSolution(Deque<Integer> stack, int stackSize) {
        return stack.size() == stackSize;
    }

    boolean isValidPartialSolution(Deque<Integer> stack) {
        return isHeadUnique(stack);
    }

    boolean isHeadUnique(Deque<Integer> stack) {
        int currentIndex = 0;
        int beforeLast = stack.size() - 1;
        Iterator<Integer> iterator = stack.iterator();
        while (currentIndex++ < beforeLast) {
            if (iterator.next().compareTo(stack.getLast()) == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        new BacktrackingCleanCode().backtracking(5);
    }

}
