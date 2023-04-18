package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class PauseCommand extends Command {
	
	private Game game;

	public PauseCommand(String command, Game game) {
		super(command);
		this.game = game;
	}

	// This method is called by the 'Pause' button on the bottom panel
	// and switches between playing and select/edit mode.
	public void actionPerformed(ActionEvent ev) {
		// System.out.println("Pause");
		game.handlePause();
	}
}
