package a1;

import tage.*;
import tage.shapes.*;
import tage.input.InputManager; // input management
import tage.input.action.PitchAction;
import tage.input.action.ForwardBackAction;
import tage.input.action.YawActionK;

import net.java.games.input.Controller;

import java.util.ArrayList;
import java.lang.Math;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

import org.joml.*;

public class MyGame extends VariableFrameRateGame
{
	private static Engine engine;

	private boolean paused=false;
	private boolean offDolphinCam = true;
	private int counter=0;
	private int frameCounter = 0;
	private double lastFrameTime, currFrameTime, elapsTime;

	private GameObject dol, cub, torus, sphere, plane, wAxisX, wAxisY, wAxisZ;
	private ObjShape dolS, cubS, torusS, sphereS, planeS, wAxisLineShapeX, wAxisLineShapeY, wAxisLineShapeZ;
	private TextureImage doltx, brick, grass, corvette, assignt;
	private Light light1;
	private Camera myCamera;
	private InputManager inputManager;
	private Vector3f location, // object location in world coordinates
			forward, // object forward vector in world coordinates (n-vector/z-axis)
			up, // object up vector in world coordinates (v-vector/y-axis)
			right; // object right vector in world coordinates (u-vector/x-axis)

	public MyGame() { super(); }

	public static void main(String[] args)
	{	MyGame game = new MyGame();
		engine = new Engine(game);
		game.initializeSystem();
		game.game_loop();
	}

	@Override
	public void loadShapes()
	{	dolS = new ImportedModel("dolphinHighPoly.obj");
		cubS = new Cube();
		torusS = new Torus();
		sphereS = new Sphere();
		planeS = new Plane();

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
	public void loadTextures()
	{	doltx = new TextureImage("Dolphin_HighPolyUV.png");
		brick = new TextureImage("brick1.jpg");
		grass = new TextureImage("grass1.jpg");
		corvette = new TextureImage("corvette1.jpg");
		assignt = new TextureImage("assign1.png");
	}

	@Override
	public void buildObjects()
	{	Matrix4f initialTranslation, initialScale;

		// build dolphin in the center of the window
		dol = new GameObject(GameObject.root(), dolS, doltx);
		initialTranslation = (new Matrix4f()).translation(0,0,0);
		initialScale = (new Matrix4f()).scaling(0.75f);
		dol.setLocalTranslation(initialTranslation);
		dol.setLocalScale(initialScale);

		Matrix4f initialTranslationCub, initialScaleCub;
		// build a brick at the right side of the window
		cub = new GameObject(GameObject.root(), cubS, brick);
		initialTranslationCub = (new Matrix4f()).translation(3,1,0);
		initialScaleCub = (new Matrix4f()).scaling(0.5f);
		cub.setLocalTranslation(initialTranslationCub);
		cub.setLocalScale(initialScaleCub);

		Matrix4f initialTranslationTorus, initialScaleTorus;
		// build a grass torus at the left side of the window
		torus = new GameObject(GameObject.root(), torusS, grass);
		initialTranslationTorus = (new Matrix4f()).translation(-3,1,0);
		initialScaleTorus = (new Matrix4f()).scaling(0.5f);
		torus.setLocalTranslation(initialTranslationTorus);
		torus.setLocalScale(initialScaleTorus);

		Matrix4f initialTranslationSphere, initialScaleSphere;
		// build a sphere logo textured at the right side of the window
		sphere = new GameObject(GameObject.root(), sphereS, corvette);
		initialTranslationSphere = (new Matrix4f()).translation(3,-1,0);
		initialScaleSphere = (new Matrix4f()).scaling(0.5f);
		sphere.setLocalTranslation(initialTranslationSphere);
		sphere.setLocalScale(initialScaleSphere);

		Matrix4f initialTranslationPlane, initialScalePlane;
		// build a plane textured at the left side of the window
		plane = new GameObject(GameObject.root(), planeS, assignt);
		initialTranslationPlane = (new Matrix4f()).translation(-3,-1,0);
		initialScalePlane = (new Matrix4f()).scaling(0.5f);
		plane.setLocalTranslation(initialTranslationPlane);
		plane.setLocalScale(initialScalePlane);

		// Build World Axis Lines (X, Y, Z) in the center of the window
		wAxisX = new GameObject(GameObject.root(), wAxisLineShapeX);
		wAxisY = new GameObject(GameObject.root(), wAxisLineShapeY);
		wAxisZ = new GameObject(GameObject.root(), wAxisLineShapeZ);

		// Set world axis colors (red, green, blue) - X, Y, Z respectively
		wAxisX.getRenderStates().setColor(new Vector3f(1f, 0, 0));
		wAxisY.getRenderStates().setColor(new Vector3f(0, 1f, 0));
		wAxisZ.getRenderStates().setColor(new Vector3f(0, 0, 1f));
	}

	@Override
	public void initializeLights()
	{	Light.setGlobalAmbient(0.5f, 0.5f, 0.5f);
		light1 = new Light();
		light1.setLocation(new Vector3f(5.0f, 4.0f, 2.0f));
		(engine.getSceneGraph()).addLight(light1);
	}

	@Override
	public void initializeGame()
	{	
		currFrameTime = System.currentTimeMillis();
		lastFrameTime = currFrameTime;
		elapsTime = 0.0;
		(engine.getRenderSystem()).setWindowDimensions(1900,1000);

		// ------------- positioning the camera -------------
		myCamera = engine.getRenderSystem().getViewport("MAIN").getCamera();
		myCamera.setLocation(new Vector3f(0,0,5));

		inputManager = engine.getInputManager();
		ArrayList<Controller> controllers = inputManager.getControllers(); // get all our controllers
		PitchAction pitchUp = new PitchAction(this, 0.0005f);
		PitchAction pitchDown = new PitchAction(this, -0.0005f);
		ForwardBackAction moveForward = new ForwardBackAction(this, 0.0005f);
		ForwardBackAction moveBackward = new ForwardBackAction(this, -0.0005f);
		YawActionK leftYaw = new YawActionK(this, 1);
		YawActionK rightYaw = new YawActionK(this, -1);

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
	}

	private float getFramesPerSecond() {
		return (float) (frameCounter / elapsTime);
	}

	@Override
	public void update()
	{
		lastFrameTime = currFrameTime;
		currFrameTime = System.currentTimeMillis();
		if (!paused) 
		{
			elapsTime += (currFrameTime - lastFrameTime) / 1000.0;
		}
		// dol.setLocalRotation((new Matrix4f()).rotation((float)elapsTime, 0, 1, 0));

		// build and set HUD
		int elapsTimeSec = Math.round((float)elapsTime);
		String elapsTimeStr = Integer.toString(elapsTimeSec);
		String counterStr = Integer.toString(counter);
		String dispStr1 = "Time = " + elapsTimeStr;
		String dispStr2 = "Keyboard hits = " + counterStr;
		Vector3f hud1Color = new Vector3f(1,0,0);
		Vector3f hud2Color = new Vector3f(0,0,1);
		(engine.getHUDmanager()).setHUD1(dispStr1, hud1Color, 15, 15);
		(engine.getHUDmanager()).setHUD2(dispStr2, hud2Color, 500, 15);

		inputManager.update(getFramesPerSecond());
		frameCounter++;
	}

	@Override
	public void keyPressed(KeyEvent e)
	{	Vector3f loc, fwd, newLocation;
		switch (e.getKeyCode())
		{	case KeyEvent.VK_C:
				counter++;
				break;
			// case KeyEvent.VK_W:
			// 	fwd = dol.getWorldForwardVector();
			// 	loc = dol.getWorldLocation();
			// 	newLocation = loc.add(fwd.mul(0.02f));
			// 	dol.setLocalLocation(newLocation);
			// 	break;
			case KeyEvent.VK_1:
				paused = !paused;
				break;
			case KeyEvent.VK_2:
				dol.getRenderStates().setWireframe(true);
				break;
			case KeyEvent.VK_3:
				dol.getRenderStates().setWireframe(false);
				break;
			case KeyEvent.VK_4:
				(engine.getRenderSystem().getViewport("MAIN").getCamera()).setLocation(new Vector3f(0,0,0));
				offDolphinCam = true;
				break;
			case KeyEvent.VK_SPACE:
				if (offDolphinCam) {
					setOnDolphinCam();
					offDolphinCam = false;
				} else {
					setOffDolphinCam();
					offDolphinCam = true;
				}
				break;
		}
		super.keyPressed(e);
	}

	public Camera getCameraMain() {
		return myCamera;
	}

	public boolean onDolphinCam() {
		return !offDolphinCam;
	}

	public void setOnDolphinCam() {
		float hopOnDistance = -4.5f;
		float upDistance = 1.0f;
		location = getAvatar().getWorldLocation();
		forward = getAvatar().getWorldForwardVector();
		up = getAvatar().getWorldUpVector();
		right = getAvatar().getWorldRightVector();
		myCamera.setU(right);
		myCamera.setV(up);
		myCamera.setN(forward);
		myCamera.setLocation(location
				.add(up.mul(upDistance))
				.add(forward.mul(hopOnDistance)));
	}

	public void setOffDolphinCam() {
		float hopOffDistance = -5f;
		float upDistance = 0.5f;
		// if (!offDolphinCam) {
			location = getAvatar().getWorldLocation();
			forward = getAvatar().getWorldForwardVector();
			up = getAvatar().getWorldUpVector();
			right = getAvatar().getWorldRightVector();
			getCameraMain().setU(right);
			getCameraMain().setV(up);
			getCameraMain().setN(forward);
			getCameraMain().setLocation(location
					.add(up.mul(upDistance))
					.add(forward.mul(hopOffDistance)));
		// }
	}

	public GameObject getAvatar() {
		return dol;
	}
}