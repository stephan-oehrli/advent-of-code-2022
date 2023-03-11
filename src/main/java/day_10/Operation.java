package day_10;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

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
