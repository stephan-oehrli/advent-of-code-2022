package day_9;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DayNineTest {

    @Test
    void should_count_number_of_coordinates_visited_at_least_once_by_tail() {
        int numOfVisitedCoordinates = DayNine.countCoordinatesVisitedByTail(getTestInput(), 1);

        assertThat(numOfVisitedCoordinates).isEqualTo(13);
    }

    @Test
    void should_count_number_of_coordinates_visited_at_least_once_by_tail_with_multiple_tail_knots() {
        int numOfVisitedCoordinates = DayNine.countCoordinatesVisitedByTail(getTestInputTwo(), 9);

        assertThat(numOfVisitedCoordinates).isEqualTo(36);
    }

    private List<String> getTestInput() {
        List<String> testInput = new ArrayList<>();
        testInput.add("R 4");
        testInput.add("U 4");
        testInput.add("L 3");
        testInput.add("D 1");
        testInput.add("R 4");
        testInput.add("D 1");
        testInput.add("L 5");
        testInput.add("R 2");
        return testInput;
    }

    private List<String> getTestInputTwo() {
        List<String> testInput = new ArrayList<>();
        testInput.add("R 5");
        testInput.add("U 8");
        testInput.add("L 8");
        testInput.add("D 3");
        testInput.add("R 17");
        testInput.add("D 10");
        testInput.add("L 25");
        testInput.add("U 20");
        return testInput;
    }
}