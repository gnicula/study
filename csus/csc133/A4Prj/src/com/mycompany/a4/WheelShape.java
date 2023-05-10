package com.mycompany.a4;

import com.codename1.ui.Graphics;

public class WheelShape extends EllipseShape {

    WheelShape(int height, int width, int color) {
        super(height, width, color);
    }

    @Override
    public void selfDraw(Graphics g) {
        g.setColor(color);
        if (isFilled) {
            g.fillArc(-width/2, -height/2, width, height, angle, 270);
        } else {
            g.drawArc(-width/2, -height/2, width, height, angle, 270);
        }
    }

}
