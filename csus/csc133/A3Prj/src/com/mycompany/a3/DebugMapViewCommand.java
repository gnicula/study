package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

// This class is a debug command that can be used to perform
// manual 'ticks' of the GameWorld.
// It can be used to check expected behavior step by step.
public class DebugMapViewCommand extends Command {

	private boolean isActive = false;
	private Game game;

	public DebugMapViewCommand(String command, Game game) {
		super(command);
		this.game = game;
	}
	
	// Called from the overflow menu item 'activate debug'
	// Enters 'tick' mode so the game can be run step by step.
	public void actionPerformed(ActionEvent ev) {
		isActive = !isActive;
		game.enableDebugTicks(isActive);
	}

}
