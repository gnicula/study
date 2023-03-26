package com.mycompany.a2;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CollideWithEnergyStationCommand extends Command {
private GameWorld gw;
	
	public CollideWithEnergyStationCommand(String command, GameWorld world) {
		super(command);
		gw = world;
	}
	
	// This method is called by the 'collide with energy station' button
	// in the bottom container.
	// The method implemented in the model takes care of updating the
	// energy of the robot and the generation of a new energy station.
	public void actionPerformed(ActionEvent ev) {
		// System.out.println("Collided with an Energy Station");
		gw.collideWithEnergyStation();
	}
	
	
}
