package day_6;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class DaySixTest {

    @ParameterizedTest
    @CsvSource({
            "mjqjpqmgbljsphdztnvjfqwrcgsmlb, 7",
            "bvwbjplbgvbhsrlpgdmjqwftvncz, 5",
            "nppdvjthqldpwncqszvftbrmjlhg, 6",
            "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg, 10",
            "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw, 11",
    })
    void should_find_first_packet_marker(String input, int expectedIndex) {
        int indexOfFirstMarker = DaySix.findFirstMarker(input, 4);

        assertThat(indexOfFirstMarker).isEqualTo(expectedIndex);
    }

    @ParameterizedTest
    @CsvSource({
            "mjqjpqmgbljsphdztnvjfqwrcgsmlb, 19",
            "bvwbjplbgvbhsrlpgdmjqwftvncz, 23",
            "nppdvjthqldpwncqszvftbrmjlhg, 23",
            "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg, 29",
            "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw, 26",
    })
    void should_find_first_message_marker(String input, int expectedIndex) {
        int indexOfFirstMarker = DaySix.findFirstMarker(input, 14);

        assertThat(indexOfFirstMarker).isEqualTo(expectedIndex);
    }

}