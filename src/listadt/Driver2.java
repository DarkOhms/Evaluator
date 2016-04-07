package listadt;

public class Driver2 {
 
	public static void main(String []args){
	  List<String> obj = new List<String>();
	  
	  obj.insert("This");
	  obj.insert("is");
	  obj.insert("my");
	  obj.insert("super");
	  obj.insert("list");
	  
	  Iterator<String> it = obj.iterator();
	  
	  while(it.hasNext()){
		  System.out.println(it.nextData());
	  }
	  
	  obj.removeTail();
	  obj.removeTail();
	  obj.removeTail();
	  obj.removeTail();
	  obj.removeTail();
	  
	  it = obj.iterator();
	  
	  while(it.hasNext()){
		  System.out.println(it.nextData());
	  }
  }
}
