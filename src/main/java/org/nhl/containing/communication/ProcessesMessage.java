package org.nhl.containing.communication;

/**
 * Interface for things that can process messages.
 */
public interface ProcessesMessage {
    public int getId();

    public int getProcessingMessageId();
    public void setProcessingMessageId(int id);
    
    public boolean isTaskComplete();
    public void setTaskComplete(boolean taskComplete);
}
