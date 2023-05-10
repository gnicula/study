package com.mycompany.a4;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

// New in A3, all GameObjects must implement this interface.
public interface IDrawable {

	public void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn);

}
