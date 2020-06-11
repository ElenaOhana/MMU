package testers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import processes.*;
import util.MMULogger;
import memory.MemoryManagementUnit;
import algorithms.FIFOAlgoImpl;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

public class MMUDriver {
	
	// path to the json configuration file 
	public static String CONFIG_FILE = "config.txt";
	public static final int ramCapacity = 3;
	
	public static void main(String[] args)
	{
		// create mmu with fifo algorithm
		MemoryManagementUnit mmu = new MemoryManagementUnit(ramCapacity, new FIFOAlgoImpl<Integer>(ramCapacity));
		
		// read the json configuration file to the RunConfiguration object
		RunConfiguration runConfig = readConfigurationFile();
		
		// get the cycles from the RunConfiguration object
		List<ProcessCycles> processCycles = runConfig.getProcessesCycles();
		
		// for each set of cycles create one process
		List<processes.Process> processes = createProcesses(processCycles, mmu);
		
		MMULogger.getInstance().write("RC " + ramCapacity, Level.INFO);
		MMULogger.getInstance().write("PN " + processes.size(), Level.INFO);
		
		// run all the process
		runProcesses(processes);
	}
	
	// reads the json configuration file and create a RunConfiguration object
	public static RunConfiguration readConfigurationFile()
	{	
		// reads the json configuration file and create a RunConfiguration object
		try {
			return new Gson().fromJson(new JsonReader(new FileReader(CONFIG_FILE)), RunConfiguration.class);
		} 
		catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// if something went wrong return null
		return null;
	}
		
	// for each a set of cycles create new process
	public static List<processes.Process> createProcesses(List<ProcessCycles> processCycles, MemoryManagementUnit mmu)
	{
		// the list of the new process
		LinkedList<processes.Process> processes = new LinkedList<processes.Process>();
		
		// for each cycle
		for(int i=0 ; i<processCycles.size() ; i++)
			processes.add(new processes.Process(i, mmu, processCycles.get(i))); // create new process for cycles
		
		return processes;
	}
	
	// for ead process creates a new thread and start it
	public static void runProcesses(List<processes.Process> processes)
	{
		// for each process
		for(int i=0 ; i< processes.size() ; i++)
			new Thread(processes.get(i)).start(); // start the process
	}
}
