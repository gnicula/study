package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class AboutCommand extends Command {
	private GameWorld gw;
	
	public AboutCommand(String command, GameWorld world) {
		super(command);
		gw = world;
	}

	// This is called by the 'About' menu item.
	public void actionPerformed(ActionEvent ev) {
		displayAboutDialogue();
	}
	
	// This method is a helper and displays the about dialog.
	private void displayAboutDialogue() {
		Command cOk = new Command("Close");
		String dialogText = "Name: Gabriele Nicula\nCourse Name: CSC-133\nVersion: 3.2" +
				"\n\nSounds from findsounds.com and pixabay.com";
		Command[] cmds = new Command[] {cOk};
		Dialog.show("About", dialogText, cmds);
	}
}

