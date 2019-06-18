package code;

import java.util.Comparator;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.CompareGenerator;

import given.Entry;
import given.iAdaptablePriorityQueue;
import given.iBinarySearchTree;


public class ArrayBasedHeap<Key, Value> implements iAdaptablePriorityQueue<Key, Value> {

	Entry<Key, Value>[] E = new Entry[1000];
	int size;
	private Comparator<Key> cmp;

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	public int parentOf(int i){
		return (i-1)/2;
	}
	public int leftChildOf(int i){
		return 2*i+1;
	}
	public int rightChildOf(int i){
		return 2*i+1;
	}
	public boolean hasLeftChild(int i){
		return leftChildOf(i) < size();
	}
	public boolean hasRightChild(int i){
		return rightChildOf(i) < size();
	}
	public void swap(int i, int j){
		Entry<Key, Value> temp = E[i];
		E[i] = E[j];
		E[j] = temp;
	}
	public void upheap(int i){
		while (i>0) {
			int p = parentOf(i);
			if (cmp.compare(E[i].getKey(), E[p].getKey()) >= 0)
				break;
			swap(i,p);
			i=p;
		}
	}
	public void downheap(int i){
		while (hasLeftChild(i)) {
			int smallChild = leftChildOf(i);
			if (hasRightChild(i)) {
				int rc = rightChildOf(i);
				if (cmp.compare(E[rc].getKey(), E[smallChild].getKey()) < 0)
					smallChild = rc;
			}
			if (cmp.compare(E[smallChild].getKey(), E[i].getKey()) >= 0)
				break;
			swap(i, smallChild);
			i = smallChild;
		}
	}

	@Override
	public void setComparator(Comparator<Key> C) {
		cmp = C;
	}

	@Override
	public void insert(Key k, Value v) {
		Entry<Key, Value> newestEntry = new Entry<Key, Value>(k, v);
		E[size] = newestEntry;
		upheap(size);
		size++;
	}

	@Override
	public Entry<Key, Value> pop() {
		if (isEmpty()){
			return null;
		}
		Entry<Key, Value> temp = E[0];
		swap(0,size-1);
		E[size - 1] = null;
		downheap(0);
		size--;
		return temp;
	}

	@Override
	public Entry<Key, Value> top() {
		return E[0];
	}

	@Override
	public Value remove(Key k) {
		Value temp = null;
		for(int i = 0; i < size; i++){
			if(E[i].getKey() == k){
				temp = E[i].getValue();
				swap(i,size-1);
				E[size] = null;
				downheap(i);
				size--;
			}
		}
		return temp;
	}

	@Override
	public Key replaceKey(Entry<Key, Value> entry, Key k) {
		Key oldKey = entry.getKey();
		entry.setKey(k);
		return oldKey;
	}

	@Override
	public Key replaceKey(Value v, Key k) {
		Key oldKey = null;
		for(int i = 0; i < size; i++){
			if(E[i].getValue() == v){
				oldKey = E[i].getKey();
				E[i].setKey(k);
			}
		}
		return oldKey;
	}
	
	
	@Override
	public Value replaceValue(Entry<Key, Value> entry, Value v) {
		Value oldValue = entry.getValue();
		entry.setValue(v);
		return oldValue;
	}


}