package org.nhl.containing.vehicles;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Spatial;
import org.nhl.containing.Container;

/**
 *
 * @author Jeroen
 */
public class Train extends Transporter {

    private AssetManager assetManager;
    private int numberOfWagons;
    private int wagonZAxis = -11;
    private int containerZAxis = -5;
    private Spatial nextWagon;
    private Spatial train;
    private Spatial wagon;
    private Container C;

    public Train(AssetManager assetManager, int numberOfWagons, Container c) {
        this.assetManager = assetManager;
        this.numberOfWagons = numberOfWagons;
        this.C = c;
        initTrain();
    }

    /**
     * Initialize a train.
     */
    public void initTrain() {

        // Load a model.
        Spatial train = assetManager.loadModel("Models/medium/train/train.j3o");
        this.attachChild(train);

        //Load wagons.
        Spatial wagon = assetManager.loadModel("Models/medium/train/wagon.j3o");

        for (int i = 0; i < numberOfWagons; i++) {
            Spatial nextWagon = wagon.clone();
            nextWagon.setLocalTranslation(0, 0, wagonZAxis);
            C.setLocalTranslation(0, 1f, containerZAxis);
            this.attachChild(nextWagon);
            this.attachChild(C);

            wagonZAxis -= 15;
        }
    }

    public Spatial getWagon() {
        return wagon;
    }
}
