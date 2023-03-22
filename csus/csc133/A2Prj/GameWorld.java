package com.mycompany.a2;
import java.util.ArrayList;
import java.util.Observable;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Form;

public class GameWorld extends Observable {
	
	private int count = 0;
	private ArrayList<GameObject> GameObjectCollection = new ArrayList<GameObject>();
	
	public void init() {
		
		GameObjectCollection.add(new Base(8, 50, 50, ColorUtil.BLACK, this, 1));
		GameObjectCollection.add(new Base(8, 100, 100, ColorUtil.BLACK, this, 2));
		GameObjectCollection.add(new Base(8, 150, 150, ColorUtil.BLACK, this, 3));
		GameObjectCollection.add(new Base(8, 200, 200, ColorUtil.BLACK, this, 4));
		GameObjectCollection.add(new Base(8, 300, 300, ColorUtil.BLACK, this, 5));
	
		GameObjectCollection.add(new EnergyStation(10, 20, 50, ColorUtil.GREEN, this));
		GameObjectCollection.add(new EnergyStation(10, 70, 40, ColorUtil.GREEN, this));
		
		GameObjectCollection.add(new Drone(10, 75, 75, ColorUtil.BLUE, 20, 0, this));
		GameObjectCollection.add(new Drone(10, 95, 95, ColorUtil.BLUE, 20, 0, this));
		
		//Player Robot will always be the last one initialized.
		GameObjectCollection.add(new Robot(10, GameObjectCollection.get(0).getX(), GameObjectCollection.get(0).getY(), 
				ColorUtil.CYAN, 30, 50, 2, this));
		
	}

	public void mCommand() {
		for (int i=0; i < GameObjectCollection.size() - 1; i++) {
			System.out.println(GameObjectCollection.get(i));
		}
	}
	
	public void dCommand() {
		System.out.println(GameObjectCollection.get(GameObjectCollection.size() - 1));
	}
	
	public int getCount() {
		return count;
	}
	
	public void tick() {
		count++;
	}

	public void exit() {
		System.exit(0);
	}
		
}