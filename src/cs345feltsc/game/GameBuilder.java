/**
 * This work is licensed under the Creative Commons Attribution 3.0
 * Unported License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to
 * Creative Commons, 444 Castro Street, Suite 900,
 * Mountain View, California, 94041, USA. 
 */

package cs345feltsc.game;

import java.io.*;
import java.util.*;

import cs345feltsc.reader.Builder;

/**
 * This class is an implementation of a Builder for the game.
 * 
 * @author Chris Reedy (Chris.Reedy@wwu.edu)
 */
public class GameBuilder implements Builder {
    
    /* The game this GameBuilder is building. */
    Game game;
  
	/**
	 * Do any setup that is required before calling any of the makeXXX 
	 * methods. This method does not have to do anything.
	 */
	@Override public void startBuild(InputStream in, PrintStream out) {
		// Start a new game.
	    game = new Game();
	    game.commandIn = in;
	    game.messageOut = out;
	    
	    game.allWords = new ArrayList<Word>();
	    game.allActions = new ArrayList<Action>(); 
	    game.allPaths = new ArrayList<Path>();
	}
	
	/**
	 * Do any final work that is required after all the makeXXX
	 * methods have been called.
	 * 
	 * This implementation just returns the game object.
	 */
	@Override public Game buildComplete() {
		return game;
	}
	
	/**
	 * Make a Word
	 * @param word the string value for the word
	 * @param match the MatchType for the word, either MatchType.PREFIX
	 *              or MatchType.EXACT
	 * @return the created Word object
	 */
	@Override public Word makeWord(String word, MatchType match) {
		Word newWord = null;
		if(match.equals(MatchType.PREFIX))
			newWord = makePWord(word);
		else if(match.equals(MatchType.EXACT))
			newWord = makeEWord(word);
		if(newWord!=null)
			game.allWords.add(newWord);
		return newWord;
	}
	
	private PWordType makePWord(String word){
		PWordType newWord = new PWordType(word);
		return newWord;
	}
	
	private EWordType makeEWord(String word){
		EWordType newWord = new EWordType(word);
		return newWord;
	}
	/**
	 * Make a Term. If the name of the created term is "noisewords" the
	 * noiseWords attribute in Game is set to this term.
	 * @param name the String name for the Term
	 * @return The created Term object
	 */
	@Override
	public Term makeTerm(String name){
		Term newTerm = new TermType(name, new ArrayList<Word>());
		if(name.equals("noisewords"))
			game.noisewords = newTerm;
		return newTerm;
	}
	
	/**
	 * Make a Room.
	 * @param name the String name for the Room
	 * @param desc the String description for the Room
	 * @return The created Room object
	 */
	@Override
	public Room makeRoom(String name, String desc) {
		Room newRoom = new RoomType(name, desc);
		return newRoom;
	}
	
	/**
	 * Make a Path between rooms
	 * @param vocab an Term for words that designate the path
	 * @param from the Room object where the path originates
	 * @param to the Room object where the path terminates
	 */
	@Override
	public void makePath(Term vocab, Room from, Room to) {
	    Path newPath = new Path(vocab, from, to);
	    //store Path to Collection<Path> paths of associated Rooms 
	    game.allPaths.add(newPath);
	    from.addPath(newPath);
	}
	
	/**
	 * Make an Action and add it to the set of all Action.
	 * @param vocab1 the first Term for this Action.
	 * @param vocab2 the second Term for this Action or null if there
	 *               is no second Term.
	 * @param action the actual action to be executed.
	 */
	@Override
	public void makeAction(Term vocab1, Term vocab2, ActionMethod action) {
		Action newAction = new Action(vocab1, vocab2, action);
		game.allActions.add(newAction);
	}
	
	/**
	 * Make a Player. The field thePlayer in the Game object is set to the
	 * created Player object.
	 * @param name the name of the player
	 */
	@Override
	public Player makePlayer(String name) {
		Player newPlayer = new PlayerType(name);
		game.thePlayer = newPlayer;
		return newPlayer;
	}
	
	/**
	 * Make a GameObject.
	 * @param name the name of the object
	 * @param vocab the vocab for the object
	 * @param inventoryDesc the inventory description
	 * @param hereIsDesc the "here is" description
	 * @param longDesc the long description
	 * @return the created GameObject
	 */
	@Override
	public GameObject makeGameObject(String name, Term vocab,
			String inventoryDesc, String hereIsDesc, String longDesc) {
		GameObject obj = new ObjectType(name, vocab, inventoryDesc, hereIsDesc, longDesc);
		game.allObjects.add(obj);
		return obj;
	}
}
