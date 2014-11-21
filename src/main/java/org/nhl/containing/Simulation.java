package org.nhl.containing;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Spatial;
import de.lessvoid.nifty.Size;
import org.nhl.containing.cranes.DockingCrane;
import java.util.ArrayList;
import java.util.List;
import org.nhl.containing.vehicles.Boat;
import org.nhl.containing.vehicles.Lorry;
import org.nhl.containing.vehicles.Train;

/**
 * test
 *
 * @author normenhansen
 */
public class Simulation extends SimpleApplication {

    private Communication communication;
    private List<Container> totalContainerList;
    private List<Container> trainContainerList;
    private List<Container> seashipContainerList;
    private List<Container> inlandshipContainerList;
    private Container c;
    //TIJDELIJK
    private int locationInt = 0;

    public Simulation() {
        communication = new Communication();
        totalContainerList = new ArrayList<Container>();
    }

    @Override
    public void simpleInitApp() {
        cam();
        scene();
        communication.Start();
    }

    /**
     * Camera settings of the scene.
     */
    public void cam() {
        viewPort.setBackgroundColor(ColorRGBA.Blue);
        cam.setLocation(new Vector3f(0, 5, 0));
        flyCam.setMoveSpeed(50);
    }

    /*
     * Method for initializing the scene.
     */
    public void scene() {
        // Light pointing diagonal from the top right to the bottom left.
        DirectionalLight light = new DirectionalLight();
        light.setDirection((new Vector3f(-0.5f, -0.5f, -0.5f)).normalizeLocal());
        light.setColor(ColorRGBA.White);
        rootNode.addLight(light);

        // A second light pointing diagonal from the bottom left to the top right.
        DirectionalLight secondLight = new DirectionalLight();
        secondLight.setDirection((new Vector3f(0.5f, 0.5f, 0.5f)).normalizeLocal());
        secondLight.setColor(ColorRGBA.White);
        rootNode.addLight(secondLight);

        // Add dockingcranes to the scene.
        DockingCrane dockingCrane = new DockingCrane(assetManager);
        dockingCrane.setLocalTranslation(10, 0, 0);
        rootNode.attachChild(dockingCrane);
        DockingCrane secondDockingCrane = new DockingCrane(assetManager);
        secondDockingCrane.setLocalTranslation(10, 0, 30);
        rootNode.attachChild(secondDockingCrane);

        // Add a train to the scene.
        Train train = new Train(assetManager, 10);
        train.setLocalTranslation(30, 0, 30);

        rootNode.attachChild(train);

        // Add a container to the scene.
        Container container = new Container(assetManager, communication.getContainerOwner(),
                communication.getContainerID(), communication.getTransportType(), new Vector3f(6, 0, 0));
        container.setLocalTranslation(0, 0, 0);
        rootNode.attachChild(container);

        // Platform for the scene.        
        Spatial platform = assetManager.loadModel("Models/platform/platform.j3o");
        platform.scale(20, 1, 20);
        rootNode.attachChild(platform);
    }

    @Override
    public void simpleUpdate(float tpf) {
        if (!communication.getCommand().isEmpty()) {
            createObject();
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
/**
 * This methode will process all incomming create commands.
 * 
 * Create containers and add them to a list<container>. 
 * When the list<container> == maxValueContainer (so max count of the sended commands)
 * Then take apart the List<Container> and divide them to there TransportList.
 * When the List<container> == empty then create the vehicles.
 */
    private void createObject() {
        if (communication.getMaxValueContainers() != 0) {
            if (communication.getMaxValueContainers() == totalContainerList.size()) {
                for (Container c : totalContainerList) {
                    if (c.getTransportType().equals("binnenschip")) {
                        inlandshipContainerList.add(c);
                    }
                    if (c.getTransportType().equals("zeeschip")) {
                        seashipContainerList.add(c);
                    }
                    if (c.getTransportType().equals("trein")) {
                        trainContainerList.add(c);
                    }
                    if (c.getTransportType().equals("vrachtauto")) {
                        // Lorry can only contain 1 container, so has to create immediately.
                        Lorry l = new Lorry(assetManager);
                        //c.setLocation(l.getLocation());
                        c.attachChild(l);
                    }
                }
                if (!inlandshipContainerList.isEmpty() && communication.getTransportType().equals("binnenschip")) {
                    Boat b = new Boat(assetManager, Boat.Size.INLANDSHIP);
                }
                if (!seashipContainerList.isEmpty() && communication.getTransportType().equals("zeeschip")) {
                    Boat b = new Boat(assetManager, Boat.Size.SEASHIP);
                }
                if (!trainContainerList.isEmpty() && communication.getTransportType().equals("trein")) {
                    Train t = new Train(assetManager, trainContainerList.size());
                    c.attachChild(t.getWagon());
                }
            }
        } else {
            //owner, id, arrival-transport-type
            c = new Container(assetManager, communication.getContainerOwner(),
                    communication.getContainerID(), communication.getTransportType(), new Vector3f(0, locationInt += 10, 0));
            totalContainerList.add(c);
        }
    }

    public void getData() {
    }

    public void userInput() {
    }

    public void readyCheck() {
    }
}
