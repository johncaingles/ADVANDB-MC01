package controller;

import model.AppDatabase;
import model.Transaction;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by John Israel on 4/11/2016.
 */
public class BroadcastRunner implements Runnable {

    private Socket centralSocket;
    private Socket marinduqueSocket;
    private Socket palawanSocket;

    private PeerToPeerDBController controller;

    public BroadcastRunner(PeerToPeerDBController controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        while(true){

            try {
                (this.centralSocket = new Socket(AppDatabase.CENTRAL_IP_ADDRESS, AppDatabase.CENTRAL_PORT_NUMBER)).close();
                System.out.println("central is UP");
            } catch (IOException e) {
                System.out.println("central is DOWN");
            }
            try {
                (this.marinduqueSocket = new Socket(AppDatabase.MARINDUQUE_IP_ADDRESS, AppDatabase.MARINDUQUE_PORT_NUMBER)).close();;
                System.out.println("marinduque is UP");
            } catch (IOException e) {
                System.out.println("marinduque is DOWN");
            }
            try {
                (this.palawanSocket = new Socket(AppDatabase.PALAWAN_IP_ADDRESS, AppDatabase.PALAWAN_PORT_NUMBER)).close();
                System.out.println("palawan is UP");
            } catch (IOException e) {
                System.out.println("palawan is DOWN");
            }

            System.out.println();
        }

    }
}
