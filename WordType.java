package cs345feltsc.game;

/**
 * this is the extended interface for word
 * @author catfelts (feltsc@students.wwu.edu)
 *
 */

public interface WordType extends Word {
	
	/**
	 *Get the string for the word represented by this word.
	 * @return the String for the word
	 */
	@Override
	public String getWord();
	
	/**
	 * Check to see if the this word matches
	 * a String called token.
	 * @param token is the String that is being 
	 * matched with this Word's word
	 * @return MatchType if the tokens word and MatchType
	 * match the token, MatchType.NONE if there is no match
	 */
	@Override
	public MatchType match(String token);
	

}
