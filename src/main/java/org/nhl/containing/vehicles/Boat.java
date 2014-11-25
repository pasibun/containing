package org.nhl.containing.vehicles;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Spatial;
import org.nhl.containing.Communication;

/**
 *
 * @author Jeroen
 */
public class Boat extends Transporter {

    public enum Size {

        INLANDSHIP, SEASHIP
    };
    private AssetManager assetManager;
    private Boat.Size size;
    private Spatial boat;

    public Boat(AssetManager assetManager, Size size) {
        this.assetManager = assetManager;
        this.size = size;
        initBoat();
    }

    /**
     * Initialize a boat.
     */
    public void initBoat() {
        try {
            switch (size) {
                case INLANDSHIP:
                    // Load a model.
                    boat = assetManager.loadModel("Models/medium/ship/seaship.j3o");
                    this.attachChild(boat);
                    break;
                case SEASHIP:
                    // Load a model.
                    boat = assetManager.loadModel("Models/medium/ship/seaship.j3o");
                    this.attachChild(boat);
                    break;
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }

    }
}
