package cs345feltsc.game;

import java.util.ArrayList;
import java.util.Collection;

public class ContainerType implements Container {
	
	protected String name;
	protected Collection<GameObject> contents;
	
	public ContainerType(){
		name = null;
		contents = new ArrayList<GameObject>();
	}
	
	public ContainerType(String name, Collection<GameObject> contents){
		this.name = name;
		this.contents = contents;
	}

	@Override
	public void addObject(GameObject obj) {
		contents.add(obj);

	}

	@Override
	public void removeObject(GameObject obj) {
		contents.remove(obj);

	}

	@Override
	public boolean contains(GameObject obj) {
		if(contents.contains(obj))
			return true;
		return false;
	}

	@Override
	public Collection<GameObject> getContents() {
		return contents;
	}

}
