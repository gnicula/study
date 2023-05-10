package com.mycompany.a4;

import com.codename1.ui.Graphics;

public class EllipseShape extends TransformedShape {
    protected boolean isFilled;
    protected int height, width;
    protected int angle;

    protected int color;

    EllipseShape(int height, int width, int color) {
        this.height = height;
        this.width = width;
        this.color = color;
        angle = 0;
        isFilled = false;
    }

    public void setFilled(boolean filled) {
        isFilled = filled;
    }

    public void setAngle(int whangle) {
        this.angle = whangle;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void selfDraw(Graphics g) {
        g.setColor(color);
        if (isFilled) {
            g.fillArc(-width/2, -height/2, width, height, angle, 360);
        } else {
            g.drawArc(-width/2, -height/2, width, height, angle, 360);
        }
    }

}
