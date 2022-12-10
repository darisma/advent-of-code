package advent2022;

import java.util.List;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day8 {


	private static final AdventInputReader AIR = new AdventInputReader();
	private static final String FILENAME = "2022input/day8.txt";

	static final List<String> INPUT_DATA = AIR.getStringStream(FILENAME)
			.collect(Collectors.toList());

	public static void main (String[] args) {
		System.out.println("Answer to A is: " + new Day8().solveAdventA());
		System.out.println("Answer to B is: " + new Day8().solveAdventB());
	}

	private long solveAdventA() {
		
		int[][] map = generateMap( INPUT_DATA );
		
		long visibleTrees = countVisibleTrees(map);
		
		return visibleTrees;
	}


	private long solveAdventB() {
		int[][] map = generateMap( INPUT_DATA );
		
		return generateScenicMap(map);
	}
	
	private long generateScenicMap(int[][] map) {

		int maxValue = 0;
		
		for(int s = 0; s < map.length; s++) {
			for(int t = 0; t < map[0].length; t++) {
				int scenicScore = calculateScenicScoreFor(map, s, t);
				if(scenicScore > maxValue) {
					maxValue = scenicScore;
				}
			}
		}
		
		return maxValue;
	}

	private int calculateScenicScoreFor(int[][] map, int i, int j) {
		int seenTreesInNorth = 1;
		int seenTreesInSouth = 1;
		int seenTreesInWest = 1;
		int seenTreesInEast = 1;
		
		if(i == 0 || i == map[0].length - 1) {
			return 0;
		}
		
		if(j == 0 || j == map.length - 1) {
			return 0;
		}
		
		//north
		for(int x = i - 1; x > 0; x--) {
			
			if(map[x][j] >= map[i][j]) {
				break;
			}
			seenTreesInNorth ++;
		}
		// south
		for(int x = i + 1; x < map[0].length - 1; x++) {

			if(map[x][j] >= map[i][j]) {
				break;
			}
			seenTreesInSouth ++;

		}
		// west
		for(int y = j - 1; y > 0; y--) {

			if(map[i][y] >= map[i][j]) {
				break;
			}
			seenTreesInWest ++;

		}
		// east
		for(int y = j + 1; y < map.length - 1; y++) {

			if(map[i][y] >= map[i][j]) {
			break;
			}
			seenTreesInEast ++;
		}

		return seenTreesInEast * seenTreesInNorth * seenTreesInSouth * seenTreesInWest;
	}

	private long countVisibleTrees(int[][] map) {
		long visible = 0;
		
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[0].length; j++) {
				if(isVisible(map, i, j)) {
					visible ++;
				}else {
				}
			}
		}
		
		return visible;
	}

	private boolean isVisible(int[][] map, int i, int j) {
		if(i == 0 || i == map[0].length - 1) {
			return true;
		}
		if(j == 0 || j == map.length - 1) {
			return true;
		}
		int value = map[i][j];
		
		boolean visibleFromWest = false;
		boolean visibleFromEast = false;
		boolean visibleFromNorth = false;
		boolean visibleFromSouth = false;
		//west
		for(int x = 0; x < i; x++) {
			if(map[x][j] >= value) {
				visibleFromWest = false;
				break;
			}
			visibleFromWest = true;
		}
		// east
		for(int x = i + 1; x < map[0].length; x++) {
			if(map[x][j] >= value) {
				visibleFromEast = false;
				break;
			}
			visibleFromEast = true;
		}
		// north
		for(int y = 0; y < j; y++) {
			if(map[i][y] >= value) {
				visibleFromNorth = false;
				break;
			}
			visibleFromNorth = true;
		}
		// south
		for(int y = j + 1; y < map.length; y++) {
			if(map[i][y] >= value) {
				visibleFromSouth = false;
				break;
			}
			visibleFromSouth = true;
		}
		
		return visibleFromEast || visibleFromNorth || visibleFromSouth || visibleFromWest;
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
}
