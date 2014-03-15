/* MyEdge.java */

public class MyEdge {
	  // Note that there is no field for weight of an edge,
	  // as that information is stored with as the key with
	  // the priorityQueue
	  Object obj1; // First vertex of an edge
	  Object obj2; // Second vertex of an edge
	  
	  // Default constructor
	  public MyEdge() {
	  }
	  
	  // Another edge constructor
	  public MyEdge(Object vertex1, Object vertex2) {
		  obj1 = vertex1;
		  obj2 = vertex2;  
	  }
}
