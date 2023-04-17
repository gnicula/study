package com.mycompany.a3;

// This is the player controlled Robot class.
// It is moveable and steerable, and it is a singleton
// which means there is going to be only one Robot object
// throughout the program. 
public class Robot extends Movable implements ISteerable {

	// Instance to keep the singleton Robot object.
	private static Robot robotInstance = null;
	
	private final int SPEED_INCREASE = 5;
	private final int SPEED_DECREASE = 5;
	private final int MAX_STEERING_DIRECTION = 40;
	private final int MAX_POSSIBLE_DAMAGE = 100;

	// steeringDirection is the adjustment angle in degrees in range -40 to 40.
	private int steeringDirection;
	private int maximumSpeed;
	private int energyLevel;
	private int energyConsumptionRate;
	// damage level is an int between 0-100.
	protected int damageLevel;
	private int lastBaseReached;
	private int lives;
	// Use the following attributes to memorize initial position and energy value.
	// These are used for reinitializing after losing a life.
	private double initialXLocation;
	private double initialYLocation;
	private int initialEnergyValue;
	
	// Constructor
	protected Robot(int size, double x, double y, int color, int speed, int maximumSpeed, int energyLevel, int energyConsumptionRate,
			GameWorld world) {
		super(size, x, y, color, speed, energyConsumptionRate, world);
		this.maximumSpeed = maximumSpeed;
		this.energyLevel = energyLevel;
		this.energyConsumptionRate = energyConsumptionRate;
		this.steeringDirection = 0;
		this.damageLevel = 0;
		this.lastBaseReached = 1;	
		this.lives = 3;
		// For reinitializing with the same energy and position.
		this.initialEnergyValue = energyLevel;
		this.initialXLocation = x;
		this.initialYLocation = y;
	}
	
	// getInstance is the only way to create a Robot object and it will create it only once.
	synchronized public static Robot getInstance(int size, double x, double y, int color, int speed, int maximumSpeed, int energyLevel, int energyConsumptionRate,
			GameWorld world) {
		if (robotInstance == null) {
			robotInstance = new Robot(size, x, y, color, speed, maximumSpeed, energyLevel, energyConsumptionRate, world);
		}
		return robotInstance;
	}
	
	// Robot gets reset to the first base and loses a life upon "dying".
	public void reinitializeRobot() {
		this.steeringDirection = 0;
		this.damageLevel = 0;
		this.lastBaseReached = 1;
		this.setSpeed(0);
		this.setEnergyLevel(initialEnergyValue);
		this.setX(initialXLocation);
		this.setY(initialYLocation);
	}

	// Increases speed based on constant SPEED_INCREASE
	public void accelerate() {
		int speed = getSpeed();
		speed += SPEED_INCREASE;

		adjustSpeed(speed);
	}
	
	// Decreases speed based on constant SPEED_DECREASE
	public void brake() {
		int speed = getSpeed();
		speed -= SPEED_DECREASE;		
		speed = Math.max(0, speed);
		
		setSpeed(speed);
	}
	
	public int getLives() {
		return lives;
	}

	// Methods for ISteerable interface
	public void steerLeft() {
		this.steeringDirection -= 5;
		clampSteering();
	}

	public void steerRight() {
		this.steeringDirection += 5;
		clampSteering();
	}

	// Getter and setter methods
	public int getSteeringDirection() {
		return steeringDirection;
	}

	public void setSteeringDirection(int steeringDirection) {
		this.steeringDirection = steeringDirection;
		clampSteering();
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
				System.out.println("lives left = " + lives);
				getWorld().reInitialize();
			} else {
				System.out.println("Game over, you failed!");
				getWorld().exit();
			}
		}
	}

	public int getLastBaseReached() {
		return lastBaseReached;
	}

	public void setLastBaseReached(int lastBaseReached) {
		this.lastBaseReached = lastBaseReached;
		
		if (lastBaseReached == getWorld().getNumBases()) { 
			System.out.println("Game over, you win! Total time: #" + getWorld().getCount());
			getWorld().exit();
		}
	}

	@Override
	public String toString() {
		String parentDesc = super.toString();
		String myDesc = "Robot: " + parentDesc + ", Max Speed=" + maximumSpeed + ", steeringDirection="
				+ steeringDirection + ", energyLevel=" + energyLevel + ", damageLevel=" + damageLevel
				+ ", Last base reached=" + lastBaseReached + ", lives=" + lives;
		return myDesc;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	// Adjusts speed based on damage taken.
	private void adjustSpeed(int speed) {

		speed = Math.min(maximumSpeed, speed);
		speed = (int)((float)speed * ((100.0 - (float)damageLevel) / 100.0));

		if (energyLevel == 0) {
			speed = 0;
		}
		// System.out.println("Speed after adjustment: " + speed);
		setSpeed(speed);

	}


	@Override
	public void move() {
		// System.out.println("Robot::move()\n");
		// Reduce the energy level based on energyConsumptionRate
		setEnergyLevel(Math.max(0, energyLevel - energyConsumptionRate));
		if (energyLevel > 0) {
			// Calculate the heading based on steeringDirection
			int newHeading = (getHeading() + steeringDirection + 360) % 360;
			// Update the heading of the robot
			setHeading(newHeading);			
			super.move();
		} else {
			System.out.println("Out of Energy, you lose a life.");
			--lives;
			getWorld().reInitialize();
		}
		
		// Check if the robot has reached a new base
//        int currentBase = GameWorld.getInstance().getCurrentBase(this);
//        if (currentBase > this.lastBaseReached)
	}
	
	private void clampSteering() {
		this.steeringDirection = Math.min(MAX_STEERING_DIRECTION, Math.max(this.steeringDirection, -MAX_STEERING_DIRECTION));
	}
}

