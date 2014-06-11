package cs345feltsc.game;

import java.util.*;

public class TermType implements Term {
	
	private String name;
	private List<Word> words;
	
	public TermType(String name, List<Word> words){
		this.name = name;
		this.words = words;
	}

	@Override
	public void addWord(Word word) {
		words.add(word);
	}
	
	public ArrayList<Word> getWords(){
		return (ArrayList<Word>) words;
	}

	public String getName(){
		return name;
	}
}
