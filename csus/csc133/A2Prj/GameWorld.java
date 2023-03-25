package com.mycompany.a2;

import java.util.Observable;
import java.util.Random;

import com.codename1.charts.util.ColorUtil;

public class GameWorld extends Observable {

	private final int NPR_COLLISION_DAMAGE = 10;

	private int height = 0;
	private int width = 0;
	private int count = 0;
	private boolean sound = false;
	private int numBases = 0;

	private GameObjectCollection goc = null;

	public void init() {

		goc = new GameObjectCollection();

		goc.add(new Base(10, 50, 50, ColorUtil.BLACK, this, 1));
		goc.add(new Base(10, 250, 50, ColorUtil.BLACK, this, 2));
		goc.add(new Base(10, 450, 50, ColorUtil.BLACK, this, 3));
		goc.add(new Base(10, 50, 250, ColorUtil.BLACK, this, 4));
		goc.add(new Base(10, 50, 450, ColorUtil.BLACK, this, 5));

		goc.add(new EnergyStation(10, 200, 200, ColorUtil.GREEN, this));
		goc.add(new EnergyStation(10, 600, 300, ColorUtil.GREEN, this));

		goc.add(new Drone(10, 75, 75, ColorUtil.BLUE, 20, 0, this));
		goc.add(new Drone(10, 95, 95, ColorUtil.BLUE, 20, 0, this));

		// get the first base to initialize Robot locations.
		IIterator it = goc.getIterator();
		GameObject first = it.getNext();

		// initialize 3 enemy non player robots.
		NonPlayerRobot npr = new NonPlayerRobot(10, first.getX() + 250, first.getY() + 25, ColorUtil.MAGENTA, 15, 25, 100, 1,
				this);
		npr.setStrategy(new AttackStrategy(npr));
		goc.add(npr);
		npr = new NonPlayerRobot(10, first.getX() + 35, first.getY() + 145, ColorUtil.MAGENTA, 20, 25, 100, 1, this);
		npr.setStrategy(new AttackStrategy(npr));
		goc.add(npr);
		npr = new NonPlayerRobot(10, first.getX() + 160, first.getY() + 140, ColorUtil.MAGENTA, 25, 25, 100, 1, this);
		npr.setStrategy(new AttackStrategy(npr));
		goc.add(npr);

		// Player Robot will always be the last one initialized.
		goc.add(new Robot(20, first.getX(), first.getY(), ColorUtil.argb(255, 200, 100, 255), 5, 30, 50, 2, this));

		numBases = getNumBases();
	}

	public void mCommand() {

		IIterator it = goc.getIterator();
		while (it.hasNext()) {
			System.out.println(it.getNext());
		}
	}

	public void dCommand() {

		System.out.println(getPlayerRobot());
	}

	public int getCount() {
		System.out.println("getCount: " + count);
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

	public void tick() {
		count++;
		updateMoveableObjects();
		setChanged();
		notifyObservers(this);
	}

	public void changeStrategy() {
		IIterator it = goc.getIterator();
		while (it.hasNext()) {
			GameObject go = it.getNext();
			if (go instanceof NonPlayerRobot) {
				((NonPlayerRobot) go).changeStrategy();
			}
		}
	}

	public Base getNextBaseInSequence(int sequenceNumber) {
		IIterator it = goc.getIterator();

		sequenceNumber += 1;
		if (sequenceNumber > numBases) {
			sequenceNumber %= numBases;
		}

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
	
	public void reInitialize(int livesLeft) {
		init();
		getPlayerRobot().setLives(livesLeft);
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
	
	public void collideWithDrone() {
		Robot myRobot = getPlayerRobot();
		int currentDamage = myRobot.getDamageLevel();
		myRobot.setDamageLevel(currentDamage + NPR_COLLISION_DAMAGE/2);
	}

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

	public void collideWithBase(int baseNum) {
		Robot myRobot = getPlayerRobot();
		int lastBaseReachedCheck = myRobot.getLastBaseReached();

		if ((lastBaseReachedCheck + 1) == baseNum) {
			myRobot.setLastBaseReached(baseNum);
		}
	}

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

	private void fadeColor(GameObject obj) {
		int currentColor = obj.getColor();
		int alpha = ColorUtil.alpha(currentColor) / 2;
		obj.setColor(ColorUtil.argb(alpha, ColorUtil.red(currentColor), ColorUtil.green(currentColor),
				ColorUtil.blue(currentColor)));
	}

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