package advent2023;

import java.util.List;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day6 {

	static AdventInputReader ir = new AdventInputReader();
	static final String FILENAME = "2023input/day6.txt";

	static List<String> gameData = ir.getStringStream(FILENAME).collect(Collectors.toList());
	static String[] times = gameData.get(0).substring(gameData.get(0).indexOf(":") + 1).trim().split("\\s+");
	static String[] distances = gameData.get(1).substring(gameData.get(1).indexOf(":") + 1).trim().split("\\s+");

	public static void main (String[] args) {
		System.out.println("Answer to 1a is: " + solveAdvent1a());
		System.out.println("Answer to 1b is: " + solveAdvent1b());
	}

	private static long solveAdvent1a() {

		int result = 1;

		for(int i = 0; i < times.length ; i ++) {
			int raceWins = 0;
			int time = Integer.parseInt(times[i]);
			int distance = Integer.parseInt(distances[i]);
			for(int j = 0; j <= time; j ++) {
				if(goesFartherThanRecord(j, time, distance)) {
					raceWins ++;
				}
			}
			result = result * raceWins;
		}

		return result;
	}


	private static boolean goesFartherThanRecord(long chargeTime, long time, long distance) {

		if(chargeTime == 0 || chargeTime == time) {
			return false;
		}

		long speed = chargeTime;
		long travelTime = time - chargeTime;
		long distanceTravelled = speed * travelTime;

		return distanceTravelled > distance;
	}

	private static long solveAdvent1b() {
		long result = 1;

		long time = Long.parseLong(times[0] + times[1] + times[2] + times[3]);
		long distance = Long.parseLong(distances[0] + distances[1] + distances[2] + distances[3]);

		long start = 0;
		long end = time;

		// min
		while(start != end) {
			if(goesFartherThanRecord((start + end) / 2, time, distance)) {
				end = end - (end - start) / 2;
			}else {
				start = start + (end - start) / 2;
			}

			if(Math.abs(start-end) == 1) {
				break;
			}
		}

		long min = start;

		start = 0;
		end = time;

		// max
		while(start != end) {
			if(goesFartherThanRecord((start + end) / 2, time, distance)) {
				start = start + (end - start) / 2;
			}else {
				end = end - (end - start) / 2;
			}

			if(Math.abs(start - end) == 1 || start > end) {
				System.out.println("Breakiin, " + start + " " + end);
				break;
			}
		}

		long max = start;

		// löydä min ja max ja laita ne for-looppiin
		long raceWins = 0;
		for(long j = min; j <= max; j ++) {
			if(goesFartherThanRecord(j, time, distance)) {
				raceWins ++;
			}
		}
		result = result * raceWins;

		return result;
	}
}