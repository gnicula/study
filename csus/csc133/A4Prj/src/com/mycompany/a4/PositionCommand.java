package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

// New in A3, enables position mode which can be used to
// drop the currently selected game object to a new location.
public class PositionCommand extends Command {
	private GameWorld gw;

	public PositionCommand(String command, GameWorld world) {
		super(command);
		gw = world;
	}

	// This method is called by Position button.
	// It then dispatches to the model to toggle position mode.
	public void actionPerformed(ActionEvent ev) {
		gw.enablePosition(true);
	}
}
