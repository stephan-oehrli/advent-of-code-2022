package day_10;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public abstract class Cpu {

    protected int registerX = 1;
    protected int cycle = 1;

    abstract void onTick();

    void execute(Command command) {
        onTick();
        switch (command.operation()) {
            case ADD -> {
                cycle++;
                onTick();
                cycle++;
                registerX += command.parameter();
            }
            case NOOP -> cycle++;
        }
    }

    Command parseCommand(String input) {
        String[] split = StringUtils.split(input);
        Operation operation = Operation.getOperation(split[0]);
        Integer parameter = null;
        if (split.length > 1) {
            parameter = Integer.parseInt(split[1]);
        }
        return new Command(operation, parameter);
    }
}
