package com.mycompany.a2;

import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class ExitDialogCommand extends Command {
	private GameWorld gw;

	public ExitDialogCommand(String command, GameWorld world) {
		super(command);
		gw = world;
	}

	public void actionPerformed(ActionEvent ev) {
		displayExitDialogue();
	}

	private void displayExitDialogue() {
		Command cOk = new Command("Confirm");
		Command cCancel = new Command("Cancel");
		Command[] cmds = new Command[] { cOk, cCancel };
		Command response = Dialog.show("Exit Game?", "", cmds);
		
		if (response == cOk) {
			gw.exit();
		} 
	}
}
