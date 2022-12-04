package day_4;

import utils.FileUtil;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DayFour {

    public static void main(String[] args) throws FileNotFoundException {
        List<String> input = FileUtil.readToList("day_4.txt");

        System.out.println("Num of fully containing pairs: " + countFullyContainingPairs(input));
        System.out.println("Num of overlapping pairs: " + countOverlappingPairs(input));
    }

    public static int countFullyContainingPairs(List<String> input) {
        int numFullyContainingPairs = 0;
        for (String pair : input) {
            ElfPair elfPair = createElfPair(pair);
            if (elfPair.first.contains(elfPair.second) || elfPair.second.contains(elfPair.first())) {
                numFullyContainingPairs++;
            }
        }
        return numFullyContainingPairs;
    }

    public static int countOverlappingPairs(List<String> input) {
        int numOverlappingPairs = 0;
        for (String pair : input) {
            ElfPair elfPair = createElfPair(pair);
            Set<Integer> first = createSet(elfPair.first);
            Set<Integer> second = createSet(elfPair.second);
            first.retainAll(second);
            if (!first.isEmpty()) {
                numOverlappingPairs++;
            }
        }
        return numOverlappingPairs;
    }

    private static Set<Integer> createSet(String range) {
        HashSet<Integer> rangeSet = new HashSet<>();
        for (String number : range.split(",")) {
            if (!number.isEmpty()) {
                rangeSet.add(Integer.parseInt(number));
            }
        }
        return rangeSet;
    }

    static ElfPair createElfPair(String input) {
        String[] pair = input.split(",");
        String[] firstRange = pair[0].split("-");
        String[] secondRange = pair[1].split("-");
        return new ElfPair(createRangeString(firstRange), createRangeString(secondRange));
    }

    private static String createRangeString(String[] range) {
        StringBuilder rangeString = new StringBuilder().append(",");
        int start = Integer.parseInt(range[0]);
        int end = Integer.parseInt(range[1]);
        for (int i = start; i <= end; i++) {
            rangeString.append(i).append(",");
        }
        return rangeString.toString();
    }

    record ElfPair(String first, String second) {
    }
}
