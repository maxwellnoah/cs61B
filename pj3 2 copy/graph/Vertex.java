package graph;
import list.*;

public class Vertex {
  private Object vertex; // field for actual Object
  private DList edgeList; // DList keeping all the edges
  private ListNode myNode;
  int degree;
 // private DList neighbors; // DList of all neighbors
  
  public Vertex(Object item) {
    vertex = item;
    edgeList = new DList();
    myNode = null;
    //neighbors = new DList();
  }
  
  public Object getItem() {
    return vertex;
  }
  
  public void setNode(ListNode node) {
    myNode = node;
  }
  
  public ListNode getNode() {
    return myNode;
  }
  
  
  
  /*
   * public DList getNeighbors() {
    return neighbors;
  }*/
  
  public DList getEdges() {
    return edgeList;
  }
  /*
  public void addNeighbor(Vertex neighbor) {
    neighbors.insertBack(neighbor);
  }*/
  
  public void addEdge(Edge newEdge) {
    edgeList.insertBack(newEdge);
    degree++;
  }
  
  public void removeEdge() {
    degree--;
  }
  
  public int getDegree() {
	  return degree;
  }
}
