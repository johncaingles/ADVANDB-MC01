package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;

import controller.PeerToPeerDBController;

public class PeerToPeerDBView extends JPanel implements ActionListener, KeyListener {
	private JTable table;
	private MainFrame mainFrame;
	private JScrollPane jscrllpnlTable;
	private JLabel lblQuery;
	private JComboBox cmbbxQuery;
	private JComboBox cmbbxIsolationType;
	private JComboBox cmbbxType;
	private JLabel lblQueryDetails;
	private JLabel lblTime;
	private JTextPane txtpnTime;
	private JButton btnSend;
	private JTextArea txtarLog; 


	/** Controller */
	private PeerToPeerDBController controller;
	private JTextArea txtarQuery;
	private JTable resultTable;

	public PeerToPeerDBView(MainFrame mainFrame, int nodeType) {
		this.setLayout(null);
		this.setBounds(10, 72, 802, 480);
		controller = new PeerToPeerDBController(this, nodeType);

		txtarQuery = new JTextArea();
		txtarQuery.setToolTipText("Enter query here");
		txtarQuery.setBounds(177, 184, 615, 57);
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
		jscrllpnlTable.setBounds(177, 265, 615, 204);
		jscrllpnlTable.setVisible(true);
		add(jscrllpnlTable);

		lblQuery = new JLabel("Query Node");
		lblQuery.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblQuery.setBounds(10, 13, 104, 14);
		add(lblQuery);

		JLabel lblIsolationLevel = new JLabel("Isolation Level");
		lblIsolationLevel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblIsolationLevel.setBounds(10, 69, 89, 14);
		add(lblIsolationLevel);

		cmbbxQuery = new JComboBox();
		cmbbxQuery.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cmbbxQuery.setBounds(10, 38, 157, 20);
		cmbbxQuery.addActionListener(this);
		/** stuff */
		cmbbxQuery.addItem("Palawan");
		cmbbxQuery.addItem("Marinduque");
		cmbbxQuery.addItem("Central");
		add(cmbbxQuery);

		cmbbxIsolationType = new JComboBox();
		cmbbxIsolationType.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cmbbxIsolationType.setBounds(10, 87, 157, 20);
		cmbbxIsolationType.addActionListener(this);
		/** stuff */
		cmbbxIsolationType.addItem("Read uncommited");
		cmbbxIsolationType.addItem("Read commited");
		cmbbxIsolationType.addItem("Read repeatable");
		cmbbxIsolationType.addItem("Serializable");
		add(cmbbxIsolationType);

		lblQueryDetails = new JLabel("Logs");
		lblQueryDetails.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblQueryDetails.setBounds(177, 8, 157, 24);
		add(lblQueryDetails);

		lblTime = new JLabel("Time");
		lblTime.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTime.setBounds(10, 395, 157, 20);
		add(lblTime);

		txtpnTime = new JTextPane();
		txtpnTime.setText("0ms");
		txtpnTime.setFont(new Font("Tahoma", Font.PLAIN, 30));
		txtpnTime.setBounds(10, 426, 157, 43);
		add(txtpnTime);

		btnSend = new JButton("Send");
		btnSend.setBounds(30, 210, 109, 31);
		btnSend.addActionListener(this);
		add(btnSend);

		txtarLog = new JTextArea();
		txtarLog.setToolTipText("Enter query here");
		txtarLog.setBounds(177, 28, 615, 133);
		add(txtarLog);

		cmbbxType = new JComboBox();
		cmbbxType.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cmbbxType.setBounds(10, 136, 157, 20);
		cmbbxType.addItem("Read");
		cmbbxType.addItem("Write");
		add(cmbbxType);

		JLabel lblQuery_1 = new JLabel("Query");
		lblQuery_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblQuery_1.setBounds(177, 163, 157, 24);
		add(lblQuery_1);

		JLabel lblTable = new JLabel("Table");
		lblTable.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTable.setBounds(177, 242, 157, 24);
		add(lblTable);

		JLabel lblType = new JLabel("Type");
		lblType.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblType.setBounds(10, 112, 157, 24);
		add(lblType);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
/*		if((ae.getSource() == btnSubmit || ae.getSource() == btnExecute)) {

			String query = getInputQuery();
			if(ae.getSource() == btnSubmit)
				controller.submitCustomStatement(query);
			else controller.executeCustomStatement(query);
			txtarQuery.setEditable(true);
		} else */
		if(ae.getSource() == cmbbxQuery){
			controller.setQueryType(getInputQueryType());
		} else
		if(ae.getSource() == cmbbxIsolationType){
			controller.setIsolationType(getInputIsolationType());
		} else
		if(ae.getSource() == btnSend){
			String ip = "hi, this is where you change the IP";
			if(cmbbxQuery.getSelectedItem().toString().equalsIgnoreCase("Palawan"))
				ip = "192.162.1.8";
			else if(cmbbxQuery.getSelectedItem().toString().equalsIgnoreCase("Marinduque"))
				ip = "192.162.1.9";
			else if(cmbbxQuery.getSelectedItem().toString().equalsIgnoreCase("Central"))
				ip = "192.162.1.10";
			String msg = txtarQuery.getText();
			String type = cmbbxType.getSelectedItem().toString();
			controller.queryToNode(ip, msg, type);
		}
	}


//	
//	public void refreshTxtQuery() {
//		controller.refreshQuery();
//		setQueryTextArea(controller.getFinalQuery());
//	}

	private String getInputQueryType() {
		String input = cmbbxQuery.getSelectedItem().toString();
		return input;
	}

	private String getInputIsolationType() {
		String input = cmbbxIsolationType.getSelectedItem().toString();
		return input;
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
	
	public void setLogText(String log){
		txtarLog.setText(log);
	}
	
	public String getLogText(){
		return txtarLog.getText();
	}
}
