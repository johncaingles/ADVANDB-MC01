package model;

import javax.sql.rowset.CachedRowSet;
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

    public static final int AVAILABILITY_BROADCAST = 10;

    /** attributes */
    private String queryStatement;
	private int transactionType;
    private int sourceNodeId;
    private int affectedNodeId;
    private int targetNodeId;
	private CachedRowSet crs;

    private int timeDelay;

    public Transaction(int transactionType, CachedRowSet crs, int sourceNodeId, int affectedNodeId) {
        this.transactionType = transactionType;
        this.crs = crs;
        this.sourceNodeId = sourceNodeId;
        this.affectedNodeId = affectedNodeId;
    }

    public Transaction(String queryStatement, int transactionType, int sourceNodeId, int affectedNodeId) {
        this.queryStatement = queryStatement;
        this.transactionType = transactionType;
        this.sourceNodeId = sourceNodeId;
        this.affectedNodeId = affectedNodeId;
    }

    public Transaction(int transactionType, int sourceNodeId, int affectedNodeId) {
        this.transactionType = transactionType;
        this.sourceNodeId = sourceNodeId;
        this.affectedNodeId = affectedNodeId;
    }

    public int getTimeDelay() {
        return timeDelay;
    }

    public void setTimeDelay(int timeDelay) {
        this.timeDelay = timeDelay;
    }

    public int getTargetNodeId() {
        return targetNodeId;
    }

    public void setTargetNodeId(int targetNodeId) {
        this.targetNodeId = targetNodeId;
    }

    public int getAffectedNodeId() {
        return affectedNodeId;
    }

    public void setAffectedNodeId(int affectedNodeId) {
        this.affectedNodeId = affectedNodeId;
    }

    public int getSourceNodeId() {
        return sourceNodeId;
    }

    public void setSourceNodeId(int sourceNodeId) {
        this.sourceNodeId = sourceNodeId;
    }

    public CachedRowSet getCrs() {
        return crs;
    }

    public void setCrs(CachedRowSet crs) {
        this.crs = crs;
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
