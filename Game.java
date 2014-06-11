/* This work is licensed under the Creative Commons Attribution 3.0
 * Unported License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to
 * Creative Commons, 444 Castro Street, Suite 900,
 * Mountain View, California, 94041, USA. 
 */

package cs345feltsc.game;

import java.util.*;
import java.io.*;

import cs345feltsc.interpret.CommandParser;
import cs345feltsc.interpret.GamePrintStream;


/**
 * This class contains the global objects for a game.
 * 
 * @author Chris Reedy (Chris.Reedy@wwu.edu)
 */
public class Game {
		
    /**
     * A collection of all the game objects.
     */
	public Collection<GameObject> allObjects = new HashSet<GameObject>();
	
	/**
	 * A list of all the Words made by the GameBuilder
	 */
	public List<Word> allWords;
	
	/**
	 * A list of all the Actions made by the GameBuilder
	 */
	public List<Action> allActions;
	
	/**
	 * A list of all the Terms categorized as "nosiewords"
	 * (There should be only one term in the list, and this Term
	 * contains the Words categorized as "noisewords" by the
	 * HardCodedGame).
	 */
	public List<Term> noisewords;

    /**
     * The player.
     */
	public Player thePlayer;
	
    /**
     * This boolean controls the execution of the command parser.
     * Setting this boolean to true, using setExit, will cause the
     * interpreter to exit after completing the current command.
     */
	public boolean exit = false;
    
    /**
     * The command parser.
     */
	public CommandParser parser;
	
    /**
     * An input stream that is used to get commands from the user.
     */
	public InputStream commandIn;
	
    /**
     * A GamePrintStream where output is sent to the user.
     */
	public GamePrintStream messageOut;
	
	public Game() {
		// Nothing to do so far.
	}

}
