package org.nhl.containing;

import com.jme3.animation.LoopMode;
import com.jme3.app.SimpleApplication;
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Spatial;
import org.nhl.containing.cranes.DockingCrane;
import java.util.ArrayList;
import java.util.List;
import org.nhl.containing.vehicles.Agv;
import org.nhl.containing.vehicles.Boat;
import org.nhl.containing.vehicles.Lorry;
import org.nhl.containing.vehicles.Train;
import org.nhl.containing.vehicles.Vehicle;

/**
 * test
 *
 * @author normenhansen
 */
public class Simulation extends SimpleApplication {
    private List<Container> totalContainerList;
    private List<Container> trainContainerList;
    private List<Container> seashipContainerList;
    private List<Container> inlandshipContainerList;
    private Container c;
    private Agv avg;
    private Communication communication;
    private MotionPath avgPath;
    private MotionEvent motionControl;
    private DirectionalLight sun;
    private boolean debug;
    //Tijdelijk
    int locationInt = 0;

    public Simulation() {
        communication = new Communication();
        totalContainerList = new ArrayList<Container>();
        trainContainerList = new ArrayList<Container>();
        seashipContainerList = new ArrayList<Container>();
        inlandshipContainerList = new ArrayList<Container>();
    }

    @Override
    public void simpleInitApp() {
        cam();
        scene();
        userInput();
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

    /**
     * Sends an OK-type message to the controller of the object that is ready
     * for a new task -SUBJECT TO CHANGE, MAYBE SUPERCLASS OBJECT IN THE
     * FUTURE!-
     *
     * @param veh -SUBJECT TO CHANGE, MAYBE SUPERCLASS OBJECT IN THE FUTURE!-
     */
    public void sendOkMessage(Vehicle veh) {
        String message = "<OK><OBJECT>" + veh.getName() + "</OBJECT><OBJECTID>" + veh.getId() + "</OBJECTID></OK>";
        communication.sendMessage(message);
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

        /*// Add a train to the scene.
        Train train = new Train(assetManager, 10);
        train.setLocalTranslation(30, 0, 30);
        
        rootNode.attachChild(train);
        
        // Add a container to the scene.
        Container container = new Container(assetManager);
        container.setLocalTranslation(0, 0, 0);
        rootNode.attachChild(container);*/
//        // Add a train to the scene.
//        Train train = new Train(assetManager, 10);
//        train.setLocalTranslation(30, 0, 30);

        //rootNode.attachChild(train);

        // Add a container to the scene.
        Container container = new Container(assetManager, "Coca Cola",
                "1-6547", "vrachtauto", new Vector3f(6, 0, 0));
        totalContainerList.add(container);
        container = new Container(assetManager, "Coca Cola",
                "1-6547", "Trein", new Vector3f(6, 0, 0));
        totalContainerList.add(container);
        container = new Container(assetManager, "Coca Cola",
                "1-6547", "Trein", new Vector3f(6, 0, 0));
        container.setLocalTranslation(100, 0, 0);
        totalContainerList.add(container);
        createObject();

        //rootNode.attachChild(container);
        //Add an AVG and create its path
        avg = new Agv(assetManager);
        avg.setLocalTranslation(-230, 0, -180);
        rootNode.attachChild(avg);
        createPath();
        motionControl.setLoopMode(LoopMode.Loop);
        motionControl.play();

        // Platform for the scene.        
        Spatial platform = assetManager.loadModel("Models/platform/platform.j3o");
        platform.scale(20, 1, 20);
        rootNode.attachChild(platform);
    }

    void createPath() {
        avgPath = new MotionPath();
        avgPath.addWayPoint(new Vector3f(-230, 0, -180));
        avgPath.addWayPoint(new Vector3f(-230, 0, -80));
        avgPath.addWayPoint(new Vector3f(-230, 0, 20));
        avgPath.addWayPoint(new Vector3f(-230, 0, 125));
        avgPath.addWayPoint(new Vector3f(-130, 0, 125));
        avgPath.addWayPoint(new Vector3f(30, 0, 125));
        avgPath.addWayPoint(new Vector3f(80, 0, 125));
        avgPath.addWayPoint(new Vector3f(180, 0, 125));
        avgPath.addWayPoint(new Vector3f(290, 0, 125));
        avgPath.addWayPoint(new Vector3f(290, 0, 20));
        avgPath.addWayPoint(new Vector3f(290, 0, -80));
        avgPath.addWayPoint(new Vector3f(290, 0, -140));
        avgPath.addWayPoint(new Vector3f(190, 0, -140));
        avgPath.addWayPoint(new Vector3f(90, 0, -140));
        avgPath.addWayPoint(new Vector3f(0, 0, -140));
        avgPath.addWayPoint(new Vector3f(-90, 0, -140));
        avgPath.addWayPoint(new Vector3f(-190, 0, -140));
        motionControl = new MotionEvent(avg, avgPath);
        motionControl.setDirectionType(MotionEvent.Direction.PathAndRotation);
        motionControl.setRotation(new Quaternion().fromAngleNormalAxis(0, Vector3f.UNIT_Y));
        motionControl.setInitialDuration(10f);
        motionControl.setSpeed(0.2f);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    /**
     * This methode will process all incomming create commands.
     *
     * Create containers and add them to a list<container>. When the
     * list<container> == maxValueContainer (so max count of the sended
     * commands) Then take apart the List<Container> and divide them to there
     * TransportList. When the List<container> == empty then create the
     * vehicles.
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
                    if (c.getTransportType().equals("Trein")) {
                        trainContainerList.add(c);
                    }
                    if (c.getTransportType().equals("vrachtauto")) {
                        // Lorry can only contain 1 container, so has to create immediately.
                        Lorry l = new Lorry(assetManager);
                        //c.setLocation(l.getLocation());
                        c.attachChild(l);
                    }
                }
                for (Container c : inlandshipContainerList) {
                    if (!inlandshipContainerList.isEmpty() && communication.getTransportType().equals("binnenschip")) {
                        Boat b = new Boat(assetManager, Boat.Size.INLANDSHIP);
                    }
                }
                for (Container c : seashipContainerList) {
                    if (!seashipContainerList.isEmpty() && communication.getTransportType().equals("zeeschip")) {
                        Boat b = new Boat(assetManager, Boat.Size.SEASHIP);
                    }
                }
                for (Container c : trainContainerList) {
                    Train t = new Train(assetManager, trainContainerList.size(), c);
                    t.setLocalTranslation(30, 0, 30);
                    rootNode.attachChild(t);
                }
            }
        } else {
            //owner, id, arrival-transport-type
            c = new Container(assetManager, communication.getContainerOwner(),
                    communication.getContainerIso(), communication.getTransportType(), new Vector3f(0, locationInt += 10, 0));
            totalContainerList.add(c);
        }
    }

    public void getData() {
    }

    public void userInput() {
        inputManager.addMapping("debugmode", new KeyTrigger(KeyInput.KEY_P));
        ActionListener acl = new ActionListener() {
            public void onAction(String name, boolean keyPressed, float tpf) {
                if (name.equals("debugmode") && keyPressed) {
                    if (debug) {
                        debug = false;
                        avgPath.disableDebugShape();
                    } else {
                        debug = true;
                        avgPath.enableDebugShape(assetManager, rootNode);
                    }
                }
            }
        };
        inputManager.addListener(acl, "debugmode");
    }

    public void readyCheck() {
    }
}
