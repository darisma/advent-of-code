package advent2023;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day7 {

	static AdventInputReader ir = new AdventInputReader();
	static final String FILENAME = "2023input/day7.txt";

	static List<String> gameData = ir.getStringStream(FILENAME).collect(Collectors.toList());


	public static void main (String[] args) {
		System.out.println("Answer to 1a is: " + solveAdvent1a());
		System.out.println("Answer to 1b is: " + solveAdvent1b());
	}

    private static long solveAdvent1a() {
	    List<Hand> hands = gameData.stream().map(e -> new Hand(e, 1)).collect(Collectors.toList());
		hands.sort(new HandComparator());
		
		return countWinnings(hands);
	}

    private static long solveAdvent1b() {
        List<Hand> hands = gameData.stream().map(e -> new Hand(e, 2)).collect(Collectors.toList());
        hands.sort(new HandComparator());

        System.out.println("jorma");
        
		return countWinnings(hands);
	}

    private static long countWinnings(List<Hand> hands) {

        long winnings = 0;
        for(int i = 0; i < hands.size(); i++) {
            winnings = winnings + hands.get(i).bid * (i + 1);
        }
        return winnings;
    }
    
	static class Hand {
	    
	    String hand;
	    Integer bid;
	    Integer handType;
	    Map<String, Integer> cardAmounts;
	    int part;
	    
	    public Hand(String s, int part) {
	        this.part = part;
	        hand = s.split(" ")[0];
	        bid = Integer.parseInt(s.split(" ")[1]);
            if(part == 1) {
                cardAmounts = countCardAmounts();
            }
            if(part == 2) {
                cardAmounts = countCardAmountsPart2();
            }
	        handType = getHandType(hand);
	    }

        private Integer getHandType(String hand) {

            // fiveofakind
            if(cardAmounts.containsValue(5)) {
                return 7;
            }
            // fourofakind
            if(cardAmounts.containsValue(4)) {
                return 6;
            }
            // fullhouse
            if(cardAmounts.containsValue(3) && cardAmounts.containsValue(2)) {
                return 5;
            }
            // three of a kind
            if(cardAmounts.containsValue(3)) {
                return 4;
            }
            // two pairs
            if(cardAmounts.containsValue(2) && cardAmounts.size() == 3) {
                return 3;
            }
            // pair
            if(cardAmounts.containsValue(2)) {
                return 2;
            }
            // high
            return 1;
        }

        private Map<String, Integer> countCardAmounts() {
            Map<String, Integer> cards = new HashMap<>();
            for(int i = 0; i < hand.length(); i ++) {
                String s = hand.substring(i, i + 1);
                if(cards.containsKey(s)) {
                    cards.put(s, cards.get(s) + 1);
                }else {
                    cards.put(s,  1);
                }
            }
            return cards;
        }
        
        private Map<String, Integer> countCardAmountsPart2() {
            Map<String, Integer> cards = new HashMap<>();
            int jokerCount = 0;
            for(int i = 0; i < hand.length(); i++) {
                String s = hand.substring(i, i + 1);
                if(s.equalsIgnoreCase("J")) {
                    jokerCount++;
                }
                if(cards.containsKey(s)) {
                    cards.put(s, cards.get(s) + 1);
                }else {
                    cards.put(s, 1);
                }
            }
            final int totalJoker = jokerCount;
            if(totalJoker > 0) {
            int maxValueInMap = (Collections.max(cards.values()));

            List<String> maxEntryKeys = cards.entrySet().stream().filter(e -> e.getValue().intValue() == maxValueInMap).map(e -> e.getKey()).collect(Collectors.toList());
            maxEntryKeys.sort(new maxEntryComparator());
            maxEntryKeys = List.of(maxEntryKeys.get(maxEntryKeys.size() - 1));
            cards.put(maxEntryKeys.get(0), maxValueInMap + totalJoker);
            cards.getClass();
            }
            return cards;
        }

        @Override
        public String toString() {
            return "Hand [hand=" + hand + ", bid=" + bid + ", handType=" + handType + ", cardAmounts=" + cardAmounts
                    + "]";
        }
	}
	
	static class maxEntryComparator implements Comparator<String>{

		@Override
		public int compare(String s1, String s2) {
            if(s1.equalsIgnoreCase("A")) {
                return 1;
            }
            if(s2.equalsIgnoreCase("A")) {
                return -1;
            }
            if(s1.equalsIgnoreCase("K")) {
                return 1;
            }
            if(s2.equalsIgnoreCase("K")) {
                return -1;
            }
            if(s1.equalsIgnoreCase("Q")) {
                return 1;
            }
            if(s2.equalsIgnoreCase("Q")) {
                return -1;
            }
            if(s1.equalsIgnoreCase("T")) {
                return 1;
            }
            if(s2.equalsIgnoreCase("T")) {
                return -1;
            }
            if(s1.equalsIgnoreCase("J")) {
                return -1;
            }
            if(s2.equalsIgnoreCase("J")) {
                return 1;
            }

            return Integer.compare(Integer.parseInt(s1), Integer.parseInt(s2));
			
		}
		
	}
	
	static class HandComparator implements Comparator<Hand>{

        @Override
        public int compare(Hand hand1, Hand hand2) {
            if(hand1.handType != hand2.handType) {
                return Integer.compare(hand1.handType, hand2.handType);
            }
            for(int i = 0; i < hand1.hand.length(); i ++) {
                String s1 = hand1.hand.substring(i, i + 1);
                String s2 = hand2.hand.substring(i, i + 1);
                
                if(! s1.equalsIgnoreCase(s2)) {
                    if(s1.equalsIgnoreCase("A")) {
                        return 1;
                    }
                    if(s2.equalsIgnoreCase("A")) {
                        return -1;
                    }
                    if(s1.equalsIgnoreCase("K")) {
                        return 1;
                    }
                    if(s2.equalsIgnoreCase("K")) {
                        return -1;
                    }
                    if(s1.equalsIgnoreCase("Q")) {
                        return 1;
                    }
                    if(s2.equalsIgnoreCase("Q")) {
                        return -1;
                    }
                    if(hand1.part == 1 && s1.equalsIgnoreCase("J")) {
                        return 1;
                    }
                    if(hand1.part == 1 && s2.equalsIgnoreCase("J")) {
                        return -1;
                    }
                    if(s1.equalsIgnoreCase("T")) {
                        return 1;
                    }
                    if(s2.equalsIgnoreCase("T")) {
                        return -1;
                    }
                    if(hand1.part == 2 && s1.equalsIgnoreCase("J")) {
                        return -1;
                    }
                    if(hand1.part == 2 && s2.equalsIgnoreCase("J")) {
                        return 1;
                    }

                    return Integer.compare(Integer.parseInt(s1), Integer.parseInt(s2));

                }
            }
            System.out.println("hands same");
            return 0;
        }  
	}
}