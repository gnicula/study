package com.mycompany.a2;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;

public class Game extends Form {
	private GameWorld gw;
	private MapView mv;
	private ScoreView sv;


	public Game() {
		gw = new GameWorld();
		mv = new MapView();
		sv = new ScoreView();

		createGUI();
		gw.setDimensions(mv.getHeight(), mv.getWidth());
		gw.init();
		gw.addObserver(sv);
		gw.addObserver(mv);
		// play();
		this.show();
	}
	
	public void actionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub

	} // actionPerformed

	private void createGUI() {
		setLayout(new BorderLayout());
		Container topContainer = sv;
		topContainer.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.YELLOW));
		add(BorderLayout.NORTH, topContainer);
		createBottomContainer();
		createLeftContainer();
		createRightContainer();
		Container centerContainer = new Container();
		centerContainer.getAllStyles().setBgTransparency(255);
		centerContainer.getAllStyles().setBgColor(ColorUtil.LTGRAY);
		centerContainer.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.MAGENTA));
		add(BorderLayout.CENTER, centerContainer);
		createToolbar();

	}

	private void createToolbar() {
		Toolbar myToolbar = new Toolbar();
		setToolbar(myToolbar);
		
		TextField myTF = new TextField("Robo-Track Game");
		myToolbar.setTitleComponent(myTF);
		
		Command sideMenuItem1 = new AccelerateCommand("Accelerate", gw);
		myToolbar.addCommandToSideMenu(sideMenuItem1);
		this.addKeyListener('a', sideMenuItem1);

		CheckBox sideMenuItem2 = new CheckBox("Sound ON/OFF");
		sideMenuItem2.setCommand(new SoundCommand("Sound Command", gw));
		myToolbar.addComponentToSideMenu(sideMenuItem2);

		Command sideMenuItem3 = new AboutCommand("About", gw);
		myToolbar.addCommandToSideMenu(sideMenuItem3);
		
		Command sideMenuItem4 = Command.create("Exit", null, new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				gw.exit();
			} //actionPerformed
		}); //new ActionListener()
		myToolbar.addCommandToSideMenu(sideMenuItem4);
		
		Command overflowMenuItem1 = new Command("Overflow Menu Item 1");
		myToolbar.addCommandToOverflowMenu(overflowMenuItem1);
		Command helpCommand = new HelpCommand("Help", gw);
		myToolbar.addCommandToRightBar(helpCommand);
		 
	}

	private void createLeftContainer() {
		Container leftContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		leftContainer.getAllStyles().setPadding(Component.TOP, 100);
		
		BlueButton accel = new BlueButton("Accelerate");
		accel.setCommand(new AccelerateCommand("Accelerate", gw));
		leftContainer.add(accel);
		
		BlueButton left = new BlueButton("Left");
		Command leftCommand = new LeftCommand("Left", gw);
		left.setCommand(leftCommand);
		this.addKeyListener('l', leftCommand);
		leftContainer.add(left);
		
		BlueButton strats = new BlueButton("Change Strategies");
		strats.setCommand(new StrategiesCommand("Strategy", gw));
		leftContainer.add(strats);
		
		leftContainer.getAllStyles().setBorder(Border.createLineBorder(4,
		ColorUtil.BLUE));
		add(BorderLayout.WEST,leftContainer);
	}
	
	private void createRightContainer() {
		Container rightContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		rightContainer.getAllStyles().setPadding(Component.TOP, 100);
		
		BlueButton brake = new BlueButton("Brake");
		Command brakeCommand = new BrakeCommand("Brake", gw);
		brake.setCommand(brakeCommand);
		this.addKeyListener('b', brakeCommand);
		rightContainer.add(brake);
		
		BlueButton right = new BlueButton("Right");
		Command rightCommand = new RightCommand("Right", gw);
		right.setCommand(rightCommand);
		this.addKeyListener('r', rightCommand);
		rightContainer.add(right);
			
		rightContainer.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.BLUE));
		add(BorderLayout.EAST, rightContainer);
	}
	
	private void createBottomContainer() {
		Container bottomContainer = new Container(new FlowLayout(Component.CENTER));
		
		BlueButton collideWithNPR = new BlueButton("Collide With NPR");
		collideWithNPR.setCommand(new CollideWithNPRCommand("Collided with NPR", gw));
		bottomContainer.add(collideWithNPR);
		
		BlueButton collideWithBase = new BlueButton("Collide with Base");
		collideWithBase.setCommand(new CollideWithBaseCommand("Collided with Base", gw));
		bottomContainer.add(collideWithBase);
		
		BlueButton collideWithEnergyStation = new BlueButton("Collide with Energy Station");
		Command collideWithEnergyStationCommand = new CollideWithEnergyStationCommand("Collided with Energy Station", gw);
		collideWithEnergyStation.setCommand(collideWithEnergyStationCommand);
		this.addKeyListener('e', collideWithEnergyStationCommand);
		bottomContainer.add(collideWithEnergyStation);
		
		BlueButton collideWithDrone = new BlueButton("Collide with Drone");
		Command collideWithDroneCommand = new CollideWithDroneCommand("Collided with Drone", gw);
		collideWithDrone.setCommand(collideWithDroneCommand);
		this.addKeyListener('g', collideWithDroneCommand);
		bottomContainer.add(collideWithDrone);		
		
		BlueButton tick = new BlueButton("Tick");
		Command tickCommand = new TickCommand("Tick", gw);
		tick.setCommand(tickCommand);
		this.addKeyListener('t', tickCommand);
		bottomContainer.add(tick);
		
		add(BorderLayout.SOUTH, bottomContainer);
	}

	public static int createBaseCollisionDialog() {
		Command cOk = new Command("Confirm");
		Command cCancel = new Command("Cancel");
		Command[] cmds = new Command[]{cOk, cCancel};
		TextField myTF = new TextField();
		Command c = Dialog.show("Enter Base number:", myTF, cmds);
		int baseNum = 0;
		if (c == cOk) {
			baseNum = Integer.parseInt(myTF.getText());
		} 
		return baseNum;
	}

	// new ActionListener()
// addActionListener
}
