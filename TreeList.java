//Afik Cohen (afikcohen) - 209513480
//May Buhadana (maybuhadana) - 313558041

/**
 *
 * Tree list
 *
 * An implementation of a Tree list with  key and info
 *
 */
 public class TreeList{
	 
	 private AVLTree tree;
	 private int len;
	 private AVLTree.IAVLNode first;
	 private AVLTree.IAVLNode last;
	 /**
	 *
	 * public TreeList()
	 *
	 * constructor.
	 * 
	 * time complexity: O(1).
	 */
	 
	 public TreeList(){
		 this.tree = new AVLTree();
		 this.len=0;
		 this.first=null;
		 this.last=null;
	 }
	 
	 /**
   * public Item retrieve(int i)
   *
   * returns the item in the ith position if it exists in the list.
   * otherwise, returns null.
   * 
   * time complexity: O(log(n)).
   */
  public Item retrieve(int i)
  {
	  AVLTree.IAVLNode node=retrieveNode(i); 
	  if (node==null) 
		  return null;
	  else {
		  Item res=new Item(node.getKey(),node.getValue());  
		  return res;
	  }
  }
  
  /**
   * private AVLTree.IAVLNode retrieveNode(int i)
   *
   * returns the node that represent the item in the ith position if it exists in the list.
   * otherwise, returns null.
   * 
   * time complexity: O(log(n)).
   */
  private AVLTree.IAVLNode retrieveNode(int i)
  {
	 if (i<0 || i>this.len-1) { //invalid index
		 return null;
	 }
	 if (i==0) //we are looking for first item
		 return first;
	 if (i==this.len-1) //we are looking for last item
		 return last;
	AVLTree.IAVLNode node=this.tree.getRoot();
	int index;
	while (true) { //searching the node with the given index
		if (node.getLeft()!=null)
			index=((AVLTree.AVLNode)node.getLeft()).getSize(); //calculating the index of the node
		else
			index=0;
		if (index==i) { //we found the node
			return node;
		}
		else if (index<i) { //keep searching
			node=node.getRight();
			i=i-index-1; 
		}
		else { //keep searching
			node=node.getLeft();
		}	
	}

  }
  
  
  

  /**
   * public int insert(int i, int k, String s) 
   *
   * inserts an item to the ith position in list  with key k and  info s.
   * returns -1 if i<0 or i>n otherwise return 0.
   * 
   * time complexity: O(log(n)).
   */
   public int insert(int i, int k, String s) {
	  if (i<0 || i>this.len) { //Invalid index
			 return -1;
	  }
	  if (tree.empty()) {
		  insertToEmptyList(k,s);
	  }
	  else { 
		  AVLTree.IAVLNode newNode= this.tree.new AVLNode(k,s); //creating a new node
		  this.tree.balance(insertToNonEmptyList(i,newNode)); //insert the node and balance the tree 	  
	  }
	  return 0;
   }
   
   /**
    * private void insertToEmptyList(int k, String s)
    *
    * inserts a node with key k and value s to an empty list.
    * 
    * time complexity: O(1).
    */
   private void insertToEmptyList(int k, String s) {
		  this.tree.insert(k, s);
		  this.first=this.tree.getRoot();
		  this.last=this.tree.getRoot();
		  this.len++;
	   }
   
   /**
    *  private AVLTree.IAVLNode insertToNonEmptyList(int i, AVLTree.IAVLNode newNode)
    *  
    * inserts the given node at the i'th index.
    * returns the node that we'll start the balancing process from it upwards, or null if no balance needed.
    * 
    * time complexity: O(log(n)).
    */
   
   private AVLTree.IAVLNode insertToNonEmptyList(int i, AVLTree.IAVLNode newNode) {
		  AVLTree.IAVLNode x; // this will be the node that from it we'll start balance the tree
		  if (i==this.len) { //insert last
			  this.last.setRight(newNode);
			  newNode.setParent(this.last);
			  x=this.last;
			  this.last=newNode; //updating the last field of the tree
			 }
		  else if (i==0) { //insert first
			  this.first.setLeft(newNode);
			  newNode.setParent(this.first);
			  x=this.first; 
			  this.first=newNode; //updating the first field of the tree
		  }
		  else { 
			x = retrieveNode(i); 
			if (x.getLeft()==null) { //we can insert the node as the left child of his successor
				x.setLeft(newNode);
				newNode.setParent(x);
			}
			else { //we need to insert the node as the right child of his predecessor
				x=retrieveNode(i-1);
				x.setRight(newNode);
				newNode.setParent(x);
			}
		}
		  this.len++;
		  return x;
   }  
		  
	   	

  /**
   * public int delete(int i)
   *
   * deletes an item in the ith posittion from the list.
	* returns -1 if i<0 or i>n-1 otherwise returns 0.
	* 
	* time complexity: O(log(n)).
   */
   public int delete(int i)
   {
	   if (i<0 || i>this.len-1) { //invalid index
			 return -1;
	  }
	   AVLTree.IAVLNode node=retrieveNode(i); 
	   if (i==0) //we going to delete the first node
		   this.first=retrieveNode(1);
	   if (i==len-1) //we going to delete the last node
		   this.last=retrieveNode(len-2);
	   AVLTree.IAVLNode balanceStart; //we'll start the balancing process from this node
	   if (node.getRight()==null && node.getLeft()==null) {  //the node is a leaf
		   balanceStart=this.tree.deleteLeaf(node);
	   }
	   else if (node.getRight()==null) {  //the node has only left child
		   balanceStart=this.tree.deleteWithOnlyLeftChild(node);
	   }
	   else if (node.getLeft()==null) {  //the node has only right child
		   balanceStart=this.tree.deleteWithOnlyRightChild(node);
	   }
	   else { //the node has two children
		   balanceStart=this.tree.deleteWithTwoChildren(node);
	   }
	   this.tree.balance(balanceStart); //balance the tree
	   this.len--;
	   return 0;   
   }
	  
 }