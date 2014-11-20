package org.nhl.containing.controller;

/**
 * Central logic controller.
 *
 * @author TRJMM
 */
public class Controller {

    private Communication client;

    public Controller() {
        client = new Communication();
    }

    public void Start() {
        client.Start();
    }
}
