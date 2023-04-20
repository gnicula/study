package com.mycompany.a3;

import java.util.Random;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class Drone extends Movable {
	
	public Drone(int size, double x, double y, int color, int speed, int heading, GameWorld world) {
		super(size, x, y, color, speed, heading, world);
		Random rand = new Random();
		setHeading(rand.nextInt(360)); // initialize heading to a random value between 0 and 359 degrees, 0 is North.
		setSpeed(rand.nextInt(50) + 5); // initialize speed to a random value between 5 and 10
	}

	@Override
	public void move(int tickTime) {
		// add small random value (e.g., 5 degrees) to heading while moving
		Random rand = new Random();
		int headingChange = rand.nextInt(10) - 5;
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
		this.setX(newX);
		this.setY(newY);
		locationBoundAdjust();
	}

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

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		int x[] = new int[3];
		int y[] = new int[3];
		x[0] = pCmpRelPrnt.getX() + (int)getX();
		y[0] = pCmpRelPrnt.getY() + (int)getY() + getSize()/2;
		x[1] = pCmpRelPrnt.getX() + (int)getX() + getSize()/2;
		y[1] = pCmpRelPrnt.getY() + (int)getY() - getSize()/2;
		x[2] = pCmpRelPrnt.getX() + (int)getX() - getSize()/2;
		y[2] = pCmpRelPrnt.getY() + (int)getY() - getSize()/2;
		g.setColor(getColor());
		g.drawPolygon(x, y, 3);
	}

	@Override
	public String toString() {
		String parentDesc = super.toString();
		String myDesc = "Drone: " + parentDesc;
		return myDesc;
	}
}
