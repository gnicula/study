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

    // Returns true when ShockWave must be removed from GameWorld.
    public boolean isSpent() {
        return ticks > 200;
    }

    @Override
    public void move(int tickTime) {
        // calculate the new position of the ShockWave based on its speed and heading
        double dx = this.getSpeed() * Math.cos(Math.toRadians(90 - this.getHeading()));
        double dy = this.getSpeed() * Math.sin(Math.toRadians(90 - this.getHeading()));
        double newX = this.getX() + dx * tickTime / 1000;
        double newY = this.getY() + dy * tickTime / 1000;

        this.setXY(newX, newY);

        // ShockWaves have a lifetime based on ticks.
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
        Transform gXform = Transform.makeIdentity();
        g.getTransform(gXform);
        Transform gOrigXform = gXform.copy(); // save the original xform
        gXform.concatenate(myRotate); // Rotation is LAST
        gXform.translate((float) getX(), (float) getY());
        gXform.scale(myScale.getScaleX(), myScale.getScaleY());
        g.setTransform(gXform);

        myWave.draw(g);
        g.setTransform(gOrigXform); // restore the original xform (remove LTs)
        // do not use resetAffine() in draw()! Instead use
    }

    @Override
    public String toString() {
        String parentDesc = super.toString();
        String myDesc = "ShockWave: " + parentDesc;
        return myDesc;
    }
}
