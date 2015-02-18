package multiregexpbulks;

import java.io.Serializable;
import java.util.*;
import java.util.Map.Entry;


/**
 * Interface class for using the regexpsMachine class.
 *
 * @param <V>
 */
public class RegexpsMachineHelper<V> implements Serializable {

    private static final long serialVersionUID = -7220640852684147030L;
    private transient Map<String, V> regexpToValueMapping;
    private ArrayList<V> indexedValuesArray = null;
    private V[] runTimeIndexedValuesArray = null;
    private RegexpsMachine regexpsMachine = null;

    public RegexpsMachineHelper() {

        regexpToValueMapping = new TreeMap<String, V>(new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {

                return s1.compareTo(s2);
            }
        });
    }

    /**
     * This method is used to add pattern with a desired return value.
     * you cannot query the regexpMachine until you have called constructAutomatonMapping()
     *
     * @param regexpString - The regexp to be added. The language is of brics.dk automaton.
     * @param value        - Values which to be return upon a match.
     * @see <a href="http://www.brics.dk/automaton/doc/index.html?dk/brics/automaton/RegExp.html">
     * Brics Automaton regexp language
     * </a>
     */
    public void add(String regexpString, V value) {

        regexpToValueMapping.put(regexpString.intern(), value);
    }

    /**
     * construct the mapping after you ar done adding regexps.
     */
    public void constructAutomatonMapping() {

        if (regexpToValueMapping.isEmpty()) {
            return;
        }
        regexpsMachine = new RegexpsMachine();
        String[] regexps = new String[regexpToValueMapping.size()];
        indexedValuesArray = new ArrayList<V>(regexpToValueMapping.size());
        Iterator<Entry<String, V>> itr = regexpToValueMapping.entrySet().iterator();
        int c = 0;
        while (itr.hasNext()) {
            Entry<String, V> nextEntry = itr.next();
            regexps[c] = nextEntry.getKey();
            indexedValuesArray.add(c, nextEntry.getValue());
            c++;
        }
        regexpsMachine.constructMultiPattern(regexps);
        //noinspection unchecked
        runTimeIndexedValuesArray = (V[]) indexedValuesArray.toArray();
        indexedValuesArray = null;
    }

    /**
     * @param query - Run this string on the regexpMachine and get the longest match.
     * @return V assigned to the longest match matching pattern. In case there are two or more matches with the same
     * length than the last match in lexicographic order is returned.
     */
    public V getValueForURL(String query) {

        int runURLOnMultiPatterns = regexpsMachine.runQueryOnMultiPatterns(query);
        return runURLOnMultiPatterns == Integer.MAX_VALUE ? null : runTimeIndexedValuesArray[runURLOnMultiPatterns];
    }

    /**
     * @param query - Run this string on the regexpMachine and get the first match in lexicographic order.
     * @return - First match in lexicographic order.
     */
    public V getValueForURLEager(String query) {

        int runURLOnMultiPatterns = regexpsMachine.runQueryOnMultiPatternsEager(query);
        return runURLOnMultiPatterns == Integer.MAX_VALUE ? null : runTimeIndexedValuesArray[runURLOnMultiPatterns];
    }

    /**
     * @param query - Run this string on the regexpMachine and get all matches.
     * @return - All matches
     */
    public List<V> getAllCategoriesForURL(String query) {

        Collection<Integer> arr = regexpsMachine.runQueryOnMultiPatternsAndGetAllMatches(query);
        List<V> list = new LinkedList<V>();
        for (Integer i : arr) {
            list.add(runTimeIndexedValuesArray[i]);
        }
        return list;
    }

    @SuppressWarnings("unused")
    /**
     * To be used on a constructed machine.
     */
    public int getPatternCount() {

        return runTimeIndexedValuesArray != null ? runTimeIndexedValuesArray.length : 0;
    }
}