package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import utilities.dbconnection.MySQLConnector;

public class AppDatabase {
	
	private LinkedHashMap<String, LinkedHashMap<String, String>> queryStatements;
	private LinkedHashMap<String, String> queryInfos;
	
	public AppDatabase(){
		queryStatements = new LinkedHashMap<String, LinkedHashMap<String, String>>();
		queryInfos = new LinkedHashMap<String, String>();
		initializeQueryOptimizations();
		intializeQueryInfos();
	}
	

	private void initializeQueryOptimizations() {
		LinkedHashMap<String, String> queryStatementsBasedOnOptimization;
		String queryType;
		String queryDescription;
		String optimizationType;
		String statement;
		
		/** Custom */
		queryType = "Custom";
		/** Description */
		queryDescription = "Enter anything bebeh!";
		/** Start of Optimization List */
		queryStatementsBasedOnOptimization = new LinkedHashMap<>();
		/** Final initialization */
		queryStatements.put(queryType, queryStatementsBasedOnOptimization);
		queryInfos.put(queryType, queryDescription);
		
		/** Query 1 */
		queryType = "Query 1";
		/** Description */
		queryDescription = "Ooooh look at me I am the first query po";
		/** Start of Optimization List */
			queryStatementsBasedOnOptimization = new LinkedHashMap<>();
			
			optimizationType = "Table rearranging thingy";
			statement = "party party?";
			queryStatementsBasedOnOptimization.put(optimizationType, statement);
			
			optimizationType = "More optimize such wow";
			statement = "wow amazing";
			queryStatementsBasedOnOptimization.put(optimizationType, statement);
		/** End of Optimization List */
		/** Final initialization */
		queryStatements.put(queryType, queryStatementsBasedOnOptimization);
		queryInfos.put(queryType, queryDescription);
		
		/** Query 1 */
		queryType = "Query 2";
		/** Description */
		queryDescription = "Ooooh look at me I am the second query po";
		/** Start of Optimization List */
			queryStatementsBasedOnOptimization = new LinkedHashMap<>();
			
			optimizationType = "Basic";
			statement = "SELECT AND SHIT";
			queryStatementsBasedOnOptimization.put(optimizationType, statement);
			
		/** End of Optimization List */
		/** Final initialization */
		queryStatements.put(queryType, queryStatementsBasedOnOptimization);
		queryInfos.put(queryType, queryDescription);
		
		/** Query 1 */
		queryType = "Query 2";
		/** Description */
		queryDescription = "Ooooh look at me I am the second query po";
		/** Start of Optimization List */
			queryStatementsBasedOnOptimization = new LinkedHashMap<>();
			
			optimizationType = "Basic";
			statement = "SELECT AND SHIT";
			queryStatementsBasedOnOptimization.put(optimizationType, statement);
			
		/** End of Optimization List */
		/** Final initialization */
		queryStatements.put(queryType, queryStatementsBasedOnOptimization);
		queryInfos.put(queryType, queryDescription);
		
		/** Query 3 */
		queryType = "Query 3";
		/** Description */
		queryDescription = "Ooooh look at me I am the second query po";
		/** Start of Optimization List */
			queryStatementsBasedOnOptimization = new LinkedHashMap<>();
			
			optimizationType = "Basic";
			statement = "SELECT AND SHIT";
			queryStatementsBasedOnOptimization.put(optimizationType, statement);
			
		/** End of Optimization List */
		/** Final initialization */
		queryStatements.put(queryType, queryStatementsBasedOnOptimization);
		queryInfos.put(queryType, queryDescription);
		
		/** Query 4 */
		queryType = "Query 4";
		/** Description */
		queryDescription = "Ooooh look at me I am the second query po";
		/** Start of Optimization List */
			queryStatementsBasedOnOptimization = new LinkedHashMap<>();
			
			optimizationType = "Basic";
			statement = "SELECT AND SHIT";
			queryStatementsBasedOnOptimization.put(optimizationType, statement);
			
		/** End of Optimization List */
		/** Final initialization */
		queryStatements.put(queryType, queryStatementsBasedOnOptimization);
		queryInfos.put(queryType, queryDescription);
		
		/** Query 5 */
		queryType = "Query 5";
		/** Description */
		queryDescription = "Ooooh look at me I am the second query po";
		/** Start of Optimization List */
			queryStatementsBasedOnOptimization = new LinkedHashMap<>();
			
			optimizationType = "Basic";
			statement = "SELECT AND SHIT";
			queryStatementsBasedOnOptimization.put(optimizationType, statement);
			
		/** End of Optimization List */
		/** Final initialization */
		queryStatements.put(queryType, queryStatementsBasedOnOptimization);
		queryInfos.put(queryType, queryDescription);
		
		/** Query 6 */
		queryType = "Query 6";
		/** Description */
		queryDescription = "Ooooh look at me I am the second query po";
		/** Start of Optimization List */
			queryStatementsBasedOnOptimization = new LinkedHashMap<>();
			
			optimizationType = "Basic";
			statement = "SELECT AND SHIT";
			queryStatementsBasedOnOptimization.put(optimizationType, statement);
			
		/** End of Optimization List */
		/** Final initialization */
		queryStatements.put(queryType, queryStatementsBasedOnOptimization);
		queryInfos.put(queryType, queryDescription);
		
		/** Query 7 */
		queryType = "Query 7";
		/** Description */
		queryDescription = "Ooooh look at me I am the second query po";
		/** Start of Optimization List */
			queryStatementsBasedOnOptimization = new LinkedHashMap<>();
			
			optimizationType = "Basic";
			statement = "SELECT AND SHIT";
			queryStatementsBasedOnOptimization.put(optimizationType, statement);
			
		/** End of Optimization List */
		/** Final initialization */
		queryStatements.put(queryType, queryStatementsBasedOnOptimization);
		queryInfos.put(queryType, queryDescription);
	}

	private void intializeQueryInfos() {
		
	}
	
	public LinkedHashMap<String, LinkedHashMap<String, String>> getMapOfQueries(){
		return queryStatements;
	}
		
}
