package day_5;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class DayFiveTest {

    @Test
    void should_create_three_stacks() {
        List<Stack<Character>> stacks = DayFive.createStacks(getTestInput());

        assertThat(stacks.size()).isEqualTo(3);
        assertThat(stacks.get(0)).containsExactly('Z', 'N');
        assertThat(stacks.get(1)).containsExactly('M', 'C', 'D');
        assertThat(stacks.get(2)).containsExactly('P');
    }

    @Test
    void should_create_nine_stacks() {
        List<Stack<Character>> stacks = DayFive.createStacks(getLargerTestInput());

        assertThat(stacks.size()).isEqualTo(9);
        assertThat(stacks.get(0)).containsExactly('G', 'D', 'V', 'Z', 'J', 'S', 'B');
        assertThat(stacks.get(1)).containsExactly('Z', 'S', 'M', 'G', 'V', 'P');
        assertThat(stacks.get(2)).containsExactly('C', 'L', 'B', 'S', 'W', 'T', 'Q', 'F');
        assertThat(stacks.get(3)).containsExactly('H', 'J', 'G', 'W', 'M', 'R', 'V', 'Q');
        assertThat(stacks.get(4)).containsExactly('C', 'L', 'S', 'N', 'F', 'M', 'D');
        assertThat(stacks.get(5)).containsExactly('R', 'G', 'C', 'D');
        assertThat(stacks.get(6)).containsExactly('H', 'G', 'T', 'R', 'J', 'D', 'S', 'Q');
        assertThat(stacks.get(7)).containsExactly('P', 'F', 'V');
        assertThat(stacks.get(8)).containsExactly('D', 'R', 'S', 'T', 'J');
    }

    @ParameterizedTest
    @MethodSource
    void should_translate_command_in_three_numbers(String input, int[] expected) {
        int[] commands = DayFive.translateCommand(input);

        assertThat(commands.length).isEqualTo(3);
        assertThat(commands).containsExactly(expected);
    }

    private static Stream<Arguments> should_translate_command_in_three_numbers() {
        return Stream.of(
                Arguments.of("move 1 from 2 to 1", new int[]{1, 1, 0}),
                Arguments.of("move 3 from 1 to 3", new int[]{3, 0, 2}),
                Arguments.of("move 19 from 3 to 9", new int[]{19, 2, 8})
        );
    }

    @Test
    void should_rearrange_correctly() {
        List<Stack<Character>> stacks = DayFive.rearrange(getTestInput(), DayFive.CrateMover.CRATE_MOVER_9000);

        assertThat(stacks.size()).isEqualTo(3);
        assertThat(stacks.get(0)).containsExactly('C');
        assertThat(stacks.get(1)).containsExactly('M');
        assertThat(stacks.get(2)).containsExactly('P', 'D', 'N', 'Z');
    }

    @Test
    void should_find_correct_message() {
        String message = DayFive.findMessage(getTestInput(), DayFive.CrateMover.CRATE_MOVER_9000);

        assertThat(message).isEqualTo("CMZ");
    }

    @Test
    void should_find_correct_message_with_crate_mover_9001() {
        String message = DayFive.findMessage(getTestInput(), DayFive.CrateMover.CRATE_MOVER_9001);

        assertThat(message).isEqualTo("MCD");
    }

    private List<String> getTestInput() {
        List<String> input = new ArrayList<>();
        input.add("    [D]    ");
        input.add("[N] [C]    ");
        input.add("[Z] [M] [P]");
        input.add(" 1   2   3 ");
        input.add("");
        input.add("move 1 from 2 to 1");
        input.add("move 3 from 1 to 3");
        input.add("move 2 from 2 to 1");
        input.add("move 1 from 1 to 2");
        return input;
    }

    private List<String> getLargerTestInput() {
        List<String> input = new ArrayList<>();
        input.add("        [F] [Q]         [Q]        ");
        input.add("[B]     [Q] [V] [D]     [S]        ");
        input.add("[S] [P] [T] [R] [M]     [D]        ");
        input.add("[J] [V] [W] [M] [F]     [J]     [J]");
        input.add("[Z] [G] [S] [W] [N] [D] [R]     [T]");
        input.add("[V] [M] [B] [G] [S] [C] [T] [V] [S]");
        input.add("[D] [S] [L] [J] [L] [G] [G] [F] [R]");
        input.add("[G] [Z] [C] [H] [C] [R] [H] [P] [D]");
        input.add(" 1   2   3   4   5   6   7   8   9");
        input.add("");
        input.add("move 1 from 2 to 1");
        input.add("move 3 from 1 to 3");
        input.add("move 2 from 2 to 1");
        input.add("move 1 from 1 to 2");
        return input;
    }
}