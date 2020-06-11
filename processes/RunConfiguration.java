package processes;

import java.util.List;

public class RunConfiguration
{
	private List<ProcessCycles> processesCycles;
	
	// constructor
	public RunConfiguration(List<ProcessCycles> processesCycles)
	{
		this.processesCycles = processesCycles;
	}
	
	// get process cycles
	public List<ProcessCycles> getProcessesCycles()
	{
		return this.processesCycles;
	}
	
	// set process cycles
	public void setProcessesCycles(List<ProcessCycles> processesCycles)
	{
		this.processesCycles = processesCycles;
	}
	
	public String toString()
	{
		return this.toString();
	}
	
}
