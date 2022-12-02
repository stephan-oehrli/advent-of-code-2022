package day_2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DayTwoTest {

    @Test
    void should_calculate_rock_paper_scissors_score() {
        List<String> input = getTestInput();

        int score = DayTwo.calculateRockPaperScissorsScore(input);

        assertThat(score).isEqualTo(15);
    }

    @Test
    void should_calculate_rock_paper_scissors_score_with_given_round_ends() {
        List<String> input = getTestInput();

        int score = DayTwo.calculateRockPaperScissorsScoreWithGivenRoundEnds(input);

        assertThat(score).isEqualTo(12);
    }

    private List<String> getTestInput() {
        List<String> input = new ArrayList<>();
        input.add("A Y");
        input.add("B X");
        input.add("C Z");
        return input;
    }

}