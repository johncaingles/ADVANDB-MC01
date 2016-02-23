package driver;
import javax.swing.JFrame;

import view.MainFrame;
import view.TableView;


public class Driver 
{	
	public static void main (String[]args)
	{
		new MainFrame("ADVANDB MC01").renderView("Table View");
	}
}
