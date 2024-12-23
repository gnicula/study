package com.mycompany.a4;

import java.util.Random;

import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Point;

public class Drone extends Movable {
	private Random droneRand;
	private TriangleShape myTriangle1, myTriangle2;
	
	public Drone(int size, double x, double y, int color, int speed, int heading, GameWorld world) {
		super(size, x, y, color, speed, heading, world);
		droneRand = new Random();
		myTriangle1 = new TriangleShape(size, size, color);
		myTriangle2 = new TriangleShape(size/2, size/2, color);
		myTriangle2.setFilled(true);
		myTriangle2.translate(0, size/3);
		myScale.scale(1, -1);
	}

	@Override
	public void move(int tickTime) {
		// add small random value (e.g., 5 degrees) to heading while moving
		int headingChange = droneRand.nextInt(10) - 5;
		setHeading((getHeading() + headingChange + 360) % 360);

		// calculate the new position of the drone based on its speed and heading
		double dx = this.getSpeed() * Math.cos(Math.toRadians(90 - this.getHeading()));
		double dy = this.getSpeed() * Math.sin(Math.toRadians(90 - this.getHeading()));
		double newX = this.getX() + dx * tickTime / 1000;
		double newY = this.getY() + dy * tickTime / 1000;

		int currentHeading = getHeading();
		// check if the new position is within the world bounds
		// System.out.println("Drone::move new X: " + newX + " new Y: " + newY + " width: " + getWorld().getWidth() + " height: " + getWorld().getHeight());
		// if the drone hits the left side of the world, change heading and do not move out of bounds
		if (newX < 0) {
			setHeading(360 - currentHeading);
		//if the drone hits the right side of the world, change heading and do not move out of bounds
		} else if (newX > this.getWorld().getWidth()) {
			setHeading(360 - currentHeading);
		//if the drone hits the top border of the world, change heading and do not move out of bounds.
		} else if (newY < 0) {
			setHeading(currentHeading < 90 ? 180 - currentHeading : (540 - currentHeading) % 360);
		//if the drone hits the bottom border of the world, change heading and do not move out of bounds.
		} else if (newY > this.getWorld().getHeight()){
			setHeading(currentHeading <= 180 ? 180 - currentHeading : 540 - currentHeading);
		}		
		this.setXY(newX, newY);
		locationBoundAdjust();
	}

	// Drone collision on bases has no effect.
	@Override
	public void handleCollision(GameObject otherObject) {
		if (!collidingWith.contains(otherObject)) {
			if (otherObject instanceof Fixed) {
				// Drone collides with Base or EnergyBase, do nothing?
			} else if (otherObject instanceof Drone) {
				// Drone collides with other Drone, do nothing?
			} else if (otherObject instanceof Robot) {
				getWorld().collideRobotWithDrone((Robot)otherObject, this);
			}
			collidingWith.add(otherObject);
			// Make sure the other object doesn't handle this collision.
			if (!otherObject.collidingWith.contains(this)) {
				otherObject.collidingWith.add(this);
			}
		}	
	}

	public void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn) {
		myRotate = Transform.makeIdentity();
		myRotate.rotate((float)Math.toRadians(getHeading()-180), 
			(float)pCmpRelScrn.getX() - pCmpRelPrnt.getX(), 
			(float)pCmpRelScrn.getY() - pCmpRelPrnt.getY());
		Transform gXform = Transform.makeIdentity();
		g.getTransform(gXform);
		Transform gOrigXform = gXform.copy(); //save the original xform
		gXform.translate((float)getX(), (float)getY());
		gXform.scale(myScale.getScaleX(), myScale.getScaleY());
		gXform.concatenate(myRotate);
		g.setTransform(gXform);
		myTriangle1.draw(g);
		myTriangle2.draw(g);
		g.setTransform(gOrigXform); //restore the original xform (remove LTs)
		//do not use resetAffine() in draw()! Instead use getTransform()/setTransform(gOrigForm)
	}

	@Override
	public String toString() {
		String parentDesc = super.toString();
		String myDesc = "Drone: " + parentDesc;
		return myDesc;
	}
}
