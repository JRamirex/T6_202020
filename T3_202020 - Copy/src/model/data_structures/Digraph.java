package model.data_structures;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import model.data_structures.*;

public class Digraph<K extends Comparable <K>, V extends Comparable<V>> {
    private static final String NEWLINE = System.getProperty("line.separator");

    private int vert;           // number of vertices in this digraph
    private int E;                 // number of edges in this digraph
    private Hashtable<K, Vertex> vertex;   // adj[v] = adjacency list for vertex v
    private Hashtable<K, Integer> indegree;        // indegree[v] = indegree of vertex v
    private Hashtable<K, ArrayList<K>> adj;
    
    /**
     * Initializes an empty digraph with <em>V</em> vertices.
     *
     * @param  V the number of vertices
     * @throws IllegalArgumentException if {@code V < 0}
     */
    public Digraph(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices in a Digraph must be nonnegative");
        this.vert = V;
        this.E = 0;
        indegree = new Hashtable<>();
        vertex = new Hashtable<>();
        adj = new Hashtable<>();

    }
    
    public Digraph ()
    {
    	this.vert = 0;
    	this.E = 0;
        indegree = new Hashtable<>();
        vertex = new Hashtable<>();
        adj = new Hashtable<>();
    }

        
    /**
     * Returns the number of vertices in this digraph.
     *
     * @return the number of vertices in this digraph
     */
    public int numVertices() {
        return vertex.size();
    }

    /**
     * Returns the number of edges in this digraph.
     *
     * @return the number of edges in this digraph
     */
    public int numEdges() {
        return E;
    }
    
    public void addVertex(K id, V value)
    {
    	if(!vertex.contains(id))
    	{
    		Vertex v = new Vertex(id, value);
        	vertex.put(id, v);
        	vert++;
    	}
    }

    /**
     * Adds the directed edge v→w to this digraph.
     *
     * @param  v the tail vertex
     * @param  w the head vertex
     * @throws IllegalArgumentException unless both {@code 0 <= v < V} and {@code 0 <= w < V}
     */
    public void addEdge(K source, K dest, double weight) {
    	
    	Vertex sour = (Vertex)vertex.get(source);
    	Vertex to = (Vertex)vertex.get(dest);
    	Edge e = new Edge(sour , to);
    	e.setWeight(weight);
    	if(to.hasEdge(source) || sour.hasEdge(dest) || to.hasEdge(dest) || sour.hasEdge(source))
    	{
    		Edge e1 =to.getEdge(source);
    		double prom = (e.getWeigth() + e1.getWeigth())/2;
    		e.setWeight(prom);
    		E--;
    	}
    	sour.addEdge(e);
    	to.addEdge(e);

    	if(adj.contains(source))
    	{
    		ArrayList tmp = adj.get(source);
    		tmp.add(dest);
    		adj.put(source, tmp);
    	}
    	else
    	{
    		ArrayList tmp = new ArrayList<>();
    		tmp.add(dest);
    		adj.put(source, tmp);
    	}

    	if(indegree.contains(dest))
    	{
    		int tmp = indegree.get(dest);
    		tmp++;
    		indegree.put(dest, tmp);
    	}
    	else
    	{
    		indegree.put(dest, 1);
    	}


    	E++;
    }

    /**
     * Returns the vertices adjacent from vertex {@code v} in this digraph.
     *
     * @param  v the vertex
     * @return the vertices adjacent from vertex {@code v} in this digraph, as an iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<K> adj(int v) {
        return adj.get(v);
    }

    /**
     * Returns the number of directed edges incident from vertex {@code v}.
     * This is known as the <em>outdegree</em> of vertex {@code v}.
     *
     * @param  v the vertex
     * @return the outdegree of vertex {@code v}               
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int outdegree(K v) {
        return adj.get(v).size();
    }

    /**
     * Returns the number of directed edges incident to vertex {@code v}.
     * This is known as the <em>indegree</em> of vertex {@code v}.
     *
     * @param  v the vertex
     * @return the indegree of vertex {@code v}               
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int indegree(int v) {
        return indegree.get(v);
    }

  

    public Vertex getVertext(K id)
    {
    	return vertex.get(id);
    }
    
    public Edge getEdge(K idS, K idD)
    {
    	Edge rta = null;
    
    	Vertex to = vertex.get(idS);
    	for(Object e: to.edgess())
    	{
    		Edge ed = (Edge) e;
    		if(ed.getDest().getId() == idD)
    			rta = ed;
    	}
    	return rta;
    }
    
    public List adjacentEdges(K id)
    {
    	List rta = new ArrayList();
    	Vertex v = vertex.get(id);
    	for(Object o: v.edgess())
    	{
    		Edge e = (Edge) o;
    		if(e.getSource().getId() == id)
    			rta.add(e);
    	}
    	return rta;
    }
    
    public List adjacentVertex(K id)
    {
    	List rta = new ArrayList();
    	
    	for(Object o: adj.get(id))
    	{
    		Vertex v = vertex.get(o);
    		rta.add(v);
    	}
    	
    	return rta;
    }
    
    public Iterable<Vertex> vertices()
    {
    	return vertex.values();
    }
    
    public Set edges()
    {
    	Set rta = new HashSet();
    	
    	for(Vertex v : vertex.values())
    	{
    		for(Object o: v.edgess())
        	{
        		Edge e = (Edge) o;
        		rta.add(e);
        	}
    	}
    	
    	return rta;
    }

    public boolean containsVertex(K id)
    {
    	return vertex.contains(id);
    }

}
