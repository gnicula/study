package com.mycompany.a4;

import com.codename1.ui.geom.Point;

// New in A3, all selectable game objects must implement this interface.
public interface ISelectable {

	public void setSelected(boolean sel);
	
	public boolean isSelected();
	// Returns true if the object's bounding box contains
	// the point with the given parent offset.
	public boolean contains(Point pPtrRel, Point pCmpRel);

}
