package day_6;

import utils.FileUtil;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class DaySix {

    public static void main(String[] args) throws FileNotFoundException {
        String input = FileUtil.readToString("day_6.txt");

        System.out.println("First packet marker at: " + findFirstMarker(input, 4));
        System.out.println("First packet marker at: " + findFirstMarker(input, 14));
    }

    public static int findFirstMarker(String input, int numOfDistinctChars) {
        for (int i = numOfDistinctChars; i < input.length(); i++) {
            if (!hasRepetition(input.substring(i - numOfDistinctChars, i))) {
                return i;
            }
        }
        return 0;
    }

    private static boolean hasRepetition(String substring) {
        Map<Character, Integer> charCounter = new HashMap<>();
        for (char c : substring.toCharArray()) {
            charCounter.merge(c, 1, Integer::sum);
        }
        for (Integer value : charCounter.values()) {
            if (value > 1) {
                return true;
            }
        }
        return false;
    }
}
