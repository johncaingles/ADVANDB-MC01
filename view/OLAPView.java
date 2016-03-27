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
import javax.swing.JSeparator;

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
	private JLabel lblOlapOperations;
	private JButton btnAccessibilityRollUp;
	private JButton btnAccessibilityDrillDown;
	private JButton btnEducationRollUp;
	private JButton btnEducationDrillDown;
	private JCheckBox chckbxUnregisteredVoter;
	private JCheckBox chckbxOverseasFilipinoWorker;
	
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
    	txtarQuery.setBounds(276, 13, 417, 165);
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
        jscrllpnlTable.setBounds(276, 189, 516, 280);
        jscrllpnlTable.setVisible(true);
        add(jscrllpnlTable);
        
        lblTime = new JLabel("Execution Time");
        lblTime.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblTime.setBounds(10, 395, 256, 20);
        add(lblTime);
        
        txtpnTime = new JTextPane();
        txtpnTime.setText("0ms");
        txtpnTime.setFont(new Font("Tahoma", Font.PLAIN, 30));
        txtpnTime.setBounds(10, 426, 256, 43);
        add(txtpnTime);
        
        btnExecute = new JButton("Execute");
        btnExecute.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnExecute.setBounds(703, 84, 89, 23);
        btnExecute.addActionListener(this);
        add(btnExecute);
        
        chckbxLegalAge = new JCheckBox("Legal Age");
        chckbxLegalAge.setFont(new Font("Tahoma", Font.PLAIN, 11));
        chckbxLegalAge.setBounds(10, 244, 256, 23);
        chckbxLegalAge.addActionListener(this);
        add(chckbxLegalAge);
        
        btnLocationDrillDown = new JButton("Drill Down");
        btnLocationDrillDown.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnLocationDrillDown.setBounds(177, 87, 89, 23);
        btnLocationDrillDown.addActionListener(this);
        add(btnLocationDrillDown);
        
        JLabel lblLocation = new JLabel("Location");
        lblLocation.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblLocation.setHorizontalAlignment(SwingConstants.CENTER);
        lblLocation.setBounds(109, 87, 58, 23);
        add(lblLocation);
        
        btnLocationRollUp = new JButton("Roll Up");
        btnLocationRollUp.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnLocationRollUp.setBounds(10, 87, 89, 23);
        btnLocationRollUp.addActionListener(this);
        add(btnLocationRollUp);
        
        lblSliceAndDice = new JLabel("Slice and Dice");
        lblSliceAndDice.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblSliceAndDice.setBounds(10, 217, 258, 20);
        add(lblSliceAndDice);
        
        chckbxRegisteredVoter = new JCheckBox("Registered Voter ");
        chckbxRegisteredVoter.setFont(new Font("Tahoma", Font.PLAIN, 11));
        chckbxRegisteredVoter.setBounds(10, 270, 256, 23);
        chckbxRegisteredVoter.addActionListener(this);
        add(chckbxRegisteredVoter);
        
        JLabel lblRollUpAndDrillDown = new JLabel("Roll Up and Drill Down");
        lblRollUpAndDrillDown.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblRollUpAndDrillDown.setBounds(10, 56, 258, 20);
        add(lblRollUpAndDrillDown);
        
        btnAccessibilityRollUp = new JButton("Roll Up");
        btnAccessibilityRollUp.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnAccessibilityRollUp.setBounds(10, 121, 89, 23);
        btnAccessibilityRollUp.addActionListener(this);
        add(btnAccessibilityRollUp);
        
        JLabel lblAccessibility = new JLabel("Accessibility");
        lblAccessibility.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblAccessibility.setHorizontalAlignment(SwingConstants.CENTER);
        lblAccessibility.setBounds(109, 121, 58, 23);
        add(lblAccessibility);
        
        btnAccessibilityDrillDown = new JButton("Drill Down");
        btnAccessibilityDrillDown.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnAccessibilityDrillDown.setBounds(177, 121, 89, 23);
        btnAccessibilityDrillDown.addActionListener(this);
        add(btnAccessibilityDrillDown);
        
        btnEducationRollUp = new JButton("Roll Up");
        btnEducationRollUp.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnEducationRollUp.setBounds(10, 155, 89, 23);
        btnEducationRollUp.addActionListener(this);
        add(btnEducationRollUp);
        
        JLabel lblEducation = new JLabel("Education");
        lblEducation.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblEducation.setHorizontalAlignment(SwingConstants.CENTER);
        lblEducation.setBounds(109, 155, 58, 23);
        add(lblEducation);
        
        btnEducationDrillDown = new JButton("Drill Down");
        btnEducationDrillDown.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnEducationDrillDown.setBounds(177, 155, 89, 23);
        btnEducationDrillDown.addActionListener(this);
        add(btnEducationDrillDown);
        
        chckbxUnregisteredVoter = new JCheckBox("Unregistered Voter");
        chckbxUnregisteredVoter.setFont(new Font("Tahoma", Font.PLAIN, 11));
        chckbxUnregisteredVoter.setBounds(10, 296, 256, 23);
        chckbxUnregisteredVoter.addActionListener(this);
        add(chckbxUnregisteredVoter);
        
        chckbxOverseasFilipinoWorker = new JCheckBox("Overseas Filipino Worker (OFW)");
        chckbxOverseasFilipinoWorker.setFont(new Font("Tahoma", Font.PLAIN, 11));
        chckbxOverseasFilipinoWorker.setBounds(10, 322, 256, 23);
        chckbxOverseasFilipinoWorker.addActionListener(this);
        add(chckbxOverseasFilipinoWorker);
        
        lblOlapOperations = new JLabel("OLAP Operations");
        lblOlapOperations.setHorizontalAlignment(SwingConstants.CENTER);
        lblOlapOperations.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblOlapOperations.setBounds(8, 13, 258, 20);
        add(lblOlapOperations);
        
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
		
		if(ae.getSource() == chckbxUnregisteredVoter) {
			String clause = "c.registered_voter=2";
			
			if(chckbxUnregisteredVoter.isSelected())
				controller.addSliceAndDice(clause);
			else controller.removeSliceAndDice(clause);
			refreshTxtQuery();
		}
		
		if(ae.getSource() == chckbxOverseasFilipinoWorker) {
			String clause = "c.ofw=1";
			
			if(chckbxOverseasFilipinoWorker.isSelected())
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
		/** ACCESSIBILITY */
		if(ae.getSource() == btnAccessibilityDrillDown) {
			String attribute = "accessibility";
			
			// TODO add drill down
			controller.drillDown(attribute);
			refreshTxtQuery();
		}
		
		if(ae.getSource() == btnAccessibilityRollUp) {
			String attribute = "accessibility";
					
			controller.rollUp(attribute);
			refreshTxtQuery();
		}
		
		if(ae.getSource() == btnEducationDrillDown) {
			String attribute = "education";
			
			// TODO add drill down
			controller.drillDown(attribute);
			refreshTxtQuery();
		}
		
		if(ae.getSource() == btnEducationRollUp) {
			String attribute = "education";
					
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
