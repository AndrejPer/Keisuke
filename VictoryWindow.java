import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VictoryWindow extends JFrame {
	
	JButton saveExit, newGame;
	ListenerVictory l;
	Game currentGame;
	
	/*
	 * Desc: constructor for the class
	 * Param: current - current frame 
	 * Pre: frame properly set
	 * Post: 
	 * Result: window constructed; new game constructed or current one saved and exited
	 * Env: -
	 */
	
	public VictoryWindow(Game current) {
		setSize(400, 300);
		setVisible(true);
		//we don't want the program to terminate when closing victory window, so setting to dispose on close
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		currentGame = current;
		
		
		JPanel content = new JPanel(new GridLayout(3, 1));
		add(content);
		
		//message of congratulation
		JLabel message = new JLabel("Congrats! You Won!");
		content.add(message);
		
		saveExit = new JButton("Save and exit");
		newGame = new JButton("New game");
		content.add(saveExit);
		content.add(newGame);
		
		l = new ListenerVictory(saveExit, newGame, currentGame, this);
		
		saveExit.addActionListener(l);
		newGame.addActionListener(l);
		
	}
	
}

