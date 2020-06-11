package util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class MMULogger
{
	final static String DEFAULT_FILE_NAME = "log.txt";
	private static FileHandler handler;
	private static MMULogger instance;
	
	public static MMULogger getInstance()
	{
		if(instance == null)
			instance = new MMULogger();
		
		return instance;
	}
	
	private MMULogger()
	{
		try
		{
			handler = new FileHandler(DEFAULT_FILE_NAME, false);
			handler.setFormatter(new OnlyMessageFormatter());
			
		}
		catch(IOException e)
		{
			System.err.println("Cannot create handler" + e.getMessage());
		}
	}
	
	public void write(String command, Level level)
	{
		handler.publish(new LogRecord(level, command + "\r\n"));
	}
	
	public class OnlyMessageFormatter extends Formatter
	{
		public OnlyMessageFormatter()
		{
			super();
		}
		
		public String format(final LogRecord record)
		{
			return record.getMessage();
		}
	}
}


