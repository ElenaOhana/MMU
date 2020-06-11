package memory;

public class Page <T>{

	private int pageId;
	private T content;
	
	public Page(int id, T content) {
		this.pageId = id;
		this.content = content;
	}
	
	public int getPageId() {
		return this.pageId;
	}
	
	public void setPageId(int pageId) {
		this.pageId = pageId;
	}
	
	public T getContent() {
		return this.content;
	}
	
	public void setContent(T content) {
		this.content = content;
	}
	
	@Override
	public int hashCode() {
		return content.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		try{
			Page<T> page = (Page<T>) obj;
			if(this.pageId == page.pageId && this.content.equals(page.content))
				return true;
			return false;
		} catch(Exception e) {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "Page " + this.pageId + ": " + this.content.toString();
	}
}
