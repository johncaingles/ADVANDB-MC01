package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import controller.PeerToPeerDBController;

public class PeerToPeerDBView extends JPanel implements ActionListener, KeyListener {
	private JTable table;
	private MainFrame mainFrame;
	private JScrollPane jscrllpnlTable;
	private JLabel lblQuery;
	private JComboBox cmbbxQueryNode;
	private JComboBox cmbbxIsolationLevel;
	private JComboBox cmbbxQueryType;
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

		cmbbxQueryNode = new JComboBox();
		cmbbxQueryNode.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cmbbxQueryNode.setBounds(10, 38, 157, 20);
		cmbbxQueryNode.addActionListener(this);
        /** DO NOT REARRANGE LIST ORDER HAHA DIS IS CRUICIAL */
        cmbbxQueryNode.addItem("Central");
        cmbbxQueryNode.addItem("Marinduque");
		cmbbxQueryNode.addItem("Palawan");
		add(cmbbxQueryNode);

		cmbbxIsolationLevel = new JComboBox();
		cmbbxIsolationLevel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cmbbxIsolationLevel.setBounds(10, 87, 157, 20);
		cmbbxIsolationLevel.addActionListener(this);
        /** DO NOT REARRANGE LIST ORDER HAHA DIS IS CRUICIAL */
		cmbbxIsolationLevel.addItem("Read uncommited");
		cmbbxIsolationLevel.addItem("Read commited");
		cmbbxIsolationLevel.addItem("Read repeatable");
		cmbbxIsolationLevel.addItem("Serializable");
		add(cmbbxIsolationLevel);

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

		cmbbxQueryType = new JComboBox();
		cmbbxQueryType.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cmbbxQueryType.setBounds(10, 136, 157, 20);
		cmbbxQueryType.addItem("Read");
		cmbbxQueryType.addItem("Write");
        cmbbxQueryType.addActionListener(this);
		add(cmbbxQueryType);

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

        controller.initializeUI();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == cmbbxQueryType){
            String query = controller.getQueryStatement(getInputQueryType(), getInputQueryNode());
            setQueryTextArea(query);
		} else
		if(ae.getSource() == btnSend){
			String ip = "hi, this is where you change the IP";
//			if(cmbbxQueryNode.getSelectedItem().toString().equalsIgnoreCase("Palawan"))
//				ip = "192.162.1.8";
//			else if(cmbbxQueryNode.getSelectedItem().toString().equalsIgnoreCase("Marinduque"))
//				ip = "192.162.1.9";
//			else if(cmbbxQueryNode.getSelectedItem().toString().equalsIgnoreCase("Central"))
//				ip = "192.162.1.10";
			String targetNode = getInputQueryNode();
			String query = getInputQuery();
			String type = getInputQueryType();
            String isolationLevel = cmbbxIsolationLevel.getSelectedItem().toString();
			controller.sendTransactonFromGUI(targetNode, query, type, isolationLevel);
		}
	}


//	
//	public void refreshTxtQuery() {
//		controller.refreshQuery();
//		setQueryTextArea(controller.getFinalQuery());
//	}

	public String getInputQueryNode() {
		String input = cmbbxQueryNode.getSelectedItem().toString();
		return input;
	}

    public String getInputIsolationType() {
		String input = cmbbxIsolationLevel.getSelectedItem().toString();
		return input;
	}

    public String getInputQuery() {
		String input = txtarQuery.getText();
		return input;
	}

    public String getInputQueryType() {
        String input = cmbbxQueryType.getSelectedItem().toString();
        return input;
    }

	public JTable getResultTable(){
		return resultTable;
	}

	public JTextPane getTxtTime(){
		return txtpnTime;
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

    public void setCmbbxQueryNode(DefaultComboBoxModel model){
        cmbbxQueryNode.setModel(model);
    }

    public void setCmbbxIsolationLevel(DefaultComboBoxModel model){
        cmbbxIsolationLevel.setModel(model);
    }

    public void setCmbbxQueryType(DefaultComboBoxModel model){
        cmbbxQueryType.setModel(model);
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

    public void setResultTableModel(TableModel resultModel) {
        this.resultTable.setModel(resultModel);
    }
}