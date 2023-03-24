package com.mycompany.a2;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class HelpCommand extends Command {
	private GameWorld gw;

	public HelpCommand(String command, GameWorld world) {
		super(command);
		gw = world;
	}

	public void actionPerformed(ActionEvent ev) {
		displayHelpDialogue();
	}

	private void displayHelpDialogue() {
		Command cOk = new Command("Ok");
		String dialogText = "Accelerate - 'a'\nBrake - 'b'\nTurn Left - 'l'\nTurn Right - 'r'\nCollide With Energy Station - 'e'\nCollide With Drone - 'g'\nTick - 't'";
		Command[] cmds = new Command[] { cOk };
		Dialog.show("Game Help", dialogText, cmds);
	}
}
