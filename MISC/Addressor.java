import java.util.*;
"T", "G" };

	public Addressor(int sequenceLen) {
		this.sequenceLen = sequenceLen;
	}

	public static void main(String[] args) {
		Addressor addressor = new Addressor(6);
		addressor.getMaxAdd();
	}

	protected String makeDNA(String combination) {
		StringBuilder string = new StringBuilder();
		
		for (int j = 0; j < sequenceLen; j++) {
			string.append(dnaValues[combination.charAt(j) - '0']);
		}

		return string.toString();
	}
public class Addressor {

	protected int sequenceLen;
	protected String[] dnaValues = { "A", "C", 
	
	boolean isUncorrelated(String first, String second) {
		int length = first.length();

		if (first.length() > second.length())
			length = second.length();

		int i = 0;

		if (first.equals(second))
			i = 1;

		while (i < length) {

			int endIndex = length - i;
			int startIndex = 0 + i;
			
			if (first.substring(startIndex, length).equals(second.substring(0, endIndex)))
				return false;
			
			i++;
		}
		return true;
	}

	boolean isMutuallyUncorrelated(Set<String> set) {
		for (String first : set) {
			for (String second : set) {
				if (!isUncorrelated(first, second)) {
					return false;
				}
			}
		}
		return true;
	}

	public Set<Set<String>> superSet(Set<String> mainSet) {
		Set<Set<String>> sets = new HashSet<Set<String>>();
		
		if (mainSet.isEmpty()) {
			sets.add(new HashSet<String>());
			return sets;
		}

		List<String> list = new ArrayList<String>(mainSet);
		String head = list.get(0);
		Set<String> rest = new HashSet<String>(list.subList(1, list.size()));

		for (Set<String> set : superSet(rest)) {
			Set<String> newSet = new HashSet<String>();
			newSet.add(head);
			newSet.addAll(set);

			if (isMutuallyUncorrelated(set)) {
				sets.add(set);

				if (isMutuallyUncorrelated(newSet)) 
					sets.add(newSet);
			}
		}

		return sets;
	}
    
   	public Set<String> getMaxAdd() {
		Set<String> set = new HashSet<String>();

		for (int i = 0; i < Math.pow(dnaValues.length - 1, sequenceLen - 1); i++) {
			StringBuilder sb = new StringBuilder(Integer.toString(i, dnaValues.length - 1));
			sb.append("3");

			while (sb.length() < sequenceLen)
				sb.insert(0, "0");
			
			set.add(makeDNA(sb.toString()));
		}

		return set;
	}
}
