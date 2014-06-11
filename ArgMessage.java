package cs345feltsc.game;

import cs345feltsc.interpret.GamePrintStream;
import cs345feltsc.interpret.Message;

public class ArgMessage implements Message {
	
	private int index;
	
	public ArgMessage(int index){
		this.index = index;
	}

	@Override
	public void print(GamePrintStream out)  {
		throw new UnsupportedOperationException("Unable to print ArgMessage.");
	}

	@Override
	public void printArgs(GamePrintStream out, String... args) {
		out.print(args[index]);
	}

}
