package advent2023;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day2 {

    static AdventInputReader ir = new AdventInputReader();
    static final String FILENAME = "2023input/day2.txt";
    
    static List<String> gameData = ir.getStringStream(FILENAME).collect(Collectors.toList());
    static List<Game> games = gameData.stream().map(e -> new Game(e)).collect(Collectors.toList());
    
    public static void main (String[] args) {
        
        System.out.println("Answer to 1a is: " + solveAdvent1a());
        System.out.println("Answer to 1b is: " + solveAdvent1b());
    }
    
    private static int solveAdvent1a() {
        return sumIdsOfPossibleGames(games, 12, 13, 14);
    }
    

    private static int sumIdsOfPossibleGames(List<Game> games, int maxRed, int maxGreen, int maxBlue) {
    	return games.stream().filter(e -> e.isPossible(maxRed, maxGreen, maxBlue)).mapToInt(e -> e.id).sum();
	}

	private static int solveAdvent1b()  {
        return games.stream().mapToInt(e -> e.getPower()).sum();
    }

    static class Game{
    	int id;
    	List<Reveal> reveals = new ArrayList<>();

    	public Game(String initString) {
    		this.id = Integer.parseInt(initString.substring(5, initString.indexOf(":")));
    		StringTokenizer tokenizer = new StringTokenizer(initString.substring(initString.indexOf(": ") + 2), ";");
    		while(tokenizer.hasMoreTokens()) {
    			reveals.add(new Reveal(tokenizer.nextToken()));
    		}
    	}

    	public boolean isPossible(int red, int green, int blue) {
    		return reveals.stream().filter(e -> e.getBlue() > blue || e.getGreen() > green || e.getRed() > red).findAny().isEmpty();
    	}

    	public int getPower() {
    		Integer power = reveals.stream().mapToInt(e -> e.getBluePower()).max().orElse(1) *
    				reveals.stream().mapToInt(e -> e.getGreenPower()).max().orElse(1) *
    				reveals.stream().mapToInt(e -> e.getRedPower()).max().orElse(1);
    		return power;
    	}
    }

    static class Reveal{
    	Integer blue;
    	Integer green;
    	Integer red;

    	public Reveal(String initString) {
    		StringTokenizer tokenizer = new StringTokenizer(initString, ", ");
    		while(tokenizer.hasMoreTokens()) {
    			String token = tokenizer.nextToken();
    			int amount = Integer.parseInt(token);
    			String color = tokenizer.nextToken();
    			if (color.equalsIgnoreCase("blue")) {
    				this.blue = amount;
    			}else if (color.equalsIgnoreCase("green")) {
    				this.green = amount;
    			}else if (color.equalsIgnoreCase("red")) {
    				this.red = amount;
    			}
    		}
    	}

    	public int getBlue() {
    		if(blue == null) {
    			return 0;
    		}
    		else return blue.intValue();
    	}

    	public int getGreen() {
    		if(green == null) {
    			return 0;
    		}
    		else return green.intValue();
    	}

    	public int getRed() {
    		if(red == null) {
    			return 0;
    		}
    		else return red.intValue();
    	}

    	public int getBluePower() {
    		return Integer.max(getBlue(), 1);
    	}

    	public int getGreenPower() {
    		return Integer.max(getGreen(), 1);
    	}

    	public int getRedPower() {
    		return Integer.max(getRed(), 1);
    	}
    }
}
