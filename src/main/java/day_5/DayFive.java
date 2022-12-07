package day_5;

import utils.FileUtil;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class DayFive {

    public static void main(String[] args) throws FileNotFoundException {
        List<String> input = FileUtil.readToList("day_5.txt");

        System.out.println("Message with CrateMover9000 is: " + findMessage(input, CrateMover.CRATE_MOVER_9000));
        System.out.println("Message with CrateMover9001 is: " + findMessage(input, CrateMover.CRATE_MOVER_9001));
    }

    public static String findMessage(List<String> input, CrateMover crateMover) {
        List<Stack<Character>> stacks = rearrange(input, crateMover);

        StringBuilder result = new StringBuilder();
        for (Stack<Character> chars : stacks) {
            result.append(chars.pop());
        }

        return result.toString();
    }

    static List<Stack<Character>> rearrange(List<String> input, CrateMover crateMover) {
        List<Stack<Character>> stacks = createStacks(input);

        for (String command : input) {
            if (crateMover == CrateMover.CRATE_MOVER_9000) {
                applyCommandWith9000(stacks, command);
            } else {
                applyCommandWith9001(stacks, command);
            }
        }

        return stacks;
    }

    private static void applyCommandWith9000(List<Stack<Character>> stacks, String command) {
        if (command.startsWith("move ")) {
            int[] instructions = translateCommand(command);
            Stack<Character> from = stacks.get(instructions[1]);
            Stack<Character> to = stacks.get(instructions[2]);
            for (int i = 0; i < instructions[0]; i++) {
                to.push(from.pop());
            }
        }
    }

    private static void applyCommandWith9001(List<Stack<Character>> stacks, String command) {
        if (command.startsWith("move ")) {
            int[] instructions = translateCommand(command);
            Stack<Character> from = stacks.get(instructions[1]);
            Stack<Character> to = stacks.get(instructions[2]);
            List<Character> characters = new ArrayList<>();
            for (int i = 0; i < instructions[0]; i++) {
                characters.add(from.pop());
            }
            for (int i = characters.size() - 1; i >= 0; i--) {
                to.push(characters.get(i));
            }
        }
    }

    static List<Stack<Character>> createStacks(List<String> testInput) {
        List<Stack<Character>> stacks = new ArrayList<>();

        int numOfStacks = (testInput.get(0).length() + 1) / 4;
        for (int i = 0; i < numOfStacks; i++) {
            stacks.add(new Stack<>());
        }

        for (String line : testInput) {
            if (line.isEmpty()) {
                break;
            }
            for (char c : line.replaceAll("[^A-Z]", "").toCharArray()) {
                int index = line.indexOf(c) / 4;
                line = line.replaceFirst(String.valueOf(c), "-");
                stacks.get(index).add(c);
            }
        }

        for (Stack<Character> stack : stacks) {
            Collections.reverse(stack);
        }

        return stacks;
    }

    static int[] translateCommand(String input) {
        String[] split = input.split("move | from | to ");
        return new int[]{Integer.parseInt(split[1]), Integer.parseInt(split[2]) - 1, Integer.parseInt(split[3]) - 1};
    }

    enum CrateMover {
        CRATE_MOVER_9000,
        CRATE_MOVER_9001
    }
}
