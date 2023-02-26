package day_9;

import org.apache.commons.lang3.StringUtils;
import utils.FileUtil;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DayNine {

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        List<String> input = FileUtil.readToList("day_9.txt");

        System.out.println("Number of visited coordinates by tail for at least once: " +
                countCoordinatesVisitedByTail(input, 1));
        System.out.println("Number of visited coordinates by tail with 9 tail knots for at least once: " +
                countCoordinatesVisitedByTail(input, 9));
    }

    public static int countCoordinatesVisitedByTail(List<String> input, int numOfTailKnots) {
        // initialize head and tail knots
        Set<Position> visitedTailPositions = new HashSet<>();
        List<Position> tailPositions = new ArrayList<>();
        Position startPosition = new Position(0, 0);
        Position headPosition = startPosition;
        for (int i = 0; i < numOfTailKnots; i++) {
            tailPositions.add(startPosition);
        }
        visitedTailPositions.add(startPosition);

        for (String commandString : input) {
            Command command = parseCommand(commandString);
            for (int i = 0; i < command.amount; i++) {
                headPosition = calculateNewHeadPosition(headPosition, command.direction);
                tailPositions.set(0, calculateNewTailPosition(tailPositions.get(0), headPosition));
                for (int j = 1; j < numOfTailKnots; j++) {
                    tailPositions.set(j, calculateNewTailPosition(tailPositions.get(j), tailPositions.get(j - 1)));
                }
                visitedTailPositions.add(tailPositions.get(numOfTailKnots - 1));
            }
        }

        return visitedTailPositions.size();
    }

    private static Position calculateNewHeadPosition(Position previousPosition, Direction direction) {
        int x = previousPosition.x;
        int y = previousPosition.y;
        switch (direction) {
            case UP -> y++;
            case DOWN -> y--;
            case RIGHT -> x++;
            case LEFT -> x--;
        }
        return new Position(x, y);
    }

    private static Position calculateNewTailPosition(Position previousTailPosition, Position leadPosition) {
        int currentX = previousTailPosition.x;
        int currentY = previousTailPosition.y;

        // check if tail move is required
        if (Math.abs(leadPosition.x - currentX) > 1 || Math.abs(leadPosition.y - currentY) > 1) {
            // tail is on the same vertical line with lead position
            if (leadPosition.x == currentX && leadPosition.y > currentY) return new Position(currentX, currentY + 1);
            if (leadPosition.x == currentX && leadPosition.y < currentY) return new Position(currentX, currentY - 1);
            // tail is on the same horizontal line with lead position
            if (leadPosition.y == currentY && leadPosition.x > currentX) return new Position(currentX + 1, currentY);
            if (leadPosition.y == currentY && leadPosition.x < currentX) return new Position(currentX - 1, currentY);
            // tail moves diagonal
            int newX = currentX + (leadPosition.x - currentX > 0 ? 1 : -1);
            int newY = currentY + (leadPosition.y - currentY > 0 ? +1 : -1);
            return new Position(newX, newY);
        }

        return previousTailPosition;
    }

    private static Command parseCommand(String input) {
        String[] split = StringUtils.split(input);
        return new Command(Direction.getDirectionOf(split[0]), Integer.parseInt(split[1]));
    }

    private record Position(int x, int y) {
    }

    private record Command(Direction direction, int amount) {
    }

    enum Direction {
        LEFT("L"), RIGHT("R"), UP("U"), DOWN("D");

        private final String command;

        Direction(String command) {
            this.command = command;
        }

        public static Direction getDirectionOf(String command) {
            for (Direction direction : Direction.values()) {
                if (direction.command.equals(command)) {
                    return direction;
                }
            }
            throw new IllegalArgumentException(command + " is not a valid direction.");
        }
    }
}
