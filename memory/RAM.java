package memory;

import java.util.HashMap;
import java.util.Map;

public class RAM {
	
	// a map from the page Id to the page itself.
	private Map<Integer, Page<byte[]>> pages;
	private int totalCapacity;
	public static final int PAGE_SIZE = 3;
	
	public RAM(int initialCapacity) {
		pages = new HashMap<>(initialCapacity);
		this.totalCapacity = initialCapacity;
	}
	
	public Page<byte[]> getPage(int pageId) {
		return pages.get(pageId);
	}
	
	public void addPage(Page<byte[]> addPage) {
		pages.put(addPage.getPageId(), addPage);
	}
	
	public void removePage(Page<byte[]> removePage) {
		pages.remove(removePage.getPageId());
	}
	
	public Boolean isFull()
	{
		return totalCapacity - pages.size() == 0 ? true : false;
	}

	public Page<byte[]>[] getPages(Integer[] pageIds) {
		Page<byte[]>[] pageArr = new Page[pageIds.length];
		for(int i=0; i<pageIds.length; i++) {
			pageArr[i] = pages.get(pageIds[i]);
		}
		return pageArr;
	}

	public void addPages(Page<byte[]>[] addPages) {
		for(int i=0; i<addPages.length; i++) {
			pages.put(addPages[i].getPageId(), addPages[i]);
		}
	}

	public void removePages(Page<byte[]>[] removePages) {
		for(int i=0; i<removePages.length; i++) {
			pages.remove(removePages[i].getPageId());
		}
	}
}
