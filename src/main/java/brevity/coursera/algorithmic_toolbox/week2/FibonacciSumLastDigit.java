package brevity.coursera.algorithmic_toolbox.week2;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class FibonacciSumLastDigit {
  private static long getFibonacciSumNaive(long n) {
    if (n <= 1)
      return n;

    long previous = 0;
    long current = 1;
    long sum = 1;

    for (long i = 0; i < n - 1; ++i) {
      long tmp_previous = previous;
      previous = current;
      current = tmp_previous + current;
      sum += current;
    }

    return sum % 10;
  }

  private static long getFibonacciSum(long n) {
    Map<Long, Long> pisanoSequence = getFibonacciPisanoPeriodicSequence(n, 10);
    int seqSize = pisanoSequence.size();
    long sum = 0;
    for (long i = 1; i <= (n % seqSize); i++) {
      sum = sum + pisanoSequence.get(i);
    }
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
    long n = scanner.nextLong();
    long s = getFibonacciSum(n);
    System.out.println(s);
  }
}

