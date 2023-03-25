package com.mycompany.a2;

import java.util.Random;

public class Drone extends Movable {
	
	public Drone(int size, double x, double y, int color, int speed, int heading, GameWorld world) {
		super(size, x, y, color, speed, heading, world);
		Random rand = new Random();
		setHeading(rand.nextInt(360)); // initialize heading to a random value between 0 and 359 degrees, 0 is North.
		setSpeed(rand.nextInt(50) + 5); // initialize speed to a random value between 5 and 10
	}

	@Override
	public void move() {
		// add small random value (e.g., 5 degrees) to heading while moving
		Random rand = new Random();
		int headingChange = rand.nextInt(10) - 5;
		setHeading((getHeading() + headingChange + 360) % 360);

		// calculate the new position of the drone based on its speed and heading
		double dx = this.getSpeed() * Math.cos(Math.toRadians(90 - this.getHeading()));
		double dy = this.getSpeed() * Math.sin(Math.toRadians(90 - this.getHeading()));
		double newX = this.getX() + dx;
		double newY = this.getY() + dy;

		int currentHeading = getHeading();
		// check if the new position is within the world bounds
		System.out.println("new X: " + newX + " new Y: " + newY + " width: " + getWorld().getWidth() + " height: " + getWorld().getHeight());
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
		locationBoundAdjust();
		
		this.setX(newX);
		this.setY(newY);
	}
	
	@Override
	public String toString() {
		String parentDesc = super.toString();
		String myDesc = "Drone: " + parentDesc;
		return myDesc;
	}

		// check if the drone is directly over a robot and cause damage to the robot if
		// necessary
//		for (Robots robot : this.getWorld().getRobots()) {
//			if (robot.contains(this.getX(), this.getY())) {
//				double damage = robot.getCollisionDamage() / 2;
//				robot.takeDamage(damage);
//				break;
//			}
//		}
	//}
}
