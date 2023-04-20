package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

// This class is a debug command that I used to visually see
// the position of the game objects. 
// I used it to test my implementation of non player robot strategies
// and generally was helpful to get visual feedback of the model 
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
