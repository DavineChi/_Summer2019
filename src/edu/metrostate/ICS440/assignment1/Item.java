package edu.metrostate.ICS440.assignment1;

/****************************************************************************************************************
 * A linked-list implementation of a generic Item (Node) class.
 * <p>
 *  
 * <b>Note:</b>
 *   Parts of this class implementation have been borrowed from Data Structures and Other Objects Using Java,
 *   4th edition, written by Michael Main.
 * 
 * @author Shannon L. Fisher
 * <p>
 * Begin Date:	2019.05.23
 * <p>
 * Due Date:	2019.06.06
 */
public class Item<T> {

	private T data;
	private Item<T> link;
	
	/************************************************************************************************************
	 * Constructor used to create a new Item object with a specified generic data object and a link Item.
	 * <p>
	 * 
	 * @param data
	 *   a reference to this Item's associated data object
	 * 
	 * @param link
	 *   a reference to this Item's link
	 * 
	 * @postcondition
	 *   A new Queue object has been created with a specified data object and link.
	 */
	public Item(T data, Item<T> link) {
		
		this.data = data;
		this.link = link;
	}
	
	/************************************************************************************************************
	 * An accessor method to retrieve the data object associated with this Item.
	 * <p>
	 * 
	 * @return
	 *   This Item's associated data object.
	 */
	public T getData() {
		
		return data;
	}
	
	/************************************************************************************************************
	 * A modifier method to assign the specified data object to this Item.
	 * <p>
	 * 
	 * @param data
	 *        the data object to assign to this Item
	 * 
	 * @postcondition
	 *   A new data object has been assigned to this Item.
	 */
	public void setData(T data) {
		
		this.data = data;
	}
	
	/************************************************************************************************************
	 * An accessor method to retrieve this Item's link.
	 * <p>
	 * 
	 * @return
	 *   This Item's link.
	 */
	public Item<T> getLink() {
		
		return link;
	}
	
	/************************************************************************************************************
	 * A modifier method to assign the specified link to this Item.
	 * <p>
	 * 
	 * @param link
	 *        the link to assign to this Item
	 * 
	 * @postcondition
	 *   A new link has been assigned to this Item.
	 */
	public void setLink(Item<T> link) {
		
		this.link = link;
	}
	
	/************************************************************************************************************
	 * A modifier method to create and add a new Item to this Item's link using the specified data object.
	 * <p>
	 * 
	 * @param item
	 *        the data object used for the new Item
	 * 
	 * @postcondition
	 *   A new Item is created from the specified object and is added as this Item's link.
	 */
    public void addAfter(T item) {
    	
        link = new Item<T>(item, link);
    }
	
	@Override
	public String toString() {
		
		return "Item [data=" + data + ", link=" + link + "]";
	}
}
