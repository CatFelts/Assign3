package cs345feltsc.game;

import java.util.*;
import cs345feltsc.game.Game;



public class PlayerType extends ContainerType implements Player {

	private Room loc;
	private Game game;

	public PlayerType(String name, Collection<GameObject> contents, Room loc, Game game){
		this.name = name;
		this.contents = contents;
		this.loc = loc;
		this.game = game;
	}
	
	public PlayerType(String name, Game game){
		this(name, new ArrayList<GameObject>(), null, game);
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
	public boolean moveOnPath(Word pathName) {
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
		return moved;
	}

	@Override
	public void lookAround() {
		game.messageOut.print(loc.getDescription());
		for(GameObject obj : loc.getContents()){
			game.messageOut.print(" ");
			game.messageOut.print(obj.getHereIsDesc());
		}

	}
	

}
