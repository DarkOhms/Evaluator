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
 * Evaluator ---- 1:1 uses ----- Queue
 * Parser ------- 1:2 includes - Stack  //?
 * Parser ------- 1:1 includes-- Queue  //?
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
import listadt.Iterator;

import java.util.*;

public class Evaluator {
  
	//Parser parse = new Parser();
  
  Stack<String> eval = new Stack<String>();
  Queue<String> postfix = new Queue<String>();
  
  
  String unaryEval(String data, String operator){
	  
	  return result;
  }
  
  String binaryEval(String data1, String data2, String operator){
	  
	  return result;
  }
  
  public double evaluate(String input){
	  
	  //parse the infix
	  Parser parser = new Parser(input);
	  parser.parse();
	  
	  Iterator<String> it = parser.s1.iterator();
	  
	  //put it in postfix queue
	  while(it.hasNext()){
		  postfix.enqueue(it.nextData());
	  }
	  
	  //check for operators and put everything else on eval stack
	  //loop through postfix and evaluate
	  while(!postfix.isEmpty()){
		  
		  if(postfix.firstInLine() == "\\+|\\*|/|sin|sqr|abs"){
			  //use the operator to evaluate the eval stack
			  
		  }else{
			  eval.push(postfix.dequeue());
		  }
	  }
	  
	  
  }
  
  
  
  public static void main(String[] args){
	  
	
	 
  }
}
