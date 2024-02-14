package tage.input.action;

import a1.MyGame;
import net.java.games.input.Event;
import tage.Camera;
import tage.GameObject;

/**
 * PitchActionJ nose up/down around local side axis.
 */
public class PitchActionJ extends AbstractInputAction {

    private MyGame game;
    private GameObject dolph;
    private Camera camera;
    private float speed, scale = 0.001f;

    /**
     * Constructor for joystick input
     * 
     * @param game
     */
    public PitchActionJ(MyGame game) {
        this.game = game;
        this.dolph = game.getAvatar();
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

        dolph.pitch(speed);
        if (game.onDolphinCam())
        {
            game.setOnDolphinCam();
        }
    }
}