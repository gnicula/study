package com.mycompany.a3;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

// Energy station is a Fixed object which additionally has an energy capacity.
public class EnergyStation extends Fixed {
	private int capacity;
	
	public EnergyStation(int size, double x, double y, int color, GameWorld w) {
		super(size, x, y, color, w);
		this.capacity = size * 3;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public void setCapacity(int cap) {
		capacity = cap;
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		g.setColor(getColor());
		if (!isSelected) {
			g.fillArc(pCmpRelPrnt.getX() + (int)getX() - getSize()/2,
					pCmpRelPrnt.getY() + (int)getY() - getSize()/2,
					getSize(), getSize(), 0, 360);
		} else {
			g.drawArc(pCmpRelPrnt.getX() + (int)getX() - getSize()/2,
					pCmpRelPrnt.getY() + (int)getY() - getSize()/2,
					getSize(), getSize(), 0, 360);			
		}
		drawNumber(g,  pCmpRelPrnt, getCapacity());
	}

	public String toString() { 
		String parentDesc = super.toString();
		String myDesc = "Energy Station: " + parentDesc + ", Capacity= " + capacity;
		return myDesc ; 
	}
}
