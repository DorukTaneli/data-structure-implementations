package code;

import java.util.ArrayList;
import java.util.List;

import given.Entry;
import given.iAdaptablePriorityQueue;

/*
 * Implement a binary search tree based priority queue
 * Do not try to create heap behavior (e.g. no need for a last node)
 * Just use default binary search tree properties
 */

public class BSTBasedPQ<Key, Value> extends BinarySearchTree<Key, Value> implements iAdaptablePriorityQueue<Key, Value> {
	
	//BinarySearchTree<Key, Value> bst = new BinarySearchTree<Key, Value>();
	
	@Override
	public void insert(Key k, Value v) {
		put(k, v);
		//NotImplementedYetSoft();
	}

	@Override
	public Entry<Key, Value> pop() {
		if(isEmpty())
			return null;
		Entry<Key, Value> temp = findMin(getRoot());
		remove(temp.getKey());
		return temp;
	}

	@Override
	public Entry<Key, Value> top() {
		return findMin(getRoot());
	}

	@Override
	public Key replaceKey(Entry<Key, Value> entry, Key k) {
		Key temp = entry.getKey();
		entry.setKey(k);
		return temp;
	}

	@Override
	public Key replaceKey(Value v, Key k) {
		List<BinaryTreeNode<Key, Value>> nodes = getNodesInOrder();
		Key temp = null;
		for(int i = 0; i < size(); i++){
			if(nodes.get(i).getValue().equals(v)){
				temp = getNode(nodes.get(i).getKey()).getKey();
				getNode(nodes.get(i).getKey()).setKey(k);
			}
		}
		return temp;
	}

	@Override
	public Value replaceValue(Entry<Key, Value> entry, Value v) {
		Value temp = entry.getValue();
		entry.setValue(v);
		return temp;
	}
	
	  public static void NotImplementedYetSoft()
	  {
	    String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
	      String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
	      String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
	      int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
	        
	      String message = "Not implemented yet:" + System.lineSeparator() + className + "." + methodName + "():" + lineNumber;
	        
	      System.out.println(message);
	  }

}
