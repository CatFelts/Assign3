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
     * a List of all the game Words.
     */
    public List<Word> allWords;
    
    /**
     * a List of all the game Actions.
     */
    public List<Action> allActions;

    /**
     * a List of all the game Paths.
     */
    public List<Path> allPaths;
    
    /**
     * the Term of all the noisewords
     */
    public Term noisewords;
    
    /**
     * The player.
     */
	public Player thePlayer;
	
	/**
	 * The Room which the player is currently in
	 */
	public Room currentLoc;
	
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
     * A PrintStream that is used to send output to the user.
     */
	public PrintStream messageOut;
	
	public Game() {
		// Nothing to do so far.
	}

}
