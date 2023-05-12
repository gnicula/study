package com.mycompany.a4;

import java.util.ArrayList;
import java.util.Random;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class WaveShape extends TransformedShape {
    ArrayList<Point> controlPointArray = new ArrayList<Point>();
    private int color;

    WaveShape(int size, int color) {
        Random myRand = new Random();
        for (int i=0; i<=3; i++) {
            Point p = new Point(myRand.nextInt(size) - size/2, myRand.nextInt(size) - size/2);
            controlPointArray.add(p);
        }
        this.color = color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void selfDraw(Graphics g) {
        g.setColor(color);
        drawBezierCurve(g);
    }

    private double blendingFunction(int i, double t) {
        switch (i) {
            case 0: return ( (1-t) * (1-t) * (1-t) ) ; // (1-t)3
            case 1: return ( 3 * t * (1-t) * (1-t) ) ; // 3t(1-t)2
            case 2: return ( 3 * t * t * (1-t) ) ; // 3t2(1-t)
            case 3: return ( t * t * t ) ; // t3
        }
        return t * t * t;
    }

    void drawBezierCurve (Graphics g) {
         // start drawing at first control point
        Point currentPoint = controlPointArray.get(0);
         // vary the parametric value "t" over the length of the curve
        double t = 0 ;
        while (t <= 1) {
            // compute next point on the curve as the sum of the Control Points, each
            // weighted by the appropriate polynomial evaluated at ‘t’.
            Point nextPoint = new Point(0, 0);
            for (int i=0; i<=3; i++) {
                nextPoint.setX(nextPoint.getX() + 
                    (int)(blendingFunction(i,t) * controlPointArray.get(i).getX()));
                nextPoint.setY(nextPoint.getY() + 
                    (int)(blendingFunction(i,t) * controlPointArray.get(i).getY()));
            }
            g.drawLine(currentPoint.getX(), currentPoint.getY(), 
                nextPoint.getX(), nextPoint.getY());
            currentPoint = nextPoint;
            t = t + 0.1;
        }
    }
        
}
