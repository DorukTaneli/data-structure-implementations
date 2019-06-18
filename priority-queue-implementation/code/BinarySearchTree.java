package code;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import given.iMap;
import given.Entry;
import given.iBinarySearchTree;

/*
 * Implement a vanilla binary search tree using a linked tree representation
 * Use the BinaryTreeNode as your node class
 */

public class BinarySearchTree<Key, Value> implements iBinarySearchTree<Key, Value>, iMap<Key, Value> {

	public BinaryTreeNode<Key, Value> root;
	private Comparator<Key> comparator;
	int size = 0;

	//	//	lazým olmaz herhalde
	//	//	protected BinaryTreeNode<Key, Value> validate(BinaryTreeNode<Key, Value> node) throws IllegalArgumentException {
	//	//		if (node.getParent() == node)     // our convention for defunct node
	//	//			throw new IllegalArgumentException("p is no longer in the tree");
	//	//		return node;
	//	//	}
	//	//
	//	//	public BinaryTreeNode<Key, Value> addRoot(Entry<Key, Value> e) throws IllegalStateException {
	//	//		if (!isEmpty()) throw new IllegalStateException("Tree is not empty");
	//	//		root = new BinaryTreeNode<Key, Value>(e, null, null, null);
	//	//		size = 1;
	//	//		return root;
	//	//	}
	//

	// CONSTRUCTOR
	public BinarySearchTree(BinaryTreeNode<Key, Value> n, Comparator<Key> c) {
		root = n;
		comparator = c;
	}

	public BinarySearchTree() {
	}

	@Override
	public Value get(Key k) {
		return getValue(k);
	}

	@Override
	public Value put(Key k, Value v) {
		BinaryTreeNode<Key, Value> newNode = new BinaryTreeNode<Key, Value>(k, v, null, null, null);
		size++;
		if(root == null){
			root = newNode;
			return null;
		}
		BinaryTreeNode<Key, Value> current = root;
		BinaryTreeNode<Key, Value> parent = null;
		while(true){
			parent = current;
			if(comparator.compare(k, current.getKey()) < 0){
				current = current.getLeft();
				if(current == null){
					current = newNode;
					parent.setLeft(current);
					current.setParent(parent);
					return null;
				}
			}else{
				current = current.getRight();
				if(current == null){
					current = newNode;
					parent.setRight(current);
					current.setParent(parent);
					return null;
				}
			}
		}
	}

	@Override
	public Value remove(Key k) {
		if(get(k) == null)
			return null;
		size--;
		return removeHelper(k,root).getValue();
	}


	public BinaryTreeNode<Key, Value> removeHelper(Key k, BinaryTreeNode<Key, Value> t){
		if(t == null)
			return t;

		int compareResult = comparator.compare(k, t.getKey());

		if(compareResult < 0)
			t.leftChild = removeHelper(k, t.leftChild);
		else if (compareResult > 0)
			t.rightChild = removeHelper(k, t.rightChild);
		else if (t.leftChild != null && t.rightChild != null){ //two children
			t.entry = findMin(t.rightChild).entry;
			t.rightChild = removeHelper(t.getKey(), t.rightChild);
		}
		else
			t = (t.leftChild != null) ? t.leftChild : t.rightChild;
		return t;
	}

	public BinaryTreeNode<Key, Value> findMin(BinaryTreeNode<Key, Value> node){
		if(node == null)
			return null;
		else if(node.getLeft() == null)
			return node;
		return findMin(node.getLeft());
	}

	public BinaryTreeNode<Key, Value> findMax(BinaryTreeNode<Key, Value> node){
		if(node != null)
			while(node.getRight() != null)
				node = node.getRight();

		return node;
	}

	@Override
	public Iterable<Key> keySet() {
		ArrayList<Key> lst = new ArrayList<Key>();
		List<BinaryTreeNode<Key, Value>> nodes = getNodesInOrder();
		for(int i = 0; i<nodes.size(); i++){
			lst.add(nodes.get(i).getKey());
		}
		return lst;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size==0;
	}

	@Override
	public int height() {
		if (isEmpty()) return 0;
		else return heightHelper(root);
	}

	public int heightHelper(BinaryTreeNode<Key,Value> node) {
		int leftH = 0;
		int rightH = 0;
		if (node == null)
			return -1;
		else {
			if (node.getLeft()!=null)
				leftH=heightHelper(node.getLeft());
			if (node.getRight()!=null)
				rightH=heightHelper(node.getRight());
			return Math.max(leftH, rightH)+1;
		}
	}

	@Override
	public BinaryTreeNode<Key, Value> getRoot() {
		return root;
	}

	@Override
	public BinaryTreeNode<Key, Value> getParent(BinaryTreeNode<Key, Value> node) {
		return node.getParent();
	}

	@Override
	public boolean isInternal(BinaryTreeNode<Key, Value> node) {
		return node.getLeft() != null || node.getRight() != null;
	}

	@Override
	public boolean isExternal(BinaryTreeNode<Key, Value> node) {
		return node.getLeft() == null && node.getRight() == null;
	}

	@Override
	public boolean isRoot(BinaryTreeNode<Key, Value> node) {
		return node == root;
	}

	@Override
	public BinaryTreeNode<Key, Value> getNode(Key k) {
		BinaryTreeNode<Key, Value> current = root;
		while(current!=null){
			if(current.getEntry().getKey() == k){
				return current;
			}else if(comparator.compare(current.getEntry().getKey(), k) > 0){
				current = current.getLeft();
			}else{
				current = current.getRight();
			}
		}
		return null;
	}

	@Override
	public Value getValue(Key k) {
		BinaryTreeNode<Key, Value> current = root;
		while(current!=null){
			if(current.getEntry().getKey() == k){
				return current.getValue();
			}else if(comparator.compare(current.getEntry().getKey(), k) > 0){
				current = current.getLeft();
			}else{
				current = current.getRight();
			}
		}
		return null;
	}

	@Override
	public BinaryTreeNode<Key, Value> getLeftChild(BinaryTreeNode<Key, Value> node) {
		return node.getLeft();
	}

	@Override
	public BinaryTreeNode<Key, Value> getRightChild(BinaryTreeNode<Key, Value> node) {
		return node.getRight();
	}

	@Override
	public BinaryTreeNode<Key, Value> sibling(BinaryTreeNode<Key, Value> node) {
		BinaryTreeNode<Key, Value> temp = node.getParent().getLeft();
		if (temp == node)
			return node.getParent().getRight();
		else return temp;
	}

	@Override
	public boolean isLeftChild(BinaryTreeNode<Key, Value> node) {
		return node.getParent().getLeft() == node;
	}

	@Override
	public boolean isRightChild(BinaryTreeNode<Key, Value> node) {
		return node.getParent().getRight() == node;
	}

	@Override
	public List<BinaryTreeNode<Key, Value>> getNodesInOrder() {
		List<BinaryTreeNode<Key, Value>> A = new ArrayList<BinaryTreeNode<Key, Value>>();
		return getNodesInOrderHelper(root, A);
	}

	public List<BinaryTreeNode<Key, Value>> getNodesInOrderHelper(BinaryTreeNode<Key,Value> node, List<BinaryTreeNode<Key, Value>> A){
		if(node.getLeft() != null)
			getNodesInOrderHelper(node.getLeft(), A);
		A.add(node);
		if(node.getRight() != null)
			getNodesInOrderHelper(node.getRight(), A);
		return A;
	}

	@Override
	public void setComparator(Comparator<Key> C) {
		this.comparator = C;
	}

	@Override
	public Comparator<Key> getComparator() {
		return comparator;
	}

	@Override
	public BinaryTreeNode<Key, Value> ceiling(Key k) {
		return ceilingHelper(root, k);
	}

	public BinaryTreeNode<Key, Value> ceilingHelper(BinaryTreeNode<Key, Value> x, Key key) {
		if (x == null) return null;
		int cmp = comparator.compare(key, x.getKey()); 
		if (cmp == 0) return x;
		if (cmp >  0) return ceilingHelper(x.getRight(), key);
		BinaryTreeNode<Key, Value> t = ceilingHelper(x.getLeft(), key); 
		if (t != null) return t;
		else return x; 
	}

	public BinaryTreeNode<Key, Value> floor(Key key) {
		return floorHelper(root, key);
	}

	public BinaryTreeNode<Key, Value> floorHelper(BinaryTreeNode<Key, Value> x, Key key) {
		if (x == null) return null;
		int cmp = comparator.compare(key, x.getKey()); 
		if (cmp == 0) return x;
		if (cmp <  0) return floorHelper(x.getLeft(), key);
		BinaryTreeNode<Key, Value> t = floorHelper(x.getRight(), key); 
		if (t != null) return t;
		else return x; 
	} 


}
