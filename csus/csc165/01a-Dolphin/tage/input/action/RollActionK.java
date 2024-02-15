package tage.input.action;

import a1.MyGame;
import net.java.games.input.Event;
import tage.Camera;
import tage.GameObject;

/**
 * RollActionK rolls the dolphin left/right around its moving direction.
 */
public class RollActionK extends AbstractInputAction {

    private MyGame game;
    private GameObject dolph;
    private Camera camera;

    /**
     * scaling factor for smoothness
     */
    private float speed,
            scale = 0.001f; // scale for smoothness

    /**
     * yaw direction: 1 (left) or -1 (right)
     */
    private int roll_direction;

    /**
     * Constructor
     * the direction is either 1 (left) or -1 (right)
     * 
     * @param game
     * @param direction 1 for left, -1 for right
     */
    public RollActionK(MyGame game, int direction) {
        this.game = game;
        this.dolph = game.getDolphin();
        this.camera = game.getMyCamera();
        this.roll_direction = direction;
    }

    @Override
    public void performAction(float time, Event evt) {
        speed = scale * time * roll_direction;

        dolph.roll(speed);
        if (game.onDolphinCam())
        {
            game.setOnDolphinCam();
        }

    }
}