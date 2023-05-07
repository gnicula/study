package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class StrategiesCommand extends Command {
	
	private GameWorld gw;

	public StrategiesCommand(String command, GameWorld world) {
		super(command);
		gw = world;
	}

	// Calls changeStrategy when the 'change strategy' button is pushed 
	// in the bottom container. 
	public void actionPerformed(ActionEvent ev) {
		gw.changeStrategy();
	}
}
