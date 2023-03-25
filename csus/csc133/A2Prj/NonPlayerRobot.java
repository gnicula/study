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
	
	public void invokeStrategy() {
		strategy.apply();
	}
	
	public void changeStrategy() {
		if (strategy instanceof AttackStrategy) {
			setStrategy(new NextBaseStrategy(this));
		} else {
			setStrategy(new AttackStrategy(this));
		}
	}

	public void moveTowardNextBase() {
		Base nextBase = getWorld().getNextBaseInSequence(getLastBaseReached());
		System.out.println("Move toward next base " + nextBase);
		double xBasePos = nextBase.getX();
		double yBasePos = nextBase.getY();
		
		double a = xBasePos - this.getX();
		double b = yBasePos - this.getY();
		
		double angleBRad = MathUtil.atan(a/b);
		double angleBDegree = Math.toDegrees(angleBRad);
		
		setSteeringDirection((int)angleBDegree);
	}

	public void moveTowardPlayerRobot() {
//		GameObject targetRobot = getWorld().getPlayerRobot();
//		System.out.println("Move toward player robot " + targetRobot);
//		double xTargetPos = targetRobot.getX();
//		double yTargetPos = targetRobot.getY();
		double xTargetPos = 1000;
		double yTargetPos = 500;
		
		double a = xTargetPos - this.getX();
		double b = yTargetPos - this.getY();
		
		double angleBRad = MathUtil.atan(a/b);
		double angleBDegree = 180 - Math.toDegrees(angleBRad);
		
		System.out.println("NonPlayerRobot::setsteeringdir() " + angleBDegree + "\n");
		setSteeringDirection((int)angleBDegree);
	}
	
	@Override
	public void setDamageLevel(int damageLevel) {
		this.damageLevel = damageLevel;
	}
	
	@Override
	public void move() {
		System.out.println("NonPlayerRobot::move()\n");
		invokeStrategy();
		if (damageLevel < NPR_MAX_POSSIBLE_DAMAGE) {
			setEnergyLevel(Math.max(10, getEnergyLevel() - getEnergyConsumptionRate()));
	        double theta = Math.toRadians(90 - getHeading());
	        double deltaX = Math.cos(theta) * getSpeed();
	        double deltaY = Math.sin(theta) * getSpeed();
	        setX(getX() + deltaX);
	        setY(getY() + deltaY);
	        locationBoundAdjust();

			// Calculate the heading based on steeringDirection
			int newHeading = (getHeading() + getSteeringDirection() + 360) % 360;
			// Update the heading of the robot
			setHeading(newHeading);
		}
	}
	
	public String toString() {
		String parentDesc = super.toString();
		String myDesc = "Non-Player  " + parentDesc + ", Strategy=" + strategy.toString();
		return myDesc;
	}
}
