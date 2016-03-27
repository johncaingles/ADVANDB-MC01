package controller;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.table.TableModel;

import model.AppDatabase;
import net.proteanit.sql.DbUtils;
import utilities.dbconnection.MySQLConnector;
import view.OLAPView;

public class OLAPViewController {
	private OLAPView view;
	private MySQLConnector connector;
	private LinkedHashMap<String, LinkedHashMap<String, String>> queryMap;
	private AppDatabase appDatabase;
	
	private String finalQuery;
	private String selectStatement;
	private String fromStatement;
	private String whereStatement;
	private String groupByStatement;
	
	private int locationHierarchy;
	private int accessibilityHierarchy;
	private int educationHierarchy;
	
	public OLAPViewController(OLAPView view) {
		this.view = view;
		this.connector = new MySQLConnector(
				"jdbc:mysql://localhost:3306/", 
				"advandb_mco2", 
				"com.mysql.jdbc.Driver", 
				"root", 
				"p@ssword");
		connector.getConnection();
		this.appDatabase = new AppDatabase();
		/** Initialize list of queries */
		this.queryMap = appDatabase.getMapOfQueries();
		
		finalQuery = "";
		selectStatement = "SELECT ";
		fromStatement = "FROM fact f, citizen c, education e, household h";
		whereStatement = "WHERE f.hh_id=h.hh_id AND f.citizen_id=c.citizen_id AND f.educ_id=e.educ_id";
		groupByStatement = "GROUP BY ";
		
		locationHierarchy = 0;
		accessibilityHierarchy = 0;
		educationHierarchy = 0;
		
		refreshQuery();
	}

	public void submitQuery(String queryType, String optimizationType) {
		// TODO Auto-generated method stub
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
	
	public void initializeViewData() {
//		/** Get keys(query list) */
//		ArrayList<String> queryList = new ArrayList<>();
//		for ( String key : queryMap.keySet() ) {
//			queryList.add(key);
//		}
//		/** Set model for queries */
//		DefaultComboBoxModel queryModel = new DefaultComboBoxModel( queryList.toArray() );
//		view.setQueryModel(queryModel);		
	}

	public void updateQueryTxtArea(String queryType, String optimizationType) {
		String query = queryMap.get(queryType).get(optimizationType);
		view.setQueryTextArea(query);
	}

	public void refreshQuery() {
		String selectString = this.selectStatement;
		String fromString = this.fromStatement;
		String whereString = this.whereStatement;
		String groupByString = this.groupByStatement;
		
		if(selectStatement.equals("SELECT ")){
			selectString = "SELECT *";
			selectString += "\n";
		} else {
			selectString = selectString.substring(0, selectString.length()-1) + ", COUNT(*) as Total";
			selectString += "\n";
		}
		
		if(fromStatement.equals("FROM fact f, citizen c, education e, household h")){
			fromString = "FROM fact f, citizen c, education e, household h";
			fromString += "\n";
		}
		
		if(this.whereStatement.equals("WHERE f.hh_id=h.hh_id AND f.citizen_id=c.citizen_id AND f.educ_id=e.educ_id")){
			whereString = "WHERE f.hh_id=h.hh_id AND f.citizen_id=c.citizen_id AND f.educ_id=e.educ_id";
			whereString += "\n";
		} else 
		if(this.whereStatement.equals("WHERE f.hh_id=h.hh_id AND f.citizen_id=c.citizen_id AND f.educ_id=e.educ_id AND")){
			whereString = "WHERE f.hh_id=h.hh_id AND f.citizen_id=c.citizen_id AND f.educ_id=e.educ_id";
			whereString += "\n";
		} else{
			whereString = whereString.substring(0, whereString.length());
			whereString += "\n";
		}
		
		if(this.groupByStatement.equals("GROUP BY ")){
			groupByString = "";
		} else {
			groupByString = groupByString.substring(0, groupByString.length()-1);
			groupByString += "\n";
		}
		
		finalQuery = selectString 
					+ fromString 
					+ whereString
					+ groupByString;
	}

	public void addSliceAndDice(String whereClause) {
		this.whereStatement += "\nAND " + whereClause + "";
	}

	public String getFinalQuery() {
		// TODO Auto-generated method stub
		return finalQuery;
	}

	public void removeSliceAndDice(String whereClause) {
		this.whereStatement = this.whereStatement.replace("\nAND " + whereClause, "");
	}
	
	public void drillDown(String attribute) {
		String selectClause = "";
		String groupByClause = "";
		
		if(attribute.equals("location")){
			locationHierarchy++;
			selectClause = getClauseForHierarchy("location", locationHierarchy);
			groupByClause = getClauseForHierarchy("location", locationHierarchy);
			
//			this.selectStatement += selectClause +  ",";
//			this.groupByStatement += groupByClause + ",";
			this.selectStatement += selectClause;
			this.groupByStatement += groupByClause;

			if(locationHierarchy>4){
				locationHierarchy=4;
			}
		}
		
		if(attribute.equals("accessibility")){
			accessibilityHierarchy++;
			selectClause = getClauseForHierarchy("accessibility", accessibilityHierarchy);
			groupByClause = getClauseForHierarchy("accessibility", accessibilityHierarchy);
			
//			this.selectStatement += selectClause +  ",";
//			this.groupByStatement += groupByClause + ",";
			this.selectStatement += selectClause;
			this.groupByStatement += groupByClause;

			if(accessibilityHierarchy>2){
				accessibilityHierarchy=2;
			}
		}
		
		if(attribute.equals("education")){
			educationHierarchy++;
			selectClause = getClauseForHierarchy("education", educationHierarchy)+"educ_name,";
			groupByClause = getClauseForHierarchy("education", educationHierarchy);
			
//			this.selectStatement += selectClause +  ",";
//			this.groupByStatement += groupByClause + ",";
			this.selectStatement += selectClause;
			this.groupByStatement += groupByClause;

			if(educationHierarchy>1){
				educationHierarchy=1;
			}
		}
	}
	
	private String getClauseForHierarchy(String attribute, int hierarchy) {
		String finalClause = "";
		
		if(attribute.equals("location")){
			switch(hierarchy) {
				case 0 : finalClause=""; break;
				case 1 : finalClause="regn,"; break;
				case 2 : finalClause="prov,"; break;
				case 3 : finalClause="mun,"; break;
				case 4 : finalClause="brgy,"; break;
				default: finalClause="";
			}
			
			return finalClause;
		}
		
		if(attribute.equals("accessibility")){
			switch(hierarchy) {
				case 0 : finalClause=""; break;
				case 1 : finalClause="welec,"; break;
				case 2 : finalClause="radio,tv,computer,internet,"; break;
				default: finalClause="";
			}
			
			return finalClause;
		}
		
		if(attribute.equals("education")){
			switch(hierarchy) {
				case 0 : finalClause=""; break;
				case 1 : finalClause="educ_id,"; break;
				default: finalClause="";
			}
			
			return finalClause;
		}
		
		return finalClause;
	}

	public void rollUp(String attribute) {
		String selectClause = "";
		String groupByClause = "";
		 
		if(attribute.equals("location")){
			selectClause = getClauseForHierarchy("location", locationHierarchy);
			groupByClause = getClauseForHierarchy("location", locationHierarchy);
			locationHierarchy--;
			
			System.out.println("waxxzup");
			this.selectStatement = this.selectStatement.replace(selectClause, "");
			this.groupByStatement = this.groupByStatement.replace(groupByClause, "");
			
			if(locationHierarchy<=0){
				locationHierarchy=0;
			}
		}
		
		if(attribute.equals("accessibility")){
			selectClause = getClauseForHierarchy("accessibility", accessibilityHierarchy);
			groupByClause = getClauseForHierarchy("accessibility", accessibilityHierarchy);
			accessibilityHierarchy--;
			
			this.selectStatement = this.selectStatement.replace(selectClause, "");
			this.groupByStatement = this.groupByStatement.replace(groupByClause, "");
			
			if(accessibilityHierarchy<=0){
				accessibilityHierarchy=0;
			}
		}
		
		if(attribute.equals("education")){
			selectClause = getClauseForHierarchy("education", educationHierarchy)+"educ_name,";
			groupByClause = getClauseForHierarchy("education", educationHierarchy);
			educationHierarchy--;
			
			this.selectStatement = this.selectStatement.replace(selectClause, "");
			this.groupByStatement = this.groupByStatement.replace(groupByClause, "");
			
			if(educationHierarchy<=0){
				educationHierarchy=0;
			}
		}
		
	}
	

}
