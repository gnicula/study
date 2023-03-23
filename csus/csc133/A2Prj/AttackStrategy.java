package com.mycompany.a2;

public class AttackStrategy implements IStrategy {

	private NonPlayerRobot npr;
	
	AttackStrategy(NonPlayerRobot enemyRobot) {
		npr = enemyRobot;
	}
	
	public void apply() {
		npr.moveTowardPlayerRobot();
	}
}
