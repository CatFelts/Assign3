/* This work is licensed under the Creative Commons Attribution 3.0
 * Unported License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to
 * Creative Commons, 444 Castro Street, Suite 900,
 * Mountain View, California, 94041, USA. 
 */

package cs345feltsc.game;

/**
 * This is the interface provided for Players.
 * 
 * @author Chris Reedy (Chris.Reedy@wwu.edu)
 */
public interface Player extends Container {

	/**
	 * Get the room that is the player's current location.
	 * @return the current location
	 */
	public Room getLocation();
	
	/**
	 * Move the player to the designated location.
	 * @param room the room to move the player to.
	 */
	public void apportTo(Room room);

	/**
	 * Start the player at the given room. Used to initialize the player
	 * at the start of the game.
	 * @param room the location to start the player.
	 */
	public void startAt(Room room);

	/**
	 * Move the player along the designated path.
	 * @param pathName the word giving the name of the path to use.
	 * 
	 * @return true if the player moved
	 */
	public boolean moveOnPath(Word pathName);
	
	/**
	 * Output a description of the current location of the player.
	 */
	public void lookAround();

}
