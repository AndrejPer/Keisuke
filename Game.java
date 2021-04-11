import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Game extends JFrame {

	public JPanel content, fieldManipulation, navigation, upperMenu;
	public JButton[][] cellButtons; // for the main playing field
	JButton[] digits;
	public JButton save, newGame, checker, displaySol, endless, load, setSize;
	public int[][] fields = {{0}}, // for storing the chosen digit layout
			solution; // for the solution to the current game

	ArrayList<String> horizontal, // for horizontal numbers
			vertical; // for vertical numbers

	int n, m; // dimensions
	
	boolean endlessGame = false; //for flagging endless mode

	static Random random = new Random(); // for starting in random dimensions

	ListenerGame lGame;
	ListenerNavigation lNavigation;  // declaring the different listeners we will be needing

	// panel.add(button, BorderLayout.NORTH);

	public Game(int first, int second, int[][] solutionMatrix, int[][] fieldsMatrix, boolean loadFlag, boolean endlessGame) {

		setVisible(true);
		setSize(1000, 1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



		// dimensions will be made randomly between 5 and 8 (check constructors below)
		n = first;
		m = second;
		
		//setting the endless flag
		this.endlessGame = endlessGame;
		

		
		
		// the matrix of buttons
		// the panel for displaying these buttons, ie playing cell
		cellButtons = new JButton[n][m];
		content = new JPanel();
		content.setLayout(new GridLayout(n, m));
		
		//in case the game was loaded from a file, fields and solution matrix will be supplied
		if(loadFlag) {
			solution = solutionMatrix;
			fields = fieldsMatrix;
			System.out.println("option 1");
		}
		
		

		// if the loading flag is false, but endless flag is true, than we have to create it in endless mode
		else if(this.endlessGame) {
			
			fields = new int[n][m];
			solution = new int[n][m];
			
			// one case is that the game is in endless mode and this is the first iteration
			if(solutionMatrix == null) {
				//these matrices will be 5x5 in this case
				createGame(solution, 1, 3, fields);
				System.out.println("option 2");
			}
		
		//else, if the game is in endless mode, but solution matrix exist, 
		//then we should use it to construct the game new game
		else {
			//in this case, the dimensions will be by one bigger than in the previous game
			createEndlessGame(solution, solutionMatrix, 1, 3, fields);
			
			System.out.println("option 3");
			System.out.println("dimension " + solutionMatrix.length + solutionMatrix[0].length);
			
			}
			
		}
		
			
			
			
		//if none if this is true, than we will make a standard random new game
		else {
			//in case game was started fresh, we will declare:
			// - the matrix for storing the game data and
			// - the matrix of solutions
			
			fields = new int[n][m];
			solution = new int[n][m];
			
			// creating the solutions
			createGame(solution, 1, 3, fields);
			
			System.out.println("option 4");
			
		}
		
		
		
		// functions for creating the numbers out of solution digits, which store them in String format for displaying to user
		horizontal = compileHorizontal(solution);
		vertical = compileVertical(solution);
		
		
		// Initialising playing field cells
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				// if this field is suppose to be shaded, make the button appropriate
				// some weird things are going on when trying this on mac so i tried different things
				if (solution[i][j] == -1) {// value for shaded fields will be marked by 999
					cellButtons[i][j] = new JButton();
					// cellButtons[i][j].setBackground(Color.BLACK);
					cellButtons[i][j].setOpaque(true); // it doesn't listen when on mac so...
					cellButtons[i][j].setForeground(Color.BLACK);
					cellButtons[i][j].setBorderPainted(false);
					content.add(cellButtons[i][j]);

					// we will not add listener to this button to prevent it from changing
				}

				else {
					cellButtons[i][j] = new JButton();
					cellButtons[i][j].setBackground(Color.RED);
					content.add(cellButtons[i][j]);
					
					//in case the game was loaded from a file, "flag" will be true, so we will load values if they were inputed
					//if the value of fields[i][j] is 0, then that cell was not "touched" by the user, so we will ignore it
					if(loadFlag && fields[i][j] != 0) cellButtons[i][j].setText(String.valueOf(fields[i][j]));
					
					// in case we are in endless mode, again, some cells need to have initial value displayed, 
					// namely all entries in the (n-1)x(m-1) upper left "sub-matrix"
					// we will copy the values from the solution matrix this time
					// we are ordering the conditions in a specific order, from the "least likely" to the "most likely"
					// this way, later conditions will not be evaluated uselessly if the "difficult" conditions are not met
					if(endlessGame && solutionMatrix != null && i < n - 1 && j < m - 1) {
						cellButtons[i][j].setText(String.valueOf(solution[i][j]));
					}

				}
			}
		}

		add(content);

		// field manipulation "keyboard" with digits, hint button and solution button
		fieldManipulation = new JPanel();
		fieldManipulation.setLayout(new GridLayout(4, 3));

		// initialising digits "keyboard" buttons
		digits = new JButton[10];
		for (int i = 0; i < 10; i++) {
			digits[i] = new JButton("" + (i + 1) % 10);
			digits[i].setPreferredSize(new Dimension(100, 100));
			fieldManipulation.add(digits[i]);

		}
		add(fieldManipulation, BorderLayout.EAST);

		// cell checker
		checker = new JButton("Check entry");
		fieldManipulation.add(checker);
		
		// displaying solution
		displaySol = new JButton("Display solution");
		fieldManipulation.add(displaySol);

		// initialising the listener who will manage the core of the game-play
		lGame = new ListenerGame(digits, cellButtons, fields, solution, checker, displaySol, this);

		// adding listener to all the buttons
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				cellButtons[i][j].addActionListener(lGame);
			}
		}

		// adding the aforementioned listener to digits button
		for (int i = 0; i < 10; i++) {
			digits[i].addActionListener(lGame);
		}

		// adding listener to the checker button
		checker.addActionListener(lGame);
		// adding listener to the  solution display button
		displaySol.addActionListener(lGame);

		// NAVIGATION
		// creating navigation part which will have
		// - button for saving current game in a file
		// - button for starting a new game
		// - going into endless mode (didn't do this part)
		// - button for loading a saved game
		navigation = new JPanel();
		navigation.setLayout(new GridLayout(1, 4));
		save = new JButton("Save");
		newGame = new JButton("New Game");
		endless = new JButton("Endless Mode");
		load = new JButton("Load Game");
		setSize = new JButton("Set Dimensions");
		

		lNavigation = new ListenerNavigation(save, newGame, endless, load, setSize, this);

		navigation.add(save);
		save.addActionListener(lNavigation);
		navigation.add(newGame);
		newGame.addActionListener(lNavigation);
		navigation.add(endless);
		endless.addActionListener(lNavigation);
		navigation.add(load);
		load.addActionListener(lNavigation);
		navigation.add(setSize);
		setSize.addActionListener(lNavigation);

		// UPPERMENU
		// Initialising the upperMenu panel which will contain
		// - Navigation panel
		// - text about the rule of the game
		// - the text about horizontal numbers
		// - the text about vertical numbers
		upperMenu = new JPanel();
		upperMenu.setLayout(new GridLayout(4, 1));
		upperMenu.add(navigation);

		JLabel rules = new JLabel("Choose a digit and then choose a cell!");
		JLabel hor = new JLabel("\nHorizontal numbers: " + horizontal);
		JLabel ver = new JLabel("\nVertical numbers: " + vertical);

		upperMenu.add(rules);
		upperMenu.add(hor);
		upperMenu.add(ver);
		add(upperMenu, BorderLayout.NORTH);

	}

	

	// constructor without parameters
	public Game() {
		this(random.nextInt(3) + 5, random.nextInt(3) + 5, null, null, false, false);
	}
	
	
	//constructor when doing endless mode
	public Game(int first, int second) {
		this(first, second, null, null, false, false);
		
	}
	

	// method for "creating" the game with digits in range [min, max]

	/*
	 * Desc: the game "distributes" the digits randomly throughout the 2D field
	 * Param: matrix - declared 2D array used for storing the digits; min - the min
	 * possible value for a digit max - the max possible value of the digit Pre:
	 * matrix be declared, min to be smaller than max Post: Result: Env: -
	 */

	private void createGame(int[][] matrix, int minimal, int maximal, int[][] fields) {
		
		Random random = new Random(); //initialising new random so no repetition of numbers when in endless mode
		
		int min, max;
		// in case max is smaller than min by some crazy accident
		if (maximal < minimal) {

			min = maximal;
			max = minimal;
		}

		else {
			min = minimal;
			max = maximal;
		}

		for (int i = 0; i < matrix.length; i++) {

			// deciding on the shaded part for every row randomly
			int shaded = random.nextInt(matrix[0].length);

			for (int j = 0; j < matrix[0].length; j++) {

				if (j == shaded) {
					// marking the shaded cell with -1, and storing the info in the fields array as
					// well
					matrix[i][j] = -1;
					fields[i][j] = -1;
					
					
					System.out.print(matrix[i][j] + "    ");
				}

				// adding digits from 1 - 3 randomly
				else {
					// adding a random digit
					matrix[i][j] = random.nextInt(max + 1 - min) + min;
					// System.out.printf("The correct digit for cell (%d, %d) is %d\n", i, j,
					// matrix[i][j]);
					System.out.print(matrix[i][j] + "    ");
				}

			}

			System.out.println("");
		}

	}
	
	
	
	
	
	private void createEndlessGame(int[][] newSolution, int[][] pastSolution, int minimal, int maximal, int[][] fields) {
		Random random = new Random(); //initialising new random so no repetition of numbers when in endless mode
		
		int min, max, shaded;
		// in case max is smaller than min by some crazy accident
		if (maximal < minimal) {

			min = maximal;
			max = minimal;
		}

		else {
			min = minimal;
			max = maximal;
		}

		// we will need random numbers, so initialising random

		for (int i = 0; i < newSolution.length; i++) {

			// deciding on the shaded part for the new row
			// will be used only for the last row
			shaded = random.nextInt(newSolution[0].length);

			for (int j = 0; j < newSolution[0].length; j++) {
				
				//first, we will copy the appropriate values 
				if(i < newSolution.length - 1 && j < newSolution[0].length - 1) {
					newSolution[i][j] = pastSolution[i][j];
					fields[i][j] = newSolution[i][j];
					
				}

				else {
					if (j == shaded) {
						// marking the shaded cell with -1, and storing the info in the fields array as well
						newSolution[i][j] = -1;
						fields[i][j] = -1;
						
						
						System.out.print(newSolution[i][j] + "    ");
					}

					// adding digits from 1 - 3 randomly
					else {
						// adding a random digit
						newSolution[i][j] = random.nextInt(max + 1 - min) + min;
						// System.out.printf("The correct digit for cell (%d, %d) is %d\n", i, j,
						// matrix[i][j]);
						System.out.print(newSolution[i][j] + "    ");
					}
				
				}
				


			}

			System.out.println("");
		}

		
	}
	
	
	
	

	/*
	 * Desc: creates a dynamic array of numbers composed of given digits in the form
	 * of a String Param: solution - 2D array with the correct digits for the given
	 * game out of which we will construct the vertical numbers Pre: solution
	 * initialised Post: numbers in String format Result: all the vertical numbers
	 * as ArrayList of Strings 
	 * Env: -
	 */
	private ArrayList<String> compileVertical(int[][] solution) {
		ArrayList<String> ver = new ArrayList<String>(); // temporarily
		String tmp; // for storing temporarily current number

		for (int i = 0; i < solution[0].length; i++) {
			for (int j = 0; j < solution.length; j++) {
				
				tmp = new String("");
				while (j < solution.length && solution[j][i] != -1) {
					//concatenating the digit to the current sequence of digits
					tmp = tmp + ("" + solution[j][i]);
					j++;
					

				}
				
				// to avoid dealing with empty strings
				if (tmp.length() > 0)
					ver.add(tmp);

			}
		}

		Collections.sort(ver);// sorting in alphabetical order, will make no sense for number, so no hints given to players
		System.out.println(ver);

		return ver;

	}

	/*
	 * Desc: creates a dynamic array of numbers composed of given digits in the form
	 * of a String Param: solution - 2D array with the correct digits for the given
	 * game out of which we will construct the horizontal numbers Pre: solution
	 * initialised Post: numbers in String format Result: all the horizontal numbers
	 * as ArrayList of Strings Env: -
	 */

	private ArrayList<String> compileHorizontal(int[][] solution) {

		ArrayList<String> hor = new ArrayList<String>(); // temporarily
		String tmp; // for storing temporarily current number

		for (int i = 0; i < solution.length; i++) {
			for (int j = 0; j < solution[0].length; j++) {
				
				tmp = new String("");
				while (j < solution[0].length && solution[i][j] != -1) {

					tmp = tmp + ("" + solution[i][j]);
					j++;
					

				}
				
				// to avoid dealing with empty strings
				if (tmp.length() > 0)
					hor.add(tmp);

			}
		}

		Collections.sort(hor); // sorting in alphabetical order, will make no sense for number, so no hints given to players
		System.out.println(hor);

		return hor;

	}

}
