package cs345feltsc.game;

import cs345feltsc.interpret.GamePrintStream;
import cs345feltsc.interpret.Message;

public class StringMessage implements Message {
	
	private String msg;
	
	public StringMessage(String msg){
		this.msg = msg;
	}

	@Override
	public void print(GamePrintStream out) {
		out.print(msg);
	}

	@Override
	public void printArgs(GamePrintStream out, String... args) {
		out.print(msg);
	}

}
