package com.mycompany.a3;

public class NextBaseStrategy implements IStrategy {
	
	private NonPlayerRobot npr;
	
	NextBaseStrategy(NonPlayerRobot enemyRobot) {
		npr = enemyRobot;
	}
	
	// This is called by the invokeStrategy().
	// It decouples the NPR GameObjects from the set of strategies they can have.
	public void apply() {
		npr.steerTowardNextBase();
	}
	
}
