/*
 * Evaluator class
 * 
 * Luke Martin 
 * Dixon CISP 430
 * 
 * Class Asociations
 * 
 * Evaluator // performs evaluation of a string input into a numerical answer of the highest common precision
 * Parser // takes an infix expression and makes a postfix expression for evaluation
 * Tokenizer //makes tokens from the input string
 * HashTable  // stores and retrieves tokens for processing by the evaluator
 * Stack // used by Parser and Evaluator
 * Queue // also used by Parser and Evaluator
 * 
 * Evaluator ---- 1:1 uses ----- Parser
 * Evaluator ---- 1:2 uses ----- Stack  //?
 * Parser ------- 1:1 uses ----- Stack  
 * Parser ------- 1:1 uses ----- Queue  
 * Parser ------- 1:1 uses ----- StringTokenizer
 * 
 * operator tokens::= {+,-,/,*,Sin,Cos,Sqrt,Abs}
 * operandTokens::={A,B,C,D}
 * ConstantTokens::={"1..9", "whole.decimal"}
 * 
 * postfix is queue of tokens
 * 
 */

import listadt.*;
import listadt.Queue;
import listadt.Stack;
import HashTable.*;

public class Evaluator {
  
  Stack<String> eval = new Stack<String>();
  Queue<String> postfix = new Queue<String>();
  HashTable symbols = new HashTable();
  
  {
	  symbols.insert("alpha", 25);
	  symbols.insert("beta", 10);
	  symbols.insert("charlie", 6.0);
	  symbols.insert("delta", 11);
  }
  
  
  
  String unaryEval(String data, String operator){
	  double operand = 0.0;
	  String result = "";
	  
	  if(data.matches("[a-zA-Z]+")){
		  operand = symbols.getData(data);
	  }else{
		  operand = Double.parseDouble(data);
	  }
	  
	  switch(operator){
	    case "sin":  result = Double.toString(Math.sin(operand));
	                break;
	    case "sqr":  result = Double.toString(Math.sqrt(operand));
	                break;
	    case "abs":  result = Double.toString(Math.abs(operand));
	                break;
	  }
		  
	  return result;
  }
  
  String binaryEval(String data1, String data2, String operator){
	  
	  double left = 0.0;
	  double right = 0.0;
	  
	  String result = "";
	  
	  if(data1.matches("[a-zA-Z]+")){
		  right = symbols.getData(data1);
	  }else{
		  right = Double.parseDouble(data1);
	  }
	  
	  if(data2.matches("[a-zA-Z]+")){
		  left = symbols.getData(data2);
	  }else{
		  left = Double.parseDouble(data2);
	  }
	  
	  
	  switch(operator){
	    case "+":  result = Double.toString(left+right);
	                break;
	    case "-":  result = Double.toString(left-right);
	                break;
	    case "*":  result = Double.toString(left*right);
	                break;
	    case "/":  result = Double.toString(left/right);
                    break;
	  }
	  return result;
  }
  
  public double evaluate(String input) throws SyntaxError{
	  
	  //parse the infix
	  Parser parser = new Parser(input);
	  parser.parse();
	  
	  //prime the eval stack with the symbol for assignment
	  //check for operators and put everything else on eval stack
	  //loop through parser.s1 and evaluate
	  
	  eval.push(parser.s1.dequeue());
	  double data = 0.0;
	  
	  while(!parser.s1.isEmpty()){
		  
		  if(parser.s1.firstInLine().matches("=")){
			  
			  data = Double.parseDouble(eval.pop());
			  String key = eval.pop();
			  symbols.insert(key, data);
			  return data;
		  }
		  if(parser.s1.firstInLine().matches("\\+|\\*|/|sin|sqr|abs|-")){
			  //use the operator to evaluate the eval stack
			  switch(parser.s1.firstInLine()){
			    
			    case "+":
			    case "-":
			    case "/":
			    case "*": eval.push(binaryEval(eval.pop(), eval.pop(), parser.s1.dequeue()));
			              break;
			    case "sin":
			    case "sqr":
			    case "abs": eval.push(unaryEval(eval.pop(), parser.s1.dequeue()));
			                break;
			  }
			  
		  }else{
			  eval.push(parser.s1.dequeue());
		  }
		  
	  }
	  return data;	  
  }
  
  
  
  public static void main(String[] args){
	  
	String userInput = new String("X=12*(alpha+3)");
	Evaluator eze = new Evaluator();
	
	System.out.println("symbols.insert(\"alpha\", 25)");
	System.out.println("symbols.insert(\"beta\", 10)");
	System.out.println("symbols.insert(\"charlie\", 6)");
	System.out.println("symbols.insert(\"delta\", 11)");
	
	System.out.println("userInput = X=12*(alpha+3) ");
	
	System.out.println("alpha   EXPECTED: 25");
	System.out.println("beta    EXPECTED: 10");
	System.out.println("charlie EXPECTED: 6");
	System.out.println("delta   EXPECTED: 11");
	System.out.println("X       EXPECTED: 336 ");
	
	try{
		System.out.println("X is:" + eze.evaluate(userInput));
	}catch(SyntaxError e){
		 System.out.println(e.getMessage());
	}
	
	System.out.println("-------------------Actual------------------");
	display(eze);
	
	System.out.println("userInput = \"alpha = alpha + beta / charlie * delta\"");
	
	userInput = "alpha = alpha + beta / charlie * delta";
	
	System.out.println("alpha   EXPECTED: 43.33333333333");
	System.out.println("beta    EXPECTED: 10");
	System.out.println("charlie EXPECTED: 6");
	System.out.println("delta   EXPECTED: 11");
	System.out.println("X       EXPECTED: 336 ");
	
	try{
	    System.out.println("alpha is:" + eze.evaluate(userInput));
	}catch(SyntaxError e){
		System.out.println(e.getMessage());
	}
	
	System.out.println("-------------------Actual------------------");
	display(eze);
	
    System.out.println("userInput = \"beta = 5/2.0 + alpha\"");
	
	userInput = "beta = 5/2.0 + alpha";
	
	System.out.println("alpha   EXPECTED: 43.33333333333");
	System.out.println("beta    EXPECTED: 45.83333333333");
	System.out.println("charlie EXPECTED: 6");
	System.out.println("delta   EXPECTED: 11");
	System.out.println("X       EXPECTED: 336 ");
	
	try{
		System.out.println("beta is:" + eze.evaluate(userInput));
	}catch(SyntaxError e){
		System.out.println(e.getMessage());
	}
	
	System.out.println("-------------------Actual------------------");
	display(eze);
	
	System.out.println("userInput = \"charlie = sin(alpha) + (charlie-delta)\"");
		
	userInput = "charlie = sin(alpha) + (charlie-delta)";
		
	System.out.println("alpha   EXPECTED: 43.33333333333");
	System.out.println("beta    EXPECTED: 45.83333333333");
	System.out.println("charlie EXPECTED: -5.60436119243");
	System.out.println("delta   EXPECTED: 11");
	System.out.println("X       EXPECTED: 336 ");
		
		try{
		    System.out.println("charlie is:" + eze.evaluate(userInput));
		}catch(SyntaxError e){
			System.out.println(e.getMessage());
		}
	
	System.out.println("-------------------Actual------------------");
	display(eze);
	
	System.out.println("userInput = \"delta = alpha - beta * charlie/delta\"");
	
	userInput = "delta = alpha - beta * charlie/delta";
		
	System.out.println("alpha   EXPECTED: 43.33333333333");
	System.out.println("beta    EXPECTED: 45.83333333333");
	System.out.println("charlie EXPECTED: -5.60436119243");
	System.out.println("delta   EXPECTED: 66.68483830182");
	System.out.println("X       EXPECTED: 336 ");
		
		try{
		    System.out.println("charlie is:" + eze.evaluate(userInput));
		}catch(SyntaxError e){
			System.out.println(e.getMessage());
		}
	
	System.out.println("-------------------Actual------------------");
	display(eze);
	
  }//end main
  
  public static void display(Evaluator eval){
	  
	System.out.println("alpha contains   :" + eval.symbols.getData("alpha"));
	System.out.println("beta contains    :" + eval.symbols.getData("beta"));
	System.out.println("charlie contains :" + eval.symbols.getData("charlie"));
	System.out.println("delta contains   :" + eval.symbols.getData("delta"));
	System.out.println("X contains       :" + eval.symbols.getData("X"));
	  
	
  }
}
