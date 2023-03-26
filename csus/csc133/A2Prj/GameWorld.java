package com.mycompany.a2;

import java.util.Observable;
import java.util.Random;

import com.codename1.charts.util.ColorUtil;

// GameWorld acts as the model and handles all the initializations
// necessary for the game. 
// It works without the GUI and dispatches information as necessary to game 
public class GameWorld extends Observable {

	private final int NPR_COLLISION_DAMAGE = 10;

	private int height = 0;
	private int width = 0;
	private int count = 0;
	// New in A2 a flag indicating whether sound is on or off.
	private boolean sound = false;
	private int numBases = 0;

	private GameObjectCollection goc = null;

	// Creates the initial game setup or when the
	// player loses a life. 
	public void init() {

		goc = new GameObjectCollection();

		goc.add(new Base(20, 50, 50, ColorUtil.BLACK, this, 1));
		goc.add(new Base(20, 250, 50, ColorUtil.BLACK, this, 2));
		goc.add(new Base(20, 450, 50, ColorUtil.BLACK, this, 3));
		goc.add(new Base(20, 50, 250, ColorUtil.BLACK, this, 4));
		goc.add(new Base(20, 50, 450, ColorUtil.BLACK, this, 5));
		goc.add(new Base(20, 800, 800, ColorUtil.BLACK, this, 6));

		goc.add(new EnergyStation(10, 200, 200, ColorUtil.GREEN, this));
		goc.add(new EnergyStation(10, 600, 300, ColorUtil.GREEN, this));

		goc.add(new Drone(10, 75, 75, ColorUtil.BLUE, 20, 0, this));
		goc.add(new Drone(10, 95, 95, ColorUtil.BLUE, 20, 0, this));

		// Get the first base to initialize Robot locations.
		IIterator it = goc.getIterator();
		GameObject first = it.getNext();

		// Initialize 3 enemy non player robots with different strategies.
		NonPlayerRobot npr = new NonPlayerRobot(10, first.getX() + 250, first.getY() + 25, ColorUtil.MAGENTA, 15, 25, 100, 1,
				this);
		npr.setStrategy(new AttackStrategy(npr));
		goc.add(npr);
		npr = new NonPlayerRobot(10, first.getX() + 35, first.getY() + 145, ColorUtil.MAGENTA, 20, 25, 100, 1, this);
		npr.setStrategy(new NextBaseStrategy(npr));
		goc.add(npr);
		npr = new NonPlayerRobot(10, first.getX() + 160, first.getY() + 140, ColorUtil.MAGENTA, 25, 25, 100, 1, this);
		npr.setStrategy(new NextBaseStrategy(npr));
		goc.add(npr);

		// Player Robot will always be the last element in the collection.
		goc.add(Robot.getInstance(20, first.getX(), first.getY(), ColorUtil.argb(255, 200, 100, 255), 5, 50, 50, 2, this));

		numBases = getNumBases();
	}

	// Iterates through all the GameObjects in the collection
	// and prints their string representation.
	public void mCommand() {

		IIterator it = goc.getIterator();
		while (it.hasNext()) {
			System.out.println(it.getNext());
		}
	}

	// A1, not used in A2 because the information is in the ScoreView.
	public void dCommand() {

		System.out.println(getPlayerRobot());
	}

	// Returns the number of clock ticks.
	public int getCount() {
		// System.out.println("getCount: " + count);
		return count;
	}

	public int getLivesLeft() {
		return getPlayerRobot().getLives();
	}

	public boolean getSoundSetting() {
		return sound;
	}

	public void setSoundSetting(boolean on) {
		sound = on;
	}
	
	// Set the dimensions of the map from MapView.
	// For some reason, these are larger than the actual size of the
	// MapView visible on the screen.
	// TODO Find a way to obtain the exact pixel values of the MapView container.
	public void setDimensions(int height, int width) {
		this.setHeight(height);
		this.setWidth(width);
	}

	public void accelerate() {
		getPlayerRobot().accelerate();
	}

	public void brake() {
		getPlayerRobot().brake();
	}

	// Each tick we update all movable objects, and notify 
	// the registered observers: MapView and ScoreView.
	public void tick() {
		count++;
		updateMoveableObjects();
		setChanged();
		notifyObservers(this);
	}

	// Iterate through GameObjectCollection, 
	// identify the nonplayer robots,
	// and flip their strategy.
	public void changeStrategy() {
		IIterator it = goc.getIterator();
		while (it.hasNext()) {
			GameObject go = it.getNext();
			if (go instanceof NonPlayerRobot) {
				((NonPlayerRobot) go).changeStrategy();
			}
		}
	}

	// Given a base seq number, ex. last base reached, find
	// the base object of the next in sequence base.
	public Base getNextBaseInSequence(int sequenceNumber) {
		sequenceNumber += 1;
		if (sequenceNumber > numBases) {
			sequenceNumber %= numBases;
		}

		IIterator it = goc.getIterator();
		while (it.hasNext()) {
			GameObject go = it.getNext();
			if (go instanceof Base) {
				int baseSequenceNum = ((Base) go).getSequenceNumber();
				if (sequenceNumber == baseSequenceNum) {
					return (Base) go;
				}
			}
		}
		return null;
	}

	public void exit() {
		System.exit(0);
	}
	
	public void reInitialize() {
		init();
		getPlayerRobot().reinitializeRobot();
	}
	
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	// The player Robot is the last item in the collection.
	public Robot getPlayerRobot() {

		IIterator it = goc.getIterator();
		if (!it.hasNext()) {
			return null;
		}

		GameObject go = it.getNext();

		while (it.hasNext()) {
			go = it.getNext();
		}

		return (Robot) go;
	}

	public void turnLeft() {
		Robot myRobot = getPlayerRobot();

		myRobot.steerLeft();
	}

	public void turnRight() {
		Robot myRobot = getPlayerRobot();

		myRobot.steerRight();
	}
	
	// The Drone object does not need any updates on collision.
	// The amount of damage taken by the player robot is half of the
	// damage dealt by a collision with an NPR.
	public void collideWithDrone() {
		Robot myRobot = getPlayerRobot();
		int currentDamage = myRobot.getDamageLevel();
		myRobot.setDamageLevel(currentDamage + NPR_COLLISION_DAMAGE/2);
	}

	// The Robot collides with the first NPR in the collection,
	// dealing damage to both Robots.
	public void collideWithNPR() {
		Robot myRobot = getPlayerRobot();
		int currentDamage = myRobot.getDamageLevel();
		myRobot.setDamageLevel(currentDamage + NPR_COLLISION_DAMAGE);

		IIterator it = goc.getIterator();
		while (it.hasNext()) {
			GameObject go = it.getNext();
			if (go instanceof NonPlayerRobot) {
				currentDamage = ((NonPlayerRobot) go).getDamageLevel();
				((NonPlayerRobot) go).setDamageLevel(currentDamage + NPR_COLLISION_DAMAGE);
				break;
			}
		}
	}

	// As per A1/A2 requirements, have the player input a base sequence number
	// check if it is one more than the last base reached,
	// if so, change last base reached.
	// If not, the last base reached remains the same.
	public void collideWithBase(int baseNum) {
		Robot myRobot = getPlayerRobot();
		int lastBaseReachedCheck = myRobot.getLastBaseReached();

		if ((lastBaseReachedCheck + 1) == baseNum) {
			myRobot.setLastBaseReached(baseNum);
		}
	}

	// Player robot simulates a collision with an Energy Station.
	// This drains all the energy from the station and gives it to the Robot.
	public void collideWithEnergyStation() {
		Robot myRobot = getPlayerRobot();
		int currentEnergy = myRobot.getEnergyLevel();
		int energyStationEnergy;

		IIterator it = goc.getIterator();
		while (it.hasNext()) {
			GameObject go = it.getNext();
			if (go instanceof EnergyStation) {
				EnergyStation station = (EnergyStation) go;
				energyStationEnergy = station.getCapacity();
				// If the energy station is already depleted, go to the next one.
				if (energyStationEnergy == 0) {
					continue;
				}
				myRobot.setEnergyLevel(currentEnergy + energyStationEnergy);
				station.setCapacity(0);
				fadeColor(station);
				addRandomEnergyStation();
				break;
			}
		}
	}
	
	public int getNumBases() {
		int countBases = 0;
		IIterator it = goc.getIterator();
		while (it.hasNext()) {
			GameObject go = it.getNext();
			if (go instanceof Base) {
				++countBases;
			}
		}

		return countBases;
	}

	public IIterator getGameObjectsIterator() {
		return goc.getIterator();
	}

	// Adds a new Energy Station in a random location with a random size around 10.
	private void addRandomEnergyStation() {
		Random rand = new Random();
		double randXloc = (double) rand.nextInt(400);
		double randYloc = (double) rand.nextInt(400);
		int randSize = rand.nextInt(10) + 5;
		int index = goc.size() - 1;
		if (index < 0) {
			index = 0;
		}
		goc.add(index, new EnergyStation(randSize, randXloc, randYloc, ColorUtil.GREEN, this));

	}

	// Halves the current alpha of an object to fade out the color.
	private void fadeColor(GameObject obj) {
		int currentColor = obj.getColor();
		int alpha = ColorUtil.alpha(currentColor) / 2;
		obj.setColor(ColorUtil.argb(alpha, ColorUtil.red(currentColor), ColorUtil.green(currentColor),
				ColorUtil.blue(currentColor)));
	}

	// Iterates through the collection and calls every movable object's move() method.
	private void updateMoveableObjects() {
		IIterator it = goc.getIterator();
		while (it.hasNext()) {
			GameObject go = it.getNext();
			if (go instanceof Movable) {
				((Movable)go).move();
			}
		}
	}
}