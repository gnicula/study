package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

// This class creates an exit dialog with two buttons and exits if
// 'Confirm' was pressed.
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
