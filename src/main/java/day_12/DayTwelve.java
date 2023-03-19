package day_12;

import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import utils.FileUtil;

import java.io.FileNotFoundException;
import java.util.*;

public class DayTwelve {

    public static void main(String[] args) throws FileNotFoundException {
        List<String> input = FileUtil.readToList("day_12.txt");

        System.out.println("Fewest steps: " + calculateFewestSteps(input));
        System.out.println("Fewest steps with multiple start positions: " + calculateFewestStepsWithMultipleStartPositions(input));
    }

    public static int calculateFewestStepsWithMultipleStartPositions(List<String> input) {
        char[][] map = Util.initMap(input);
        List<Position> startPositions = Util.findAllStartPositions(map);
        List<Integer> steps = new ArrayList<>();
        for (Position startPosition : startPositions) {
            try {
                steps.add(new SignalMeter(map, startPosition).measure());
            } catch (IllegalStateException e) {
                // No solution found. Ignore.
            }
        }
        return Collections.min(steps);
    }

    public static int calculateFewestSteps(List<String> input) {
        char[][] map = Util.initMap(input);
        Position startPosition = Util.findFirstPosition(map, 'S');
        return new SignalMeter(map, startPosition).measure();
    }

    @RequiredArgsConstructor
    public static class SignalMeter {

        private final char[][] map;
        private final Position startPosition;
        private final List<Position> visitedPositions = new ArrayList<>();
        private final Queue<Position> searchQueue = new LinkedList<>();

        private Position currentPosition;
        private int cost = -1;

        // Breadth first search algorithm
        public int measure() {
            visitedPositions.add(startPosition);
            searchQueue.add(startPosition);

            while (!searchQueue.isEmpty()) {
                currentPosition = searchQueue.remove();
                enqueuePossibleNeighbours();
                if (cost >= 0) {
                    return cost;
                }
            }

            throw new IllegalStateException("No way found to the location with the best signal.");
        }

        private void enqueuePossibleNeighbours() {
            // LEFT
            checkNeighbour(new Position(currentPosition.x() - 1, currentPosition.y(), currentPosition.cost() + 1));
            // RIGHT
            checkNeighbour(new Position(currentPosition.x() + 1, currentPosition.y(), currentPosition.cost() + 1));
            // UPPER
            checkNeighbour(new Position(currentPosition.x(), currentPosition.y() - 1, currentPosition.cost() + 1));
            // LOWER
            checkNeighbour(new Position(currentPosition.x(), currentPosition.y() + 1, currentPosition.cost() + 1));
        }

        private void checkNeighbour(Position position) {
            if (isPositionOnMap(position) && isPositionValid(position)) {
                visitedPositions.add(position);
                searchQueue.add(position);
            }
        }

        private boolean isPositionValid(Position position) {
            if (visitedPositions.contains(position)) {
                return false;
            }

            char currentChar = getChar(currentPosition) == 'S' ? 'a' : getChar(currentPosition);
            char neighbourChar = getChar(position) == 'S' ? 'a' : getChar(position);

            // check if neighbour char is goal
            if (neighbourChar == 'E') {
                if (currentChar == 'y' || currentChar == 'z') {
                    cost = position.cost();
                    return true;
                }
                neighbourChar = 'z';
            }

            return currentChar - neighbourChar >= -1;
        }

        private boolean isPositionOnMap(Position position) {
            int x = position.x();
            int y = position.y();
            return y >= 0 && y < map.length && x >= 0 && x < map[0].length;
        }

        private char getChar(Position position) {
            return map[position.y()][position.x()];
        }
    }

    public record Position(int x, int y, int cost) {
        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Position position)) return false;
            return this.x() == position.x() && this.y() == position.y();
        }
    }

    @UtilityClass
    public static class Util {

        public static char[][] initMap(List<String> input) {
            int yLength = input.size();
            int xLength = input.get(0).length();
            char[][] map = new char[yLength][xLength];
            for (int y = 0; y < yLength; y++) {
                map[y] = input.get(y).toCharArray();
            }
            return map;
        }

        public Position findFirstPosition(char[][] map, char positionChar) {
            for (int y = 0; y < map.length; y++) {
                for (int x = 0; x < map[y].length; x++) {
                    if (positionChar == map[y][x]) {
                        return new Position(x, y, 0);
                    }
                }
            }
            throw new IllegalStateException("No position with char '" + positionChar + "' found.");
        }


        public static List<Position> findAllStartPositions(char[][] map) {
            List<Position> positions = new ArrayList<>();
            for (int y = 0; y < map.length; y++) {
                for (int x = 0; x < map[y].length; x++) {
                    if ('S' == map[y][x] || 'a' == map[y][x]) {
                        positions.add(new Position(x, y, 0));
                    }
                }
            }
            return positions;
        }
    }
}
