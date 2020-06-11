package testers;

import algorithms.FIFOAlgoImpl;
import algorithms.LRUAlgoImpl;
import algorithms.MFUAlgoImpl;

public class AlgorithmTest {

	// helping method.
	// receives string array (represents the current buffer), and prints it.
	private static void printBuf(String[] buf) {
		
		System.out.print("[");
		for(int i=0; i<buf.length-1; i++) {
			System.out.print(buf[i] + ",");
		}
		System.out.println(buf[buf.length-1] + "]");
	}
 	
	// helping method.
	// received buffer, and replaces
	private static void replaceBuf(String[] buf, String source, String target) {
		for(int i=0; i<buf.length; i++) {
			if(buf[i].equals(source)) {
				buf[i] = target;
				return;
			}
				
		}
	}
	
	public static void main(String[] args) {
		String[] input = { "s1", "s2", "s3", "stringA", "s2", "stringB", "s1", "s2", "stringC", "s1", "stringD", "stringE" };
		
		// creating the algorithms
		FIFOAlgoImpl<String> fifoAlgo = new FIFOAlgoImpl<String>(3);
		LRUAlgoImpl<String> lruAlgo = new LRUAlgoImpl<String>(3);
		MFUAlgoImpl<String> mfuAlgo = new MFUAlgoImpl<String>(3);

		String[] buf;
		
		// FIFO ALGORITHM.
		System.out.println("--- FIFO ALGORITHM ---");
		
		// create a temporary buffer for displaying the elements.
		// filling the buffer with 3 first elements.
		buf = new String[] {"", "", ""};
		System.out.println("adding: " + input[0]);
		fifoAlgo.add(input[0]);
		buf[0] = input[0];
		System.out.println("adding: " + input[1]);
		fifoAlgo.add(input[1]);
		buf[1] = input[1];
		System.out.println("adding: " + input[2]);
		fifoAlgo.add(input[2]);
		buf[2] = input[2];
		 
		// iterating over the rest of the elements and adding them, and showing the result of what elements were removed.
		for(int i=3; i<12; i++) {
			System.out.println("adding: " + input[i]);
			String s = fifoAlgo.add(input[i]);
			if(s != null) {
				System.out.println("need to remove from RAM: " + s);
				replaceBuf(buf, s, input[i]);
			}
			printBuf(buf);
		}
		
		// LRU ALGORITHM
		System.out.println("--- LRU ALGORITHM ---");
		
		// create a temporary buffer for displaying the elements.
		// filling the buffer with 3 first elements.
		buf = new String[] {"", "", ""};
		System.out.println("adding: " + input[0]);
		lruAlgo.add(input[0]);
		buf[0] = input[0];
		System.out.println("adding: " + input[1]);
		lruAlgo.add(input[1]);
		buf[1] = input[1];
		System.out.println("adding: " + input[2]);
		lruAlgo.add(input[2]);
		buf[2] = input[2];
		 
		// iterating over the rest of the elements (some adding, and some getting), and showing the result of what elements were removed.
		for(int i=3; i<12; i++) {
			// items 4 and 7 will activate 'get' function.
			if( i == 4 || i == 7) {
				System.out.println("getting: " + input[i]);
				lruAlgo.get(input[i]);
			}
			else {
				System.out.println("adding: " + input[i]);
				String s = lruAlgo.add(input[i]);
				if(s != null) {
					System.out.println("need to remove from RAM: " + s);
					replaceBuf(buf, s, input[i]);
				}
			}
			printBuf(buf);
		}
		
		// MFU ALGORITHM
		System.out.println("--- MFU ALGORITHM ---");
		
		// create a temporary buffer for displaying the elements.
		// filling the buffer with 3 first elements.
		buf = new String[] {"", "", ""};
		System.out.println("adding: " + input[0]);
		mfuAlgo.add(input[0]);
		buf[0] = input[0];
		System.out.println("adding: " + input[1]);
		mfuAlgo.add(input[1]);
		buf[1] = input[1];
		System.out.println("adding: " + input[2]);
		mfuAlgo.add(input[2]);
		buf[2] = input[2];
		 
		// iterating over the rest of the elements and adding them, and showing the result of what elements were removed.
		for(int i=3; i<12; i++) {
			System.out.println("adding: " + input[i]);
			String s = mfuAlgo.add(input[i]);
			if(s != null) {
				System.out.println("need to remove from RAM: " + s);
				replaceBuf(buf, s, input[i]);
			}
			printBuf(buf);
		}
	}

}
