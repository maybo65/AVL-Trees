//Afik Cohen (afikcohen) - 209513480
//May Buhadana (maybuhadana) - 313558041

/**
 *
 * AVLTree
 *
 * An implementation of a AVL Tree with
 * distinct integer keys and info
 *
 */

public class AVLTree {
	private IAVLNode root;
	private IAVLNode minimum;
	private IAVLNode maximum;
	
	/**
	   * public AVLTree()
	   *
	   * constructor.
	   * 
	   * time complexity: O(1).
	   *
	   */
	public AVLTree() {
		this.root=null;
		this.minimum=null;
		this.maximum=null;
	}

  /**
   * public boolean empty()
   *
   * returns true if and only if the tree is empty.
   * 
   *time complexity: O(1).
   *
   */
  public boolean empty() {
	if (this.root==null) 
		return true; 
	else
		return false;
  }

 /**
   * public String search(int k)
   *
   * returns the info of an item with key k if it exists in the tree.
   * otherwise, returns null.
   * 
   * time complexity: O(log(n)).
   */
  public String search(int k)
  {
	  if (this.empty()) 
		return null;
	  else {
		  IAVLNode x =find(k);
		  if(x.getKey()==k)
			  return x.getValue();
		  else 
			  return null;	  
	  }
  }

  /**
   * public int insert(int k, String i)
   *
   * inserts an item with key k and info i to the AVL tree.
   * the tree must remain valid (keep its invariants).
   * returns the number of rebalancing operations, or 0 if no rebalancing operations were necessary.
   * returns -1 if an item with key k already exists in the tree.
   * 
   * time complexity: O(log(n)).
   */
   public int insert(int k, String i) {
	   
	   AVLNode newNode = new AVLNode(k,i); 
	   updateMinMaxBeforeInsert(newNode);
	   	if (this.empty()) {
	   		this.root=newNode;
			return 0; 
	   	}
	   IAVLNode parent = find(newNode.getKey()); // the tree is not empty, therefore the parent is not null
	   if (parent.getKey()==k) //a node with a key k is already exist in the the tree
		   return -1;
	   else
		   insertAsSonOf(parent,newNode);		  
	   return balance((AVLNode) parent);	
	   }
	  
   /**
    * private IAVLNode find(int k)
    *
    *returns node with key k if it exists in the tree.
    *otherwise, returns the node who suppose to be the parent of a node with key k, 
    *if the node will be inserted in the current structure of the tree. 
    *
    *time complexity: O(log(n)). 
    */

   private IAVLNode find(int k) {
	   if (this.empty()) 
			return null;
	   IAVLNode x=this.getRoot();
	   while (true) { //searching the given key, from the root down
			  if (x.getKey()==k) //we found the node with key k 
				  break;
			  else if (x.getKey()<k) {// the possible location for the node is in the right subtree
				  if (x.getRight()==null) { //node with key k is not exists
					  break;
				  }
				  else
					  x=(AVLNode) x.getRight(); //the node might be exists, move to the right subtree
			  }
			  else {// the possible location for the node is in the left subtree
				  if (x.getLeft()==null) {//node with key k is not exists
					  break;
				  }
				  else
					  x=(AVLNode) x.getLeft();//the node might be exists, move to the left subtree
			  }
		  }
	   return x; 
   }
   
   /**
    *  private void updateMinMaxBeforeInsert(IAVLNode node)
    *
    * updating the min and max fields, for the insertion method, 
    * if the new node's key is smaller then the min's key, or bigger then the max's key.
    * 
    * time complexity: O(1).
    */
   
   private void updateMinMaxBeforeInsert(IAVLNode node) {
	   if(this.empty()) { //the tree is empty
		   	this.minimum=node; 
	   		this.maximum=node;
	   }
	   else {
		   int k=node.getKey();
		   if (k<this.minimum.getKey()) { //the new node's key is smaller then the min's key
			   this.minimum=node;
		   }
		   if (k>this.maximum.getKey()) { //the new node's key is bigger then the max's key
			   this.maximum=node;
		   }
	   }

   }
   
   /**
    * private void insertAsSonOf(IAVLNode parent, IAVLNode son) 
    *
    *gets two nodes, with different keys, parent and son.
    *inserts the son as the correct child of the parent.
    *
    *time complexity: O(1).
    */
   private void insertAsSonOf(IAVLNode parent, IAVLNode son) {
	   if(parent.getKey()>son.getKey()) { //the son should be the left child
		   parent.setLeft(son);
	   }
	   else { //the son should be the right child
		   parent.setRight(son);
	   }
	   son.setParent(parent); 
   }
   
   /**
    *  private int heightCal(IAVLNode x)
    * 
    * calculates and returns the height of a given node.
    * 
    * time complexity: O(1).
    */
   
   private int heightCal(IAVLNode x) {
	   if (x.getRight()!=null && x.getLeft()!=null) //the node has two children
		   return Math.max(x.getLeft().getHeight(),x.getRight().getHeight())+1;
	   else if (x.getRight()!=null) //the node has only right child
		   return x.getRight().getHeight()+1;
	   else if (x.getLeft()!=null) //the node has only left child
		   return x.getLeft().getHeight()+1;
	   else //the node is a leaf
		   return 0;
   }
   
   /**
    *  private int sizeCal(IAVLNode x)
    * 
    * calculates and returns the size of a given node.
    * 
    * time complexity: O(1).
    */
   
   private int sizeCal(IAVLNode x) {
	   if (x.getLeft()!=null&&x.getRight()!=null) //the node has two children
		   return ((AVLNode)x.getLeft()).size+((AVLNode)x.getRight()).size+1;
	   if(x.getRight()!=null) //the node has only right child
		   return ((AVLNode)x.getRight()).size+1;
       if(x.getLeft()!=null) //the node has only left child
	       return ((AVLNode)x.getLeft()).size+1;
       else //the node is a leaf
    	   return 1;
   }
   
   /**
    * private IAVLNode leftRotation(IAVLNode x)
    * 
    * operating a left rotation on a given node x.
    * returns the node that is now the root of the subtree, that x was its root before the rotation.
    * 
    * time complexity: O(1).
    */
   
   private IAVLNode leftRotation(IAVLNode x) {
	   AVLNode y= (AVLNode) x.getRight();
	   x.setRight(y.getLeft());
	   if (y.getLeft()!=null)
		   y.getLeft().setParent(x);
	   y.setParent(x.getParent());
	   if (x.getParent()==null) 
		   this.root=y;
	   else if (x==x.getParent().getLeft()) 
		   x.getParent().setLeft(y);
	   else
		   x.getParent().setRight(y);
	   y.setLeft(x);
	   x.setParent(y);
	   ((AVLNode)x).setSize(sizeCal(x)); //updating size
	   y.setSize(sizeCal(y)); //updating size
	   x.setHeight(heightCal(x)); //updating height
	   y.setHeight(heightCal(y)); //updating height
	   return y;
	   
   }
   
   /**
    * private IAVLNode rightRotation(IAVLNode x)
    * 
    * operating a right rotation on a given node x.
    * returns the node that is now the root of the subtree, that x was its root before the rotation.
    * 
    * time complexity: O(1).
    */
   
   private IAVLNode rightRotation(IAVLNode x) {
	   AVLNode y= (AVLNode)x.getLeft();
	   x.setLeft(y.getRight());
	   if (y.getRight()!=null)
		   y.getRight().setParent(x);
	   y.setParent(x.getParent());
	   if (x.getParent()==null) 
		   this.root=y;
	   else if (x==x.getParent().getLeft()) 
		   x.getParent().setLeft(y);
	   else
		   x.getParent().setRight(y);
	   y.setRight(x);
	   x.setParent(y);
	   ((AVLNode)x).setSize(sizeCal(x)); //updating size
	   y.setSize(sizeCal(y)); //updating size
	   x.setHeight(heightCal(x)); //updating height
	   y.setHeight(heightCal(y)); //updating height
	   return y;
   }
   
   
   /**
    * private int BFcal(IAVLNode x) 
    * 
    * calculates and returns the BF of a given node
    * 
    * time complexity: O(1).
    */
   
   private int BFcal(IAVLNode x) {
	   if(x.getLeft()!=null && x.getRight()!=null)  //the node has two children
		   return x.getLeft().getHeight()-x.getRight().getHeight();
	   if(x.getLeft()!=null) //the node has only left child
		   return x.getLeft().getHeight()+1;
	   if(x.getRight()!=null) //the node has only right child
		   return -1-x.getRight().getHeight();
	   else //the node is a leaf
		   return 0;
   }
   
   /**
    * public int balance(IAVLNode x)
    * 
    * balancing the tree from a given node all the way up to the root.
    * returns the number of rebalancing operations.
    * 
    * time complexity: O(log(n)).
    */
   public int balance(IAVLNode x) {
	   int cnt=0; //count the number of rebalancing operations
	   int BF;
	   int sonBF;
	   while (x!=null) {
		   BF=BFcal(x);
		   if (BF==-2) { //if there is a problem with the BF
			   sonBF=BFcal(x.getRight());
			   if (sonBF==-1 || sonBF==0) { //checking which rotation we need
				   x=leftRotation(x);
				   cnt++;
			   }
			   else {
				   rightRotation(x.getRight());
				   x=leftRotation(x);
				   cnt+=2;
			   }
		   }
		   else if (BF==2){ //if there is a problem with the BF
			   sonBF=BFcal(x.getLeft()); 
			   if (sonBF==1 || sonBF==0) { //checking which rotation we need
				   x=rightRotation(x);
				   cnt++;
			   }
			   else {
				   leftRotation(x.getLeft());
				   x=rightRotation(x);
				   cnt+=2;
			   }
		   }
		   x.setHeight(heightCal(x)); //updating the height of the node
		   ((AVLNode)x).setSize(sizeCal(x)); //updating the size of the node
		   x=((AVLNode)x.getParent()); //moving up
		}
	   return cnt;
   }
   
   
   
  /**
   * public int delete(int k)
   *
   * deletes an item with key k from the binary tree, if it is there;
   * the tree must remain valid (keep its invariants).
   * returns the number of rebalancing operations, or 0 if no rebalancing operations were needed.
   * returns -1 if an item with key k was not found in the tree.
   * 
   * time complexity: O(log(n)).
   */

   
   public int delete(int k)
   {
	   if (this.empty()) {
		   return -1;
	   }
	   updateMinMaxBeforeDelete(k); 
	   IAVLNode node=find(k);
	   IAVLNode balanceStart; //we'll start the balancing process from this node
	   if (node.getKey()!=k) //there is no node with key k in the tree
		   return -1;
	   else if (node.getRight()==null && node.getLeft()==null)  //the node is a leaf
		   balanceStart=deleteLeaf(node);
	   else if (node.getRight()==null)  //the node has only left child
		   balanceStart=deleteWithOnlyLeftChild(node);
	   else if (node.getLeft()==null)  //the node has only right child
		   balanceStart=deleteWithOnlyRightChild(node);
	   else //the node has two children
		   balanceStart=deleteWithTwoChildren(node);
	   return balance(balanceStart); 
   }
   
   /**
    *  private void updateMinMaxBeforeDelete(int k)
    *
    * updating the min and max fields, for the deletion method,
    * if the given k (which is the key of the node that is supposed to be deleted) is the min's or max's key.
    * 
    * time complexity: O(log(n)). 
    */
   
   private void updateMinMaxBeforeDelete(int k) {
	   if (k==this.minimum.getKey()) { //the min supposed to be deleted
		   if (this.minimum.getHeight()==0) { //the min is a leaf
			   this.minimum= this.minimum.getParent(); //the new min is the prev min's parent
		   }
		   else { //the min is not a leaf, he necessarily have only right child
			   this.minimum= successorWithRightChild(this.minimum); // the new min is the prev min's successor	   
		   }
	   }
	   if (k==this.maximum.getKey()) { ////the max is supposed to be deleted
		   if (this.maximum.getHeight()==0) { //the max is a leaf
			   this.maximum= this.maximum.getParent(); //the new max is the prev max's parent
		   }
		   else { //the max is not a leaf, he necessarily have only left child
			   this.maximum= predecessorWithLeftChild(this.maximum); // the new max is the prev max's predecessor	 	   
		   }
	   }
		   
   }
   
   
   /**
    *  public IAVLNode deleteLeaf(IAVLNode node)
    *
    * gets a leaf and deletes it from the tree.
    *returns the node that we'll start the balancing process from it upwards, or null if no balance needed.
    *
    *time complexity: O(1).
    */
   
   public IAVLNode deleteLeaf(IAVLNode node) {
	   int k=node.getKey();
	   if (this.root.getKey()==k) { //the given node is the root
		   this.root=null;  //updating the root
		   return null;
	   }
	   else { //the given node is not the root
		  IAVLNode parent=node.getParent();
		//checking which child the node is to its parent and updating the pointers
		  if (parent.getRight()==node) { 
			  parent.setRight(null); 
		  }
		  else {
			  parent.setLeft(null);
		  }
		  node.setParent(null);
		  return parent;
	   }
	   
   }
   
   /**
    *  public IAVLNode deleteWithOnlyRightChild(IAVLNode node)
    *
    * gets a node with only right child and deletes it from the tree.
    *returns the node that we'll start the balancing process from it upwards, or null if no balance needed.
    *
    *time complexity: O(1).
    */
   
   public IAVLNode deleteWithOnlyRightChild(IAVLNode node) {
	   int k=node.getKey();
	   if (this.root.getKey()==k) { //the given node is the root
		   this.root=(AVLNode) node.getRight();  //updating the root
		   this.root.setParent(null);
		   node.setRight(null);
		   return null;
	   }
	   else { //the given node is not the root
		   IAVLNode parent=node.getParent();
		 //checking which child the node is to its parent and updating the pointers
		   if (parent.getRight()==node) {
			   parent.setRight(node.getRight());
			   node.getRight().setParent(parent);
			   node.setParent(null);
			   node.setRight(null);
		   }
		   else {
			   parent.setLeft(node.getRight());
			   node.getRight().setParent(parent);
			   node.setParent(null);
			   node.setRight(null);
		   }
		   return parent;
	   }
   }
   
   /**
    *  public IAVLNode deleteWithOnlyLeftChild(IAVLNode node)
    *
    * gets a node with only left child and deletes it from the tree.
     *returns the node that we'll start the balancing process from it upwards, or null if no balance needed.
     *
     *time complexity: O(1).
    */
   
   public IAVLNode deleteWithOnlyLeftChild(IAVLNode node) {
	   int k=node.getKey();
	   if (this.root.getKey()==k) { //the given node is the root
		   this.root=(AVLNode) node.getLeft(); //updating the root
		   this.root.setParent(null);
		   node.setLeft(null);
		   return null;
	   }
	   else {  //the given node is not the root
		   IAVLNode parent=node.getParent();
		 //checking which child the node is to its parent and updating the pointers
		   if (parent.getRight()==node) {
			   parent.setRight(node.getLeft());
			   node.getLeft().setParent(parent);
			   node.setParent(null);
			   node.setLeft(null);
		   }
		   else {
			   parent.setLeft(node.getLeft());
			   node.getLeft().setParent(parent);
			   node.setParent(null);
			   node.setLeft(null);
		   }
		   return parent;
	   }
   }
   

   /**
    *  public IAVLNode deleteWithTwoChildren(IAVLNode node)
    *
    * gets a node with only left child and deletes it from the tree.
    *returns the node that we'll start the balancing process from it upwards, or null if no balance needed.
    *
    *time complexity: O(log(n)).
    */
   public IAVLNode deleteWithTwoChildren(IAVLNode node) {
	   IAVLNode successor=successorWithRightChild(node); //find the successor of the given node
	   int k=node.getKey();
	   IAVLNode balanceStart; //we'll start the balancing process from this node
	   if (successor.getHeight()==0) //the successor is a leaf
		   balanceStart=deleteLeaf(successor);
	   else { //the successor is not a leaf, so it necessarily has only a right child
		   balanceStart=deleteWithOnlyRightChild(successor);
	   }
	   if (balanceStart==node)
		   balanceStart=successor;
	   if (this.root.getKey()==k) { //the given node is the root
		  this.root=(AVLNode) successor;  //updating the root
		  //updating the pointers
		  successor.setRight(node.getRight());
		  successor.setLeft(node.getLeft());
		  if (node.getLeft()!=null)
			  node.getLeft().setParent(successor);
		  if (node.getRight()!=null)
			  node.getRight().setParent(successor);
		  node.setParent(null);
		  node.setRight(null);
		  node.setLeft(null);
	   }
	   else { //the given node is not the root
		  IAVLNode parent=node.getParent();
		//checking which child the node is to its parent 
		  if (parent.getRight()==node) {
			  parent.setRight(successor);
		  }
		  else {
			  parent.setLeft(successor);
		  }
		//updating the pointers
		  successor.setRight(node.getRight());
		  successor.setLeft(node.getLeft());
		  successor.setParent(parent);
		  if (node.getLeft()!=null)
			  node.getLeft().setParent(successor);
		  if (node.getRight()!=null)
			  node.getRight().setParent(successor);
		  node.setParent(null);
		  node.setRight(null);
		  node.setLeft(null);
	   }
	   return balanceStart;
	   }
   
   /**
    * private IAVLNode successorWithRightChild(IAVLNode node)
    *
    * gets a node with right child.
    * returns the successor of the node.
    * 
    * time complexity: O(log(n)).
    */
   
   private IAVLNode successorWithRightChild(IAVLNode node) {
	   IAVLNode y=node.getRight();
	   while (y.getLeft()!=null) { //reaching the node with minimal key in the right subtree of the given node
		   y=y.getLeft();
	   }
	   return y;
   }

   /**
    * private IAVLNode predecessorWithLeftChild(IAVLNode node)
    *
    * gets a node with left child.
    * returns the predecessor of the node.
    * 
    * time complexity: O(log(n)).
    */
   
   private IAVLNode predecessorWithLeftChild(IAVLNode node) {
	   IAVLNode y=node.getLeft();
	   while (y.getRight()!=null) { //reaching the node with maximal key in the left subtree of the given node
		   y=y.getRight();
	   }
	   return y;
   }

   
   /**
    * public String min()
    *
    * Returns the info of the item with the smallest key in the tree,
    * or null if the tree is empty.
    * 
    * time complexity: O(1).
    */
   public String min()
   {
	   if (this.empty())
		   return null;
	   else
		   return this.minimum.getValue();
   }

   /**
    * public String max()
    *
    * Returns the info of the item with the largest key in the tree,
    * or null if the tree is empty.
    * 
    * time complexity: O(1).
    */
   public String max()
   {
	   if (this.empty())
		   return null;
	   else
		   return this.maximum.getValue();
   
   }

  /**
   * public int[] keysToArray()
   *
   * Returns a sorted array which contains all keys in the tree,
   * or an empty array if the tree is empty.
   * 
   * time complexity: O(n).
   */
  public int[] keysToArray()
  {
	  if (this.empty()) {
		  int[] arr = new int [0]; 
		  return arr; //return an empty list
	  }
      int[] arr = new int[this.size()]; 
      keysToArrayRec(this.getRoot(), arr, 0); //fills the array
      return arr;              
  }
  
  /**
   * private int keysToArrayRec(IAVLNode x, int[] arr, int index)
   *
   *a recursive method that fill the given array with the keys of the tree's nodes,
   *starting from the smallest key all the way to the maximum key.
   *the function uses the returned index in order to place the key in the right index in the array. 
   *
   *time complexity: O(n).

   */
  
  private int keysToArrayRec(IAVLNode x, int[] arr, int index){
	  if(x==null) 
		  return index;
	  	index=keysToArrayRec(x.getLeft(),arr, index); //first recur on left subtree
        arr[index]=x.getKey(); //insert the node's key
        index++;
        index=keysToArrayRec(x.getRight(),arr,index); //recur the right subtree
	    return index;
  }
  
  
  /**
   * public String[] infoToArray()
   *
   * Returns an array which contains all info in the tree,
   * sorted by their respective keys,
   * or an empty array if the tree is empty.
   * 
   * time complexity: O(n).
   */
  public String[] infoToArray()
  {
        if (this.empty()) {
         String[] arr = new String [0];
  		  return arr;
  		  }
        String[] arr = new String[this.size()];
        infoToArrayRec(this.getRoot(), arr, 0);
        return  arr;         
  }
  
  /**
   * private int infoToArrayRec(IAVLNode x, String[] arr, int index)
   *
   *a recursive method that fill the given array with the values of the tree's nodes,
   *starting from the value of the node with the smallest key, all the way to the maximum key.
   *the function uses the returned index in order to place node's value in the right index in the array. 
   *
   *time complexity: O(n).

   */
  
  private int infoToArrayRec(IAVLNode x, String[] arr, int index){
	  if(x==null) 
		  return index;
	    index=infoToArrayRec(x.getLeft(),arr, index); //first recur on left subtree
        arr[index]=x.getValue();//insert the node's value
        index++;
        index=infoToArrayRec(x.getRight(),arr,index); //recur the right subtree
	    return index;
  }
 
   /**
    * public int size()
    *
    * Returns the number of nodes in the tree.
    *
    * precondition: none
    * postcondition: none
    * 
    * time complexity: O(1).
    */
   public int size() {
	   if (this.root==null)
		   return 0;
	   else
		   return ((AVLNode)this.root).getSize(); 
   }
   
     /**
    * public IAVLNode getRoot()
    *
    * Returns the root AVL node, or null if the tree is empty
    *
    * precondition: none
    * postcondition: none
    * 
    * time complexity: O(1).
    */
   public IAVLNode getRoot()
   {
		return this.root;
   }
   

	/**
	   * public interface IAVLNode
	   * ! Do not delete or modify this - otherwise all tests will fail !
	   */
	public interface IAVLNode{	
		public int getKey(); //returns node's key 
		public String getValue(); //returns node's value [info]
		public void setLeft(IAVLNode node); //sets left child
		public IAVLNode getLeft(); //returns left child (if there is no left child return null)
		public void setRight(IAVLNode node); //sets right child
		public IAVLNode getRight(); //returns right child (if there is no right child return null)
		public void setParent(IAVLNode node); //sets parent
		public IAVLNode getParent(); //returns the parent (if there is no parent return null)
    	public void setHeight(int height); // sets the height of the node
    	public int getHeight(); // Returns the height of the node 
	}

   /**
   * public class AVLNode
   *
   * If you wish to implement classes other than AVLTree
   * (for example AVLNode), do it in this file, not in 
   * another file.
   * This class can and must be modified.
   * (It must implement IAVLNode)
   */
  public class AVLNode implements IAVLNode{
	  
	  private int key;
	  private String value;
	  private AVLNode left;
	  private AVLNode right;
	  private AVLNode parent;
	  private int size;
	  private int height;
	  
	  public AVLNode(int Key, String Value)
	  {
		  this.key = Key;
		  this.value = Value;
		  this.left=null;
		  this.right=null;
		  this.parent=null;
		  this.size=1;
		  this.height=0;
	  }
		public int getKey()
		{
			return this.key; 
		}
		public String getValue()
		{
			return this.value; 
		}
		public void setLeft(IAVLNode node)
		{
			this.left=(AVLNode) node; 
		}
		public IAVLNode getLeft()
		{
			return this.left; 
		}
		public void setRight(IAVLNode node)
		{
			this.right= (AVLNode) node; 
		}
		public IAVLNode getRight()
		{
			return this.right; 
		}
		public void setParent(IAVLNode node)
		{
			this.parent=(AVLNode) node; 
		}
		public IAVLNode getParent()
		{
			return this.parent; 
		}

		public void setHeight(int height)
		{
			this.height= height; 
		}
		public int getHeight()
		{
			return this.height; 
		}
		public void setSize(int size)
		{
			this.size=size; 
		}
		public int getSize()
		{
			return this.size; 
		}

  }

}




