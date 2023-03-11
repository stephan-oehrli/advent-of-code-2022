package day_10;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import utils.FileUtil;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class DayTen {

    public static void main(String[] args) throws FileNotFoundException {
        List<String> input = FileUtil.readToList("day_10.txt");
        int signalStrength = new Cpu().calculateSignalStrength(input);
        String image = new Cpu().renderImage(input);

        System.out.println("Signal strength is: " + signalStrength);
        System.out.println("\n" + image);
    }

    @Getter
    public static class Cpu {

        private int registerX = 1;
        private int cycle = 1;
        private int signalStrength = 0;
        private final StringBuilder image = new StringBuilder();

        public String renderImage(List<String> inputs) {
            for (String input : inputs) {
                execute(parseCommand(input), this::draw);
            }
            return image.toString();
        }

        private void draw() {
            int currentPos = (cycle - 1) % 40;
            int spriteStart = registerX - 1;
            int spriteEnd = registerX + 1;
            boolean isSpriteOnCurrentPos = currentPos >= spriteStart && currentPos <= spriteEnd;
            image.append(isSpriteOnCurrentPos ? "#" : ".");
            if (cycle % 40 == 0) {
                image.append("\n");
            }
        }

        public int calculateSignalStrength(List<String> inputs) {
            for (String input : inputs) {
                execute(parseCommand(input), this::calculateSignalStrength);
            }
            return signalStrength;
        }

        private void calculateSignalStrength() {
            // calculate on 20 and after 20 on steps of 40 (e.g. 60, 100, 140 etc.)
            if (cycle == 20 || cycle > 20 && (cycle - 20) % 40 == 0) {
                signalStrength += registerX * cycle;
            }
        }

        public void execute(Command command, Runnable monitorFunction) {
            monitorFunction.run();
            switch (command.operation) {
                case ADD -> {
                    cycle++;
                    monitorFunction.run();
                    cycle++;
                    registerX += command.parameter;
                }
                case NOOP -> cycle++;
            }
        }

        public Command parseCommand(String input) {
            String[] split = StringUtils.split(input);
            Operation operation = Operation.getOperation(split[0]);
            Integer parameter = null;
            if (split.length > 1) {
                parameter = Integer.parseInt(split[1]);
            }
            return new Command(operation, parameter);
        }
    }

    public record Command(Operation operation, Integer parameter) {
    }

    @RequiredArgsConstructor
    public enum Operation {
        ADD("addx"), NOOP("noop");

        private final String name;

        public static Operation getOperation(String name) {
            return Arrays.stream(values())
                    .filter((value) -> value.name.equals(name))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No such operation found: " + name));
        }
    }
}
