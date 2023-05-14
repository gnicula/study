package com.mycompany.a4;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
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

	private final int TICK_TIME = 20;

	private GameWorld gw;
	private MapView mv;
	private ScoreView sv;
	// New in A3, use UITimer to tick() the game world.
	private UITimer gameTimer;
	private int elapsedTime = 0;

	private Command debugMapCommand;
	private Command accelerateCommand;
	private BlueButton accelerateButton;
	private BlueButton brakeButton;
	private BlueButton leftButton;
	private BlueButton rightButton;
	
	private BlueButton pauseGameButton;
	private BlueButton positionButton;
	private BlueButton tickDebugButton;

	// Constructor of Game creates and initializes the model and
	// the views (MapView and ScoreView) and attaches them as
	// observers to the model.
	public Game() {
		gw = new GameWorld();
		mv = new MapView(gw);
		sv = new ScoreView(gw);

		createGUI();
		this.show();
		// Set GameWorld dimension smaller than the view.
		gw.setDimensions(mv.getInnerWidth() / 2, mv.getInnerHeight() / 2);
		mv.resetViewWindow();
		gw.addObserver(sv);
		gw.addObserver(mv);
		this.repaint();

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
			// Disable key listeners
			this.removeKeyListener('a', accelerateCommand);
			this.removeKeyListener('b', brakeButton.getCommand());
			this.removeKeyListener('l', leftButton.getCommand());
			this.removeKeyListener('r', rightButton.getCommand());
			
			gameTimer.cancel();
		} else {
			// Re-enable key listeners
			this.addKeyListener('a', accelerateCommand);
			this.addKeyListener('b', brakeButton.getCommand());
			this.addKeyListener('l', leftButton.getCommand());
			this.addKeyListener('r', rightButton.getCommand());
			
			gameTimer.schedule(TICK_TIME, true, this);
		}
		// Enable/Disable menu item commands as per A3 assignment
		accelerateCommand.setEnabled(!isPaused);
		// Enable/Disable Buttons as per A3 assignment
		accelerateButton.setEnabled(!isPaused);
		brakeButton.setEnabled(!isPaused);
		leftButton.setEnabled(!isPaused);
		rightButton.setEnabled(!isPaused);
		
		String buttonText = isPaused ? "Play" : "Pause";
		pauseGameButton.setText(buttonText);
		positionButton.getCommand().setEnabled(isPaused);
		positionButton.setEnabled(isPaused);
	}

	// Creates all the GUI elements.
	private void createGUI() {
		createToolbar();
		setLayout(new BorderLayout());
		Container topContainer = sv;
		topContainer.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.LTGRAY));
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
	}

	// Creates a toolbar with a menu and labels as defined in the assignment.
	private void createToolbar() {
		Toolbar myToolbar = new Toolbar();
		setToolbar(myToolbar);

		Label myTF = new Label("Robo-Track Game");
		myTF.getAllStyles().setFgColor(ColorUtil.argb(255, 0, 0, 0));
		myToolbar.setTitleComponent(myTF);
		
		// Accelerate command is initialized by the left container
		accelerateCommand = new AccelerateCommand("Accelerate", gw);
		Command sideMenuItem1 = accelerateCommand;
		myToolbar.addCommandToSideMenu(sideMenuItem1);

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
//		debugMapCommand = new DebugMapViewCommand("Activate DEBUG tick", this);
//		// Change to 'true' to activate debug cmd in overflow menu.
//		debugMapCommand.setEnabled(false);
//		myToolbar.addCommandToOverflowMenu(debugMapCommand);
	}

	// Left container has three buttons in a vertical BoxLayout.
	private void createLeftContainer() {
		Container leftContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		leftContainer.getAllStyles().setPadding(Component.TOP, 100);
		
		accelerateButton = new BlueButton("Accelerate");
		accelerateButton.setCommand(accelerateCommand);
		this.addKeyListener('a', accelerateCommand);
		leftContainer.add(accelerateButton);
		
		leftButton = new BlueButton("Left");
		Command leftCommand = new LeftCommand("Left", gw);
		leftButton.setCommand(leftCommand);
		this.addKeyListener('l', leftCommand);
		leftContainer.add(leftButton);
		
		BlueButton strats = new BlueButton("Change Strategies");
		strats.setCommand(new StrategiesCommand("Strategy", gw));
		leftContainer.add(strats);
		
		leftContainer.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.BLUE));
		add(BorderLayout.WEST,leftContainer);
	}
	
	// Right container has two buttons in a vertical BoxLayout.
	private void createRightContainer() {
		Container rightContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		rightContainer.getAllStyles().setPadding(Component.TOP, 100);
		
		brakeButton = new BlueButton("Brake");
		Command brakeCommand = new BrakeCommand("Brake", gw);
		brakeButton.setCommand(brakeCommand);
		this.addKeyListener('b', brakeCommand);
		rightContainer.add(brakeButton);
		
		rightButton = new BlueButton("Right");
		Command rightCommand = new RightCommand("Right", gw);
		rightButton.setCommand(rightCommand);
		this.addKeyListener('r', rightCommand);
		rightContainer.add(rightButton);
			
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
	
	// NOT part of A3 assignment, can be used to debug by running
	// step by step. It works only when the game is not paused.
	public void enableDebugTicks(boolean enable) {
		if (!gw.getPaused() ) {
			tickDebugButton.setHidden(!enable);
			tickDebugButton.getParent().animateLayout(200);
			if (enable) {
				// Stop timer
				gameTimer.cancel();
			} else {
				gameTimer.schedule(TICK_TIME, true, this);
			}
		}
	}
}
