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

	/*
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
	*/

	public void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn) {
		// Do not forget to do “local origin” transformations.
		// ORDER of LTs: Scaling LT will be applied to coordinates FIRST,
		// then Translation LT, and lastly Rotation LT.
		// Also restore the xform at the end of draw() to remove this sub-shape’s LTs
		// from xform of the Graphics object. Otherwise, we would also
		// apply these LTs to the next sub-shape since it also uses the same Graphics object.
		Transform gXform = Transform.makeIdentity();
		g.getTransform(gXform);
		Transform gOrigXform = gXform.copy(); //save the original xform
		// gXform.translate(pCmpRelScrn.getX(),pCmpRelScrn.getY());
		gXform.concatenate(myRotate); //Rotation is LAST
		// gXform.translate(myTranslate.getTranslateX(), myTranslate.getTranslateY());
		gXform.translate((float)getX(), (float)getY());
		gXform.scale(myScale.getScaleX(), myScale.getScaleY());
		// gXform.translate(-pCmpRelScrn.getX(),-pCmpRelScrn.getY());
		g.setTransform(gXform);
		//draw the lines as before
		myEllipse.setFilled(!isSelected);
		myEllipse.draw(g);
		// g.drawLine(pCmpRelPrnt.getX()+top.getX(), pCmpRelPrnt.getY()+top.getY(),
		// pCmpRelPrnt.getX() + bottomLeft.getX(),pCmpRelPrnt.getY() + bottomLeft.getY());
		//...[draw the rest of the lines]
		g.setTransform(gOrigXform); //restore the original xform (remove LTs)
		//do not use resetAffine() in draw()! Instead use getTransform()/setTransform(gOrigForm)
	}

	public String toString() { 
		String parentDesc = super.toString();
		String myDesc = "Energy Station: " + parentDesc + ", Capacity= " + capacity;
		return myDesc ; 
	}
}
