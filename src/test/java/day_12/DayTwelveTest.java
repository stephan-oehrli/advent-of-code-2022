package day_12;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DayTwelveTest {

    @Test
    void should_calculate_fewest_steps() {
        assertThat(DayTwelve.calculateFewestSteps(createTestInput())).isEqualTo(31);
    }
    
    @Test
    void should_calculate_fewest_steps_with_multiple_start_positions() {
        assertThat(DayTwelve.calculateFewestStepsWithMultipleStartPositions(createTestInput())).isEqualTo(29);
    }

    @Test
    void should_create_two_dimensional_array_from_input() {
        List<String> input = createTestInput();

        char[][] map = DayTwelve.Util.initMap(input);

        assertThat(map).hasNumberOfRows(5);
        for (char[] chars : map) {
            assertThat(chars).hasSize(8);
        }
    }

    @ParameterizedTest
    @CsvSource({
            "S,0,0",
            "E,5,2",
            "x,5,1",
            "s,3,2"
    })
    void should_find_first_position_in_char_array(char search, int xPos, int yPos) {
        List<String> input = createTestInput();
        char[][] map = DayTwelve.Util.initMap(input);

        DayTwelve.Position position = DayTwelve.Util.findFirstPosition(map, search);

        assertThat(position.x()).isEqualTo(xPos);
        assertThat(position.y()).isEqualTo(yPos);
    }
    
    @Test
    void should_find_all_positions_in_char_array() {
        List<String> input = createTestInput();
        char[][] map = DayTwelve.Util.initMap(input);
        
        List<DayTwelve.Position> positions = DayTwelve.Util.findAllStartPositions(map);
        
        assertThat(positions).hasSize(6);
    }

    private static List<String> createTestInput() {
        List<String> input = new ArrayList<>();
        input.add("Sabqponm");
        input.add("abcryxxl");
        input.add("accszExk");
        input.add("acctuvwj");
        input.add("abdefghi");
        return input;
    }
}