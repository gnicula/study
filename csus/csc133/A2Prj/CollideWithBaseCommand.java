package com.mycompany.a2;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CollideWithBaseCommand extends Command {
private GameWorld gw;
	
	public CollideWithBaseCommand(String command, GameWorld world) {
		super(command);
		gw = world;
	}
	
	public void actionPerformed(ActionEvent ev) {
		System.out.println("Collided with a Base");
		int baseNum = Game.createBaseCollisionDialog();
		gw.collideWithBase(baseNum);
	}
	
	
}
