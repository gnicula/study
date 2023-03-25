package com.mycompany.a2;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.ComponentImage;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;

public class MapView extends Container implements Observer {

	// DEBUG view only
	private Label imgLabel;
	private Graphics gDbg;

	public void update(Observable o, Object arg) {
		// code here to call the method in GameWorld (Observable) that output the
		// game object information to the console

		GameWorld gw = (GameWorld) o;
		gw.mCommand();

		// DEBUG view only
//		gDbg.fillRect(200, 200, 200, 200);
//		imgLabel.paintComponent(gDbg);
	}

	// DEBUG view only
	@Override
	public void paint(Graphics g) {
		super.paint(g);

	}

	public void setGraphics(Graphics g) {
		gDbg = g;
		gDbg.setColor(ColorUtil.BLUE);
	}

	public void createDebugMapviewImage() {
		Image img = Image.createImage(getHeight(), getWidth());
		imgLabel = new Label() {
			@Override
			public void paint(Graphics g) {
			 // red color
			 g.setColor(0xff0000);
			 // paint the screen in red
			 g.fillRect(getX(), getY(), getWidth(), getHeight());
			 // draw hi world in white text at the top left corner of the screen
			 g.setColor(0xffffff);
			 g.drawString("Hi World", getX(), getY());
			 }
		};
//		imgLabel.setIcon(img);
		add(imgLabel);
//		setGraphics(img.getGraphics());
	}

}
