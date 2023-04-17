package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;

// This class simulates a collision between the 
// Player robot and the given Base by sequenceNumber
public class CollideWithBaseCommand extends Command {
private GameWorld gw;
	
	public CollideWithBaseCommand(String command, GameWorld world) {
		super(command);
		gw = world;
	}
	
	// The action uses the helper private method to create 
	// a dialog with an input text field to get the base number
	// for simulating a collision.
	public void actionPerformed(ActionEvent ev) {
		// System.out.println("Collided with a Base");
		int baseNum = createBaseCollisionDialog();
		gw.collideWithBase(baseNum);
	}
	
	// Helper method that creates the dialog
	private int createBaseCollisionDialog() {
		Command cOk = new Command("Confirm");
		Command cCancel = new Command("Cancel");
		Command[] cmds = new Command[]{cOk, cCancel};
		TextField myTF = new TextField();
		Command c = Dialog.show("Enter Base number:", myTF, cmds);
		int baseNum = 0;
		if (c == cOk) {
			baseNum = Integer.parseInt(myTF.getText());
		} 
		return baseNum;
	}
}
