package com.mycompany.a2;

public class NextBaseStrategy implements IStrategy {
	
	private NonPlayerRobot npr;
	
	NextBaseStrategy(NonPlayerRobot enemyRobot) {
		npr = enemyRobot;
	}
	
	public void apply() {
		npr.moveTowardNextBase();
	}
	
}
