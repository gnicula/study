package com.mycompany.a4;

import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Point;

public class ShockWave extends Movable {
    private WaveShape myWave;
    private int ticks = 0;

    public ShockWave(int size, double x, double y, int color, int speed, int heading, GameWorld world) {
        super(size, x, y, color, speed, heading, world);
        myWave = new WaveShape(size, color);
    }

    public boolean isSpent() {
        return ticks > 200;
    }

    @Override
    public void move(int tickTime) {
        // calculate the new position of the drone based on its speed and heading
        double dx = this.getSpeed() * Math.cos(Math.toRadians(90 - this.getHeading()));
        double dy = this.getSpeed() * Math.sin(Math.toRadians(90 - this.getHeading()));
        double newX = this.getX() + dx * tickTime / 1000;
        double newY = this.getY() + dy * tickTime / 1000;

        this.setX(newX);
        this.setY(newY);

        ++ticks;
    }

    // ShockWave collision has no effect.
    @Override
    public void handleCollision(GameObject otherObject) {
        if (!collidingWith.contains(otherObject)) {
            collidingWith.add(otherObject);
            // Make sure the other object doesn't handle this collision.
            if (!otherObject.collidingWith.contains(this)) {
                otherObject.collidingWith.add(this);
            }
        }
    }

    public void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn) {
        // Do not forget to do “local origin” transformations.
        // ORDER of LTs: Scaling LT will be applied to coordinates FIRST,
        // then Translation LT, and lastly Rotation LT.
        // Also restore the xform at the end of draw() to remove this sub-shape’s LTs
        // from xform of the Graphics object. Otherwise, we would also
        // apply these LTs to the next sub-shape since it also uses the same Graphics
        // object.
        Transform gXform = Transform.makeIdentity();
        g.getTransform(gXform);
        Transform gOrigXform = gXform.copy(); // save the original xform
        // gXform.translate(pCmpRelScrn.getX(),pCmpRelScrn.getY());
        gXform.concatenate(myRotate); // Rotation is LAST
        // gXform.translate(myTranslate.getTranslateX(), myTranslate.getTranslateY());
        gXform.translate((float) getX(), (float) getY());
        gXform.scale(myScale.getScaleX(), myScale.getScaleY());
        // gXform.translate(-pCmpRelScrn.getX(),-pCmpRelScrn.getY());
        g.setTransform(gXform);
        // draw the lines as before
        myWave.draw(g);
        // g.drawLine(pCmpRelPrnt.getX()+top.getX(), pCmpRelPrnt.getY()+top.getY(),
        // pCmpRelPrnt.getX() + bottomLeft.getX(),pCmpRelPrnt.getY() +
        // bottomLeft.getY());
        // ...[draw the rest of the lines]
        g.setTransform(gOrigXform); // restore the original xform (remove LTs)
        // do not use resetAffine() in draw()! Instead use
        // getTransform()/setTransform(gOrigForm)
    }

    @Override
    public String toString() {
        String parentDesc = super.toString();
        String myDesc = "ShockWave: " + parentDesc;
        return myDesc;
    }
}
