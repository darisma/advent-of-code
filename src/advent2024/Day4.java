package advent2024;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import utils.AdventInputReader;

public class Day4 {

    static AdventInputReader ir = new AdventInputReader();
    static final String FILENAME = "2024input/day4.txt";

    static final List<String> wordData = ir.getStringStream(FILENAME).collect(Collectors.toList());
    static int leveys = wordData.get(0).length();
    static int korkeus = wordData.size();

    static char[][] map = generateMap( wordData );

    public static void main(String[] args) {
        System.out.println("Answer to 1a is: " + solveAdvent1a());
        System.out.println("Answer to 1b is: " + solveAdvent1b());
    }

    private static long solveAdvent1a() {
        List<String> searchData = new ArrayList<>();

        // Get all possible "Lines"
        // FiND all XMAS or SAMX substring from those lines

        // Horizontal
        searchData.addAll(wordData);

        // Vertical
        for(int i = 0; i < leveys; i++) {
            StringBuilder sb = new StringBuilder();
            for(int j = 0; j < korkeus; j++) {
                sb.append(getCharAt(i, j));
            }
            searchData.add(sb.toString());
        }

        // Diagonal \ vasen alapuoli nurkkapala jää
        for (int s = 0; s < korkeus; s++) {
            StringBuilder sb = new StringBuilder();
            for (int i = s, j = 0; i < korkeus && j < leveys; i++, j++) {
                sb.append(getCharAt(j, i));
            }
            searchData.add(sb.toString());
        }

        // Diagonal \ oikea ylä
        for (int s = 1; s < leveys; s++) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0, j = s; i < korkeus && j < leveys; i++, j++) {
                sb.append(getCharAt(j, i));
            }
            searchData.add(sb.toString());
        }

        // Diagonal / vasen ylä -
        for (int s = leveys - 1; s > 0; s--) {
            StringBuilder sb = new StringBuilder();
            for (int j = s, i = 0; j >= 0 && i < korkeus; j--, i++) {
                sb.append(getCharAt(j, i));
            }
            searchData.add(sb.toString());
        }


        // Diagonal / oikea ala
        for (int s = 1; s < korkeus; s++) {
            StringBuilder sb = new StringBuilder();
            for (int i = s, j = leveys - 1; i < korkeus && j >= 0; i++, j--) {
                sb.append(getCharAt(j, i));
            }
            searchData.add(sb.toString());
        }

        return searchData.stream()
                .mapToInt(d -> StringUtils.countMatches(d, "XMAS") + StringUtils.countMatches(d, "SAMX"))
                .sum();

    }

    private static char getCharAt(int i, int j) {
        return map[j][i];
    }

    private static long solveAdvent1b() {
        long result = 0;

        List<List<LetterAndLocation>> searchData = new ArrayList<>();

        // Diagonal \ vasen alapuoli nurkkapala jää
        for (int s = 0; s < korkeus; s++) {
            List<LetterAndLocation> list = new ArrayList<>();
            for (int i = s, j = 0; i < korkeus && j < leveys; i++, j++) {
                list.add(new LetterAndLocation(getCharAt(j,i), j, i));
            }
            searchData.add(list);
        }

        // Diagonal \ oikea ylä
        for (int s = 1; s < leveys; s++) {
            List<LetterAndLocation> list = new ArrayList<>();
            for (int i = 0, j = s; i < korkeus && j < leveys; i++, j++) {
                list.add(new LetterAndLocation(getCharAt(j,i), j, i));
            }
            searchData.add(list);
        }

        // Diagonal / vasen ylä
        for (int s = leveys - 1; s > 0; s--) {
            List<LetterAndLocation> list = new ArrayList<>();
            for (int j = s, i = 0; j >= 0 && i < korkeus; j--, i++) {
                list.add(new LetterAndLocation(getCharAt(j,i), j, i));
            }
            searchData.add(list);
        }

        // Diagonal / oikea ala
        for (int s = 1; s < korkeus; s++) {
            List<LetterAndLocation> list = new ArrayList<>();
            for (int i = s, j = leveys - 1; i < korkeus && j >= 0; i++, j--) {
                list.add(new LetterAndLocation(getCharAt(j,i), j, i));
            }
            searchData.add(list);
        }

        // find all MASes or SAMs from diagonals, save the coords of the middle A
        // if there are multiple findings of same coords, that is an X-Mas

        List<LetterAndLocation> xmases = findSAMsAndMASes(searchData);

        Set<String> aCoords = new HashSet<>();
        for (LetterAndLocation a : xmases) {
            String coordKey = a.x + "," + a.y;
            if (!aCoords.add(coordKey)) {
                result++;
            }
        }
        return result;
    }

    private static List<LetterAndLocation> findSAMsAndMASes(List<List<LetterAndLocation>> searchData) {
        List<LetterAndLocation> results = new ArrayList<>();

        for (List<LetterAndLocation> letterList : searchData) {
            for (int i = 1; i < letterList.size() - 1; i++) {
                char current = letterList.get(i).letter;
                char prev = letterList.get(i - 1).letter;
                char next = letterList.get(i + 1).letter;

                if ((current == 'A' && prev == 'S' && next == 'M') || (current == 'A' && prev == 'M' && next == 'S')) {
                    results.add(letterList.get(i));
                }
            }
        }

        return results;
    }

    private static char[][] generateMap(List<String> rivit) {
        int rows = rivit.size();
        int cols = rivit.isEmpty() ? 0 : rivit.get(0).length();
        char[][] kartta = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            kartta[i] = rivit.get(i).toCharArray();
        }

        return kartta;
    }

    static class LetterAndLocation {

        char letter;
        int x;
        int y;

        public LetterAndLocation(char letter, int x, int y) {
            this.letter = letter;
            this.x = x;
            this.y = y;
        }
    }
}