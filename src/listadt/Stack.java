package listadt;
/**
 * 
 */

/**
 * @author The Force
 *
 */
public class Stack <T> extends List<T>{

  public void push(T newEntry){
	
	  insert(newEntry);
  }
  
  public T pop(){
	  
	  T tempData = null;
	  
	  if(head == null){
		  tempData = null;
	  }
	  if(count == 2){
		  tempData = tail.data;
		  tail = null;
	  }
	  if(count == 1){
		  tempData = head.data;
		  head = null;
	  }
	  if(count > 2){
		  tempData = tail.data;
		  
		  ListItem<T> tempItem = tail.prev;
		  tempItem.next = null;
		  tail = tempItem;
		 
	  }
	  count-- ;
	  return tempData;
  }
  public T showTop(){
	  
	  if (tail == null) {
		  return null;
	  } else {
		  return tail.data;		  
	  }
  }
}
