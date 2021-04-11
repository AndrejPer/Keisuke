import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ListenerGame implements ActionListener{
	
	JButton[][] cellButtons;
	JButton[] digits;
	int currentDigit = 0;
	//we have the 2D array with buttons on one side and an array of digits 0-9 on the other
	//when a digit is pressed, the value is saved in currentDigit
	//when a cell is pressed, its displayed text is changed to the value of current value of "currentDigit"
	//"currentDigit" initialised to 0 to avoid any mumbo jumbo
	
	

	int[][] fields, solution;
	//"field" is for storing the current digit setup
	//"solution" contains the solution of course

	
	VictoryWindow vw;
	//when the user solves it correctly, a window will pop
	
	
	JButton checker, displaySol;
	boolean toCheck = false; 
	//checking consists of seeing if the button checker was pressed;
	//if it was, setting a flag "toCheck" to true
	//next, if a button in the field was pressed and toCheck is true, we will display the info about the correctness of the given input and set "toCheck" to false
	
	Game currentGame;
	//to be able to save the game, i need the Game object
	
	public ListenerGame(JButton[] digits, JButton[][] cells, int[][] fields, int[][] solution, JButton checker, JButton displaySol, Game currentGame) {
		
		this.digits = digits;
		cellButtons = cells;
		this.fields = fields;
		this.solution = solution;
		this.checker = checker;
		this.displaySol = displaySol;
		this.currentGame = currentGame;


	}
	
	
	//there are several parts
	//1) if the pressed button was a 0-9 digit
	//2) if the pressed button was a cell in the field, then update value
	//3) if checker was pressed, then setting the program so it checks the value
	//4) if the victory window has popped up and a button from it was pressed
	public void actionPerformed(ActionEvent e) {
		
		System.out.println("value of endless" + currentGame.endlessGame);
		
		JButton pressed = (JButton) e.getSource();
		
		//getting digit
		for(int i = 0; i < digits.length; i++) {
			if(pressed == digits[i]) {
				currentDigit = Integer.parseInt(digits[i].getText());
				System.out.println("Current digit: " + currentDigit);
			}
		}
		
		//setting value in cell
		for(int i = 0; i < cellButtons.length; i++) {
			for(int j = 0; j < cellButtons[0].length; j++) {
				if(pressed == cellButtons[i][j] && solution[i][j] != -1) {
					
					//in case this button was pressed to be checked
					if(toCheck) {
						
						//System.out.println("Gotten into checker part!");
						
						if(Integer.parseInt(cellButtons[i][j].getText()) == solution[i][j]) {
							cellButtons[i][j].setForeground(Color.GREEN);
						}
						
						else {
							cellButtons[i][j].setForeground(Color.RED);
						}
						
						//setting the value of "toCheck" back to false so no checking happens without invocation
						toCheck = false;
					}
					
					else {
						
						//setting colour back to black in case the given cell was "checked" and recoloured
						cellButtons[i][j].setForeground(Color.BLACK);
						cellButtons[i][j].setText(String.valueOf(currentDigit));
						fields[i][j] = currentDigit;
						System.out.printf("Position (%d, %d) updated to value of %d\n", i, j, currentDigit);
					
					
					
						//test
						System.out.println(check());
				
						//to catch the moment the solution is found
						if(check()) {
							
							//first, if it's not endless mode, displaying the victory window
							if( ! currentGame.endlessGame)
								vw = new VictoryWindow(currentGame);
							
							else {
								//setting up the variables so we can send them to the constructor
								int n = currentGame.n + 1;
								int m = currentGame.m + 1;
								int[][] pastSolution = currentGame.solution;
								
								//now that we copied the info, we will kill the previous window
								currentGame.dispose();
								
								//we will only be needing solutions this time, not the user input in the form of fields array, since they are equivalent
								Game newEndlessGame = new Game(n, m, pastSolution, null, false, true);
							}
						}
					
					}
				}
			}
		}
		
		if(checker == pressed) {
			//System.out.println("Checker pressed");
			toCheck = true;
		}
		
		//for displaying solution
		if(displaySol == pressed) {
			
			System.out.println("Displaying commenced");
			
			//we will iterate through the buttons and make them display the correct value
			for(int i = 0; i < cellButtons.length; i ++) {
				for(int j = 0; j < cellButtons[0].length; j++) {
					//we need to skip the shaded cells
					if(solution[i][j] == -1) continue;
					cellButtons[i][j].setText(String.valueOf(solution[i][j]));
				}
			}
			
		}
		
}



	private boolean check() {
		for(int i = 0; i < fields.length; i++) {
			for(int j = 0; j < fields[0].length; j++) {
				//in case all the digits are correct
				//added the condition with "0" so the shaded cells do not affect the result
				if(fields[i][j] != solution[i][j]) {
					System.out.printf("seems these are different on (%d, %d): %d and %d\n", i, j, fields[i][j], solution[i][j]);
					return false;
				}
			}
		}
		
		//if the return false statement hasn't been executed, all the fields are the same
		return true;
	}
	
	
}
