package model.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import model.AppDatabase;
import model.Transaction;

public class ClientRunner implements Runnable {

	@Override
	public void run() {
		try {
            // haha whut
            // dapat runnable/ thread pa rin daw siya in case of multiple sendings
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
