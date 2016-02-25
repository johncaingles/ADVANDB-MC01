package model;

import java.util.ArrayList;
import java.util.HashMap;

import utilities.dbconnection.MySQLConnector;

public class AppDatabase {
	
	private HashMap<String, ArrayList<String>> mapOfQueries;
	
	public AppDatabase(){
		mapOfQueries = new HashMap<String, ArrayList<String>>();
		initializeMapOfQueriesList();
	}
	
	private void initializeMapOfQueriesList() {
		ArrayList<String> tempList;
		/** Custom */
		tempList = new ArrayList<>();
		mapOfQueries.put("Custom", tempList);
		
		/** Query 1 */
		tempList = new ArrayList<>();
		tempList.add("Database thingy");
		tempList.add("More Optimization");
		mapOfQueries.put("Query 1", tempList);
	}
	
	public HashMap<String, ArrayList<String>> getMapOfQueries(){
		return mapOfQueries;
		
	}
		
}
