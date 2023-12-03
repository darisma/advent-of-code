package advent2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import utils.AdventInputReader;

public class Day13 {


	private static final AdventInputReader AIR = new AdventInputReader();
	private static final String FILENAME = "2022input/day13_test.txt";

	static final List<String> INPUT_DATA = AIR.getStringStream(FILENAME).collect(Collectors.toList());


	public static void main (String[] args) {
		System.out.println("Answer to A is: " + new Day13().solveAdventA());
		System.out.println("Answer to B is: " + new Day13().solveAdventB());
	}

	private long solveAdventA() {

		return 0;
	}

	private long solveAdventB() {


		
		return 0;
	}

	public class Packet{


		
	}
	
	public class PacketData{
		
	}
	
	public class PacketInteger extends PacketData{
		int numValue;
		
		public List<Integer> valueAsList() {
			return Arrays.asList(numValue);
		}
		
	}
	
	public class PacketArray extends PacketData{
		List<PacketData> list;
	}
}
