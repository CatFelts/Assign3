package cs345feltsc.game;

import cs345feltsc.interpret.GamePrintStream;
import cs345feltsc.interpret.Message;

public class ConcatMessage implements Message {
	
	private Message[] msgs;
	
	public ConcatMessage(Message... msgs){
		this.msgs = msgs;
	}

	@Override
	public void print(GamePrintStream out) {
		for(Message msg : msgs){
			msg.print(out);
		}

	}

	@Override
	public void printArgs(GamePrintStream out, String... args) {
		for(Message msg : msgs){
			msg.printArgs(out, args);
		}
	}

}
