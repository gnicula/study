package com.mycompany.a2;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class LeftCommand extends Command {
	
	private GameWorld gw;

	public LeftCommand(String command, GameWorld world) {
		super(command);
		gw = world;
	}

	// This method is called by the 'Left' button on the left panel
	// and dispatches to the model to perform the left steering input.
	public void actionPerformed(ActionEvent ev) {
		// System.out.println("Left");
		gw.turnLeft();
	}
}
