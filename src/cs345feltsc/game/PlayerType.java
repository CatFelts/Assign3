package cs345feltsc.game;

import java.util.*;



public class PlayerType implements Player {
	
	private String name;
	private Collection<GameObject> contents;
	private Room loc;
	private Collection<Path> paths;

	public PlayerType(String name, Collection<GameObject> contents, Room loc, Collection<Path> paths){
		this.name = name;
		this.contents = contents;
		this.loc = loc;
		this.paths = paths;
	}
	
	public PlayerType(String name){
		this(name, new ArrayList<GameObject>(), null, new ArrayList<Path>());
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
	public Room getLocation() {
		return loc;
	}

	@Override
	public void apportTo(Room room) {
		loc = room;
	}

	@Override
	public void startAt(Room room) {
		loc = room;
	}

	@Override
	public void moveOnPath(Word pathName) {
		boolean moved = false;
		ArrayList<Path> paths = (ArrayList<Path>) loc.getPaths();
		for(Path p: paths){
			if(p.getTerm().getWords().contains(pathName)){
				
				if(p.getStart().equals(loc)){
					loc = p.getEnd();
					moved = true;
				}
			}
		}
		if(moved == false){
			System.out.println("Error: No such path exists");
		}
	}

	@Override
	public void lookAround() {
		System.out.println(loc.getDescription());
		if(!loc.getContents().isEmpty()){
			for(GameObject obj : loc.getContents()){
				System.out.print(obj.getHereIsDesc());
			}
		}

	}
	

}
