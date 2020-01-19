package brevity.coursera.algorithmic_toolbox.week2;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.LongStream;

public class FibonacciPartialSum {
  private static long getFibonacciPartialSumNaive(long from, long to) {
    long sum = 0;

    long current = 0;
    long next = 1;

    for (long i = 0; i <= to; ++i) {
      if (i >= from) {
        sum += current;
      }

      long new_current = next;
      next = next + current;
      current = new_current;
    }

    return sum % 10;
  }

  private static long getFibonacciPartialSum(long from, long to) {
    Map<Long, Long> pisanoSequence = getFibonacciPisanoPeriodicSequence(to, 10);
    long seqSize = pisanoSequence.size();
    long nrOfPisanoSequencePeriodsBetweenTheFromAndTo = (to - from) / seqSize;
    long startIndexOfLastPisanoSequence = from + (nrOfPisanoSequencePeriodsBetweenTheFromAndTo * seqSize);
    long sumOfRemainingPisanoSequenceNumbersUntilTo = LongStream.rangeClosed(startIndexOfLastPisanoSequence, to)
      .map(fibonacciIndex -> fibonacciIndex % seqSize)
      .map(pisanoSequenceIndex -> pisanoSequence.get(pisanoSequenceIndex))
      .sum();
    long sumOfPisanoSequence = pisanoSequence.values().stream()
      .mapToLong(e -> e)
      .sum();
    long sum = nrOfPisanoSequencePeriodsBetweenTheFromAndTo * sumOfPisanoSequence + sumOfRemainingPisanoSequenceNumbersUntilTo;
    return sum % 10;
  }

  private static Map<Long, Long> getFibonacciPisanoPeriodicSequence(long n, long divider) {
    class FibonacciDto {
      Long current;
      Long previous;

      public FibonacciDto(Long current, Long previous) {
        this.current = current;
        this.previous = previous;
      }
    }
    Map<Long, Long> pisanoSequence = new HashMap<>();
    pisanoSequence.put(0L, 0L);
    pisanoSequence.put(1L, 1L);

    Predicate<FibonacciDto> isPeriodic = fibonacciDto -> fibonacciDto.current == 1L && fibonacciDto.previous == 0L;
    BiPredicate<FibonacciDto, Long> isPeriodicSequence = (fibonacciDto, fibonacciIndex) -> {
      if (isPeriodic.test(fibonacciDto)) {
        long index = fibonacciIndex + 1;
        pisanoSequence.remove(index);
        return true;
      } else {
        long index = fibonacciIndex + 2;
        pisanoSequence.put(index, fibonacciDto.current);
        return false;
      }
    };

    if (n <= 1) {
      pisanoSequence.put(n, n);
    }
    long previous = 0;
    long current = 1;
    for (long i = 0; i < n - 1; ++i) {
      long tmp_previous = previous;
      previous = current;
      current = (tmp_previous + current) % divider;
      if (isPeriodicSequence.test(new FibonacciDto(current, previous), i)) {
        break;
      }
    }

    return pisanoSequence;
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    long from = scanner.nextLong();
    long to = scanner.nextLong();
    System.out.println(getFibonacciPartialSum(from, to));
  }
}

