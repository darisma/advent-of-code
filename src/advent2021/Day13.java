package advent2021;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day13 {

	private static final AdventInputReader AIR = new AdventInputReader();
	private static final String FILENAME = "2021input/day13.txt";

	static final List<String> INPUT_DATA = AIR.getStringStream(FILENAME)
			.collect(Collectors.toList());

	private static final String FOLD = "fold along ";
	private static final String COORD_SEPARATOR = ",";
	private static final String FOLD_SEPARATOR = "=";
	private static final String FOLD_DIRECTION_UP = "y";
	
	public static void main (String[] args) {
		System.out.println("Answer to A is: " + new Day13().solveAdventA());
		new Day13().solveAdventB();
		System.out.println("Answer to B is in the console, just above");
	}

	private long solveAdventA() {
		Map<Coord, Boolean> koordinaatit = generateCoordMap(INPUT_DATA);
		List<String> instructions = INPUT_DATA.stream().filter(e -> e.contains(FOLD)).collect(Collectors.toList());

		foldPage(koordinaatit, instructions.get(0));

		return koordinaatit.keySet().stream().filter(koordinaatit::get).count();
	}

	private long solveAdventB() {

		Map<Coord, Boolean> koordinaatit = generateCoordMap(INPUT_DATA);

		List<String> instructions = INPUT_DATA.stream().filter(e -> e.contains(FOLD)).collect(Collectors.toList());

		for(String foldInstruction : instructions) {
			foldPage(koordinaatit, foldInstruction);
		}

		int maxX = koordinaatit.keySet().stream().mapToInt(e -> e.x).max().getAsInt();
		int maxY = koordinaatit.keySet().stream().mapToInt(e -> e.y).max().getAsInt();
		printPoints(koordinaatit, maxX, maxY);

		return koordinaatit.keySet().stream().filter(koordinaatit::get).count();
	}

	private void foldPage(Map<Coord, Boolean> koordinaatit, String foldInstruction) {
		
		boolean foldingUp = foldInstruction.substring(foldInstruction.indexOf(FOLD_SEPARATOR) - 1, 
				foldInstruction.indexOf(FOLD_SEPARATOR)).equalsIgnoreCase(FOLD_DIRECTION_UP);

		int foldIndex = Integer.parseInt(foldInstruction.substring(foldInstruction.indexOf(FOLD_SEPARATOR) + 1,
				foldInstruction.length()));

		if(foldingUp) {
			koordinaatit.keySet().stream().filter(e -> (e.y > foldIndex) && (koordinaatit.get(e) != null && koordinaatit.get(e)))
			.forEach(f -> processCoord(f, koordinaatit, foldIndex, true));
			//remove redundant
			koordinaatit.keySet().stream().filter(e -> (e.y > foldIndex)).collect(Collectors.toList()).stream().forEach(koordinaatit::remove);
		}
		else {
			koordinaatit.keySet().stream().filter(e -> (e.x > foldIndex) && (koordinaatit.get(e) != null && koordinaatit.get(e)))
			.forEach(f -> processCoord(f, koordinaatit, foldIndex, false));

			koordinaatit.keySet().stream().filter(e -> (e.x > foldIndex)).collect(Collectors.toList()).stream().forEach(koordinaatit::remove);
		}

	}

	private void processCoord(Coord coord, Map<Coord, Boolean> koordinaatit, int foldIndex, boolean foldingUp) {
		if(foldingUp) {
			int resultingY = foldIndex - (coord.y - foldIndex);
			koordinaatit.put(new Coord(coord.x, resultingY), true);
		}else {
			int resultingX = foldIndex - (coord.x - foldIndex);
			koordinaatit.put(new Coord(resultingX, coord.y), true);
		}
	}

	private Map<Coord, Boolean> generateCoordMap(List<String> rivit) {

		Map<Coord, Boolean> koordinaatit = new HashMap<>();

		// create coords with dots to find out the dimensions
		for(String rivi : rivit) {
			if(rivi.indexOf(COORD_SEPARATOR) != -1) {
				koordinaatit.put(new Coord( rivi.split(COORD_SEPARATOR)[0], 
						rivi.split(COORD_SEPARATOR)[1] ), true);
			}
		}

		int maxX = koordinaatit.keySet().stream().mapToInt(e -> e.x).max().getAsInt();
		int maxY = koordinaatit.keySet().stream().mapToInt(e -> e.y).max().getAsInt();

		for(int i = 0; i <= maxX + 1; i++) {
			for(int j = 0; j <= maxY + 1; j++) {
				if(!koordinaatit.containsKey(new Coord(i, j))) {
					koordinaatit.put(new Coord(i,j), false);
				}
			}
		}
		return koordinaatit;
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

		@Override
		public String toString() {
			return "Coord [x=" + x + ", y=" + y +"]";
		}

	}

	private static void printPoints(Map<Coord, Boolean> map, int maxX, int maxY) {
		for(int i = 0; i < maxY ; i++) {
			for(int j = 0; j < maxX; j++) {
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

}
