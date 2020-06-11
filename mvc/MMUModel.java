package mvc;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class MMUModel {
	// list of lines of commands
	private List<String> commands;
	
	// constructor - open the command file, read all text and then call the function readLog
	public MMUModel(String filePath)
	{
		// creating the list of the commands
		commands = new LinkedList<String>();
		   
		// content of the file
		String content = "";
		
		// open the command file
	    File file = new File(filePath);
	    try {
	    	
	    	// create a reader for the file
	        FileReader reader = new FileReader(file);
	        
	        //read all the chars of the file to chars array
	        char[] chars = new char[(int) file.length()];
	        reader.read(chars);
	        
	        // creating a string from the char array
	        content = new String(chars);
	        
	        // closing the reader
	        reader.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	   
	   // call to readLog - filling the list of commands by lines
	   readLog(content);
	}
	
	// get commands
	public List<String> getModel()
	{
		return commands;
	}
	
	private void readLog(String log)
	{
		// splitting the file by lines
		String[] lines = log.split("\r\n");

		// for each line
		for(String line : lines)
		{
			// if line length is 0 - just empty line
			if(line.length() == 0)
				continue;
			
			// spliting the line by spaces
			String[] splitedLine = line.split(" ");
			
			// checking the first word of the line and by that validating the command
			if(splitedLine[0].equals("RC"))
			{
				commands.add(line);
			}
			else if(splitedLine[0].equals("PN"))
			{
				commands.add(line);
			}
			else if(splitedLine[0].equals("PF"))
			{
				commands.add(line);
			}
			else if(splitedLine[0].equals("PR"))
			{
				commands.add(line);
			}
			else if(splitedLine[0].length() >= 2 && splitedLine[0].charAt(0) == 'P' && Character.isDigit(splitedLine[0].charAt(1)))
			{
				commands.add(line);
			}
			
		}
	}
}
