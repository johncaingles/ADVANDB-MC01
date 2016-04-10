package model;

import javax.swing.table.TableModel;
import java.io.Serializable;

public class Transaction implements Serializable {

    /** transaction types */
    public static final int REQUEST_READ_UNCOMMITTED  = 0;
    public static final int REQUEST_READ_COMMITTED    = 1;
    public static final int REQUEST_READ_REPEATABLE   = 2;
    public static final int REQUEST_READ_SERIALIZEABLE= 3;

    public static final int REQUEST_WRITE            = 4;

    public static final int RESPONSE_READ_SUCCESS    = 5;
    public static final int RESPONSE_READ_FAIL       = 7;
    public static final int RESPONSE_WRITE_SUCCESS   = 8;
    public static final int RESPONSE_WRITE_FAIL      = 9;

    /** attributes */
    private String queryStatement;
	private int transactionType;
    private int sourceNodeId;
	private TableModel resultModel;

    public Transaction(int transactionType, TableModel resultModel, int sourceNodeId) {
        this.transactionType = transactionType;
        this.resultModel = resultModel;
        this.sourceNodeId = sourceNodeId;
    }

    public Transaction(String queryStatement, int transactionType, int sourceNodeId) {
        this.queryStatement = queryStatement;
        this.transactionType = transactionType;
        this.sourceNodeId = sourceNodeId;
    }

    public int getSourceNodeId() {
        return sourceNodeId;
    }

    public void setSourceNodeId(int sourceNodeId) {
        this.sourceNodeId = sourceNodeId;
    }

    public TableModel getResultModel() {
        return resultModel;
    }

    public void setResultModel(TableModel resultModel) {
        this.resultModel = resultModel;
    }

    public String getQueryStatement() {
		return queryStatement;
	}
	public void setQueryStatement(String queryStatement) {
		this.queryStatement = queryStatement;
	}
	public int getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(int transactionType) {
		this.transactionType = transactionType;
	}
	
	
	
}
