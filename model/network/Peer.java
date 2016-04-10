package model.network;
import java.io.*;
import java.net.*;
import java.util.*;

public class Peer {

    private int port;
    private ArrayList<Other> followers;
    private ArrayList<Other> following;
    private ArrayList<Other> connections;
    private ArrayList<Other> requests;

    public Peer(int port) {
        this.port = port;
        followers = new ArrayList<>();
        following = new ArrayList<>();
        requests = new ArrayList<>();
        connections = new ArrayList<>();
    }

    public void setPort(int port) {
        this.port = port;
    }
    
    public int getPort(){
        return port;
    }
    
    public int searchRequests(String IP){
        int index = -1;
        for (int i = 0; i < requests.size(); i++)
            if (requests.get(i).getIP().equals(IP))
                index = i;
        return index;
    }

    public void connectToOther(String IP) {
        try {
            Socket connection = new Socket(IP, port);
            DataOutputStream outToServer = new DataOutputStream(connection.getOutputStream());
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Connected to " + IP);
            System.out.println("Type your message: ");
            String msg = input.readLine();
            if (msg.equals("\"APPROVE\"\0")) {
                if (searchRequests(IP) != -1){
                    removeRequest(IP);
                    addFollower(IP);
                    msg = "Your follow request has been accepted.";
                    System.out.println("You are now following " + IP);
                }
                else
                    System.out.println(IP + "has not requested to follow you.");
                
            }
            else if (msg.equals("\"FOLLOW\"\0"))
                msg = "\"FOLLOW\"\0";
            outToServer.writeBytes(msg);
            connection.close();
        } catch (IOException E) {
            System.out.println("Unable to connect to " + IP);
        }

    }

    public void addFollower(String IP) {
        followers.add(new Other(IP, "user1"));
    }
    
    public void addFollowing(String IP){
        following.add(new Other(IP, "user1"));
    }
    
    public void addRequest(String IP) {
        requests.add(new Other(IP, "user1"));
    }
    
    public void removeRequest(String IP) {
        for (int i = 0; i < requests.size(); i++)
            if (requests.get(i).getIP().equals(IP))
                requests.remove(i);
    }

    public java.net.ServerSocket makeSocket(int portnum) throws IOException {
        ServerSocket socket = new ServerSocket(port);
        return socket;
    }

    public void mainLoop() {
        Thread t = new Thread(new ConnectListener(this));
        t.start();
    }
}
