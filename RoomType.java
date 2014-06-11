package cs345feltsc.game;

import java.util.*;

public class RoomType extends ContainerType implements Room {
	
	private String desc;
	private Collection<Path> paths;
	
	public RoomType(String name , String desc){
		this.name = name;
		this.desc = desc;
		this.contents = new ArrayList<GameObject>();
		this.paths = new ArrayList<Path>();
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
