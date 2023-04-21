package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.FlowLayout;

	//This class is the view class for displaying the game status.
	// Time, lives left, player last base reached, player energy level, damage
	// level, sound
public class ScoreView extends Container implements Observer {
	

	private Label timeValue;
	private Label lifeValue;
	private Label lastBaseReachedValue;
	private Label playerEnergyValue;
	private Label playerDamageValue;
	private Label soundSettingValue;
	
	public ScoreView(GameWorld gw) {
		super();
		int timeStep = gw.getCount();
		int livesLeft = gw.getRobotInitialLives();
		int lastBase = 1; //playerRobot.getLastBaseReached();
		int energyValue = gw.getRobotInitialEnergy();
		int damageValue = 0; //playerRobot.getDamageLevel();
		boolean soundSetting = gw.getSoundSetting();
		createUiElements(timeStep, livesLeft, lastBase, energyValue, damageValue, soundSetting);
	}

	// Updates passed Observable object with new values.
	public void update(Observable o, Object arg) {
		GameWorld gw = (GameWorld) o;
		Robot playerRobot = gw.getPlayerRobot();
		String count = String.valueOf(gw.getCount());
		String livesLeft = String.valueOf(gw.getLivesLeft());
		timeValue.setText(count);
		lifeValue.setText(livesLeft);
		lastBaseReachedValue.setText(String.valueOf(playerRobot.getLastBaseReached()));
		playerEnergyValue.setText(String.valueOf(playerRobot.getEnergyLevel()));
		playerDamageValue.setText(String.valueOf(playerRobot.getDamageLevel()));
		boolean soundON = gw.getSoundSetting();
		String soundLabelText = "ON";
		if (!soundON) {
			soundLabelText = "OFF";
		}
		soundSettingValue.setText(soundLabelText);
		
		if (gw.getLivesLeft() <= 0) {
			System.out.println("Game over, you failed!");
			gw.exit();
		}
		
	}

	// Creates the ScoreView UI to display related information.
	public void createUiElements(int timeStep, int livesLeft, int lastBase, int energyValue, int damageValue, boolean soundSetting) {
		FlowLayout myLayout = new FlowLayout(LEFT);
		setLayout(myLayout);

		Label timeText = new Label("Time:");
		timeText.getAllStyles().setFgColor(ColorUtil.argb(255, 0, 0, 200));
		this.add(timeText);
		timeValue = new Label("00" + String.valueOf(timeStep));
		timeValue.getAllStyles().setFgColor(ColorUtil.argb(255, 0, 0, 200));
		this.add(timeValue);
		
		Label lifeText = new Label("Lives left:");
		lifeText.getAllStyles().setFgColor(ColorUtil.argb(255, 0, 0, 200));
		this.add(lifeText);
		lifeValue = new Label(String.valueOf(livesLeft));
		lifeValue.getAllStyles().setFgColor(ColorUtil.argb(255, 0, 0, 200));
		this.add(lifeValue);

		Label lastBaseReachedText = new Label("Player Last Base Reached:");
		lastBaseReachedText.getAllStyles().setFgColor(ColorUtil.argb(255, 0, 0, 200));
		this.add(lastBaseReachedText);
		lastBaseReachedValue = new Label(String.valueOf(lastBase));
		lastBaseReachedValue.getAllStyles().setFgColor(ColorUtil.argb(255, 0, 0, 200));
		this.add(lastBaseReachedValue);
		
		Label playerEnergyText = new Label("Player Energy Level:");
		playerEnergyText.getAllStyles().setFgColor(ColorUtil.argb(255, 0, 0, 200));
		this.add(playerEnergyText);
		playerEnergyValue = new Label(String.valueOf(energyValue));
		playerEnergyValue.getAllStyles().setFgColor(ColorUtil.argb(255, 0, 0, 200));
		this.add(playerEnergyValue);
		
		Label playerDamageText = new Label("Player Damage Level:");
		playerDamageText.getAllStyles().setFgColor(ColorUtil.argb(255, 0, 0, 200));
		this.add(playerDamageText);
		playerDamageValue = new Label(String.valueOf(damageValue));
		playerDamageValue.getAllStyles().setFgColor(ColorUtil.argb(255, 0, 0, 200));
		this.add(playerDamageValue);
		
		Label soundSettingText = new Label("Sound:");
		soundSettingText.getAllStyles().setFgColor(ColorUtil.argb(255, 0, 0, 200));
		this.add(soundSettingText);
		soundSettingValue = new Label(soundSetting ? "ON" : "OFF");
		soundSettingValue.getAllStyles().setFgColor(ColorUtil.argb(255, 0, 0, 200));
		this.add(soundSettingValue);

		// Tell layout to use all space available?
		myLayout.setFillRows(true);
	}
}