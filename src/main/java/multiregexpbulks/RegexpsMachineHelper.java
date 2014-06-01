package multiregexpbulks;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;
import java.util.Map.Entry;

public class RegexpsMachineHelper<V> implements Serializable {

    private static final long serialVersionUID = -7220640852684147030L;
    private Map<String, V> regexpToValueMapping;
    private V[] indexedValuesArray = null;
    private volatile RegexpsMachine regexpsMachine = null;


    public RegexpsMachineHelper() {
        regexpToValueMapping = new TreeMap<String, V>(new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareTo(s2);
            }
        });
    }

    public void add(String urlPrefix, V value) {
        regexpToValueMapping.put(urlPrefix.intern(), value);
    }

    public void constructAutomatonMapping() {
        if (regexpToValueMapping.isEmpty()) {
            return;
        }
        regexpsMachine = new RegexpsMachine();
        String[] regexps = new String[regexpToValueMapping.size()];
        //noinspection unchecked
        indexedValuesArray = GenSet((Class<V>) regexpToValueMapping.values().iterator().next().getClass(), regexpToValueMapping.size());
        Iterator<Entry<String, V>> itr = regexpToValueMapping.entrySet().iterator();
        int c = 0;
        while (itr.hasNext()) {
            Entry<String, V> nextEntry = itr.next();
            regexps[c] = nextEntry.getKey();
            indexedValuesArray[c] = nextEntry.getValue();
            c++;
        }
        regexpsMachine.constructMultiPattern(regexps);
    }

    public V getCategoryForURL(String url) {
        int runURLOnMultiPatterns = regexpsMachine.runURLOnMultiPatterns(url);
        return runURLOnMultiPatterns == Integer.MAX_VALUE ? null : indexedValuesArray[runURLOnMultiPatterns];
    }

    public List<V> getAllCategoriesForURL(String url) {
        Collection<Integer> arr = regexpsMachine.runURLOnMultiPatternsAndGetAllMatches(url);
        List<V> list = new LinkedList<V>();
        for (Integer i : arr) {
            list.add(indexedValuesArray[i]);
        }
        return list;
    }

    @SuppressWarnings("unused")
    public int getPatternCount() {
        return indexedValuesArray != null ? indexedValuesArray.length : 0;
    }

    public V[] GenSet(Class<V> c, int s) {
        @SuppressWarnings("unchecked")
        V[] arr = (V[]) Array.newInstance(c, s);
        return arr;
    }
}
