package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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

public class TableView extends JPanel implements ActionListener, KeyListener  
{
	private JTable table;
	private MainFrame mainFrame;

	/** Controller */
	private TableViewController controller;
	
	/** Panel Components */
	private JButton btnSubmit;
	private JTextArea txtarQuery;
	private JTable resultTable;
	
    public TableView(MainFrame mainFrame)
    {
    	this.setLayout(null);
    	this.setBounds(10, 72, 604, 371);
    	this.mainFrame = mainFrame;
    	controller = new TableViewController(this);
    	
    	btnSubmit = new JButton("SUBMIT");
    	btnSubmit.setFont(new Font("Roboto", Font.PLAIN, 14));
    	btnSubmit.setBounds(505, 13, 89, 43);
    	btnSubmit.addActionListener(this);
    	this.add(btnSubmit);
    	
    	txtarQuery = new JTextArea();
    	txtarQuery.setToolTipText("Enter query here");
    	txtarQuery.setBounds(10, 13, 485, 43);
    	this.add(txtarQuery);
    	
    	/** Initial model for table */
        String [] header={"name","age", "null"};
        String [][] data={{"akash","20"},{"pankaj","24"},{"pankaj","24"},{"pankaj","24"},{"pankaj","24"}};
        DefaultTableModel tableModel = new DefaultTableModel(data,header);
        
        /** JTable setup */
        resultTable = new JTable(tableModel);
        resultTable.setPreferredScrollableViewportSize(new Dimension(450,63));
        resultTable.setFillsViewportHeight(true);
        
        JScrollPane jscrllpnlTable=new JScrollPane(resultTable);
        jscrllpnlTable.setBounds(10, 67, 584, 263);
        jscrllpnlTable.setVisible(true);
        add(jscrllpnlTable);
        
    }
    
    /** Action Listeners */
    @Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == btnSubmit) {
			// TODO query thingy
		}
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
