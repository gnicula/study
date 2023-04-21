package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class AccelerateCommand extends Command {
	
	private GameWorld gw;

	public AccelerateCommand(String command, GameWorld world) {
		super(command);
		gw = world;
	}
	
	// This method is called by the accelerate button on the left panel
	// or by pressing 'a' or by using the side menu 'Accelerate' option.
	// Dispatches to the model to perform the accelerate.
	public void actionPerformed(ActionEvent ev) {
		// System.out.println("Accelerate");
		gw.accelerate();
	}
}