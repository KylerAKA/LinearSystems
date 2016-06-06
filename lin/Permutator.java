package lin;

public abstract class Permutator<O> {
	O crn;
	
	/**
	 * Sets the crn to the first step in the permutation
	 * 
	 * @return the first step in permutation
	 */
	abstract O toStart();
	
	/**
	 * The logic to move from one permutation to the next
	 * 
	 * @return
	 */
	abstract boolean permute();
	
	/**
	 * Returns the current step of the permutation
	 * 
	 * @return
	 */
	O getCurrent() {
		return crn;
	}
	
}
