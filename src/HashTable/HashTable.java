package HashTable;

import java.util.*;

import HashTable.HashTable;

import java.io.*;
	
public class HashTable{
	
	
	//main buckets
	bucket[] buckets = new bucket[20];
	
	//overflow
    bucket[] overflow = new bucket[10];  //allocates overflow
	
	// index determined by hash method used in the insert method
	int index;
	
	// overflow index for populate function
	int overflowIndex;
    
    //search data
    String[] search = new String[20];
    
    //search results
    searchResult[] results = new searchResult[20];
    {
    	initializeHT();
    }
    
   
    String fitKey(String key){
	   
	   if(key.length() < 10){
	    	  while(key.length() < 10){
	    		  String newString = new String(" ");
	    		  key += newString;
	    	  }
	      }else{
	    	  key = key.substring(0,10);
	      }
	   return key;
   }
    
    
    private void hashFunction(String key){
    	
    	int intermediateValue;
    	
    	//handle length discrepancys
    	key = fitKey(key);
    	
    	intermediateValue = (int)(key.charAt(0)) + (int)(key.charAt(2)) + (int)(key.charAt(4));
			index = intermediateValue%20;
			
    }
	
    // get data to be inserted into the hash and store it in allData
	public void initializeHT(){
	
		//initialize buckets
		for(int i =0; i < buckets.length; i++)
			buckets[i] = new bucket();
		//initialize overflow
		for(int i =0; i < overflow.length; i++)
			overflow[i] = new bucket();
		index = 0;
		
	}
	
  //hash insert 
  public void insert(String tempKey, double tempData){
	  
		 
		  //grab key and data from allData to prep for insert
		  
		  hashFunction(tempKey);
		  
		  //check bucket index for free slot and insert or find overflow
		  if(buckets[index].hasFreeSlot())
			  buckets[index].fillSlot(tempKey, tempData);
		  else
			  if(buckets[index].hasOverflow()){
				 overflowIndex = buckets[index].getOverflow();
		         overflow[overflowIndex].fillSlot(tempKey, tempData);
			  }else{
				 //allocate overflow and set overflow index
				 overflowIndex = allocateOverflow();
				 //System.out.println("Overflow index is: " + overflowIndex);
				 
				 //link bucket to an overflow bucket
				 buckets[index].setOverflow(overflowIndex);
				 
				 //fill overflow with temp key and data
				 overflow[overflowIndex].fillSlot(tempKey, tempData);
				 
			  }
		  
  }
  
 public double getData(String tempKey){
	 
	 hashFunction(tempKey);
	 
	 int slotResult = buckets[index].searchBucket(tempKey);
	 
	 return buckets[index].slots[slotResult].getData();
	 
 }
 
 public void searchHash(String tempKey){
	  
	  
	  for(int i = 0; i < search.length; i++){
		
		   hashFunction(tempKey);
		  
		  //check bucket index for key match
		  
		  int slotResult = buckets[index].searchBucket(tempKey);
		  
		  if(slotResult != -1){
			  results[i].foundKey(tempKey);
			  results[i].wasFound();
			  results[i].inSlot(slotResult);
			  results[i].inBucket(index);
			  results[i].foundData(buckets[index].getSlot(slotResult).getData());
			  
		  }else//if bucket has an overflow bucket, search that bucket then store result in results[]
			   if(buckets[index].hasOverflow()){
				 
				 overflowIndex = buckets[index].getOverflow();
				 //System.out.println("Overflow bucket is: " + overflowIndex);
				 slotResult = overflow[overflowIndex].searchBucket(tempKey);
				 
				 if(slotResult != -1){
				   
				   //System.out.println(slotResult);
				   results[i].foundKey(tempKey);
				   results[i].wasFound();
				   results[i].inSlot(slotResult);
			       results[i].inBucket(overflowIndex);
				   results[i].foundData(overflow[overflowIndex].getSlot(slotResult).getData());
				   results[i].setOverflow(true);
				 }else{
					   //System.out.println("Could not find " + tempKey);
					   results[i].missingKey(tempKey);
					 }
			 
				 
			  }else{
				  //System.out.println("Could not find " + tempKey);
				  results[i].missingKey(tempKey);
			  }
		  
	  }
	  
  }
  
  int allocateOverflow(){
	
	 int tempIndex = -1;
	 
	 for(int i = 0; i < overflow.length; i++){
		if(overflow[i].count == 0){
			tempIndex = i;
            break;
		}
	 }
	 
	 return tempIndex; 
  }
  
  public void generateReport(){
	  try{
		  PrintWriter writer = new PrintWriter("verify.txt");
	  writer.println("Hash Table Verification Report");
	  	for (int bucket = 0; bucket < 20; bucket++) {
	  		writer.println("Bucket " + bucket);
	  		for (int slot = 0; slot < 3; slot++) {
	  			writer.println("    Slot " + slot);
	  			writer.println("        Key: " + buckets[bucket].slots[slot].key);
	  			writer.println("        Data: " + buckets[bucket].slots[slot].data);
	  		}
	  		writer.println("    Overflow Pointer: " + buckets[bucket].overflowIndex);
	  	}
	  	for (int overflow = 0; overflow < 10; overflow++) {
	  		writer.println("Overflow " + overflow);
	  		for (int slot = 0; slot < 3; slot++) {
	  			writer.println("    Slot " + slot);
	  			writer.println("        Key: " + this.overflow[overflow].slots[slot].key);
	  			writer.println("        Data: " + this.overflow[overflow].slots[slot].data);
	  		}
	 			writer.println("    Overflow Pointer: " + this.overflow[overflow].overflowIndex);
	  	}
	  	writer.println();
	  	writer.println("Average chain length is: " + String.valueOf(averageChainLength()));
	  	writer.close();
	  }catch(FileNotFoundException e) {
			System.out.println("File not found");
		}
  }
  
  public void searchReport(){
	  
	  try{
		  PrintWriter writer = new PrintWriter("searchresults.txt");
	  
	  writer.println("                   SEARCH REULTS                                ");
	  writer.println("================================================================");
	  
	  for (searchResult result : results) {
			if (result.found) {
				writer.println(result.key + " found in bucket " + result.bucket + ": slot " + result.slot);
			} else {
				writer.println(result.key + " not found");
			}
		  }
	  writer.close();
	  }catch(FileNotFoundException e) {
			System.out.println("File not found");
		}
  }
  
  public int getChainLength(bucket b){
	  
	 int length;
	 length = b.count;
	 if(b.hasOverflow()){
		 length += overflow[b.getOverflow()].getCount() + 1;
	 }
	 return length;
	  
  }
  public double averageChainLength(){
	  
	  int numChains = 0;
	  int totalChainLength = 0;
	  double avgChainLength = 0.0;
	  
	  for(int i = 0; i < buckets.length; i++){
		  if(buckets[i].getCount() >0){
			  numChains++;
		  }
		  totalChainLength += getChainLength(buckets[i]);
	  }
	  
	  avgChainLength = Math.floor(((double)totalChainLength/numChains)*100)/100;
	  
	  return avgChainLength;
  }
  
  public static void main(String[] args){
		
	    
		HashTable myHash = new HashTable();
		myHash.initializeHT();
		myHash.insert("alpha", 12000);
		System.out.println(myHash.getData("alpha"));
  }
}

class bucket{
	// 3 slots per bucket
	slot [] slots = new slot[3];
	
	//auto initialize slots
	{
		for(int i = 0; i < slots.length; i++)
			slots[i] = new slot();
	}
	
	
	int count = 0;  //initialize slot fill count
	boolean bucketFull = false;  //set default overflow status to false
	int overflowIndex = -1;
	
	
	public boolean hasFreeSlot(){
		if(count < 3)
			return true;
		else
			return false;
	}
	
	public boolean hasOverflow(){
		if(overflowIndex == -1)
			return false;
		else
			return true;
	}
	
	public int getOverflow(){
		return overflowIndex;
	}
	
	public void setOverflow(int overflow){
		overflowIndex = overflow;
	}
	
	public void fillSlot(String key, double data){
		slots[count].setKey(key);
		slots[count].setData(data);
		count++;
		
		if(count == 2)
			bucketFull = true;
	}
	
	public slot getSlot(int s){ 
		return slots[s];
	}
	
	public int getCount(){
		return count;
	}
	
	//returns a slot index where the key finds a match or returns a -1 sentinel
	public int searchBucket(String key){
	  
		for(int i = 0; i < count; i++){
			if(Objects.equals(slots[i].getKey(),key)){
				//System.out.println("Found in slot: " + i);
				return i;
				
			}else{
				//System.out.println("Not found in slot: " + i); 
			}
		}return -1;
	
	}
}

class slot{
	
	String key = new String();
	double data = 0.0;  
	
	//use default constructor, make others later
	
	public String getKey(){
		return key;
	}
	
	public double getData(){
		return data;
	}
	
	public void setKey(String k){
		key = k;
	}
	
	public void setData(double d){
		data = d;
	}
}


class searchResult{
     
	boolean found = false;
	boolean overflow = false;
	int bucket;
	int slot;
	String key;
	double data;
	
	public boolean checkFound(){return found;}
	
	public void wasFound(){found = true;}
	
	public int inBucket(){return bucket;}
	
	public void inBucket(int b){bucket = b;}
	
	public int inSlot(){return slot;}
	
	public void inSlot(int s){slot = s;}
	
	public void foundKey(String k){key = k;}
	
	public void missingKey(String k){key = k;}
	
	public String keyResult(){return key;}
	
    public void foundData(double d){data = d;}
	
	public double dataResult(){return data;}
	
	public void setOverflow(boolean b){overflow = b;}
}
  