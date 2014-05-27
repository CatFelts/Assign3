package cs345feltsc.game;

public class ObjectType implements GameObject {
	
	public String name;
	private Term vocab;
	private String inventoryDesc, hereIsDesc, longDesc;
	
	/**
	 * creates a new instance of ObjectType 
	 * @param name used as name
	 * @param vocab used as Term
	 * @param inventoryDesc used as inventoryDesc
	 * @param hereIsDesc used as hereIsDesc
	 * @param longDesc used as longDesc
	 */
	public ObjectType(String name, Term vocab, String inventoryDesc, String hereIsDesc, String longDesc){
		this.name = name;
		this.vocab = vocab;
		this.inventoryDesc = inventoryDesc;
		this.hereIsDesc = hereIsDesc;
		this.longDesc = longDesc;
	}
	
	 /**
     * Check to see if the given Word identifies this object.
     * 
     * @param w  the word to be matched.
     * @return  true if the word identifies this item.
     */
	@Override
	public boolean match(Word w) {
		for(Word word: vocab.getWords()){
			if(w.match(word.getWord())!=MatchType.NONE)
					return true;
		}
		return false;
	}

	/**
     * Get the inventory (short) description
     * @return  the description
     */
	@Override
	public String getInventoryDesc() {
		return inventoryDesc;
	}

	/**
     * Get the here is (short sentence) description.
     * @return  the desciption
     */
	@Override
	public String getHereIsDesc() {
		return hereIsDesc;
	}

	/**
     * Get the long (examine) description.
     * @return  the description
     */
	@Override
	public String getLongDesc() {
		return longDesc;
	}

}
