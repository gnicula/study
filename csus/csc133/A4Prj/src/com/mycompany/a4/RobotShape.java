package com.mycompany.a4;

import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Point;

public class RobotShape extends TransformedShape {
    private boolean isFilled;
    private int height, width;
    private int color;
    private int wheelAngle, earPos;
    WheelShape leftWheel, rightWheel;
    TriangleShape ear;

    RobotShape(int height, int width, int color) {
        this.height = height;
        this.width = width;
        this.color = color;
        isFilled = false;
        wheelAngle = 0;
        earPos = 0;
        leftWheel = new WheelShape(height/2, width/4, color);
        leftWheel.localXform.translate(-width/4, 0);
        rightWheel = new WheelShape(height/4, width/2, color);
        rightWheel.localXform.translate(+width/4, 0);
        subShape.add(leftWheel);
        subShape.add(rightWheel);
        ear = new TriangleShape(height/3, width/3, color);
        ear.localXform.translate(0, height/2);
        subShape.add(ear);
    }

    public void setFilled(boolean filled) {
        isFilled = filled;
        ear.setFilled(true);
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void rotateWheels(Point pCmpRelScrn) {
        wheelAngle += 20;
        wheelAngle %= 360;
        earPos += 2;
        if (earPos > width/2) {
            earPos = -width/2;
        }
        // System.out.println("Wheel angle: " + wheelAngle);
        subShape.clear();
        leftWheel = new WheelShape(height/2, width/4, color);
        leftWheel.setAngle(wheelAngle);
        leftWheel.localXform.translate(-width/4, 0);
        rightWheel = new WheelShape(height/4, width/2, color);
        rightWheel.setAngle(wheelAngle);
        rightWheel.localXform.translate(+width/4, 0);
        ear.localXform.translate(earPos / 10, 0);
        subShape.add(leftWheel);
        subShape.add(rightWheel);
        subShape.add(ear);
    }

    public void selfDraw(Graphics g) {
        g.setColor(color);
        if (isFilled) {
            g.fillRect(-width/2, 0, width, height/2);
        } else {
            g.drawRect(-width/2, 0, width, height/2);
        }
    }
}
