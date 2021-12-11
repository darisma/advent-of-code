package advent2021;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day11 {

	private static final AdventInputReader AIR = new AdventInputReader();
	private static final String FILENAME = "2021input/day11.txt";

	static final List<String> INPUT_DATA = AIR.getStringStream(FILENAME)
			.collect(Collectors.toList());

	public static void main (String[] args) {
		System.out.println("Answer to A is: " + new Day11().solveAdventA());
		System.out.println("Answer to B is: " + new Day11().solveAdventB());
	}

	private long solveAdventA() {
		Map<Coord, Integer> map = generateCoordMap(INPUT_DATA);

		int totalflashes = 0;

		for (int i = 0 ; i < 100 ; i++) {
			totalflashes += advanceTurn(map);
		}

		return totalflashes;
	}

	private long solveAdventB() {
		Map<Coord, Integer> map = generateCoordMap(INPUT_DATA);

		int flashesThisTurn = 0;
		int i = 0;
		do {
			i++;
			flashesThisTurn = advanceTurn(map);
		} while ( flashesThisTurn != 100);


		return i;
	}

	private int advanceTurn(Map<Coord, Integer> map) {
		List<Entry<Coord, Integer>> flashedThisTurn = new ArrayList<>();
		List<Entry<Coord, Integer>> overNine;

		// bump all
		map.entrySet().stream().forEach(e -> e.setValue(e.getValue() + 1));

		do {
			overNine = map.entrySet().stream().filter(e -> e.getValue() > 9).collect(Collectors.toList());
			flashedThisTurn.addAll(overNine);
			overNine.stream().forEach(e -> e.setValue(0));
			overNine.stream().forEach(e -> bumpNeighbours(e, map));
		} while (!overNine.isEmpty()); 

		// reset all flashed back to 0
		map.entrySet().stream().filter(e -> flashedThisTurn.contains(e)).forEach(f -> f.setValue(0));

		return flashedThisTurn.size();
	}

	private void bumpNeighbours(Entry<Coord, Integer> flashed, Map<Coord, Integer> map) {
		map.entrySet().stream().filter(e -> flashed.getKey().getNeighbours().contains(e.getKey())).forEach(f -> f.setValue(f.getValue() + 1 ));;
	}

	public static class Coord {

		int x;
		int y;
		boolean hasFlashedThisTurn = false;

		public Coord(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public List<Coord> getNeighbours() {
			return List.of(
					new Coord(x - 1, y), 
					new Coord(x + 1, y), 
					new Coord(x, y - 1), 
					new Coord(x, y + 1),
					new Coord(x - 1, y - 1), 
					new Coord(x + 1, y - 1), 
					new Coord(x + 1, y + 1), 
					new Coord(x - 1, y + 1))
					.stream().filter(e -> e.x >= 0 && e.y >= 0 && e.x < 10 && e.y < 10).collect(Collectors.toList());
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
	
//	private static void printPoints(Map<Coord, Integer> map) {
//		int dimension = (int)Math.sqrt(map.size());
//		for(int i = 0; i < dimension ; i++) {
//			for(int j = 0; j < dimension; j++) {
//				if(j == dimension - 1) {
//					System.out.println(map.get(new Coord(i, j)));
//				}else {
//					System.out.print(map.get(new Coord(i, j)));
//				}
//			}
//			if(i == dimension - 1) {
//				System.out.println("");
//			}
//		}
//	}

	private Map<Coord, Integer> generateCoordMap(List<String> rivit) {
		Map<Coord, Integer> koordinaatit = new HashMap<>();
		for(int i = 0; i < rivit.size(); i++) {
			for(int j = 0; j < rivit.get(0).length(); j++) {
				koordinaatit.put(new Coord(i, j), Integer.parseInt("" + rivit.get(i).charAt(j)));
			}
		}
		return koordinaatit;
	}	

}
