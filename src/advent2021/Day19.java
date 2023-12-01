package advent2021;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;


import utils.AdventInputReader;

public class Day19 {

	private static final AdventInputReader AIR = new AdventInputReader();
	private static final String FILENAME = "2021input/day19.txt";
	
	//private static final String ALGORITHM = "..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..###..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###.######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#..#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#......#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#.....####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#.......##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#";
	private static final String ALGORITHM = "##..#.####..#.#.##.#######.######.#####..#...##.###....#..#.##..#....#.#.##.#.#.#...###..###..#.#..#.#####.#.#.##..##.#.###.#..##.###.###...####.#####....#....#..#.#...#.#.#.#.#...#####...##..####.##.##...#.##.#####........#..#####..##.##..###########....#.##.#####.##..#####...#..#.#.##...#.#.#..#...####......#.#.####....##.#.##.####..##..##.#.......#...#.###########.....###.#######....##.#.#######..#.......###.##....#.#..#....##..#.###.#..#.###.###.##..##.#.#...#.#.....###.#.#....###..#.....##.#.#####.....";

	static final List<String> INPUT_DATA = AIR.getStringStream(FILENAME)
			.collect(Collectors.toList());

	public static void main (String[] args) {
		System.out.println("Answer to A is: " + new Day19().solveAdventA());
		System.out.println("Answer to B is: " + new Day19().solveAdventB());
	}

	private long solveAdventA() {
		Map<Coord, Boolean> koordinaatit = generateCoordMap(INPUT_DATA);

		printPoints(koordinaatit, 110, 110);
		
		//koordinaatit.entrySet().stream().forEach(e -> System.out.println(e.getKey().x + ", " + e.getKey().y +": " + e.getKey().generateBinary(koordinaatit)));
		
		for(int i = 0 ; i < 2 ; i ++) {
			koordinaatit = enhance(koordinaatit);

			printPoints(koordinaatit, 110, 110);
		
		}
		

		long litPixels = koordinaatit.entrySet().stream().filter(e -> e.getValue()).count();
		return litPixels;

	}


	private Map<Coord, Boolean> enhance(Map<Coord, Boolean> koordinaatit) {
		
		//System.out.println("LITPIXELS: " + koordinaatit.entrySet().stream().filter(e -> e.getValue()).count());

		Map<Coord, Boolean> enhancedMap = new HashMap<>();
		
		int minX = koordinaatit.entrySet().stream().mapToInt(e -> e.getKey().x).min().getAsInt();
		int maxX = koordinaatit.entrySet().stream().mapToInt(e -> e.getKey().x).max().getAsInt();
		int minY = koordinaatit.entrySet().stream().mapToInt(e -> e.getKey().y).min().getAsInt();
		int maxY = koordinaatit.entrySet().stream().mapToInt(e -> e.getKey().y).max().getAsInt();
		
		//System.out.println("minit: minX maxX minY maxY"+ minX + " " + maxX + " " + minY + " " + maxY + " " );
		
		for(int j = minY - 1; j <= maxY + 1; j++) {

			for (int i = minX -1; i <= maxX + 1; i++){
				if(coordShouldBeLit(i, j, koordinaatit, i%2 == 0)) {
					enhancedMap.put(new Coord(i, j), true);
			    }else {
			    	enhancedMap.put(new Coord(i, j), false);
			    }
			}
		}
		//System.out.println("LITPIXELS now: " + enhancedMap.entrySet().stream().filter(e -> e.getValue()).count());


		
		return enhancedMap;
	}

	private boolean coordShouldBeLit(int i, int j, Map<Coord, Boolean> koordinaatit, boolean evenTurn) {
		
		Optional<Entry<Coord, Boolean>> coordEntry = koordinaatit.entrySet().stream().filter(e -> e.getKey().equals(new Coord(i, j))).findFirst();
		int index;
		if(!coordEntry.isPresent()) {
			Coord createdCoord = new Coord(i, j);
			koordinaatit.put(createdCoord, false);
			index = Integer.parseInt(createdCoord.generateBinary(koordinaatit, evenTurn), 2);
		}else {
			index = Integer.parseInt(coordEntry.get().getKey().generateBinary(koordinaatit, evenTurn), 2);
			
		}
		 coordEntry = koordinaatit.entrySet().stream().filter(e -> e.getKey().equals(new Coord(i, j))).findFirst();
		//System.out.println("For x, y " + i +", " + j+", Binary string was " + coordEntry.get().getKey().generateBinary(koordinaatit)+ " and index was " + index);
		 //System.out.println("Index is " + index);
		//System.out.println("Index was " + index);
		if(ALGORITHM.charAt(index) == '#') {
		//	System.out.println("Algorithm says we should light pixel: i,j"+ i +", " +j);
			return true;
		}
		//System.out.println("Algorithm says we should dim pixel: i,j"+ i +", " +j);
		return false;
		
	}

	
	
	private long solveAdventB() {
		Map<Coord, Boolean> koordinaatit = generateCoordMap(INPUT_DATA);

		//printPoints(koordinaatit, 100, 100);
		
		//koordinaatit.entrySet().stream().forEach(e -> System.out.println(e.getKey().x + ", " + e.getKey().y +": " + e.getKey().generateBinary(koordinaatit)));
		
		//for(int i = 0 ; i < 50 ; i ++) {
		//	System.out.println("round: "+i);
			//koordinaatit = enhance(koordinaatit);

			//printPoints(koordinaatit, 100, 100);
		
		//}
		

		long litPixels = koordinaatit.entrySet().stream().filter(e -> e.getValue()).count();
		
		return litPixels;
	}

	private Map<Coord, Boolean> generateCoordMap(List<String> rivit) {

		Map<Coord, Boolean> koordinaatit = new HashMap<>();

		// create coords with dots to find out the dimensions
		for(int j = 0; j < rivit.size() ; j++) {
			String rivi = rivit.get(j);
			for (int i = 0; i < rivi.length(); i++){
				if(rivi.charAt(i) == '#') {
			    	koordinaatit.put(new Coord(i, j), true);
			    }else {
			    	koordinaatit.put(new Coord(i, j), false);
			    }
			}
		}
		
		for(int y = -5 ; y < rivit.size() + 5 ; y++) {
			for(int x = -5 ; x < rivit.get(0).length() + 5 ; x++) {
			if(!koordinaatit.containsKey(new Coord(x, y))) {
				koordinaatit.put(new Coord(x,y),false);
			}
		}}

		return koordinaatit;
	}
	
	private static void printPoints(Map<Coord, Boolean> map, int maxX, int maxY) {
		for(int i = -maxY; i < maxY ; i++) {
			for(int j = -maxX ; j < maxX; j++) {
				if(j == maxX - 1) {
					System.out.println(getGraphics(map.get(new Coord(j, i))));
				}else {
					System.out.print(getGraphics(map.get(new Coord(j, i))));
				}
			}
			if(i == maxY - 1) {
				System.out.println("");
			}
		}
	}

	private static String getGraphics(Boolean b) {
		if(b != null && b.booleanValue()) {
			return "#";
		}
		return ".";
	}
	
	public static class Coord {

		int x;
		int y;

		public Coord(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public Coord(String x, String y) {
			this.x = Integer.parseInt(x);
			this.y = Integer.parseInt(y);

		}
		
		public List<Coord> getNeighboursAndSelf() {
			return List.of(
					new Coord(x - 1, y - 1),
					new Coord(x, y - 1), 
					new Coord(x + 1, y - 1),
					new Coord(x - 1, y),
					new Coord(x, y),
					new Coord(x + 1, y),
					new Coord(x - 1, y + 1), 
					new Coord(x, y + 1),
					new Coord(x + 1, y + 1));
		}

		public String generateBinary(Map<Coord, Boolean> koordinaatit, boolean evenTurn) {
			StringBuilder sb = new StringBuilder();
			for(Coord coord : getNeighboursAndSelf()) {
				if(koordinaatit.containsKey(coord) && koordinaatit.get(coord).booleanValue()) {
					sb.append("1");
				}
				else {
					if(ALGORITHM.charAt(0) == '#') {
						if(evenTurn) {
							sb.append("1");
						}else {
							sb.append("0");
						}

					}
					if(ALGORITHM.charAt(0) == '.') {
						sb.append("0");
						
					}
				}
			}
			return sb.toString();
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