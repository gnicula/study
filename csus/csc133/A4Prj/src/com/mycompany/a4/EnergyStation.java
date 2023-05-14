package com.mycompany.a4;

import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Point;

// Energy station is a Fixed object which additionally has an energy capacity.
public class EnergyStation extends Fixed {
	private int capacity;
	private EllipseShape myEllipse;
	
	public EnergyStation(int size, double x, double y, int color, GameWorld w) {
		super(size, x, y, color, w);
		this.capacity = size * 3;
		myEllipse = new EllipseShape(size, size, color);
		myEllipse.setText(Integer.toString(capacity));
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public void setCapacity(int cap) {
		capacity = cap;
	}

	public void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn) {
		Transform gXform = Transform.makeIdentity();
		g.getTransform(gXform);
		Transform gOrigXform = gXform.copy(); //save the original xform
		gXform.concatenate(myRotate);
		gXform.translate((float)getX(), (float)getY());
		gXform.scale(myScale.getScaleX(), myScale.getScaleY());
		g.setTransform(gXform);

		myEllipse.setFilled(!isSelected);
		myEllipse.draw(g);
		g.setTransform(gOrigXform); //restore the original xform (remove LTs)
		//do not use resetAffine() in draw()! Instead use getTransform()/setTransform(gOrigForm)
	}

	public String toString() { 
		String parentDesc = super.toString();
		String myDesc = "Energy Station: " + parentDesc + ", Capacity= " + capacity;
		return myDesc ; 
	}
}
