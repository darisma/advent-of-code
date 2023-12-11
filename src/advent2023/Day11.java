package advent2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day11 {

    static AdventInputReader ir = new AdventInputReader();
    static final String FILENAME = "2023input/day11.txt";

    static List<String> INPUT_DATA = ir.getStringStream(FILENAME).collect(Collectors.toList());

    public static void main (String[] args) {
        System.out.println("Answer to 1a is: " + solveAdvent1a());
        System.out.println("Answer to 1b is: " + solveAdvent1b());
    }

    private static long solveAdvent1a() {

        // check input data, for each empty line, add another empty line at index + 1
        // iterate through original, create a new one
        List<String> horizontalExpansion = new ArrayList<>();
        for(String s : INPUT_DATA) {
            if(s.matches("\\.*")) {
                horizontalExpansion.add(s);
            }
            horizontalExpansion.add(s);
        }

        // check data, for each empty column, add another column at index + 1
        // iterate through previous and create a new one
        for(int i = horizontalExpansion.get(0).length() - 1; i >= 0; i --) {
            if(columnJustSpace(horizontalExpansion, i)) {
                addColumn(horizontalExpansion, i);
            }
        }

        // make note of the locations of each galaxy
        HashMap<Integer, Coord> galaxies = new HashMap<>();
        int galaxyNumber = 1;
        for(int i = 0; i < horizontalExpansion.get(0).length(); i ++) {
            for (int j = 0; j < horizontalExpansion.size(); j ++) {
                if(horizontalExpansion.get(j).charAt(i) == '#') {
                    galaxies.put(galaxyNumber, new Coord(i, j));
                    galaxyNumber ++;
                }
            }
        }

        // find shortest path for pairs: 1 and 2, 3, 4, .... n
        // abs(index y1 - index y2) + abs(index x1 - index x2)
        // then 2 and 3, 4, 5 ... n
        // then 3 and 4, 5, 6 ... n
        // until n - 1 and n
        // sum shortest paths
        List<Integer> shortestPaths = new ArrayList<>();

        for(int i = 1; i < galaxies.size(); i ++) {
            Coord galaxy = galaxies.get(i);
            for(int j = i + 1; j < galaxies.size() + 1; j++) {
                shortestPaths.add(pathLength(galaxy, galaxies.get(j)));
            }
        }

        return shortestPaths.stream().mapToInt(e -> e).sum();
    }

    private static long solveAdvent1b() {

        List<Integer> horizontalIndices = new ArrayList<>();

        for(int i = 0; i < INPUT_DATA.size(); i ++) {
            if(INPUT_DATA.get(i).matches("\\.*")) {
                horizontalIndices.add(i);
            }
        }

        List<Integer> verticalIndices = new ArrayList<>();

        for(int i = 0; i < INPUT_DATA.get(0).length(); i++) {
            if(columnJustSpace(INPUT_DATA, i)) {
                verticalIndices.add(i);
            }
        }

        HashMap<Integer, Coord> galaxies = new HashMap<>();
        int galaxyNumber = 1;
        for(int i = 0; i < INPUT_DATA.get(0).length(); i ++) {
            for (int j = 0; j < INPUT_DATA.size(); j ++) {
                if(INPUT_DATA.get(j).charAt(i) == '#') {
                    galaxies.put(galaxyNumber, new Coord(i, j));
                    galaxyNumber ++;
                }
            }
        }

        List<Long> shortestPaths = new ArrayList<>();

        for(int i = 1; i < galaxies.size(); i ++) {
            Coord galaxy = galaxies.get(i);
            for(int j = i + 1; j < galaxies.size() + 1; j++) {
                shortestPaths.add(pathLengthWithSpace(galaxy, galaxies.get(j), horizontalIndices, verticalIndices));
            }
        }

        return shortestPaths.stream().mapToLong(e -> e).sum();
    }

    private static long pathLengthWithSpace(Coord galaxy, Coord coord, List<Integer> horizontalIndices,
            List<Integer> verticalIndices) {
        long pathWithoutExpansion = Math.abs(galaxy.x - coord.x) + Math.abs(galaxy.y - coord.y);
        long horizontalIndicesPassed = horizontalIndices.stream().filter(e -> e > Math.min(galaxy.y, coord.y) && e < Math.max(galaxy.y, coord.y)).count();
        long verticalIndicesPassed = verticalIndices.stream().filter(e -> e > Math.min(galaxy.x, coord.x) && e < Math.max(galaxy.x, coord.x)).count();

        return pathWithoutExpansion + 999999 * horizontalIndicesPassed + 999999 * verticalIndicesPassed;
    }

    private static Integer pathLength(Coord galaxy, Coord coord) {
        return Math.abs(galaxy.x - coord.x) + Math.abs(galaxy.y - coord.y);
    }

    private static void addColumn(List<String> horizontalExpansion, int index) {
        for(int i = 0; i < horizontalExpansion.size(); i++) {
            String original = horizontalExpansion.get(i);
            horizontalExpansion.set(i, original.substring(0, index) + "." + original.substring(index));
        }
    }

    private static boolean columnJustSpace(List<String> horizontalExpansion, int index) {
        for(int i = 0; i < horizontalExpansion.size(); i++) {
            if(horizontalExpansion.get(i).charAt(index) != '.') {
                return false;
            }
        }
        return true;
    }

    static class Coord{

        int x;
        int y;

        public Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Coord [x=" + x + ", y=" + y + "]";
        }
    }
}