package model.network;

import com.mysql.jdbc.MySQLConnection;
import com.sun.rowset.CachedRowSetImpl;
import controller.PeerToPeerDBController;
import model.AppDatabase;
import model.Transaction;
import net.proteanit.sql.DbUtils;
import utilities.dbconnection.MySQLConnector;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by John Israel on 4/12/2016.
 */
public class IncomingTransactionsHandler implements Runnable{

    Transaction transaction;
    PeerToPeerDBController controller;
    MySQLConnector connector;

    public IncomingTransactionsHandler(Transaction transaction, PeerToPeerDBController controller, MySQLConnector connector) {
        this.controller = controller;
        this.transaction = transaction;
        this.connector = connector;
    }

    @Override
    public void run() {
        ResultSet resultSet = null;
        CachedRowSet cachedRowSet = null;
        int responseTransactionType = 0;
        Transaction transactionResponse = null;

        /** AYAN BASTA EXECUTE KASI WALANG LINALABAS PERO SUBMIT MERON OH YEAH */
        /** write request sa central, to other node */
        if(transaction.getTransactionType() == Transaction.REQUEST_WRITE) {
        if (transaction.getAffectedNodeId() != AppDatabase.CENTRAL_NODE_ID && controller.nodeId == AppDatabase.CENTRAL_NODE_ID) {
            if (transaction.getTransactionType() == Transaction.REQUEST_WRITE) {

                controller.sendTransactionToNode(transaction, transaction.getAffectedNodeId());
            }
        } else if (transaction.getTargetNodeId() != controller.nodeId && transaction.getAffectedNodeId() == controller.nodeId) {
            // means relay, fromo other node, then from central
            if (!connector.isTableLocked()) {

                connector.executeUpdate(transaction.getQueryStatement());
                connector.lockTable();
                try {
                    Thread.sleep(transaction.getTimeDelay() * 1000);                 //1000 milliseconds is one second.
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                connector.unlockTable();
                responseTransactionType = Transaction.RESPONSE_WRITE_SUCCESS;
                controller.appendToLog("Write success!");
            } else {
                responseTransactionType = Transaction.RESPONSE_WRITE_FAIL;
                controller.appendToLog("Write failed.");
            }

            transactionResponse = new Transaction(responseTransactionType, AppDatabase.CENTRAL_NODE_ID, transaction.getAffectedNodeId());
            /** send response */
            controller.sendTransactionToNode(transactionResponse, transaction.getSourceNodeId());

        } else if (transaction.getSourceNodeId() == controller.nodeId && transaction.getTransactionType() == Transaction.RESPONSE_WRITE_SUCCESS) {
            // central will now pass to original asker
            //relay
            if (transaction.getAffectedNodeId() == AppDatabase.PALAWAN_NODE_ID)
                controller.sendTransactionToNode(transaction, AppDatabase.MARINDUQUE_NODE_ID);
            else if (transaction.getAffectedNodeId() == AppDatabase.MARINDUQUE_NODE_ID)
                controller.sendTransactionToNode(transaction, AppDatabase.PALAWAN_NODE_ID);
        }
    }
        else
        switch (transaction.getTransactionType()) {
            case Transaction.REQUEST_WRITE :
                if( !connector.isTableLocked() ) {

                    connector.executeUpdate(transaction.getQueryStatement());
                    connector.lockTable();
                    try {
                        Thread.sleep(transaction.getTimeDelay()*1000);                 //1000 milliseconds is one second.
                    } catch(InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                    connector.unlockTable();
                    responseTransactionType = Transaction.RESPONSE_WRITE_SUCCESS;
                    controller.appendToLog("Write success!");
                }
                else {
                    responseTransactionType = Transaction.RESPONSE_WRITE_FAIL;
                    controller.appendToLog("Write fail.");
                }

                transactionResponse = new Transaction(responseTransactionType, controller.nodeId, transaction.getAffectedNodeId());
                /** send response */
                controller.sendTransactionToNode(transactionResponse, transaction.getSourceNodeId());
                break;
            case Transaction.REQUEST_READ_UNCOMMITTED :
                /** Set Isolation Level */
                connector.updateIsolationLevel("READ UNCOMMITTED");
                /** prepare result set to be returned */
                if( !connector.isTableLocked() ) {
                    resultSet = controller.queryAndReturnResultSet(transaction.getQueryStatement());
                    try {
                        Thread.sleep(transaction.getTimeDelay()*1000);                 //1000 milliseconds is one second.
                    } catch(InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                    cachedRowSet = null;
                    try {
                        cachedRowSet = new CachedRowSetImpl();
                        cachedRowSet.populate(resultSet);
                        responseTransactionType = Transaction.RESPONSE_READ_SUCCESS;
                    } catch (SQLException e) {
                        responseTransactionType = Transaction.RESPONSE_READ_FAIL;
                        e.printStackTrace();
                    }
                }
                else {
                    System.out.println("PARTY PERO HINDI");
                    responseTransactionType = Transaction.RESPONSE_READ_FAIL;
                }

                /** check if success */
                transactionResponse = new Transaction(responseTransactionType, cachedRowSet, controller.nodeId, transaction.getAffectedNodeId());

                /** send response */
                controller.sendTransactionToNode(transactionResponse, transaction.getSourceNodeId());
                break;

            case Transaction.REQUEST_READ_COMMITTED :
                /** Set Isolation Level */
                connector.updateIsolationLevel("READ READ COMMITTED");
                /** prepare result set to be returned */
                if( !connector.isTableLocked() ) {
                    resultSet = controller.queryAndReturnResultSet(transaction.getQueryStatement());
                    cachedRowSet = null;
                    try {
                        cachedRowSet = new CachedRowSetImpl();
                        cachedRowSet.populate(resultSet);
                        responseTransactionType = Transaction.RESPONSE_READ_SUCCESS;
                    } catch (SQLException e) {
                        responseTransactionType = Transaction.RESPONSE_READ_FAIL;
                        e.printStackTrace();
                    }
                }
                else {
                    System.out.println("PARTY PERO HINDI");
                    responseTransactionType = Transaction.RESPONSE_READ_FAIL;
                }

                /** check if success */
                transactionResponse = new Transaction(responseTransactionType, cachedRowSet, controller.nodeId, transaction.getAffectedNodeId());

                /** send response */
                controller.sendTransactionToNode(transactionResponse, transaction.getSourceNodeId());
                break;
            case Transaction.REQUEST_READ_REPEATABLE :
                /** Set Isolation Level */
                connector.updateIsolationLevel("REPEATABLE READ");
                /** prepare result set to be returned */
                if( !connector.isTableLocked() ) {
                    resultSet = controller.queryAndReturnResultSet(transaction.getQueryStatement());
                    cachedRowSet = null;
                    try {
                        cachedRowSet = new CachedRowSetImpl();
                        cachedRowSet.populate(resultSet);
                        responseTransactionType = Transaction.RESPONSE_READ_SUCCESS;
                    } catch (SQLException e) {
                        responseTransactionType = Transaction.RESPONSE_READ_FAIL;
                        e.printStackTrace();
                    }
                }
                else {
                    System.out.println("PARTY PERO HINDI");
                    responseTransactionType = Transaction.RESPONSE_READ_FAIL;
                }

                /** check if success */
                transactionResponse = new Transaction(responseTransactionType, cachedRowSet, controller.nodeId, transaction.getAffectedNodeId());

                /** send response */
                controller.sendTransactionToNode(transactionResponse, transaction.getSourceNodeId());
                break;
            case Transaction.REQUEST_READ_SERIALIZEABLE :
                /** Set Isolation Level */
                connector.updateIsolationLevel("READ SERIALIZABLE");
                /** prepare result set to be returned */
                if( !connector.isTableLocked() ) {
                    resultSet = controller.queryAndReturnResultSet(transaction.getQueryStatement());
                    cachedRowSet = null;
                    try {
                        cachedRowSet = new CachedRowSetImpl();
                        cachedRowSet.populate(resultSet);
                        responseTransactionType = Transaction.RESPONSE_READ_SUCCESS;
                    } catch (SQLException e) {
                        responseTransactionType = Transaction.RESPONSE_READ_FAIL;
                        e.printStackTrace();
                    }
                }
                else {
                    System.out.println("PARTY PERO HINDI");
                    responseTransactionType = Transaction.RESPONSE_READ_FAIL;
                }

                /** check if success */
                transactionResponse = new Transaction(responseTransactionType, cachedRowSet, controller.nodeId, transaction.getAffectedNodeId());

                /** send response */
                controller.sendTransactionToNode(transactionResponse, transaction.getSourceNodeId());
                break;
            case Transaction.RESPONSE_READ_SUCCESS :
//                queryAndShowResult(transaction.getQueryStatement());
                controller.appendToLog("Successfully read from " + controller.getNodeNameFromId(transaction.getSourceNodeId()));
                try {
                    controller.view.setResultTableModel(DbUtils.resultSetToTableModel(transaction.getCrs().getOriginal()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case Transaction.RESPONSE_READ_FAIL :
                controller.appendToLog("Failed to read from " + controller.getNodeNameFromId(transaction.getSourceNodeId()));
                break;
            case Transaction.RESPONSE_WRITE_SUCCESS :
                controller.appendToLog("Successfully wrote to " + controller.getNodeNameFromId(transaction.getSourceNodeId()));
                break;
            case Transaction.RESPONSE_WRITE_FAIL :
                controller.appendToLog("Failed to write to " + controller.getNodeNameFromId(transaction.getSourceNodeId()));
        }
    }
}
