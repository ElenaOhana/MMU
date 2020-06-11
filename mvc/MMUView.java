package mvc;

import java.awt.Point;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class MMUView
{
	// lines of commands
	private List<String> commands;

	int ramCapacity = 0;
	int processesNumber = 0;
	
	Shell shell;
	// main table
	Table table;
	
	Label pageFaultAmountLabel;
	Text pageFaultAmountTextField;
	
	Label pageReplacementAmountLabel;
	Text pageReplacmentAmountTextField;
	
	Text sleepBetweenCommandTextField;
	
	Button playButton;
	Button playAllButton;
	
	org.eclipse.swt.widgets.List processesList;
	org.eclipse.swt.widgets.List commandsList;
	
	// current commands index
	int commandIndex = -1;

	int sleepBetweenCommandsMili = 1000;
	
	int pageFaultCounter = 0;
	int pageReplacmentCounter = 0;
	
	Boolean rowsInitialized = false;
	Boolean playingAll = false;
	
	Color backgroundColor;
	Font listFont;
	
	Image playImage;
	Image fastForwardImage;
	
	public MMUView(List<String> commands)
	{	
		// reading the first 2 lines of the command file
		// they set the ram capacity and processes number
		// and removing them from the kist
		String ramCapacityCommand = commands.remove(0);
		String ramCapacityString = ramCapacityCommand.split(" ")[1];
		ramCapacity = Integer.parseInt(ramCapacityString);
		
		String processesNumberCommand = commands.remove(0);
		String processesNumberString = processesNumberCommand.split(" ")[1];
		processesNumber = Integer.parseInt(processesNumberString);
		
		// setting the commands
		this.commands = commands;
	}
	
	public void open()
	{
		// create the display
		Display display = new Display();
		
		// create the shell
		this.shell = new Shell(display);		
		shell.setText("MMU Simulator");
		shell.setSize(1000,700);
		shell.setLocation(390,0);
		
		//shell.setBackgroundMode(SWT.INHERIT_FORCE);
		//Device d;
		backgroundColor = new Color((Device)display,217,210,238);
		shell.setBackground(backgroundColor);
		
		listFont = new Font((Device)display, "Arial", 10, SWT.BOLD);
		
		playImage = new Image(display, "res\\buttons\\play.png");
		fastForwardImage = new Image(display, "res\\buttons\\play_all.png");
		
		// initialize all the controls
		initTable();
		initSatuts();
		initButtons();
		initList();

		
		// display the shell and wait
		shell.open();
		while(!shell.isDisposed())
		{
			if(!display.readAndDispatch())
			{
				display.sleep();
			}
		}
		backgroundColor.dispose();
		listFont.dispose();
		playImage.dispose();
		fastForwardImage.dispose();
		display.dispose();
	}
	
	public void initTable()
	{
		// create the table
		table = new Table(shell, SWT.BORDER | SWT.MULTI);
		table.setSize((int)(shell.getSize().x * 0.7), 200);
		table.setHeaderVisible(true);
		
		// create the columns by ram capacity
		for(int i=0 ; i < ramCapacity  ; i++)
		{
			TableColumn tc1 = new TableColumn(table, SWT.NONE);
			tc1.setText("");
			tc1.pack();
		}
	}
	
	public void initSatuts()
	{
		// creating the page fault and page replacement labels and text fields
		
		pageFaultAmountLabel = new Label(shell, SWT.CENTER);
	    pageFaultAmountLabel.setText("Page fault amount");
	    pageFaultAmountLabel.setLocation((int)(shell.getSize().x*0.75) + 10, 50);
	    pageFaultAmountLabel.setSize(100,30);
	    pageFaultAmountLabel.setBackground(backgroundColor);
	    
	    pageFaultAmountTextField = new Text(shell, SWT.BORDER);
	    pageFaultAmountTextField.setText("0");
	    pageFaultAmountTextField.setLocation((int)(shell.getSize().x*0.75) + 150, 47);
	    pageFaultAmountTextField.setSize(30,20);
	    
	    pageReplacementAmountLabel = new Label(shell, SWT.CENTER);
	    pageReplacementAmountLabel.setText("Page replacment amount");
	    pageReplacementAmountLabel.setLocation((int)(shell.getSize().x*0.75) + 10, 90);
	    pageReplacementAmountLabel.setSize(140,30);
	    pageReplacementAmountLabel.setBackground(backgroundColor);
	    
	    pageReplacmentAmountTextField = new Text(shell, SWT.BORDER);
	    pageReplacmentAmountTextField.setText("0");
	    pageReplacmentAmountTextField.setLocation((int)(shell.getSize().x*0.75) + 150, 87);
	    pageReplacmentAmountTextField.setSize(30,20);
	    
	    sleepBetweenCommandTextField = new Text(shell, SWT.BORDER);
	    sleepBetweenCommandTextField.setText("" + sleepBetweenCommandsMili);
	    sleepBetweenCommandTextField.setLocation(150, 250);
	    sleepBetweenCommandTextField.setSize(60,20);
	}
	
	public void initButtons()
	{
		// initialize the buttons and add listeners to their clicks
		
		playButton = new Button(shell, SWT.PUSH);
		//playButton.setText("Play");
		playButton.setSize(40, 20);
		playButton.setLocation(30, 250);
	    playButton.setImage(playImage);
		
		playButton.addSelectionListener(new SelectionListener() {
	    	
	      public void widgetSelected(SelectionEvent event) {
	    	  playClicked();
	      }

	      public void widgetDefaultSelected(SelectionEvent event) {
	    	  playClicked();
	      }
	    });
	
		playAllButton = new Button(shell, SWT.PUSH);
		//playAllButton.setText("Play All");
		playAllButton.setSize(60, 20);
		playAllButton.setLocation(80, 250);
		playAllButton.setImage(fastForwardImage);
		
		playAllButton.addSelectionListener(new SelectionListener() {
	    	
		      public void widgetSelected(SelectionEvent event) {
		    	  playAllClicked();
		      }

		      public void widgetDefaultSelected(SelectionEvent event) {
		    	  playAllClicked();
		      }
		    });
	}
	
	public void initList()
	{
		// initialize the processes list
		processesList = new org.eclipse.swt.widgets.List(shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		processesList.setLocation(250, 250);
	    processesList.setSize(100,200);
	    
	    for(int i=0  ; i<processesNumber ; i++)
	    	processesList.add("process " + i);
	    
	    commandsList = new org.eclipse.swt.widgets.List(shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
	    commandsList.setLocation(400, 250);
	    commandsList.setSize(200,400);
	    commandsList.setEnabled(false);
	    
	    for(int i=0  ; i< commands.size() ; i++)
	    	commandsList.add(commands.get(i));
	    
	    commandsList.select(0);
	    commandsList.setFont(listFont);
	    commandsList.setForeground(backgroundColor);
	}
	
	public void resetView()
	{
		// reseting the view
		
		// setting the text fields to 0
		pageFaultAmountTextField.setText("0");
		pageReplacmentAmountTextField.setText("0");
		
		// setting the counters to 0
		pageFaultCounter = 0;
		pageReplacmentCounter = 0;
		
		if(table == null)
			return;
		
		TableColumn[] tableColumns = table.getColumns();
		
		// clearing the columns header
		for(int i=0 ; i<tableColumns.length ; i++)
			tableColumns[i].setText("");
		
		// clearing all the cells
		TableItem[] tableItems = table.getItems();
		for(int i=0 ; i < tableItems.length ; i++)
			for(int j=0 ; j<tableColumns.length ; j++)
				tableItems[i].setText(j,"");
	}
	
	public void playClicked()
	{
		// increasing commands index by 1 moving to the next command
		commandIndex++;
		
		// if we passed all the commands we reset the view
		if(commandIndex >= commands.size())
		{
			commandIndex = -1;
			resetView();
			return;
		}
		
		commandsList.setSelection(commandIndex);
		
		// current commands
		String command = commands.get(commandIndex);
		
		// splitting the commands by spaces
		String[] splitedCommands = command.split(" ");
		
		if(splitedCommands[0].equals("PF")) // page fault PF 2(id=2)
		{
			// increasing the page fault counter and changing it in the view
			pageFaultCounter++;
			pageFaultAmountTextField.setText(pageFaultCounter + "");
			pageFaultAmountTextField.update();
			
			// getting the page fault id
			String pageIdString = splitedCommands[1];
			
			// looking for an empty column to put the page in
			TableColumn[] tableColumns = table.getColumns();
			int colIndex = 0;
			for( ; colIndex < tableColumns.length ; colIndex++)
			{
				if(tableColumns[colIndex].getText().equals(""))
					break;
			}
			
			// setting the column header for the by the page id
			tableColumns[colIndex].setText(pageIdString);
		}
		else if(splitedCommands[0].equals("PR")) // page replacment PR MTH 3 MTR 5
		{
			
			// increasing the page replacement counter and changing it in the view
			pageReplacmentCounter++;
			pageReplacmentAmountTextField.setText(pageReplacmentCounter + "");
			pageReplacmentAmountTextField.update();
			
			// getting the page id to the HD and the page id to the ram
			String pageIdToHDString = splitedCommands[2]; // 3
			String pageIdToRamString = splitedCommands[4]; // 5
			
			// switching the column of the page to the HD with the page id to the ram (3 turns to 5)
			TableColumn[] tableColumns = table.getColumns();
			for(int i=0 ; i < tableColumns.length ; i++)
			{
				// if we found to column with the page id to the HD
				if(tableColumns[i].getText().equals(pageIdToHDString))
				{
					// set the column header to page id to the ram
					tableColumns[i].setText(pageIdToRamString);
					break;
				}
			}
			
		}
		else if(splitedCommands[0].length() >= 2 &&
				splitedCommands[0].charAt(0) == 'P' &&
				Character.isDigit(splitedCommands[0].charAt(1))) // get pages - P1 R 4 W [-47, -96, 42, -62, -106]
		{
			
			if(playingAll)
			{
				int[] selectedProcesses = processesList.getSelectionIndices();
				String processIdString = splitedCommands[0].substring(1,splitedCommands[0].length());
				int processId = Integer.parseInt(processIdString);
				
				Boolean isProcessSelected = false;
				
				for(int i=0 ; i<selectedProcesses.length ; i++)
				{
					if(selectedProcesses[i] == processId)
					{
						isProcessSelected = true;
						break;
					}
				}
				
				if(!isProcessSelected)
					return;
			}
			
			// get the page id
			String pageIdString = splitedCommands[2]; //4
			int pageId = Integer.parseInt(pageIdString);
			
			// find the start and end of the data
			int bufferStartIndex = command.indexOf("[") + 1;
			int bufferEndIndex = command.indexOf("]");
			
			// cutting only the part of the array
			String bufferString = command.substring(bufferStartIndex, bufferEndIndex);
			
			// splitting the array by ,
			String[] buffer = bufferString.split(",");
			
			if(rowsInitialized == false)
			{
				initRows(buffer.length);
				rowsInitialized = true;
			}
			
			
			// looking for the column index with the page id
			TableColumn[] tableColumns = table.getColumns();
			int colIndex = 0;
			for( ; colIndex < tableColumns.length ; colIndex++)
			{
				if(tableColumns[colIndex].getText().equals(pageIdString))
					break;
			}
			
			TableItem[] tableItems = table.getItems();
			for(int i=0 ; i< tableItems.length ; i++)
			{
				// setting each cell in the column with the byte from the array
				tableItems[i].setText(colIndex, buffer[i].trim()); // trim removes leading and trailing spaces
			}
		}
		// arranging the table columns
		packTableColumns();
	}
	
	public void playAllClicked()
	{
		// run through all the commands at once
		resetView();
		
		sleepBetweenCommandTextField.setEnabled(false);
		processesList.setEnabled(false);
		commandIndex = -1;
		playingAll = true;
		for(int i=0 ; i<commands.size() ; i++)
		{
			playClicked();
			commandsList.update();
			try {
				Thread.sleep(sleepBetweenCommandsMili);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		playingAll = false;
		sleepBetweenCommandTextField.setEnabled(true);
		processesList.setEnabled(true);
	}	
	
	public void initRows(int rowsNumber)
	{
		// initialize number of rows with empty arrays
		for(int i=0; i<rowsNumber ; i++)
		{
			TableItem emptyItem = new TableItem(table, SWT.NONE);
		    emptyItem.setText(new String[ramCapacity]);
		}
	}

	public void packTableColumns()
	{
		// arranging the table columns
		TableColumn[] tableColumns = table.getColumns();
		for(int i=0 ; i<tableColumns.length ; i++)
			tableColumns[i].pack();
	}
}
