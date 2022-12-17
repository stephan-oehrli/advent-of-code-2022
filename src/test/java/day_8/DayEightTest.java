package day_8;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DayEightTest {

    @Test
    void should_find_num_of_visible_trees() {
        int numOfVisibleTrees = DayEight.findNumOfVisibleTrees(getTestInput());

        assertThat(numOfVisibleTrees).isEqualTo(21);
    }

    @Test
    void should_find_largest_scenic_score() {
        int numOfVisibleTrees = DayEight.findLargesScenicScore(getTestInput());

        assertThat(numOfVisibleTrees).isEqualTo(8);
    }

    private List<String> getTestInput() {
        List<String> testInput = new ArrayList<>();
        testInput.add("30373");
        testInput.add("25512");
        testInput.add("65332");
        testInput.add("33549");
        testInput.add("35390");
        return testInput;
    }

}