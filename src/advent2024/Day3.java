package advent2024;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import utils.AdventInputReader;

public class Day3 {

    static AdventInputReader ir = new AdventInputReader();
    static final String FILENAME = "2024input/day3.txt";

    static final List<String> memoryData = ir.getStringStream(FILENAME).collect(Collectors.toList());

    public static void main(String[] args) {
        System.out.println("Answer to 1a is: " + solveAdvent1a());
        System.out.println("Answer to 1b is: " + solveAdvent1b());
    }

    private static long solveAdvent1a() {
        int result = 0;
        for(String line : memoryData) {
            int index = 0;
            while (line.indexOf("mul(", index) != -1) {
                int mul = line.indexOf("mul(", index) + 4;
                String sisalto = line.substring(mul, line.indexOf(")", mul));
                if(validContents(sisalto)) {
                    result += Math.multiplyFull(Integer.parseInt(sisalto.split(",")[0]), Integer.parseInt(sisalto.split(",")[1]));
                }
                index = mul;
            }
        }
        return result;
    }

    private static long solveAdvent1b() {
        long result = 0;
        boolean doing = true;
        for(String line : memoryData) {
            int index = 0;
            List<Integer> doingIndices = new ArrayList<>();
            doing = getDoableIndices(doing, line, doingIndices);
            while (line.indexOf("mul(", index) != -1) {
                int mul = line.indexOf("mul(", index) + 4;
                String multiplicationContent = line.substring(mul, line.indexOf(")", mul));
                if(validContents(multiplicationContent) && doingIndices.contains(Integer.valueOf(mul))) {
                    result += doMultiplication(multiplicationContent);
                }
                index = mul;
            }
        }
        return result;
    }


    private static long doMultiplication(String multiplicationContent) {
        return Math.multiplyFull(Integer.parseInt(multiplicationContent.split(",")[0]), Integer.parseInt(multiplicationContent.split(",")[1]));
    }

    private static boolean validContents(String multiplicationContent) {
        String[] split = multiplicationContent.split(",");
        return split.length == 2 && isNumeric(split[0]) && isNumeric(split[1]);
    }

    private static boolean getDoableIndices(Boolean doCalc, String line, List<Integer> doingIndices) {

        boolean lineLeft = true;
        int index = 0;
        while(lineLeft) {
            if(doCalc) {
                int nextdont = line.indexOf("don't()", index);
                if(nextdont == -1) {
                    IntStream.range(index, line.length()).forEach(e -> doingIndices.add(e));
                    lineLeft = false;
                    index = 0;
                }else {
                    IntStream.range(index, nextdont).forEach(e -> doingIndices.add(e));
                    doCalc = false;
                    index = nextdont;
                }
            }else {
                int nextDo = line.indexOf("do()", index);
                if(nextDo == -1) {
                    lineLeft = false;
                    index = 0;
                }else {
                    doCalc = true;
                    index = nextDo;
                }
            }
        }
        return doCalc;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}