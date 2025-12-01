package advent2025;

import java.util.List;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day1 {
    static final String FILENAME = "2025input/day1.txt";
    static final List<String> rotationData = new AdventInputReader().getStringStream(FILENAME).collect(Collectors.toList());
    static final List<Rotation> rotations = rotationData.stream().map(Day1::parseRotation).collect(Collectors.toList());

    public static void main(String[] args) {
        System.out.println("Answer to 1a is: " + solveAdvent1a(rotationData));
        System.out.println("Answer to 1b is: " + solveAdvent1b(rotationData));
    }

    private static class Rotation {
        final char direction;
        final int amount;
        Rotation(char direction, int amount) {
            this.direction = direction;
            this.amount = amount;
        }
    }

    private static Rotation parseRotation(String rotation) {
        return new Rotation(rotation.charAt(0), Integer.parseInt(rotation.substring(1)));
    }

    private static int solveAdvent1a(List<String> data) {
        int pointer = 50;
        int timesResultedIn0 = 0;
        for (Rotation rotation: rotations) {
            // tehdään pyöräytys ja katsotaan osuiko nollaan
            if (rotation.direction == 'L') {
                pointer = Math.floorMod(pointer - rotation.amount, 100);
            } else if (rotation.direction == 'R') {
                pointer = (pointer + rotation.amount) % 100;
            }
            if (pointer == 0) {
                timesResultedIn0++;
            }
        }
        return timesResultedIn0;
    }

    private static long solveAdvent1b(List<String> data) {
        int pointer = 50;
        int timesAt0 = 0;
        for (String rotationStr : data) {
            boolean wasAt0 = (pointer == 0); // pyörähdys lähtee nollasta ?
            Rotation rotation = parseRotation(rotationStr);
            if (rotation.direction == 'L') {
                    pointer -= rotation.amount;
                    while (pointer < 0) {
                        timesAt0++;
                        pointer += 100;
                    }
                    if (pointer == 0) {
                        timesAt0++;
                    }
                    if (wasAt0) {
                        timesAt0--; // started from 0, don't count it
                    }
            } else if (rotation.direction == 'R') {
                pointer += rotation.amount;
                while (pointer >= 100) {
                    timesAt0++;
                    pointer -= 100;
                }
            }
        }
        return timesAt0;
    }
}