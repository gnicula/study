package com.mycompany.a4;

import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

// Enables/disables game sound.
public class SoundCommand extends Command {
	private GameWorld gw;

	public SoundCommand(String command, GameWorld world) {
		super(command);
		gw = world;
	}

	// This method is called by the sound setting checkbox in the side menu.
	// It then dispatches to the model to update the ScoreView.
	public void actionPerformed(ActionEvent ev) {
		CheckBox cb = (CheckBox)ev.getComponent();
		gw.setSoundSetting(cb.isSelected());
	}
}
