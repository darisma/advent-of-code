package advent2022;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day18 {


	private static final AdventInputReader AIR = new AdventInputReader();
	private static final String FILENAME = "2022input/day18.txt";

	static final List<String> INPUT_DATA = AIR.getStringStream(FILENAME)
			.collect(Collectors.toList());

	public static void main (String[] args) {
		System.out.println("Answer to A is: " + new Day18().solveAdventA());
		System.out.println("Answer to B is: " + new Day18().solveAdventB());
	}

	private long solveAdventA() {

		Set<Coord> koordinates = generateCoordMap(INPUT_DATA);
		
		int totalOpen = 0;
		
		for(Coord c: koordinates) {
			totalOpen += getOpenSurfaces(c, koordinates);
		}
		
		return totalOpen;
	}


	int getOpenSurfaces(Coord c, Set<Coord> koordinates) {
		int open = 6;
		// if a neighbour is found from koordinates, open --
		for(Coord neighbour : c.getNeighbours()) {
			if(koordinates.contains(neighbour)) {
				open --;
			}
		}
	
		return open;
	}

	private long solveAdventB() {
		// only exterior surfaces (drop all cubes that are trapped inside other cubes)
		return 0;
	}


	public static class Coord {

		int x;
		int y;
		int z;

		public Coord(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}

		public Set<Coord> getNeighbours() {
			Set<Coord> neighbours = new HashSet<>();
			neighbours.add(new Coord(x - 1, y, z)); 
			neighbours.add(new Coord(x + 1, y, z)); 
			neighbours.add(new Coord(x, y - 1, z)); 
			neighbours.add(new Coord(x, y + 1, z));
			neighbours.add(new Coord(x, y, z - 1));
			neighbours.add(new Coord(x, y, z + 1));
				
			return neighbours;
		}

		@Override
		public String toString() {
			return "Coord [x=" + x + ", y=" + y + ", z=" + z + " ]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
			result = prime * result + z;
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
			if (z != other.z)
				return false;
			return true;
		}
	}
	
	private Set<Coord> generateCoordMap(List<String> rivit) {
		Set<Coord> koordinaatit = new HashSet<>();
		
		for(String rivi : rivit) {
			koordinaatit.add(new Coord(Integer.parseInt(rivi.split(",")[0]), Integer.parseInt(rivi.split(",")[1]), Integer.parseInt(rivi.split(",")[2])));
		}
		
		return koordinaatit;
	}	

}
