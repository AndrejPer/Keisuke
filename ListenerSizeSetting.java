import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

public class ListenerSizeSetting implements ActionListener {
	JButton set;
	int n, m;
	Game current;
	JTextField rows, columns;
	sizeSettingWindow ssw;
	
	public ListenerSizeSetting(JButton set, Game current, JTextField rows, JTextField columns, sizeSettingWindow ssw) {
		this.set = set;
		this.current = current;
		this.rows = rows;
		this.columns = columns;
		this.ssw = ssw;
	}
	
	/*
	 * Desc: checkes if set button pressed, then tries to collect numbers from the text field
	 * Param: e - ActionEvent object 
	 * Pre: 
	 * Post: 
	 * Result: 
	 * Env: -
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(set == e.getSource()) {
			System.out.print("got here");
			
			
			
			try {
				n = Integer.parseInt(rows.getText());
				m = Integer.parseInt(columns.getText());
				
			} catch (NumberFormatException e1) {
				
			}
			
			ssw.dispose();
			current.dispose();
			Game newGame = new Game(n, m, null, null, false, false);
		}
		
	}
	
	

}
