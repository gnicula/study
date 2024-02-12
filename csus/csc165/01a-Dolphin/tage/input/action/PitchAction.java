package tage.input.action;

import a1.MyGame;
import net.java.games.input.Event;
import tage.Camera;
import tage.GameObject;

/**
 * PitchAction implements pitch up/down of the Game object.
 */
public class PitchAction extends AbstractInputAction {
    private MyGame game;
    private GameObject avatar;
    private Camera camera;
    private float movement_speed;
    private float movement_scale_factor; // positive nose up, negative nose down

    public PitchAction(MyGame game, float factor) {
        this.game = game;
        this.avatar = game.getAvatar();
        this.camera = game.getCameraMain();
        this.movement_scale_factor = factor;
    }

    @Override
    public void performAction(float time, Event evt) {
        movement_speed = movement_scale_factor * time;
        // the analog stick is not being used to move
        // if (evt.getValue() >= -ANALOG_STICK_DEAD_ZONE && evt.getValue() <= ANALOG_STICK_DEAD_ZONE) {
        //     return;
        // }

        // if (game.isInFreeCamMode()) { // the camera is free to move around
        //     camera.pitch(movement_speed);
        // } else { // the camera is bound to the avatar
            avatar.pitch(movement_speed);
            // game.positionCameraBehindAvatar();
        // }
    }
}