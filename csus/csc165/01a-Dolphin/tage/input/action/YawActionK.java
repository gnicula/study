package tage.input.action;

import a1.MyGame;
import net.java.games.input.Event;
import tage.Camera;
/*
 * @author Gabriele Nicula
 */

import tage.GameObject;

/**
 * YawActionK turn the dolphin left/right around the world Y axis.
 */
public class YawActionK extends AbstractInputAction {

    private MyGame game;
    private GameObject dolph;
    private Camera camera;

    /**
     * scaling factor for smoothness
     */
    private float speed,
            scale = 0.0002f; // scale for smoothness

    /**
     * yaw direction: 1 (left) or -1 (right)
     */
    private int yaw_direction;

    /**
     * Constructor
     * the direction is either 1 (left) or -1 (right)
     * 
     * @param game
     * @param direction 1 for left, -1 for right
     */
    public YawActionK(MyGame game, int direction) {
        this.game = game;
        this.dolph = game.getDolphin();
        this.camera = game.getMyCamera();
        this.yaw_direction = direction;
    }

    @Override
    public void performAction(float time, Event evt) {
        speed = scale * time * yaw_direction;

        dolph.yaw(speed);
        if (game.onDolphinCam())
        {
            game.setOnDolphinCam();
        }

    }
}