/* Save this in a file called Main.java to compile and test it */

/* Do not add a package declaration */

import java.util.*;
import java.util.stream.IntStream;

/* You may add any imports here, if you wish, but only from the
   standard library */

public class Main {
    public static int processArray(ArrayList<Integer> array) {
        /*
         * Modify this function to process `array` as indicated
         * in the question. At the end, return the appropriate
         * value.
         *
         * Please create appropriate classes, and use appropriate
         * data structures as necessary.
         *
         * Do not print anything in this method
         *
         * Submit this entire program (not just this function)
         * as your answer
         */

        List<Integer> intermediateLosingStreak = new ArrayList<>();
        Map<Integer, List<Integer>> losingStreakMap = new HashMap<>();

        IntStream.rangeClosed(0, array.size() - 1)
                .skip(1)
                .forEach(currentIndex -> {
                    if (currentIndex == (array.size() - 1)) {
                        populateLosingStreak(array, intermediateLosingStreak, losingStreakMap, currentIndex);

                    } else {

                        if (array.get(currentIndex) >= array.get(currentIndex + 1)) {
                            intermediateLosingStreak.add(array.get(currentIndex));
                        } else {
                            populateLosingStreak(array, intermediateLosingStreak, losingStreakMap, currentIndex);
                        }
                    }

                });

        int maxLosingStreak = losingStreakMap.keySet()
                .stream()
                .mapToInt(v -> v)
                .max().orElse(1);

        if (maxLosingStreak == 1) {
            return 0;
        }

        List<Integer> maxLosingStreakList = losingStreakMap.get(maxLosingStreak);
        return maxLosingStreakList.get(0) - maxLosingStreakList.get((maxLosingStreakList.size() - 1));

    }

    private static void populateLosingStreak(ArrayList<Integer> array, List<Integer> intermediateLosingStreak, Map<Integer, List<Integer>> losingStreakMap, int currentIndex) {
        intermediateLosingStreak.add(array.get(currentIndex));
        List<Integer> copyIntermediateLosingStreak = new ArrayList<>();
        copyIntermediateLosingStreak.addAll(intermediateLosingStreak);

        losingStreakMap.put(copyIntermediateLosingStreak.size(), copyIntermediateLosingStreak);

        intermediateLosingStreak.clear();
    }

    public static void main(String[] args) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            int num = in.nextInt();
            if (num < 0)
                break;
            arrayList.add(new Integer(num));
        }
        int result = processArray(arrayList);
        System.out.println(result);
    }
}
