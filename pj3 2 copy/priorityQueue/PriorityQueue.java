/* PriorityQueue.java */

package priorityQueue;

/**
 * The priorityQueue is a data structure, as described in lecture 25.
 * Its structure will keep its items in a prioritized fashioned.
 * 
 * @author calvin_cheng
 */

public class PriorityQueue {
  Object[] queue; // Array keep track of the Queue
  int size; // Keeps track of the size
  int defaultSize = 20; // Initial priority queue size
  int nextIndex;
  
  public PriorityQueue() {
	queue = new Object[defaultSize];
	size = 0;
	nextIndex = 1;
  }
  
  public int size() {
	return size;
  }
  
  public boolean isEmpty() {
	return size<1;
  }
  
  public Entry min(){
	Entry curEntry = (Entry) queue[1];
	if (isEmpty()) {
	  return null; // Return null, if curEntry is null
	} else {
	  return curEntry;
	}
  }
  
  public Entry insert(int key, Object value) {
	// Creates a new Entry according to the arguments
	Entry newEntry = new Entry();
	newEntry.key = key;
	newEntry.value = value;
	
	// Resizing the Queue, as necessary
	int totalLength = queue.length;
	if ((double)size/totalLength > 0.90) { // When too large
	  Object[] newQueue = new Object[totalLength*2];
	  for (int i=1; i<=size; i++) {
		newQueue[i] = queue[i];
	  }
	  queue = newQueue; // Replace old queue with new queue
	}

	queue[nextIndex] = newEntry; // Place new Entry in queue
	int parentIndex = (int) Math.floor(nextIndex/2);
	int curIndex = nextIndex;
	
	// Bubble up entry, if needed
	while (parentIndex>0 && key<((Entry)queue[parentIndex]).key) {
	  queue[curIndex] = queue[parentIndex];
	  queue[parentIndex] = newEntry;
	  curIndex = parentIndex;
	  parentIndex = (int) Math.floor(curIndex/2);
	}
	
	nextIndex++;
	size++;
	return newEntry;
  }
  
  public Entry removeMin() {
	if (isEmpty()) {
	  return null; // Return null ifEmpty
	}
	
	Entry returnMe = (Entry) queue[1]; // Keep minEntry to return later
	queue[1] = queue[nextIndex-1]; // Replace with last Entry;
	
	
	// Find minimum values
	int curIndex = 1;
	int[] minChild = minChild(curIndex);
	int minChildIndex = minChild[0];
	int minChildKey = minChild[1];
	while ((curIndex <= size && minChildIndex <= size) &&
			((Entry)queue[curIndex]).key > minChildKey) {
			  Object temp = queue[curIndex];
			  queue[curIndex] = queue[minChildIndex];
			  queue[minChildIndex] = temp;
				
			  // Recursively loop through
			  curIndex = minChildIndex;
			  minChild = minChild(curIndex);
			  minChildIndex = minChild[0];
			  minChildKey = minChild[1];
	}	
	queue[nextIndex-1] = null;
	nextIndex--;
	size--;
	return returnMe;
  }
  
  // Private Helper function to identify the minimum Child
  private int[] minChild(int curIndex) {
	int[] minChild = new int[2];
	int child1 = curIndex*2;
	int child2 = (curIndex*2)+1;
	int child1Key = Integer.MAX_VALUE;
	int child2Key = Integer.MAX_VALUE;
	
	if (child1 <= size && queue[child1] != null) {
	  child1Key = ((Entry)queue[child1]).key;
	}
	if (child2 <= size && queue[child2] != null) {
	  child2Key = ((Entry)queue[child2]).key;
	}
	
	int minChildIndex = 0;
	int minChildKey = Integer.MAX_VALUE;
	if (child1Key < child2Key) {
	  minChildIndex = child1;
	  minChildKey = child1Key;
	} else if (child2Key <= child1Key) {
	  minChildIndex = child2;
	  minChildKey = child2Key;
	}
	
	minChild[0] = minChildIndex;
	minChild[1] = minChildKey;
	return minChild;
  }
  
  public static void main(String[] args) {
	  
	//Test Code!
	PriorityQueue myQueue = new PriorityQueue();
	java.util.Random random = new java.util.Random();
	
	for (int i=1; i<250; i++) {
		int rando = random.nextInt(1000);
		System.out.println("Just inserted " + rando);
		myQueue.insert(rando, "This is item number " + i);
		
	}

	System.out.println();
	System.out.println("Final size " + myQueue.size());
	System.out.println("Now testing removeMin");
	System.out.println();
	
	for (int i=0; i<=300; i++) {
		Entry myEntry = myQueue.removeMin();
		if (myEntry != null) {
		System.out.println("For call number " + i + " key is : " + myEntry.key);
		}
	}
	
  }
}
