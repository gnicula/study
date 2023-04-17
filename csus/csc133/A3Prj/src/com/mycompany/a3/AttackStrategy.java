package com.mycompany.a3;

public class AttackStrategy implements IStrategy {

	private NonPlayerRobot npr;
	
	AttackStrategy(NonPlayerRobot enemyRobot) {
		npr = enemyRobot;
	}
	
	// This strategy steers the non player robot towards 
	// the player robot.
	// The strategy is set to the NPR by the model.
	public void apply() {
		npr.steerTowardPlayerRobot();
	}
}
