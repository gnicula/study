package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

// The 'Base' class implements basic methods and it has a toString() 
// that builds the Base string representation on top of its 
// inherited toString method.
public class Base extends Fixed {
	// Base objects are Fixed objects with a sequence number.
	private int sequenceNumber;
	
	public Base(int size, double x, double y, int color, GameWorld w, int sequenceNumber) {
		super(size, x, y, color, w);
		this.sequenceNumber = sequenceNumber;		
	}
	
	@Override
	public void setColor(int color) {
		System.out.println("Color cannot be changed once set");
	}
	
	public int getSequenceNumber() {
		return this.sequenceNumber;
	}
	
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		int x[] = new int[3];
		int y[] = new int[3];
		x[0] = pCmpRelPrnt.getX() + (int)getX();
		y[0] = pCmpRelPrnt.getY() + (int)getY() + getSize()/2;
		x[1] = pCmpRelPrnt.getX() + (int)getX() + getSize()/2;
		y[1] = pCmpRelPrnt.getY() + (int)getY() - getSize()/2;
		x[2] = pCmpRelPrnt.getX() + (int)getX() - getSize()/2;
		y[2] = pCmpRelPrnt.getY() + (int)getY() - getSize()/2;
		g.setColor(getColor());
		g.fillPolygon(x, y, 3);
		drawNumber(g,  pCmpRelPrnt, sequenceNumber);
	}

	@Override
	public String toString() {   
		String parentDesc = super.toString();    
		String myDesc = "Base: " + parentDesc + ", sequenceNumber=" + sequenceNumber;
		return myDesc;
	}
}
