
public class Test {
	String check = "charlie"; 
	public void unaryCheck(){
	    	if(check.contains("abs") || check.contains("sin") || check.contains("sqr")){
	    		System.out.println("True");
	    	}
	    }
  public static void main(String[] args){
	  
	  Test test = new Test();
	  test.unaryCheck();
	  if(check.contains("[a-zA-Z]+")){
		  System.out.println("True");
	  }
	  
  }
}
