import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class sizeSettingWindow extends JFrame{
	
	JTextField rows, columns;
	JButton set;
	Game current;
	
	public sizeSettingWindow(Game current) {
		setSize(400, 300);
		setVisible(true);
		//we don't want the program to terminate when closing this window, so setting to dispose on close
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		this.current = current;
		
		rows = new JTextField("");
		JLabel labelr = new JLabel("Rows [ >= 4]");
		columns = new JTextField("");
		JLabel labelc = new JLabel("Columns [ >= 4]");
		set = new JButton("Set");
		
		JPanel content = new JPanel(new GridLayout(5, 1));
		
		//adding components
		content.add(labelr);
		content.add(rows);
		content.add(labelc);
		content.add(columns);
		content.add(set);
		
		add(content);
		
		//checking if correct
		ListenerSizeSetting l = new ListenerSizeSetting(set, current, rows, columns, this);
		
		set.addActionListener(l);
	
		
	}

}
