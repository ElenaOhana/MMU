package mvc;

public class MMUController
{
	// path to the command file
	final static String MODEL_FILE_PATH = "log.txt";
	
	public static void main(String[] args)
	{
		// creating the model
		MMUModel model = new MMUModel(MODEL_FILE_PATH);
		
		// creating the view by the model data
		MMUView view = new MMUView(model.getModel());
		
		// display the view
		view.open();
	}
}
