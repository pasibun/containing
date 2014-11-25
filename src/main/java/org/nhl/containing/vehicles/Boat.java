package org.nhl.containing.vehicles;

import com.jme3.asset.AssetManager;
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 *
 * @author Jeroen
 */
public class Boat extends Transporter {

    private AssetManager assetManager;
    private float speed = 0.5f;

    public Boat(AssetManager assetManager) {
        this.assetManager = assetManager;
        initBoat();
    }

    /**
     * Initialize a boat.
     */
    public void initBoat() {

        // Load a model.
        Spatial boat = assetManager.loadModel("Models/medium/ship/seaship.j3o");
        this.attachChild(boat);
    }
     /**
     * creates the trains waypoints and lets the train drive over them
     * direction TRUE = the train arrives
     * direction FALSE = the train departs
     */
    public void move(boolean direction){
        MotionPath path = new MotionPath();
        MotionEvent motionControl = new MotionEvent(this, path);
        //boat arrives
        if (direction) {
            path.addWayPoint(new Vector3f(-700, 0, 500));
            path.addWayPoint(new Vector3f(-330, 0, 0));
        }
        //boat leaves
        else{
            path.addWayPoint(new Vector3f(-330, 0, 0));
            path.addWayPoint(new Vector3f(-345, 0, -300));
        }
        motionControl.setRotation(new Quaternion().fromAngleNormalAxis(0, Vector3f.UNIT_Y));
        motionControl.setInitialDuration(10f);
        motionControl.setSpeed(speed);
        motionControl.play();
    }
}