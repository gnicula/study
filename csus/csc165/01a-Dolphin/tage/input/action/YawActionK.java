package tage.input.action;

import a1.MyGame;
import net.java.games.input.Event;
import tage.Camera;
import tage.GameObject;

/**
 * YawActionK turn the dolphin left/right around the world Y axis.
 */
public class YawActionK extends AbstractInputAction {

    private MyGame game;
    private GameObject avatar; // the avatar is the "main character" in the game
    private Camera camera;

    /**
     * Arbitrary scaling factor to make the avatar move at a reasonable speed
     */
    private float movement_speed,
            movement_scale_factor = 0.001f; // positive because we are moving forward

    /**
     * Arbitrary value denoting that the analog stick is being used to rotate
     * <ul>
     * <li><code>1</code> rotate left with keyboard</li>
     * <li><code>-1</code> rotate right with keyboard</li>
     * </ul>
     */
    private int inputConfiguration;

    /**
     * Constructor for analog stick input.
     * 
     * @param game
     */
    public YawActionK(MyGame game) {
        this.game = game;
        this.avatar = game.getAvatar();
        this.camera = game.getCameraMain();
    }

    /**
     * Constructor for keyboard input.
     * When the analog stick is not used as input,
     * the direction is either 1 (left) or -1 (right).
     * 
     * @param game
     * @param direction 1 for left, -1 for right:
     *                  the analog stick is not used as input
     */
    public YawActionK(MyGame game, int direction) {
        this.game = game;
        this.avatar = game.getAvatar();
        this.camera = game.getCameraMain();
        this.inputConfiguration = direction;
    }

    @Override
    public void performAction(float time, Event evt) {
        movement_speed = movement_scale_factor * time * inputConfiguration;

        // if (game.isInFreeCamMode()) { // the camera is free to move around
        //     camera.yaw(movement_speed);
        // } else { // the camera is bound to the avatar
            avatar.yaw(movement_speed);
            if (game.onDolphinCam())
            {
                game.setOnDolphinCam();
            }

        //     game.positionCameraBehindAvatar();
        // }
    }
}