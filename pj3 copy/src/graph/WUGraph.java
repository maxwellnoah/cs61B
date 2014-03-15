/* WUGraph.java */

package graph;

/**
 * The WUGraph class represents a weighted, undirected graph.  Self-edges are
 * permitted.
 */

import list.*;
import dict.*;

public class WUGraph {
	private DList vertexList;
	private HashTableChained vertexTable, edgeTable;
	private int vertices, edges;

  /**
   * WUGraph() constructs a graph having no vertices or edges.
   *
   * Running time:  O(1).
   */
  public WUGraph(){
	  vertexList = new DList();
	  vertexTable = new HashTableChained();
	  edgeTable = new HashTableChained();
	  vertices = 0;
	  edges = 0;
  }
  /**
   * vertexCount() returns the number of vertices in the graph.
   *
   * Running time:  O(1).
   */
  public int vertexCount(){
	return vertices;
}

  /**
   * edgeCount() returns the number of edges in the graph.
   *
   * Running time:  O(1).
   */
  public int edgeCount(){
		return edges;
	    }

  /**
   * getVertices() returns an array containing all the objects that serve
   * as vertices of the graph.  The array's length is exactly equal to the
   * number of vertices.  If the graph has no vertices, the array has length
   * zero.
   *
   * (NOTE:  Do not return any internal data structure you use to represent
   * vertices!  Return only the same objects that were provided by the
   * calling application in calls to addVertex().)
   *
   * Running time:  O(|V|).
   */
  public Object[] getVertices(){
	  Object [] array = new Object [vertexList.length()];
	  int counter = 0;
	  DListNode temp = (DListNode) vertexList.front();
	  while (temp.isValidNode()){
		  try{
			  array[counter] = temp.item();
			  temp = (DListNode) temp.next();
			  counter ++;
		  }
		  catch (InvalidNodeException e){
			  System.out.println(e);
		  }
	  }
	  return array;
  }
  /**
   * addVertex() adds a vertex (with no incident edges) to the graph.  The
   * vertex's "name" is the object provided as the parameter "vertex".
   * If this object is already a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(1).
   */
  public void addVertex(Object vertex){
	  if (!isVertex(vertex)){
		  vertexList.insertBack(vertex);
		  vertexTable.insert(vertex, new DList()).listVertex = (DListNode) vertexList.back();
		  vertices ++;
	  }
  }
  /**
   * removeVertex() removes a vertex from the graph.  All edges incident on the
   * deleted vertex are removed as well.  If the parameter "vertex" does not
   * represent a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public void removeVertex(Object vertex){
	  dict.Entry e = vertexTable.find(vertex);
	  if (e != null){
		  DListNode node = (DListNode) ((DList) e.value()).front();

		  while (node.isValidNode()){
			  try{
				  if (((dict.Entry)node.item()).e1 != node){
					  ((dict.Entry)node.item()).e1.remove();
				  }
				  else if (((dict.Entry)node.item()).e2 != node){
					  ((dict.Entry)node.item()).e2.remove();
				  }
				  edgeTable.remove(((dict.Entry) node.item()).key());
				  node = (DListNode) node.next();
			  }
			  catch (InvalidNodeException exception){
				  System.out.println("removeVertex error");
			  }
		  }
		  vertices --;
		  edges -= ((List) e.value()).length();
		  try{
			  e.listVertex.remove();
		  }
		  catch(InvalidNodeException exception){
			  System.out.println("removeVertex error 2");
		  }
		  vertexTable.remove(vertex);
	  }
  }

  /**
   * isVertex() returns true if the parameter "vertex" represents a vertex of
   * the graph.
   *
   * Running time:  O(1).
   */
  public boolean isVertex(Object vertex){
	    //sees if the given vertex is in the hashtable
		return vertexTable.find(vertex) != null;
	    }

  /**
   * degree() returns the degree of a vertex.  Self-edges add only one to the
   * degree of a vertex.  If the parameter "vertex" doesn't represent a vertex
   * of the graph, zero is returned.
   *
   * Running time:  O(1).
   */
  public int degree(Object vertex) {
	  Entry e = vertexTable.find(vertex);
	  if (e == null){
		  return 0;
	  }
	  else{
		  return ((List) e.value()).length();
	  }
  }


  /**
   * getNeighbors() returns a new Neighbors object referencing two arrays.  The
   * Neighbors.neighborList array contains each object that is connected to the
   * input object by an edge.  The Neighbors.weightList array contains the
   * weights of the corresponding edges.  The length of both arrays is equal to
   * the number of edges incident on the input vertex.  If the vertex has
   * degree zero, or if the parameter "vertex" does not represent a vertex of
   * the graph, null is returned (instead of a Neighbors object).
   *
   * The returned Neighbors object, and the two arrays, are both newly created.
   * No previously existing Neighbors object or array is changed.
   *
   * (NOTE:  In the neighborList array, do not return any internal data
   * structure you use to represent vertices!  Return only the same objects
   * that were provided by the calling application in calls to addVertex().)
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public Neighbors getNeighbors(Object vertex){
	  Entry e = vertexTable.find(vertex);
	  if (e == null || ((List) e.value()).length() == 0){
		  return null;
	  }
	  DListNode node = (DListNode) ((DList) e.value()).front();
	  Neighbors n = new Neighbors();
	  n.neighborList = new Object[((List) e.value()).length()];
	  n.weightList = new int [((List) e.value()).length()];
	  int ctr = 0;
	  while (node.isValidNode()){
		  try{
			  if (((VertexPair)((Entry) node.item()).key()).object1 != vertex){
				  n.neighborList[ctr] = ((VertexPair)((Entry) node.item()).key()).object1;
			  }
			  else{
				  n.neighborList[ctr] = ((VertexPair)((Entry) node.item()).key()).object2;
			  }
			  n.weightList[ctr] = (int) ((Entry) node.item()).value();
			  ctr ++;
			  node = (DListNode) node.next();
		  }
		  catch (InvalidNodeException exception){
			  System.out.println ("getNeighbors error");
		  }
	  }
	  return n;
  }

  /**
   * addEdge() adds an edge (u, v) to the graph.  If either of the parameters
   * u and v does not represent a vertex of the graph, the graph is unchanged.
   * The edge is assigned a weight of "weight".  If the edge is already
   * contained in the graph, the weight is updated to reflect the new value.
   * Self-edges (where u == v) are allowed.
   *
   * Running time:  O(1).
   */
  public void addEdge(Object u, Object v, int weight);

  /**
   * removeEdge() removes an edge (u, v) from the graph.  If either of the
   * parameters u and v does not represent a vertex of the graph, the graph
   * is unchanged.  If (u, v) is not an edge of the graph, the graph is
   * unchanged.
   *
   * Running time:  O(1).
   */
  public void removeEdge(Object u, Object v);

  /**
   * isEdge() returns true if (u, v) is an edge of the graph.  Returns false
   * if (u, v) is not an edge (including the case where either of the
   * parameters u and v does not represent a vertex of the graph).
   *
   * Running time:  O(1).
   */
  public boolean isEdge(Object u, Object v);

  /**
   * weight() returns the weight of (u, v).  Returns zero if (u, v) is not
   * an edge (including the case where either of the parameters u and v does
   * not represent a vertex of the graph).
   *
   * (NOTE:  A well-behaved application should try to avoid calling this
   * method for an edge that is not in the graph, and should certainly not
   * treat the result as if it actually represents an edge with weight zero.
   * However, some sort of default response is necessary for missing edges,
   * so we return zero.  An exception would be more appropriate, but
   * also more annoying.)
   *
   * Running time:  O(1).
   */
  public int weight(Object u, Object v);

}