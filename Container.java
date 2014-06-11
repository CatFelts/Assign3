/**
 * This work is licensed under the Creative Commons Attribution 3.0
 * Unported License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to
 * Creative Commons, 444 Castro Street, Suite 900,
 * Mountain View, California, 94041, USA. 
 */

package cs345feltsc.game;

import java.util.Collection;

/**
 * This is the interface provided for Containers.
 * 
 * @author Chris Reedy (Chris.Reedy@wwu.edu)
 */

public interface Container {
    
    /**
     * Add the given object to the container.
     * 
     * @param obj  the object to be added to the container.
     */
    public void addObject(GameObject obj);
	
    /**
     * Remove the given object from the container.
     * 
     * @param obj  the object to be removed from the container.
     */
	public void removeObject(GameObject obj);
	
    /**
     * True if the given object is in the container.
     * 
     * @param obj  the object to be checked
     * @return  true if the object is in the container
     */
	public boolean contains(GameObject obj);
	
    /**
     * A collection of all the objects in the container.
     * 
     * @return  the collection
     */
	public Collection<GameObject> getContents();

}
