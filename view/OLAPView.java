package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;

import controller.OLAPViewController;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

public class OLAPView extends JPanel implements ActionListener, KeyListener{

	private JTable table;
	private MainFrame mainFrame;
	private JScrollPane jscrllpnlTable;
	private JLabel lblTime;
	private JTextPane txtpnTime;
	private JButton btnExecute;
	
	private JButton btnLocationDrillDown;
	

	/** Controller */
	private OLAPViewController controller;
	
	/** Panel Components */
	private JButton btnSubmit;
	private JTextArea txtarQuery;
	private JTable resultTable;
	private JCheckBox chckbxLegalAge;
	private JButton btnLocationRollUp;
	private JLabel lblSliceAndDice;
	private JCheckBox chckbxRegisteredVoter;
	
    public OLAPView(MainFrame mainFrame)
    {
    	this.setLayout(null);
    	this.setBounds(10, 72, 802, 480);
    	this.mainFrame = mainFrame;
    	controller = new OLAPViewController(this);
    	
    	btnSubmit = new JButton("QUERY");
    	btnSubmit.setFont(new Font("Roboto", Font.PLAIN, 14));
    	btnSubmit.setBounds(703, 13, 89, 62);
    	btnSubmit.addActionListener(this);
    	this.add(btnSubmit);
    	
    	txtarQuery = new JTextArea();
    	txtarQuery.setToolTipText("Enter query here");
    	txtarQuery.setBounds(276, 13, 417, 146);
    	this.add(txtarQuery);
    	
    	/** Initial model for table */
        String [] header={};
        String [][] data={};
        DefaultTableModel tableModel = new DefaultTableModel(data,header);
        
        /** JTable setup */
        resultTable = new JTable(tableModel);
        resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        resultTable.setPreferredScrollableViewportSize(new Dimension(450,63));
        resultTable.setFillsViewportHeight(true);
        
        jscrllpnlTable=new JScrollPane(resultTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
       // jscrllpnlTable.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jscrllpnlTable.setBounds(276, 170, 516, 299);
        jscrllpnlTable.setVisible(true);
        add(jscrllpnlTable);
        
        lblTime = new JLabel("Time");
        lblTime.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblTime.setBounds(10, 395, 157, 20);
        add(lblTime);
        
        txtpnTime = new JTextPane();
        txtpnTime.setText("0ms");
        txtpnTime.setFont(new Font("Tahoma", Font.PLAIN, 30));
        txtpnTime.setBounds(10, 426, 157, 43);
        add(txtpnTime);
        
        btnExecute = new JButton("Execute");
        btnExecute.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnExecute.setBounds(703, 84, 89, 23);
        btnExecute.addActionListener(this);
        add(btnExecute);
        
        chckbxLegalAge = new JCheckBox("Legal Age");
        chckbxLegalAge.setBounds(10, 257, 126, 23);
        chckbxLegalAge.addActionListener(this);
        add(chckbxLegalAge);
        
        btnLocationDrillDown = new JButton("Drill Down");
        btnLocationDrillDown.setBounds(177, 141, 89, 23);
        btnLocationDrillDown.addActionListener(this);
        add(btnLocationDrillDown);
        
        JLabel lblLocation = new JLabel("Location");
        lblLocation.setHorizontalAlignment(SwingConstants.CENTER);
        lblLocation.setBounds(109, 141, 58, 23);
        add(lblLocation);
        
        btnLocationRollUp = new JButton("Roll Up");
        btnLocationRollUp.setBounds(10, 141, 89, 23);
        btnLocationRollUp.addActionListener(this);
        add(btnLocationRollUp);
        
        lblSliceAndDice = new JLabel("Slice and Dice");
        lblSliceAndDice.setBounds(10, 204, 258, 20);
        add(lblSliceAndDice);
        
        chckbxRegisteredVoter = new JCheckBox("Registered Voter ");
        chckbxRegisteredVoter.setBounds(10, 283, 126, 23);
        chckbxRegisteredVoter.addActionListener(this);
        add(chckbxRegisteredVoter);
        
        controller.initializeViewData();
    	refreshTxtQuery();
    }
    
    /** Action Listeners */
    @Override
	public void actionPerformed(ActionEvent ae) {
		if((ae.getSource() == btnSubmit || ae.getSource() == btnExecute)) {

			String query = getInputQuery();
			if(ae.getSource() == btnSubmit)
				controller.submitCustomStatement(query);
			else controller.executeCustomStatement(query);
			txtarQuery.setEditable(true);
		}
		
		/** SLICE AND DICE */
		if(ae.getSource() == chckbxLegalAge) {
			String clause = "c.age_yr >= 18";
			
			if(chckbxLegalAge.isSelected())
				controller.addSliceAndDice(clause);
			else controller.removeSliceAndDice(clause);
			refreshTxtQuery();
		}
		
		if(ae.getSource() == chckbxRegisteredVoter) {
			String clause = "c.registered_voter=1";
			
			if(chckbxRegisteredVoter.isSelected())
				controller.addSliceAndDice(clause);
			else controller.removeSliceAndDice(clause);
			refreshTxtQuery();
		}
		
		/** Drill down roll up */		
		/** LOCATION */
		if(ae.getSource() == btnLocationDrillDown) {
			String attribute = "location";
			
			// TODO add drill down
			controller.drillDown(attribute);
			refreshTxtQuery();
		}
		
		if(ae.getSource() == btnLocationRollUp) {
			String attribute = "location";
					
			controller.rollUp(attribute);
			refreshTxtQuery();
		}
		
	}

	public void refreshTxtQuery() {
		controller.refreshQuery();
		setQueryTextArea(controller.getFinalQuery());
	}

	private String getInputQuery() {
		String input = txtarQuery.getText();
		return input;
	}
	
	public JTable getResultTable(){
		return resultTable;
	}
	
	public JTextPane getTxtTime(){
		return txtpnTime;
	}
	
//	public void setQueryModel(DefaultComboBoxModel model){
//		cmbbxQuery.setModel(model);
//	}
//
//	public void setOptimizationListModel(DefaultComboBoxModel queryModel) {
//		cmbbxOptimizationType.setModel(queryModel);		
//	}

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

	public void setQueryTextArea(String query) {
		txtarQuery.setText(query);		
	}
}
