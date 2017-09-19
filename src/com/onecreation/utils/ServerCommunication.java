package com.onecreation.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerCommunication {
    public static final int MAX_PORT_NUMBER = 65535;

    private ServerSocket serverSocket;
    private Socket connection;

    public ServerCommunication(String portNumber) {
        int parsedInt = 0;
        try{
            parsedInt = Integer.parseInt(portNumber);
        }catch (NumberFormatException e){
            e.printStackTrace(System.out);
        }
        serverSocket = getNextAvailablePort(parsedInt);

    }

    public InputStream getInputStream() {
        InputStream in = null;
        try {
            connection = serverSocket.accept();
            in = connection.getInputStream();
        } catch (IOException e) {
            System.out.println("An error has occured");
            e.printStackTrace(System.out);
        }
        return in;
    }
    public static ServerSocket getNextAvailablePort(int portNumber) {

        ServerSocket availablePort = null;
        while (portNumber != MAX_PORT_NUMBER && availablePort == null) {
            try {
                availablePort = new ServerSocket(portNumber);
            } catch (IOException e) {
                System.out.println(String.format("Port %d is not available", portNumber));
                portNumber++;
            }
        }
        if (availablePort != null) {
            System.out.println(String.format("Server listening on port %s", portNumber));
        } else {
            System.out.println("Unable to find port to listen");
        }
        return availablePort;

    }

    public OutputStream getOutputStream() {
        OutputStream out = null;
        try {
            out = connection.getOutputStream();
        } catch (IOException e) {
            System.out.println("An error has occurred");
            e.printStackTrace(System.out);
        }
        return out;
    }
}
