package day_13;

import day_13.DayThirteen.PacketComparator;
import day_13.DayThirteen.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static day_13.DayThirteen.Util.createList;
import static day_13.DayThirteen.Util.createPairs;
import static day_13.DayThirteen.findDecoderKey;
import static day_13.DayThirteen.sumUpCorrectOrderPairIndices;
import static org.assertj.core.api.Assertions.assertThat;

class DayThirteenTest {

    @Test
    void should_create_list() {
        String input = "[[4,[6,[9,0,1,10],[6,9],[0,5,9,8],[6,6]],9,[[8],[7,1,8,10,2],9,[9,0,5,1,9]]],[],[[1,7,7,[6]],10],[4,[5,4],9,[]]]";

        List<Object> list = createList(input);
        assertThat(list).hasSize(4);
        assertThat(list.get(0)).isInstanceOf(List.class);
    }

    @Test
    void should_create_pairs() {
        List<String> input = createTestInput();

        List<Pair> pairs = createPairs(input);

        assertThat(pairs).hasSize(8);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "[1,1,3,1,1];[1,1,5,1,1];-1",
            "[1,1,5,1,1];[1,1,5,1];1",
            "[1,[1]];[1,[1]];0",
            "[1,[2]];[1,[1]];1",
            "[[1],[2,3,4]];[[1],4];-1",
            "[9];[[8,7,6]];1",
            "[9];[8,7,6];1",
            "[[4,4],4,4];[[4,4],4,4,4];-1",
            "[7,7,7,7];[7,7,7];1",
            "[[]];[3];-1",
            "[[[]]];[3];-1",
            "[[[]]];[[]];1",
            "[[]];[[[]]];-1",
            "[[3]];[[[[2]]]];1",
            "[[2]];[[[[3]]]];-1",
            "[1,[2,[3,[4,[5,6,7]]]],8,9];[1,[2,[3,[4,[5,6,0]]]],8,9];1",
            "[1,[2,[3,[4,[5,6,7]]]],8,9];[1,[2,[3,[4,[5,6]]]],8,9];1",
            "[1,[2,[3,[4,[5,6,7]]]],8,9];[1,[2,[3,[4,[5,6,8]]]],8,9];-1",
            "[1,[2],[3,4],5,[6,7]];[1,[2],[3,4],5,[6,7]];0",
            "[1,[2],[3,4],5,[6,7]];[1,[2],[3],[4],5,[6,7]];1",
            "[1,1];[1,1,1];-1",
            "[[4],3];[[5],4];-1",
            "[3];[[]];1",
            "[4];[2,2,2];1",
            "[];[[[[5,10]]]];-1",
            "[];[];0",
            "[3];[[[4]]];-1",
            "[[0,0,0]];[2];-1",
            "[[4],3];[[5],2];-1",
            "[[[[3]]]];[[3]];0",
            "[[8,[[7,0,6,5],9],[[7,10],10,[9,7],8],5,[8,7,7,[2,5,2,10],4]],[2,9,[2,2,9,[6]],3],[]];[[[8],[[0,3,3],5,6],0,[[9,4,8,4,8],7,6]],[[8,5,2,8]],[]];1"
    })
    void should_have_pairs_in_right_order(String left, String right, int expected) {
        Pair pair = new Pair(createList(left), createList(right));

        assertThat(pair.compare()).isEqualTo(expected);
    }

    @Test
    void should_sum_up_correct_order_pair_indices() {
        assertThat(sumUpCorrectOrderPairIndices(createTestInput())).isEqualTo(13);
    }

    @Test
    void should_sort_input() {
        List<List<Object>> packets = DayThirteen.Util.createPacketList(createTestInput());

        packets.sort(new PacketComparator());

        assertThat(packets.get(0)).hasSize(0);
        assertThat(packets.get(packets.size() - 1).get(0)).isEqualTo(9);
    }

    @Test
    void should_find_decoder_key() {
        assertThat(findDecoderKey(createTestInput())).isEqualTo(140);
    }

    private static List<String> createTestInput() {
        List<String> input = new ArrayList<>();
        input.add("[1,1,3,1,1]");
        input.add("[1,1,5,1,1]");
        input.add("");
        input.add("[[1],[2,3,4]]");
        input.add("[[1],4]");
        input.add("");
        input.add("[9]");
        input.add("[[8,7,6]]");
        input.add("");
        input.add("[[4,4],4,4]");
        input.add("[[4,4],4,4,4]");
        input.add("");
        input.add("[7,7,7,7]");
        input.add("[7,7,7]");
        input.add("");
        input.add("[]");
        input.add("[3]");
        input.add("");
        input.add("[[[]]]");
        input.add("[[]]");
        input.add("");
        input.add("[1,[2,[3,[4,[5,6,7]]]],8,9]");
        input.add("[1,[2,[3,[4,[5,6,0]]]],8,9]");
        return input;
    }

}