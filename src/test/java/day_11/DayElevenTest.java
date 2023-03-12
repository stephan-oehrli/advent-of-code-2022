package day_11;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import utils.FileUtil;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

class DayElevenTest {

    @ParameterizedTest
    @CsvSource({
            "3, 20, 10605",
            "1, 10000, 2713310158"
    })
    void should_calculate_monkey_business(Integer worryDivisor, Integer rounds, Long expected) throws FileNotFoundException {
        List<String> input = FileUtil.readToList("day_11_test_input.txt");

        assertThat(DayEleven.calculateMonkeyBusiness(input, rounds, worryDivisor)).isEqualTo(expected);
    }

    @Test
    void should_parse_monkey() {
        List<String> input = new ArrayList<>();
        input.add("Monkey 0:");
        input.add("  Starting items: 79, 98");
        input.add("  Operation: new = old * 19");
        input.add("  Test: divisible by 23");
        input.add("    If true: throw to monkey 2");
        input.add("    If false: throw to monkey 3");

        DayEleven.Monkey monkey = DayEleven.MonkeyUtil.parseFrom(input, 3);

        assertThat(monkey.getItems()).containsExactly(79L, 98L);
        assertThat(monkey.getOperation().apply(79L)).isEqualTo(79 * 19);
        assertThat(monkey.getPredicate().test(23L)).isTrue();
        assertThat(monkey.getPredicate().test(1L)).isFalse();
        assertThat(monkey.getTrueMonkey()).isEqualTo(2);
        assertThat(monkey.getFalseMonkey()).isEqualTo(3);
    }

    @Test
    void should_parse_operation() {
        Function<Long, Long> function = DayEleven.MonkeyUtil.parseOperation("  Operation: new = old * 19");

        assertThat(function.apply(2L)).isEqualTo(38);
    }

    @Test
    void should_initialize_monkeys() throws FileNotFoundException {
        List<String> input = FileUtil.readToList("day_11_test_input.txt");
        List<DayEleven.Monkey> monkeys = DayEleven.MonkeyUtil.initMonkeys(input, 3);

        assertThat(monkeys.size()).isEqualTo(4);
        assertThat(monkeys.get(0).getPlayers().size()).isEqualTo(4);
    }
}