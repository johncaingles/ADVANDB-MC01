package model.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import controller.PeerToPeerDBController;
import model.AppDatabase;
import model.Transaction;

public class ClientRunner implements Runnable {

    private PeerToPeerDBController controller;

    private Transaction transaction;
    private String ipAddress;
    private int portNumber;

    public ClientRunner(PeerToPeerDBController controller, Transaction transaction, String ipAddress, int portNumber) {
        this.controller = controller;
        this.transaction = transaction;
        this.ipAddress = ipAddress;
        this.portNumber = portNumber;
    }

	@Override
	public void run() {

        // haha whut
        // dapat runnable/ thread pa rin daw siya in case of multiple sendings
        try {
            System.out.println("about to send ip: " + ipAddress);
            System.out.println("about to send port: " + portNumber);
            Socket clientSocket = new Socket(ipAddress, portNumber);
            ObjectOutputStream outToClient;
            outToClient = new ObjectOutputStream(clientSocket.getOutputStream());
            outToClient.writeObject(transaction);
        } catch (IOException e) {
            controller.failedToSendTransaction( transaction,  ipAddress,  portNumber);
        }


	}
	
}
