package com.mycompany.a4;

import java.io.IOException;
import java.util.ArrayList;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Point;

// Base class for all the game objects. 
public class GameObject implements ICollider, IDrawable {
	private int size;
	private int color;
	private GameWorld world;
	// Vector to keep objects that this object is currently colliding with.
	protected ArrayList<GameObject> collidingWith;
	// New in A4: myTranslate, myRotate, and myScale private fields
	// of type Transform to perform local to top level transformations.
	// Also new in A4: there's no xPos and yPos - this are kept in myTranslate.
	Transform myTranslate, myRotate, myScale;
	
	public static final int STRING_OFFSET = 6;

	// Common to all game objects is size, location, color, and a handle to the model.
	public GameObject(int size, double x, double y, int color, GameWorld w) {
		myTranslate = Transform.makeIdentity();
		myRotate = Transform.makeIdentity();
		myScale = Transform.makeIdentity();
		this.size = size;
		// this.xPos = x;
		// this.yPos = y;
		myTranslate.translate((float)x, (float)y);
		this.color = color;
		this.world = w;
		this.collidingWith = new ArrayList<GameObject>();
	}

	public int getSize() {
		return size;
	}

	// New in A4 - use local transform to keep object's world location.
	public double getX() {
		return myTranslate.getTranslateX();
	}

	public void setXY(double x, double y) {
		myTranslate = Transform.makeIdentity();
		myTranslate.translate((float)x, (float)y);
	}

	public double getY() {
		return myTranslate.getTranslateY();
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

	// ICollider interface methods
	// Check for collision is done measuring distance
	// between object centers rather than their bounded
	// boxes intersection. I choose this method because
	// the Drones and Bases are triangles and visually
	// the bounded boxes based collision looks unconfirmed.
	@Override
	public boolean collidesWith(GameObject other) {
		double distX = getX() - other.getX();
		double distY = getY() - other.getY();
		double distC = Math.sqrt(distX * distX + distY * distY);
		
		int thisR= this.getSize() / 2;
		int otherR= other.getSize() / 2;
		// Compute (thisR + otherR - 1) to compare
		int minDist = thisR + otherR - 1;
		final boolean isCollision = distC < minDist;
		// If collision didn't happen and they were colliding
		// Remove each other from their collidingWith list.
		if (!isCollision) {
			if (collidingWith.contains(other)) {
				collidingWith.remove(other);
			}
			if (other.collidingWith.contains(this)) {
				other.collidingWith.remove(this);
			}			
		}
		return isCollision;
	}

	@Override
	public void handleCollision(GameObject otherObject) {
		if (!collidingWith.contains(otherObject)) {
			collidingWith.add(otherObject);
			// Make sure the other object doesn't handle this collision.
			if (!otherObject.collidingWith.contains(this)) {
				otherObject.collidingWith.add(this);
			}
		}
	}

	// IDrawable interface methods
	@Override
	public void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn) {
		// Override in derived classes to draw the object.
		System.out.println("Method must be implemented by derived classes.");
	}
	
	// This method is overridden by the base classes 
	// so they can add their specific attributes.
	public String toString() {
		String myDesc = "location=" + getX() + ", " + getY() + ", color(ARGB)=" + "[" + ColorUtil.alpha(color) + ","
				+ ColorUtil.red(color) + "," + ColorUtil.green(color) + "," + ColorUtil.blue(color) + "]" + ", size="
				+ size;
		;
		return myDesc;
	}

}
