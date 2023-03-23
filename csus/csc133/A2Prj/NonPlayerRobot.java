package com.mycompany.a2;

import com.codename1.util.MathUtil;
import java.lang.Math;

public class NonPlayerRobot extends Robot {
	private IStrategy strategy;
	
	public NonPlayerRobot(int size, double x, double y, int color, int maximumSpeed, int energyLevel,
			int energyConsumptionRate, GameWorld world) {
		super(size, x, y, color, maximumSpeed, energyLevel, energyConsumptionRate, world);
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
		
		double xBasePos = nextBase.getX();
		double yBasePos = nextBase.getY();
		
		double a = xBasePos - this.getX();
		double b = yBasePos - this.getY();
		
		double angleBRad = MathUtil.atan(a/b);
		double angleBDegree = Math.toDegrees(angleBRad);
		
		setSteeringDirection((int)angleBDegree);
	}

	public void moveTowardPlayerRobot() {
		GameObject targetRobot = getWorld().getPlayerRobot();
		
		double xTargetPos = targetRobot.getX();
		double yTargetPos = targetRobot.getY();
		
		double a = xTargetPos - this.getX();
		double b = yTargetPos - this.getY();
		
		double angleBRad = MathUtil.atan(a/b);
		double angleBDegree = Math.toDegrees(angleBRad);
		
		setSteeringDirection((int)angleBDegree);
	}
	
	public String toString() {
		String parentDesc = super.toString();
		String myDesc = "Non-Player  " + parentDesc + ", Strategy=" + strategy.toString();
		return myDesc;
	}
}
