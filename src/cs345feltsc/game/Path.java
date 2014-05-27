package cs345feltsc.game;

public class Path {
	private Term vocab;
	private Room from, to;
	
	public Path(Term vocab, Room from, Room to){
		this.vocab = vocab;
		this.from = from;
		this.to = to;
	}
	
	public Term getTerm(){
		return vocab;
	}
	
	public Room getStart(){
		return from;
	}
	
	public Room getEnd(){
		return to;
	}
}
