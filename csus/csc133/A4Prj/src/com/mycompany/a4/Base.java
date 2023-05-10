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

	/*
	// Draw itself as a triangle
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
		if (!isSelected) {
			g.fillPolygon(x, y, 3);
		} else {
			g.drawPolygon(x, y, 3);			
		}
		drawNumber(g,  pCmpRelPrnt, sequenceNumber);
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
		myTriangle.setFilled(!isSelected);
		myTriangle.draw(g);
		// g.drawLine(pCmpRelPrnt.getX()+top.getX(), pCmpRelPrnt.getY()+top.getY(),
		// pCmpRelPrnt.getX() + bottomLeft.getX(),pCmpRelPrnt.getY() + bottomLeft.getY());
		//...[draw the rest of the lines]
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
