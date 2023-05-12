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
		// winRight = 931 / 2; // hardcoded value = this.getWidth()/2 (for the iPad skin)
		// winTop = 639 / 2; // hardcoded value = this.getHeight()/2 (for the iPad skin)
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

	/*
	 * @Override
	 * public void paint(Graphics g) {
	 * // Set a white background
	 * g.setColor(ColorUtil.WHITE);
	 * g.fillRect(getX(), getY(), gw.getWidth(), gw.getHeight());
	 * // Iterate through all objects and ask them to draw() themselves
	 * // using this Graphics object.
	 * IIterator it = gw.getGameObjectsIterator();
	 * while (it.hasNext()) {
	 * GameObject go = it.getNext();
	 * go.draw(g, new Point(getX(), getY()));
	 * }
	 * }
	 */

	 /*
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		// ...[calculate winWidth and winHeight]
		float winWidth = gw.getWidth();
		float winHeight = gw.getHeight();
		System.out.println("Win w: " + winWidth + " Win h: " + winHeight);
		// construct the Viewing Transformation Matrix
		worldToND = buildWorldToNDXform(winWidth, winHeight, winLeft, winBottom);
		ndToDisplay = buildNDToDisplayXform(this.getWidth(), this.getHeight());
		theVTM = ndToDisplay.copy();
		theVTM.concatenate(worldToND); // worldToND will be applied first to points!
		// concatenate the VTM onto the g’s current transformation
		// (do not forget to apply “local //origin” transformation)
		Transform gXform = Transform.makeIdentity();
		g.getTransform(gXform);
		// gXform.translate(getAbsoluteX(), getAbsoluteY()); // local origin xform (part 2)
		gXform.concatenate(theVTM); // VTM xform
		// gXform.translate(-getAbsoluteX(), -getAbsoluteY()); // local origin xform (part 1)
		g.setTransform(gXform);
		// tell each shape to draw itself using the g (which contains the VTM)
		Point pCmpRelPrnt = new Point(this.getX(), this.getY());
		Point pCmpRelScrn = new Point(getAbsoluteX(), getAbsoluteY());
		// for (Shape s : shapeCollection)
		// s.draw(g, pCmpRelPrnt, pCmpRelScrn);
		// Iterate through all objects and ask them to draw() themselves
		// using this Graphics object.
		IIterator it = gw.getGameObjectsIterator();
		while (it.hasNext()) {
			GameObject go = it.getNext();
			go.draw(g, pCmpRelPrnt, pCmpRelScrn);
		}
		g.resetAffine();
	}
	*/

	public void paint(Graphics g) {
		// super.paint(g);
		updateVTM(viewWidth, viewHeight, viewL, viewB);
		Transform gXform = Transform.makeIdentity();
		g.getTransform(gXform);
		Transform oldOne = gXform.copy();
		// System.out.println("Y container: " + getY());
		gXform.translate(getX(), getY()); //+340
		gXform.concatenate(theVTM); // VTM xform
		// gXform.translate(-getAbsoluteX(), -getAbsoluteY());
		g.setTransform(gXform);
		Point pCmpRelPrnt = new Point(this.getX(), this.getY());
		Point pCmpRelScrn = new Point(getAbsoluteX(), getAbsoluteY());
		IIterator it = gw.getGameObjectsIterator();
		while (it.hasNext()) {
			GameObject go = it.getNext();
			go.draw(g, pCmpRelPrnt, pCmpRelScrn);
		}
		g.setColor(ColorUtil.GREEN);
		g.drawRect(10, 10, (int)gw.getWidth()-20, (int)gw.getHeight()-20);
		g.setTransform(oldOne);
		g.setColor(ColorUtil.BLUE);
		g.drawRect(10+getX(), 10+getY(), this.getWidth()-20, this.getHeight()-20);
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
	
	/*
	private Transform buildWorldToNDXform(float winWidth, float winHeight, float winLeft, float winBottom) {
		Transform tmpXfrom = Transform.makeIdentity();
		tmpXfrom.scale((float) (1.0 / winWidth), (float) (1.0 / winHeight));
		tmpXfrom.translate(-winLeft, -winBottom);
		return tmpXfrom;
	}

	private Transform buildNDToDisplayXform(float displayWidth, float displayHeight) {
		Transform tmpXfrom = Transform.makeIdentity();
		tmpXfrom.translate(0, displayHeight);
		tmpXfrom.scale(displayWidth, -displayHeight);
		return tmpXfrom;
	}
	*/

	public void zoom(float factor) {
		float newWidth = originalViewWidth * factor;
		float newHeight = originalViewHeight * factor;
		if(newWidth<0||newHeight<0||newWidth>8192||newHeight>4320) {
			return;
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
