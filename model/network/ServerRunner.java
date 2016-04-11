package model.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import controller.PeerToPeerDBController;
import model.Transaction;

public class ServerRunner implements Runnable {	

	private ServerSocket serverSocket;
	private PeerToPeerDBController controller;

	public ServerRunner(ServerSocket serverSocket, PeerToPeerDBController controller) {
		this.serverSocket = serverSocket;
		this.controller = controller;
	}
	
	@Override
	public void run() {
		try {
        	while(true){
				// Create the Client Socket FOR ACCEPTING
				Socket clientSocket = serverSocket.accept();
                System.out.println("IYAK");
                ObjectInputStream inFromClient = new ObjectInputStream(clientSocket.getInputStream());
                System.out.println("PO");
                Transaction receivedTransaction = (Transaction)inFromClient.readObject();
				if(receivedTransaction != null){
					controller.receiveTransaction(receivedTransaction);
				}
        	}
	    } catch (Exception e) {
			System.out.println("WTF!!!!!!!!");
			System.err.println("Server Error: " + e.getMessage());
	        System.err.println("Localized: " + e.getLocalizedMessage());
	        System.err.println("Stack Trace: " + e.getStackTrace());
	        System.err.println("To String: " + e.toString());
	    }
	}
	

}
