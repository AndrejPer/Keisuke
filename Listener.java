import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Listener implements ActionListener {
	
	public JButton[][] buttons;
	
	
	Listener(JButton[][] buttons) {
		this.buttons = buttons;
	}
	
	
	
	public void actionPerformed(ActionEvent a) {
		JButton pressed = (JButton) a.getSource();
		pressed.setText("Thanks for the press");
		
		
		//iterating through button to see which one was pressed
		for(int i = 0; i < buttons.length; i++) {
			for(int j = 0; j < buttons[0].length; j++) {
				if(pressed.equals(buttons[i][j])) {
					System.out.printf("Pressed (%d, %d)!\n", i, j);
				}
			}
		}
	}

}
