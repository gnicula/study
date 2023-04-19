package com.mycompany.a3;

import java.util.Observable;
import java.util.Random;

import com.codename1.charts.util.ColorUtil;

// GameWorld acts as the model and handles all the initializations
// necessary for the game. 
// Dispatches information as necessary to game objects and views. 
public class GameWorld extends Observable {

	// Light Blue
	private final int LIGHTBLUE = ColorUtil.argb(255, 153, 204, 255);
	// Faded Green
	private final int FADEDGREEN = ColorUtil.argb(255, 0, 155, 0);
	// Energy Station Green
	private final int ENERGYGREEN = ColorUtil.argb(255, 0, 122, 0);
	
	private final int NPR_COLLISION_DAMAGE = 10;
	// Delta in pixels from selection pointer to center of object
	// to be considered selected.
	private final int SELECT_DELTA = 15;

	// Sounds
	private BGSound bgs;
	private Sound energy1;
	private Sound energy2;
	private Sound collision1;
	private Sound collision2;
	private Sound destroyed;
	
	private int height = 0;
	private int width = 0;
	private int count = 0;
	// Flag indicating whether sound is on or off.
	private boolean sound = false;
	private boolean paused = false;
	private int numBases = 0;

	private GameObjectCollection goc = null;
	private Fixed currentSelectedObject = null;
	private boolean userEdit = false;

	// Creates the initial game setup or when the
	// player loses a life. 
	public void init() {

		goc = new GameObjectCollection();
		// TODO: add these in set dimensions based on w, h
		goc.add(new Base(60, 100, 60, LIGHTBLUE, this, 1));
		goc.add(new Base(60, 250, 80, LIGHTBLUE, this, 2));
		goc.add(new Base(60, 150, 450, LIGHTBLUE, this, 3));
		goc.add(new Base(60, 550, 650, LIGHTBLUE, this, 4));
		// goc.add(new Base(60, 50, 450, LIGHTBLUE, this, 5));
		// goc.add(new Base(60, 800, 800, LIGHTBLUE, this, 6));

		goc.add(new EnergyStation(50, 200, 200, ENERGYGREEN, this));
		goc.add(new EnergyStation(80, 600, 300, ENERGYGREEN, this));
		goc.add(new EnergyStation(40, 700, 400, ENERGYGREEN, this));
		goc.add(new EnergyStation(70, 300, 700, ENERGYGREEN, this));

		goc.add(new Drone(40, 375, 375, ColorUtil.BLACK, 20, 0, this));
		goc.add(new Drone(40, 495, 95, ColorUtil.BLACK, 20, 0, this));

		// Get the first base to initialize Robot locations.
		IIterator it = goc.getIterator();
		GameObject first = it.getNext();

		// Initialize 3 enemy non player robots with different strategies.
		NonPlayerRobot npr = new NonPlayerRobot(50, first.getX() + 250, first.getY() + 25, ColorUtil.MAGENTA, 15, 25, 100, 1,
				this);
		npr.setStrategy(new AttackStrategy(npr));
		goc.add(npr);
		npr = new NonPlayerRobot(50, first.getX() + 100, first.getY() + 150, ColorUtil.MAGENTA, 20, 25, 100, 1, this);
		npr.setStrategy(new NextBaseStrategy(npr));
		goc.add(npr);
		npr = new NonPlayerRobot(50, first.getX() + 200, first.getY() + 140, ColorUtil.MAGENTA, 25, 25, 100, 1, this);
		npr.setStrategy(new NextBaseStrategy(npr));
		goc.add(npr);

		// Player Robot will always be the last element in the collection.
		goc.add(Robot.getInstance(60, first.getX(), first.getY(), ColorUtil.argb(255, 255, 1, 1), 5, 50, 50, 2, this));

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
		if (!paused) {
			if (sound) {
				bgs.play();
			} else {
				bgs.pause();
			}
		}
	}
	
	// Set the dimensions of the map from MapView.
	public void setDimensions(int height, int width) {
		this.setHeight(height);
		this.setWidth(width);
	}

	public void accelerate() {
		if (!paused) {
			getPlayerRobot().accelerate();
		}
	}

	public void brake() {
		if (!paused) {
			getPlayerRobot().brake();
		}
	}

	// Each tick we update all movable objects, and notify 
	// the registered observers: MapView and ScoreView.
	public void tick() {
		// If the game is 'Paused' nothing is moving.
		// Counter is also stopped.
		if (!paused) {
			count++;
			updateMoveableObjects();
		}
		// Views may update ex. for displaying selections
		// even if the game is paused.
		setChanged();
		notifyObservers(this);
	}

	// Iterate through GameObjectCollection, 
	// identify the nonplayer robots,
	// and flip their strategy.
	public void changeStrategy() {
		if (!paused) {
			IIterator it = goc.getIterator();
			while (it.hasNext()) {
				GameObject go = it.getNext();
				if (go instanceof NonPlayerRobot) {
					((NonPlayerRobot) go).changeStrategy();
				}
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
		if (!paused) {
			Robot myRobot = getPlayerRobot();
			myRobot.steerLeft();
		}
	}

	public void turnRight() {
		if (!paused) {
			Robot myRobot = getPlayerRobot();
			myRobot.steerRight();
		}
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
	
	void pause() {
		paused = !paused;
		if (!paused) {
			if (currentSelectedObject != null) {
				currentSelectedObject.setSelected(false);
				currentSelectedObject = null;
			}
			if (sound) {
				bgs.play();
			}
		} else {
			if (sound) {
				bgs.pause();
			}
		}
	}
	
	public boolean getPaused() {
		return paused;
	}

	public void enablePosition(boolean b) {
		userEdit = b;
	}
	
	public boolean isUserEdit() {
		return userEdit;
	}

	public Fixed getSelected() {
		return currentSelectedObject;
	}

	public void selectObjectAt(int xPos, int yPos) {
		// System.out.println("selectObjectAt: " + xPos + ", " + yPos);
		boolean foundObjectToSelect = false;
		IIterator it = goc.getIterator();
		while (it.hasNext()) {
			GameObject go = it.getNext();
			if (go instanceof Fixed) {
				int objX = (int) go.getX();
				int objY = (int) go.getY();
				// System.out.println("selectObjectAt with object: " + objX + ", " + objY);
				if (Math.abs(objX - xPos) < SELECT_DELTA && Math.abs(objY - yPos) < SELECT_DELTA) {
					if (currentSelectedObject != null) {
						currentSelectedObject.setSelected(false);
					}
					currentSelectedObject = (Fixed)go;
					selectObject((Fixed)go);
					foundObjectToSelect = true;
					break;
				}
			}
		}
		if (!foundObjectToSelect) {
			unselectAllObjects();
		}
	}
	
	public void selectObject(Fixed obj) {
		obj.setSelected(true);
	}
	
	public void unselectAllObjects() {
		if (currentSelectedObject != null) {
			currentSelectedObject.setSelected(false);
		}
		currentSelectedObject = null;
	}
	
	public void moveSelectedObjectTo(int xPos, int yPos) {
		// Check if there is a selected object and move it to
		// its new location.
		if (currentSelectedObject != null) {
			currentSelectedObject.setX(xPos);
			currentSelectedObject.setY(yPos);
			// unselect moved item
			currentSelectedObject.setSelected(false);
			currentSelectedObject = null;
			// clear userEdit flag
			userEdit = false;
		}
	}
	
	public void createSounds() {
		bgs = new BGSound("back_lady80s_16khz.wav");
		energy1 = new Sound("energy1.wav");
		energy2 = new Sound("energy2.wav");
		collision1 = new Sound("collision1.wav");
		collision2 = new Sound("collision2.wav");
		destroyed = new Sound("destroyed.wav");
	}

	// Adds a new Energy Station in a random location with a random size around 10.
	private void addRandomEnergyStation() {
		Random rand = new Random();
		double randXloc = (double) rand.nextInt(600);
		double randYloc = (double) rand.nextInt(600);
		int randSize = rand.nextInt(40) + 30;
		int index = goc.size() - 1;
		if (index < 0) {
			index = 0;
		}
		goc.add(index, new EnergyStation(randSize, randXloc, randYloc, ENERGYGREEN, this));

	}

	// Halves the current alpha of an object to fade out the color.
	// Alternatively set a lighter version of the color.
	private void fadeColor(GameObject obj) {
//		int currentColor = obj.getColor();
//		int alpha = ColorUtil.alpha(currentColor) / 3;
//		obj.setColor(ColorUtil.argb(alpha, ColorUtil.red(currentColor), ColorUtil.green(currentColor),
//				ColorUtil.blue(currentColor)));
		obj.setColor(FADEDGREEN);
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