package com.mycompany.a2;

public class EnergyStation extends Fixed {
	
	public EnergyStation(int size, double x, double y, int color, GameWorld w) {
		super(size, x, y, color, w);
		this.capacity = size * 1;
	}

	private int capacity;
	
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
