package model.network;
import java.io.*;
import java.net.*;
import java.util.*;

public class Connection implements Runnable {

    private Socket connectionSocket;
    private Peer p;

    public Connection(Socket s, Peer p) {
        this.connectionSocket = s;
        this.p = p;
    }
    
    @Override
    public void run() {
        try {
            String msg;
            InetAddress IPsender;
            BufferedReader fromPeer = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            msg = fromPeer.readLine();
            IPsender = connectionSocket.getInetAddress();
            System.out.println("You have connected to " + IPsender);
            if (msg.equals("\"FOLLOW\"")) {
                System.out.println("You have received a follow request from: " + IPsender);
                p.addRequest(IPsender.toString());
            } else if (msg.equals("\"APPROVE\"")) {
                System.out.println(IPsender + "approved your follow request");
                p.addFollowing(IPsender.toString());
                p.removeRequest(IPsender.toString());
            } else if (msg.equals("\"UNFOLLOW\"")) {
                System.out.println(IPsender + "unfollowed you.");
                p.removeRequest(IPsender.toString());
            } else {
                System.out.println(msg + " sent from: " + IPsender);
            }
            connectionSocket.close();
        } catch (IOException E) {
            System.out.println("Error!");
        }
    }
}
