package com.mycompany.a4;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class MapView extends Container implements Observer {

	private GameWorld gw = null;

	public MapView(GameWorld gw) {
		super();
		this.gw = gw;
	}
	
	public void update(Observable o, Object arg) {
//		gw = (GameWorld) o;
		// Call the method in GameWorld (Observable) that outputs the
		// game object information to the console.
		gw.mCommand();
		// Repaint will trigger paint() and it should paint all objects.
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		// Set a white background 
		 g.setColor(ColorUtil.WHITE);
		 g.fillRect(getX(), getY(), gw.getWidth(), gw.getHeight());
		// Iterate through all objects and ask them to draw() themselves
		 // using this Graphics object.
		IIterator it = gw.getGameObjectsIterator();
		while (it.hasNext()) {
			GameObject go = it.getNext();
			go.draw(g, new Point(getX(), getY()));
		}
	}

	// Used by the editing mode when game is paused.
	@Override 
	public void pointerPressed(int x, int y) {
		// Handle pointer pressed only when 'paused'
		if (gw.getPaused()) {
			int xPos = x - getParent().getAbsoluteX() - getX();
			int	yPos = y - getParent().getAbsoluteY() - getY();
			if (gw.isUserEdit()) {
				gw.moveSelectedObjectTo(xPos, yPos);
			} else {
				gw.selectObjectAt(xPos, yPos);
			}
		}
	}
	
}
