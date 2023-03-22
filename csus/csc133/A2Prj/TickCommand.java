package com.mycompany.a2;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class TickCommand extends Command {
	public TickCommand(String command) {
		super(command);
	}

	public void actionPerformed(ActionEvent ev) {
		System.out.println("...");
	}
}
