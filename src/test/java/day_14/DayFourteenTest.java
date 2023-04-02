package day_14;

import day_14.DayFourteen.Cave;
import day_14.DayFourteen.Coordinate;
import day_14.DayFourteen.Util;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static day_14.DayFourteen.Util.createCaveMap;
import static day_14.DayFourteen.Util.parseRockCoordinates;
import static org.assertj.core.api.Assertions.assertThat;

class DayFourteenTest {

    @Test
    void should_parse_coordinates() {
        assertThat(parseRockCoordinates(createTestInput())).containsExactly(
                new Coordinate(498, 4, true, false),
                new Coordinate(498, 5, true, false),
                new Coordinate(498, 6, true, false),
                new Coordinate(497, 6, true, false),
                new Coordinate(496, 6, true, false),
                new Coordinate(503, 4, true, false),
                new Coordinate(502, 4, true, false),
                new Coordinate(502, 5, true, false),
                new Coordinate(502, 6, true, false),
                new Coordinate(502, 7, true, false),
                new Coordinate(502, 8, true, false),
                new Coordinate(502, 9, true, false),
                new Coordinate(501, 9, true, false),
                new Coordinate(500, 9, true, false),
                new Coordinate(499, 9, true, false),
                new Coordinate(498, 9, true, false),
                new Coordinate(497, 9, true, false),
                new Coordinate(496, 9, true, false),
                new Coordinate(495, 9, true, false),
                new Coordinate(494, 9, true, false)
        );
    }

    @Test
    void should_get_max_values() {
        List<Coordinate> coordinates = parseRockCoordinates(createTestInput());

        assertThat(Util.getMaxX(coordinates)).isEqualTo(503);
        assertThat(Util.getMaxY(coordinates)).isEqualTo(9);
    }

    @Test
    void should_create_cave_map() {
        Coordinate[][] caveMap = createCaveMap(createTestInput(), false);

        assertThat(caveMap).hasDimensions(12, 653);
        assertThat(caveMap[9][500].isSolid()).isTrue();
        assertThat(caveMap[8][500].isSolid()).isFalse();
    }

    @Test
    void should_calculate_fill_quantity_without_floor() {
        Cave cave = new Cave(createCaveMap(createTestInput(), false));

        assertThat(cave.calculateFillQuantity()).isEqualTo(24);
    }

    @Test
    void should_calculate_fill_quantity_with_floor() {
        Cave cave = new Cave(createCaveMap(createTestInput(), true));

        assertThat(cave.calculateFillQuantity()).isEqualTo(93);
    }

    private static List<String> createTestInput() {
        List<String> input = new ArrayList<>();
        input.add("498,4 -> 498,6 -> 496,6");
        input.add("503,4 -> 502,4 -> 502,9 -> 494,9");
        return input;
    }
}