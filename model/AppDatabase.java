package model;

import java.util.ArrayList;
import java.util.HashMap;

import utilities.dbconnection.MySQLConnector;

public class AppDatabase {
	
	private HashMap<String, HashMap<String, String>> queryStatements;
	private HashMap<String, String> queryInfos;
	
	public AppDatabase(){
		queryStatements = new HashMap<String, HashMap<String, String>>();
		queryInfos = new HashMap<String, String>();
		initializeQueryOptimizations();
		intializeQueryInfos();
	}
	

	private void initializeQueryOptimizations() {
		HashMap<String, String> queryStatementsBasedOnOptimization;
		String queryType;
		String optimizationType;
		String statement;
		/** Custom */
		queryType = "Custom";
		/** Start of Optimization List */
		optimizationType = "";
		statement = "";
		queryStatementsBasedOnOptimization = new HashMap<>();
		queryStatementsBasedOnOptimization.put(optimizationType, statement);
		/** Final initialization */
		queryStatements.put(queryType, queryStatementsBasedOnOptimization);
		
		/** Query 1 */
		queryType = "Query 1";
		/** Start of Optimization List */
		optimizationType = "";
		statement = "";
		queryStatementsBasedOnOptimization = new HashMap<>();
		queryStatementsBasedOnOptimization.put(optimizationType, statement);
		/** Final initialization */
		queryStatements.put(queryType, queryStatementsBasedOnOptimization);
	}

	private void intializeQueryInfos() {
		
	}
	
	public HashMap<String, HashMap<String, String>> getMapOfQueries(){
		return queryStatements;
	}
		
}
