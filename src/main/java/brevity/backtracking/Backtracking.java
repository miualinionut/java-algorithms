package brevity.backtracking;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class Backtracking {

    void backtracking(int stackSize) {
        Deque<Integer> stack = new ArrayDeque<>();
        increase(stack);

        do {
            incrementCurrentHead(stack);
            if (isCurrentHeadValid(stack, stackSize)) {
                if (isValidPartialSolution(stack)) {
                    if (isCompleteSolution(stack, stackSize)) {
                        display(stack);
                    } else {
                        increase(stack);
                    }
                }
            } else {
                decrease(stack);
            }
        } while (!stack.isEmpty());

    }

    void decrease(Deque<Integer> stack) {
        stack.removeLast();
    }

    void increase(Deque<Integer> stack) {
        stack.addLast(0);
    }

    void display(Deque<Integer> stack) {
        stack.forEach(e -> System.out.print(e + " "));
        System.out.println();
    }

    boolean isCompleteSolution(Deque<Integer> stack, int stackSize) {
        return stack.size() == stackSize;
    }

    boolean isValidPartialSolution(Deque<Integer> stack) {
        return isHeadUnique(stack);
    }

    boolean isCurrentHeadValid(Deque<Integer> stack, Integer maxValidValue) {
        return stack.getLast() <= maxValidValue;
    }

    void incrementCurrentHead(Deque<Integer> stack) {
        stack.addLast(stack.removeLast() + 1);
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
        new Backtracking().backtracking(5);
    }

}
