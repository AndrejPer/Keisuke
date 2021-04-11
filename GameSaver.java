import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GameSaver {
	Game currentGame;
	
	//the constructor will save all the relevant info into a file with the following format
	//first line - "n m" (dimensions)
	//following n lines - matrix n times m of the solution of the given game
	//next n lines - matrix n times m of the inputed digits (0 if the cell was not touched)
	public GameSaver(Game frame) {
		currentGame = frame;
		
		//using file chooser to open the dialog and enable the user to select where to save the file
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("text", "txt");
		chooser.setFileFilter(filter);
		int ret = chooser.showSaveDialog(null);
		
		if(ret == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
		
		try {
			//if possible, writing into the file
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			
			//first writing the dimensions
			writer.write(frame.n + " " + frame.m + "\n");
			
			//second writing the solution 
			for(int i = 0; i < frame.n; i++) {
				for(int j = 0; j < frame.m; j++) {
					writer.write("" + frame.solution[i][j] + " ");
				}
				
				writer.write("\n");
			}
			
			//writer.write("\n"); maybe better without this line in between
			
			//finally, writing the so far inputed digits
			for(int i = 0; i < frame.n; i++) {
				for(int j = 0; j < frame.m; j++) {
					writer.write("" + frame.fields[i][j] + " ");
				}
				
				writer.write("\n");
			}
			
			
			writer.close();
			
		} catch(Exception e) {
			System.out.println("Error");
		}
		
		}
	}
	
	

}
