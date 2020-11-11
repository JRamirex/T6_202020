package model.logic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.opencsv.bean.CsvToBeanBuilder;

import clases.Hash;
import clases.Pelicula;
import clases.ShellSort;
import controller.Controller;
import model.data_structures.ArregloDinamico;
import model.data_structures.IListaEncadenada;
import model.data_structures.ListaEncadenada;
import model.data_structures.ListaEncadenada.Nodo;
import model.data_structures.ListaEncadenadaSinComparable;
import model.data_structures.NodoHash;
import model.data_structures.TablaHashSeparateChaining;
import model.data_structures.TablaSimbolos;
import model.data_structures.tablaHashLinearProbing;

/**
 * Definicion del modelo del mundo
 *
 */
public class Modelo {
	
	/**
	 * Atributos del modelo del mundo
	 */
	private Hash hash;
	private Controller controller;
	private ShellSort shellsort;
	private int tamañoLista = 2017;
	private int tamañoListaActores = 400009;
	private int tamañoSiguientePrimo;
	private boolean hayPeliculas;
	private IListaEncadenada datos;
	private TablaSimbolos linearProbing, separateChaining, separateChainingActores;
	
	/**
	 * Constructor del modelo del mundo con capacidad predefinida
	 */
	public Modelo(Controller pController)
	{
		hayPeliculas = false;
		controller = pController;
	}
	
	/**
	 * Servicio de consulta de numero de elementos presentes en el modelo 
	 * @return numero de elementos presentes en el modelo
	 */
	public int darTamano()
	{
		return datos.contarDatos();
	}
	
	/**
	 * Servicio de consulta si hay una base de datos cargada 
	 * @return true si hay una base de datos cargada, false de lo contrario
	 */
	public boolean darCarga() {
		return hayPeliculas;
	}

	/**
	 * Requerimiento de agregar dato
	 * @param dato a agregar
	 */
	public void agregar(String dato)
	{	
		datos.insert(dato);
	}
	

	public void cargarLista() {
		datos = new ListaEncadenada();
		String archivo = "./data/SmallMoviesDetailsCleaned.csv";
		String archivo2 = "./data/MoviesCastingRaw-small.csv";
		String linea = "";
		String linea2 = "";
		try 
		{
			BufferedReader br = new BufferedReader(new FileReader(archivo));
			br.readLine();
			BufferedReader br2 = new BufferedReader(new FileReader(archivo2));
			br2.readLine();
			while((linea = br.readLine()) !=null && (linea2 = br2.readLine()) !=null)
			{
				String[] valores = linea.split(";");
				String[] valores2 = linea2.split(";"); 
				if(valores[0].equals(valores2[0]))
				{
					String[] fechaProduccion = valores[10].split("/");
					String añoProduccion = fechaProduccion[2];
					String llave = (valores[8]+"," + añoProduccion);
					Pelicula pelicula = new Pelicula((Integer.parseInt(valores[0])), ((String)valores[5]), valores[2], valores2[12], Float.parseFloat(valores[18]), Float.parseFloat(valores[17]),valores2[1],valores2[3],valores2[5],valores2[7],valores2[9], valores[8], añoProduccion);
					((ListaEncadenada) datos).insert(pelicula);
				}
			} 
			hayPeliculas = true;
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void cargarArreglo()
	{
		datos = new ArregloDinamico(10);
		String archivo = "./data/SmallMoviesDetailsCleaned.csv";
		String archivo2 = "./data/MoviesCastingRaw-small.csv";
		String linea = "";
		String linea2 = "";
		try 
		{
			BufferedReader br = new BufferedReader(new FileReader(archivo));
			br.readLine();
			BufferedReader br2 = new BufferedReader(new FileReader(archivo2));
			br2.readLine();
			while((linea = br.readLine()) !=null && (linea2 = br2.readLine()) !=null)
			{
				String[] valores = linea.split(";");
				String[] valores2 = linea2.split(";"); 
				if(valores[0].equals(valores2[0]))
				{
					String[] fechaProduccion = valores[10].split("/");
					String añoProduccion = fechaProduccion[2];
					String llave = (valores[8]+"," + añoProduccion);
					Pelicula agregada = new Pelicula((Integer.parseInt(valores[0])), ((String)valores[5]), valores[2], valores2[12], Float.parseFloat(valores[18]), Float.parseFloat(valores[17]),valores2[1],valores2[3],valores2[5],valores2[7],valores2[9], valores[8], añoProduccion);
					agregada.ordenarActores();
					datos.agregarAlFinal(agregada);
				}
			} 
			hayPeliculas = true;
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * Funcion de cargar una base de datos de peliculas como una hash table
	 */
	public void cargarHashTable() 
	{
		datos = new ArregloDinamico(10);
		separateChaining = new TablaHashSeparateChaining<Hash, Pelicula>(tamañoLista);
		linearProbing = new tablaHashLinearProbing<>(tamañoLista);
		String archivo = "./data/SmallMoviesDetailsCleaned.csv";
		String archivo2 = "./data/MoviesCastingRaw-small.csv";
		String linea = "";
		String linea2 = "";
		try 
		{
			BufferedReader br = new BufferedReader(new FileReader(archivo));
			br.readLine();
			BufferedReader br2 = new BufferedReader(new FileReader(archivo2));
			br2.readLine();
			while((linea = br.readLine()) !=null && (linea2 = br2.readLine()) !=null)
			{
				String[] valores = linea.split(";");
				String[] valores2 = linea2.split(";"); 
				if(valores[0].equals(valores2[0]))
				{
					String[] fechaProduccion = valores[10].split("/");
					String añoProduccion = fechaProduccion[2];
					String llave = (valores[8]+"," + añoProduccion);

					Pelicula pelicula = new Pelicula((Integer.parseInt(valores[0])), ((String)valores[5]), valores[2], valores2[12], Float.parseFloat(valores[18]), Float.parseFloat(valores[17]),valores2[1],valores2[3],valores2[5],valores2[7],valores2[9],valores[8],añoProduccion);
					Hash key = new Hash(llave, tamañoLista); 
					ListaEncadenadaSinComparable<Pelicula> listaConLaPeli = new ListaEncadenadaSinComparable<Pelicula>();
					listaConLaPeli.agregarAlPrincipio(pelicula);
					
					separateChaining.put(key,pelicula);
			
					datos.agregarAlFinal(pelicula);
				}
			} 
			
			hayPeliculas = true; 
			
			ListaEncadenadaSinComparable<Hash> listaHash = new ListaEncadenadaSinComparable<Hash>();
			listaHash = separateChaining.keySet();
			for (int i = 0; i < listaHash.contarDatos(); i++) 
			{
				Hash act = listaHash.darElemento(i); 
				System.out.print("|| La llave en pos " + i + " es la llave: "  + act.darLlave());
			}

		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void cargarHashTableActores() 
	{
		separateChainingActores = new TablaHashSeparateChaining<Hash, Pelicula>(tamañoListaActores);
		String archivo = "./data/SmallMoviesDetailsCleaned.csv";
		String archivo2 = "./data/MoviesCastingRaw-small.csv";
		String linea = "";
		String linea2 = "";
		try 
		{
			BufferedReader br = new BufferedReader(new FileReader(archivo));
			br.readLine();
			BufferedReader br2 = new BufferedReader(new FileReader(archivo2));
			br2.readLine();
			while((linea = br.readLine()) !=null && (linea2 = br2.readLine()) !=null)
			{
				String[] valores = linea.split(";");
				String[] valores2 = linea2.split(";"); 
				if(valores[0].equals(valores2[0]))
				{
					String[] fechaProduccion = valores[10].split("/");
					String añoProduccion = fechaProduccion[2];
					String llave1 = (valores2[1]);
					String llave2 = (valores2[3]);
					String llave3 = (valores2[5]);
					String llave4 = (valores2[7]);
					String llave5 = (valores2[9]);
					
					Pelicula pelicula = new Pelicula((Integer.parseInt(valores[0])), ((String)valores[5]), valores[2], valores2[12], Float.parseFloat(valores[18]), Float.parseFloat(valores[17]),valores2[1],valores2[3],valores2[5],valores2[7],valores2[9],valores[8],añoProduccion);
					Hash key1 = new Hash(llave1, tamañoListaActores); 
					Hash key2 = new Hash(llave2, tamañoListaActores); 
					Hash key3 = new Hash(llave3, tamañoListaActores); 
					Hash key4 = new Hash(llave4, tamañoListaActores); 
					Hash key5 = new Hash(llave5, tamañoListaActores); 
					
					ListaEncadenadaSinComparable<Pelicula> listaConLaPeli = new ListaEncadenadaSinComparable<Pelicula>();
					listaConLaPeli.agregarAlPrincipio(pelicula);
					
					separateChainingActores.put(key1, pelicula);
					separateChainingActores.put(key2, pelicula);
					separateChainingActores.put(key3, pelicula);
					separateChainingActores.put(key4, pelicula);
					separateChainingActores.put(key5, pelicula);
					
					System.out.print(" || "+ llave1 + " || ");
				}
			} 
			
			hayPeliculas = true; 

		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Esta funcion ordena por conteo las peliculas
	 * @param tipo Tipo de ordenamiento, true para las peliculas mas votadas, false para las menos votadas
	 */
	public void ShellSortCount(boolean tipo) {
		Comparable[] peliculas = datos.elementos();
		shellsort.sortCount(peliculas,datos.contarDatos(),tipo);
		for (short i=0;i<10;i++) {
			ImprimirPelicula((Pelicula)peliculas[i]);
		}
	}
	
	/**
	 * Esta funcion ordena por conteo las peliculas
	 * @param tipo Tipo de ordenamiento, true para las peliculas mas votadas, false para las menos votadas
	 */
	public void ShellSortAverage(boolean tipo) {
		Comparable[] peliculas = datos.elementos();
		shellsort.sortAverage(peliculas,datos.contarDatos(),tipo);
		for (short i=0;i<10;i++) {
			ImprimirPelicula((Pelicula)peliculas[i]);
		}
	}
	
	public void ImprimirPelicula(int index) {
		controller.ImprimirPelicula((Pelicula)datos.darElemento(index));
	}
	
	public void ImprimirPelicula(Pelicula aImprimir) {
		controller.ImprimirPelicula(aImprimir);
	}
	
	public void darPeliculasActorHash(String pActor)
	{
		ArregloDinamico<String> pelis = new ArregloDinamico<String>(10);
		ArregloDinamico<String> directores = new ArregloDinamico<String>(10);
		String directorMasRepetido = null; 
		int numeroDirectoMasRepetido = 0; 
		float promedio = 0; 
		
		Hash actor = new Hash(pActor, tamañoListaActores);
		ListaEncadenadaSinComparable<Pelicula> listaPelisActor = ((TablaHashSeparateChaining<Hash, Pelicula>) separateChaining).getLista(actor);
		
		
		for (int i = 0; i < listaPelisActor.contarDatos(); i++) 
		{
			Pelicula act = (Pelicula) listaPelisActor.darElemento(i); 
			if(act.estaElActorEnLista(pActor)==true)
			{
				pelis.agregarAlFinal(act.darNombrePelicula());
				directores.agregarAlFinal(act.darNombreDirector());
				promedio+= act.darVotosPromedio(); 
			}
		}
		
		for (int i = 0; i < directores.contarDatos(); i++) 
		{
			int cantidadDeVecesRepetido = 1; 
			for (int j = i+1; j < directores.contarDatos(); j++) 
			{
				if(directores.darElemento(i).equalsIgnoreCase(directores.darElemento(j)))
				{
					cantidadDeVecesRepetido++; 
				}
			}
			if(cantidadDeVecesRepetido> numeroDirectoMasRepetido)
			{
				numeroDirectoMasRepetido = cantidadDeVecesRepetido;
				directorMasRepetido = directores.darElemento(i);
			}
		} 
		
		if (pelis.contarDatos()>0){
			System.out.println("----------");
			System.out.println("La cantidad de peliculas en las que ha actuado es de " + pelis.contarDatos());
			System.out.println("Las películas en las que actua son: ");
			for(int i=0;i<pelis.contarDatos();i++) {
				System.out.println(pelis.darElemento(i));	
			}
			System.out.println("----------");
			System.out.println("El promedio de votación de las peliculas en las que actua es de " + promedio/pelis.contarDatos());
			System.out.println("El director con el qué se han hecho más colaboraciones es " + directorMasRepetido);
		}
		else {
			System.out.println("----------");
			System.out.println("La persona dada no ha actuado en ninguna pelicula");
		}
	}
	
	public void darPeliculasGenero(String genero)
	{
		ArregloDinamico<String> pelis = new ArregloDinamico<String>(1000);
		float promedio = 0; 
		
		for (int i = 0; i < datos.contarDatos(); i++) 
		{
			Pelicula act = (Pelicula) datos.darElemento(i);
			if(act.darGenero().equalsIgnoreCase(genero))
			{
				pelis.agregarAlFinal(act.darNombrePelicula());
				promedio += act.darVotosPromedio(); 
			}
		}
		promedio = promedio/pelis.contarDatos(); 
		System.out.println("----------");
		System.out.println("Hay " + pelis.contarDatos() + " películas de ese género");
		if (pelis.contarDatos()>0){
			System.out.println("Las películas de ese género son: ");
			for(int i=0;i<pelis.contarDatos();i++) {
				System.out.println(pelis.darElemento(i));	
			}
			System.out.println("----------");
			System.out.println("El promedio de votación en esas peliculas es de " + promedio);
		}
	}
	
	public void darPeliculasActor(String pActor)
	{
		ArregloDinamico<String> pelis = new ArregloDinamico<String>(10);
		ArregloDinamico<String> directores = new ArregloDinamico<String>(10);
		String directorMasRepetido = null; 
		int numeroDirectoMasRepetido = 0; 
		float promedio = 0; 
		
		for (int i = 0; i < datos.contarDatos(); i++) 
		{
			Pelicula act = (Pelicula) datos.darElemento(i); 
			if(act.estaElActorEnLista(pActor)==true)
			{
				pelis.agregarAlFinal(act.darNombrePelicula());
				directores.agregarAlFinal(act.darNombreDirector());
				promedio+= act.darVotosPromedio(); 
			}
		};
		
		for (int i = 0; i < directores.contarDatos(); i++) 
		{
			int cantidadDeVecesRepetido = 1; 
			for (int j = i+1; j < directores.contarDatos(); j++) 
			{
				if(directores.darElemento(i).equalsIgnoreCase(directores.darElemento(j)))
				{
					cantidadDeVecesRepetido++; 
				}
			}
			if(cantidadDeVecesRepetido> numeroDirectoMasRepetido)
			{
				numeroDirectoMasRepetido = cantidadDeVecesRepetido;
				directorMasRepetido = directores.darElemento(i);
			}
		} 
		
		
		if (pelis.contarDatos()>0){
			System.out.println("----------");
			System.out.println("La cantidad de peliculas en las que ha actuado es de " + pelis.contarDatos());
			System.out.println("Las películas en las que actua son: ");
			for(int i=0;i<pelis.contarDatos();i++) {
				System.out.println(pelis.darElemento(i));	
			}
			System.out.println("----------");
			System.out.println("El promedio de votación de las peliculas en las que actua es de " + promedio/pelis.contarDatos());
			System.out.println("El director con el qué se han hecho más colaboraciones es " + directorMasRepetido);
		}
		else {
			System.out.println("----------");
			System.out.println("La persona dada no ha actuado en ninguna pelicula");
		}
	}
	
	public void darPeliculasDirector(String pDirector) {
		ArregloDinamico<String> pelis = new ArregloDinamico<String>(10);
		float promedio = 0; 
		for (int i = 0; i < datos.contarDatos(); i++) 
		{
			Pelicula act = (Pelicula) datos.darElemento(i); 
			if(act.darNombreDirector().compareToIgnoreCase(pDirector)==0)
			{
				pelis.agregarAlFinal(act.darNombrePelicula());
				promedio+= act.darVotosPromedio(); 
			}
		}
		if (pelis.contarDatos()>0){
			System.out.println("----------");
			System.out.println("El director ha dirigido " + pelis.contarDatos()+ " peliculas.");
			System.out.println("Las películas que ha dirigido son: ");
			for(int i=0;i<pelis.contarDatos();i++) {
				System.out.println(pelis.darElemento(i));	
			}
			System.out.println("----------");
			System.out.println("El promedio de votación de las peliculas en las que ha dirigido es de " + promedio/pelis.contarDatos());
		}
		else {
			System.out.println("----------");
			System.out.println("La persona dada no ha dirigido ninguna pelicula");
		}
	}
	
}
