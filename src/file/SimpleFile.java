package file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SimpleFile extends File{

	private static final long serialVersionUID = 1L;

	public SimpleFile(String filePath) {
		super(filePath);
	}
	
	public boolean createFile() {
		boolean success = false;
		
		try {
			success = this.createNewFile();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return success;
	}
	
	public boolean writeFile(String txt) {
		boolean success = false;
		
		try {
			FileWriter writer = new FileWriter(this.getAbsolutePath());
			writer.write(txt);
			writer.close();
			success = true;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return success;
	}
	
	public String readFile() {
		String txt = "";
		
		try {
			Scanner reader = new Scanner(this);
			
			while (reader.hasNextLine()) {
				txt += reader.nextLine() + "\n";
		    }
		    reader.close();
		    
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		return txt;
	}
}
