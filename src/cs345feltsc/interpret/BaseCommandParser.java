/**
 * This work is licensed under the Creative Commons Attribution 3.0
 * Unported License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to
 * Creative Commons, 444 Castro Street, Suite 900,
 * Mountain View, California, 94041, USA. 
 */

package cs345feltsc.interpret;

import java.io.InputStream;
import java.util.*;

import cs345feltsc.game.*;

/**
 * This class is the command parser for a game. 
 * @author Chris Reedy (Chris.Reedy@wwu.edu)
 */
public class BaseCommandParser implements CommandParser {

	private Game game;

	/**
     * Construct a new command interpreter.
     * 
     * @param game the Game object for this parser.
     */
	public BaseCommandParser(Game game) {
	    this.game = game;
		game.parser = this;
	}
	
    /**
     * Run the command interpreter.
     * @throws IOException
     */
	public void run() {
	    while (!game.exit) {
			runOneCommand();
		}
		game.messageOut.println();
	}
	
    /**
     * Set the exit flag to the specified value. The old value of exit
     * is returned.
     * @param exit the new value for the exit flag
     * @return the old value of the exit flag
     */
	@Override
	public boolean setExit(boolean exit) {
		boolean old = game.exit;
		game.exit = exit;
		return old;
	}
	
    /**
     * Run one command
     * @throws IOException if there is an IOException when reading or
     *         writing the input or output streams.
     */
	
	private void runOneCommand() {

		
		
		boolean dataRecieved = true;
		String token1 = null;
		String token2 = null;
		/*
		 * prompt the user to enter a command and call each word the user enters
		 * a "token" (while loop will continue until tokens entered by user
		 * matches to an Action labeled as finalAction)
		 */
		dataRecieved = true;
		token1 = null;
		token2 = null;
		Scanner scanner = new Scanner(game.commandIn);
		ArrayList<String> tokens = null;
		tokens = promptUser(scanner);
		if (tokens.size() == 0){
			dataRecieved = false;
		}
		else if (tokens.size() > 2) {
			System.out.println("Error: too many tokens entered!");
			dataRecieved = false;
		}

		
		Word word1 = null;
		Word word2 = null;
		ArrayList<Word> wordList1;
		ArrayList<Word> wordList2;

		if (dataRecieved) {
			token1 = tokens.get(0);
			wordList1 = matchTokenToWord(token1);
			if(wordList1.size() == 0){
				System.out.println("Error: no Words matched to token1 ("+token1+")");
				dataRecieved = false;
			}
			else if(wordList1.size() >=2){
				System.out.println("Error: multiple Word matches with token1 ("+token1+")");
				dataRecieved = false;
			}
			else
				word1 = wordList1.get(0);
			if(dataRecieved){
				if(tokens.size()==2){
					token2 = tokens.get(1);
					wordList2 = matchTokenToWord(token2);
					if(token2!=null && wordList2.size() == 0){
						System.out.println("Error: no Words matched to token2 ("+token2+")");
						dataRecieved = false;
					}
					else if(wordList2.size() >=2){
						System.out.println("Error: multiple Word matches with token2 ("+token2+")");
						dataRecieved = false;
					}
					else
						word2 = wordList2.get(0);
				}
			}
		}

		if(dataRecieved){
			
			
			
			Action act = matchWordsToAction(word1, word2);
			
			if (act != null) {
				act.doAction(game, word1, word2);
				System.out.println();
			} else
				System.out.println("Error: no Action matches command entered");

		}
	}



	/**
	 * matches a String the user entered to a Word in allWords
	 * @param token the user entered
	 * @return ArrayList of Word, if more than one Word, error for
	 * multiple Word match
     */
    public ArrayList<Word> matchTokenToWord(String token) {
		if(token == null)
			return null;
		ArrayList<Word> ans = new ArrayList<Word>();
		for (Word wrd : game.allWords) {
			if (wrd.match(token).equals(MatchType.EXACT)) {
				if (wrd.getWord().equals(token)) {
					ans.add(wrd);
					return ans;
				}
			} else if(wrd.match(token).equals(MatchType.PREFIX)){
				if (wrd.getWord().equals(token)) {
					ans.add(wrd);
					return ans;
				}
				else if (wrd.getWord().startsWith(token)) {
					if(ans.size()==0)
						ans.add(wrd);
					else if(ans.size() > 0){
						ans.add(wrd);
					}
				}
			}
		}
		return ans;
	}
    
    /**
     * Match given words to an action for the list of allActions.
     * @param wrd1 the first token entered by the user now matched
     * to a Word instead of a String.
     * @param wrd2 the second token entered by the user now matched
     * to a Word instead of a String.
     * @return the Action associated with the two Words, null if no
     * such Action exists.
     */
    public Action matchWordsToAction(Word wrd1, Word wrd2){
    	Term term1;
    	Term term2;
    	boolean match1 = false;
    	boolean match2 = false;
    	for(Action act : game.allActions){
    		term1 = act.getTerm1();
    		term2 = act.getTerm2();
    		
    		for(Word w: term1.getWords()){
    			if(w.getWord().equals(wrd1.getWord())){
    				MatchType theMatch = w.match(wrd1.getWord());
    				if(!theMatch.equals(MatchType.NONE))
    					match1 = true;
    			}
    		}
    		
    		if(term2!=null &&wrd2!=null){
    			for(int i =0; i<term2.getWords().size(); i++){
    				Word w = term2.getWords().get(i);
    				if(w.getWord().equals(wrd2.getWord())){
    					if(w.match(wrd2.getWord())!=MatchType.NONE)
    						match2 = true;
    				}
    			}
    		} else if(term2 ==null && wrd2 == null)
    			match2 = true;
    		
    		if(match1== true && match2 ==true)
    			return act;
    	}
    	return null;
    }
    
  
    /**
     * prompt user to enter a command
     * @param sc the scanner associated with the game
     * @return and ArrayList of the tokens entered by the user
     */
    public ArrayList<String> promptUser (Scanner sc){
		ArrayList<String> tokens = new ArrayList<String>();
		System.out.println("Enter a command: ");
		String input = sc.nextLine();
		
		StringTokenizer st = new StringTokenizer(input);
		boolean isNoiseword = false;

		while (st.hasMoreTokens()) {
			isNoiseword = false;
			String response = st.nextToken();
			for(Word w : game.noisewords.getWords()){
				if(response.equals(w.getWord()))
					isNoiseword = true;
			}
			if(!isNoiseword)
			tokens.add(response);
		}
		return tokens;
	}
    
    

}
