package advent2025;

import java.util.List;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day1 {

    static AdventInputReader ir = new AdventInputReader();
    static final String FILENAME = "2025input/day1.txt";

    static final List<String> rotationData = ir.getStringStream(FILENAME).collect(Collectors.toList());

    public static void main(String[] args) {
        System.out.println("Answer to 1a is: " + solveAdvent1a());
        System.out.println("Answer to 1b is: " + solveAdvent1b());
    }

    private static int solveAdvent1a() {
        int pointer = 50;
        int timesResultedIn0 = 0;

        for(String rotation : rotationData) {
            String direction = rotation.substring(0, 1);
            int amount = Integer.parseInt(rotation.substring(1));

            if (direction.equals("L")) {
                pointer = Math.floorMod(pointer - amount, 100);
                if (pointer == 0) {
                    timesResultedIn0++;
                }
            } else if (direction.equals("R")) {
                pointer = (pointer + amount) % 100;
                if (pointer == 0) {
                    timesResultedIn0++;
                }
            }
        }

        return timesResultedIn0;
    }

    private static long solveAdvent1b() {
        int pointer = 50;
        int timesAt0 = 0;

        for(String rotation : rotationData) {
            String direction = rotation.substring(0, 1);
            int amount = Integer.parseInt(rotation.substring(1));

            if (direction.equals("L")) {

                if(pointer == 0) {
                    pointer = pointer - amount;

                    while (pointer < 0) {
                        timesAt0++;
                        pointer = pointer + 100;
                    }
                    if(pointer == 0) {
                        timesAt0++;
                    }
                    timesAt0--; // lähdettiin 0:sta, ei lasketa sitä
                }else {
                    pointer = pointer - amount;

                    while (pointer < 0) {
                        timesAt0++;
                        pointer = pointer + 100;
                    }
                    if(pointer == 0) {
                        timesAt0++;
                    }
                }


            } else if (direction.equals("R")) {

                pointer = pointer + amount;

                while(pointer >= 100) {
                    timesAt0++;
                    pointer = pointer - 100;
                }
            }

        }

        return timesAt0;
    }
}