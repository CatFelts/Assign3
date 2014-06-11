package cs345feltsc.game;

public class PWordType implements WordType {
	
	private String word;
	private static final MatchType match = MatchType.PREFIX;
	
	public PWordType(String word){
		this.word = word;
	}

	@Override
	public String getWord() {
		return word;
	}

	@Override
	public MatchType match(String token) {
		MatchType notMatch = MatchType.NONE;
		if(token.equalsIgnoreCase(word))
			return match;
		else return notMatch;
	}
	
	public MatchType getMatch(){
		return match;
	}

}
