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

import java.util.*;

public class Evaluator {
  
	//Parser parse = new Parser();
  
  Stack<String> eval = new Stack<String>();
  
  String unaryEval(String data, String operator){
	  
	  return result;
  }
  
  String binaryEval(String data1, String data2, String operator){
	  
	  return result;
  }
  
  
  
  public static void main(String[] args){
	  
	  String input = new String("a=3*5+6");
	  StringTokenizer st = new StringTokenizer(input,"=-+*)(", true);
	     while (st.hasMoreTokens()) {
	         st.nextToken();
	         
	     }
	 
  }
}
