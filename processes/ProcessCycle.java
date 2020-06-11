package processes;

import java.util.List;

public class ProcessCycle
{
	private List<Integer> pages;
	private int sleepMs;
	private List<byte[]> data;
	
	// constructor
	public ProcessCycle(List<Integer> pages, int sleepMs, List<byte[]> data)
	{
		this.pages = pages;
		this.sleepMs = sleepMs;
		this.data = data;
	}
	
	// get pages
	public List<Integer> getPages()
	{
		return pages;
	}
		
	// get sleep ms
	public int getSleepMs()
	{
		return sleepMs;
	}
		
	// get data
	public List<byte[]> getData()
	{
		return data;
	}
	
	// set pages
	public void setPages(List<Integer> pages)
	{
		this.pages = pages;
	}
	
	// set sleep ms
	public void setSleepMs(int sleepMs)
	{
		this.sleepMs = sleepMs;
	}
	
	// set data
	public void setData(List<byte[]> data)
	{
		this.data = data;
	}
	
	public String toString()
	{
		return this.toString();
	}
}
