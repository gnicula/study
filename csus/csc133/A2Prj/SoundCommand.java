package com.mycompany.a2;

import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class SoundCommand extends Command {
	private GameWorld gw;

	public SoundCommand(String command, GameWorld world) {
		super(command);
		gw = world;
	}

	public void actionPerformed(ActionEvent ev) {
		CheckBox cb = (CheckBox)ev.getComponent();
		gw.setSoundSetting(cb.isSelected());
	}
}
