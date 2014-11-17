package org.nhl.containing.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author TRJMM Used to communicate with the Backend system
 */
public class Communication {

    private enum statusEnum {

        LISTEN, SENDING, INITIALIZE, DISPOSE
    };
    private statusEnum status;
    private Socket client;
    private InputStream inFromServer;
    private DataOutputStream out;
    private final int PORT = 6666;
    private final String serverName = "localhost";
    private Thread operation;

    public Communication() {
        status = status.INITIALIZE;
        update();
    }

    /**
     * Starts the client
     */
    public void startClient() {
        try {

            System.out.println("Connecting to " + serverName
                    + " on port " + PORT);
            client = new Socket(serverName, PORT);
            System.out.println(" Just connected to "
                    + client.getRemoteSocketAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
        sleep(1000);
    }

    /**
     * Listens for input from the backend system
     */
    public void listen() {
        try {
            if (client == null) {
                client = new Socket(serverName, PORT);
                sleep(100);
            }
            System.out.println("Listening to "
                    + client.getRemoteSocketAddress() + "...");
            inFromServer = client.getInputStream();
            DataInputStream input =
                    new DataInputStream(inFromServer);
            String outputString = input.readUTF();
            if (outputString == "") {
                input.reset();
            } else {
                System.out.println("Recieved string " + outputString + " from backend system!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a message to the backend system
     *
     * @param message The message
     */
    public void sendMessage(String message) {
        status = status.SENDING;

        try {
            if (client == null) {
                client = new Socket(serverName, PORT);
                sleep(100);
            }
            System.out.println("Trying to send message " + message + " to the Backend system!");
            out = new DataOutputStream(client.getOutputStream());
            out.writeUTF(message);
            System.out.println("Sent message " + message + " to the Backend system!");

        } catch (Exception e) {
            e.printStackTrace();
        }
        status = status.LISTEN;
    }

    /**
     * Stops the client and stops this thread
     */
    public void stopClient() {
        try {
            client.close();
            status = status.DISPOSE;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will be called once and loops until the thread stops.
     */
    private void update() {
        operation = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        switch (status) {
                            case INITIALIZE:
                                startClient();
                                status = status.LISTEN;
                                break;
                            case LISTEN:
                                listen();
                                status = status.LISTEN;
                                break;
                            case SENDING:
                                sleep(100);
                                status = status.SENDING;
                                break;
                            case DISPOSE:
                                operation.stop();
                                break;
                        }

                    } catch (Throwable e) {
                    }
                }
            }
        });
        operation.setName("Simulation Communicator");
        operation.start();
    }

    /**
     * Sleep this thread we are working with for x milliseconds
     *
     * @param milliseconds How long are we waiting in milliseconds?
     */
    public void sleep(int milliseconds) {
        try {
            operation.currentThread().sleep(milliseconds); //1000 milliseconds is one second.
        } catch (InterruptedException ex) {
            operation.currentThread().interrupt();
        }
    }
}
