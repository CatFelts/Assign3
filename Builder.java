/**
 * This work is licensed under the Creative Commons Attribution 3.0
 * Unported License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to
 * Creative Commons, 444 Castro Street, Suite 900,
 * Mountain View, California, 94041, USA. 
 */

package cs345feltsc.reader;

import java.io.InputStream;

import cs345feltsc.game.ActionMethod;
import cs345feltsc.game.Game;
import cs345feltsc.game.GameObject;
import cs345feltsc.game.MatchType;
import cs345feltsc.game.Player;
import cs345feltsc.game.Room;
import cs345feltsc.game.Term;
import cs345feltsc.game.Word;
import cs345feltsc.interpret.GamePrintStream;
import cs345feltsc.interpret.Message;

/**
 * This is the interface provided for classes that build games.
 * 
 * @author Chris Reedy (Chris.Reedy@wwu.edu)
 */
public interface Builder {
	
    /**
     * Do any setup that is required before calling any of the makeXXX 
     * methods. This method should create the game object that's
     * returned by buildComplete and capture the values of in and out
     * for use later.
     */
	public void startBuild(InputStream in, GamePrintStream out);
	
	/**
	 * Do any final work that is required after all the makeXXX
	 * methods have been called.
	 * 
	 * This implementation must return the constructed game object.
	 */
	public Game buildComplete();
	
	/**
	 * Make a Word
     * 
     * @param word
     *            the string value for the word
     * @param match
     *            the MatchType for the word, either MatchType.PREFIX or
     *            MatchType.EXACT
	 * @return the created Word object
	 */
	public Word makeWord(String word, MatchType match);
	
	/**
	 * Make a Term.
     * 
     * @param name
     *            the String name for the Term
	 * @return The created Term object
	 */
	public Term makeTerm(String name);
	
	/**
	 * Make a Room.
     * 
     * @param name
     *            the String name for the Room
     * @param desc
     *            the String description for the Room
	 * @return The created Room object
	 */
	public Room makeRoom(String name, String desc);
	
	/**
	 * Make a Path between rooms
     * 
     * @param vocab
     *            an Term for words that designate the path
     * @param from
     *            the Room object where the path originates
     * @param to
     *            the Room object where the path terminates
	 */
	public void makePath(Term vocab, Room from, Room to);
	
	/**
	 * Make an Action and add it to the set of all Actions.
     * 
     * @param vocab1
     *            the first Term for this Action.
     * @param vocab2
     *            the second Term for this Action or null if there is no second
     *            Term.
     * @param action
     *            the actual action to be executed.
	 */
	public void makeAction(Term vocab1, Term vocab2, ActionMethod action);
	
	/**
	 * Make a Player.
     * 
     * @param name
     *            the name of the player
	 */
	public Player makePlayer(String name);
	
	/**
	 * Make a GameObject.
     * 
     * @param name
     *            the name of the object
     * @param vocab
     *            the vocab for the object
     * @param inventoryDesc
     *            the inventory description
     * @param hereIsDesc
     *            the "here is" description
     * @param longDesc
     *            the long description
	 * @return the created GameObject
	 */
	public GameObject makeGameObject(String name, Term vocab,
			String inventoryDesc, String hereIsDesc, String longDesc);
    
    /**
     * Make a message containing the given string.
     * @param msg  the String with the message
     * @return  the created message object
     */
    public Message makeMessage(String msg);

    /**
     * Make a message consisting of the concatenation of msgs.
     * @param msgs  the Messages that make up the message
     * @return  the created message object
     */
    public Message makeMessage(Message... msgs);

    /**
     * Make a message that produces the indexth argument.
     * @param index   the index of the argument
     * @return  the created message object
     */
    public Message makeArgMessage(int index);

    /**
     * Make a message that cycles through a collection of messages. The first
     * time the message is printed, the first of the messages will be used.
     * The second time, the second, and so on. After the last is printed,
     * the messages will cycle back to the first message.
     * 
     * @param msgs  the Messages that make up the cycle
     * @return  the created message object
     */
    public Message makeCycleMessage(Message... msgs);
    
}
