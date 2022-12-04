package day_4;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static day_4.DayFour.ElfPair;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.from;

class DayFourTest {

    @Test
    void should_return_elf_pair_string() {
        List<String> input = getTestInput();

        ElfPair elfPair = DayFour.createElfPair(input.get(0));

        assertThat(elfPair)
                .returns(",2,3,4,", from(ElfPair::first))
                .returns(",6,7,8,", from(ElfPair::second));
    }

    @Test
    void should_count_fully_containing_pairs() {
        List<String> input = getTestInput();

        int numFullyContainingPairs = DayFour.countFullyContainingPairs(input);

        assertThat(numFullyContainingPairs).isEqualTo(2);
    }

    @Test
    void should_count_overlapping_pairs() {
        List<String> input = getTestInput();

        int numOverlappingPairs = DayFour.countOverlappingPairs(input);

        assertThat(numOverlappingPairs).isEqualTo(4);
    }

    private List<String> getTestInput() {
        List<String> input = new ArrayList<>();
        input.add("2-4,6-8");
        input.add("2-3,4-5");
        input.add("5-7,7-9");
        input.add("2-8,3-7");
        input.add("6-6,4-6");
        input.add("2-6,4-8");
        return input;
    }

}