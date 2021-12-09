package advent2021;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import utils.AdventInputReader;

public class Day9 {

	private static final AdventInputReader AIR = new AdventInputReader();
	private static final String FILENAME = "2021input/day9.txt";

	static final List<String> INPUT_DATA = AIR.getStringStream(FILENAME)
			.collect(Collectors.toList());

	static final List<char[]> karttaRivit = INPUT_DATA.stream().
			map(e -> e.toCharArray())
			.collect(Collectors.toList());

	public static void main (String[] args) {
		System.out.println("Answer to A is: " + new Day9().solveAdventA());
		System.out.println("Answer to B is: " + new Day9().solveAdventB());

	}

	private long solveAdventA() {

		int[][] map = generateMap( INPUT_DATA );

		List<Integer> lowPoints = getLowPointsFromMap(map);

		return lowPoints.stream().reduce(0, (a, b) -> a + b + 1 );

	}

	private List<Integer> getLowPointsFromMap(int[][] map) {
		List<Integer> lowPoints = new ArrayList<>();
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[0].length; j++) {
				if(isLowSpot(map, i, j)) {
					lowPoints.add(map[i][j]);
				}
			}
		}
		return lowPoints;
	}

	private boolean isLowSpot(int[][] map, int x, int y) {
		int spot = map[x][y];
		if (x > 0 && spot >= map[x - 1][y] 
				|| (x < map.length - 1 && spot >= map[x + 1][y])
				|| ( y > 0 && spot >= map[x][y - 1]) 
				|| (y < map[0].length - 1 && spot >= map[x][y + 1])) {
			return false;
		}

		return true;

	}

	private int[][] generateMap(List<String> rivit) {
		int[][] kartta = new int[rivit.size()][rivit.get(0).length()];
		for(int i = 0; i < rivit.size(); i++) {
			for(int j = 0; j < rivit.get(0).length(); j++) {
				kartta[i][j] = Integer.parseInt("" + rivit.get(i).charAt(j));
			}
		}
		return kartta;
	}

	private long solveAdventB() {

		return 0;
	}

}
