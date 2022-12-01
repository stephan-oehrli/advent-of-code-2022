package day_1;

import utils.FileUtil;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DayOne {

    public static void main(String[] args) throws FileNotFoundException {
        List<String> input = FileUtil.readToList("day_1.txt");
        System.out.println("Most calories: " + findMostCalories(input));
        System.out.println("Most calories of top three: " + findSumOfTopThreeCalories(input));
    }

    public static int findMostCalories(List<String> input) {
        List<Integer> elvesCalories = getElvesCaloriesListFrom(input);
        return Collections.max(elvesCalories);
    }

    public static int findSumOfTopThreeCalories(List<String> input) {
        List<Integer> elvesCalories = getElvesCaloriesListFrom(input);
        Collections.sort(elvesCalories);
        Collections.reverse(elvesCalories);
        return elvesCalories.get(0) + elvesCalories.get(1) + elvesCalories.get(2);
    }

    private static List<Integer> getElvesCaloriesListFrom(List<String> input) {
        List<Integer> elvesCalories = new ArrayList<>();
        int count = 0;
        for (String entry : input) {
            if (entry.length() > 0) {
                count += Integer.parseInt(entry);
            } else {
                elvesCalories.add(count);
                count = 0;
            }
        }
        elvesCalories.add(count);
        return elvesCalories;
    }
}
