/**
 * This work is licensed under the Creative Commons Attribution 3.0
 * Unported License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to
 * Creative Commons, 444 Castro Street, Suite 900,
 * Mountain View, California, 94041, USA. 
 */

package cs345feltsc.interpret;

/**
 * This is the interface provided for a "PrintStream" for a game.
 * 
 * @author Chris Reedy (Chris.Reedy@wwu.edu)
 */
public interface GamePrintStream {

	/**
	 * Print a String.
	 * @param s  The String to be printed.
	 */
	public void print(String s);
	
	/**
	 * Print a String and terminate the line.
	 * @param s  The String to be printed.
	 */
	public void println(String s);
	
	/**
	 * Terminate the current line.
	 */
	public void println();
	
	/**
	 * A convenience method to write a formatted string to the output
	 * stream using the specified format string and arguments.
	 * 
	 * This method is equivalent to print(String.format(format, args));
	 * 
	 * @param format  A format string as described in the Java library
	 *                Format string syntax.
	 * @param args  The arguments referenced by the format specifiers
	 *              in the format string.
	 */
	public void printf(String format, Object... args);
	
	/**
	 * Flush the stream. Write any buffered output and then flush the
	 * underlying output stream.
	 */
	public void flush();
}
