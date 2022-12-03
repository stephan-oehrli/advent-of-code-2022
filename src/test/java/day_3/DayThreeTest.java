package day_3;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DayThreeTest {

    @Test
    void should_calculate_sum_of_priorities() {
        List<String> input = getTestInput();

        int sum = DayThree.calculateSumOfPriorities(input);

        assertThat(sum).isEqualTo(157);
    }

    @Test
    void should_calculate_sum_of_group_priorities() {
        List<String> input = getTestInput();

        int sum = DayThree.calculateSumOfGroupBadgesPriorities(input);

        assertThat(sum).isEqualTo(70);
    }

    private List<String> getTestInput() {
        List<String> input = new ArrayList<>();
        input.add("vJrwpWtwJgWrhcsFMMfFFhFp");
        input.add("jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL");
        input.add("PmmdzqPrVvPwwTWBwg");
        input.add("wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn");
        input.add("ttgJtRGJQctTZtZT");
        input.add("CrZsJsPPZsGzwwsLwLmpwMDw");
        return input;
    }


}