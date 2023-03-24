package com.mycompany.a2;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CollideWithNPRCommand extends Command {

	private GameWorld gw;
	
	public CollideWithNPRCommand(String command, GameWorld world) {
		super(command);
		gw = world;
	}
	
	public void actionPerformed(ActionEvent ev) {
		System.out.println("Collided with NPR");
		gw.collideWithNPR();
	}
}
