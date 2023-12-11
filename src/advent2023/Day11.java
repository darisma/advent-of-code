package advent2023;

import java.util.List;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day11 {

	static AdventInputReader ir = new AdventInputReader();
	static final String FILENAME = "2023input/day11_test.txt";

	static List<String> INPUT_DATA = ir.getStringStream(FILENAME).collect(Collectors.toList());
	static char[][] map = generateMap( INPUT_DATA );
	
	static int leveys = INPUT_DATA.get(0).length();
	static int korkeus = INPUT_DATA.size();
	
	public static void main (String[] args) {
		System.out.println("Answer to 1a is: " + solveAdvent1a());
		System.out.println("Answer to 1b is: " + solveAdvent1b());
	}

    private static long solveAdvent1a() {

    	return 0;
	}

	private static long solveAdvent1b() {
    	int predictionSum = 0;
    	return predictionSum;
	}

	private static char[][] generateMap(List<String> rivit) {
		char[][] kartta = new char[rivit.size()][rivit.get(0).length()];
		for(int i = 0; i < rivit.size(); i++) {
			for(int j = 0; j < rivit.get(0).length(); j++) {
				kartta[i][j] = rivit.get(i).charAt(j);
			}
		}
		return kartta;
	}
}