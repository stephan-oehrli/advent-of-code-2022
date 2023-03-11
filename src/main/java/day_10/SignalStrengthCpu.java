package day_10;

import java.util.List;

public class SignalStrengthCpu extends Cpu{

    private int signalStrength = 0;

    public int calculateSignalStrength(List<String> inputs) {
        for (String input : inputs) {
            execute(parseCommand(input));
        }
        return signalStrength;
    }

    @Override
    void onTick() {
        // calculate on 20 and after 20 on steps of 40 (e.g. 60, 100, 140 etc.)
        if (cycle == 20 || cycle > 20 && (cycle - 20) % 40 == 0) {
            signalStrength += registerX * cycle;
        }
    }
}
