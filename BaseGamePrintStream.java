/**
 * This work is licensed under the Creative Commons Attribution 3.0
 * Unported License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to
 * Creative Commons, 444 Castro Street, Suite 900,
 * Mountain View, California, 94041, USA. 
 */

package cs345feltsc.interpret;

import java.io.*;
import java.lang.Object;

/**
 * This class is a GamePrintStream.
 * 
 * @author Chris Reedy (Chris.Reedy@wwu.edu)
 */
public class BaseGamePrintStream implements GamePrintStream {
	
	public final int MAX_LINE;
	public final String LINE_SEP = "\n";
	
	private PrintStream out;
	private StringBuilder buf = new StringBuilder();
	private int lineCount; 
	
	public BaseGamePrintStream(PrintStream out, int maxLine) {
		this.out = out;
		this.MAX_LINE = maxLine;
		lineCount = 0;
	}

	@Override
	public void print(String s) {
		buf.append(s);
		doOutput(false);
	}
	
	@Override
	public void println(String s) {
		buf.append(s);
		println();
	}
	
	@Override
	public void println() {
	    buf.append(LINE_SEP);
	    doOutput(false);
	}
	
	@Override
	public void printf(String s, Object... objs) {
		buf.append(String.format(s, objs));
		doOutput(false);
	}
	
	@Override
	public void flush() {
	    doOutput(true);
	}
	
    /**
     * Process output. Some subset of the buffer is output to out. If
     * flush is true, all of the buffer must be output. If flush is false,
     * some subset of the buffer may be output. Any residual part of the 
     * buffer that is not output remains in the buffer for subsequent calls.
     * 
     * The following rules are observed when producing output:
     *   - The buffer consists of lines which are separated by the string
     *     given by LINE_SEP.
     *   - A "full line" is a line that is ended by a LINE_SEP. A "partial
     *     line" is a line that is not a full line.
     *   - Each line consists of words separated by white space characters.
     *   - A line is output by outputting the words in the line separated
     *     by spaces. Any white space at the beginning of the line is 
     *     eliminated. Multiple white space characters are compressed to a
     *     single space.
     *   - At any point where the words in a line would exceed MAX_LINE,
     *     a call to out.println() is substituted for the space that
     *     would appear between words.
     *   - A full line is ended by a call to out.println(). No white space
     *     appears at the end of the output of a full line.
     *   - If a partial line ends in a white space character, a single white
     *     space character ends the output
     */
    private void doOutput(boolean flush) {
    	if(flush == true){
    		String[] lines = buf.toString().split("\n", -1);
    		for(String line : lines){
    			String[] words = line.split("\\s+");
    			if(words.length ==1){
					out.println();
					lineCount= 0;
    			}
    			for(int i =0; i<words.length; i++){
    				String word = words[i];
    				if(lineCount + word.length() >= MAX_LINE){
    					out.println();
    					out.print(word);
    					lineCount = word.length();
    				}
    				else{
    					if(i!=0){
    						out.print(" ");
    						lineCount++;
    					}
    					out.print(word);
    					lineCount += word.length();
    				}
    			}
    		}
    		buf.setLength(0);
    	}
    }
}