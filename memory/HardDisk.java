package memory;

import java.io.FileNotFoundException;

import util.HardDiskInputStream;

public class HardDisk {
	
	final static String DEFAULT_FILE_NAME = "hdPages.txt";
	final static int _SIZE = 1000;
	
	private HardDiskInputStream hdStreamer;
	
	private static HardDisk _instance;
	
	private HardDisk()
	{
		try 
		{
			hdStreamer = new HardDiskInputStream(DEFAULT_FILE_NAME, RAM.PAGE_SIZE);
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static HardDisk getInstance()
	{
		if(_instance == null)
			_instance = new HardDisk();
		
		return _instance;
	}
	
	public Page<byte[]> pageFault(int pageId)
	{
		return hdStreamer.readSinglePage(pageId);
	}
	
	public Page<byte[]> pageReplacement(Page<byte[]> moveToHdPage, Integer moveToRamId)
	{
		return hdStreamer.readSinglePage(moveToRamId);
	}
}
