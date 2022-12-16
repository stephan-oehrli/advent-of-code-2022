package day_7;

import lombok.RequiredArgsConstructor;
import utils.FileUtil;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class DaySeven {

    public static void main(String[] args) throws FileNotFoundException {
        List<String> inputList = FileUtil.readToList("day_7.txt");

        System.out.println("Sum of small directories: " + countSumOfSmallDirectories(inputList));
        System.out.println("Size of directory to delete: " + findSmallestSizeOfDirectory(inputList));
    }

    public static long countSumOfSmallDirectories(List<String> inputList) {
        FileSystem fileSystem = new FileSystem(inputList);
        List<Directory> directories = fileSystem.findDirectory(100000, Find.IS_SMALLER_THAN);
        return directories.stream().mapToLong(Directory::getSize).sum();
    }

    public static long findSmallestSizeOfDirectory(List<String> inputList) {
        FileSystem fileSystem = new FileSystem(inputList);
        long missingSpace = 30000000 - fileSystem.getFreeSpace();
        List<Directory> directories = fileSystem.findDirectory(missingSpace, Find.IS_LARGER_THAN);
        return directories.stream().mapToLong(Directory::getSize).min().orElseThrow();
    }

    static class FileSystem {

        private final Directory root = new Directory("/", null);
        private Directory current = root;

        public FileSystem(List<String> inputList) {
            for (String input : inputList) {
                process(input);
            }
        }

        public long getFreeSpace() {
            return 70000000 - root.getSize();
        }

        public List<Directory> findDirectory(long size, Find find) {
            List<Directory> directories = new ArrayList<>();
            findDirectories(size, root, directories, find);
            return directories;
        }

        private void findDirectories(long size, Directory directory, List<Directory> directories, Find find) {
            for (DataStructure file : directory.files) {
                if (file instanceof Directory) {
                    if ((find.equals(Find.IS_SMALLER_THAN) && file.getSize() <= size) ||
                            (find.equals(Find.IS_LARGER_THAN) && file.getSize() >= size)) {

                        directories.add((Directory) file);
                    }
                    findDirectories(size, (Directory) file, directories, find);
                }
            }
        }

        public void process(String input) {
            if (input.startsWith("$")) {
                runCommand(input.substring(2));
            } else {
                parseInfo(input);
            }
        }

        private void parseInfo(String input) {
            String[] split = input.split(" ");
            if (split[0].equals("dir")) {
                current.addData(new Directory(split[1], current));
            } else {
                current.addData(new File(split[1], Long.parseLong(split[0])));
            }
        }

        private void runCommand(String command) {
            String[] split = command.split(" ");
            if (split[0].equals("cd")) {
                if (split[1].equals("/")) {
                    current = root;
                } else {
                    current = current.changeDirectory(split[1]);
                }
            }
        }
    }

    enum Find {
        IS_LARGER_THAN,
        IS_SMALLER_THAN
    }

    @RequiredArgsConstructor
    static class Directory implements DataStructure {

        private final String name;
        private final Directory parent;
        private final List<DataStructure> files = new ArrayList<>();

        void addData(DataStructure data) {
            files.add(data);
        }

        Directory changeDirectory(String name) {
            if (name.equals("..")) {
                return parent;
            }
            for (DataStructure file : files) {
                if (file.getName().equals(name)) {
                    if (file instanceof Directory) {
                        return (Directory) file;
                    } else {
                        throw new IllegalStateException(name + " is not a directory");
                    }
                }
            }
            throw new IllegalStateException("Directory " + name + " does not exist");
        }

        @Override
        public long getSize() {
            long size = 0L;
            for (DataStructure file : files) {
                size += file.getSize();
            }
            return size;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    record File(String name, long size) implements DataStructure {
        @Override
        public long getSize() {
            return size;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    interface DataStructure {
        long getSize();

        String getName();
    }
}
