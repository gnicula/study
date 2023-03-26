package com.mycompany.a2;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CollideWithDroneCommand extends Command {
private GameWorld gw;
	
	public CollideWithDroneCommand(String command, GameWorld world) {
		super(command);
		gw = world;
	}
	
	// Called by the 'collide with a drone' button in the bottom container.
	public void actionPerformed(ActionEvent ev) {
		// System.out.println("Collided with Drone");
		gw.collideWithDrone();
	}
}
