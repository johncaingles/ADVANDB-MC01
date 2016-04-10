package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.ResultSet;

import javax.swing.table.TableModel;

import model.AppDatabase;
import model.Transaction;
import model.network.ClientRunner;
import model.network.ServerRunner;
import net.proteanit.sql.DbUtils;
import utilities.dbconnection.MySQLConnector;
import view.PeerToPeerDBView;

public class PeerToPeerDBController {
	
	/** Network stuff */
	private ServerRunner serverRunner;

    /** Model stuff */
    private int nodeId;
    
    /** SQL stuff */
    private MySQLConnector connector;
    
    private PeerToPeerDBView view;
    
	public PeerToPeerDBController(PeerToPeerDBView view, int nodeId) {
		this.view = view;
        this.nodeId = nodeId;

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
	}
	
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
		view.getTxtTime().setText(Long.toString(connector.getExeTime())+ " ms");
	}

    public ResultSet queryAndReturnResultSet(String query){
        ResultSet resultSet = connector.executeQuery(query);
        return resultSet;
    }

	public void executeCustomStatement(String query) {
		connector.executeStatement(query);
		view.getTxtTime().setText(Long.toString(connector.getExeTime())+ " ms");
	}

    public void receiveTransaction(Transaction transaction) {
        appendToLog("Received incoming transaction from " + getNodeNameFromId(transaction.getSourceNodeId()));
        System.out.println("recevied Query" + transaction.getQueryStatement());
        System.out.println("recevied Type" + transaction.getTransactionType());

        /** AYAN BASTA EXECUTE KASI WALANG LINALABAS PERO SUBMIT MERON OH YEAH */
        switch (transaction.getTransactionType()) {
            case Transaction.REQUEST_WRITE :
                executeCustomStatement(transaction.getQueryStatement());
                break;
            case Transaction.REQUEST_READ_UNCOMMITTED :
                /** Set Isolation Level */
                connector.updateIsolationLevel("READ UNCOMMITTED");
                /** prepare result set to be returned */
                ResultSet resultSet = queryAndReturnResultSet(transaction.getQueryStatement());
                TableModel resultModel = DbUtils.resultSetToTableModel(resultSet);

                /** check if success */
                int responseTransactionType;
                responseTransactionType = Transaction.RESPONSE_READ_SUCCESS;
                Transaction transactionResponse = new Transaction(Transaction.RESPONSE_READ_SUCCESS, resultModel, nodeId);

                /** send response */
                sendTransactionToNode(transactionResponse, transaction.getSourceNodeId());
                break;
            case Transaction.RESPONSE_READ_SUCCESS :
//                queryAndShowResult(transaction.getQueryStatement());
                appendToLog("Successfully read from " + getNodeNameFromId(transaction.getSourceNodeId()));
                view.setResultTableModel(transaction.getResultModel());
                break;
        }
    }

    public void sendTransactonFromGUI(String targetNode, String query, String queryType, String isolationLevel) {

        int targetNodeId = 0;
        int transactionType = 0;

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

        /** set target node id */
        if(targetNode.equalsIgnoreCase("Palawan")) {
            targetNodeId = AppDatabase.PALAWAN_NODE_ID;
        }
        else if(targetNode.equalsIgnoreCase("Marinduque")) {
            targetNodeId = AppDatabase.MARINDUQUE_NODE_ID;
        }
        else if(targetNode.equalsIgnoreCase("Central")) {
            targetNodeId = AppDatabase.CENTRAL_NODE_ID;
        }

        Transaction transaction = new Transaction(query, transactionType, this.nodeId);

        sendTransactionToNode(transaction, targetNodeId);
    }

	public void sendTransactionToNode( Transaction transaction, int targetNodeId) {

        /** start client as thread */
        ClientRunner clientRunner = new ClientRunner();
		Thread clientThread = new Thread(clientRunner);

        String targetIpAddress = getIpAddressFromId(targetNodeId);
        int targetPortNum= getPortNumberFromId(targetNodeId);
        /** send transaction */
        appendToLog("Sending transaction to " + getNodeNameFromId(targetNodeId));
        clientRunner.sendTransactionToClient(transaction, targetIpAddress, targetPortNum);
	}

    public void appendToLog(String log) {
        String logs = view.getLogText() + log + "\n" ;
        view.setLogText(logs);
    }

    public void initializeUI() {
        view.setQueryTextArea(getQueryStatement(view.getInputQueryType(), view.getInputQueryNode()));

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

    private String getNodeNameFromId(int id) {
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
        if( inputQueryType.equalsIgnoreCase("Read")) {
            queryStatement = "SELECT *" + "\n"
                    + "FROM hpq_hh";
        } else
        if( inputQueryType.equalsIgnoreCase("Write")) {
            queryStatement = "";
        }

        return queryStatement;
    }
}
