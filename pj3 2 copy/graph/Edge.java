/* Edge.java */

package graph;


import list.*;

public class Edge {
	
	private Object uObject;
	private Object vObject;
	private Vertex u;
	private Vertex v;
	private VertexPair myPair;
	private DListNode partner;
	private DListNode self;
	private int weight;

	public Edge(Object obj1, Object obj2, Vertex v1, Vertex v2, int value, VertexPair vPair) {
		uObject = obj1;
		vObject = obj2;
		u = v1;
		v = v2;
		weight = value;
		partner = null;
		self = null;
		myPair = vPair;
	}
	
	public void setPartner(DListNode other) {
		partner = other;
	}
	
	public DListNode getPartner() {
		return partner;
	}
	
	public void setSelf(DListNode other) {
		self = other;
	}
	
	public DListNode getSelf() {
		return self;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public void setWeight(int num) {
		weight = num;
	}
	
	public Object getUObject() {
		return uObject;
	}
	
	public Object getVObject() {
		return vObject;
	}
	
	public VertexPair getPair() {
		return myPair;
	}
	
	public Vertex getU() {
		return u;
	}
	
	public Vertex getV() {
		return v;
	}
		
}