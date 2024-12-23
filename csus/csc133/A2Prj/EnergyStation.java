package com.mycompany.a2;

// Energy station is a Fixed object which additionally has an energy capacity.
public class EnergyStation extends Fixed {
	private int capacity;
	
	public EnergyStation(int size, double x, double y, int color, GameWorld w) {
		super(size, x, y, color, w);
		this.capacity = size * 1;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public void setCapacity(int cap) {
		capacity = cap;
	}
	
	public String toString() { 
		String parentDesc = super.toString();
		String myDesc = "Energy Station: " + parentDesc + ", Capacity= " + capacity;
		return myDesc ; 
	}
}
