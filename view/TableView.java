package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextArea;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JScrollBar;
import javax.swing.table.DefaultTableModel;

import controller.TableViewController;

import java.awt.ScrollPane;
import java.awt.Panel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextPane;

public class TableView extends JPanel implements ActionListener, KeyListener  
{
	private JTable table;
	private MainFrame mainFrame;
	private JScrollPane jscrllpnlTable;
	private JLabel lblQuery;
	private JComboBox cmbbxQuery;
	private JComboBox cmbbxOptimizationType;
	private JLabel lblQueryDetails;
	private JTextPane txtpnQueryDetails;
	private JLabel lblTime;
	private JTextPane txtpnTime;
	
	

	/** Controller */
	private TableViewController controller;
	
	/** Panel Components */
	private JButton btnSubmit;
	private JTextArea txtarQuery;
	private JTable resultTable;
	
    public TableView(MainFrame mainFrame)
    {
    	this.setLayout(null);
    	this.setBounds(10, 72, 802, 480);
    	this.mainFrame = mainFrame;
    	controller = new TableViewController(this);
    	
    	btnSubmit = new JButton("SUBMIT");
    	btnSubmit.setFont(new Font("Roboto", Font.PLAIN, 14));
    	btnSubmit.setBounds(703, 13, 89, 94);
    	btnSubmit.addActionListener(this);
    	this.add(btnSubmit);
    	
    	txtarQuery = new JTextArea();
    	txtarQuery.setToolTipText("Enter query here");
    	txtarQuery.setBounds(177, 13, 516, 94);
    	this.add(txtarQuery);
    	
    	/** Initial model for table */
        String [] header={"name","age", "null"};
        String [][] data={{"akash","20"},{"pankaj","24"},{"pankaj","24"},{"pankaj","24"},{"pankaj","24"}};
        DefaultTableModel tableModel = new DefaultTableModel(data,header);
        
        /** JTable setup */
        resultTable = new JTable(tableModel);
        resultTable.setPreferredScrollableViewportSize(new Dimension(450,63));
        resultTable.setFillsViewportHeight(true);
        
        jscrllpnlTable=new JScrollPane(resultTable);
        jscrllpnlTable.setBounds(177, 118, 615, 351);
        jscrllpnlTable.setVisible(true);
        add(jscrllpnlTable);
        
        lblQuery = new JLabel("Query");
        lblQuery.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblQuery.setBounds(10, 13, 104, 14);
        add(lblQuery);
        
        JLabel lblOptimizationType = new JLabel("Optimization Type");
        lblOptimizationType.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblOptimizationType.setBounds(10, 69, 89, 14);
        add(lblOptimizationType);
        
        cmbbxQuery = new JComboBox();
        cmbbxQuery.setBounds(10, 38, 157, 20);
        cmbbxQuery.addActionListener(this);
        add(cmbbxQuery);
        
        cmbbxOptimizationType = new JComboBox();
        cmbbxOptimizationType.setBounds(10, 87, 157, 20);
        cmbbxOptimizationType.addActionListener(this);
        add(cmbbxOptimizationType);
        
        lblQueryDetails = new JLabel("Query Details");
        lblQueryDetails.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblQueryDetails.setBounds(10, 118, 157, 24);
        add(lblQueryDetails);
        
        txtpnQueryDetails = new JTextPane();
        txtpnQueryDetails.setBounds(10, 147, 157, 176);
        add(txtpnQueryDetails);
        
        lblTime = new JLabel("Time");
        lblTime.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblTime.setBounds(10, 334, 157, 20);
        add(lblTime);
        
        txtpnTime = new JTextPane();
        txtpnTime.setBounds(10, 365, 157, 104);
        add(txtpnTime);
        
        controller.initializeViewData();
    }
    
    /** Action Listeners */
    @Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == btnSubmit && getInputQueryType() == "Custom") {
			String query = getInputQuery();
			controller.submitCustomStatement(query);
			System.out.println(getInputQuery());
			// TODO Insert to table using results
//			resultTable.setModel(arg0);
		} else 
		if(ae.getSource() == btnSubmit) {
			String queryType = getInputQueryType();
			String optimizationType = getChosenOptimizationType();
			controller.submitQuery(queryType, optimizationType);
			txtarQuery.setEditable(false);
			// TODO Render Table
			
		} else
		if(ae.getSource() == cmbbxQuery){
			String queryType = getInputQueryType();
			controller.updateOptimizationList(queryType);
		}
	}

	private String getInputQueryType() {
		String input = cmbbxQuery.getSelectedItem().toString();
		return input;
	}

	private String getChosenOptimizationType() {
		String input = cmbbxOptimizationType.getSelectedItem().toString();
		return input;
	}

	private String getInputQuery() {
		String input = txtarQuery.getText();
		return input;
	}
	
	public JTable getResultTable(){
		return resultTable;
	}
	
	public void setQueryModel(DefaultComboBoxModel model){
		cmbbxQuery.setModel(model);
	}

	public void setOptimizationListModel(DefaultComboBoxModel queryModel) {
		cmbbxOptimizationType.setModel(queryModel);		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
