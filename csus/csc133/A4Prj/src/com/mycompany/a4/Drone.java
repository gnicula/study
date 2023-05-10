package com.mycompany.a4;

import java.util.Random;

import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Point;

public class Drone extends Movable {
	private Random droneRand;
	private TriangleShape myTriangle;
	
	public Drone(int size, double x, double y, int color, int speed, int heading, GameWorld world) {
		super(size, x, y, color, speed, heading, world);
		droneRand = new Random();
		myTriangle = new TriangleShape(size, size, color);
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
		this.setX(newX);
		this.setY(newY);
		// myRotate.rotate((float)Math.toRadians(getHeading()), (float)newX, (float)newY);
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

	/*
	// Draws itself as a triangle
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
		myTriangle.draw(g);
	}
	*/

	public void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn) {
		// Do not forget to do “local origin” transformations.
		// ORDER of LTs: Scaling LT will be applied to coordinates FIRST,
		// then Translation LT, and lastly Rotation LT.
		// Also restore the xform at the end of draw() to remove this sub-shape’s LTs
		// from xform of the Graphics object. Otherwise, we would also
		// apply these LTs to the next sub-shape since it also uses the same Graphics object.
		Transform gXform = Transform.makeIdentity();
		g.getTransform(gXform);
		Transform gOrigXform = gXform.copy(); //save the original xform
		// gXform.translate(pCmpRelScrn.getX(),pCmpRelScrn.getY());
		gXform.concatenate(myRotate); //Rotation is LAST
		// gXform.translate(myTranslate.getTranslateX(), myTranslate.getTranslateY());
		gXform.translate((float)getX(), (float)getY());
		gXform.scale(myScale.getScaleX(), myScale.getScaleY());
		// gXform.translate(-pCmpRelScrn.getX(),-pCmpRelScrn.getY());
		g.setTransform(gXform);
		//draw the lines as before
		myTriangle.draw(g);
		// g.drawLine(pCmpRelPrnt.getX()+top.getX(), pCmpRelPrnt.getY()+top.getY(),
		// pCmpRelPrnt.getX() + bottomLeft.getX(),pCmpRelPrnt.getY() + bottomLeft.getY());
		//...[draw the rest of the lines]
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
