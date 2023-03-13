package com.mycompany.a1;

import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;

public class Game extends Form {
	private GameWorld gw;

	public Game() {
		gw = new GameWorld();
		gw.init();
		play();
	}

	private void play() {
		Label myLabel = new Label("Enter a Command:");
		this.addComponent(myLabel);
		final TextField myTextField = new TextField();
		this.addComponent(myTextField);
		this.show();

		myTextField.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {

				String sCommand = myTextField.getText().toString();
				myTextField.clear();
				switch (sCommand.charAt(0)) {
				case 'a':
					//TODO tell game world to accelerate
					break;
				case 'b':
					//TODO tell game world to brake
					break;
				case 'l':
					//TODO tell robot to turn left
					
					break;
				case 'r':
					//TODO tell robot to turn right
					break;
				
				case 'm':
					gw.mCommand();
				case 'x':
					gw.exit();
					break;
				// add code to handle rest of the commands
				} // switch
			} // actionPerformed
		} // new ActionListener()
		); // addActionListener
	} // play

	public void actionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub

	} // actionPerformed

	// new ActionListener()
// addActionListener
}
