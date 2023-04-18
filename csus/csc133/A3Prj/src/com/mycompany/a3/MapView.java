package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.ComponentImage;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.geom.Point;

public class MapView extends Container implements Observer {

	private GameWorld gw = null;
	// DEBUG view of objects only
	private MapViewLabel imgLabel = null;

	public MapView( GameWorld gw) {
		super();
		this.gw = gw;
	}
	
	public void update(Observable o, Object arg) {
//		gw = (GameWorld) o;
		// Call the method in GameWorld (Observable) that outputs the
		// game object information to the console.
		gw.mCommand();
		// Repaint all objects.
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		// Set a light gray background 
		 g.setColor(ColorUtil.WHITE);
		 g.fillRect(getX(), getY(), gw.getWidth(), gw.getHeight());
		// Now paint all objects as rectangles on the image
		IIterator it = gw.getGameObjectsIterator();
		while (it.hasNext()) {
			GameObject go = it.getNext();
			go.draw(g, new Point(getX(), getY()));
//			g.setColor(go.getColor());
//			g.fillRect((int)go.getX(), (int)go.getY(), go.getSize(), go.getSize());	
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
			while (it.hasNext()) {
				GameObject go = it.getNext();
				go.draw(g, new Point(getX(), getY()));
//				g.setColor(go.getColor());
//				g.fillRect((int)go.getX(), (int)go.getY(), go.getSize(), go.getSize());	
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
