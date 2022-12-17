package day_8;

import utils.FileUtil;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class DayEight {

    public static void main(String[] args) throws FileNotFoundException {
        List<String> input = FileUtil.readToList("day_8.txt");

        System.out.println("Number of visible trees: " + findNumOfVisibleTrees(input));
        System.out.println("Largest scenic score: " + findLargesScenicScore(input));
    }

    public static int findNumOfVisibleTrees(List<String> input) {
        TreeMap treeMap = new TreeMap(input);
        return treeMap.countVisibleTrees();
    }

    public static int findLargesScenicScore(List<String> input) {
        TreeMap treeMap = new TreeMap(input);
        return treeMap.calculateLargestScenicScore();
    }

    private static class TreeMap {

        private final List<Tree> trees = new ArrayList<>();
        private final int[][] intTreeArr;
        private final int height;
        private final int width;

        public TreeMap(List<String> input) {
            height = input.size();
            width = input.get(0).length();
            intTreeArr = new int[height][width];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int treeHeight = Integer.parseInt(String.valueOf(input.get(y).charAt(x)));
                    trees.add(new Tree(treeHeight, x, y));
                    intTreeArr[y][x] = treeHeight;
                }
            }
        }

        public int countVisibleTrees() {
            int count = 0;
            for (Tree tree : trees) {
                if (tree.x == 0 || tree.x == width - 1 || tree.y == 0 || tree.y == height - 1) {
                    count++;
                } else if (isTreeVisible(tree)) {
                    count++;
                }
            }
            return count;
        }

        private boolean isTreeVisible(Tree tree) {
            List<Tree> top = trees.stream()
                    .filter(t -> t.x == tree.x && t.y < tree.y && t.height >= tree.height)
                    .toList();
            if (top.isEmpty()) return true;
            List<Tree> down = trees.stream()
                    .filter(t -> t.x == tree.x && t.y > tree.y && t.height >= tree.height)
                    .toList();
            if (down.isEmpty()) return true;
            List<Tree> left = trees.stream()
                    .filter(t -> t.y == tree.y && t.x < tree.x && t.height >= tree.height)
                    .toList();
            if (left.isEmpty()) return true;
            List<Tree> right = trees.stream()
                    .filter(t -> t.y == tree.y && t.x > tree.x && t.height >= tree.height)
                    .toList();
            return right.isEmpty();
        }

        public int calculateLargestScenicScore() {
            int largestScenicScore = 0;
            for (Tree tree : trees) {
                if (tree.x != 0 && tree.x != width - 1 && tree.y != 0 && tree.y != height - 1) {
                    int score = calculateScenicScore(tree);
                    if (score > largestScenicScore) {
                        largestScenicScore = score;
                    }
                }
            }
            return largestScenicScore;
        }

        private int calculateScenicScore(Tree tree) {
            int top = getTopScenicScore(tree);
            int bottom = getBottomScenicScore(tree);
            int left = getLeftScenicScore(tree);
            int right = getRightScenicScore(tree);
            return top * bottom * left * right;
        }

        private int getRightScenicScore(Tree tree) {
            int score = 0;
            for (int x = tree.x + 1; x < width; x++) {
                if (intTreeArr[tree.y][x] >= tree.height) {
                    return ++score;
                } else {
                    score++;
                }
            }
            return score;
        }

        private int getLeftScenicScore(Tree tree) {
            int score = 0;
            for (int x = tree.x - 1; x >= 0; x--) {
                if (intTreeArr[tree.y][x] >= tree.height) {
                    return ++score;
                } else {
                    score++;
                }
            }
            return score;
        }

        private int getBottomScenicScore(Tree tree) {
            int score = 0;
            for (int y = tree.y + 1; y < height; y++) {
                if (intTreeArr[y][tree.x] >= tree.height) {
                    return ++score;
                } else {
                    score++;
                }
            }
            return score;
        }

        private int getTopScenicScore(Tree tree) {
            int score = 0;
            for (int y = tree.y - 1; y >= 0; y--) {
                if (intTreeArr[y][tree.x] >= tree.height) {
                    return ++score;
                } else {
                    score++;
                }
            }
            return score;
        }

    }

    private record Tree(int height, int x, int y) {
    }
}
