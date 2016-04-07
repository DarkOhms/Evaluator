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
	  
	  T tempData = showTop();
	  removeTail();
	  return tempData;
  }
  public T showTop(){
	  
	  if(tail != null){
	    return tail.data;
	  }else{
		  return null;
	  }
  }
}
