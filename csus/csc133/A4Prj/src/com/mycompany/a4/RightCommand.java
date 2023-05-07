package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class RightCommand extends Command {
	
	private GameWorld gw;

	public RightCommand(String command, GameWorld world) {
		super(command);
		gw = world;
	}

	// This method is called by the 'Right' button on the right panel
	// and dispatches to the model to perform the right steering input.
	public void actionPerformed(ActionEvent ev) {
		// System.out.println("Right");
		gw.turnRight();
	}
}
