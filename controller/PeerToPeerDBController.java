package controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;

import javax.swing.table.TableModel;

import model.AppDatabase;
import model.Transaction;
import model.network.Peer;
import net.proteanit.sql.DbUtils;
import utilities.dbconnection.MySQLConnector;
import view.PeerToPeerDBView;

public class PeerToPeerDBController {
	
	/** Network stuff */
	private ServerRunner serverRunner;
	private ClientRunner clientRunner;
	private ServerSocket serverSocket;
//    private Node node;
    
    /** Input stuff */
    private String queryType;
    private String isolationType;

    /** Model stuff */
    private int nodeType;
    
    /** SQL stuff */
    private MySQLConnector connector;
    
    private PeerToPeerDBView view;
    
	public PeerToPeerDBController(PeerToPeerDBView view, int nodeType) {
		this.view = view;
        this.nodeType = nodeType;
		/** DB stuff */
        String dbSchema = "";
        switch (nodeType){
            case AppDatabase.CENTRAL_NODE_CONSTANT: dbSchema = "hpq_central"; break;
            case AppDatabase.PALAWAN_NODE_CONSTANT: dbSchema = "hpq_palawan"; break;
            case AppDatabase.MARINDUQUE_NODE_CONSTANT: dbSchema = "hpq_marinduque"; break;
        }
		this.connector = new MySQLConnector(
			"jdbc:mysql://localhost:3306/", 
			dbSchema,
			"com.mysql.jdbc.Driver", 
			"root", 
			"p@ssword");
		connector.getConnection();
		
		/** network stuff */
		startServer();
	}

//	public void connectToOther() {
//		try {
//            Socket connection = new Socket(IP, port);
//            DataOutputStream outToServer = new DataOutputStream(connection.getOutputStream());
//            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
//           
//            String msg = "";
//            
//            outToServer.writeBytes(msg);
//            connection.close();
//        } catch (IOException E) {
//            System.out.println("Unable to connect to " + IP);
//        }
//	}
	
	private void startServer() {
		ServerSocket serverSocket = null ;
		try {
			serverSocket = new ServerSocket(AppDatabase.PORT_NUMBER);
            serverRunner = new ServerRunner(serverSocket, this);
            Thread serverThread = new Thread(serverRunner);
            serverThread.start();
		} catch (IOException e) {
			appendToLog("Error starting server, the localhost might already be in use");
		}
	}

	public void submitCustomStatement(String query) {
		ResultSet resultSet = connector.executeQuery(query);
		TableModel model = DbUtils.resultSetToTableModel(resultSet);
		view.getResultTable().setModel(model);
		view.getTxtTime().setText(Long.toString(connector.getExeTime())+ " ms");
	}

	public void executeCustomStatement(String query) {
		connector.executeStatement(query);
		view.getTxtTime().setText(Long.toString(connector.getExeTime())+ " ms");
	}

    public void receiveTransaction(Transaction transaction) {
        appendToLog("Received incoming transaction");
        System.out.println("recevied Query" + transaction.getQuery());
        System.out.println("recevied Type" + transaction.getType());

        /** AYAN BASTA EXECUTE KASI WALANG LINALABAS PERO SUBMIT MERON OH YEAH */
        if(transaction.getType().equals("write"))
            executeCustomStatement(transaction.getQuery());
        else if (transaction.getType().equals("read")) {
            submitCustomStatement(transaction.getQuery());
        }
        submitCustomStatement(transaction.getQuery());
    }


	public void queryToNode(String ipAddress, String query, String queryType) {
        /** prepare transaction to send */
		Transaction transaction = new Transaction(query, queryType);

        /** start client as thread */
        ClientRunner clientRunner = new ClientRunner();
		Thread clientThread = new Thread(clientRunner);

        /** send transaction */
        clientRunner.sendTransactionToClient(transaction, ipAddress);
	}

    public void appendToLog(String log) {
        String logs = view.getLogText() + "\n" + log;
        view.setLogText(logs);
    }

    public void setQueryType(String inputQueryType) {
        this.queryType = inputQueryType;
    }

    public void setIsolationType(String inputIsolationType) {
        this.isolationType = inputIsolationType;
    }
}
