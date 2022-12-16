package day_7;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DaySevenTest {

    @Test
    void should_count_sum_of_directories_with_at_most_100000_size() {
        long sum = DaySeven.countSumOfSmallDirectories(getTestInput());

        assertThat(sum).isEqualTo(95437);
    }
    
    @Test
    void should_find_smallest_directory_size_which_frees_up_enough_space() {
        long smallestSizeOfDirectory = DaySeven.findSmallestSizeOfDirectory(getTestInput());
        
        assertThat(smallestSizeOfDirectory).isEqualTo(24933642);
    }

    private List<String> getTestInput() {
        ArrayList<String> input = new ArrayList<>();
        input.add("$ cd /");
        input.add("$ ls");
        input.add("dir a");
        input.add("14848514 b.txt");
        input.add("8504156 c.dat");
        input.add("dir d");
        input.add("$ cd a");
        input.add("$ ls");
        input.add("dir e");
        input.add("29116 f");
        input.add("2557 g");
        input.add("62596 h.lst");
        input.add("$ cd e");
        input.add("$ ls");
        input.add("584 i");
        input.add("$ cd ..");
        input.add("$ cd ..");
        input.add("$ cd d");
        input.add("$ ls");
        input.add("4060174 j");
        input.add("8033020 d.log");
        input.add("5626152 d.ext");
        input.add("7214296 k");
        return input;
    }
}