import java.io.BufferedReader;
import java.io.File;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GameLoader {
	
	JFileChooser chooser;
	Game loadedGame;
	JLabel message = new JLabel();
	BufferedReader reader;
	int n, m;
	int[][] solution, field;
	
	public GameLoader() {
		
		
		chooser = new JFileChooser();
		//setting the path of the folder to be displayed by default - change this when testing!!
		chooser.setCurrentDirectory(new File("/Users/Andrej/Desktop"));
		//we want accept only txt files
		chooser.setFileFilter(new FileNameExtensionFilter("text files", "txt"));
		
		//for checking if the loading of the file succeed
		int returnValue = chooser.showOpenDialog(loadedGame);
		
		//if the return value is appropriate, initiate the file reading
		if(returnValue == JFileChooser.APPROVE_OPTION) {
			
			try {
				
				System.out.println("might just open the file!");
				
				File file = chooser.getSelectedFile();
				Scanner scan = new Scanner(file);
				
				//first line should be dimensions
				String dimensionPart = scan.nextLine();
				System.out.println("dimensions part: " + dimensionPart);
				String[] dimensions = dimensionPart.split(" ");
				
				n = Integer.parseInt(dimensions[0]);
				m = Integer.parseInt(dimensions[1]);
				
				//now having dimensions, we will declare the respective matrices
				solution = new int[n][m];
				field = new int[n][m];
				
				
				for(int i  = 0; i < n; i++) {
					String line = scan.nextLine();
					//System.out.println("Gotten to first for with line " + line);
					String[] digits = line.split(" "); //should be m of them
					
					
					if(digits.length == m) {
						for(int j = 0; j < m; j++) {
							
							int tmp = Integer.parseInt(digits[j]);
							System.out.println(tmp);
							solution[i][j] = tmp;

						}
						
					}
					
					else {
						System.out.println("Error ");
						break;
					}
					
				}
				
				//now the user fields
				for(int i  = 0; i < n; i++) {
					String line = scan.nextLine();
					String[] digits = line.split(" "); //should be m of them
					if(digits.length != m) {
						System.out.println("Error");
						break;
					}
					
					else {
						for(int j = 0; j < m; j++) {
							field[i][j] = Integer.parseInt(digits[j]);
							System.out.println("" + digits[j] + "saved");
						}
					}
					
				}
				
				
				
			} catch (Exception e) {
				
			}
			
			
			loadedGame = new Game(n, m, solution, field, true, false);
			
		}
		
		else {
			message.setText("No file chosen");
		}
	}

}
