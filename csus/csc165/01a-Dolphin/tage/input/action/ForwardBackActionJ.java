package tage.input.action;

import a1.MyGame;
import net.java.games.input.Event;
import tage.Camera;
import tage.GameObject;

/**
 * ForwardBackAction implements moving forward or backward of the Game object.
 */
public class ForwardBackActionJ extends AbstractInputAction {
    private MyGame game;
    private GameObject dolph;
    private Camera camera;
    private float speed;
    private float scale;

    public ForwardBackActionJ(MyGame game, float factor) {
        this.game = game;
        this.dolph = game.getAvatar();
        this.camera = game.getMyCamera();
        this.scale = factor;
    }

    @Override
    public void performAction(float time, Event evt) {
        float keyValue = evt.getValue();
        if (keyValue > -.2 && keyValue < .2)
        {
            return; // deadzone
        }
        float advance_direction = (keyValue >= .2) ? -1 : 1;
        speed = scale * time * advance_direction;

        dolph.moveForwardBack(speed);
        if (game.onDolphinCam())
        {
            game.setOnDolphinCam();
        }
    }
}