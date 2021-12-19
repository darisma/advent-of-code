package advent2021;

import java.util.ArrayList;
import java.util.List;

public class Day17 {

	// target area: x=265..287, y=-103..-58

	private int minX = 265;
	private int maxX = 287;
	private int minY = -103;
	private int maxY = -58;
	
	public static void main (String[] args) {
		System.out.println("Answer to A is: " + new Day17().solveAdventA());
		System.out.println("Answer to B is: " + new Day17().solveAdventB());
	}

	private int solveAdventA() {
		List<Integer> possibleXValues = new ArrayList<>();
		for (int x = 1 ; x < maxX; x ++) {
			if (velocityXhits(x, minX, maxX)) {
				possibleXValues.add(x);
			}
		}

		List<Integer> possibleYValues = new ArrayList<>();
		
		for (int y = 0 ; y > minY ; y--) {
			if(velocityYhits(y, minY, maxY)) {
				possibleYValues.add(y);
			}
		}
		return highestPointForSpeedY(possibleYValues.stream().mapToInt(e -> Math.abs(e.intValue())).max().getAsInt());

	}

	private boolean velocityXhits(int speed, int minTarget, int maxTarget) {
		int location = 0;
		while(speed > 0) {
			location = location + speed;
			
			if(minTarget <= location && location <= maxTarget) {
				return true;
			}
			
			speed = speed -1;
		}
		return false;
	}

	private boolean velocityYhits(int speed, int minTarget, int maxTarget) {
		int location = 0;
		while(location > maxTarget) {
			location = location + speed;
			
			if(minTarget <= location && location <= maxTarget) {
				return true;
			}
			speed = speed -1 ;
		}
		return false;
	}
	
	private int highestPointForSpeedY(int speed) {
		
		int height = 0;
		while (speed > 0) {
			height = height + speed;
			speed --;
		}
		return height;
	}
	
	private long solveAdventB() {

		return bruteCheck();
		
	}


	private int bruteCheck() {

		int hits = 0;
		
		int minXSpeedToReachArea = 0 ; // calc correct one here
		int maxXSpeedToHitArea = maxX;
		int minYSpeedToReachArea = -minY ; 
		int maxYSpeedToReachArea = minY; ;
		
		for(int x = minXSpeedToReachArea; x <= maxXSpeedToHitArea; x ++) {
			for(int y = minYSpeedToReachArea ; y >= maxYSpeedToReachArea; y --) {
				if(checkIfHits(x, y)) {
					hits = hits + 1;
				}
			}
		}
		return hits;
	}

	private boolean checkIfHits(int xSpeed, int ySpeed) {
		int xLocation = 0;
		int yLocation = 0;
		
		while(xLocation <= maxX && yLocation >= minY) {
			
			if(minX <= xLocation && yLocation <= maxY) {
				return true;
			}
			
			xLocation = xLocation + xSpeed;
			if(xSpeed > 0) {
				xSpeed = xSpeed - 1;
			}
			
			yLocation = yLocation + ySpeed;
			ySpeed = ySpeed - 1;
		}
		return false;
	}

}