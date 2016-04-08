import java.util.StringTokenizer;

import listadt.*;

public class Parser {

    String toParse = new String();
    String inputSymbol = new String();
	List<String> tokens = new List<String>();
    Stack<String> s2 = new Stack<String>();
    Queue<String> s1 = new Queue<String>();
    
    public Parser(String input){
    	toParse = input;
    }
    
  void parseTable(String inputSymbol){
    	
    switch(inputSymbol){
   	 
 	   case "=": nested_switch1();
 	             break;
 	   case "+":
 	   case "-": nested_switch2();
 	             break;
 	   case "*":
 	   case "/": nested_switch3();
 	             break;
 	   case "(": nested_switch4();
 	             break;
 	   case ")": nested_switch5();
 	             break;
 	   default: s1();
 	            break;
    }
  }
	
    public void parse(){  	
    	
    	StringTokenizer st = new StringTokenizer(toParse,"=-+*/)(", true);
	     while (st.hasMoreTokens()) {
	         tokens.insert(st.nextToken());
	         
	     }
	     
	     Iterator<String> ob1 = tokens.iterator();
	     while(ob1.hasNext()){
	         
	    	 inputSymbol = ob1.nextData();
	    	 parseTable(inputSymbol);
	    	               
	     } 
	     //end of input
	     u2();
    }
  void nested_switch1(){
		
     if(s2.showTop() == null){
    	s2();
     }else{
       	System.out.println("Error");
     }
    	
    	
  }
    
  void nested_switch2(){
	
	if(s2.showTop() == null){
		System.out.println("Error");
    }
  	
	switch(s2.showTop()){
            
  	    case "=":
  	    case "(": s2();
  	               break;
  	    case "+":
  	    case "-":
  	    case "*": 
  	    case "/": u1();
  	              break;
  	    
  	               
    }
  }
  
  void nested_switch3(){
		
		if(s2.showTop() == null){
			//throw
			System.out.println("Error");
	    }else{
	  	
		  switch(s2.showTop()){
	            
	  	      case "=":
	  	      case "-":
	  	      case "+":
	  	      case "(": s2();
	  	                 break;
	  	      case "*": 
	  	      case "/": u1();
	  	                break;
	  	      	               
	     }
	  }
  }
  
  void nested_switch4(){
		
	if(s2.showTop() == null){
		System.out.println("Error");
    }else{
        s2();             
    }
  }
  
  void nested_switch5(){
		
		if(s2.showTop() == null||s2.showTop().equals("=")){
			System.out.println("Error");
	    }else{
	        uc();            
	    }
  }
	
	void s1(){
		s1.enqueue(inputSymbol);
	}
	
    void s2(){
		s2.push(inputSymbol);
	}
    
    void u1(){
		s1.enqueue(s2.pop());
		parseTable(inputSymbol);
	}
    
    void uc(){
    	while(!s2.isEmpty()){
    		if(s2.showTop().equals("(")){
    			s2.pop();
    			break;
    		}
			s1.enqueue(s2.pop());
		}
	}
    
    void u2(){
		while(!s2.isEmpty()){
			s1.enqueue(s2.pop());
		}
	}
    
    void uError(){
    	
    }
  
}
