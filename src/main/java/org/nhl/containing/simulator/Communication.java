package org.nhl.containing.simulator;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * Used to communicate with the Backend system
 */
public class Communication extends Thread {

    private enum commandsEnum {

        WAIT, MOVE, INITIALIZE, LOCATION, LOCATIONNAME, DISPOSE
    };
    private commandsEnum commands;
    private Socket client;
    private OutputStream outToServer;
    private InputStream inFromServer;
    private final int PORT = 6666;

    public Communication() {
        commands = commands.INITIALIZE;
    }

    public void startClient() {
        String serverName = "localhost";
        String commando;
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        try {

            System.out.println("Connecting to " + serverName
                    + " on port " + PORT);
            client = new Socket(serverName, PORT);
            System.out.println("Just connected to "
                    + client.getRemoteSocketAddress());
            outToServer = client.getOutputStream();
            DataOutputStream out =
                    new DataOutputStream(outToServer);
            commando = inFromUser.readLine();
            out.writeUTF(commando);
            inFromServer = client.getInputStream();
            DataInputStream in =
                    new DataInputStream(inFromServer);
            System.out.println("Server says " + in.readUTF());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        String serverName = "localhost";
        String commando;
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        try {
            client = new Socket(serverName, PORT);
            System.out.println("Listening to "
                    + client.getRemoteSocketAddress() + "...");
            outToServer = client.getOutputStream();
            DataOutputStream out =
                    new DataOutputStream(outToServer);
            System.out.println("Please provide a commando for server " + client.getRemoteSocketAddress());
            commando = inFromUser.readLine();
            out.writeUTF(commando);
            inFromServer = client.getInputStream();
            DataInputStream in =
                    new DataInputStream(inFromServer);
            System.out.println("Server says " + in.readUTF());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                switch (commands) {
                    case INITIALIZE:
                        startClient();
                        commands = commands.WAIT;
                        break;
                    case WAIT:
                        listen();
                        commands = commands.WAIT;
                }

            } catch (Throwable e) {
            }


        }
    }
}
