package com.mycompany.a2;

import java.util.Random;

public class Drone extends Movable {
	
	public Drone(int size, double x, double y, int color, int speed, int heading, GameWorld world) {
		super(size, x, y, color, speed, heading, world);
		Random rand = new Random();
		setHeading(rand.nextInt(360)); // initialize heading to a random value between 0 and 359 degrees, 0 is North.
		setSpeed(rand.nextInt(5) + 5); // initialize speed to a random value between 5 and 10
	}

	@Override
	public void move() {
		// add small random value (e.g., 5 degrees) to heading while moving
		Random rand = new Random();
		int headingChange = rand.nextInt(10) - 5;
		setHeading(getHeading() + headingChange);

		// calculate the new position of the drone based on its speed and heading
		double dx = this.getSpeed() * Math.cos(Math.toRadians(this.getHeading()));
		double dy = this.getSpeed() * Math.sin(Math.toRadians(this.getHeading()));
		double newX = this.getX() + dx;
		double newY = this.getY() + dy;

		// check if the new position is within the world bounds
		if (newX < 0) {
			// if the drone hits a side of the world, change heading and do not move out of
			// bounds
			setHeading((getHeading() - 180) % 360);
			newX = 0;			
		} else if (newX > this.getWorld().getWidth()) {
			
		} else if (newY < 0 || newY > this.getWorld().getHeight()) {
			// if the drone hits a top or bottom of the world, change heading and do not
			// move out of bounds
			setHeading((int) ((180 - getHeading()) % 360));
			dx = this.getSpeed() * Math.cos(Math.toRadians(this.getHeading()));
			dy = this.getSpeed() * Math.sin(Math.toRadians(this.getHeading()));
		} else {
			// move the drone to the new position
			this.setX(newX);
			this.setY(newY);
		}
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
