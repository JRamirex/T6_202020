package model.data_structures;

public class Vertex<K extends Comparable<K>,V>
{
	/**
	 * El id de este vertice
	 */
	K id;
	
	/**
	 * El valor contenido en el vertice
	 */
	V valor;
	
	/**
	 * Tabla que guarda los arcos
	 * Sus llaves son los id del destino diferente al actual
	 * Sus valores son de tipo Edge
	 */
	tablaHashLinearProbing<K, V> tablaArcos;
	
	/**
	 * Tabla que guarda los vertices, sus valores son de tipo Vertex
	 */
	tablaHashLinearProbing<K, V> tablaVertices;
	
	/**
	 * Booleano que muestra si el Vertex actual está o no está marcado
	 */
	boolean marcado; 
	
	
	/**
	 * Constructor del Vertex
	 * @param pId id 
	 * @param pValor los datos que se quieran guardar 
	 */
	public Vertex(K pId, V pValor)
	{
		id = pId;
		valor = pValor;
		tablaArcos = new tablaHashLinearProbing<K, V>(10); 
		tablaVertices = new tablaHashLinearProbing<K, V>(10); 
		marcado = false;
	}
	
	/**
	 * Retorna el id de este vertex
	 * @return id
	 */
	public K getId()
	{
		return id;
	}
	
	/**
	 * El valor del vertex
	 * @return valor
	 */
	public V getInfo()
	{
		return valor;
	}
	
	/**
	 * Retorna el estado de la marca
	 * @return marcado, puede ser true o false
	 */
	public boolean getMark()
	{
		return marcado;
	}
	
	/**
	 * Se agrega un nuevo arco a la tablaArcos
	 * Para hacer las llaves de los arcos el metodo toma como llave el id del vertex
	 * distinto del actual. 
	 * EJEMPLOS: 
	 * -Si el arco sale de este vertex, entonces toma el vertex al que llega
	 * -Si el arco llega a este vertex, entonces toma el vertex del que sale ese arco
	 * Siempre va a tomar el vertex distinto la actual, para esto usa el if 
	 * @param nuevoArco el arco que se desea agregar
	 * Actualiza siempre la lista de vertices
	 */
	public void addEdge(Edge<K,V> nuevoArco)
	{
		if(nuevoArco.getDest()== this)
		{
			tablaArcos.put(nuevoArco.getSource().getId(), (V) nuevoArco);
			actualizarListaVertices(nuevoArco);	
		}
		else
		{
			tablaArcos.put(nuevoArco.getDest().getId(), (V) nuevoArco);
			actualizarListaVertices(nuevoArco);	
		}
	}
	
	/**
	 * Recibe el arco que recien se agrego y toma el vertex pidiendo el id del vertex distinto al actual
	 * @param arco, arco recien agregado
	 */
	public void actualizarListaVertices(Edge<K,V> arco)
	{
		if(arco.getDest()== this)
		{
			tablaVertices.put(arco.getSource().getId(), (V) arco.getSource());
		}
		else
		{
			tablaVertices.put(arco.getDest().getId(), (V) arco.getDest());
		}
	}
	
	/**
	 * Este vertice queda marcado, marcado se vuelve verdadero
	 */
	public void mark()
	{
		marcado = true;
	}
	
	/**
	 * Este vertice queda desmarcado, marcado se vuelve false
	 */
	public void unMark()
	{
		marcado = false;
	}
	
	/**
	 * Retorna cuantos arcos están saliendo del vertex actual
	 * Para esto pide la lista encadenada de los valores de tablaArcos, 
	 * esa lista retorna Edges, a los cuales se les pide dar el vertex del que salen
	 * Si salen del vertex actual aumenta el contador
	 */
	public int outDegrees()
	{
		ListaEncadenadaSinComparable<V> arcos = tablaArcos.valueSet(); 
		int numSalientes = 0; 
		
		for (int i = 0; i < arcos.contarDatos(); i++) 
		{
			Edge<K,V> act = (Edge<K, V>) arcos.darElemento(i); 
			
			if(act.getSource() == this)
			{
				numSalientes++;
			}
		}
	}
	
	/**
	 * Retorna cuantos arcos están llegando al vertex actual
	 * Para esto pide la lista encadenada de los valores de tablaArcos, 
	 * esa lista retorna Edges, a los cuales se les pide dar el vertex al que llegan
	 * Si llegan al verex actual aumenta el contador
	 */
	public int inDegrees()
	{
		ListaEncadenadaSinComparable<V> arcos = tablaArcos.valueSet(); 
		int numSalientes = 0; 
		
		for (int i = 0; i < arcos.contarDatos(); i++) 
		{
			Edge<K,V> act = (Edge<K, V>) arcos.darElemento(i); 
			
			if(act.getDest() == this)
			{
				numSalientes++;
			}
		}
	}
	
	/**
	 * Retorna el arco entre el vertex actual y el vertex por parametro
	 * @param vertex, vertex conectado al actual
	 * @return null si no hay arco
	 * @return Edge, el arco entre las dos estaciones
	 */
	public Edge<K, V> getEdge(K vertex)
	{
		Edge<K, V> rta = (Edge<K, V>) tablaArcos.get(vertex);
		return rta; 
	}
	
	/**
	 * Retorna la tabla de hash que guarda los arcos
	 * @return tablaArcos, tabla con los arcos adyacentes
	 */
	public tablaHashLinearProbing<K, V> edges()
	{
		return tablaArcos; 
	}
	
	/**
	 * Retorna la tabla de hash que guarda los vertices adyacentes
	 * @return tablaVertices, tabla con los vertices adyacentes
	 */
	public tablaHashLinearProbing<K, V> vertices()
	{
		return tablaVertices; 
	}
	
}
