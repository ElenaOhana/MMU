package algorithms;

import java.util.LinkedList;

public class LRUAlgoImpl<T> implements IAlgo<T> {

	// elements currently in ram.
	private LinkedList<T> elements;
	private int ramSize;
	
	// constructor:
	// received max elements (according to RAM).
	public LRUAlgoImpl(int ramSize) {
		this.ramSize = ramSize;
		elements = new LinkedList<T>();
	}
	
	// get(T t):
	// access to element currently in ram.
	// according to LRU, accessing an element should move it to the front of the list (means will pop out last).
	// return value: if the element exists, returns the element, or else returns null.
	@Override
	public T get(T t) {
		if(elements.contains(t)) {
			elements.remove(t);
			elements.addFirst(t);
			return t;
		}
		else
			return null;
	}

	// add(T t):
	// adding an element to RAM.
	// according to LRU the function:
	// 1. if element already exists, removing it.
	// 2. if ram full removing the first inserted item in the list. (removing from the back of the list)
	// 3. inserting the item in front of the list.
	//return value: if an element was removed, then it returned, or else null.
	@Override
	public T add(T t) {
		T removedItem = null;
		if(elements.contains(t))
			elements.remove(t);
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
