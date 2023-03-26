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

	// DEBUG view of objects only
	private MapViewLabel imgLabel = null;

	public void update(Observable o, Object arg) {
		// Call the method in GameWorld (Observable) that outputs the
		// game object information to the console.
		GameWorld gw = (GameWorld) o;
		gw.mCommand();

		// DEBUG view only
		if (imgLabel != null) {
			repaint();
		}
	}

	// DEBUG view of objects only
	private class MapViewLabel extends Label {
		private GameWorld gwo;
		
		public MapViewLabel(GameWorld gw) {
			super();
			gwo = gw;
		}
		
		@Override
		public void paint(Graphics g) {
			// Set a light gray background 
			 g.setColor(ColorUtil.WHITE);
			 g.fillRect(getX(), getY(), gwo.getWidth(), gwo.getHeight());
			// Now paint all objects as rectangles on the image
			IIterator it = gwo.getGameObjectsIterator();
			while (it.hasNext() ) {
				GameObject go = it.getNext();
				g.setColor(go.getColor());
				g.fillRect((int)go.getX(), (int)go.getY(), go.getSize(), go.getSize());	
			}
		}
	}

	// Set up a debug view image that can be used to paint objects' location.
	public void createDebugMapviewImage(GameWorld gw) {
		if (imgLabel == null) {
			System.out.println("Create image of size: " + gw.getHeight() + ", " + gw.getWidth());
			Image img = Image.createImage(gw.getHeight(), gw.getWidth());
			imgLabel = new MapViewLabel(gw);
			imgLabel.setIcon(img);
			add(imgLabel);
		}
	}

}
