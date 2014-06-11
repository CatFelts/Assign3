/**
 * This work is licensed under the Creative Commons Attribution 3.0
 * Unported License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to
 * Creative Commons, 444 Castro Street, Suite 900,
 * Mountain View, California, 94041, USA. 
 */

package cs345feltsc.reader;

import java.io.InputStream;

import cs345feltsc.game.Game;
import cs345feltsc.interpret.GamePrintStream;

/**
 * This is the interface that must be implemented by classes that build games.
 * In addition to the method describer in this interface, the classes must have
 * a constructor of the form
 * 
 *   public Class(Builder b);
 * 
 * @author Chris Reedy (Chris.Reedy@wwu.edu)
 */
public interface GameDescription {

	/**
	 * Build the game.
	 * @param in an InputStream that will be the input for the game.
	 * @param out a PrintStream that will be the output for the game.
	 * @return the constructed Game.
	 */
	public Game build(InputStream in, GamePrintStream out);
	
}
