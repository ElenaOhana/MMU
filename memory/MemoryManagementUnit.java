package memory;

import java.awt.List;
import java.util.LinkedList;
import java.util.logging.Level;

import util.MMULogger;

import algorithms.IAlgo;

public class MemoryManagementUnit {
	private IAlgo<Integer> algo;
	private RAM ram;
	
	public MemoryManagementUnit(int ramCapacity, IAlgo<Integer> algo)
	{
		// initialize class members
		this.ram = new RAM(ramCapacity);
		this.algo = algo; 
	}
	
	public Page<byte[]>[] getPages(Integer[] pageIds)
	{

		// creating the array of pages to be returned
		Page<byte[]>[] pagesArr = new Page[pageIds.length];
		
		// looping through all the requested ids
		for(int i=0 ; i < pageIds.length ; i++)
		{
			int currPageId = pageIds[i];
			
			
			if(ram.getPage(currPageId) == null) // if the page isn't loaded into the ram, we need to bring it from the hd
			{
					Page<byte[]> pageToRam;
					if(ram.isFull()) // page replacement
					{
						// we are adding the requested page to the ram, the return id should move to the hd
						Integer pageIdToHd = algo.add(currPageId);
						
						// writing to log PR
						MMULogger.getInstance().write("PR MTH " + pageIdToHd + " MTR " + currPageId , Level.INFO);
						
						// getting the page content that should go to the hd
						Page<byte[]> pageToHd = ram.getPage(pageIdToHd);
						
						// removing the content of the removed page from the ram 
						ram.removePage(pageToHd);
						
						// asking the hd to save tghe removed page in the hd and requesting for the new page we want
						pageToRam = HardDisk.getInstance().pageReplacement(pageToHd, currPageId);
						
						// adding the page to the ram
						ram.addPage(pageToRam);
						
						System.out.println("Page Replacement: \tloading from hd page " + currPageId + " \t\tremoving from ram page " + pageIdToHd);
					}
					else // page fault
					{
						// asking from the hd the page we missing
						pageToRam = HardDisk.getInstance().pageFault(currPageId);
						
						// adding via the algo the new page
						algo.add(currPageId);
						
						// writing to log PF
						MMULogger.getInstance().write("PF " + currPageId , Level.INFO);
						
						// adding the new page to the ram
						ram.addPage(pageToRam);
						System.out.println("Page Fault: \t\tloading from hd page " + currPageId);
					}
					
					// adding the requested page to the list of pages
					pagesArr[i] = pageToRam;
			}
			else // the page is loaded in the ram, ask the algo to get the page
			{
				// adding the requested page to the list of pages
				pagesArr[i] = ram.getPage(algo.get(currPageId));
			}
		}

		return pagesArr;
	
	}

}
