package org.nhl.containing.vehicles;

import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.MotionPathListener;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.scene.Node;
import org.nhl.containing.communication.ProcessesMessage;

public class Vehicle extends Node implements MotionPathListener, ProcessesMessage {

    private int id;
    private int processingMessageId = -1;
    private boolean taskComplete = true;
    protected MotionPath path;

    public Vehicle(int id) {
        super();
        this.id = id;
    }

    @Override
    public int getProcessingMessageId() {
        return processingMessageId;
    }

    @Override
    public void setProcessingMessageId(int id) {
        processingMessageId = id;
    }

    @Override
    public boolean isTaskComplete() {
        return taskComplete;
    }

    @Override
    public void setTaskComplete(boolean taskComplete) {
        this.taskComplete = taskComplete;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void onWayPointReach(MotionEvent motionControl, int wayPointIndex) {
        // If the current waypoint is the last waypoint
        if (wayPointIndex + 1 == path.getNbWayPoints()) {
            taskComplete = true;
        }
    }
}
