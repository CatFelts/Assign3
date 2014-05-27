/**
 * This work is licensed under the Creative Commons Attribution 3.0
 * Unported License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to
 * Creative Commons, 444 Castro Street, Suite 900,
 * Mountain View, California, 94041, USA. 
 */

package cs345feltsc.game;

/**
 * This is the interface defines an Action method which is the
 * method for an Action.
 * 
 * @author Chris Reedy (Chris.Reedy@wwu.edu)
 */
public interface ActionMethod {

	/**
	 * Do the action.
	 * @param game the game object for the action
	 * @param w1 the first word of the command that caused this action
	 * @param w2 the second word of the command or null if a one word command
	 */
	public void doAction(Game game, Word w1, Word w2);
	
}
