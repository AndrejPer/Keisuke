import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;


public class ListenerNavigation implements ActionListener{
	
	JButton save, newGame, endless, load, setSize;
	Game frame;

	
	public ListenerNavigation(JButton save, JButton newGame, JButton endless, JButton load, JButton setSize, Game frame) {
		this.save = save;
		this.newGame = newGame;
		this.endless = endless;
		this.frame = frame;
		this.load = load;
		this.setSize = setSize;

	}
	
	//one of five possible actions
	//1) if save pressed - current game is saved to a txt file
	// 2) newGame - terminates the current game and initiates a new one
	// 3) initiates the endless mode
	// 4) loads a presaved game from a file
	// 5) asks user to set the dimensions of the new game

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//i will use only if statement, not if..else if..else, to be sure code is executed in rigth conditions
			
		//first if pressed button is "save"
		if(save == e.getSource()) {
			GameSaver saver = new GameSaver(frame);
			
		}
		
		if(newGame == e.getSource()) {
			frame.dispose();
			Game newerGame = new Game();
			
		}
		
		if(endless == e.getSource()) {
			frame.dispose();
			//last parameter has to be true now, since it's the flag for endless
			Game endlessGame = new Game(5, 5, null, null, false, true);
		}
		
		if(load == e.getSource()) {
			GameLoader loader = new GameLoader();
			frame.dispose();
		}
		
		if(setSize == e.getSource()) {
			
			sizeSettingWindow ssw = new sizeSettingWindow(frame);
			
		}

		
	}

}
