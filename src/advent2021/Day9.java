package advent2021;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import utils.AdventInputReader;

public class Day9 {

	private static final AdventInputReader AIR = new AdventInputReader();
	private static final String FILENAME = "2021input/day9.txt";

	static final List<String> INPUT_DATA = AIR.getStringStream(FILENAME)
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

	private int solveAdventB() {
		Map<Coord, Integer> map = generateCoordMap(INPUT_DATA);
		List<Coord> lowPoints = getLowPointCoords(map);

		List<Integer> basinSizes = new ArrayList<>();
		
		for(Coord c : lowPoints) {
			Set<Coord> basin = new HashSet<>();
			getBasinSizeForLowPoint(c, basin, generateCoordMap(INPUT_DATA));
			basinSizes.add(basin.size());
		}
		
		return basinSizes.stream().sorted((e1, e2) -> e2.compareTo(e1)).limit(3).reduce(1, (a, b) -> a * b);
	}



	/**
	 * For solutionA
	 * 
	 * @param rivit
	 * @return
	 */
	private int[][] generateMap(List<String> rivit) {
		int[][] kartta = new int[rivit.size()][rivit.get(0).length()];
		for(int i = 0; i < rivit.size(); i++) {
			for(int j = 0; j < rivit.get(0).length(); j++) {
				kartta[i][j] = Integer.parseInt("" + rivit.get(i).charAt(j));
			}
		}
		return kartta;
	}
	
	/**
	 * For solution A
	 * 
	 * @param map
	 * @return
	 */
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

	/**
	 * For solution A
	 * 
	 * @param map
	 * @param x
	 * @param y
	 * @return
	 */
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
	
	private void getBasinSizeForLowPoint(Coord lowPoint, Set<Coord> checkedCoords, Map<Coord, Integer> map) {
		checkedCoords.add(lowPoint);
		for(Coord c : lowPoint.getNeighbours()) {
			if(!checkedCoords.contains(c) && underNineNotNull(c, map)){
				checkedCoords.add(c);
				getBasinSizeForLowPoint(c, checkedCoords, map);
			}
		}
	}

	private boolean underNineNotNull(Coord e, Map<Coord, Integer> map) {
		int maxRowIndex = map.keySet().stream().mapToInt(f -> f.x).max().orElseThrow();
		int maxColumnIndex = map.keySet().parallelStream().mapToInt(g -> g.y).max().orElseThrow();
		if(map.get(e) == null) {
			return false;
		}else if(map.get(e).intValue() == 9) {
			return false;
		}else if(e.x < 0 || e.y < 0) {
			return false;
		}else if(e.x > maxRowIndex || e.y > maxColumnIndex) {
			return false;
		}
		return true;
	}

	private Map<Coord, Integer> generateCoordMap(List<String> rivit) {
		Map<Coord, Integer> koordinaatit = new HashMap<>();
		for(int i = 0; i < rivit.size(); i++) {
			for(int j = 0; j < rivit.get(0).length(); j++) {
				koordinaatit.put(new Coord(i, j), Integer.parseInt("" + rivit.get(i).charAt(j)));
			}
		}
		return koordinaatit;
	}

	private List<Coord> getLowPointCoords(Map<Coord, Integer> map) {
		return map.keySet().stream().filter(e -> isLowPoint(e, map)).collect(Collectors.toList());
	}
	
	private boolean isLowPoint(Coord e, Map<Coord, Integer> map) {
		return e.getNeighbours().stream().allMatch(f -> map.get(f) == null || map.get(f).intValue() > map.get(e).intValue());
	}

    private class Coord {

    	int x;
    	int y;

    	public Coord(int x, int y) {
    		this.x = x;
    		this.y = y;
    	}
    	
        public List<Coord> getNeighbours() {
            return List.of(new Coord(x - 1, y), new Coord(x + 1, y), new Coord(x, y - 1), new Coord(x, y + 1));
       }

		@Override
		public String toString() {
			return "Coord [x=" + x + ", y=" + y + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Coord other = (Coord) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}
    }
	
}
