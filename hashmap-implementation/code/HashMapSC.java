package code;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import given.AbstractHashMap;
import given.HashEntry;

/*
 * The file should contain the implementation of a hashmap with:
 * - Separate Chaining for collision handling
 * - Multiply-Add-Divide (MAD) for compression: (a*k+b) mod p
 * - Java's own linked lists for the secondary containers
 * - Resizing (to double its size) and rehashing when the load factor gets above a threshold
 *   Note that for this type of hashmap, load factor can be higher than 1
 * 
 * Some helper functions are provided to you. We suggest that you go over them.
 * 
 * You are not allowed to use any existing java data structures other than for the buckets (which have been 
 * created for you) and the keyset method
 */

public class HashMapSC<Key, Value> extends AbstractHashMap<Key, Value> {

	// The underlying array to hold hash entry Lists
	private LinkedList<HashEntry<Key, Value>>[] buckets;

	// Note that the Linkedlists are still not initialized!
	@SuppressWarnings("unchecked")
	protected void resizeBuckets(int newSize) {
		// Update the capacity
		N = nextPrime(newSize);
		buckets = (LinkedList<HashEntry<Key, Value>>[]) Array.newInstance(LinkedList.class, N);
	}

	/*
	 * ADD MORE FIELDS IF NEEDED
	 * 
	 */

	// The threshold of the load factor for resizing
	protected float criticalLoadFactor;

	/*
	 * ADD A NESTED CLASS IF NEEDED
	 * 
	 */



	public int hashValue(Key key, int iter) {
		return hashValue(key);
	}

	public int hashValue(Key key) {
		// TODO: Implement the hashvalue computation with the MAD method. Will be almost
		// the same as the primaryHash method of HashMapDH
		return Math.abs(Math.floorMod((a * key.hashCode() + b) , P));
	}
	//DONE  

	// Default constructor
	public HashMapSC() {
		this(101);
	}

	public HashMapSC(int initSize) {
		// High criticalAlpha for representing average items in a secondary container
		this(initSize, 10f);
	}

	public HashMapSC(int initSize, float criticalAlpha) {
		N = initSize;
		criticalLoadFactor = criticalAlpha;
		resizeBuckets(N);

		// Set up the MAD compression and secondary hash parameters
		updateHashParams();

		/*
		 * ADD MORE CODE IF NEEDED
		 * 
		 */
	}

	/*
	 * ADD MORE METHODS IF NEEDED
	 * 
	 */
	public boolean contains(Key k){
		List<HashEntry<Key, Value>> whichList = buckets[hashValue(k)];
		return whichList.contains(k);
	}

	@Override
	public Value get(Key k) {
		// TODO Auto-generated method stub
		List<HashEntry<Key, Value>> whichList = buckets[hashValue(k)];
		if (whichList.isEmpty()) return null;
		for (int i = 0; i < whichList.size(); i++) {
			if(whichList.get(i).getKey() == k){
				return whichList.get(i).getValue();
			}
		}
		n--;
		return null;
		//		Value temp = null;
		//		for (int i = 0; i < buckets[hashValue(k)].size(); i++) {
		//			if (buckets[hashValue(k)].get(i).getKey() == k)
		//				temp = buckets[hashValue(k)].get(i).getValue();
		//		}
		//		return temp;
	}

	@Override
	public Value put(Key k, Value v) {
		// TODO Auto-generated method stub
		// Do not forget to resize if needed!
		// Note that the linked lists are not initialized!
		checkAndResize();
		Value old = get(k);
		if(buckets[hashValue(k)] == null)
			buckets[hashValue(k)] = new LinkedList<HashEntry<Key, Value>>();
		buckets[hashValue(k)].add(new HashEntry<Key, Value>(k,v));
		n++;
		return old;
	}

	@Override
	public Value remove(Key k) {
		// TODO Auto-generated method stub
		Value temp = null;
		for (int i = 0; i < buckets[hashValue(k)].size(); i++) {
			if (buckets[hashValue(k)].get(i).getKey() == k){
				temp = buckets[hashValue(k)].get(i).getValue();
				buckets[hashValue(k)].remove(i);
			}
		}
		if (temp != null)
			n--;
		return temp;
	}

	@Override
	public Iterable<Key> keySet() {
		// TODO Auto-generated method stub
		ArrayList<Key> ks = new ArrayList<Key>();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < buckets[i].size(); j++){
				ks.add(buckets[i].get(j).getKey());
			}
		}
		return ks;
	}

	/**
	 * checkAndResize checks whether the current load factor is greater than the
	 * specified critical load factor. If it is, the table size should be increased
	 * to 2*N and recreate the hash table for the keys (rehashing). Do not forget to
	 * re-calculate the hash parameters and do not forget to re-populate the new
	 * array!
	 */
	protected void checkAndResize() {
		if (loadFactor() > criticalLoadFactor) {
			// TODO: Fill this yourself
			HashMapSC<Key,Value> temp = new HashMapSC<Key,Value>(nextPrime(2*N));
			for (Key key : keySet()) {
				temp.put(key, get(key));
			}
			this.N = temp.N;
			this.n = temp.n;
			this.buckets = temp.buckets;
		}
	}
}
