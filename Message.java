/**
 * This work is licensed under the Creative Commons Attribution 3.0
 * Unported License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to
 * Creative Commons, 444 Castro Street, Suite 900,
 * Mountain View, California, 94041, USA. 
 */

package cs345feltsc.interpret;


/**
 * This is the interface provided simple messages.
 * 
 * @author Chris Reedy (Chris.Reedy@wwu.edu)
 */
public interface Message {
	
	
	/**
	 * Output the message.
	 * 
	 * @param out  A game stream where the message is to be output.
	 */
	void print(GamePrintStream out);
	
	/**
	 * Output the message with the chosen additional arguments 
	 * substituted.
	 * 
	 * For purposes of this interface, a message that does not require
	 * arguments is output as if msg.print(out) was called.
	 *
	 * @param out  A stream where the message is to be output.
	 * @param args The strings for filling in parameters.
	 */
	void printArgs(GamePrintStream out, String... args);

}
