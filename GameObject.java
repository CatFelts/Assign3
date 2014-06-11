/* This work is licensed under the Creative Commons Attribution 3.0
 * Unported License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to
 * Creative Commons, 444 Castro Street, Suite 900,
 * Mountain View, California, 94041, USA. 
 */

package cs345feltsc.game;

/**
 * This is the interface provided for Game Objects.
 * 
 * @author Chris Reedy (Chris.Reedy@wwu.edu)
 */
public interface GameObject {

    /**
     * Check to see if the given Word identifies this object.
     * 
     * @param w  the word to be matched.
     * @return  true if the word identifies this item.
     */
	public boolean match(Word w);
	
    /**
     * Get the inventory (short) description
     * @return  the description
     */
    public String getInventoryDesc();
    
    /**
     * Get the here is (short sentence) description.
     * @return  the desciption
     */
    public String getHereIsDesc();
    
    /**
     * Get the long (examine) description.
     * @return  the description
     */
    public String getLongDesc();
}
