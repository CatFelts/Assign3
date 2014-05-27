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
import cs345feltsc.game.MatchType;
import cs345feltsc.game.Player;
import cs345feltsc.game.Room;
import cs345feltsc.game.Term;
import cs345feltsc.game.Word;

/**
 * This class builds a hard coded game.
 * 
 * @author Chris Reedy (Chris.Reedy@wwu.edu)
 */
public class HardCodedGameRoom implements GameDescription {
	
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
	
	public HardCodedGameRoom(Builder builder) {
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
	
	
	public Game build(InputStream in, PrintStream out) {
		
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
		
		builder.makeAction(vQuit, null, new ActionMethod() {
			@Override
			public void doAction(Game game, Word w1, Word w2) {
				 game.messageOut.println("Goodbye.");
				 game.parser.setExit(true);
			}
		});
		
		builder.makeAction(vKill, null, new ActionMethod() {
			@Override
			public void doAction(Game game, Word w1, Word w2) {
				game.messageOut.printf("What exactly is it you want me to %s?", w1.getWord());
			}
		});
		
		final Term vKillStuff = builder.makeTerm("killstuff");
		addWords(vKillStuff, MatchType.PREFIX, "gold", "silver", "floor", "wall");

		builder.makeAction(vKill, vKillStuff, new ActionMethod() {
			@Override
			public void doAction(Game game, Word w1, Word w2) {
			    game.messageOut.printf("How exactly do you propose that I %s the %s?", w1.getWord(), w2.getWord());
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

		final Room rBallroom = builder.makeRoom("ballroom", "You have entered the ballroom. The room has a high ceiling with two magnificent crystal chandeliers which illuminate the room.");

		builder.makePath(vEastIn, rBalcony, rNorth);
		builder.makePath(vWestOutExit, rNorth, rBalcony);
		builder.makePath(vSouth, rNorth, rSouth);
		builder.makePath(vNorth, rSouth, rNorth);
		builder.makePath(vEast, rSouth, rBallroom);
		builder.makePath(vWest, rBallroom, rSouth);
		
		builder.makeAction(vMove, vDirect, new ActionMethod() {
			@Override
			public void doAction(Game game, Word w1, Word w2) {
				Room loc = game.thePlayer.getLocation();
				game.thePlayer.moveOnPath(w2);
				if (loc != game.thePlayer.getLocation())
					// The location changed.
				    game.thePlayer.lookAround();
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
				    game.messageOut.printf("I don't understand %s.", w1.getWord());
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
		final Term vObjects = builder.makeTerm("objects");
		addWords(vObjects, MatchType.PREFIX, "message", "paper", "coin", "goldcoin");
		final Term vRead = builder.makeTerm("read");
		addWords(vRead, MatchType.PREFIX, "read");
		
        builder.makeAction(vGet, vObjects, new ActionMethod() {
            @Override
            public void doAction(Game game, Word w1, Word w2) {
                game.messageOut.printf("You told me to %s %s.", w1.getWord(), w2.getWord());
            }
        });
        
        builder.makeAction(vDrop, vObjects, new ActionMethod() {
            @Override
            public void doAction(Game game, Word w1, Word w2) {
                game.messageOut.printf("You told me to %s %s.", w1.getWord(), w2.getWord());
            }
        });
        
        builder.makeAction(vExamine, vObjects, new ActionMethod() {
            @Override
            public void doAction(Game game, Word w1, Word w2) {
                game.messageOut.printf("I'm looking at %s.", w2.getWord());
            }
        });
        
        builder.makeAction(vInventory, null, new ActionMethod() {
            @Override
            public void doAction(Game game, Word w1, Word w2) {
                game.messageOut.print("You are not carrying anything.");
            }
        });
        
        builder.makeAction(vRead, vMessage, new ActionMethod() {
            @Override
            public void doAction(Game game, Word w1, Word w2) {
                game.messageOut.printf("The %s says, \"Enjoy your game.\"", w2.getWord());
            }
        });

		/* Create the player object. */
		Player player = builder.makePlayer("player");
		
		/* Call the builder to finish the build. */
		Game result = builder.buildComplete();

		/* Send the player to the starting location. */
		player.apportTo(rBalcony);
		player.lookAround();
		
		return result;
	}
}
