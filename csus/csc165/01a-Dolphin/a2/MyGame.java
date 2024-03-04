/*
 * @author Gabriele Nicula
 */

package a2;

import tage.*;
import tage.shapes.*;
import tage.input.InputManager; // input management
import tage.input.action.*;
import tage.nodeControllers.RotationController;
import tage.nodeControllers.StretchController;
import net.java.games.input.Controller;

import java.util.ArrayList;
import java.lang.Math;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

import org.joml.*;


public class MyGame extends VariableFrameRateGame {
	private static Engine engine;

	private boolean paused = false;
	private boolean offDolphinCam = true; // default camera off dolphin
	private boolean axesRenderState = true;
	private boolean[] visitedSites = new boolean[4]; // default initialized to false
	private int counter = 0;
	private int frameCounter = 0;
	private double lastFrameTime, currFrameTime, elapsTime;

	private GameObject dol, cub, torus, sphere, plane, groundPlane, wAxisX, wAxisY, wAxisZ, manual, magnet;
	private ObjShape dolS, cubS, torusS, sphereS, planeS, groundPlaneS, wAxisLineShapeX, wAxisLineShapeY, 
			wAxisLineShapeZ, manualS, magnetS;
	private TextureImage doltx, brick, grass, corvette, assignt, gold, metal, water, torusWater;
	private Light light1, light2;
	private Camera myCamera, myViewportCamera;
	private CameraOrbit3D orbitController;
	private ArrayList<NodeController> controllerArr = new ArrayList<NodeController>();
	private InputManager inputManager;
	private Vector3f location; // world object location
	private Vector3f forward; // n-vector/z-axis
	private Vector3f up; // v-vector/y-axis
	private Vector3f right; // u-vector/x-axis

	private final int WindowSizeX = 2000;
	private final int WindowSizeY = 1000;

	public MyGame() {
		super();
	}

	public static void main(String[] args) {
		MyGame game = new MyGame();
		engine = new Engine(game);
		game.initializeSystem();
		game.game_loop();
	}

	@Override
	public void loadShapes() {
		dolS = new ImportedModel("dolphinHighPoly.obj");
		cubS = new Cube();
		torusS = new Torus();
		sphereS = new Sphere();
		planeS = new Plane();
		groundPlaneS = new Plane();
		manualS = new MyManualObject();
		magnetS = new MyMagnetObject();

		float lineLength = 1.0f;
		Vector3f worldOrigin = new Vector3f(0f, 0f, 0f);
		Vector3f lineX = new Vector3f(lineLength, 0f, 0f);
		Vector3f lineY = new Vector3f(0f, lineLength, 0f);
		Vector3f lineZ = new Vector3f(0f, 0f, -lineLength);

		wAxisLineShapeX = new Line(worldOrigin, lineX);
		wAxisLineShapeY = new Line(worldOrigin, lineY);
		wAxisLineShapeZ = new Line(worldOrigin, lineZ);
	}

	@Override
	public void loadTextures() {
		doltx = new TextureImage("Dolphin_HighPolyUV.png");
		brick = new TextureImage("brick1.jpg");
		grass = new TextureImage("grass1.jpg");
		corvette = new TextureImage("corvette1.jpg");
		assignt = new TextureImage("assign1.png");
		gold = new TextureImage("gold1.jpg");
		metal = new TextureImage("magnet1.jpg");
		// https://www.pexels.com/photo/body-of-water-261403/
		water = new TextureImage("water.jpg");
		// https://www.pexels.com/photo/aerial-shot-of-blue-water-3894157/
		torusWater = new TextureImage("waterTorus.jpg");
	}

	@Override
	public void buildObjects() {
		Matrix4f initialTranslation, initialScale;

		// build dolphin in the center of the window
		dol = new GameObject(GameObject.root(), dolS, doltx);
		initialTranslation = (new Matrix4f()).translation(0, 0.2f, 0);
		initialScale = (new Matrix4f()).scaling(0.75f);
		dol.setLocalTranslation(initialTranslation);
		dol.setLocalScale(initialScale);

		Matrix4f initialTranslationCub, initialScaleCub;
		// build a brick at the right side of the window
		cub = new GameObject(GameObject.root(), cubS, brick);
		initialTranslationCub = (new Matrix4f()).translation(4, 0.15f, -4);
		initialScaleCub = (new Matrix4f()).scaling(0.25f);
		cub.setLocalTranslation(initialTranslationCub);
		cub.setLocalScale(initialScaleCub);

		Matrix4f initialTranslationTorus, initialScaleTorus;
		// build a grass torus at the left side of the window
		torus = new GameObject(GameObject.root(), torusS, torusWater);
		torus.getRenderStates().setTiling(1);
		initialTranslationTorus = (new Matrix4f()).translation(-3, 0.11f, -4);
		initialScaleTorus = (new Matrix4f()).scaling(0.5f);
		torus.setLocalTranslation(initialTranslationTorus);
		torus.setLocalScale(initialScaleTorus);

		Matrix4f initialTranslationSphere, initialScaleSphere;
		// build a sphere logo textured at the right side of the window
		sphere = new GameObject(GameObject.root(), sphereS, corvette);
		initialTranslationSphere = (new Matrix4f()).translation(4, 0.2f, 4);
		initialScaleSphere = (new Matrix4f()).scaling(0.5f);
		sphere.setLocalTranslation(initialTranslationSphere);
		sphere.setLocalScale(initialScaleSphere);
		// now create a example of a hierarchical relationship by adding 
		// a smaller sphereSatellite to our sphere object.
		GameObject sphereSatellite = new GameObject(sphere, sphereS, grass);
		sphereSatellite.setLocalScale((new Matrix4f()).scaling(0.25f));
		sphereSatellite.setLocalTranslation((new Matrix4f()).translation(0.75f, 0.5f, 0));
		sphereSatellite.propagateTranslation(true);
		sphereSatellite.propagateRotation(true);
		sphereSatellite.applyParentRotationToPosition(true);

		Matrix4f initialTranslationPlane, initialScalePlane;
		// build a plane textured at the left side of the window
		plane = new GameObject(GameObject.root(), planeS, assignt);
		initialTranslationPlane = (new Matrix4f()).translation(-3, .01f, 4);
		initialScalePlane = (new Matrix4f()).scaling(0.75f);
		plane.setLocalTranslation(initialTranslationPlane);
		plane.setLocalScale(initialScalePlane);

		Matrix4f initialTranslationManual, initialScaleManual;
		// build my manual object
		manual = new GameObject(GameObject.root(), manualS, gold);
		initialTranslationManual = (new Matrix4f()).translation(-4.5f, 2.0f, 0);
		initialScaleManual = (new Matrix4f()).scaling(0.4f);
		manual.setLocalTranslation(initialTranslationManual);
		manual.setLocalScale(initialScaleManual);
		manual.getRenderStates().hasLighting(true);

		Matrix4f initialTranslationGround, initialScaleGround;
		// build the ground plane on X-Z
		groundPlane = new GameObject(GameObject.root(), groundPlaneS, water);
		// initialTranslationGround = (new Matrix4f()).translation(-4.5f, 2, 0);
		initialScaleGround = (new Matrix4f()).scaling(5.0f);
		// groundPlane.setLocalTranslation(initialTranslationManual);
		groundPlane.setLocalScale(initialScaleGround);
		groundPlane.getRenderStates().hasLighting(true);
		groundPlane.setIsTerrain(true);

		// Build World Axis Lines (X, Y, Z) in the center of the window
		wAxisX = new GameObject(GameObject.root(), wAxisLineShapeX);
		wAxisY = new GameObject(GameObject.root(), wAxisLineShapeY);
		wAxisZ = new GameObject(GameObject.root(), wAxisLineShapeZ);

		// Set world axis colors (red, green, blue) - X, Y, Z respectively
		wAxisX.getRenderStates().setColor(new Vector3f(1.0f, 0, 0));
		wAxisY.getRenderStates().setColor(new Vector3f(0, 1.0f, 0));
		wAxisZ.getRenderStates().setColor(new Vector3f(0, 0, 1.0f));

	}

	@Override
	public void initializeLights() {
		Light.setGlobalAmbient(0.5f, 0.5f, 0.5f);
		light1 = new Light();
		light1.setLocation(new Vector3f(5.0f, 4.0f, 2.0f));
		(engine.getSceneGraph()).addLight(light1);
		light2 = new Light();
		light2.setLocation(new Vector3f(-5.0f, 4.0f, -2.0f));
		(engine.getSceneGraph()).addLight(light2);
	}

	@Override
	public void createViewports() {
		(engine.getRenderSystem()).addViewport("MAIN",0,0,1f,1f);
		(engine.getRenderSystem()).addViewport("RIGHT", .75f, 0, .25f, .25f);

		Viewport rightVp = (engine.getRenderSystem()).getViewport("RIGHT");

		myViewportCamera = rightVp.getCamera();
		rightVp.setHasBorder(true);
		rightVp.setBorderWidth(4);
		rightVp.setBorderColor(0.0f, 1.0f, 0.0f);

		myViewportCamera.setLocation(new Vector3f(0, 2, 0));
		myViewportCamera.setU(new Vector3f(1, 0, 0));
		myViewportCamera.setV(new Vector3f(0, 0, -1));
		myViewportCamera.setN(new Vector3f(0, -1, 0));
	}

	@Override
	public void initializeGame() {
		currFrameTime = System.currentTimeMillis();
		lastFrameTime = currFrameTime;
		elapsTime = 0.0;
		(engine.getRenderSystem()).setWindowDimensions(WindowSizeX, WindowSizeY);

		inputManager = engine.getInputManager();
		String gamepadName = inputManager.getFirstGamepadName();
		// Get all our controllers and print their info: name, type
		ArrayList<Controller> controllers = inputManager.getControllers();
		for (Controller controller : controllers) {
			System.err.println("Controller: " + controller.getName());
			System.err.println("Type: " + controller.getType());
			// if (controller.getType().toString() == "Gamepad") {
			// 	gamepadName = controller.getName();
			// }
		}

		// ------------- positioning the camera -------------
		myCamera = engine.getRenderSystem().getViewport("MAIN").getCamera();
		myCamera.setLocation(new Vector3f(0, 0, 5.0f));

		// CameraOrbit3D initialization
		orbitController = new CameraOrbit3D(myCamera, dol, gamepadName, engine);
		// Initialize our nodeControllers
		NodeController rotController1 = new RotationController(engine, new Vector3f(0,1,0), 0.01f);
		rotController1.addTarget(cub);
		controllerArr.add(rotController1);
		NodeController stretchController1 = new StretchController(engine, 1.0f);
		stretchController1.addTarget(torus);
		controllerArr.add(stretchController1);
		NodeController rotController2 = new RotationController(engine, new Vector3f(0,1,0), 0.005f);
		rotController2.addTarget(sphere);
		controllerArr.add(rotController2);
		NodeController stretchController2 = new StretchController(engine, 1.0f);
		stretchController2.addTarget(plane);
		controllerArr.add(stretchController2);

		for (NodeController n : controllerArr) {
			(engine.getSceneGraph()).addNodeController(n);
		}

		PitchActionK pitchUp = new PitchActionK(this, 0.0002f);
		PitchActionK pitchDown = new PitchActionK(this, -0.0002f);
		PitchActionJ pitchJ = new PitchActionJ(this);
		ForwardBackActionK moveForward = new ForwardBackActionK(this, 0.0002f);
		ForwardBackActionK moveBackward = new ForwardBackActionK(this, -0.0002f);
		ForwardBackActionJ moveJ = new ForwardBackActionJ(this, 0.0002f);
		YawActionK leftYaw = new YawActionK(this, 1);
		YawActionK rightYaw = new YawActionK(this, -1);
		YawActionJ XYaw = new YawActionJ(this);
		RollActionK leftRoll = new RollActionK(this, -1);
		RollActionK rightRoll = new RollActionK(this, 1);

		// A2 New actions
		SecondaryViewportZoomActionK zoomOut = new SecondaryViewportZoomActionK(this, 0.0004f);
		SecondaryViewportZoomActionK zoomIn = new SecondaryViewportZoomActionK(this, -0.0004f);
		SecondaryViewportPanXActionK panLeft = new SecondaryViewportPanXActionK(this, -0.0004f);
		SecondaryViewportPanXActionK panRight = new SecondaryViewportPanXActionK(this, 0.0004f);
		SecondaryViewportPanYActionK panUp = new SecondaryViewportPanYActionK(this, -0.0004f);
		SecondaryViewportPanYActionK panDown = new SecondaryViewportPanYActionK(this, 0.0004f);

		// Bind keyboard keys W, S, A, D, Q, E, UP, DOWN to their actions
		inputManager.associateActionWithAllKeyboards(
				net.java.games.input.Component.Identifier.Key.UP,
				pitchUp,
				InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		inputManager.associateActionWithAllKeyboards(
				net.java.games.input.Component.Identifier.Key.DOWN,
				pitchDown,
				InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		inputManager.associateActionWithAllKeyboards(
				net.java.games.input.Component.Identifier.Key.W,
				moveForward,
				InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		inputManager.associateActionWithAllKeyboards(
				net.java.games.input.Component.Identifier.Key.S,
				moveBackward,
				InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		inputManager.associateActionWithAllKeyboards(
				net.java.games.input.Component.Identifier.Key.A,
				leftYaw,
				InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		inputManager.associateActionWithAllKeyboards(
				net.java.games.input.Component.Identifier.Key.D,
				rightYaw,
				InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		inputManager.associateActionWithAllKeyboards(
				net.java.games.input.Component.Identifier.Key.Q,
				leftRoll,
				InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		inputManager.associateActionWithAllKeyboards(
				net.java.games.input.Component.Identifier.Key.E,
				rightRoll,
				InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);

		//A2 New Actions
		inputManager.associateActionWithAllKeyboards(
				net.java.games.input.Component.Identifier.Key.LBRACKET,
				zoomOut,
				InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		
		inputManager.associateActionWithAllKeyboards(
				net.java.games.input.Component.Identifier.Key.RBRACKET,
				zoomIn,
				InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
			
		inputManager.associateActionWithAllKeyboards(
				net.java.games.input.Component.Identifier.Key.O,
				panLeft,
				InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
				
		inputManager.associateActionWithAllKeyboards(
				net.java.games.input.Component.Identifier.Key.P,
				panRight,
				InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		
		inputManager.associateActionWithAllKeyboards(
				net.java.games.input.Component.Identifier.Key._0,
				panUp,
				InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);

		inputManager.associateActionWithAllKeyboards(
				net.java.games.input.Component.Identifier.Key.L,
				panDown,
				InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);

		// Now bind X, Y, YRot to joystick/game controller
		inputManager.associateActionWithAllGamepads(
				net.java.games.input.Component.Identifier.Button._0,
				pitchUp, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		inputManager.associateActionWithAllGamepads(
				net.java.games.input.Component.Identifier.Button._1,
				pitchDown, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		inputManager.associateActionWithAllGamepads(
				net.java.games.input.Component.Identifier.Axis.RZ,
				pitchJ, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		inputManager.associateActionWithAllGamepads(
				net.java.games.input.Component.Identifier.Axis.X,
				XYaw, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		inputManager.associateActionWithAllGamepads(
				net.java.games.input.Component.Identifier.Axis.Y,
				moveJ, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
	}

	private float getFramesPerSecond() {
		return (float) (frameCounter / elapsTime);
	}

	private String printVector3f(Vector3f v) {
		return String.format("x:%2.3f, y:%2.3f, z:%2.3f", v.x(), v.y(), v.z());
	}

	private void arrangeHUD() {
		// build and set HUD
		int elapsTimeSec = Math.round((float) elapsTime);
		String elapsTimeStr = Integer.toString(elapsTimeSec);
		String counterStr = Integer.toString(counter);
		counterStr =  counter < 4 ? "Score = " + counterStr : "Score = " + counterStr + " You Win!";
		String dispStr1 = "Time = " + elapsTimeStr + " " + counterStr;
		String dispStr2 = "Pos = " + printVector3f(dol.getWorldLocation());
		Vector3f hud1Color = new Vector3f(1, 0, 0);
		Vector3f hud2Color = new Vector3f(0, 0, 1);
		int hud1x, hud1y, hud2x, hud2y;
		float mainViewportAbsoluteLeft = engine.getRenderSystem().getViewport("MAIN").getActualLeft();
		// System.out.println("actual left: " + mainViewportAbsoluteLeft);
		float mainViewportAbsoluteBottom = engine.getRenderSystem().getViewport("MAIN").getActualBottom();
		// System.out.println("actual bottom: " + mainViewportAbsoluteBottom);
		float secondaryViewportAbsoluteLeft = engine.getRenderSystem().getViewport("RIGHT").getActualLeft();
		// System.out.println("viewport2 actual left: " + secondaryViewportAbsoluteLeft);
		float secondaryViewportAbsoluteBottom = engine.getRenderSystem().getViewport("RIGHT").getActualBottom();
		// System.out.println("viewport2 actual bottom: " + secondaryViewportAbsoluteBottom);

		hud1x = (int)(mainViewportAbsoluteLeft) + 10; // - WindowSizeX / 2);
		hud1y = 10;
		(engine.getHUDmanager()).setHUD1(dispStr1, hud1Color, hud1x, hud1y);

		hud2x = (int)(secondaryViewportAbsoluteLeft) + 10; // - WindowSizeX / 2);
		hud2y = 10;
		(engine.getHUDmanager()).setHUD2(dispStr2, hud2Color, hud2x, hud2y);
	}

	@Override
	public void update() {
		lastFrameTime = currFrameTime;
		currFrameTime = System.currentTimeMillis();
		if (!paused) {
			elapsTime += (currFrameTime - lastFrameTime) / 1000.0;
		}

		arrangeHUD();
		inputManager.update(getFramesPerSecond());
		orbitController.updateCameraPosition();
		updateDolphinScore();
		toggleNodeControllers();
		frameCounter++;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		Vector3f loc, fwd, newLocation;
		switch (e.getKeyCode()) {
			// case KeyEvent.VK_C:
			// counter++;
			// AddMagnetToManualObject();
			// break;
			case KeyEvent.VK_1:
				axesRenderState = !axesRenderState;
				if (axesRenderState) {
					wAxisX.getRenderStates().enableRendering();
					wAxisY.getRenderStates().enableRendering();
					wAxisZ.getRenderStates().enableRendering();
				} else {
					wAxisX.getRenderStates().disableRendering();
					wAxisY.getRenderStates().disableRendering();
					wAxisZ.getRenderStates().disableRendering();
				}
				break;
			case KeyEvent.VK_2:
				dol.getRenderStates().setWireframe(true);
				break;
			case KeyEvent.VK_3:
				dol.getRenderStates().setWireframe(false);
				break;
			case KeyEvent.VK_4:
				(engine.getRenderSystem().getViewport("MAIN").getCamera()).setLocation(new Vector3f(0, 0, 0));
				offDolphinCam = true;
				break;
			// case KeyEvent.VK_SPACE:
			// 	if (offDolphinCam) {
			// 		setOnDolphinCam();
			// 	} else {
			// 		setOffDolphinCam();
			// 	}
			// 	break;
		}
		super.keyPressed(e);
	}

	public Camera getMyCamera() {
		return myCamera;
	}

	public Camera getMyViewportCamera() {
		return myViewportCamera;
	}

	public boolean onDolphinCam() {
		return !offDolphinCam;
	}

	public void setOnDolphinCam() {
		float hopOnDistance = -1.75f;
		float upDistance = 0.5f;
		location = dol.getWorldLocation();
		forward = dol.getWorldForwardVector();
		up = dol.getWorldUpVector();
		right = dol.getWorldRightVector();
		myCamera.setU(right);
		myCamera.setV(up);
		myCamera.setN(forward);
		myCamera.setLocation(location.add(up.mul(upDistance))
				.add(forward.mul(hopOnDistance)));

		offDolphinCam = false;
	}

	public void setOffDolphinCam() {
		float hopOffDistance = -2.0f;
		float upDistance = 1.0f;
		location = dol.getWorldLocation();
		forward = dol.getWorldForwardVector();
		up = dol.getWorldUpVector();
		right = dol.getWorldRightVector();
		myCamera.setU(right);
		myCamera.setV(up);
		myCamera.setN(forward);
		myCamera.setLocation(location.add(up.mul(upDistance))
				.add(forward.mul(hopOffDistance)));

		offDolphinCam = true;
	}

	public GameObject getDolphin() {
		return dol;
	}

	public void AddMagnetToManualObject(int n_magnet) {
		Matrix4f initialTranslationMagnet, initialScaleMagnet, initialRotationMagnet;
		// build the magnet object
		magnet = new GameObject(GameObject.root(), magnetS, metal);
		magnet.setParent(dol);
		magnet.propagateRotation(true);
		magnet.propagateTranslation(true);
		magnet.applyParentRotationToPosition(true);
		initialRotationMagnet = (new Matrix4f()).rotate(90.0f, new Vector3f(0.0f, 1.0f, 0.0f));
		initialTranslationMagnet = (new Matrix4f()).translation(
				0.0f, 0.02f, -(n_magnet * 0.035f + 0.35f));
		initialScaleMagnet = (new Matrix4f()).scaling(0.05f);
		magnet.setLocalRotation(initialRotationMagnet);
		magnet.setLocalTranslation(initialTranslationMagnet);
		magnet.setLocalScale(initialScaleMagnet);
		magnet.getRenderStates().hasLighting(true);
		
		NodeController magnetSpinController = new RotationController(engine, new Vector3f(0,1,0), 0.01f);
		magnetSpinController.addTarget(magnet);
		magnetSpinController.enable();
		engine.getSceneGraph().addNodeController(magnetSpinController);
	}

	public boolean checkDolphinNearObject(GameObject gObject) {
		Vector3d distanceToObj = new Vector3d(0, 0, 0);
		distanceToObj.x = (Math.abs(
				dol.getWorldLocation().x() - gObject.getWorldLocation().x()));
		distanceToObj.y = (Math.abs(
				dol.getWorldLocation().y() - gObject.getWorldLocation().y()));
		distanceToObj.z = (Math.abs(
				dol.getWorldLocation().z() - gObject.getWorldLocation().z()));

		double distance = distanceToObj.length();
		return (distance < 0.5) ? true : false;
	}

	private void updateDolphinScore() {
		// Check for each object (cub, torus, sphere, plane) and update visited state
		if (!visitedSites[0]) {
			visitedSites[0] = checkDolphinNearObject(cub);
		}
		if (!visitedSites[1]) {
			visitedSites[1] = checkDolphinNearObject(torus);
		}
		if (!visitedSites[2]) {
			visitedSites[2] = checkDolphinNearObject(sphere);
		}
		if (!visitedSites[3]) {
			visitedSites[3] = checkDolphinNearObject(plane);
		}
		// Update the score and add magnet(s) if site(s) were visited
		int old_counter = counter;
		counter = (visitedSites[0] ? 1 : 0) + (visitedSites[1] ? 1 : 0)
				+ (visitedSites[2] ? 1 : 0) + (visitedSites[3] ? 1 : 0);
		if (counter > old_counter) {
			for (int i = old_counter; i < counter; ++i) {
				AddMagnetToManualObject(i);
			}
		}
	}

	private void toggleNodeControllers() {
		for (int i = 0; i < visitedSites.length; ++i) {
			if (visitedSites[i] && !controllerArr.get(i).isEnabled()) {
				controllerArr.get(i).enable();
			}
		}
	}

	private void printControls() {
		System.out.println("Gamepad and Key Bindings:");
		System.out.println("\tMove Forward:\t\tW, Gamepad Left Joystick Y-axis up");
		System.out.println("\tMove Backward:\t\tS, Gamepad Left Joystick Y-axis down");
		System.out.println("\tYaw Left:\t\tA, Gamepad Left Joystick X-axis left");
		System.out.println("\tYaw Right:\t\tD, Gamepad Left Joystick X-axis right");
		System.out.println("\tRoll Left:\t\tQ");
		System.out.println("\tRoll Right:\t\tE");
		System.out.println("\tPitch Up:\t\tUp Arrow, Gamepad Button 0");
		System.out.println("\tPitch Down:\t\tDown Arrow, Gamepad Button 1");
		System.out.println("\tCamera Rotate Left:\t\tGamepad Right Joystick RX-axis left");
		System.out.println("\tCamera Rotate Right:\t\tGamepad Right Joystick RX-axis right");
		System.out.println("\tCamera Elevation Up:\t\tGamepad Right Joystick RY-axis up");
		System.out.println("\tCamera Elevation Down:\t\tGamepad Right Joystick RY-axis down");
		System.out.println("\tCamera Zoom In:\t\tGamepad Button 2");
		System.out.println("\tCamera Zoom Out:\t\tGamepad Button 3");
		System.out.println("\tPan Mini-Map Up:\t\t0");
		System.out.println("\tPan Mini-Map Right:\t\tP");
		System.out.println("\tPan Mini-Map Down:\t\tL");
		System.out.println("\tPan Mini-Map Right:\t\tO");
		System.out.println("\tMini-Map Zoom In:\t\t]");
		System.out.println("\tMini-Map Zoom Out:\t\t[");
		System.out.println("\tToggle World Axes Object:\t\t1");
		System.out.println("\t");
	}
}
