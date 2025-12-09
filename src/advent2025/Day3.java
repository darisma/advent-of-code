package advent2025;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.AdventInputReader;

public class Day3 {

    private static final Logger log = LoggerFactory.getLogger(Day3.class);

    static final String FILENAME = "2025input/day3.txt";
    static final List<String> batteries = new AdventInputReader().getStringStream(FILENAME).collect(Collectors.toList());
    static final List<List<Integer>> banks = batteries.stream()
            .map(line -> line.chars().mapToObj(c -> c - '0').collect(Collectors.toList()))
            .collect(Collectors.toList());
    static final List<Bank> batteryBanks = banks.stream()
            .map(Bank::new)
            .collect(Collectors.toList());

    public static void main(String[] args) {
        System.out.println("Answer to 1a is: " + solveAdvent1a());
        System.out.println("Answer to 1b is: " + solveAdvent1b());
    }

    private static long solveAdvent1a() {
        long count = 0;
        for (Bank bank : batteryBanks) {
            int maxCharge = -1;
            int maxPosition = -1;
            for(Battery b : bank.batteries.subList(0, bank.batteries.size() - 1)) {
                if(b.charge > maxCharge) {
                    maxCharge = b.charge;
                    maxPosition= b.position;
                }
            }

            int secondMaxCharge = -1;
            for(Battery b : bank.batteries.subList(maxPosition + 1, bank.batteries.size())) {
                if(b.charge > secondMaxCharge) {
                    secondMaxCharge = b.charge;
                }
            }

            count += Integer.parseInt(String.valueOf(maxCharge) + String.valueOf(secondMaxCharge));
        }

        return count;
    }

    private static long solveAdvent1b() {
        long count = 0;

        for (Bank bank : batteryBanks) {
            StringBuilder joltageString = new StringBuilder();
            int maxCharge = -1;
            int maxPosition = -1;
            //needs to be before the last 11 positions
            for(Battery b : bank.batteries.subList(0, bank.batteries.size() - 11)) {
                if(b.charge > maxCharge) {
                    maxCharge = b.charge;
                    maxPosition= b.position;
                }
            }

            joltageString.append(maxCharge);

            for(int i = 1; i < 12; i++) {
                int nextCharge = -1;
                for(Battery b : bank.batteries.subList(maxPosition + 1, bank.batteries.size() - 11 + i)) {
                    if(b.charge > nextCharge) {
                        nextCharge = b.charge;
                        maxPosition= b.position;
                    }
                }

                joltageString.append(nextCharge);
            }
            count += Long.parseLong(joltageString.toString());
        }

        return count;
    }

    private static class Bank {
        List<Battery> batteries;

        public Bank(List<Integer> batteries) {
            this.batteries = IntStream.range(0, batteries.size())
                    .mapToObj(i -> new Battery(i, batteries.get(i)))
                    .collect(Collectors.toList());
        }
    }

    private static class Battery {
        int position;
        int charge;

        public Battery(int position, int charge) {
            this.position = position;
            this.charge = charge;
        }
    }
}