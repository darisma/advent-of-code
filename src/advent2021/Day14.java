package advent2021;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import utils.AdventInputReader;

public class Day14 {

	private static final AdventInputReader AIR = new AdventInputReader();
	private static final String FILENAME = "2021input/day14.txt";

	static final List<String> INPUT_DATA = AIR.getStringStream(FILENAME)
			.collect(Collectors.toList());

	public static void main (String[] args) {
		System.out.println("Answer to A is: " + new Day14().solveAdventA());
		System.out.println("Answer to B is: " + new Day14().solveAdventB());
	}

	private long solveAdventA() {

		Map<String, String> insertionRules = generateRuleMap();

		String result = INPUT_DATA.get(0);

		for( int i = 0; i < 10; i++) {
			result = processResult(result, insertionRules);
		}

		return countResultA(result);
	}

	private long solveAdventB() {

		Map<String, String> insertionRules = generateRuleMap();

		Map<String, Long> pairCount = new HashMap<>();

		String result = INPUT_DATA.get(0);

		// starting pairs
		for (int i = 0 ; i < result.length() - 1 ; i++) {
			String key = result.substring(i, i + 2);
			if(!pairCount.containsKey(key)) {
				pairCount.put(key, 1L);
			}else {
				pairCount.put(key, pairCount.get(key) + 1L);
			}
		}

		// one pair generates 2 pairs every turn

		for( int i = 0; i < 40; i++) {

			Map<String, Long> newPairCount = new HashMap<>();

			for(Entry<String, String> e : insertionRules.entrySet()) {

				String firstNewPairKey = getNewPair(e.getKey(), insertionRules, true);
				addToPairCount(newPairCount, firstNewPairKey, pairCount.get(e.getKey()));

				String secondNewPairKey = getNewPair(e.getKey(), insertionRules, false);
				addToPairCount(newPairCount, secondNewPairKey, pairCount.get(e.getKey()));
			}

			pairCount = newPairCount;
		}

		return countResultB(pairCount, result.substring(result.length() -1, result.length()));
	}

	private String processResult(String s, Map<String, String> insertionRules) {

		StringBuilder sb = new StringBuilder();

		for(int i = 0; i < s.length() ; i ++) {
			sb.append(s.charAt(i));
			if(i < s.length() -1 ) {
				String pairKey = s.substring(i, i+2);
				sb.append(insertionRules.get(pairKey));
			}
		}

		return sb.toString();
	}
	
	private long countResultA(String result) {

		Map<String, Integer> counts = new HashMap<>();
		for(int i = 0; i < result.length() ; i ++) {
			String key = result.substring(i, i + 1);
			if(!counts.containsKey(key)) {
				counts.put(key, 1);
			}else {
				counts.put(key, counts.get(key) + 1);
			}
		}
		long max = counts.entrySet().stream().mapToLong(Entry::getValue).max().orElse(0);
		long min = counts.entrySet().stream().mapToLong(Entry::getValue).min().orElse(0);

		return max - min;
	}
	
	private long countResultB(Map<String, Long> pairCount, String lastLetter) {
		Map<String, Long> counts = new HashMap<>();

		// count first letters of each pair
		for(Entry<String, Long> e : pairCount.entrySet()) {
			String firstLetter = e.getKey().substring(0, 1);
			if(counts.containsKey(firstLetter)) {
				counts.put(firstLetter, counts.get(firstLetter) + e.getValue());
			}
			else{
				counts.put(firstLetter, e.getValue());
			}
		}

		// finally add last letter of startline, as it has not been counted yet
		counts.put(lastLetter, counts.get(lastLetter) + 1);

		long max = counts.entrySet().stream().mapToLong(Entry::getValue).max().orElse(0);
		long min = counts.entrySet().stream().mapToLong(Entry::getValue).min().orElse(0);

		return (max - min); 
	}

	private void addToPairCount (Map<String, Long> countMap, String key, Long amount) {
		if (amount == null) {
			return;
		}
		if (countMap.containsKey(key)){
			countMap.put(key, countMap.get(key) + amount); 
		} else {
			countMap.put(key, amount);
		}
	}

	private String getNewPair(String key, Map<String, String> insertionRules, boolean first) {
		if (first) {
			return key.substring(0, 1) + insertionRules.get(key);
		}
		return insertionRules.get(key) + key.substring(1, 2);
	}

	private Map<String, String> generateRuleMap() {
		HashMap<String, String> ruleMap = new HashMap<>();
		for(String s : INPUT_DATA.subList(2, INPUT_DATA.size())) {
			ruleMap.put(s.split(" -> ")[0], s.split(" -> ")[1]);
		}
		return ruleMap;
	}

}