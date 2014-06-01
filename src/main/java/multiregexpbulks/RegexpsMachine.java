package multiregexpbulks;


import com.fulmicoton.multiregexp.MultiPattern;

import javax.naming.OperationNotSupportedException;
import java.io.Serializable;
import java.util.*;

public class RegexpsMachine implements Serializable {

    private static final long serialVersionUID = 6330304557747416778L;
    private static final String ERROR1 = "You must set this size prior to constructing the MultiPattern Array";
    private static final String COMMA = ",";
    private int MAX_REGEXPS_IN_MULTI_PATTERN = 7;
    private Set<MultiPattern> multiPatterns = new LinkedHashSet<MultiPattern>();
    private Map<Integer, Integer> lengthMap = new HashMap<Integer, Integer>();

    public void constructMultiPattern(String... regexps) {
        List<String> regexpsBulks = createRegexpsBulks(regexps);
        constructMultiPatternsArray(regexpsBulks.toArray(new String[regexpsBulks.size()]));
    }

    public int runURLOnMultiPatterns(String urlLine) {
        int[] matching;
        int matchedValue = Integer.MAX_VALUE;
        int prevMatchedValue;
        int bulksCounter = 0;
        for (MultiPattern bulk : multiPatterns) {
            matching = bulk.match(urlLine);
            for (int aMatching : matching) {
                prevMatchedValue = matchedValue;
                matchedValue = bulksCounter * MAX_REGEXPS_IN_MULTI_PATTERN + aMatching;
                if ((lengthMap.get(prevMatchedValue) != null) && (lengthMap.get(matchedValue) < lengthMap.get(prevMatchedValue))) {
                    matchedValue = prevMatchedValue;
                }
            }
            bulksCounter++;
        }// Best Match or nothing
        return matchedValue;
    }

    public Collection<Integer> runURLOnMultiPatternsAndGetAllMatches(String urlLine) {
        int[] matching;
        Set<Integer> allMatches = new HashSet<Integer>();
        int bulksCounter = 0;
        for (MultiPattern bulk : multiPatterns) {
            matching = bulk.match(urlLine);
            for (int aMatching : matching) {
                allMatches.add(bulksCounter * MAX_REGEXPS_IN_MULTI_PATTERN + aMatching);
            }
            bulksCounter++;
        }
        return allMatches;
    }

    @SuppressWarnings("unused")
    public void setMaxRegexpsInBulk(int size) throws OperationNotSupportedException {
        if (!multiPatterns.isEmpty()) {
            throw new OperationNotSupportedException(ERROR1);
        }
        MAX_REGEXPS_IN_MULTI_PATTERN = size;
    }

    private void constructMultiPatternsArray(String... regexps) {
        for (String str : regexps) {
            multiPatterns.add(MultiPattern.compile(str.split(COMMA)));
        }
    }

    private List<String> createRegexpsBulks(String... regexps) {
        List<String> regexpsList = new LinkedList<String>();
        int multiPatternCounter = 0;
        int count = 0;
        StringBuilder sb = new StringBuilder();
        for (String regExp : regexps) {
            sb.append(regExp);
            sb.append(COMMA);
            lengthMap.put(count++, regExp.length());
            if (multiPatternCounter == MAX_REGEXPS_IN_MULTI_PATTERN - 1) {
                multiPatternCounter = 0;
                sb.deleteCharAt(sb.length() - 1);
                regexpsList.add(sb.toString());
                sb = new StringBuilder();
            } else {
                multiPatternCounter++;
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
            regexpsList.add(sb.toString());
        }
        return regexpsList;
    }
}
