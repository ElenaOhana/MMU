package processes;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import util.MMULogger;

import memory.MemoryManagementUnit;
import memory.Page;

// implements Runnable, which mean he has to implement the function run 
// which run when we start the thread/process
public class Process implements Runnable 
{	
	// process id
	private int id;
	
	// process cycle
	private ProcessCycles processCycles;
	
	// refernce to the mmu
	private MemoryManagementUnit mmu;
	
	// lock object to sync between the threads
	private static Object lockObject = new Object();
	
	// constructor
	public Process(int id, MemoryManagementUnit mmu, ProcessCycles processCycles)
	{
		this.id = id;
		this.mmu = mmu;
		this.processCycles = processCycles;
	}
	
	// run function when the thread start
	public void run()
	{
		// telling java this is a critical code and we need to sync between the threads
		synchronized (lockObject) {

			// getting the list of cycles that the process needs to execute
			List<ProcessCycle> processCyclesList = processCycles.getProcessCycles();
			
			// for each cycle
			for(int i=0 ; i<processCyclesList.size() ; i++)
			{
				System.out.println(id + " before mmu");
				
				// get the i'th cycle
				ProcessCycle currProcessCycle = processCyclesList.get(i);
				
				// getting the requested pages from the mmu
				Page<byte[]>[] pages = mmu.getPages(currProcessCycle.getPages().toArray(new Integer[0]));
				
				// make a list of the pages
				LinkedList<byte[]> listOfPages = new LinkedList();
				for(int j=0 ; j<pages.length ; j++)
				{
					byte[] pageData = pages[j].getContent();
					listOfPages.add(pageData);
					
					String strToLog = "P" + id + " R " + pages[j].getPageId() + " W [";
					
					for(int k=0 ; k<pageData.length ; k++)
					{
						if(k + 1 != pageData.length)
							strToLog += (int)pageData[k] + ", ";
						else
							strToLog += (int)pageData[k] + "]";
					}
					
					MMULogger.getInstance().write(strToLog, Level.INFO);
				}
				
				// return the pages to the cycle
				currProcessCycle.setData(listOfPages);
				
				System.out.println(id + " after mmu");
				
				// notify all that we finished with the critical part, so other thread can run now
				lockObject.notify();
				
				// now we wait for our turn, if other thread will call 'notify' we will run if its our turn 
				try {
					lockObject.wait(currProcessCycle.getSleepMs());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		
		}
	}
}
