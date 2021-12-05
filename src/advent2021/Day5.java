package advent2021;

import java.util.List;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day5 {

	private static final AdventInputReader AIR = new AdventInputReader();
	private static final String FILENAME = "2021input/day5.txt";

	static final List<String> INPUT_DATA = AIR.getStringStream(FILENAME)
			.collect(Collectors.toList());

	static final int ROW_LENGTH = INPUT_DATA.get(0).length();

	public static void main (String[] args) {
		System.out.println("Answer to A is: " + new Day5().solveAdventA());
		System.out.println("Answer to B is: " + new Day5().solveAdventB());

	}

	private int solveAdventA() {

		List<VentLine> ventLines = INPUT_DATA.stream().map(Day5::parseVentLine).filter(VentLine::isHorizontalOrVertical).collect(Collectors.toList()); 

		Board board = new Board(ventLines);

		return board.getNumberOfOverlaps();
	}

	private int solveAdventB() {
		List<VentLine> ventLines = INPUT_DATA.stream().map(Day5::parseVentLine).collect(Collectors.toList()); 

		Board board = new Board(ventLines);

		return board.getNumberOfOverlaps();
	}

	private static VentLine parseVentLine(String ventString) {
		VentLine v = new VentLine();
		String[] coords = ventString.split(" -> ");
		v.setStartCoords(coords[0].split(","));
		v.setEndCoords(coords[1].split(","));
		return v;
	}

	class Board {

		int[][] boardArea;

		public Board (List<VentLine> ventLines) {
			int width = INPUT_DATA.stream().map(Day5::parseVentLine).collect(Collectors.toList()).stream().mapToInt(VentLine::getLargestX).max().getAsInt();
			int height = INPUT_DATA.stream().map(Day5::parseVentLine).collect(Collectors.toList()).stream().mapToInt(VentLine::getLargestY).max().getAsInt();

			boardArea = new int[++width][++height];

			ventLines.stream().forEach(this::fillBoard);
		}

		private void fillBoard(VentLine e) {
			if(e.x1 > e.x2) {
				e.flipPoints();
			}
			if(e.isHorizontalOrVertical()) {

				for ( int x = e.x1 ; x <= e.x2 ; x++ ) {
					if(e.y1 > e.y2) {
						e.flipPoints();
					}
					for ( int y = e.y1 ; y <= e.y2 ; y++ ) {
						boardArea[x][y]++; 
					}
				}
			}
			else {
				int pykala = 0;
				for( int x = e.x1 ; x <= e.x2; x++) {
					boardArea[x][e.y1+pykala]++;
					if(e.y2 > e.y1) {
						pykala++;
					}else {
						pykala--;
					}
				}
			}
		}

		private int getNumberOfOverlaps() {
			int result = 0;
			for ( int x = 0 ; x <= boardArea[0].length -1  ; x++ ) {
				for ( int y = 0 ; y < boardArea.length -1 ; y++ ) {
					if (boardArea[x][y] > 1) {
						result++;
					}
				}
			}
			return result;
		}
	}

	static class VentLine  {

		int x1 = 0;
		int y1 = 0;

		int x2 = 0;
		int y2 = 0;


		public void setStartCoords(String[] coords) {
			x1 = Integer.parseInt(coords[0]);
			y1 = Integer.parseInt(coords[1]);
		}

		public void flipPoints() {
			int tempX = x1;
			int tempY = y1;
			x1 = x2;
			y1 = y2;
			x2 = tempX;
			y2 = tempY;
		}

		public void setEndCoords(String[] coords) {
			x2 = Integer.parseInt(coords[0]);
			y2 = Integer.parseInt(coords[1]);
		}

		public boolean isHorizontalOrVertical () {
			return x1 == x2 || y1 == y2;
		}

		public int getLargestX() {
			return Math.max(x1, x2);	
		}

		public int getLargestY() {
			return Math.max(y1, y2);		
		}

	}
}
