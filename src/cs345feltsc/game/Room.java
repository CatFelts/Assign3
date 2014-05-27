/**
 * This work is licensed under the Creative Commons Attribution 3.0
 * Unported License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to
 * Creative Commons, 444 Castro Street, Suite 900,
 * Mountain View, California, 94041, USA. 
 */

package cs345feltsc.game;

import java.util.*;



/**
 * This is the interface provided for Rooms.
 * 
 * @author Chris Reedy (Chris.Reedy@wwu.edu)
 */
public interface Room extends Container {
	
    /**
     * Get the description for the room.
     * @return the String containing the description.
     */
	public String getDescription();
	
	/**
	 * Get the name of the room.
	 * @return the String set as the name
	 */
	public String getName();

	public Collection<Path> getPaths();

	public void addPath(Path newPath);


	
}
