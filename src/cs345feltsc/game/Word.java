/**
 * This work is licensed under the Creative Commons Attribution 3.0
 * Unported License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to
 * Creative Commons, 444 Castro Street, Suite 900,
 * Mountain View, California, 94041, USA. 
 */

package cs345feltsc.game;

/**
 * This is the interface provided for words.
 * 
 * @author Chris Reedy (Chris.Reedy@wwu.edu)
 */
public interface Word {
	
	/**
	 * Get the string for the word represented by this word.
	 * @return the String for the word
	 */
	String getWord();
	
	/**
	 * checks to see if a specific String matches Word
	 * @param token, the String in question
	 * @return either the MatchType of the Word the token matches, 
	 * either, MatchType.PREFIX or MatchType.EXACT, or returns
	 * MatchType.NONE if there is no Word that matches the String
	 */
	MatchType match(String token);
	
	/**
	 * returns the MatchType of the Word
	 * @return MatchType, either MatchType.PREFIX or MatchType.EXACT
	 */
	MatchType getMatch();
	
}
