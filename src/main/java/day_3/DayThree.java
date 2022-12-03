package day_3;

import utils.FileUtil;

import java.io.FileNotFoundException;
import java.util.List;

public class DayThree {

    public static void main(String[] args) throws FileNotFoundException {
        List<String> input = FileUtil.readToList("day_3.txt");

        System.out.println("Sum of items priorities: " + calculateSumOfPriorities(input));
        System.out.println("Sum of badges priorities: " + calculateSumOfGroupBadgesPriorities(input));
    }

    public static int calculateSumOfPriorities(List<String> input) {
        int sum = 0;
        for (String inventory : input) {
            String[] compartments = {
                    inventory.substring(0, inventory.length() / 2),
                    inventory.substring(inventory.length() / 2)
            };
            char item = findItemWhichAppearsInBoth(compartments);
            sum += translateToPriority(item);
        }
        return sum;
    }

    private static int translateToPriority(char item) {
        if (item >= 97) return item - 96;
        if (item >= 65) return item - 38;
        throw new IllegalStateException("Given item is not valid.");
    }

    private static char findItemWhichAppearsInBoth(String[] compartments) {
        for (char item : compartments[0].toCharArray()) {
            if (compartments[1].indexOf(item) != -1) {
                return item;
            }
        }
        throw new IllegalStateException("No item found that occurs in both compartments.");
    }

    public static int calculateSumOfGroupBadgesPriorities(List<String> input) {
        int sum = 0;
        for (int i = 0; i < input.size(); i = i + 3) {
            String[] group = {input.get(i), input.get(i + 1), input.get(i + 2)};
            char badge = findBadgeInGroup(group);
            sum += translateToPriority(badge);
        }
        return sum;
    }

    private static char findBadgeInGroup(String[] group) {
        for (char item : group[0].toCharArray()) {
            if (group[1].indexOf(item) != -1 && group[2].indexOf(item) != -1) {
                return item;
            }
        }
        throw new IllegalStateException("No badge found that occurs in each group rucksack");
    }
}
