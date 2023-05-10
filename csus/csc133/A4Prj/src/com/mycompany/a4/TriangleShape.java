package com.mycompany.a4;

import com.codename1.ui.Graphics;

public class TriangleShape extends TransformedShape {
    private boolean isFilled;
    private int height, base;
    private int[] xPt, yPt;
    private int color;

    TriangleShape(int height, int base, int color) {
        this.height = height;
        this.base = base;
        this.color = color;
        isFilled = false;
        xPt = new int[] { 0, base / 2, -base / 2 };
        yPt = new int[] { height / 2, -height / 2, -height / 2 };
    }

    public void setFilled(boolean filled) {
        isFilled = filled;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void selfDraw(Graphics g) {
        g.setColor(color);
        if (isFilled) {
            g.fillPolygon(xPt, yPt, 3);
        } else {
            g.drawPolygon(xPt, yPt, 3);
        }
    }
}
