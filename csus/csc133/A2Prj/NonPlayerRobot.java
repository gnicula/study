package com.mycompany.a2;

import com.codename1.util.MathUtil;
import java.lang.Math;

public class NonPlayerRobot extends Robot {
	
	private final int NPR_MAX_POSSIBLE_DAMAGE = 1000;
	
	private IStrategy strategy;
	
	public NonPlayerRobot(int size, double x, double y, int color, int speed, int maximumSpeed, int energyLevel,
			int energyConsumptionRate, GameWorld world) {
		super(size, x, y, color, speed, maximumSpeed, energyLevel, energyConsumptionRate, world);
	}
	
	public void setStrategy(IStrategy strat) {
		strategy = strat;
	}
	
	// Applies the currently selected strategy to the NPR.
	public void invokeStrategy() {
		strategy.apply();
	}
	
	// Changes between the two strategies currently implemented.
	public void changeStrategy() {
		if (strategy instanceof AttackStrategy) {
			setStrategy(new NextBaseStrategy(this));
		} else {
			setStrategy(new AttackStrategy(this));
		}
	}

	// This strategy will look at the Player Robot's lastBaseReached + 1 and goes to guard that base.
	public void steerTowardNextBase() {
		Base nextBase = getWorld().getNextBaseInSequence(getWorld().getPlayerRobot().getLastBaseReached());
		// System.out.println("Move toward next base " + nextBase);
		double xBasePos = nextBase.getX();
		double yBasePos = nextBase.getY();
		
		int newSteeringAngle = computeIdealSteeringAngle(xBasePos, yBasePos);
		// System.out.println("NonPlayerRobot::steerTowardNextBase() " + newSteeringAngle + "\n");
		setSteeringDirection(newSteeringAngle);
	}

	// This strategy will tell the NPR to go directly towards the Player Robot.
	public void steerTowardPlayerRobot() {
		GameObject targetRobot = getWorld().getPlayerRobot();
		// System.out.println("Move toward player robot " + targetRobot);
		double xTargetPos = targetRobot.getX();
		double yTargetPos = targetRobot.getY();
		int newSteeringAngle = computeIdealSteeringAngle(xTargetPos, yTargetPos);
		// System.out.println("NonPlayerRobot::steerTowardPlayerRobot() " + newSteeringAngle + "\n");
		setSteeringDirection(newSteeringAngle);
	}
	
	@Override
	public void setDamageLevel(int damageLevel) {
		this.damageLevel = damageLevel;
	}
	
	// Overrides the move() from Player Robot because NPRs do not 
	// run out of energy.
	// They also use a strategy to automatically adjust their steering.
	@Override
	public void move() {
		// System.out.println("NonPlayerRobot::move()\n");
		
		// The strategy will adjust the steering direction and set it. 
		invokeStrategy();
		if (damageLevel < NPR_MAX_POSSIBLE_DAMAGE) {
			// Calculate the heading based on steeringDirection
			int newHeading = (getHeading() + getSteeringDirection() + 360) % 360;
			// Update the heading of the robot
			setHeading(newHeading);

			setEnergyLevel(Math.max(10, getEnergyLevel() - getEnergyConsumptionRate()));
	        double theta = Math.toRadians(90 - getHeading());
	        double deltaX = Math.cos(theta) * getSpeed();
	        double deltaY = Math.sin(theta) * getSpeed();
	        setX(getX() + deltaX);
	        setY(getY() + deltaY);
	        locationBoundAdjust();

		}
	}
	
	public String toString() {
		String parentDesc = super.toString();
		String myDesc = "Non-Player  " + parentDesc + ", Strategy=" + strategy.toString();
		return myDesc;
	}
	
	// Computes the normalized difference between the ideal heading and the current heading.
	// Then translates this difference into steering angles.
	private int computeIdealSteeringAngle(double xTargetPos, double yTargetPos) {
		double a = this.getX() - xTargetPos;
		double b = this.getY() - yTargetPos;
		
		// First get the beta angle
		double angleBDegree = Math.toDegrees(MathUtil.atan2(b, a));
		// System.out.println("atan: " + angleBDegree);
		// Find ideal heading (90 - beta)
		int idealHeading = (int)(90.0 - angleBDegree);
		// Compute and adjust steering
		// IMPORTANT Do not pass values that have absolute value > 180
		int steerAdjust = getHeading() - idealHeading;
		if (steerAdjust > 180) {
			steerAdjust %= 360;
			steerAdjust -= 360;
		} else if (steerAdjust < -180) {
			steerAdjust %= 360;
			steerAdjust += 360;
		}
		return steerAdjust;
	}
}
