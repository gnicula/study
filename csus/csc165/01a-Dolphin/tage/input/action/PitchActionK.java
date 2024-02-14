package tage.input.action;

import a1.MyGame;
import net.java.games.input.Event;
import tage.Camera;
import tage.GameObject;

/**
 * PitchAction implements pitch up/down of the Game object.
 */
public class PitchActionK extends AbstractInputAction {
    private MyGame game;
    private GameObject dolph;
    private Camera camera;
    private float speed;
    private float scale; // positive nose up, negative nose down

    public PitchActionK(MyGame game, float factor) {
        this.game = game;
        this.dolph = game.getAvatar();
        this.camera = game.getMyCamera();
        this.scale = factor;
    }

    @Override
    public void performAction(float time, Event evt) {
        speed = scale * time;

        dolph.pitch(speed);
        if (game.onDolphinCam())
        {
            game.setOnDolphinCam();
        }

    }
}