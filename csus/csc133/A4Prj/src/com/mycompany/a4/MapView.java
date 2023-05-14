package com.mycompany.a4;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.Transform.NotInvertibleException;
import com.codename1.ui.geom.Point;

public class MapView extends Container implements Observer {

	private GameWorld gw = null;
	private Transform worldToND, ndToDisplay, theVTM;
	private float viewWidth, viewHeight, viewL, viewB;
	private float originalViewWidth, originalViewHeight;
	private int pPrevDragLocX, pPrevDragLocY;
	// private float winLeft, winBottom, winRight, winTop;

	public MapView(GameWorld gw) {
		super();
		this.gw = gw;
		resetViewWindow();
	}

	public void resetViewWindow() {
		viewWidth = gw.getWidth();
		viewHeight = gw.getHeight();
		viewL = 0;
		viewB = 0;
		originalViewWidth = viewWidth;
		originalViewHeight = viewHeight;		
	}

	public void update(Observable o, Object arg) {
		// Call the method in GameWorld (Observable) that outputs the
		// game object information to the console.
		gw.mCommand();
		// Repaint will trigger paint() and it should paint all objects.
		repaint();
	}

	public void paint(Graphics g) {
		super.paint(g);
		// First update the view transform matrix
		updateVTM(viewWidth, viewHeight, viewL, viewB);

		Transform gXform = Transform.makeIdentity();
		g.getTransform(gXform);
		// Save current Graphics transform
		Transform oldOne = gXform.copy();

		// Apply the VTM
		gXform.translate(getAbsoluteX(), getAbsoluteY()); //+340
		gXform.concatenate(theVTM); // VTM xform
		gXform.translate(-getParent().getAbsoluteX(), -getParent().getAbsoluteY());
		g.setTransform(gXform);

		// Draw all components in world coordinates
		Point pCmpRelPrnt = new Point(this.getX(), this.getY());
		Point pCmpRelScrn = new Point(getAbsoluteX(), getAbsoluteY());
		IIterator it = gw.getGameObjectsIterator();
		while (it.hasNext()) {
			GameObject go = it.getNext();
			go.draw(g, pCmpRelPrnt, pCmpRelScrn);
		}
		// DEBUG the transforms.
		// g.setColor(ColorUtil.GREEN);
		// g.drawRect(20, 20, (int)gw.getWidth()-40, (int)gw.getHeight()-40);
		// g.drawLine(0, 0, gw.getWidth(), gw.getHeight());

		// g.setTransform(oldOne);
		g.resetAffine();
	}
		
	// Used by the editing mode when game is paused.
	@Override
	public void pointerPressed(int x, int y) {
		pPrevDragLocX = x;
		pPrevDragLocY = y;
		Transform inverseConcatLTs = Transform.makeIdentity();
		// Handle selections only when 'paused'
		if (gw.getPaused()) {
			float[] pts = {x-getParent().getAbsoluteX()-getX(), y-getParent().getAbsoluteY()-getY()};
			try {
				theVTM.getInverse(inverseConcatLTs);
				inverseConcatLTs.transformPoint(pts, pts);
			} catch (NotInvertibleException e) {
				System.out.println("Couldn't get inverse world coordinates for x: " + x + ", y: " + y);
			}
			// int xPos = x - getParent().getAbsoluteX() - getX();
			// int yPos = y - getParent().getAbsoluteY() - getY();
			if (gw.isUserEdit()) {
				gw.moveSelectedObjectTo((int)pts[0], (int)pts[1]);
			} else {
				gw.selectObjectAt((int)pts[0], (int)pts[1]);
			}
		}
	}
	
	public void updateVTM(float viewWidth, float viewHeight, float viewL, float viewB) {
		theVTM = Transform.makeIdentity();
		worldToND = Transform.makeScale(1.0f/viewWidth, 1.0f/viewHeight);
		worldToND.translate(-viewL, -viewB);
		ndToDisplay = Transform.makeTranslation(0, this.getHeight());
		ndToDisplay.scale(this.getWidth(), -this.getHeight());
		theVTM.concatenate(ndToDisplay);
		theVTM.concatenate(worldToND);
	}

	public void zoom(float factor) {
		float newWidth = originalViewWidth * factor;
		float newHeight = originalViewHeight * factor;
		if(newWidth<0||newHeight<0||newWidth>8192||newHeight>4320) {
			newWidth = Math.min(Math.max(0, newWidth), 8192);
			newHeight = Math.min(Math.max(0, newHeight), 4320);
		}
		viewL += (viewWidth - newWidth)/2;
		viewB += (viewHeight - newHeight)/2;
		viewWidth = newWidth;
		viewHeight = newHeight;

		this.repaint();
	}

	public boolean pinch(float scale) {
		zoom(scale);
		return true;
	}
	
	public void panHorizontal(double delta) {
		viewL += delta;
		this.repaint();
	}
	
	public void panVertical(double delta) {
		viewB += delta;
		this.repaint();
	}

	public void pointerDragged(int x, int y) {
		double dx = pPrevDragLocX - x;
		double dy = pPrevDragLocY - y;
		dx *= (viewWidth / (float)getWidth());
		dy *= -(viewHeight / (float)getHeight());
		panHorizontal(dx);
		panVertical(dy);
		pPrevDragLocX = x;
		pPrevDragLocY = y;
	}

}
