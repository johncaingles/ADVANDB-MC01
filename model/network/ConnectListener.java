package model.network;
import java.io.*;
import java.net.*;
import java.util.*;

public class ConnectListener implements Runnable {

    private Peer p;

    public ConnectListener(Peer p) {
        this.p = p;
    }
    
    @Override
    public void run() {
        try {
            ServerSocket listenSocket = new ServerSocket(p.getPort());
            while (true) {
                Socket connectionSocket = listenSocket.accept();
                Thread t = new Thread(new Connection(connectionSocket, p));
                t.start();
            }
        } catch (IOException E) {
            E.printStackTrace();
        }
    }

}
