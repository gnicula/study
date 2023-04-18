package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

// Base class for all the game objects. 
public class GameObject implements IDrawable {
	private int size;
	private double xPos;
	private double yPos;
	private int color;
	private GameWorld world;
	public static final int STRING_OFFSET = 6;

	// Common to all game objects is size, location, color, and a handle to the model.
	public GameObject(int size, double x, double y, int color, GameWorld w) {
		this.size = size;
		this.xPos = x;
		this.yPos = y;
		this.color = color;
		this.world = w;
	}

	public int getSize() {
		return size;
	}

	public double getX() {
		return xPos;
	}

	public void setX(double x) {
		xPos = x;
	}

	public double getY() {
		return yPos;
	}

	public void setY(double y) {
		yPos = y;
	}

	public void setColor(int c) {
		color = c;
	}

	public int getColor() {
		return color;
	}

	public GameWorld getWorld() {
		return world;
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		// Override in derived classes to draw the object.
	}
	
	// This method is overridden by the base classes 
	// so they can add their specific attributes.
	public String toString() {
		String myDesc = "location=" + xPos + ", " + yPos + ", color(ARGB)=" + "[" + ColorUtil.alpha(color) + ","
				+ ColorUtil.red(color) + "," + ColorUtil.green(color) + "," + ColorUtil.blue(color) + "]" + ", size="
				+ size;
		;
		return myDesc;
	}

}
