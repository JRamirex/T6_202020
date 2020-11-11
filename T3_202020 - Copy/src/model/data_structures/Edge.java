package model.data_structures;

public class Edge<K extends Comparable<K>, V>  
{
	/**
	 * Peso de ese arco, en este caso el promedio de horas de los ciclistas que han pasado por ese camino
	 */
	double weight;
	
	/**
	 * Cantidad de ciclistas que han pasado por ese camino
	 */
	int count;
	
	/**
	 * Vertice del que sale el arco
	 */
	Vertex<K, V> source;
	
	/**
	 * Vertice al que llega el arco
	 */
	Vertex<K, V> destination;
	
	/**
	 * Crea un vertice segun el origen y el destino que le den 
	 * @param origen vertice del que sale
	 * @param destino vertice al que entra
	 */
	public Edge(Vertex<K, V> origen, Vertex<K, V> destino)
	{
		source = origen;
		destination = destino;
		weight = 0; 
	}
	
	/**
	 * Retorna vertice del source
	 * @return source
	 */
	public Vertex<K, V> getSource()
	{
		return source;
	}
	
	/**
	 * Retorna vertice del destino
	 * @return destination
	 */
	public Vertex<K, V> getDest()
	{
		return destination;
	}
	
	/**
	 * El peso del arco actual
	 * @return weight 
	 */
	public double getWeigth()
	{
		return weight;
	}
	
	/**
	 * Cambia el peso por el valor que entra por parametro
	 * @param newWeight nuevo valor del peso que entra por parametro
	 */
	public void setWeight(double newWeight)
	{
		weight = newWeight; 
	}
	
}
