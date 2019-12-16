package multiregexpbulks.finite;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;



public class FiniteAutomaton {
    private final FiniteState initial;

    public FiniteAutomaton(final FiniteState initial) {
	this.initial = initial;
    }
    
    public FiniteState getInitial() {
	return this.initial;
    }
    
    public Result testWord(final String word) {
	return testWord(getInitial(), word, 0, new Result(word));
    }
    
    private Result testWord(final FiniteState state, final String word, final Integer index, final Result result) {
	result.addState(state);
	result.addObjnum(0);
	result.addObjmap(state.getName(),0);

	if (index >= word.length()) {
	    return result;
	}
	
	final List<FiniteState> states = state.getState(word.charAt(index));
	
	Result nextResult = null;
	for (FiniteState next : states) {
	    nextResult = testWord(next, word, index+1, result.clone());
	    if (nextResult.isValid()) {
		return nextResult;
	    }
	}
	
	return nextResult;
    }
    
    protected class Result implements Cloneable {
	private String word;
	private List<FiniteState> states;
	private List<Integer> objnums;
	private Map<String, Integer> objmaps;

	public Result(final String word) {
	    this.word = word;
	    this.states = new LinkedList<FiniteState>();
	    this.objnums = new LinkedList<Integer>();
	    this.objmaps = new HashMap<String, Integer>();
	}

	public Boolean isValid() {
	    return states.get(states.size()-1).isFinal();
	}

	public FiniteState addState(FiniteState state) {
	    this.states.add(state);
	    return state;
	}

	public List<Integer> addObjnum(Integer objnum) {
	    this.objnums.add(objnum);
	    return objnums;
	}

	public Map<String, Integer> addObjmap(String name, int objnum) {
	    this.objmaps.put(name, new Integer(objnum));
	    return objmaps;
	}
	
	public List<FiniteState> getStates() {
	    return states;
	}
	public List<Integer> getObjnums() {
	    return objnums;
	}
	public Map<String, Integer> getObjmaps() {
	    return objmaps;
	}

	public String incNum(int i) {
	    Integer value = this.objnums.get(i);
	    value = value + 1;
	    this.objnums.set(i, value);
	    return this.objnums.get(i).toString();
	}
	public String incObjnum(String name) {
	    Integer value = this.objmaps.get(name);
	    value = value + 1;
	    this.objmaps.put(name, new Integer(value));
	    return this.objmaps.get(name).toString();
	}


	public String getNum(int i) {
	    return this.objnums.get(i).toString();
	}
	public String getObjnum(String name) {
	    return this.objmaps.get(name).toString();
	}


	public String getWord() {
	    return word;
	}
	
	@Override
	public Result clone() {
	    Result result = new Result(getWord());
	    for (FiniteState state : states) {
		result.addState(state);
	    }
	    for (Integer objnum : objnums) {
		result.addObjnum(objnum);
	    }
	    result.objmaps = new HashMap<String, Integer>(this.objmaps);
	    return result;
	}
	
	public String toString() {
	    StringBuffer string = new StringBuffer();
	    for (int i = 0; i < word.length(); i++) {
		if (! states.get(i).getName().equals("null")) {
		    string.append("[");
		    string.append(states.get(i).getName());
		    string.append("]");
		    string.append(" -- ");
		    string.append(word.charAt(i));
		    string.append(" --> ");
		} else {
		    string.append(" Total : ");
//		    string.append(states.get(i).incNum(i));
//		    string.append(this.incNum(i));
		    string.append(this.incObjnum(states.get(i).getName()));
		    break;
		}
	    }
		if (! states.get(word.length()).getName().equals("null")) {
		    string.append("[");
		    string.append(states.get(word.length()).getName());
		    string.append("]");
		} else {
		    string.append(" Total_End : ");
//		    string.append(String.valueOf(states.get(word.length()).incNum()));
//		    string.append(states.get(word.length()).getNum(0));
//		    string.append(this.getNum(word.length()));
//		    string.append(this.incNum(word.length()));
		    string.append(this.incObjnum(states.get(word.length()).getName()));
		    string.append(" List of int : ");
	    for (int i = 0; i < word.length(); i++) {
//		    string.append(this.incNum(i));
		    string.append(states.get(i).getName());
		    string.append(" : ");
		    string.append(this.incObjnum(states.get(i).getName()));
		    string.append(" : ");
	    }
		}
	    return string.toString();
	}
    }
}
