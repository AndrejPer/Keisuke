import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ListenerVictory implements ActionListener{
	
	JButton exit, newGame;
	Game currentGame;
	VictoryWindow vw;
	
	public ListenerVictory(JButton exit, JButton newGame, Game currentGame, VictoryWindow vw) {
		this.exit = exit;
		this.newGame = newGame;
		this.currentGame = currentGame;
		this.vw = vw;
	}

	/*
	 * Desc: checkes if the player decides to save and exit or to play anew game
	 * Param: e - ActionEvent object 
	 * Pre: 
	 * Post: 
	 * Result: 
	 * Env: -
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(exit == e.getSource()) {
			System.out.println("she wants to exit!!");
			//sending the current game to be saved
			GameSaver saver = new GameSaver(currentGame);
			//getting rid of the victory window
			vw.dispose();
			//closing the game
			currentGame.dispose();
		}
		
		if(newGame == e.getSource()) {
			currentGame.dispose();
			
			Game nextGame = new Game();

			
		}
		
	}

}
