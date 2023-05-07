package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

// This class opens up a dialog box with a list of key binds for commands.
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
		String dialogText = "Commnads available:\n" + 
				"Accelerate - 'a'\nBrake - 'b'\nTurn Left - 'l'\nTurn Right - 'r'\n";
		Command[] cmds = new Command[] { cOk };
		Dialog.show("Game Help", dialogText, cmds);
	}
}
