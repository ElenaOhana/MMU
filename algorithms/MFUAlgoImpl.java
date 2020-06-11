package algorithms;

import java.util.HashMap;
import java.util.LinkedList;

public class MFUAlgoImpl<T> implements IAlgo<T> {

	// elements currently in ram.
	private LinkedList<T> elements;
	// mapping from the element, to number of time it has been used.
	private HashMap<T, Integer> countMap;
	private int ramSize;
	
	// constructor:
	// received max elements (according to RAM).
	public MFUAlgoImpl(int ramSize) {
		this.ramSize = ramSize;
		elements = new LinkedList<T>();
		countMap = new HashMap<T, Integer>();
	}
	
	// get(T t):
	// access to element currently in ram.
	// according to MFU, accessing an element should increment number of using of that item.
	// return value: if the element exists, returns the element, or else returns null.
	@Override
	public T get(T t) {
		if(elements.contains(t)) {
			countMap.put(t, countMap.get(t) + 1);
			return t;
		}
		else
			return null;
	}

	// add(T t):
	// adding an element to RAM.
	// according to MFU the function:
	// 1. if element already exists, add to the using counter of the item.
	// 2. if RAM full searching for an item in RAM which has most using, and removing it. (remembering the index where it has been removed)
	// 3. inserting the item at the specified index, and if it has no using yet, initializing to 1 use.
	// return value: if an element was removed, then it returned, or else null.
	@Override
	public T add(T t) {
		T removedItem = null;
		int removedIndex = -1; // the index where we going to insert the new element.
		if(elements.contains(t)) {
			countMap.put(t, countMap.get(t) + 1);
			return null;
		}
		if(elements.size() == ramSize) 
		{
		
			T maxElem = elements.getFirst();
			int max = countMap.get(maxElem);
			for(T elem: elements) {
				if(countMap.get(elem) > max) {
					maxElem = elem;
					max = countMap.get(elem);
				}
			}
			removedIndex = elements.lastIndexOf(maxElem);
			elements.remove(maxElem);
			removedItem = maxElem;
		}
		if(!countMap.containsKey(t))
			countMap.put(t, 1);
		else
			countMap.put(t, countMap.get(t) + 1);
		if(removedIndex == (-1))
			elements.addLast(t);
		else
			elements.add(removedIndex, t);
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
