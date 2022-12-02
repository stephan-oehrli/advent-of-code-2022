package day_2;

import utils.FileUtil;

import java.io.FileNotFoundException;
import java.util.List;

public class DayTwo {

    public static void main(String[] args) throws FileNotFoundException {
        List<String> input = FileUtil.readToList("day_2.txt");

        int firstScore = calculateRockPaperScissorsScore(input);
        int secondScore = calculateRockPaperScissorsScoreWithGivenRoundEnds(input);

        System.out.println("First score: " + firstScore);
        System.out.println("Second score: " + secondScore);
    }

    public static int calculateRockPaperScissorsScore(List<String> input) {
        int score = 0;
        for (String round : input) {
            score += evaluateScore(round);
        }
        return score;
    }

    private static int evaluateScore(String round) {
        Shape opponentShape = Shape.fromChar(round.charAt(0));
        Shape playerShape = Shape.fromChar(round.charAt(2));
        return evaluateScore(opponentShape, playerShape);
    }

    private static int evaluateScore(Shape opponentShape, Shape playerShape) {
        int selectedShapeScore = playerShape.ordinal();
        int outcomeScore = playerShape.calculateScore(opponentShape);
        return selectedShapeScore + outcomeScore;
    }

    public static int calculateRockPaperScissorsScoreWithGivenRoundEnds(List<String> input) {
        int score = 0;
        for (String round : input) {
            score += evaluateScoreWithGivenRoundEnds(round);
        }
        return score;
    }

    private static int evaluateScoreWithGivenRoundEnds(String round) {
        Shape opponentShape = Shape.fromChar(round.charAt(0));
        Shape playerShape = findPlayerShape(opponentShape, round.charAt(2));
        return evaluateScore(opponentShape, playerShape);
    }

    private static Shape findPlayerShape(Shape opponentShape, char roundEnd) {
        if (roundEnd == 'X') return opponentShape.getInferior();
        if (roundEnd == 'Z') return opponentShape.getSuperior();
        return opponentShape;
    }

    private enum Shape {
        UNKNOWN, ROCK, PAPER, SCISSORS;

        static Shape fromChar(char c) {
            if (c == 'X' || c == 'A') {
                return ROCK;
            }
            if (c == 'Y' || c == 'B') {
                return PAPER;
            }
            if (c == 'Z' || c == 'C') {
                return SCISSORS;
            }
            return UNKNOWN;
        }

        public int calculateScore(Shape opponentShape) {
            if (isDraw(opponentShape)) {
                return 3;
            }
            if (isWin(opponentShape)) {
                return 6;
            }
            return 0;
        }

        public Shape getInferior() {
            if (this == ROCK) return SCISSORS;
            if (this == PAPER) return ROCK;
            if (this == SCISSORS) return PAPER;
            return UNKNOWN;
        }

        public Shape getSuperior() {
            if (this == ROCK) return PAPER;
            if (this == PAPER) return SCISSORS;
            if (this == SCISSORS) return ROCK;
            return UNKNOWN;
        }

        private boolean isDraw(Shape opponentShape) {
            return this == opponentShape;
        }

        private boolean isWin(Shape opponentShape) {
            return this == ROCK && opponentShape == SCISSORS ||
                    this == PAPER && opponentShape == ROCK ||
                    this == SCISSORS && opponentShape == PAPER;
        }
    }
}
