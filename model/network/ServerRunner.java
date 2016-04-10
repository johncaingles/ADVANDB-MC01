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
	
	public ServerRunner(){}
	public ServerRunner(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}
	
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
				System.out.println("Socket Extablished... basta received somethings");
				System.out.println(clientSocket.getRemoteSocketAddress());
				// Create input and output streams to client

				/** use me */
				//ObjectOutputStream outToClient = new ObjectOutputStream(clientSocket.getOutputStream());

				ObjectInputStream inFromClient = new ObjectInputStream(clientSocket.getInputStream());

				/* Create Message object and retrive information */
	//            LinkedList<Message> inList = new LinkedList<>();
	//            Message inMsg = null;
	//            inList = (LinkedList<Message>)inFromClient.readObject();
				Transaction receivedTransaction = (Transaction)inFromClient.readObject();
				if(receivedTransaction != null){
					controller.receiveTransaction(receivedTransaction);
				}
//				System.out.println("I RECEIVED: "+ receivedTransaction.getQueryStatement());
	//            inList = (LinkedList<Message>)inFromClient.readObject();
	//            inMsg = inList.pop();
				/* Send the modified Message object back */
				/** use me */
				//Transaction receivedTransaction = new Transaction("sup", "niggah");
				/** use me */
				//outToClient.writeObject(receivedTransaction);
        	}
	    } catch (Exception e) {
	        System.err.println("Server Error: " + e.getMessage());
	        System.err.println("Localized: " + e.getLocalizedMessage());
	        System.err.println("Stack Trace: " + e.getStackTrace());
	        System.err.println("To String: " + e.toString());
	    }
	}
	

}
