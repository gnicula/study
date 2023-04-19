package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

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
