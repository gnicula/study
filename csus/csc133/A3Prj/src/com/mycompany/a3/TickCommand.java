package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class TickCommand extends Command {
	
	private GameWorld gw;

	public TickCommand(String command, GameWorld world) {
		super(command);
		gw = world;
	}

	// Calls tick() when 'Tick' is pressed in the bottom container.
	public void actionPerformed(ActionEvent ev) {
		gw.tick(100);
	}
}
