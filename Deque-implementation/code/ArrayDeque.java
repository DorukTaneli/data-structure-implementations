package code;

/* 
 * ASSIGNMENT 2
 * AUTHOR: Doruk Taneli
 * Class : ArrayDeque
 *
 * You are not allowed to use Java containers!
 * You must implement the Array Deque yourself
 *
 * MODIFY 
 * 
 * */

import given.iDeque;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import given.Util;


//BARIS: May need to give them some pointers on how an arraydeque works
public class ArrayDeque<E> implements iDeque<E> {

	private E[] A;
	private int front = 0;
	private int back = 0;
	private int size = 0;
	private int capacity;
	/*
	 * ADD FIELDS IF NEEDED
	 */

	public ArrayDeque() {
		this(1000);
		/*
		 * ADD CODE IF NEEDED
		 */
	}

	public ArrayDeque(int initialCapacity) {
		if(initialCapacity < 1)
			throw new IllegalArgumentException();
		A = createNewArrayWithSize(initialCapacity);

		capacity = initialCapacity;
		/*
		 * ADD CODE IF NEEDED
		 */
	}

	// This is given to you for your convenience since creating arrays of generics is not straightforward in Java
	@SuppressWarnings({"unchecked" })
	private E[] createNewArrayWithSize(int size) {
		return (E[]) new Object[size];
	}

	//Bonus: Modify this such that the dequeue prints from front to back!
	// Hint, after you implement the iterator, use that!
	public String toString() {
		if(size==0)
			return "";
		StringBuilder sb = new StringBuilder(1000);
		sb.append("[");
		Iterator<E> iter = iterator();
		while(iter.hasNext()) {
			E e = iter.next();
			if(e == null)
				continue;
			sb.append(e);
			if(!iter.hasNext())
				sb.append("]");
			else
				sb.append(", ");
		}
		return sb.toString();
	}

	/*
	 * ADD METHODS IF NEEDED
	 */
	public void resizeStorage(){
		capacity = 2*capacity;
		Iterator<E> iter = iterator();
		E[] B = createNewArrayWithSize(capacity);
		int i = 0;
		while(iter.hasNext()){
			B[i]=iter.next();
			i++;
		}
		front = 0;
		back = size-1;
		A = B;

	}

	/*
	 * Below are the interface methods to be overriden
	 */

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return (size == 0);
	}

	@Override
	public void addFront(E o) {
		// TODO Auto-generated method stub
		if (size == A.length)
			resizeStorage();
		if(isEmpty()){
			A[front] = o;
		}else{
			front = Math.floorMod((front - 1), A.length);
			A[front] = o;
		}
		size++;  
	}

	@Override
	public E removeFront() {
		// TODO Auto-generated method stub
		if (isEmpty()){
			return null;
		}else if (size == 1){
			E temp = A[front];
			A[front] = null;
			front = 0;
			back = 0;
			size--;
			return temp;
		} else{
			E temp = A[front];
			A[front] = null;
			front = Math.floorMod((front + 1), A.length);
			size--;
			return temp;
		}
	}

	@Override
	public E front() {
		// TODO Auto-generated method stub
		if (isEmpty())
			return null;
		return A[front];
	}

	@Override
	public void addBehind(E o) {
		// TODO Auto-generated method stub
		if (size == A.length)
			resizeStorage();
		if(isEmpty()){
			A[0] = o;
		}else {
			back = Math.floorMod((back + 1), A.length);
			A[back] = o;
		}
		size++;
	}

	@Override
	public E removeBehind() {
		// TODO Auto-generated method stub
		if (isEmpty()){
			return null;
		}else if(size == 1){
			E temp = A[back];
			A[back]=null;
			back = 0;
			front = 0;
			size--;
			return temp;
		}else{
			E temp = A[back];
			A[back] = null;
			back = Math.floorMod((back - 1), A.length);
			size--;
			return temp;
		}
	}

	@Override
	public E behind() {
		// TODO Auto-generated method stub
		if (isEmpty())
			return null;
		return A[back];
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		A = createNewArrayWithSize(capacity);
		front = 0;
		back = 0;
		size = 0;

	}

	//Must print from front to back
	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		//Hint: Fill in the ArrayDequeIterator given below and return a new instance of it
		return new ArrayDequeIterator();
	}

	private final class ArrayDequeIterator implements Iterator<E> {

		/*
		 * 
		 * ADD A CONSTRUCTOR IF NEEDED
		 * Note that you can freely access everything about the outer class!
		 * 
		 */
		int currentIndex;

		public ArrayDequeIterator() {
			this.currentIndex = front;
		}

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return currentIndex != Math.floorMod((front+size), A.length);
		}

		@Override
		public E next() {
			// TODO Auto-generated method stub
			int oldIndex = currentIndex;
			currentIndex = Math.floorMod((currentIndex + 1), A.length);
			return A[oldIndex];
		}        
	}
}