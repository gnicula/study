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
 public class SecondaryViewportZoomActionK extends AbstractInputAction {
     // MyGame object where GameObjects are placed in.
     private MyGame game;
     // Camera object that is slaved to GameObject dolph.
     private Camera camera;
     // Speed at which action can occur.
     private float speed;
     // Scaling factor to slow or speed up rate at which actions can occur. Also 
     // has direction for joystick usage.
     private float direction_and_scale;
 
     /** <code> ForwardBackActionK() </code> is invoked to execute this input action.
      * @param game -- MyGame object where movement is happening.
      * @param factor -- scaling factor that controls rate at which actions can occur.
      */
     public SecondaryViewportZoomActionK(MyGame game, float factor) {
         this.game = game;
         this.camera = game.getMyViewportCamera();
         this.direction_and_scale = factor;
     }
 
     @Override
     public void performAction(float time, Event evt) {
         speed = direction_and_scale * time;
        
         camera.setLocation(camera.getLocation().add(0.0f, speed, 0.0f));
     }
 }