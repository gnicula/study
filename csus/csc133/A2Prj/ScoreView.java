package com.mycompany.a2;

import java.util.Observable;
import java.util.Observer;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;

public class ScoreView extends Container implements Observer {
	// This class is the view class for displaying the game status.
	// Time, lives left, player last base reached, player energy level, damage
	// level, sound

	Label timeValue;
	Label lifeValue;
	Label lastBaseReachedValue;
	Label playerEnergyValue;
	Label playerDamageValue;
	Label soundSettingValue;

	public ScoreView() {
		createUiElements();
	}

	public void update(Observable o, Object arg) {
		GameWorld gw = (GameWorld) o;
		Robot playerRobot = gw.getPlayerRobot();
		String count = String.valueOf(gw.getCount());
		timeValue.setText(count);
		lifeValue.setText(String.valueOf(gw.getLivesLeft()));
		lastBaseReachedValue.setText(String.valueOf(playerRobot.getLastBaseReached()));
		playerEnergyValue.setText(String.valueOf(playerRobot.getEnergyLevel()));
		playerDamageValue.setText(String.valueOf(playerRobot.getDamageLevel()));
		soundSettingValue.setText(String.valueOf(gw.getSoundSetting()));
	}

	public void createUiElements() {
		this.setLayout(new FlowLayout(CENTER));
		Label timeText = new Label("Time:");
		this.add(timeText);
		timeValue = new Label("0");
		this.add(timeValue);
		Label lifeText = new Label("Lives left:");
		this.add(lifeText);
		lifeValue = new Label("0");
		this.add(lifeValue);
		Label lastBaseReachedText = new Label("Player Last Base Reached:");
		this.add(lastBaseReachedText);
		lastBaseReachedValue = new Label("1");
		this.add(lastBaseReachedValue);
		Label playerEnergyText = new Label("Player Energy Level:");
		this.add(playerEnergyText);
		playerEnergyValue = new Label("15");
		this.add(playerEnergyValue);
		Label playerDamageText = new Label("Player Damage Level:");
		this.add(playerDamageText);
		playerDamageValue = new Label("0");
		this.add(playerDamageValue);
		Label soundSettingText = new Label("Sound:");
		this.add(soundSettingText);
		soundSettingValue = new Label("OFF");
		this.add(soundSettingValue);
	}
}