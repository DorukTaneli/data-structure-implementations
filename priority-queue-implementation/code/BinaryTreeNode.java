package code;

import given.Entry;

/*
 * The binary node class which extends the entry class.
 * This will be used in linked tree implementations
 * 
 */
public class BinaryTreeNode<Key, Value> extends Entry<Key, Value> {
  
	public Entry<Key, Value> entry; 	      		// an element stored at this node
    public BinaryTreeNode<Key, Value> parent;      // a reference to the parent node (if any)
    public BinaryTreeNode<Key, Value> leftChild;        // a reference to the left child (if any)
    public BinaryTreeNode<Key, Value> rightChild;       // a reference to the right child (if any)

    
    public BinaryTreeNode(Entry<Key, Value> e, BinaryTreeNode<Key, Value> above, BinaryTreeNode<Key, Value> leftChild, BinaryTreeNode<Key, Value> rightChild) {
        entry = e;
        parent = above;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
      }
    
    public BinaryTreeNode(Key k, Value v, BinaryTreeNode<Key, Value> above, BinaryTreeNode<Key, Value> leftChild, BinaryTreeNode<Key, Value> rightChild) {
        entry = new Entry<Key, Value>(k,v);
        parent = above;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
      }
    
    public BinaryTreeNode(Key k, Value v) {
    	entry = new Entry<Key, Value>(k,v);
      }
    
    // accessor methods
    public Entry<Key, Value> getEntry() { return entry; }
    public BinaryTreeNode<Key, Value> getParent() { return parent; }
    public BinaryTreeNode<Key, Value> getLeft() { return leftChild; }
    public BinaryTreeNode<Key, Value> getRight() { return rightChild; }
    public Key getKey() { return entry.getKey(); }
    public Value getValue() { return entry.getValue(); }
    
    // update methods
    public void setElement(Entry<Key, Value> e) { entry = e; }
    public void setParent(BinaryTreeNode<Key, Value> parentNode) { parent = parentNode; }
    public void setLeft(BinaryTreeNode<Key, Value> leftChild) { this.leftChild = leftChild; }
    public void setRight(BinaryTreeNode<Key, Value> rightChild) { this.rightChild = rightChild; }

    public String toString(){
    	return entry.toString();
    }

}
	