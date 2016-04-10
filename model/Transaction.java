package model;

import java.io.Serializable;

public class Transaction implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6316586635185135642L;
	private String query;
	private String type;
		
	public Transaction(String query, String type) {
		this.query = query;
		this.type = type;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
