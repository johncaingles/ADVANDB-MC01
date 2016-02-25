package view;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {

	private JPanel currentPanel;
	
	public MainFrame(String appName) {
		/** Frame setup */
		this.setTitle(appName);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(10, 72, 802, 520);
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void renderView(String view) {
		if(view.equals("Table View")) {
			currentPanel = new TableView(this); 
		}
		
		this.setContentPane((JPanel) currentPanel);   
		frameRevalidate();     
    }
	
	private void frameRevalidate() {
		validate();
		repaint();
		setVisible(true);
	}
}
