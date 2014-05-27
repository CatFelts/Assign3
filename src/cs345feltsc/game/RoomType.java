package cs345feltsc.game;

import java.util.*;

public class RoomType implements Room {
	
	private String name;
	private String desc;
	private Collection<GameObject> contents;
	private Collection<Path> paths;
	
	public RoomType(String name , String desc){
		this.name = name;
		this.desc = desc;
		this.contents = new ArrayList<GameObject>();
		this.paths = new ArrayList<Path>();
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

	@Override
	public String getDescription() {
		return desc;
	}
	
	public void addPath(Path path){
		paths.add(path);
	}
	
	public Collection<Path> getPaths(){
		return paths;
	}

	public String getName() {
		return name;
	}
	
	
	
}
