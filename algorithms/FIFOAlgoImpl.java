package algorithms;

import java.util.LinkedList;

public class FIFOAlgoImpl<T> implements IAlgo<T> {

	// elements currently in ram.
	private LinkedList<T> elements;
	private int ramSize;
	
	// constructor:
	// received max elements (according to RAM).
	public FIFOAlgoImpl(int ramSize) {
		this.ramSize = ramSize;
		elements = new LinkedList<T>();
	}
	
	// get(T t):
	// access to element currently in ram.
	// according to FIFO accessing an element have no effect on element popping ordering.
	// return value: if the element exists, returns the element, or else returns null.
	@Override
	public T get(T t) {
		if(elements.contains(t))
			return t;
		else
			return null;
	}

	// add(T t):
	// adding an element to RAM.
	// according to FIFO the function:
	// 1. if element already exists, finish function.
	// 2. if ram full removing the first inserted item in the list. (removing from the back of the list)
	// 3. inserting the item in front of the list.
	// return value: if an element was removed, then it returned, or else null.
	@Override
	public T add(T t) {
		T removedItem = null;
		if(elements.contains(t))
			return null;
		if(elements.size() == ramSize)
			removedItem = elements.removeLast(); 
		elements.addFirst(t);
		return removedItem;
	}

	// remove(T t):
	// removing item from the RAM.
	// removing from the list, algorithm have no effect.
	@Override
	public void remove(T t) {
		elements.remove(t);
	}

}
