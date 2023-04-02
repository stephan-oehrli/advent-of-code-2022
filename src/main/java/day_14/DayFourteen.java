package day_14;

import day_14.Visualizer.Panel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import utils.FileUtil;

import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static day_14.DayFourteen.Util.createCaveMap;

public class DayFourteen {

    public static boolean SHOW_VISUALISATION = false;

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        Cave caveWithoutFloor = new Cave(createCaveMap(FileUtil.readToList("day_14.txt"), false));
        Cave caveWithFloor = new Cave(createCaveMap(FileUtil.readToList("day_14.txt"), true));

        System.out.println("Stopped sand grains without floor: " + caveWithoutFloor.calculateFillQuantity());
        System.out.println("Stopped sand grains with floor: " + caveWithFloor.calculateFillQuantity());
    }

    @RequiredArgsConstructor
    public static class Cave {

        private final Coordinate[][] caveMap;
        private final Coordinate sourceOfSand = new Coordinate(500, 0, false, false);

        private boolean isCalculating = true;
        private int stoppedSandGrains = 0;
        private Panel panel;
        private Visualizer visualizer;

        public int calculateFillQuantity() {
            if (SHOW_VISUALISATION) {
                panel = new Panel(caveMap);
                visualizer = new Visualizer(panel);
            }
            while (isCalculating) {
                // drop sand grain
                SandGrain sandGrain = new SandGrain(sourceOfSand.getX(), sourceOfSand.getY(), true);
                while (sandGrain.isFalling()) {
                    fallAccordingTheRules(sandGrain);
                    if (SHOW_VISUALISATION) {
                        panel.setCells(panel.createCells(caveMap));
                        panel.repaint();
                    }
                }
            }
            if (SHOW_VISUALISATION) {
                visualizer.dispose();
            }
            return stoppedSandGrains;
        }

        private void fallAccordingTheRules(SandGrain sandGrain) {
            if (sandGrain.getY() == caveMap.length - 1) {
                // sand grain reached abyss -> stop calculating
                isCalculating = false;
                sandGrain.setFalling(false);
            } else if (!caveMap[sandGrain.getY() + 1][sandGrain.getX()].isSolid) {
                // position below is free
                sandGrain.setY(sandGrain.getY() + 1);
            } else if (!caveMap[sandGrain.getY() + 1][sandGrain.getX() - 1].isSolid) {
                // position one step down and to the left is free
                sandGrain.setY(sandGrain.getY() + 1);
                sandGrain.setX(sandGrain.getX() - 1);
            } else if (!caveMap[sandGrain.getY() + 1][sandGrain.getX() + 1].isSolid) {
                // position one step down and to the right is free
                sandGrain.setY(sandGrain.getY() + 1);
                sandGrain.setX(sandGrain.getX() + 1);
            } else {
                // no position left where the sand grain can fall -> sand grain stops
                stoppedSandGrains++;
                caveMap[sandGrain.getY()][sandGrain.getX()].setSolid(true);
                caveMap[sandGrain.getY()][sandGrain.getX()].setSandGrain(true);
                sandGrain.setFalling(false);
                if (sandGrain.getY() == sourceOfSand.getY() && sandGrain.getX() == sourceOfSand.getX()) {
                    // sand grain can no longer fall from source -> stop calculating
                    isCalculating = false;
                }
            }
        }
    }

    @Data
    @AllArgsConstructor
    public static class SandGrain {
        private int x;
        private int y;
        private boolean isFalling;
    }

    @Data
    @AllArgsConstructor
    public static class Coordinate {
        private final int x;
        private final int y;
        private boolean isSolid;
        private boolean isSandGrain;
    }

    @UtilityClass
    public static class Util {

        public static List<Coordinate> parseRockCoordinates(List<String> input) {
            List<Coordinate> coordinates = new LinkedList<>();
            for (String line : input) {
                coordinates.addAll(parseRockCoordinates(line));
            }
            return coordinates;
        }

        private static List<Coordinate> parseRockCoordinates(String input) {
            List<Coordinate> coordinates = new LinkedList<>();
            Queue<Coordinate> coordinateQueue = new LinkedList<>();
            for (String coordinate : input.split(" -> ")) {
                String[] split = coordinate.split(",");
                coordinateQueue.add(new Coordinate(Integer.parseInt(split[0]), Integer.parseInt(split[1]), true, false));
            }
            while (coordinateQueue.size() > 1) {
                Coordinate coordinate = coordinateQueue.remove();
                coordinates.add(coordinate);
                Coordinate nextCoordinate = coordinateQueue.peek();
                assert nextCoordinate != null;
                if (coordinate.getY() == nextCoordinate.getY()) {
                    int difference = nextCoordinate.getX() - coordinate.getX();
                    for (int i = 1; i < Math.abs(difference); i++) {
                        int newX = difference > 0 ? coordinate.x + i : coordinate.x - i;
                        coordinates.add(new Coordinate(newX, coordinate.y, true, false));
                    }
                } else if (coordinate.getX() == nextCoordinate.getX()) {
                    int difference = nextCoordinate.getY() - coordinate.getY();
                    for (int i = 1; i < Math.abs(difference); i++) {
                        int newY = difference > 0 ? coordinate.y + i : coordinate.y - i;
                        coordinates.add(new Coordinate(coordinate.x, newY, true, false));
                    }
                }
            }
            coordinates.add(coordinateQueue.remove());
            return coordinates;
        }

        public static int getMaxX(List<Coordinate> coordinates) {
            return coordinates.stream().max(Comparator.comparingInt(Coordinate::getX)).orElseThrow().getX();
        }

        public static int getMaxY(List<Coordinate> coordinates) {
            return coordinates.stream().max(Comparator.comparingInt(Coordinate::getY)).orElseThrow().getY();
        }

        public static Coordinate[][] createCaveMap(List<String> input, boolean hasFloor) {
            List<Coordinate> rocks = parseRockCoordinates(input);
            int height = getMaxY(rocks) + 3;
            int width = getMaxX(rocks) + 150;
            Coordinate[][] caveMap = new Coordinate[height][width];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    caveMap[y][x] = new Coordinate(x, y, hasFloor && y == height - 1, false);
                }
            }
            rocks.forEach(rock -> caveMap[rock.getY()][rock.getX()] = rock);
            return caveMap;
        }
    }
}
