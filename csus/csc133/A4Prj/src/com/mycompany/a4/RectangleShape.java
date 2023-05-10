package com.mycompany.a4;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public class RectangleShape extends TransformedShape {
    private boolean isFilled;
    private int height, width;
    private int color;
    private int upperLeftX, upperLeftY;

    RectangleShape(int height, int width, int color) {
        this.height = height;
        this.width = width;
        this.color = color;        
        isFilled = false;
        upperLeftX = -width/2;
        upperLeftY = -height/2;
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
            g.fillRect(upperLeftX, upperLeftY, width, height);
            // g.fillRect(upperLeftX+1, upperLeftY+1, width-2, height-2);
        } else {
            g.drawRect(upperLeftX, upperLeftY, width, height);
            g.drawRect(upperLeftX+1, upperLeftY+1, width-2, height-2);
        }
    }
}
