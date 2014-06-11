/**
 * This work is licensed under the Creative Commons Attribution 3.0
 * Unported License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to
 * Creative Commons, 444 Castro Street, Suite 900,
 * Mountain View, California, 94041, USA. 
 */

package cs345feltsc.game;

import java.util.*;

/**
 * This is the interface provided for Terms.
 * 
 * @author Chris Reedy (Chris.Reedy@wwu.edu)
 */
public interface Term {
	
    /**
     * Add the given word to this Term.
     * @param word the word to be added
     */
	public void addWord(Word word);
	
	/**
	 * get List of words associated with this Term
	 * @return ArrayList of Words
	 */
	public ArrayList<Word> getWords();
	
	/**
	 * get the name of the term
	 * @return name of the term
	 */
	public String getName();
}
