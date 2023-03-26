package com.mycompany.a2;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

// This class is a debug command that I used to visually see
// the position of the game objects. 
// I used it to test my implementation of non player robot strategies
// and generally was helpful to get visual feedback of the model 
public class DebugMapViewCommand extends Command {

	private MapView mv;
	private GameWorld gw;

	public DebugMapViewCommand(String command, MapView mv, GameWorld gw) {
		super(command);
		this.mv = mv;
		this.gw = gw;
	}
	
	// Called from the overflow menu item 'activate debug'
	// and its going to create a label with an image to be 
	// able to paint basic visual markers for the game objects.
	public void actionPerformed(ActionEvent ev) {
		// DEBUG VIEW ONLY
		mv.createDebugMapviewImage(gw);
		setEnabled(false);
	}

}
