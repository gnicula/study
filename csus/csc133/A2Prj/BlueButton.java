package com.mycompany.a2;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.Font;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;

// Custom Button class which defines how the 
// buttons in the left, right, and bottom 
// containers are styled
public class BlueButton extends Button {

	private void setBlueButtonUnselectedStyle() {
		Style stl = getUnselectedStyle();
		stl.setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
		stl.setBgTransparency(255);
		stl.setBgColor(ColorUtil.BLUE);
		stl.setFgColor(ColorUtil.WHITE);
		stl.setBorder(Border.createBevelRaised());
		stl.setPadding(5, 5, 5, 5);
	}
	
	private void setBlueButtonSelectedStyle() {
		Style stl = getSelectedStyle();
		stl.setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
		stl.setBgTransparency(255);
		stl.setBorder(Border.createBevelRaised());
		stl.setPadding(5, 5, 5, 5);
	}
	
	public BlueButton(String text) {
		super(text);
		setBlueButtonUnselectedStyle();
		setBlueButtonSelectedStyle();
	}

//	@Override
//	protected Dimension calcPreferredSize() {
//		Dimension d = super.calcPreferredSize();
//		d.setWidth(d.getWidth() - 10);
//		return d;
//	}
}
