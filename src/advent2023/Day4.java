package advent2023;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day4 {

    static AdventInputReader ir = new AdventInputReader();
    static final String FILENAME = "2023input/day4.txt";

    static List<String> gameData = ir.getStringStream(FILENAME).collect(Collectors.toList());

    public static void main (String[] args) {
        System.out.println("Answer to 1a is: " + solveAdvent1a());
        System.out.println("Answer to 1b is: " + solveAdvent1b());
    }

    private static int solveAdvent1a() {
        return countScoreForCards();
    }

    private static int solveAdvent1b() {
        return countScratchCards();
    }
    
    private static int countScoreForCards() {
        return gameData.stream().map(e -> new Card(e)).mapToInt(card -> card.getScore()).sum();
    }

    private static Set<Integer> getNumbers(String numberString) {
        StringTokenizer tokenizer = new StringTokenizer(numberString, " ");
        Set<Integer> numbers = new HashSet<>();
        while(tokenizer.hasMoreElements()) {
            numbers.add(Integer.parseInt(tokenizer.nextToken()));
        }
        return numbers;
    }

    private static int countScratchCards() {
        List<Card> cards = new ArrayList<>();

        for(String s : gameData) {
            cards.add(new Card(s));
        }

        for(Card card : cards) {
            int matches = card.getNumberOfMatches();
            for(int i = 0; i < matches; i++) {
                if(card.getId() + i < cards.size()) {
                    cards.get(card.getId() + i).addCard(card.getAmountOfCards());
                }
            }
        }

        return cards.stream().mapToInt(e -> e.getAmountOfCards()).sum();
    }

    private static Set<Integer>  createWinningNumbers(String s) {
        return getNumbers(s.substring(s.indexOf("|") + 1));
    }

    private static Set<Integer> createOwnNumbers(String s) {
        return getNumbers(s.substring(s.indexOf(":") + 1, s.indexOf("|")));
    }

    static class Card{
        private int id;
        private int amountOfCards = 1;
        Set<Integer> ownNumbers;
        Set<Integer> winningNumbers;

        public Card(String s) {
            this.id = Integer.parseInt(s.substring(s.indexOf(" "), s.indexOf(":")).trim());
            this.ownNumbers = createOwnNumbers(s);
            this.winningNumbers = createWinningNumbers(s);
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAmountOfCards() {
            return amountOfCards;
        }

        public void addCard(int howMany) {
            this.amountOfCards = this.amountOfCards + howMany;
        }

        public void setAmountOfCards(int amountOfCards) {
            this.amountOfCards = amountOfCards;
        }

        public int getNumberOfMatches() {
            int matches = 0;
            for (Integer ownNumber : this.ownNumbers) {
                if(this.winningNumbers.contains(ownNumber)) {
                    matches ++;
                }
            }
            return matches;
        }

        public int getScore() {
            int score = 0;
            for (Integer ownNumber : ownNumbers) {
                if(winningNumbers.contains(ownNumber)) {
                    if(score == 0) {
                        score = 1;
                    }else {
                        score = 2* score;
                    }
                }
            }
            return score;
        }
    }
}
