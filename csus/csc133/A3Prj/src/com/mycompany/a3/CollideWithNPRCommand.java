package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CollideWithNPRCommand extends Command {

	private GameWorld gw;
	
	public CollideWithNPRCommand(String command, GameWorld world) {
		super(command);
		gw = world;
	}
	
	// Similar to the other 'collide' commands, this one
	// dispatches to the corresponding method in the model.
	public void actionPerformed(ActionEvent ev) {
		// System.out.println("Collided with NPR");
		gw.collideWithNPR();
	}
}
