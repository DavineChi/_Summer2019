package edu.ICS440.assignment1;

public class Item<T> {

	private T data;
	private Item<T> link;
	
	public Item(T data, Item<T> link) {
		
		this.data = data;
		this.link = link;
	}
	
	public T getData() { return data; }
	public Item<T> getLink() { return link; }
	
	public void setData(T data) { this.data = data; }
	public void setLink(Item<T> link) { this.link = link; }

    public void addAfter(T item) {
    	
        link = new Item<T>(item, link);
    }
	
	@Override
	public String toString() {
		
		return "Item [data=" + data + ", link=" + link + "]";
	}
}
