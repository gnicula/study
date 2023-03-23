package com.mycompany.a2;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;

public class Game extends Form {
	private GameWorld gw;
	private MapView mv;
	private ScoreView sv;
//	private Container con;

	public Game() {
		gw = new GameWorld();
		mv = new MapView();
		sv = new ScoreView();
//		con = new Container();
		createGUI();
		gw.init();
		gw.addObserver(sv);
		// play();
		this.show();
	}

	private void play() {
		Label myLabel = new Label("Enter a Command:");
		this.addComponent(myLabel);
		final TextField myTextField = new TextField();
		this.addComponent(myTextField);
		this.show();

		myTextField.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {

				String sCommand = myTextField.getText().toString();
				myTextField.clear();
				switch (sCommand.charAt(0)) {
				case 'a':
					// TODO tell game world to accelerate
					break;
				case 'b':
					// TODO tell game world to brake
					break;
				case 'l':
					// TODO tell robot to turn left

					break;
				case 'r':
					// TODO tell robot to turn right
					break;

				case 'm':
					gw.mCommand();

				case 'd':
					gw.dCommand();

				case 't':
					gw.tick();

				case 'x':
					gw.exit();
					break;
				// add code to handle rest of the commands
				} // switch
			} // actionPerformed
		} // new ActionListener()
		); // addActionListener
	} // play

	public void actionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub

	} // actionPerformed

	private void createGUI() {
		setLayout(new BorderLayout());
		// top Container with the GridLayout positioned on the north
		Container topContainer = sv;
		// Setting the Border Color
		topContainer.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.YELLOW));
		add(BorderLayout.NORTH, topContainer);
		// left Container with the BoxLayout positioned on the west
		createLeftContainer();
		// right Container with the GridLayout positioned on the east
		Container rightContainer = new Container(new GridLayout(4, 1));
		// ...[add similar components that exists on the left container]
		add(BorderLayout.EAST, rightContainer);
		// add empty container to the center
		Container centerContainer = new Container();
		// setting the back ground color of center container to light gray
		centerContainer.getAllStyles().setBgTransparency(255);
		centerContainer.getAllStyles().setBgColor(ColorUtil.LTGRAY);
		// setting the border Color
		centerContainer.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.MAGENTA));
		add(BorderLayout.CENTER, centerContainer);
		// bottom Container with the FlowLayout positioned on the south, components are
		// laid out
		// at the center
		Container bottomContainer = new Container(new FlowLayout(Component.CENTER));
		Button tick = new Button("Tick");
		tick.setCommand(new TickCommand("Tick", gw));
		bottomContainer.add(tick);
		// ...[add similar components that exists on the top container]
		add(BorderLayout.SOUTH, bottomContainer);
		/* Code for a form with a toolbar */
		createToolbar();// make sure to use lower-case "b“, setToolBar() is deprecated

	}

	private void createToolbar() {
		Toolbar myToolbar = new Toolbar();
		setToolbar(myToolbar);
		// add a text field to the title
		TextField myTF = new TextField("Robo-Track Game");
		myToolbar.setTitleComponent(myTF);
		// [or you can simply have a text in the title: this.setTitle("Adding Items to
		// Title Bar");]
		// add an “empty” item (which does not perform any operation) to side menu
		Command sideMenuItem1 = new AccelerateCommand("Accelerate", gw);
		myToolbar.addCommandToSideMenu(sideMenuItem1);
		this.addKeyListener('a', sideMenuItem1);
		// add an “empty” item to overflow menu
		Command overflowMenuItem1 = new Command("Overflow Menu Item 1");
		myToolbar.addCommandToOverflowMenu(overflowMenuItem1);
		// add an “empty” item to right side of title bar area
		Command titleBarAreaItem1 = new Command("Title Bar Area Item 1");
		myToolbar.addCommandToRightBar(titleBarAreaItem1);
		// add an “empty” item to left side of title bar area
		Command titleBarAreaItem2 = new Command("Title Bar Area Item 2");
		myToolbar.addCommandToLeftBar(titleBarAreaItem2);
		// ...[add other side menu, overflow menu, and/or title bar area items]
	}

	private void createLeftContainer() {
		Container leftContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		//start adding components at a location 50 pixels below the upper border of the container
		leftContainer.getAllStyles().setPadding(Component.TOP, 50);
		
		Button accel = new Button("Accelerate");
		accel.setCommand(new AccelerateCommand("Accelerate", gw));
		leftContainer.add(accel);
		
		Button left = new Button("Left");
		left.setCommand(new LeftCommand("Left", gw));
		leftContainer.add(left);
		
		Button strats = new Button("Change Strategies");
		strats.setCommand(new StrategiesCommand("Strategy", gw));
		leftContainer.add(strats);
		
		leftContainer.getAllStyles().setBorder(Border.createLineBorder(4,
		ColorUtil.BLUE));
		add(BorderLayout.WEST,leftContainer);
	}

	// new ActionListener()
// addActionListener
}
