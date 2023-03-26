package day_13;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import utils.FileUtil;

import java.io.FileNotFoundException;
import java.util.*;

public class DayThirteen {

    public static final boolean IS_DEBUG = false;

    public static void debug(String message, int level) {
        if (IS_DEBUG) System.out.println(StringUtils.repeat(" ", level * 2) + message);
    }

    public static void debug(String message) {
        debug(message, 0);
    }

    public static void main(String[] args) throws FileNotFoundException {
        List<String> input = FileUtil.readToList("day_13.txt");
        
        System.out.println("Correct indices sum: " + sumUpCorrectOrderPairIndices(input));
        System.out.println("Decoder key: " + findDecoderKey(input));
    }

    public static int sumUpCorrectOrderPairIndices(List<String> input) {
        int indicesSum = 0;
        List<Pair> pairs = Util.createPairs(input);
        for (int i = 0; i < pairs.size(); i++) {
            debug("== Pair " + (i + 1) + " ==");
            if (pairs.get(i).compare() <= 0) {
                indicesSum += (i + 1);
            }
            debug("");
        }
        return indicesSum;
    }
    
    public static int findDecoderKey(List<String> input) {
        List<List<Object>> packetList = Util.createPacketList(input);
        List<Object> dividerOne = Util.createList("[[2]]");
        List<Object> dividerTwo = Util.createList("[[6]]");
        packetList.add(dividerOne);
        packetList.add(dividerTwo);
        packetList.sort(new PacketComparator());
        
        return (packetList.indexOf(dividerOne) + 1) * (packetList.indexOf(dividerTwo) + 1);
    }

    public record Pair(List<Object> left, List<Object> right) {

        public int compare() {
            return compare(left, right, 1);
        }

        @SuppressWarnings("unchecked")
        public static int compare(List<Object> leftList, List<Object> rightList, int level) {
            debug("Compare " + leftList + " vs " + rightList, level);
            level++;
            for (int i = 0; i < leftList.size(); i++) {
                if (rightList.size() - 1 < i) {
                    debug("==> x Right side ran out of items, so inputs are not in the right order", level);
                    return 1;
                }
                if (isIntegerPair(leftList.get(i), rightList.get(i))) {
                    // Compare integers
                    debug("Compare " + leftList.get(i) + " vs " + rightList.get(i), level);
                    if ((int) leftList.get(i) != (int) rightList.get(i)) {
                        if ((int) leftList.get(i) > (int) rightList.get(i)) {
                            debug("==> x Right side is smaller, so inputs are not in the right order", level);
                            return 1;
                        }
                        debug("==> ✓ Left side is smaller, so inputs are in the right order", level);
                        return -1;
                    }
                } else if (isListPair(leftList.get(i), rightList.get(i))) {
                    // Compare lists
                    int comparison = compare((List<Object>) leftList.get(i), (List<Object>) rightList.get(i), level + 1);
                    if (comparison != 0) {
                        return comparison;
                    }
                } else {
                    // Compare mixed types
                    debug("Compare " + leftList.get(i) + " vs " + rightList.get(i), level);
                    int comparison;
                    if (leftList.get(i) instanceof List) {
                        debug("~ Mixed types; convert right to [" + rightList.get(i) + "] and retry comparison", level);
                        comparison = compare((List<Object>) leftList.get(i), List.of(rightList.get(i)), level + 1);
                    } else {
                        debug("~ Mixed types; convert left to [" + leftList.get(i) + "] and retry comparison", level);
                        comparison = compare(List.of(leftList.get(i)), (List<Object>) rightList.get(i), level + 1);
                    }
                    if (comparison != 0) {
                        return comparison;
                    }
                }

            }
            if (leftList.size() < rightList.size()) {
                debug("==> ✓ Left side ran out of items, so inputs are in the right order", level);
                return -1;
            }
            return 0;
        }

        private static boolean isIntegerPair(Object leftObject, Object rightObject) {
            return leftObject instanceof Integer && rightObject instanceof Integer;
        }

        private static boolean isListPair(Object leftObject, Object rightObject) {
            return leftObject instanceof List && rightObject instanceof List;
        }
    }
    
    public static class PacketComparator implements Comparator<List<Object>> {

        @Override
        public int compare(List<Object> o1, List<Object> o2) {
            return Pair.compare(o1, o2, 0);
        }
    }

    @UtilityClass
    public static class Util {

        public List<Pair> createPairs(List<String> input) {
            List<Pair> pairs = new LinkedList<>();
            for (int i = 0; i < input.size(); i += 3) {
                pairs.add(new Pair(createList(input.get(i)), createList(input.get(i + 1))));
            }
            return pairs;
        }
        
        public List<List<Object>> createPacketList(List<String> input) {
            List<List<Object>> packets = new ArrayList<>();
            for (String line : input) {
                if (StringUtils.isNotEmpty(line)) {
                    packets.add(createList(line));
                }
            }
            return packets;
        }

        public List<Object> createList(String inputString) {
            Queue<Character> input = createCharacterQueue(inputString);
            ArrayList<Object> result = new ArrayList<>();
            Stack<List<Object>> arrayStack = new Stack<>();
            arrayStack.add(result);
            input.remove();
            while (!input.isEmpty()) {
                if (input.peek() == ',') {
                    input.remove();
                } else if (input.peek() == '[') {
                    ArrayList<Object> newArray = new ArrayList<>();
                    arrayStack.peek().add(newArray);
                    arrayStack.push(newArray);
                    input.remove();
                } else if (input.peek() == ']') {
                    arrayStack.pop();
                    input.remove();
                } else {
                    StringBuilder number = new StringBuilder(String.valueOf(input.remove()));
                    while (!input.isEmpty() && input.peek() != ',' && input.peek() != ']') {
                        number.append(input.remove());
                    }
                    arrayStack.peek().add(Integer.valueOf(number.toString()));
                }
            }
            return result;
        }

        private Queue<Character> createCharacterQueue(String input) {
            Queue<Character> characters = new LinkedList<>();
            for (char character : input.toCharArray()) {
                characters.add(character);
            }
            return characters;
        }
    }

}
