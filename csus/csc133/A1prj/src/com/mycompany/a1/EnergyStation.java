package com.mycompany.a1;

public class EnergyStation extends Fixed {
	
	public EnergyStation(int size, double x, double y, int color, GameWorld w) {
		super(size, x, y, color, w);
	}

	private int capacity;
	
	public int getCapacity() {
		return capacity;
	}
	
	public void setCapacity(int cap) {
		capacity = cap;
	}
}
