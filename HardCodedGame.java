/**
 * This work is licensed under the Creative Commons Attribution 3.0
 * Unported License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to
 * Creative Commons, 444 Castro Street, Suite 900,
 * Mountain View, California, 94041, USA. 
 */

package cs345feltsc.reader;

import java.io.*;
import java.util.*;

import cs345feltsc.game.ActionMethod;
import cs345feltsc.game.Game;
import cs345feltsc.game.GameObject;
import cs345feltsc.game.MatchType;
import cs345feltsc.game.Room;
import cs345feltsc.game.Term;
import cs345feltsc.game.Word;
import cs345feltsc.interpret.GamePrintStream;
import cs345feltsc.interpret.Message;

/**
 * This class builds a hard coded game.
 * 
 * @author Chris Reedy (Chris.Reedy@wwu.edu)
 */
public class HardCodedGame implements GameDescription {
	
	private Builder builder;
	
    private class WordData {
        MatchType m;
        Word word;
        
        WordData(MatchType m, Word word) {
            this.m = m;
            this.word = word;
        }
    }
    
    /* wordMap is a used inside the HardCodedGame to track constructed
     * words. This is used to keep build() from building the same word
     * multiple times. wordMap is private to the HardCodedGame class --
     * and should stay that way!
     */
    private Map<String, WordData> wordMap;
    
    public HardCodedGame(Builder builder) {
        this.builder = builder;
    }
    
    private Word makeWord(String w, MatchType m) {
        WordData wData = wordMap.get(w);
        if (wData != null) {
            assert wData.m == m : "Differing MatchTypes for " + w; 
            return wData.word;
        }
        Word newWord = builder.makeWord(w, m);
        wordMap.put(w, new WordData(m, newWord));
        return newWord;
    }
    
    private void addWords(Term vocab, MatchType match, String... words) {
        for (String w : words) {
            Word word = makeWord(w, match);
            vocab.addWord(word);
        }
    }
    
    Map<String, Message> messageMap = new HashMap<String, Message>();
    
    private Message _m(String smsg) {
        Message msg = messageMap.get(smsg);
        if (msg != null)
            return msg;
        msg = builder.makeMessage(smsg);
        messageMap.put(smsg, msg);
        return msg;
    }
    
    private Message _am(String smsg) {
        Message msg = messageMap.get(smsg);
        if (msg != null)
            return msg;
        /* At this point scan the message looking for % markers.
         * A %n (n in 1 .. 9) indicates that an argument is to be
         * substituted at this point.
         * A %% substitutes a percent sign.
         * 
         * XXX This does not allow for more than 9 arguments other niceties.
         */
        List<Message> msgParts = new ArrayList<Message>();
        int b = 0;
        for (int i = 0; i < smsg.length(); ++i) {
            if (smsg.charAt(i) != '%')
                continue;
            /* Found a % sign. Insert substitution.
             * XXX No check for exceeding string length, etc. is made.
             */
            msgParts.add(_m(smsg.substring(b, i)));
            i += 1;
            b = i + 1;
            char indicator = smsg.charAt(i);
            if (indicator == '%')
                msgParts.add(_m("%"));
            else
                msgParts.add(builder.makeArgMessage(indicator - '1'));
        }
        if (b < smsg.length())
            msgParts.add(_m(smsg.substring(b, smsg.length())));
        msg = builder.makeMessage(msgParts.toArray(new Message[msgParts.size()]));
        return msg;
    }
    
    /**
     * This method locates an object by name in allObjects.
     * @param game the game object for the game
     * @param w a word designating the object
     * @return the found object or null if no object was found.
     */
    private static GameObject findObject(Game game, Word w) {
        for (GameObject it : game.allObjects) {
            if (it.match(w)) {
                return it;
            }
        }
        return null;
    }
    
    @Override public Game build(InputStream in, GamePrintStream out) {
	
		/* wordMap is needed for all versions of hardcoded game. */
		wordMap = new HashMap<String, WordData>();

		/* Call the builder to start the build. */
		builder.startBuild(in, out);
		
		/* This section contains basic commands that only require words,
		 * terms, and actions to work.
		 */
		Term noiseWords = builder.makeTerm("noisewords");
		addWords(noiseWords, MatchType.PREFIX, "a", "an", "the", "and", "it",
				"that", "this", "to", "at", "with", "room");
		
		final Word wQuit = makeWord("quit", MatchType.PREFIX);
		final Word wExit = makeWord("exit", MatchType.PREFIX);
		final Word wKill = makeWord("kill", MatchType.PREFIX);
		final Word wExecute = makeWord("execute", MatchType.PREFIX);
		final Word wMagic = makeWord("xyzzy", MatchType.EXACT);
		
		/* These define some basic Terms and Actions that can be
		 * used without having to define rooms and paths.
		 */
		final Term vQuit = builder.makeTerm("quit");
		vQuit.addWord(wQuit);
		vQuit.addWord(wExit);
		
		final Term vKill = builder.makeTerm("kill");
		vKill.addWord(wKill);
		vKill.addWord(wExecute);
		
		final Term vMagic = builder.makeTerm("magic");
		vMagic.addWord(wMagic);
		
		final Message mFinal = builder.makeMessage(_m("Hope you enjoyed your game."), _m(" Come back and play again."));
		builder.makeAction(vQuit, null, new ActionMethod() {
			@Override
			public void doAction(Game game, Word w1, Word w2) {
				mFinal.print(game.messageOut);
				game.parser.setExit(true);
			}
		});
		
        final Message mKill = builder.makeCycleMessage(builder.makeMessage(
                builder.makeCycleMessage(_m("What exactly is it"),
                        _m("I don't know what")), _am(" you want me to %1?")),
                _m("Why do you persist in asking that?"));
		builder.makeAction(vKill, null, new ActionMethod() {
			@Override
			public void doAction(Game game, Word w1, Word w2) {
				mKill.printArgs(game.messageOut, w1.getWord());
			}
		});
		
		final Term vKillStuff = builder.makeTerm("killstuff");
		addWords(vKillStuff, MatchType.PREFIX, "gold", "silver", "floor", "wall");

		final Message mKillStuff = builder.makeCycleMessage(
				_am("How exactly do you propose that I %1 the %2?"),
				_am("Are you so unhappy with the %2 that you want me to %1 it?"));
		builder.makeAction(vKill, vKillStuff, new ActionMethod() {
			@Override
			public void doAction(Game game, Word w1, Word w2) {
				mKillStuff.printArgs(game.messageOut, w1.getWord(), w2.getWord());
			}
		});
		
		/* This section adds Rooms, Paths, and Words, Terms, and Actions
		 * for moving around the game.
		 */
		final Term vMove = builder.makeTerm("move");
		addWords(vMove, MatchType.PREFIX, "move", "go", "proceed", "walk");
		
		final Term vNorth = builder.makeTerm("north");
		addWords(vNorth, MatchType.PREFIX, "north");
		final Term vSouth = builder.makeTerm("south");
		addWords(vSouth, MatchType.PREFIX, "south");
		final Term vEast = builder.makeTerm("east");
		addWords(vEast, MatchType.PREFIX, "east");
		final Term vEastIn = builder.makeTerm("eastorin");
		addWords(vEastIn, MatchType.PREFIX, "east", "in");
		final Term vWest = builder.makeTerm("west");
		addWords(vWest, MatchType.PREFIX, "west");
		final Term vWestOutExit = builder.makeTerm("westoutorexit");
		addWords(vWestOutExit, MatchType.PREFIX, "west", "out", "exit");
		final Term vDirect = builder.makeTerm("direction");
		addWords(vDirect, MatchType.PREFIX, "north", "south", "east", "west", "in", "out", "exit");
		
		final Term vLook = builder.makeTerm("look");
		addWords(vLook, MatchType.PREFIX, "look");
		
		final Term vAround = builder.makeTerm("around");
		addWords(vAround, MatchType.PREFIX, "around");

		final Room rBalcony = builder.makeRoom("balcony", "You are on a balcony facing west, overlooking a beautiful garden. The only exit from the balcony is behind you.");
		final Room rNorth = builder.makeRoom("northroom", "You are in the north end of the Big Room. The room extends south from here. There is an exit to the outside to the west.");
		final Room rSouth = builder.makeRoom("southroom", "You are in the south end of the Big Room. The room extends north from here. There is a door in the east wall.");
		final Room rMagic = builder.makeRoom("magicroom", "You are in the magic workshop. There are no doors in any of the walls.");

		final Room rBallroom = builder.makeRoom("ballroom", "You have entered the ballroom. The room has a high ceiling with two magnificent crystal chandeliers which illuminate the room. "
				+ "The floor is a polished wooden parquet with flowers inlaid around the edge. "
				+ "The north wall is lined with mirrors while the south wall has large windows which look out on a beautiful garden. "
				+ "There is a door in the west wall of the room.");

		builder.makePath(vEastIn, rBalcony, rNorth);
		builder.makePath(vWestOutExit, rNorth, rBalcony);
		builder.makePath(vSouth, rNorth, rSouth);
		builder.makePath(vNorth, rSouth, rNorth);
		builder.makePath(vEast, rSouth, rBallroom);
		builder.makePath(vWest, rBallroom, rSouth);
		
		final Message mDontKnowHowToMove = _am("I don't know how to %1 %2.");
		builder.makeAction(vMove, vDirect, new ActionMethod() {
			@Override
			public void doAction(Game game, Word w1, Word w2) {
				if (game.thePlayer.moveOnPath(w2))
					// The player moved.
				    game.thePlayer.lookAround();
				else
					// Player could not move
					mDontKnowHowToMove.printArgs(game.messageOut, w1.getWord(), w2.getWord());

			}
		});
		
		builder.makeAction(vLook, null, new ActionMethod() {
			@Override
			public void doAction(Game game, Word w1, Word w2) {
			    game.thePlayer.lookAround();
			}
		});
		
		builder.makeAction(vLook, vAround, new ActionMethod() {
			@Override
			public void doAction(Game game, Word w1, Word w2) {
			    game.thePlayer.lookAround();
			}
		});
		
		final Message mDontUnderstand = _am("I don't understand %1.");
		builder.makeAction(vMagic, null, new ActionMethod() {
			final Room fromRoom = rSouth;
			final Room toRoom = rMagic;
			
			@Override
			public void doAction(Game game, Word w1, Word w2) {
				Room loc = game.thePlayer.getLocation();
				if (loc == fromRoom) {
					/* Can do this, we're in the right location. */
				    game.thePlayer.apportTo(toRoom);
					game.thePlayer.lookAround();
				} else if (loc == toRoom) {
					/* Good idea, the magic word gets you out again. */
				    game.thePlayer.apportTo(fromRoom);
					game.thePlayer.lookAround();
				} else {
					/* Wrong location. Act like we don't know the word. */
					mDontUnderstand.printArgs(game.messageOut, w1.getWord());
				}
			}
		});
		
		/* This section adds vocabulary for GameObjects. */
		final Term vExamine = builder.makeTerm("examine");
		addWords(vExamine, MatchType.PREFIX, "examine");
		final Term vInventory = builder.makeTerm("inventory");
		addWords(vInventory, MatchType.PREFIX, "inventory");
		final Term vGet = builder.makeTerm("get");
		addWords(vGet, MatchType.PREFIX, "get");
		final Term vDrop = builder.makeTerm("drop");
		addWords(vDrop, MatchType.PREFIX, "drop");
		final Term vMessage = builder.makeTerm("message");
		addWords(vMessage, MatchType.PREFIX, "message", "paper");
		final Term vCoin = builder.makeTerm("coin");
		addWords(vCoin, MatchType.PREFIX, "coin", "goldcoin");
		final Term vWand = builder.makeTerm("magicwand");
		addWords(vWand, MatchType.PREFIX, "wand", "magicwand", "woodenwand");
		final Term vObjects = builder.makeTerm("objects");
		addWords(vObjects, MatchType.PREFIX, "wand", "magicwand", "message", "paper", "coin", "goldcoin");
		final Term vRead = builder.makeTerm("read");
		addWords(vRead, MatchType.PREFIX, "read");
        final Term vWave = builder.makeTerm("wave");
        addWords(vWave, MatchType.PREFIX, "wave");
		
        /* Create game objects. */
		final GameObject iMessage = builder.makeGameObject("paper", vMessage,
				"a piece of paper",
				"There is a piece of paper here.",
				"It's a piece of paper with some writing on it.");
		
		final GameObject iCoin = builder.makeGameObject("coin", vCoin,
				"a gold coin",
				"There is a gold coin here.",
				"It's a US golden double eagle.");

		final GameObject iWand = builder.makeGameObject("magicwand", vWand,
				"a wooden wand",
				"There is a wooden wand here.",
				"It's a beautifully carved wooden wand make of alder.");

		// Put the items in the rooms.
		rNorth.addObject(iCoin);
		rBallroom.addObject(iMessage);
		rMagic.addObject(iWand);
		
		final Message mAlreadyCarryingItem = _am("You are already carrying %1.");
		final Message mNowCarryingItem = _am("You are now carrying %1.");
		final Message mCantFindItem = _am("I can't find %1 here.");
		final Message mHaveDroppedItem = _am("You have dropped %1.");
		final Message mNotCarryingItem = _am("You are not carrying %1.");
		final Message mNotCarryingAnything = _m("You are not carrying anything.");
		final Message mCarryingExact1 = _am("You are carrying %1.");
		final Message mCarryingExact2 = _am("You are carrying %1 and %2.");
		final Message mCarryingStart = _m("You are carrying ");
		final Message mCarryingSep = _m(", ");
		final Message mCarryingSepAnd = _m(", and ");
		final Message mCarryingFinal = _m(".");

		builder.makeAction(vGet, vObjects, new ActionMethod() {
			@Override
			public void doAction(Game game, Word w1, Word w2) {
				GameObject obj = findObject(game, w2);
				assert (obj != null);
				if (game.thePlayer.contains(obj)) {
					mAlreadyCarryingItem.printArgs(game.messageOut, obj.getInventoryDesc());
				} else if (game.thePlayer.getLocation().contains(obj)) {
				    game.thePlayer.getLocation().removeObject(obj);
					game.thePlayer.addObject(obj);
					mNowCarryingItem.printArgs(game.messageOut, obj.getInventoryDesc());
				} else {
					mCantFindItem.printArgs(game.messageOut, w2.getWord());
				}
			}
		});
		
		builder.makeAction(vDrop, vObjects, new ActionMethod() {
			@Override
			public void doAction(Game game, Word w1, Word w2) {
				GameObject obj = findObject(game, w2);
				assert (obj != null);
				if (game.thePlayer.contains(obj)) {
				    game.thePlayer.removeObject(obj);
					game.thePlayer.getLocation().addObject(obj);
					mHaveDroppedItem.printArgs(game.messageOut, obj.getInventoryDesc());
				} else {
					mNotCarryingItem.printArgs(game.messageOut, w2.getWord());
				}
			}
		});
		
		builder.makeAction(vExamine, vObjects, new ActionMethod() {
			@Override
			public void doAction(Game game, Word w1, Word w2) {
				GameObject obj = findObject(game, w2);
				assert (obj != null);
				if (game.thePlayer.contains(obj)) {
				    game.messageOut.print(obj.getLongDesc());
				} else {
					mNotCarryingItem.printArgs(game.messageOut, obj.getInventoryDesc());
				}
			}
		});
		
		builder.makeAction(vInventory, null, new ActionMethod() {
			@Override
			public void doAction(Game game, Word w1, Word w2) {
				Collection<GameObject> c = game.thePlayer.getContents();
				if (c.isEmpty()) {
					mNotCarryingAnything.print(game.messageOut);
				} else if (c.size() <= 2) {
					Iterator<GameObject> iter = c.iterator();
					GameObject obj1 = iter.next();
					if (c.size() == 1) {
						mCarryingExact1.printArgs(game.messageOut, obj1.getInventoryDesc());
					} else {
						GameObject obj2 = iter.next();
						mCarryingExact2.printArgs(game.messageOut, obj1.getInventoryDesc(), obj2.getInventoryDesc());
					}
				} else {
					int objNo = 1;
					for (GameObject item : c) {
						if (objNo == 1)
							mCarryingStart.print(game.messageOut);
						else if (objNo == c.size())
							mCarryingSepAnd.print(game.messageOut);
						else
							mCarryingSep.print(game.messageOut);
						game.messageOut.print(item.getInventoryDesc());
						objNo += 1;
					}
					mCarryingFinal.print(game.messageOut);
				}
			}
		});

		final Message mNoWave = builder.makeMessage(
		        builder.makeCycleMessage(_m(""), _m("There's a swishing sound. ")),
		        builder.makeCycleMessage(_m("Nothing happens."),
		                                 _am("Little sparks follow the %1."),
		                                 _m("There's a bump, but nothing else happens.")));
		final Message mNoWand = _am("You don't have a %1.");
		builder.makeAction(vWave, vWand, new ActionMethod() {
		    @Override
		    public void doAction(Game game, Word w1, Word w2) {
		        if (game.thePlayer.contains(iWand))
		            mNoWave.printArgs(game.messageOut, w2.getWord());
		        else
                    mNoWand.printArgs(game.messageOut, w2.getWord());
		    }
		});

		final Message mMessageContents = _am("The %1 says , \"Enjoy your game.\"");				
		final Message mNoMessage = _am("You don't have a %1 to read.");
		builder.makeAction(vRead, vMessage, new ActionMethod() {
			@Override
			public void doAction(Game game, Word w1, Word w2) {
			    if (game.thePlayer.contains(iMessage))
				    mMessageContents.printArgs(game.messageOut, w2.getWord());
			    else
			        mNoMessage.printArgs(game.messageOut, w2.getWord());
			}
		});

		/* Welcome message */
		final Message mWelcome = _m("Welcome to your adventure. Have a good game.\n\n");
        
		/* Finally create the player and wrap up. */
		builder.makePlayer("You");
	
		/* Call the builder to finish the build. */
		Game result = builder.buildComplete();

		/* Send the player to the starting location. */
		result.thePlayer.apportTo(rBalcony);
		mWelcome.print(result.messageOut);
		result.thePlayer.lookAround();
		result.messageOut.println();
		result.messageOut.flush();
		
		return result;
	}
}
