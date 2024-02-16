/*
 * @author Gabriele Nicula
 */

package tage.input.action;

import a1.MyGame;
import net.java.games.input.Event;
import tage.Camera;
import tage.GameObject;

/**
 * ForwardBackAction implements moving forward or backward the Game object.
 * This InputAction works with the keyboard.
 */
public class ForwardBackActionK extends AbstractInputAction {
    private MyGame game;
    private GameObject dolph;
    private Camera camera;
    private float speed;
    private float direction_and_scale;

    public ForwardBackActionK(MyGame game, float factor) {
        this.game = game;
        this.dolph = game.getDolphin();
        this.camera = game.getMyCamera();
        this.direction_and_scale = factor;
    }

    @Override
    public void performAction(float time, Event evt) {
        speed = direction_and_scale * time;

        dolph.moveForwardBack(speed, camera.getLocation());
        if (game.onDolphinCam())
        {
            game.setOnDolphinCam();
        }
    }
}