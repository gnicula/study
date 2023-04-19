package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Font;
import com.codename1.ui.RadioButton;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;

public class PositionBlueButton extends RadioButton{

	public PositionBlueButton(String text) {
		super(text);
		setBlueButtonUnselectedStyle();
		setBlueButtonSelectedStyle();
	} 
	
	private void setPositionBlueButtonUnselectedStyle() {
		Style stl = getUnselectedStyle();
		stl.setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
		stl.setBgTransparency(255);
		stl.setBgColor(ColorUtil.BLUE);
		stl.setFgColor(ColorUtil.WHITE);
		stl.setBorder(Border.createBevelRaised());
		stl.setPadding(5, 5, 5, 5);
	}

	private void setPositionBlueButtonSelectedStyle() {
		Style stl = getSelectedStyle();
		stl.setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
		stl.setBgTransparency(255);
		stl.setBgColor(ColorUtil.WHITE);
		stl.setFgColor(ColorUtil.WHITE);
		stl.setBorder(Border.createBevelRaised());
		stl.setPadding(5, 5, 5, 5);
	}

	private void setBlueButtonSelectedStyle() {
		// TODO Auto-generated method stub
		
	}

	private void setBlueButtonUnselectedStyle() {
		// TODO Auto-generated method stub
		
	}

}
