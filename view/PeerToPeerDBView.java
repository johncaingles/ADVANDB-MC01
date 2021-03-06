package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.Socket;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import controller.PeerToPeerDBController;
import model.AppDatabase;

public class PeerToPeerDBView extends JPanel implements ActionListener, KeyListener {
    private JTable table;
    private MainFrame mainFrame;
    private JScrollPane jscrllpnlTable;
    private JLabel lblQuery;
    private JComboBox cmbbxQueryNode;
    private JComboBox cmbbxIsolationLevel;
    private JComboBox cmbbxQueryType;
    private JLabel lblQueryDetails;
    private JButton btnSend;
    private JTextArea txtarLog;
    private JScrollBar sb;
    private JButton btnPalawan;
    private JButton btnMarinduque;
    private JButton btnCentral;
    private JScrollPane scrollPane_1;
    private DefaultListModel listModel = new DefaultListModel();
    /** Controller */
    private PeerToPeerDBController controller;
    private JTextArea txtarQuery;
    private JTable resultTable;
    private JScrollPane scrollPane_2;
    private JButton btnAdd;
    private JList list;
    private JButton btnDelete;
    private JTextField txtfldTimeDelay;

    public PeerToPeerDBView(MainFrame mainFrame, int nodeType) {
        this.setLayout(null);
        this.setBounds(10, 72, 802, 583);
        controller = new PeerToPeerDBController(this, nodeType);

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
        jscrllpnlTable.setBounds(177, 183, 615, 147);
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

        btnSend = new JButton("Send");
        btnSend.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnSend.setBounds(30, 437, 109, 31);
        btnSend.addActionListener(this);
        add(btnSend);

        cmbbxQueryType = new JComboBox();
        cmbbxQueryType.setFont(new Font("Tahoma", Font.PLAIN, 11));
        cmbbxQueryType.setBounds(10, 136, 157, 20);
        cmbbxQueryType.addItem("Read All");
        cmbbxQueryType.addItem("Read Item");
        cmbbxQueryType.addItem("Write - Update");
        cmbbxQueryType.addItem("Write - Delete");
        cmbbxQueryType.addActionListener(this);
        add(cmbbxQueryType);

        JLabel lblQuery_1 = new JLabel("Query");
        lblQuery_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblQuery_1.setBounds(177, 466, 157, 24);
        add(lblQuery_1);

        JLabel lblTable = new JLabel("Table");
        lblTable.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblTable.setBounds(177, 161, 157, 24);
        add(lblTable);

        JLabel lblType = new JLabel("Type");
        lblType.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblType.setBounds(10, 112, 157, 24);
        add(lblType);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(177, 28, 615, 133);
        add(scrollPane);

        sb = scrollPane.getVerticalScrollBar();
        sb.setValue( sb.getMaximum() );

        txtarLog = new JTextArea();
        scrollPane.setViewportView(txtarLog);
        txtarLog.setToolTipText("Enter query here");

        scrollPane_2 = new JScrollPane();
        scrollPane_2.setBounds(177, 494, 615, 57);
        add(scrollPane_2);

        txtarQuery = new JTextArea();
        scrollPane_2.setViewportView(txtarQuery);
        txtarQuery.setToolTipText("Enter query here");

        btnPalawan = new JButton("Palawan");
        btnPalawan.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnPalawan.setBounds(30, 307, 109, 23);
        btnPalawan.addActionListener(this);
        add(btnPalawan);

        btnMarinduque = new JButton("Marinduque");
        btnMarinduque.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnMarinduque.setBounds(30, 283, 109, 23);
        btnMarinduque.addActionListener(this);
        add(btnMarinduque);

        btnCentral = new JButton("Central");
        btnCentral.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnCentral.addActionListener(this);
        btnCentral.setBounds(30, 259, 109, 23);
        add(btnCentral);
        
        JLabel lblCheckStatus = new JLabel("Check Status");
        lblCheckStatus.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblCheckStatus.setBounds(10, 224, 157, 24);
        add(lblCheckStatus);
        
        JLabel lblTransactions = new JLabel("Transactions");
        lblTransactions.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblTransactions.setBounds(177, 330, 157, 24);
        add(lblTransactions);
        
        scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(177, 355, 615, 113);
        add(scrollPane_1);
        
        list = new JList(listModel);
        scrollPane_1.setColumnHeaderView(list);

        
        btnDelete = new JButton("Delete");
        btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnDelete.setBounds(30, 395, 109, 31);
        btnDelete.addActionListener(this);
        add(btnDelete);
        
        btnAdd = new JButton("Add");
        btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnAdd.setBounds(30, 360, 109, 31);
        btnAdd.addActionListener(this);
        add(btnAdd);
        
        JLabel lblTimeDelay = new JLabel("Time Delay");
        lblTimeDelay.setBounds(10, 167, 157, 18);
        add(lblTimeDelay);
        
        txtfldTimeDelay = new JTextField();
        txtfldTimeDelay.setBounds(10, 193, 89, 32);
        add(txtfldTimeDelay);
        txtfldTimeDelay.setColumns(10);
        
        JLabel lblSeconds = new JLabel("seconds");
        lblSeconds.setBounds(109, 193, 59, 32);
        add(lblSeconds);

        controller.initializeUI();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == cmbbxQueryType){
            String query = controller.getQueryStatement(getInputQueryType(), getInputQueryNode());
            setQueryTextArea(query);
        } else
        if(ae.getSource() == btnSend){
//            String targetNode = getInputQueryNode();
//            String query = getInputQuery();
//            String type = getInputQueryType();
//            String isolationLevel = cmbbxIsolationLevel.getSelectedItem().toString();
//            controller.sendTransactionFromGUI(targetNode, query, type, isolationLevel);
            controller.sendTransactions();
        }
        else if(ae.getSource() == btnPalawan)
        {
            checkPalawan();

        }
        else if(ae.getSource() == btnMarinduque)
        {
            checkMarinduque();
        }
        else if(ae.getSource() == btnCentral)
        {
            checkCentral();
        }
        else if(ae.getSource() == btnAdd)
        {
            String targetNode = getInputQueryNode();
            String query = getInputQuery();
            String type = getInputQueryType();
            String isolationLevel = cmbbxIsolationLevel.getSelectedItem().toString();
            int timeDelay = getTimeDelay();
            String sTrans = controller.addTransactionFromGUI(targetNode, query, type, isolationLevel, timeDelay);
            listModel.addElement(sTrans);
        }
        
        else if(ae.getSource() == btnDelete)
        {
            int i = list.getSelectedIndex();
            System.out.println(i);
            listModel.remove(i);
            controller.deleteTransactionFromGUI(i);
        }
    }


//	
//	public void refreshTxtQuery() {
//		controller.refreshQuery();
//		setQueryTextArea(controller.getFinalQuery());
//	}
    
    public int getTimeDelay() {
    	return Integer.parseInt(txtfldTimeDelay.getText());
    }

    public void setTxtfldTimeDelay(String s) {
        this.txtfldTimeDelay.setText(s);
    }

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

    public String checkPalawan()
    {
        try {
            (new Socket(AppDatabase.PALAWAN_IP_ADDRESS, AppDatabase.PALAWAN_PORT_NUMBER)).close();
            System.out.println("PALAWAN is UP");
        } catch (IOException e) {
            System.out.println("PALAWAN is DOWN");
            return "PALAWAN is DOWN";
        }
        return "PALAWAN is UP";
    }
    public String checkMarinduque()
    {
        try {
            (new Socket(AppDatabase.MARINDUQUE_IP_ADDRESS, AppDatabase.MARINDUQUE_PORT_NUMBER)).close();
            System.out.println("MARINDUQUE is UP");
        } catch (IOException e) {
            System.out.println("MARINDUQUE is DOWN");
            return "MARINDUQUE is DOWN";
        }
        return "MARINDUQUE is UP";
    }
    public String checkCentral()
    {
        try {
            (new Socket(AppDatabase.CENTRAL_IP_ADDRESS, AppDatabase.CENTRAL_PORT_NUMBER)).close();
            System.out.println("CENTRAL is UP");
        } catch (IOException e) {
            System.out.println("CENTRAL is DOWN");
            return "CENTRAL is DOWN";
        }
        return "CENTRAL is UP";
    }

    public JTable getResultTable(){
        return resultTable;
    }

/*    public JTextPane getTxtTime(){
        return txtpnTime;
    }*/

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

    public void scrollDownLogTxtAr() {
        sb.setValue( sb.getMaximum() );
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
