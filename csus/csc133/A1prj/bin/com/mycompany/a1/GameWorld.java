package com.mycompany.a1;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Form;

public class GameWorld {
	
	private Fixed[] fixedArray = new Fixed[7];
	private Movable[] movableObjects = new Movable[3];
	
	public void init() {
		
		fixedArray[0] = new Base(8, 50, 50, ColorUtil.BLACK, this, 1);
		fixedArray[1] = new Base(8, 100, 100, ColorUtil.BLACK, this, 2);
		fixedArray[2] = new Base(8, 150, 150, ColorUtil.BLACK, this, 3);
		fixedArray[3] = new Base(8, 200, 200, ColorUtil.BLACK, this, 4);
		fixedArray[4] = new Base(8, 300, 300, ColorUtil.BLACK, this, 5);
	
		fixedArray[5] = new EnergyStation(10, 20, 50, ColorUtil.GREEN, this);
		fixedArray[6] = new EnergyStation(10, 70, 40, ColorUtil.GREEN, this);
		
		movableObjects[0] = new Robot(10, fixedArray[0].getX(), fixedArray[0].getY(), 
										ColorUtil.CYAN, 30, 50, 2, this);
		movableObjects[1] = new Drone(10, 75, 75, ColorUtil.BLUE, 20, 0, this);
		movableObjects[2] = new Drone(10, 95, 95, ColorUtil.BLUE, 20, 0, this);
		
	}

	public void mCommand() {
		for (int i=0; i < fixedArray.length; i++) {
			System.out.println(fixedArray[i]);
		}
		for (int i=0; i < movableObjects.length; i++) {
			System.out.println(movableObjects[i]);
		}
		
	}

	public void exit() {
		System.exit(0);
	}
		
	}