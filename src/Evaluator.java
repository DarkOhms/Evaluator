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
  
	//Parser parse = new Parser();
  
  Stack<String> eval = new Stack<String>();
  Queue<String> postfix = new Queue<String>();
  HashTable symbols = new HashTable();
  
  {
	  symbols.insert("A", 25);
	  symbols.insert("B", 10);
  }
  
  
  
  String unaryEval(String data, String operator){
	  double operand = 0.0;
	  String result = "";
	  
	  if(data.contains("[a-zA-Z]+")){
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
		  left = symbols.getData(data1);
	  }else{
		  left = Double.parseDouble(data1);
	  }
	  
	  if(data2.matches("[a-zA-Z]+")){
		  right = symbols.getData(data1);
	  }else{
		  right = Double.parseDouble(data2);
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
  
  public double evaluate(String input){
	  
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
		  if(parser.s1.firstInLine().matches("\\+|\\*|/|sin|sqr|abs")){
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
	  
	String userInput = new String("X=12*(A+3)");
	
	//if A is stored as 25, upon evaluation this should assign 336 to X in the symbol table
	
	Evaluator eze = new Evaluator();
	System.out.println(eze.evaluate(userInput));
	 
  }
}
