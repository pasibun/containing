package org.nhl.containing.vehicles;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 *
 * @author Jeroen
 */
public class Lorry extends Transporter {

    private AssetManager assetManager;
    private Vector3f Location;

    public Lorry(AssetManager assetManager) {
        this.assetManager = assetManager;
        initLorry();
    }

    /**
     * Initialize a lorry.
     */
    public void initLorry() {


        // Load a model.
        Spatial lorry = assetManager.loadModel("Models/medium/truck.j3o");
        this.attachChild(lorry);
    }

    public Vector3f getLocation() {
        return Location;
    }
}
