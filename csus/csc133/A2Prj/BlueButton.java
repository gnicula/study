package com.mycompany.a2;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;


public class BlueButton extends Button {

	public BlueButton(String text) {
		super(text);
		String name = text;
		Style stl = getStyle();
		stl.setBgTransparency(255);
		stl.setBgColor(ColorUtil.BLUE);
		stl.setFgColor(ColorUtil.WHITE);
		stl.setBorder(Border.createBevelRaised());

		stl.setPadding(5, 5, 10, 10);
	}

	@Override
	protected Dimension calcPreferredSize() {
		Dimension d = super.calcPreferredSize();
		d.setWidth(d.getWidth() - 40);
		return d;
	}
}
