package testers;

import java.util.List;

import memory.MemoryManagementUnit;
import memory.Page;
import algorithms.FIFOAlgoImpl;

public class MMUTester {

	public static void main(String[] args)
	{
		int ramSize = 2;
		FIFOAlgoImpl<Integer> fifoAlgo = new FIFOAlgoImpl<Integer>(ramSize);
		MemoryManagementUnit mmu = new MemoryManagementUnit(ramSize, fifoAlgo);
		
		Page<byte[]>[] l = mmu.getPages(new Integer[] {5,4,2,1,3});
		
	}
	
}
