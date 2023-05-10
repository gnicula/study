package com.mycompany.a4;

import java.util.Vector;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;

public abstract class TransformedShape {
    public Transform localXform = Transform.makeIdentity();
    public Vector<TransformedShape> subShape = new Vector<TransformedShape>();
    protected String text=null;
    Font textFont = null;

    public void selfDraw(Graphics g) {
    };

    public void draw(Graphics g) {
        Transform xForm = Transform.makeIdentity();
        Transform oldOne = Transform.makeIdentity();
        g.getTransform(oldOne);
        g.getTransform(xForm);
        xForm.concatenate(localXform);
        g.setTransform(xForm);
        for (TransformedShape shape : subShape) {
            shape.draw(g);
        }
        selfDraw(g);
        if (text != null) {
            // g.setFont(textFont);
            g.setColor(ColorUtil.BLACK);
            g.scale(1, -1);
            g.drawString(text, -GameObject.STRING_OFFSET, 2*GameObject.STRING_OFFSET);
        }
        g.setTransform(oldOne);
    }

    public void setText(String text) {
        this.text = text;
        this.textFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
    }

}
