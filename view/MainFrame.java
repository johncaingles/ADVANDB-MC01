package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class MainFrame extends JFrame {

	private JPanel currentPanel;
	
	public MainFrame(String appName) {
		try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
        }
		/** Frame setup */
		this.setTitle(appName);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(10, 72, 802, 520);
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void renderView(String view) {
		if(view.equals("Queries View")) {
			currentPanel = new TableView(this); 
		} else 
		if (view.equals("Customizable OLAP")) {
			currentPanel = new OLAPView(this);
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
