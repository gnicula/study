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

	private GameObjectCollection goc = new GameObjectCollection();

	public void init() {

		goc.add(new Base(8, 50, 50, ColorUtil.BLACK, this, 1));
		goc.add(new Base(8, 100, 100, ColorUtil.BLACK, this, 2));
		goc.add(new Base(8, 150, 150, ColorUtil.BLACK, this, 3));
		goc.add(new Base(8, 200, 200, ColorUtil.BLACK, this, 4));
		goc.add(new Base(8, 300, 300, ColorUtil.BLACK, this, 5));

		goc.add(new EnergyStation(10, 20, 50, ColorUtil.GREEN, this));
		goc.add(new EnergyStation(10, 70, 40, ColorUtil.GREEN, this));

		goc.add(new Drone(10, 75, 75, ColorUtil.BLUE, 20, 0, this));
		goc.add(new Drone(10, 95, 95, ColorUtil.BLUE, 20, 0, this));

		// get the first base to initialize Robot locations.
		IIterator it = goc.getIterator();
		GameObject first = it.getNext();

		// initialize 3 enemy non player robots.
		NonPlayerRobot npr = new NonPlayerRobot(10, first.getX() + 50, first.getY() + 25, ColorUtil.MAGENTA, 25, 100, 1,
				this);
		npr.setStrategy(new NextBaseStrategy(npr));
		goc.add(npr);
		npr = new NonPlayerRobot(10, first.getX() + 35, first.getY() + 45, ColorUtil.MAGENTA, 25, 100, 1, this);
		npr.setStrategy(new AttackStrategy(npr));
		goc.add(npr);
		npr = new NonPlayerRobot(10, first.getX() + 60, first.getY() + 40, ColorUtil.MAGENTA, 25, 100, 1, this);
		npr.setStrategy(new AttackStrategy(npr));
		goc.add(npr);

		// Player Robot will always be the last one initialized.
		goc.add(new Robot(10, first.getX(), first.getY(), ColorUtil.CYAN, 30, 50, 2, this));

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
		this.height = height;
		this.width = width;
	}

	public void accelerate() {
		getPlayerRobot().accelerate();
	}

	public void brake() {
		getPlayerRobot().brake();
	}

	public void tick() {
		count++;
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

	private int getNumBases() {
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
}