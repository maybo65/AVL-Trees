//Afik Cohen (afikcohen) - 209513480
//May Buhadana (maybuhadana) - 313558041

/**
 *
 * Circular list
 *
 * An implementation of a circular list with  key and info
 *
 */
 
 public class CircularList{ 
	 
	private int len;
	private int start;
	private int maxLen;
	private Item[] arr;
	 
	/**
	  *public CircularList (int maxLen)
	  *
	  *constructor
	  *time complexity: O(1). 
	  */
	
	public CircularList (int maxLen){
		this.len=0;
		this.maxLen= maxLen;
		this.start=0;
		this.arr=new Item[maxLen];
	}
	
	
	 /**
   * public Item retrieve(int i)
   *
   * returns the item in the ith position if it exists in the list.
   * otherwise, returns null
   * time complexity: O(1). 
   */
  public Item retrieve(int i)
  {
	if (i>=this.len || i<0) //invalid index 
		return null;
	else {
		return this.arr[(i+this.start)%this.maxLen];
	}
  }

  /**
   * public int insert(int i, int k, String s) 
   *
   * inserts an item to the ith position in list  with key k and  info s.
   * returns -1 if i<0 or i>n  or n=maxLen otherwise return 0.
   * time complexity: O(min{i,n-i}).
   */
   public int insert(int i, int k, String s) {
	   if( (this.len==this.maxLen)||i<0||i>this.len) {  //invalid input
		   return -1;
	   }
	   Item item =new Item(k,s); 
	   if (i<this.len-i) { //the given index is closer to the beginning
		   for(int j=this.start-1; j<this.start-1+i;j++) { //shifting the elements in smaller indices to the left
			   this.arr[(j+this.maxLen)%this.maxLen]=this.arr[(j+1)%this.maxLen];
		   }
		   this.start= (this.start-1+this.maxLen)%this.maxLen; //update the start field
	   }
	   else { //the given index is closer to the end of the list
		   for(int j=(this.start+this.len)%this.maxLen;j>(this.start+i)%this.maxLen;j--) { //shifting the elements in bigger indices to the right
			   this.arr[(j+this.maxLen)%this.maxLen]=this.arr[(j-1+this.maxLen)%this.maxLen];
		   }
	   }
	   this.arr[(i+this.start)%this.maxLen]=item;
	   this.len++; 
	   return 0;	
   }
  
   
   
  /**
   * public int delete(int i)
   *
   * deletes an item in the ith posittion from the list.
   * returns -1 if i<0 or i>n-1 otherwise returns 0.
   * time complexity: O(min{i,n-i}).
   */
   
   
   public int delete(int i)
   { 
	   if(i<0||i>this.len) { //invalid input
		   return -1;
	   }
	   if (i<this.len-i) {  //the given index is closer to the beginning
		   for(int j=this.start+i; j>this.start;j--) { //shifting the elements in smaller indices to the right
			   this.arr[(j+this.maxLen)%this.maxLen]=this.arr[(j-1+this.maxLen)%this.maxLen];
		   }
		   this.start= (this.start+1)%this.maxLen; //update the start field
	   }
	   else {//the given index is closer to the end of the list
		   for(int j=this.start+i;j<this.start+this.len;j++) {//shifting the elements in bigger indices to the left
			   this.arr[(j+this.maxLen)%this.maxLen]=this.arr[(j+1)%this.maxLen];
		   }
	   }
	   this.len--;
	   return 0;	
   }
	  
 }
 
 
 
