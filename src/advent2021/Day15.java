package advent2021;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day15 {

	private static final AdventInputReader AIR = new AdventInputReader();
	private static final String FILENAME = "2021input/day15.txt";

	static final List<String> INPUT_DATA = AIR.getStringStream(FILENAME)
			.collect(Collectors.toList());

	Coord start = new Coord(0,0);
	Coord end;
	
	int maxX = 100;
	int maxY = 100;
	
	int counter = 0;
	
	public static void main (String[] args) {
		System.out.println("Answer to A is: " + new Day15().solveAdventA());
		System.out.println("Answer to B is: " + new Day15().solveAdventB());
	}

	private long solveAdventA() {
		Map<Coord, int[]> map = generateCoordMap(INPUT_DATA);
		
		int countedMaxX = map.entrySet().stream().mapToInt(e -> e.getKey().x).max().orElse(0);
		int countedMaxY = map.entrySet().stream().mapToInt(e -> e.getKey().y).max().orElse(0);
		maxX = countedMaxX;
		maxY = countedMaxY;

		end = new Coord(maxX, maxY);
		
		return countLowestRisk(map);
		
	}

	private long countLowestRisk(Map<Coord, int[]> map) {
		Deque<Coord> queue = new ArrayDeque<>();
		queue.add(start);
		
		while(!queue.isEmpty()) {
			Coord processing = queue.pop();
			for(Coord neighbour : processing.getNeighbours(maxX, maxY)) {
				int currentNeighbourTotalCost = map.get(neighbour)[1];
				int currentBestCostToCurrent = map.get(processing)[1];
				int neighbourRisk = map.get(neighbour)[0];
				int costToNeighbour = currentBestCostToCurrent + neighbourRisk;
				if(currentNeighbourTotalCost > costToNeighbour) {
					map.put(neighbour, new int[] {neighbourRisk, costToNeighbour});
					queue.add(neighbour);
				}
			}
		}
		
		return map.get(end)[1];
	}

	private long solveAdventB() {

		Map<Coord, int[]> originalMap = generateCoordMap(INPUT_DATA);
		int countedMaxX = originalMap.entrySet().stream().mapToInt(e -> e.getKey().x).max().orElse(0);
		int countedMaxY = originalMap.entrySet().stream().mapToInt(e -> e.getKey().y).max().orElse(0);
		maxX = countedMaxX;
		maxY = countedMaxY;
		
		Map<Coord, int[]> stitchedMap = new HashMap<>();
		stitchedMap.putAll(originalMap);
		Map<Coord, int[]> tempMap = new HashMap<>(originalMap);
		for(int i = 1; i < 5; i ++) {
			tempMap = getNewMapIncrease(tempMap, i, true);
			stitchedMap.putAll(new HashMap<>(tempMap));
		}

		Map<Coord, int[]> finalMap = new HashMap<>();
		finalMap.putAll(stitchedMap);
		tempMap = new HashMap<>(stitchedMap);
		for(int i = 1; i < 5; i ++) {
			tempMap = getNewMapIncrease(tempMap, i, false);
			finalMap.putAll(new HashMap<>(tempMap));
		}
		maxX = finalMap.entrySet().stream().mapToInt(e -> e.getKey().x).max().orElse(0);
		maxY = finalMap.entrySet().stream().mapToInt(e -> e.getKey().y).max().orElse(0);
		//printPoints(finalMap);
		

		end = new Coord(maxX, maxY);
		
		return countLowestRisk(finalMap);
	}

	private Map<Coord, int[]> getNewMapIncrease(Map<Coord, int[]> mappi, int i, boolean xIncrease) {

		Set<Entry<Coord, int[]>> entries = mappi.entrySet();
		return entries.stream().map(e -> getEntry(e, i, xIncrease))
		  .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

	}

	private Entry<Coord, int[]> getEntry(Entry<Coord, int[]> e, int i, boolean xIncrease) {
		
		if(xIncrease) {
			Coord coord = new Coord(e.getKey().x + 100, e.getKey().y);
			return Map.entry(coord, new int[] {getNewRisk(e.getValue()[0], i), Integer.MAX_VALUE});
		}
		Coord coord = new Coord(e.getKey().x, e.getKey().y +100);
		return Map.entry(coord, new int[] {getNewRisk(e.getValue()[0], i), Integer.MAX_VALUE});
	}


	private int getNewRisk(int i, int step) {
		int value = i + 1;
		if (value == 10) {
			return 1;
		}else {
			return i + 1;
		}
	}


	public static class Coord {

		int x;
		int y;

		public Coord(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public List<Coord> getNeighbours(int width, int height) {
			return List.of(
					new Coord(x - 1, y), 
					new Coord(x + 1, y), 
					new Coord(x, y - 1), 
					new Coord(x, y + 1))
					.stream().filter(e -> e.x >= 0 && e.y >= 0 && e.x <= width && e.y <= height).collect(Collectors.toList());
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
	
	@SuppressWarnings("unused")
	private void printPoints(Map<Coord, int[]> map) {
		int countedMinX = map.entrySet().stream().mapToInt(e -> e.getKey().x).min().orElse(0);
		int countedMinY = map.entrySet().stream().mapToInt(e -> e.getKey().y).min().orElse(0);
		int countedMaxX = map.entrySet().stream().mapToInt(e -> e.getKey().x).max().orElse(0);
		int countedMaxY = map.entrySet().stream().mapToInt(e -> e.getKey().y).max().orElse(0);

		
		for(int i = countedMinX; i <= countedMaxX ; i++) {
			for(int j = countedMinY; j <= countedMaxY; j++) {
				if(j == countedMaxY ) {
					System.out.println(map.get(new Coord(i, j))[0]);
				}else {
					try {
					System.out.print(map.get(new Coord(i, j))[0]);
					}catch (Exception e) {
						System.out.println("jorma");
					}
				}
			}
			if(i == countedMaxX) {
				System.out.println("");
			}
		}
	}

	private Map<Coord, int[]> generateCoordMap(List<String> rivit) {
		Map<Coord, int[]> koordinaatit = new HashMap<>();
		for(int i = 0; i < rivit.size(); i++) {
			for(int j = 0; j < rivit.get(0).length(); j++) {
				Coord c = new Coord(i,j);
				int risk = Integer.parseInt("" + rivit.get(i).charAt(j));
				if(i == 0 && j == 0 ) {
					koordinaatit.put(c, new int[] {risk, 0});
				} else {
					
					koordinaatit.put(c, new int[] {risk, Integer.MAX_VALUE});
				}
				
			}
		}
		return koordinaatit;
	}	
}