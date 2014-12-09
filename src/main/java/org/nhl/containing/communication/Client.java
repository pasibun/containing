package org.nhl.containing.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Client.
 */
public class Client implements Runnable {

    private final int portNumber = 6666;
    private final String serverName = "localhost";
    private Socket socket;
    private ListenRunnable listenRunnable;
    private SendRunnable sendRunnable;
    private boolean running;
    private boolean calledStop;

    public Client() {
    }

    @Override
    public void run() {
        try {
            if (calledStop) { //Ugly fix to stop the reconnect-loop when there's no connection at all
                stop();
            } else {
                // Open up the socket.
                socket = new Socket(serverName, portNumber);

                listenRunnable = new ListenRunnable(new BufferedReader(new InputStreamReader(socket.getInputStream())));
                sendRunnable = new SendRunnable(new PrintWriter(socket.getOutputStream(), true));

                Thread listenThread = new Thread(listenRunnable);
                listenThread.setName("ListenThread");
                Thread sendThread = new Thread(sendRunnable);
                sendThread.setName("SendThread");

                listenThread.start();
                sendThread.start();
                running = true;
            }
        } catch (IOException e) {
            System.out.println("SERVER NOT FOUND! Make sure the server is running! Now trying to reconnect...");
            try {
                Thread.sleep(2500);
                run();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }


        while (running) {
            try {
                // Do nothing.
                Thread.sleep(1000);
                // In case the client shut down the listener, shut down everything.
            } catch (Throwable e) {
                e.printStackTrace();
            }
            if (listenRunnable != null) {
                if (!listenRunnable.isRunning()) {
                    this.stop();
                }
            }
        }

    }

    /**
     * Stops the client an thus the listen- and sendrunnables
     */
    public void stop() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (listenRunnable != null) {
            try {
                listenRunnable.stop();
            } catch (Throwable e) {
            }
        }
        if (sendRunnable != null) {
            try {
                sendRunnable.stop();
            } catch (Throwable e) {
            }
        }
        running = false;
        calledStop = true;
    }

    /**
     *
     *
     * @return
     */
    public String getMessage() {
        return listenRunnable.getMessage();
    }

    public boolean hasListener() {
        if (listenRunnable == null || !listenRunnable.isRunning()) {
            return false;
        }
        return true;
    }

    /**
     * sends a message to the backend system
     *
     * @param message
     */
    public void writeMessage(String message) {
        sendRunnable.writeMessage("<Simulation>" + message + "</Simulation>");
    }
}
