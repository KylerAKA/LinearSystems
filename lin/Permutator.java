package lin;

import java.util.Vector;

public abstract class Permutator<I, O> {
	Vector<I> sources;
	
	O Coutput;
	
	/**
	 * Sets the Coutput to the first step in the permutation
	 * 
	 * @return the first step in permutation
	 */
	abstract O toStart(@SuppressWarnings ("unchecked") I... s);
	
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
	abstract O getCurrent();
	
}
