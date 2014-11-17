package org.nhl.containing.simulator;

/**
 * Central logic controller.
 * 
 * @author Ruben Bakker
 */
public class Controller {
    private Thread client;
    public Controller() {
        client = new Communication();
        client.start();
    }
}
