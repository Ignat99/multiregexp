package multiregexpbulks;



public abstract class State {

    private final String name;
//    private int objnum;
    
    public State(final String name) {
	this.name = name;
//	this.objnum = 0;
    }
    
    public String getName() {
	return this.name;
    }
/*
    @Override
    public int getNum() {
	return this.objnum;
    }

    @Override
    public int incNum() {
	this.objnum++;
	return this.objnum;
    }
*/

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	return result;
    }


    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (!(obj instanceof State)) {
	    return false;
	}
	State other = (State) obj;
	if (name == null) {
	    if (other.name != null) {
		return false;
	    }
	} else if (!name.equals(other.name)) {
	    return false;
	}
	return true;
    }
}
