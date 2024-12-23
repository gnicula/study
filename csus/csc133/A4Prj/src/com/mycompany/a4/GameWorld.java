package com.mycompany.a4;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

import com.codename1.charts.models.Point;
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
	private final int SELECT_DELTA = 25;
	// Offset of initial objects positions with margins
	private final int MARGIN_PLACE = 120;

	// Player Robot initial values
	private final int INITIAL_ENERGY = 600;
	private final int INITIAL_NUM_LIVES = 3;

	// Sounds
	private BGSound bgs;
	private Sound energy1;
	private Sound energy2;
	private Sound base_collision;
	private Sound drone_collision;
	private Sound npr_collision;
	private Sound destroyed;
	private Sound game_win;
	private Sound game_lost;

	private int height = 639;
	private int width = 931;
	private int count = 0;
	// Flag indicating whether sound is on or off.
	private boolean sound = false;
	private boolean paused = false;
	private int numBases = 0;
	// New in A4, shockwaves
	private ArrayList<Point> newShockWaves = null;

	private GameObjectCollection goc = null;
	private Fixed currentSelectedObject = null;
	private boolean userEdit = false;

	// Creates the initial game setup or when the
	// player loses a life. 
	public void init() {
		goc = new GameObjectCollection();
		newShockWaves = new ArrayList<Point>();
	}

	public void initObjects() {
		init();
		// Four bases each corner
		goc.add(new Base(50, MARGIN_PLACE, MARGIN_PLACE, LIGHTBLUE, this, 1));
		goc.add(new Base(50, width-MARGIN_PLACE, MARGIN_PLACE, LIGHTBLUE, this, 2));
		goc.add(new Base(50, MARGIN_PLACE, height-MARGIN_PLACE, LIGHTBLUE, this, 3));
		goc.add(new Base(50, width-MARGIN_PLACE, height-MARGIN_PLACE, LIGHTBLUE, this, 4));

		// Four energy stations for refill
		goc.add(new EnergyStation(40, width/2, 2 * MARGIN_PLACE, ENERGYGREEN, this));
		goc.add(new EnergyStation(50, width/2, height - 2 * MARGIN_PLACE, ENERGYGREEN, this));
		goc.add(new EnergyStation(70, 2* MARGIN_PLACE, height/2, ENERGYGREEN, this));
		goc.add(new EnergyStation(60, width - 2 * MARGIN_PLACE, height/2, ENERGYGREEN, this));

		// Three drones hovering NW with random speed and random heading
		Random rand = new Random();
		goc.add(new Drone(40, width/3 + MARGIN_PLACE, height/3, ColorUtil.BLACK, 
				rand.nextInt(10) + 35, rand.nextInt(360), this));
		goc.add(new Drone(40, width/3, height/3 + MARGIN_PLACE, ColorUtil.BLACK,
				rand.nextInt(10) + 35, rand.nextInt(360), this));
		goc.add(new Drone(40, width/3, height/3 + MARGIN_PLACE, ColorUtil.BLACK,
				rand.nextInt(10) + 35, rand.nextInt(360), this));

		// Get the first base to initialize Robot locations.
		IIterator it = goc.getIterator();
		GameObject first = it.getNext();

		// Initialize 3 enemy non player robots with different strategies.
		// Each of them close to the player robot and the first three bases
		NonPlayerRobot npr = new NonPlayerRobot(50, 3*MARGIN_PLACE, MARGIN_PLACE, 
				ColorUtil.MAGENTA, 40, 40, 100, 1, this);
		npr.setStrategy(new AttackStrategy(npr));
		goc.add(npr);
		npr = new NonPlayerRobot(50, MARGIN_PLACE, 3*MARGIN_PLACE,
				ColorUtil.MAGENTA, 40, 40, 100, 1, this);
		npr.setStrategy(new NextBaseStrategy(npr));
		goc.add(npr);
		npr = new NonPlayerRobot(50, width/2-MARGIN_PLACE, height/2-MARGIN_PLACE,
				ColorUtil.MAGENTA, 40, 40, 100, 1, this);
		npr.setStrategy(new NextBaseStrategy(npr));
		goc.add(npr);

		// Player Robot will always be the last element in the collection.
		goc.add(Robot.getInstance(60, first.getX()+10, first.getY()+10, 
				ColorUtil.argb(255, 255, 1, 1), 50, 100, INITIAL_ENERGY, 1, this));

		numBases = getNumBases();
		notifyObservers(this);
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

	public int getRobotInitialEnergy() {
		return INITIAL_ENERGY;
	}

	public int getRobotInitialLives() {
		return INITIAL_NUM_LIVES;
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
	
	public void playSoundFor(String description) {
		if (sound) {
			if (description.equalsIgnoreCase("destroyed")) {
				destroyed.play();
			}
		}
	}
	
	// Set the dimensions of the map from MapView.
	public void setDimensions(int width, int height) {
		this.width = width;
		this.height = height;
		initObjects();
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
	public void tick(int tickTime) {
		// If the game is 'Paused' nothing is moving.
		// Counter is also stopped.
		if (!paused) {
			count++;
			updateMoveableObjects(tickTime);
			CheckCollisions();
			// New in A4
			handleShockwaves();
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
		// Gracefully stop background sound
		bgs.pause();
		// exit
		// TODO: Add an exit dialog with statistics
		System.exit(0);
	}
	
	public void reInitialize() {
		initObjects();
		getPlayerRobot().reinitializeRobot();
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
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
	// The amount of damage taken by the robot is half of the
	// damage dealt by a collision with an NPR.
	public void collideRobotWithDrone(Robot robot, Drone drone) {
		int currentDamage = robot.getDamageLevel();
		robot.setDamageLevel(currentDamage + NPR_COLLISION_DAMAGE/2);
		if (sound) {
			drone_collision.play();
		}
		// Add ShockWave for this collision.
		addShockWave(robot.getX(), robot.getY());
	}

	// The Robot collides with the NPR,
	// dealing damage to both Robots.
	public void collideRobotWithNPR(Robot robot, NonPlayerRobot npr) {
		robot.setDamageLevel(robot.getDamageLevel() + NPR_COLLISION_DAMAGE);
		npr.setDamageLevel(npr.getDamageLevel() + NPR_COLLISION_DAMAGE);
		fadeColor(npr);
		if (sound) {
			npr_collision.play();
		}
		// Add ShockWave for this collision.
		addShockWave(robot.getX(), robot.getY());
	}

	// Handles collision between a Robot/NPR and a Base
	// New in A3: NPRs increment their last base reached as well. 
	public void collideRobotWithBase(Robot robot, Base base) {
		int lastBaseReached = robot.getLastBaseReached();
		int baseSeqNum = base.getSequenceNumber();

		if ((lastBaseReached + 1) == baseSeqNum) {
			// This also updates NPR's last base reached.
			robot.setLastBaseReached(baseSeqNum);
		}
		if (sound) {
			base_collision.play();
		}
	}

	// Player robot simulates a collision with an Energy Station.
	// This drains all the energy from the station and gives it to the Robot.
	public void collideRobotWithEnergyStation(Robot robot, EnergyStation station) {
		int currentEnergy = robot.getEnergyLevel();
		int energyStationEnergy = station.getCapacity();
		// Add random energy station only if the current station was
		// not already depleted
		if (energyStationEnergy > 0) {
			robot.setEnergyLevel(currentEnergy + energyStationEnergy);
			station.setCapacity(0);
			fadeColor(station);
			addRandomEnergyStation();
		}
		// Sound collision on both depleted and non-depleted energy stations
		if (sound) {
			if (robot instanceof NonPlayerRobot) {
				energy2.play();
			} else {
				energy1.play();
			}
		}
	}

	// New in A3: NPRs can collide between themselves and take damage
	public void collideNPRWithNPR(Robot npr1, NonPlayerRobot npr2) {
		npr1.setDamageLevel(npr1.getDamageLevel() + NPR_COLLISION_DAMAGE);
		fadeColor(npr1);
		npr2.setDamageLevel(npr2.getDamageLevel() + NPR_COLLISION_DAMAGE);
		fadeColor(npr2);
		if (sound) {
			npr_collision.play();
		}
		// Add ShockWave for this collision.
		addShockWave(npr1.getX(), npr2.getY());
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
		if (currentSelectedObject != null) {
			userEdit = b;
		}
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
		setChanged();
		notifyObservers();
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
		// System.out.println("moveSelectedObjectTo: " + xPos + ", " + yPos);
		// Check if there is a selected object and move it to
		// its new location.
		if (currentSelectedObject != null) {
			currentSelectedObject.setXY(xPos, yPos);
			// unselect moved item
			currentSelectedObject.setSelected(false);
			currentSelectedObject = null;
			// clear userEdit flag
			userEdit = false;
		}
		setChanged();
		notifyObservers();
	}
	
	public void createSounds() {
		bgs = new BGSound("back_lady80s_16khz.wav");
		energy1 = new Sound("energy1.wav");
		energy2 = new Sound("energy2.wav");
		base_collision = new Sound("base_collision.wav");
		drone_collision = new Sound("drone_collision.wav");
		npr_collision = new Sound("npr_collision.wav");
		destroyed = new Sound("destroyed.wav");
		game_win = new Sound("game_win.wav");
		game_lost = new Sound("game_lost.wav");
	}

	public void addShockWave(double x, double y) {
		newShockWaves.add(new Point((float)x, (float)y));
	}

	// Every tick we check for collisions and delegate to objects
	// to handle them.
	private void CheckCollisions() {
		IIterator iter = goc.getIterator();
		while (iter.hasNext()) {
			GameObject thisObj = iter.getNext();
			IIterator otherIter = goc.getIterator();
			while (otherIter.hasNext()) {
				GameObject otherObj = otherIter.getNext();
				if (!thisObj.equals(otherObj)) {
					if (thisObj.collidesWith(otherObj)) {
						System.out.println("CheckCollisions " + thisObj + " collided with " + otherObj);
						thisObj.handleCollision(otherObj);
					}
				}
			}
		}
	}

	// Adds a new Energy Station in a random location with a random size around 10.
	private void addRandomEnergyStation() {
		Random rand = new Random();
		double randXloc = 100 + (double) rand.nextInt(width - 100);
		double randYloc = 100 + (double) rand.nextInt(height - 100);
		int randSize = rand.nextInt(50) + 50;
		int index = goc.size() - 1;
		if (index < 0) {
			index = 0;
		}
		goc.add(index, new EnergyStation(randSize, randXloc, randYloc, ENERGYGREEN, this));

	}

	// Halves the current alpha of an object to fade out the color.
	// Alternatively set a lighter version of the color.
	private void fadeColor(GameObject obj) {
		final int fadeValue = 30;
		int currentColor = obj.getColor();
		int alpha = Math.max(10, ColorUtil.alpha(currentColor) - fadeValue);
		int red = Math.min(245, ColorUtil.red(currentColor) + fadeValue);
		int green = Math.min(245, ColorUtil.green(currentColor) + fadeValue);
		int blue = Math.min(245, ColorUtil.blue(currentColor) + fadeValue);
		obj.setColor(ColorUtil.argb(alpha, red, green, blue));
	}

	private void handleShockwaves() {
		// First remove all spent shockwaves
		IIterator it = goc.getIterator();
		while (it.hasNext()) {
			GameObject go = it.getNext();
			if (go instanceof ShockWave) {
				if (((ShockWave)go).isSpent()) {
					goc.remove(go);
				}
			}
		}
		// Then add new collision shockwaves
		Random shwRand = new Random();
		for (int i=0; i < newShockWaves.size(); ++i) {
			ShockWave shw = new ShockWave(100+shwRand.nextInt(200), (double)newShockWaves.get(i).getX(), 
				(double)newShockWaves.get(i).getY(), ColorUtil.BLUE, 50 + shwRand.nextInt(100), 
				shwRand.nextInt(360), this);
			goc.add(0, shw);
		}
		newShockWaves.clear();
	}

	// Iterates through the collection and calls every movable object's move() method.
	private void updateMoveableObjects(int tickTime) {
		IIterator it = goc.getIterator();
		while (it.hasNext()) {
			GameObject go = it.getNext();
			if (go instanceof Movable) {
				((Movable)go).move(tickTime);
			}
		}
	}

}