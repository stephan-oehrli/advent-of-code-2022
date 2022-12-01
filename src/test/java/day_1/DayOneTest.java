package day_1;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DayOneTest {

    @Test
    void should_find_most_calories() {
        int mostCalories = DayOne.findMostCalories(getTestInput());

        assertThat(mostCalories).isEqualTo(24000);
    }

    @Test
    void should_find_sum_of_top_three_calories() {
        int mostCalories = DayOne.findSumOfTopThreeCalories(getTestInput());

        assertThat(mostCalories).isEqualTo(45000);
    }

    private List<String> getTestInput() {
        List<String> input = new ArrayList<>();
        input.add("1000");
        input.add("2000");
        input.add("3000");
        input.add("");
        input.add("4000");
        input.add("");
        input.add("5000");
        input.add("6000");
        input.add("");
        input.add("7000");
        input.add("8000");
        input.add("9000");
        input.add("");
        input.add("10000");
        return input;
    }

}