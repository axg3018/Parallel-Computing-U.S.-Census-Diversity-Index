import java.io.*;

/**
 *	Class state stores state and county names
 *
 * @author Arjun Gupta 
 * @version 12-December-2018
 */

public class State implements Serializable{
	private String state;
	private String county;

	/**
	 *  Constructor to initialize state and county name
	 */
	State(String stName, String ctName){
		this.state = stName;
		this.county = ctName;
	}

	/**
	 *  return state name
	 */
	public String getState(){
		return state;
	}

	/**
	 *  return county name
	 */
	public String getCounty(){
		return county;
	}

	/**
	 *  compute hash for class object. Used by equals
	 */
	public int hashCode() {

    	int hash = 7;
	    hash = 31 * hash + (state == null ? 0 : state.hashCode());
	    hash = 31 * hash + (county == null ? 0 : county.hashCode());
	    return hash;
	}

	/**
	 *  Compare two class objects to check equality
	 */

	public boolean equals(Object o) {
	    // self check
	    if (this == o)
	        return true;
	    // null check
	    if (o == null)
	        return false;
	    // type check and cast
	    if (getClass() != o.getClass())
	        return false;
	    State st = (State) o;
	    // field comparison
	    return state.equals(st.state) && county.equals(st.county);
	}
}