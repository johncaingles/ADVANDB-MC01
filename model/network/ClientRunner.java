package model.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import model.AppDatabase;
import model.Transaction;

public class ClientRunner implements Runnable {
	
	private Socket socketToHost;
	
	public ClientRunner(){}
	public ClientRunner(Socket socketToHost){
		this.socketToHost = socketToHost;
	}

	@Override
	public void run() {
		try {
	        // Create the socket
//	        socketToHost = new Socket(hostIpAddress, portNum);
	        // Create the input & output streams to the server
//	        ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
	        /** --------------------------- USEFULL SHIT YUNG SA TAAS ---------------------- **/
//	        ObjectInputStream inFromServer = new ObjectInputStream(socketToHost.getInputStream());

	        /* Send the Message Object to the server */
//	        outToServer.writeObject(msgList);            
	        /** --------------------------- USEFULL SHIT YUNG SA TAAS ---------------------- **/
	        
//	        Transaction msgFrmServer = (Transaction)inFromServer.readObject();
//	        System.out.println(msgFrmServer.getQueryStatement());
//	        socketToHost.close();

	    } catch (Exception e) {
	        System.err.println("Client Error: " + e.getMessage());
	        System.err.println("Localized: " + e.getLocalizedMessage());
	        System.err.println("Stack Trace: " + e.getStackTrace());
	    }	
	}
	
	public void sendTransactionToClient(Transaction transaction, String ipAddress, int portNumber){
		
		try {
			Socket clientSocket = new Socket(ipAddress, portNumber);
			 ObjectOutputStream outToClient;
			outToClient = new ObjectOutputStream(clientSocket.getOutputStream());
			outToClient.writeObject(transaction);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
	}
	
}
