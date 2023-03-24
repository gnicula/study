package com.mycompany.a2;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CollideWithEnergyStationCommand extends Command {
private GameWorld gw;
	
	public CollideWithEnergyStationCommand(String command, GameWorld world) {
		super(command);
		gw = world;
	}
	
	public void actionPerformed(ActionEvent ev) {
		System.out.println("Collided with an Energy Station");
		gw.collideWithEnergyStation();
	}
	
	
}
