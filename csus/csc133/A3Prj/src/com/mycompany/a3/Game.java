package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.ComponentImage;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.UITimer;

// Game class acts like a view for the GameWorld model.
// It creates all the GUI components and arranges them 
// in a BorderLayout with 5 areas.
// It also creates the toolbar and the side menu.
public class Game extends Form implements Runnable {

	private final int TICK_TIME = 50;

	private GameWorld gw;
	private MapView mv;
	private ScoreView sv;
	// New in A3, use UITimer to tick() the game world.
	private UITimer gameTimer;
	private int elapsedTime = 0;

	private BlueButton pauseGameButton;
	private BlueButton positionButton;
	private BlueButton tickDebugButton;

	// Constructor of Game creates and initializes the model and
	// the views (MapView and ScoreView) and attaches them as
	// observers to the model.
	public Game() {
		gw = new GameWorld();
		gw.init();
		mv = new MapView(gw);
		// Sending model for the initial values.
		sv = new ScoreView(gw);

		createGUI();
		this.show();
		// NOTE: setDimensions enables GameWorld to place initial 
		// GameObjects in a nice way, relative to MapView's dimensions.
		gw.setDimensions(mv.getWidth(), mv.getHeight());
		// gw.setDimensions(1000, 1000);
		gw.addObserver(sv);
		gw.addObserver(mv);

		// create the timer and start ticking
		gameTimer = new UITimer(this);
		gameTimer.schedule(TICK_TIME, true, this);
		// create the sound objects for the game
		gw.createSounds();
		revalidate();
	}

	// Implement Runnable.run()
	// Tick
	@Override
	public void run()
	{
		gw.tick(TICK_TIME);
		elapsedTime += TICK_TIME;		
	}
	
	public void handlePause() {
		gw.pause();
		boolean isPaused = gw.getPaused();
		if (isPaused) {
			gameTimer.cancel();
		} else {
			gameTimer.schedule(TICK_TIME, true, this);
		}
		String buttonText = isPaused ? "Play" : "Pause";
		pauseGameButton.setText(buttonText);
		positionButton.getCommand().setEnabled(isPaused);
		positionButton.setEnabled(isPaused);
		// TODO: Draw the button disabled/enabled
	}

	// Creates all the GUI elements.
	private void createGUI() {
		setLayout(new BorderLayout());
		Container topContainer = sv;
		topContainer.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.LTGRAY));
		add(BorderLayout.NORTH, topContainer);
		createBottomContainer();
		createLeftContainer();
		createRightContainer();
		Container centerContainer = mv;
		centerContainer.getAllStyles().setBgTransparency(255);
		// centerContainer.getAllStyles().setBgColor(ColorUtil.LTGRAY);
		centerContainer.getAllStyles().setBorder(
				Border.createLineBorder(2, ColorUtil.rgb(255, 0, 0)));
		add(BorderLayout.CENTER, centerContainer);
		createToolbar();
	}

	// Creates a toolbar with a menu and labels as defined in the assignment.
	private void createToolbar() {
		Toolbar myToolbar = new Toolbar();
		setToolbar(myToolbar);
		
		Label myTF = new Label("Robo-Track Game");
		myTF.getAllStyles().setFgColor(ColorUtil.argb(255, 0, 0, 0));
		myToolbar.setTitleComponent(myTF);
		
		Command sideMenuItem1 = new AccelerateCommand("Accelerate", gw);
		myToolbar.addCommandToSideMenu(sideMenuItem1);
		this.addKeyListener('a', sideMenuItem1);

		CheckBox sideMenuItem2 = new CheckBox("Sound ON/OFF");
		sideMenuItem2.setCommand(new SoundCommand("Sound Command", gw));
		myToolbar.addComponentToSideMenu(sideMenuItem2);

		Command sideMenuItem3 = new AboutCommand("About", gw);
		myToolbar.addCommandToSideMenu(sideMenuItem3);
		
		Command sideMenuItem4 = new ExitDialogCommand("Exit", gw);
		myToolbar.addCommandToSideMenu(sideMenuItem4);
		
		Command helpCommand = new HelpCommand("Help", gw);
		myToolbar.addCommandToRightBar(helpCommand);

		// DEBUG view of components
		Command overflowMenuItem1 = new DebugMapViewCommand("Activate DEBUG tick", this);
		myToolbar.addCommandToOverflowMenu(overflowMenuItem1);
	}

	// Left container has three buttons in a vertical BoxLayout.
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
	
	// Right container has two buttons in a vertical BoxLayout.
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
	
	// Bottom container has the collision simulation buttons as well the tick button.
	// I used FlowLayout and custom buttons trying to fit the text of all the buttons.
	private void createBottomContainer() {
		Container bottomContainer = new Container(new FlowLayout(Component.CENTER));

		positionButton = new BlueButton("Position");
		positionButton.setCommand(new PositionCommand("Position", gw));
		positionButton.getCommand().setEnabled(false);
		positionButton.setEnabled(false);
		bottomContainer.add(positionButton);
		// TODO: Draw the button disabled

		pauseGameButton = new BlueButton("Pause");
		pauseGameButton.setCommand(new PauseCommand("Pause", this));
		bottomContainer.add(pauseGameButton);
		
		tickDebugButton = new BlueButton("Tick");
		Command tickCommand = new TickCommand("Tick", gw);
		tickDebugButton.setCommand(tickCommand);
		tickDebugButton.setHidden(true);
//		this.addKeyListener('t', tickCommand);
		bottomContainer.add(tickDebugButton);
		
		add(BorderLayout.SOUTH, bottomContainer);
	}
	
	public void enableDebugTicks(boolean enable) {
		tickDebugButton.setHidden(!enable);
		tickDebugButton.getParent().animateLayout(200);
	}
}
