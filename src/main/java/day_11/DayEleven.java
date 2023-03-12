package day_11;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.UtilityClass;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import utils.FileUtil;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DayEleven {

    public static void main(String[] args) throws FileNotFoundException {
        List<String> input = FileUtil.readToList("day_11.txt");

        System.out.println("MonkeyBusiness 1: " + calculateMonkeyBusiness(input, 20, 3));
        System.out.println("MonkeyBusiness 2: " + calculateMonkeyBusiness(input, 10000, 1));
    }

    public static Long calculateMonkeyBusiness(List<String> input, int rounds, int worryDivisor) {
        List<Monkey> monkeys = MonkeyUtil.initMonkeys(input, worryDivisor);
        for (int i = 0; i < rounds; i++) {
            for (Monkey monkey : monkeys) {
                monkey.play();
            }
        }
        List<Monkey> sortedMonkeys = monkeys.stream().sorted().toList();
        return sortedMonkeys.get(0).getInspectionCount() * sortedMonkeys.get(1).getInspectionCount();
    }

    @Getter
    @RequiredArgsConstructor
    public static class Monkey implements Comparable<Monkey> {

        private final Queue<Long> items;
        private final Function<Long, Long> operation;
        private final Predicate<Long> predicate;
        private final int trueMonkey;
        private final int falseMonkey;
        private final int worryDivisor;
        private final int divisor;

        @Setter
        private int reducer;
        @Setter
        private List<Monkey> players;
        private long inspectionCount = 0;

        public void play() {
            while (!items.isEmpty()) {
                inspectNextItem();
            }
        }

        private void inspectNextItem() {
            inspectionCount++;
            Long item = items.remove();
            long worryLevel = operation.apply(item) % reducer;
            if (worryDivisor != 1) {
                worryLevel = worryLevel / worryDivisor;
            }
            players.get(predicate.test(worryLevel) ? trueMonkey : falseMonkey).catchItem(worryLevel);
        }

        public void catchItem(Long item) {
            items.add(item);
        }


        @Override
        public int compareTo(Monkey o) {
            if (this.inspectionCount == o.inspectionCount) return 0;
            return this.inspectionCount < o.inspectionCount ? 1 : -1;
        }
    }

    @UtilityClass
    public static class MonkeyUtil {

        public static List<Monkey> initMonkeys(List<String> input, int worryDivisor) {
            List<List<String>> partition = ListUtils.partition(input, 7);
            List<Monkey> monkeys = new LinkedList<>();
            int reducer = 1;
            for (List<String> instructions : partition) {
                Monkey monkey = parseFrom(instructions, worryDivisor);
                monkeys.add(monkey);
                reducer *= monkey.getDivisor();
            }
            for (Monkey monkey : monkeys) {
                monkey.setPlayers(monkeys);
                monkey.setReducer(reducer);
            }
            return monkeys;
        }

        public static Monkey parseFrom(List<String> instruction, int worryDivisor) {
            Queue<Long> items = Arrays.stream(instruction.get(1).split("\\D+"))
                    .filter(StringUtils::isNotEmpty)
                    .map(Long::valueOf)
                    .collect(Collectors.toCollection(LinkedList::new));
            Function<Long, Long> operation = parseOperation(instruction.get(2));
            int divisor = Integer.parseInt(instruction.get(3).replaceAll("\\D+", ""));
            Predicate<Long> predicate = num -> num % divisor == 0;
            int trueMonkey = Integer.parseInt(instruction.get(4).replaceAll("\\D+", ""));
            int falseMonkey = Integer.parseInt(instruction.get(5).replaceAll("\\D+", ""));
            return new Monkey(items, operation, predicate, trueMonkey, falseMonkey, worryDivisor, divisor);
        }

        public static Function<Long, Long> parseOperation(String input) {
            if (StringUtils.countMatches(input, "old") > 1) {
                return input.contains("*") ? num -> num * num : num -> num + num;
            }
            Long operator = Long.valueOf(input.replaceAll("\\D+", ""));
            return input.contains("*") ? num -> num * operator : num -> num + operator;
        }
    }
}
