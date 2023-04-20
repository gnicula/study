package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

// Fixed is a type of GameObject that is used to define a
// common base type for the immovable game objects.
public class Fixed extends GameObject implements ISelectable {
	protected boolean isSelected = false;
	
	public Fixed(int size, double x, double y, int color, GameWorld w) {
		super(size, x, y, color, w);
	}
	
	public void drawNumber(Graphics g, Point pCmpRelPrnt, int number) {
		// TODO: set small font
		g.setColor(ColorUtil.BLACK);
		g.drawString(Integer.toString(number), 
				pCmpRelPrnt.getX() + (int)getX() - STRING_OFFSET, 
				pCmpRelPrnt.getY() + (int)getY() - 2*STRING_OFFSET);
	}

	public void setSelected(boolean b) {
		this.isSelected = b;
	}
	
	public boolean isSelected() {
		return isSelected;
	};

	public boolean contains(Point pPtrRel, Point pCmpRel) {
		int objX = (int) getX();
		int objY = (int) getY();
		if (Math.abs(objX - pPtrRel.getX()) < pCmpRel.getX() 
				&& Math.abs(objY - pPtrRel.getX()) < pCmpRel.getY()) {
			return true;
		}
		return false;
	}

	@Override
	public void handleCollision(GameObject otherObject) {
		if (!collidingWith.contains(otherObject)) {
			if (otherObject instanceof Robot) {
				((Robot)otherObject).handleCollision(this);
			}
		}	
	}

}
