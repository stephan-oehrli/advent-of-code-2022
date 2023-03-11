package day_10;

import utils.FileUtil;

import java.io.FileNotFoundException;
import java.util.List;

public class DayTen {

    public static void main(String[] args) throws FileNotFoundException {
        List<String> input = FileUtil.readToList("day_10.txt");
        int signalStrength = new SignalStrengthCpu().calculateSignalStrength(input);
        String image = new ImageRendererCpu().renderImage(input);

        System.out.println("Signal strength is: " + signalStrength);
        System.out.println("\n" + image);
    }

}
