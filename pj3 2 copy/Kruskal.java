/* Kruskal.java */

import graph.*;
import dict.*;
import set.*;
import priorityQueue.*;
import priorityQueue.Entry;

/**
 * The Kruskal class contains the method minSpanTree(), which implements
 * Kruskal's algorithm for computing a minimum spanning tree of a graph.
 */

public class Kruskal {
	
	/**
	   * Part II:  Kruskal's Algorithm for Minimum Spanning Trees
	========================================================
	Implement Kruskal's algorithm for finding the minimum spanning tree of a graph.
	Minimum spanning trees, and Kruskal's algorithm for constructing them, are
	discussed by Goodrich and Tamassia, Sections 13.6-13.6.1.  Your algorithm
	should be embodied in a static method called minSpanTree() in a class called
	Kruskal, which is NOT in the graph package.  Your minSpanTree() method should
	not violate the encapsulation of the WUGraph ADT, and should only access a
	WUGraph by calling the methods listed in Part I.  You may NOT add any public
	methods to the WUGraph class to make Part II easier (e.g., a method that
	returns all the edges in a WUGraph).  Remember the encapsulation rule:  your
	Kruskal code should work correctly with the WUGraph code of any other group
	taking CS 61B, and your WUGraph code should work with their Kruskal code.

	The signature of minSpanTree() is:

	  public static WUGraph minSpanTree(WUGraph g);

	This method takes a WUGraph g and returns another WUGraph that represents the
	minimum spanning tree of g.  The original WUGraph g is NOT changed!  Let G be
	the graph represented by the WUGraph g.  Your implementation should run in
	O(|V| + |E| log |E|) time, where |V| is the number of vertices in G, and |E| is
	the number of edges in G.

	Kruskal's algorithm works as follows.

	[1]  Create a new graph T having the same vertices as G, but no edges (yet).
	     Upon completion, T will be the minimum spanning tree of G.

	[2]  Make a list (not necessarily linked) of all the edges in G.  You cannot
	     build this list by calling isEdge() on every pair of vertices, because
	     that would take O(|V|^2) time.  You will need to use multiple calls to
	     getNeighbors() to obtain the complete list of edges.

	     Note that your edge data structure should be defined separately from any
	     edge data structure you use in WUGraph.java (Part I).  Encapsulation
	     requires that the internal data structures of the WUGraph class not be
	     exposed to applications (including Kruskal).

	[3]  Sort the edges by weight in O(|E| log |E|) time.  You may write the
	     sorting algorithm yourself or use one from lab, but do not use a Java
	     library sorting method.  (You can instead use a priority queue, as
	     Goodrich and Tamassia suggest, but sorting in advance is more
	     straightforward and is probably faster.)

	[4]  Finally, find the edges of T using disjoint sets, as described in Lecture
	     33 and Goodrich & Tamassia Section 11.4.  The disjoint sets code from
	     Lecture 33 is included in DisjointSets.java in the "set" package/
	     directory.  To use the disjoint sets code, you will need a way to map the
	     objects that serve as vertices to unique integers.  Again, hash tables
	     are a good way to accomplish this.  (You cannot use the same hash table as
	     the WUGraph; that should be encapsulated so Kruskal can't see it.)

	     Be forewarned that the DisjointSets class has no error checking, and will
	     fail catastrophically if you union() two vertices that are not roots of
	     their respective sets, or if you union() a vertex with itself.  If you
	     add simple error checking, it might save you a lot of debugging time (here
	     and in Homework 9).

	My own Part II solution is 100 lines long, not counting the sorting method or
	hash table code.
	   */
	
	/*
	 * O(1)   WUGraph();                construct a graph having no vertices or edges.
O(1)   int vertexCount();           return the number of vertices in the graph.
O(1)   int edgeCount();                return the number of edges in the graph.
O(|V|) Object[] getVertices();             return an array of all the vertices.
O(1)   void addVertex(Object);                       add a vertex to the graph.
O(d)   void removeVertex(Object);               remove a vertex from the graph.
O(1)   boolean isVertex(Object);          is this object a vertex of the graph?
O(1)   int degree(Object);                       return the degree of a vertex.
O(d)   Neighbors getNeighbors(Object);        return the neighbors of a vertex.
O(1)   void addEdge(Object, Object, int);      add an edge of specified weight.
O(1)   void removeEdge(Object, Object);          remove an edge from the graph.
O(1)   boolean isEdge(Object, Object);               is this edge in the graph?
O(1)   int weight(Object, Object);              return the weight of this edge.


	 */
  
  /**
   * minSpanTree() returns a WUGraph that represents the minimum spanning tree
   * of the WUGraph g.  The original WUGraph g is NOT changed.
   */
  public static WUGraph minSpanTree(WUGraph g) {
	  WUGraph graphG = g; // Original WUGraph
	  WUGraph graphT = new WUGraph(); // WUGraph to edit
	  
	  // Import vertices from graphG to graphT
	  Object[] gVertices = graphG.getVertices();
	  for (int i = 0; i<gVertices.length; i++) {
		Object curVertex = gVertices[i];
		graphT.addVertex(curVertex);
	  }
	  
	  // PriorityQueue for edges
	  PriorityQueue myEdges = new PriorityQueue();
	  
	  // Hash Table for Edges, from Jay's
	  HashTableChained d = new HashTableChained();
	  int hashCounter =0;
	  
	  // Find all edges, add them to the PriorityQueue
	  for (int i = 0; i<gVertices.length; i++) {
	    Object curVertex = gVertices[i];
	    Neighbors myNeighbors = graphG.getNeighbors(curVertex);
	    Object[] neighborList = myNeighbors.neighborList;
	    int[] weightList = myNeighbors.weightList;
	    for (int k = 0; k<weightList.length; k++) {
	      myEdges.insert(weightList[k], 
            new MyEdge(curVertex, neighborList[k]));
          d.insert(curVertex, hashCounter++); // Insert into hash, from Jay's
    	}
      }
      
	  // DisjointSet to manage sets
	  DisjointSets mySet = new DisjointSets(hashCounter);
	  
	  // Go through the whole list of edges
	  priorityQueue.Entry curEntry = myEdges.removeMin();
	  while (curEntry != null) {
		MyEdge curEdge = (MyEdge) curEntry.value;
		Object vertex1 = curEdge.obj1;
		Object vertex2 = curEdge.obj2;
		int weight = curEntry.key;
		
		
		// If the edges are already connected, iterate through and continue
		/*
		if (mySet.find(hashCode(vertex1)) == mySet.find(hashCode(vertex2))) {
		  curEntry = myEdges.removeMin(); // Iterate
		  continue; // and continue
		} */
		
		int hash1 = (Integer) d.find(vertex1).value(); //From Jay's, remove!
		int hash2 = (Integer) d.find(vertex2).value(); //From Jay's, remove!
		if (mySet.find(hash1) == mySet.find(hash2)) {
		  curEntry = myEdges.removeMin(); // Iterate
		  continue; // and continue
		}
		
		// When two edges are NOT connected
		/*if (mySet.find(hashCode(vertex1)) != mySet.find(hashCode(vertex2))) {
		  mySet.union(hashCode(vertex1), hashCode(vertex2));
		  graphT.addEdge(vertex1, vertex2, weight);
		  curEntry = myEdges.removeMin();
		}*/
		
		if (mySet.find(hash1) != mySet.find(hash2)) {
		  mySet.union(hash1, hash2);
		  graphT.addEdge(vertex1, vertex2, weight);
		  curEntry = myEdges.removeMin();
		}
	  }
	  
	  return graphT;	  
  }
  
    
  // Return list of edges for a given vertex (don't need this)
  private static PriorityQueue findEdges(WUGraph graphG, Object vertex) {
    PriorityQueue myEdges = new PriorityQueue();
    Neighbors myNeighbors = graphG.getNeighbors(vertex);
    Object[] neighborList = myNeighbors.neighborList;
    int[] weightList = myNeighbors.weightList;
    for (int k = 0; k<weightList.length; k++) {
        myEdges.insert(weightList[k], 
          new MyEdge(vertex, neighborList[k]));
    }
    return myEdges;
  }
  
}