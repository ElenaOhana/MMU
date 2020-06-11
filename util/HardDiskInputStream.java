package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException; 
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;


import memory.Page;
import memory.RAM;

public class HardDiskInputStream extends FileInputStream
{
	private FileChannel hdFileChannel;
	private int ramPageSize;
	
	public HardDiskInputStream(String fileName, int ramPageSize) throws FileNotFoundException
	{
		super(fileName);
		
		hdFileChannel = getChannel();
		this.ramPageSize = ramPageSize; 
	}
	
	public Map<Integer, Page<byte[]>> readAllPages()
	{
		// initialize the map
		HashMap<Integer, Page<byte[]>> pagesMap = new HashMap<Integer, Page<byte[]>>();
		
		// setting totalPages to the size of the hd divide by page size
		int totalPages = 0;
		try 
		{

			// set cursor position to 0
			hdFileChannel.position(0);
			
			totalPages = this.available() / ramPageSize;
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		} 
		
		// reading each page and add it to the map
		for(int i=0 ; i<totalPages ; i++)
			pagesMap.put(i, readSinglePage(i));
		
		return pagesMap;
	}
	
	public Page<byte[]> readSinglePage(Integer pageId)
	{
		// initialize the buffer
		byte[] pageData = new byte[ramPageSize];
		
		// read the the data to pageData by the requested position
		read(pageData, (pageId-1) * ramPageSize); 
		
		return new Page<byte[]>(pageId, pageData);
	}
	
	private void read(byte[] buf, int startIndex)
	{
		try 
		{
			// set cursor position to 0
			hdFileChannel.position(0);
			
			// skipping the cursor to the start index of the data
			skip(startIndex);
			
			// reading the data to buf
			read(buf);
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
}
