package com.mycompany.a2;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class AboutCommand extends Command {
	private GameWorld gw;

	public AboutCommand(String command, GameWorld world) {
		super(command);
		gw = world;
	}

	public void actionPerformed(ActionEvent ev) {
		displayHelpDialogue();
	}
	
	private void displayHelpDialogue() {
		Command cOk = new Command("Close");
		String dialogText = "Name: Gabriele Nicula\nCourse Name: CSC-133\nVersion: 2.1";
		Command[] cmds = new Command[] {cOk};
		Dialog.show("About", dialogText, cmds);
	}
}

