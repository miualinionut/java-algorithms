package brevity.coursera.algorithmic_toolbox.week3;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Change {
    private static int getChange(int m) {
        int nrOfCoins = 0;
        int amountOfChangedMoney = 0;
        while (amountOfChangedMoney < m) {
            int amountToDenominate = m - amountOfChangedMoney;
            int denomination = getDenomination(amountToDenominate);
            int amountOfDenomination = amountToDenominate / denomination;

            amountOfChangedMoney = amountOfChangedMoney + (amountOfDenomination * denomination);
            nrOfCoins = nrOfCoins + amountOfDenomination;
            //printDenomination(denomination, amountOfDenomination);
        }
        return nrOfCoins;
    }

    private static void printDenomination(int denomination, int amountOfDenomination) {
        for(int i=0;i<=amountOfDenomination;i++) {
            System.out.print(denomination + " + ");
        }
        System.out.println();
    }

    private static int getDenomination(int ammountToDenominate) {
        int defaultDenomination = 1;
        List<Integer> denominations = Arrays.asList(10, 5, 1);
        for(Integer denomination: denominations) {
            if(denomination <= ammountToDenominate) {
                return denomination;
            }
        }
        return defaultDenomination;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        System.out.println(getChange(m));

    }
}

