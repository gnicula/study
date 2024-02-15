/*
 * @author Gabriele Nicula
 */

package tage.input.action;

import a1.MyGame;
import net.java.games.input.Event;
import tage.Camera;
import tage.GameObject;

/**
 * YawActionJ turn the dolphin left/right around the world Y axis.
 * This InputAction works with the game controller.
 */
public class YawActionJ extends AbstractInputAction {

    private MyGame game;
    private GameObject dolph;
    private Camera camera;
    private float speed, scale = 0.0005f;

    /**
     * Constructor for joystick input
     * 
     * @param game
     */
    public YawActionJ(MyGame game) {
        this.game = game;
        this.dolph = game.getDolphin();
        this.camera = game.getMyCamera();
    }

    @Override
    public void performAction(float time, Event evt) {
        float keyValue = evt.getValue();
        if (keyValue > -.2 && keyValue < .2)
        {
            return; // deadzone
        }
        float turn_direction = (keyValue >= .2) ? -1 : 1;
        speed = scale * time * turn_direction;

        dolph.yaw(speed);
        if (game.onDolphinCam())
        {
            game.setOnDolphinCam();
        }
    }
}