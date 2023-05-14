package com.mycompany.a4;

import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Point;

// The 'Base' class implements basic methods and it has a toString() 
// that builds the Base string representation on top of its 
// inherited toString method.
public class Base extends Fixed {
	// Base objects are Fixed objects with a sequence number.
	private int sequenceNumber;
	private TriangleShape myTriangle;
	
	public Base(int size, double x, double y, int color, GameWorld w, int sequenceNumber) {
		super(size, x, y, color, w);
		this.sequenceNumber = sequenceNumber;
		myTriangle = new TriangleShape(size, size, color);
		myTriangle.setText(Integer.toString(sequenceNumber));
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

	public void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn) {
		Transform gXform = Transform.makeIdentity();
		g.getTransform(gXform);
		Transform gOrigXform = gXform.copy(); //save the original xform
		gXform.concatenate(myRotate);
		gXform.translate((float)getX(), (float)getY());
		gXform.scale(myScale.getScaleX(), myScale.getScaleY());
		g.setTransform(gXform);

		myTriangle.setFilled(!isSelected);
		myTriangle.draw(g);
		g.setTransform(gOrigXform); //restore the original xform (remove LTs)
		//do not use resetAffine() in draw()! Instead use getTransform()/setTransform(gOrigForm)
	}

	@Override
	public String toString() {   
		String parentDesc = super.toString();    
		String myDesc = "Base: " + parentDesc + ", sequenceNumber=" + sequenceNumber;
		return myDesc;
	}
}
