package cs345feltsc.game;

import cs345feltsc.interpret.GamePrintStream;
import cs345feltsc.interpret.Message;

public class CycleMessage implements Message {
	
	private Message[] msgs;
	private int index;
	
	public CycleMessage(Message... msgs){
		this.msgs = msgs;
		index = 0;
	}

	@Override
	public void print(GamePrintStream out) {
		int i = index % msgs.length;
		Message msg = msgs[i];
		msg.print(out);
	}

	@Override
	public void printArgs(GamePrintStream out, String... args) {
		int i = index % msgs.length;
		index++;
		Message msg = msgs[i];
		msg.printArgs(out, args);
	}
	

}
