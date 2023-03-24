package com.mycompany.a2;

public class Robot extends Movable implements ISteerable {

	private final int SPEED_INCREASE = 5;
	private final int SPEED_DECREASE = 5;
	private final int MAX_LEFT_DIRECTION = -40;
	private final int MAX_RIGHT_DIRECTION = 40;
	private final int MAX_POSSIBLE_DAMAGE = 100;

	// steeringDirection is the adjustment angle in degrees in range -40 to 40.
	private int steeringDirection;
	private int maximumSpeed;
	private int energyLevel;
	private int energyConsumptionRate;
	// damage level is an int between 0-100.
	private int damageLevel;
	private int lastBaseReached;
	private int lives;
	
	// Constructor
	public Robot(int size, double x, double y, int color, int maximumSpeed, int energyLevel, int energyConsumptionRate,
			GameWorld world) {
		super(size, x, y, color, energyConsumptionRate, energyConsumptionRate, world);
		this.steeringDirection = 0;
		this.maximumSpeed = maximumSpeed;
		this.energyLevel = energyLevel;
		this.energyConsumptionRate = energyConsumptionRate;
		this.damageLevel = 0;
		this.lastBaseReached = 1;
		this.setSpeed(0);
		this.setLives(3);
	}

	public void accelerate() {
		int speed = getSpeed();
		speed += SPEED_INCREASE;

		adjustSpeed(speed);
	}
	
	public void brake() {
		int speed = getSpeed();
		speed -= SPEED_DECREASE;
		
		speed = Math.max(0, speed);
		setSpeed(speed);
		System.out.println(speed);

	}
	
	public int getLives() {
		return lives;
	}

	// private void setSpeed(int s) {
	// speed = s;
	//
	// }

	// Methods for ISteerable interface
	public void steerLeft() {
		if (this.steeringDirection > -40) {
			this.steeringDirection -= 5;
			this.steeringDirection = Math.max(MAX_LEFT_DIRECTION, steeringDirection);
		}

	}

	public void steerRight() {
		if (this.steeringDirection < 40) {
			this.steeringDirection += 5;
			this.steeringDirection = Math.min(MAX_RIGHT_DIRECTION, steeringDirection);

		}
	}

	// Getter and setter methods
	public int getSteeringDirection() {
		return steeringDirection;
	}

	public void setSteeringDirection(int steeringDirection) {
		
		steeringDirection = Math.min(MAX_RIGHT_DIRECTION, Math.max(MAX_LEFT_DIRECTION, steeringDirection));

		this.steeringDirection = steeringDirection;
	}

	public int getMaximumSpeed() {
		return maximumSpeed;
	}

	public void setMaximumSpeed(int maximumSpeed) {
		this.maximumSpeed = maximumSpeed;
	}

	public int getEnergyLevel() {
		return energyLevel;
	}

	public void setEnergyLevel(int energyLevel) {
		this.energyLevel = energyLevel;
	}

	public int getEnergyConsumptionRate() {
		return energyConsumptionRate;
	}

	public void setEnergyConsumptionRate(int energyConsumptionRate) {
		this.energyConsumptionRate = energyConsumptionRate;
	}

	public int getDamageLevel() {
		return damageLevel;
	}

	public void setDamageLevel(int damageLevel) {
		this.damageLevel = damageLevel;
		adjustSpeed(getSpeed());
		
		if (damageLevel >= MAX_POSSIBLE_DAMAGE) {
			if (lives > 0) {
				--lives;
			} else {
				System.out.println("Out of lives \n GAME OVER");
			}
		}
	}

	public int getLastBaseReached() {
		return lastBaseReached;
	}

	public void setLastBaseReached(int lastBaseReached) {
		this.lastBaseReached = lastBaseReached;
	}

	public String toString() {
		String parentDesc = super.toString();
		String myDesc = "Robot: " + parentDesc + ", Max Speed=" + maximumSpeed + ", steeringDirection="
				+ steeringDirection + ", energyLevel=" + energyLevel + ", damageLevel=" + damageLevel
				+ ", Last base reached=" + lastBaseReached + ", clock=" + getWorld().getCount() + ", lives=" + lives;
		return myDesc;
	}

	private void setLives(int lives) {
		this.lives = lives;
	}

	private void adjustSpeed(int speed) {

		speed = Math.min(maximumSpeed, speed);

		speed *= (100 - damageLevel) / 100;

		if (energyLevel == 0) {
			speed = 0;
		}

		setSpeed(speed);

	}

}

// Override the move method from MoveableGameObject
//    @Override
//    public void move(int elapsedTicks) {
//        // Calculate the new speed and heading based on elapsedTicks and steeringDirection
//        int newSpeed = Math.min(this.getSpeed() + elapsedTicks * (this.maximumSpeed - this.energyConsumptionRate * this.damageLevel) / 10, this.maximumSpeed);
//        int newHeading = (this.getHeading() + elapsedTicks * (this.steeringDirection / 5)) % 360;
//        // Update the speed and heading of the robot
//        this.setSpeed(newSpeed);
//        this.setHeading(newHeading);
//        // Update the location of the robot based on speed and heading
//        this.setX(this.getX() + elapsedTicks * this.getSpeed() * Math.cos(Math.toRadians(this.getHeading())));
//        this.setY(this.getY() + elapsedTicks * this.getSpeed() * Math.sin(Math.toRadians(this.getHeading())));
//        // Reduce the energy level based on energyConsumptionRate and current speed
//        this.setEnergyLevel(this.energyLevel - elapsedTicks * this.energyConsumptionRate * this.getSpeed());
//        // Check if the robot has reached a new base
//        int currentBase = GameWorld.getInstance().getCurrentBase(this);
//        if (currentBase > this.lastBaseReached)
