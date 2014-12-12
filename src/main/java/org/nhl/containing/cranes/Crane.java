package org.nhl.containing.cranes;

import com.jme3.cinematic.MotionPathListener;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.scene.Node;
import org.nhl.containing.communication.ProcessesMessage;

public abstract class Crane extends Node implements MotionPathListener,
        ProcessesMessage {

    private int id;
    private int processingMessageId = -1;
    private boolean taskComplete = true;

    public Crane(int id) {
        this.id = id;
    }

    @Override
    public int getProcessingMessageId() {
        return processingMessageId;
    }

    @Override
    public void setProcessingMessageId(int id) {
        this.processingMessageId = id;
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
    public void onWayPointReach(MotionEvent motionControl, int wayPointIndex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getId() {
        return id;
    }
}
