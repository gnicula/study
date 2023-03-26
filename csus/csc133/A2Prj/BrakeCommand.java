package com.mycompany.a2;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class BrakeCommand extends Command {
	private GameWorld gw;

	public BrakeCommand(String command, GameWorld world) {
		super(command);
		gw = world;
	}

	// Brake button will trigger this action and we just dispatch
	// it to the model.
	public void actionPerformed(ActionEvent ev) {
		System.out.println("Decreased Speed");
		gw.brake();
	}
}
