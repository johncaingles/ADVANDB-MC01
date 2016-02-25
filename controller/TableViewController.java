package controller;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.table.TableModel;

import model.AppDatabase;
import net.proteanit.sql.DbUtils;
import utilities.dbconnection.MySQLConnector;
import view.TableView;

public class TableViewController {

	private TableView view;
	private MySQLConnector connector;
	private HashMap<String, ArrayList<String>> queries;
	private AppDatabase appDatabase;
	
	public TableViewController(TableView view) {
		this.view = view;
		this.connector = new MySQLConnector(
				"jdbc:mysql://localhost:3306/", 
				"db_hpq", 
				"com.mysql.jdbc.Driver", 
				"root", 
				"p@ssword");
		connector.getConnection();
		this.appDatabase = new AppDatabase();
		/** Initialize list of queries */
		this.queries = appDatabase.getMapOfQueries();
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

	public void initializeViewData() {
		/** Get keys(query list) */
		ArrayList<String> queryList = new ArrayList<>();
		for ( String key : queries.keySet() ) {
			queryList.add(key);
		}
		/** Set model for queries */
		DefaultComboBoxModel queryModel = new DefaultComboBoxModel( queryList.toArray() );
		view.setQueryModel(queryModel);		
	}

	public void updateOptimizationList(String queryType) {
		/** Get values(query list) */
		ArrayList<String> optimizationList = queries.get(queryType);
		/** Set model for queries */
		DefaultComboBoxModel queryModel = new DefaultComboBoxModel( optimizationList.toArray() );
		view.setOptimizationListModel(queryModel);	
	}
	
	
	
}
