package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;
import javax.swing.table.TableModel;

import com.sun.rowset.CachedRowSetImpl;
import model.AppDatabase;
import model.Transaction;
import model.network.ClientRunner;
import model.network.IncomingTransactionsHandler;
import model.network.ServerRunner;
import net.proteanit.sql.DbUtils;
import utilities.dbconnection.MySQLConnector;
import view.PeerToPeerDBView;

public class PeerToPeerDBController {
	
	/** Network stuff */
	private ServerRunner serverRunner;
//    private BroadcastRunner broadcastRunner;

    /** Model stuff */
    public int nodeId;
    private ArrayList<Transaction> transactions;
    
    /** SQL stuff */
    private MySQLConnector connector;
    
    public PeerToPeerDBView view;
    
	public PeerToPeerDBController(PeerToPeerDBView view, int nodeId) {
		this.view = view;
        this.nodeId = nodeId;
        this.transactions = new ArrayList<>();

		/** DB stuff */
        String dbSchema = getSchemaNameFromId(nodeId);
		this.connector = new MySQLConnector(
			"jdbc:mysql://localhost:3306/", 
			dbSchema,
			"com.mysql.jdbc.Driver", 
			"root", 
			"p@ssword");
		connector.getConnection();
		
		/** network stuff */
		startServer();
//        startAvailabilityChecker();
	}

//    private void startAvailabilityChecker() {
//        broadcastRunner = new BroadcastRunner(this);
//        Thread availabilityChecker = new Thread(broadcastRunner);
//        availabilityChecker.start();
//    }

    private void startServer() {
		ServerSocket serverSocket = null ;
		try {
            serverSocket = new ServerSocket(getPortNumberFromId(this.nodeId));
            serverRunner = new ServerRunner(serverSocket, this);
            Thread serverThread = new Thread(serverRunner);
            serverThread.start();
		} catch (IOException e) {
			appendToLog("Error starting server, port number already in use");
		}
	}



    public void queryAndShowResult(String query) {
		ResultSet resultSet = connector.executeQuery(query);
		TableModel model = DbUtils.resultSetToTableModel(resultSet);
		view.getResultTable().setModel(model);
		//view.getTxtTime().setText(Long.toString(connector.getExeTime())+ " ms");
	}

    public ResultSet queryAndReturnResultSet(String query){
        ResultSet resultSet = connector.executeQuery(query);
        return resultSet;
    }

	public void executeCustomStatement(String query) {
		connector.executeStatement(query);
		//view.getTxtTime().setText(Long.toString(connector.getExeTime())+ " ms");
	}

    public void receiveTransaction(Transaction transaction) {
        appendToLog("Received incoming transaction from " + getNodeNameFromId(transaction.getSourceNodeId()));
        System.out.println("recevied Query" + transaction.getQueryStatement());
        System.out.println("recevied Type" + transaction.getTransactionType());

        if( this.nodeId == AppDatabase.CENTRAL_NODE_ID ) {

        }

        IncomingTransactionsHandler transactionsHandler = new IncomingTransactionsHandler(transaction, this, connector);
        Thread transactionsHandlerThread = new Thread(transactionsHandler);
        transactionsHandlerThread.start();

    }

    public void sendTransactionFromGUI(String targetNode, String query, String queryType, String isolationLevel) {

        int targetNodeId = 0;
        int transactionType = 0;
        int affectedNodeId = 0;

        /** Set transaction type */
        if(queryType.equalsIgnoreCase("Read")) {

            if(isolationLevel.equalsIgnoreCase("Read uncommitted")) {
                transactionType = Transaction.REQUEST_READ_UNCOMMITTED;;
            } else
            if(isolationLevel.equalsIgnoreCase("Read commited")) {
                transactionType = Transaction.REQUEST_READ_COMMITTED;
            } else
            if(isolationLevel.equalsIgnoreCase("Read repeatable")) {
                transactionType = Transaction.REQUEST_READ_REPEATABLE;
            } else
            if(isolationLevel.equalsIgnoreCase("Serializeable")) {
                transactionType = Transaction.REQUEST_READ_SERIALIZEABLE;
            }

        } else if(queryType.equalsIgnoreCase("Write")) {
            transactionType = Transaction.REQUEST_WRITE;
        }

        System.out.println("i am target: " + targetNode);
        /** set target node id */
        if(targetNode.equalsIgnoreCase("Palawan")) {
            affectedNodeId = AppDatabase.PALAWAN_NODE_ID;
        }
        else if(targetNode.equalsIgnoreCase("Marinduque")) {
            affectedNodeId = AppDatabase.MARINDUQUE_NODE_ID;
        }
        else if(targetNode.equalsIgnoreCase("Central")) {
            affectedNodeId = AppDatabase.CENTRAL_NODE_ID;
        }

        /** Prepare transaction */
        Transaction transaction = new Transaction(query, transactionType, this.nodeId, affectedNodeId );

        /** choose target for realzszzsz */
        targetNodeId = chooseTargetNodeId(this.nodeId, transaction);
        transaction.setTargetNodeId(targetNodeId);
        sendTransactionToNode(transaction, targetNodeId);
    }

    private int chooseTargetNodeId(int nodeId, Transaction transaction) {

        int targetNodeId = -1;

        if(transaction.getTransactionType() == Transaction.REQUEST_WRITE){

            return targetNodeId = AppDatabase.CENTRAL_NODE_ID;
        }

        /** check if reading local */
        if(transaction.getAffectedNodeId() == this.nodeId)
            return targetNodeId = nodeId;
        else if(nodeId == AppDatabase.CENTRAL_NODE_ID)
            return targetNodeId = nodeId;
        else return AppDatabase.CENTRAL_NODE_ID;

//        switch( AppDatabase.CENTRAL_AVAILABILITY ) {
//            case 1 : targetNodeId = AppDatabase.CENTRAL_NODE_ID; break;
//            case -1 : targetNodeId = affectedNodeId; break;
//        }

//        if( this.nodeId == AppDatabase.CENTRAL_NODE_ID ) {
//            targetNodeId = affectedNodeId;
//        }
//        else targetNodeId = AppDatabase.CENTRAL_NODE_ID;

    }

    public void sendTransactions() {
        for(Transaction transaction : transactions) {
            int targetNodeId = chooseTargetNodeId(this.nodeId, transaction);
            sendTransactionToNode(transaction, targetNodeId);
        }
    }

    public void sendTransactionToNode( Transaction transaction, int targetNodeId) {

        String targetIpAddress = getIpAddressFromId(targetNodeId);
        int targetPortNum= getPortNumberFromId(targetNodeId);

        /** send transaction */
        appendToLog("Sending transaction to " + getNodeNameFromId(targetNodeId));
        /** start client as thread */
        ClientRunner clientRunner = new ClientRunner(this, transaction, targetIpAddress, targetPortNum);
        Thread clientThread = new Thread(clientRunner);
        clientThread.start();
	}

    public void failedToSendTransaction(Transaction transaction, String ipAddress, int portNumber) {
        /** if write operation */
        if( transaction.getTransactionType() == Transaction.REQUEST_WRITE ) {
            appendToLog("Unable to perform write operation, " + getNodeNameFromId(transaction.getAffectedNodeId())   + " is down" );
            if( this.nodeId == AppDatabase.CENTRAL_NODE_ID && transaction.getTargetNodeId() == this.nodeId ) {
                // relay back to original requester
                transaction.setTransactionType(Transaction.RESPONSE_WRITE_FAIL);
                transaction.setTargetNodeId(transaction.getSourceNodeId());
                transaction.setSourceNodeId(nodeId);
                sendTransactionToNode(transaction, transaction.getSourceNodeId());
            }
        }
        /** if read operation */
        else if( transaction.getTransactionType() == Transaction.REQUEST_READ_UNCOMMITTED ) {

            if(nodeId!= AppDatabase.CENTRAL_NODE_ID && transaction.getTargetNodeId() == AppDatabase.CENTRAL_NODE_ID && transaction.getSourceNodeId() == AppDatabase.CENTRAL_NODE_ID) {
                appendToLog(getNodeNameFromId(transaction.getAffectedNodeId()) + " server is down");
                appendToLog("Unable to perform read operation on " + getNodeNameFromId(transaction.getTargetNodeId()));
            } else
            if(nodeId!= AppDatabase.CENTRAL_NODE_ID ) {
                appendToLog("Central server is down");
                appendToLog("Unable to perform read operation on Central, will read directly from " + getNodeNameFromId(transaction.getAffectedNodeId()) + " node instead");
                transaction.setSourceNodeId(AppDatabase.CENTRAL_NODE_ID);
                sendTransactionToNode(transaction, transaction.getAffectedNodeId());
            }
            else
            if(nodeId == AppDatabase.CENTRAL_NODE_ID) {
                appendToLog(getNodeNameFromId(transaction.getTargetNodeId()) + " server is down");
                appendToLog("Unable to perform read operation on " + getNodeNameFromId(transaction.getTargetNodeId()));
            }
        }
        else if( transaction.getTransactionType() == Transaction.REQUEST_READ_COMMITTED ) {
            if(nodeId!= AppDatabase.CENTRAL_NODE_ID) {
                appendToLog("Unable to perform read operation on Central, will read directly from " + getNodeNameFromId(transaction.getAffectedNodeId()) + " node instead");
                transaction.setTargetNodeId(transaction.getAffectedNodeId());
                sendTransactionToNode(transaction, transaction.getTargetNodeId());
            }
        }
        else if( transaction.getTransactionType() == Transaction.REQUEST_READ_REPEATABLE ) {
            if(nodeId!= AppDatabase.CENTRAL_NODE_ID) {
                appendToLog("Unable to perform read operation on Central, will read directly from " + getNodeNameFromId(transaction.getAffectedNodeId()) + " node instead");
                transaction.setTargetNodeId(transaction.getAffectedNodeId());
                sendTransactionToNode(transaction, transaction.getTargetNodeId());
            }
        }
        else if( transaction.getTransactionType() == Transaction.REQUEST_READ_SERIALIZEABLE ) {
            if(nodeId!= AppDatabase.CENTRAL_NODE_ID) {
                appendToLog("Unable to perform read operation on Central, will read directly from " + getNodeNameFromId(transaction.getAffectedNodeId()) + " node instead");
                transaction.setTargetNodeId(transaction.getAffectedNodeId());
                sendTransactionToNode(transaction, transaction.getTargetNodeId());
            }
        }
    }

    public void appendToLog(String log) {
        String logs = view.getLogText() + log + "\n" ;
        view.setLogText(logs);
        view.scrollDownLogTxtAr();
    }

    public void initializeUI() {
        view.setQueryTextArea(getQueryStatement(view.getInputQueryType(), view.getInputQueryNode()));
        view.setTxtfldTimeDelay("0");
    }

    private String getIpAddressFromId(int id) {
        switch (id){
            case AppDatabase.CENTRAL_NODE_ID: return AppDatabase.CENTRAL_IP_ADDRESS;
            case AppDatabase.PALAWAN_NODE_ID: return AppDatabase.PALAWAN_IP_ADDRESS;
            case AppDatabase.MARINDUQUE_NODE_ID: return AppDatabase.MARINDUQUE_IP_ADDRESS;
        }
        return "";
    }

    private int getPortNumberFromId(int id) {
        switch (id){
            case AppDatabase.CENTRAL_NODE_ID: return AppDatabase.CENTRAL_PORT_NUMBER;
            case AppDatabase.PALAWAN_NODE_ID: return AppDatabase.PALAWAN_PORT_NUMBER;
            case AppDatabase.MARINDUQUE_NODE_ID: return AppDatabase.MARINDUQUE_PORT_NUMBER;
        }
        return -1;
    }

    public String getNodeNameFromId(int id) {
        switch (id){
            case AppDatabase.CENTRAL_NODE_ID: return "Central";
            case AppDatabase.PALAWAN_NODE_ID: return "Palawan";
            case AppDatabase.MARINDUQUE_NODE_ID: return "Marinduque";
        }
        return "";
    }

    private String getSchemaNameFromId(int id) {
        switch (nodeId){
            case AppDatabase.CENTRAL_NODE_ID: return "hpq_central";
            case AppDatabase.PALAWAN_NODE_ID: return "hpq_palawan";
            case AppDatabase.MARINDUQUE_NODE_ID: return"hpq_marinduque";
        }
        return "";
    }

    public String getQueryStatement(String inputQueryType, String inputQueryNode) {
        String queryStatement = "";
        System.out.println("queryStatement = " + queryStatement);
        if( inputQueryType.equalsIgnoreCase("Read All")) {
            queryStatement = "SELECT *" + "\n"
                          + "FROM hpq_hh";
        } else
        if( inputQueryType.equalsIgnoreCase("Read Item")) {
            queryStatement = "SELECT * from hpq_hh " + "\n" +
                    "WHERE id = 16818 ";
        } else
        if( inputQueryType.equalsIgnoreCase("Write - Update")) {
            queryStatement = "UPDATE hpq_hh " + "\n" +
                    "SET wagcsh=78000 " + "\n" +
                    "WHERE id = 16818";
        } else
        if( inputQueryType.equalsIgnoreCase("Write - Delete")) {
            queryStatement = "DELETE FROM hpq_hh  " + "\n" +
                    "WHERE id = 16818";
        }

        return queryStatement;
    }
    
    public String addTransactionFromGUI(String targetNode, String query, String queryType, String isolationLevel, int timeDelay)
    {
    	int targetNodeId = 0;
        int transactionType = 0;
        int affectedNodeId = 0;

        /** Set transaction type */
        if(queryType.contains("Read")) {

            if(isolationLevel.equalsIgnoreCase("Read uncommitted")) {
                transactionType = Transaction.REQUEST_READ_UNCOMMITTED;;
            } else
            if(isolationLevel.equalsIgnoreCase("Read commited")) {
                transactionType = Transaction.REQUEST_READ_COMMITTED;
            } else
            if(isolationLevel.equalsIgnoreCase("Read repeatable")) {
                transactionType = Transaction.REQUEST_READ_REPEATABLE;
            } else
            if(isolationLevel.equalsIgnoreCase("Serializeable")) {
                transactionType = Transaction.REQUEST_READ_SERIALIZEABLE;
            }

        } else if(queryType.contains("Write")) {
            transactionType = Transaction.REQUEST_WRITE;
        }

        System.out.println("i am target: " + targetNode);
        /** set target node id */
        if(targetNode.equalsIgnoreCase("Palawan")) {
            affectedNodeId = AppDatabase.PALAWAN_NODE_ID;
        }
        else if(targetNode.equalsIgnoreCase("Marinduque")) {
            affectedNodeId = AppDatabase.MARINDUQUE_NODE_ID;
        }
        else if(targetNode.equalsIgnoreCase("Central")) {
            affectedNodeId = AppDatabase.CENTRAL_NODE_ID;
        }

        /** Prepare transaction */
        Transaction transaction = new Transaction(query, transactionType, this.nodeId, affectedNodeId );
        transaction.setTimeDelay(timeDelay);
        transactions.add(transaction);
		return  targetNode +" - " +  query +" - "+ queryType + " - "+  isolationLevel + " - " + transaction.getTimeDelay() + "secs";

    }
    
    public void deleteTransactionFromGUI(int i)
    {
    	transactions.remove(i);
    }

}
