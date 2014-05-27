package cs345feltsc.game;

import java.util.*;

public class Action {

	private Term term1, term2;
	private ActionMethod action;
	
	public Action(Word wrd1, Word wrd2, ActionMethod action){
		ArrayList<Word> words1 = new ArrayList<Word>();
		words1.add(wrd1);
		ArrayList<Word> words2 = new ArrayList<Word>();
		words2.add(wrd2);
		this.term1 = new TermType(null, words1);
		this.term2 = new TermType(null, words2);
		this.action = action;
		
	}
 
	public Action( Term term1, Term term2, ActionMethod action){
		this.term1 = term1;
		this.term2 = term2;
		this.action = action;
	}
 
	public void doAction(Game game, Word word1, Word word2){
		this.action.doAction(game, word1, word2);
	}	
	
	public TermType getTerm1(){
		return (TermType) term1;
	}
	
	public TermType getTerm2(){
		return (TermType) term2;
	}
	
	public ActionMethod getMethod(){
		return action;
	}
}
