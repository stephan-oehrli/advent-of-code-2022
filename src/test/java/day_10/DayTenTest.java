package day_10;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import utils.FileUtil;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DayTenTest {

    @ParameterizedTest(name = "{0}")
    @CsvSource({
            "addx 15,ADD,15",
            "addx -11,ADD,-11",
            "noop,NOOP,"
    })
    void should_parse_command_correctly(String input, DayTen.Operation operation, Integer parameter) {
        DayTen.Cpu cpu = new DayTen.Cpu();

        DayTen.Command command = cpu.parseCommand(input);

        assertThat(command.operation()).isEqualTo(operation);
        assertThat(command.parameter()).isEqualTo(parameter);
    }

    @ParameterizedTest(name = "{0}")
    @CsvSource({
            "addx 15, 16, 3",
            "addx -11, -10, 3",
            "noop, 1, 2"
    })
    void should_execute_command_correctly(String input, int expectedRegisterX, int expectedCycle) {
        DayTen.Cpu cpu = new DayTen.Cpu();
        DayTen.Command command = cpu.parseCommand(input);

        cpu.execute(command, () -> {
        });

        assertThat(cpu.getRegisterX()).isEqualTo(expectedRegisterX);
        assertThat(cpu.getCycle()).isEqualTo(expectedCycle);
    }

    @Test
    void should_calculate_signal_strength_correctly() throws FileNotFoundException {
        List<String> input = FileUtil.readToList("day_10_test_input.txt");
        DayTen.Cpu cpu = new DayTen.Cpu();

        assertThat(cpu.calculateSignalStrength(input)).isEqualTo(13140);
    }

    @Test
    void should_draw_noop_only_correctly() {
        List<String> input = new ArrayList<>();
        for (int i = 0; i < 240; i++) {
            input.add("noop");
        }
        DayTen.Cpu cpu = new DayTen.Cpu();

        assertThat(cpu.renderImage(input)).isEqualTo(
                """
                        ###.....................................
                        ###.....................................
                        ###.....................................
                        ###.....................................
                        ###.....................................
                        ###.....................................
                        """);
    }

    @Test
    void should_draw_test_input_correctly() throws FileNotFoundException {
        List<String> input = FileUtil.readToList("day_10_test_input.txt");
        DayTen.Cpu cpu = new DayTen.Cpu();

        assertThat(cpu.renderImage(input)).isEqualTo(
                """
                        ##..##..##..##..##..##..##..##..##..##..
                        ###...###...###...###...###...###...###.
                        ####....####....####....####....####....
                        #####.....#####.....#####.....#####.....
                        ######......######......######......####
                        #######.......#######.......#######.....
                        """);
    }

}