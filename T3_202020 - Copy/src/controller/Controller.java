package controller;

import java.util.Scanner;

import clases.Pelicula;
import model.logic.Modelo;
import view.View;

public class Controller {

	/* Instancia del Modelo*/
	private Modelo modelo;

	/* Instancia de la Vista*/
	private View view;

	/**
	 * Crear la vista y el modelo del proyecto
	 * @param capacidad tamaNo inicial del arreglo
	 */
	public Controller ()
	{
		view = new View();
		modelo = new Modelo(this);
	}

	public void run() 
	{
		Scanner lector = new Scanner(System.in);
		boolean fin = false;
		try {
			while( !fin ){
				view.printMenu();
				int option = lector.nextInt();
				switch(option){

				case 1:
					view.printMessage("--------- \nCargar peliculas");
					modelo = new Modelo(this);
					modelo.cargarHashTable();
					view.printMessage("--------- \nLista de peliculas cargada");
					view.printMessage("--------- \nLa primera pelicula de la base de datos es: ");
					modelo.ImprimirPelicula(0);
					view.printMessage("--------- \nLa ultima pelicula de la base de datos es: ");
					modelo.ImprimirPelicula(modelo.darTamano()-1);
					view.printMessage("---------\nLa base de datos cuenta con " + modelo.darTamano() + " peliculas");						
					break;

				case 2:
					if(!modelo.darCarga()) {
						view.printMessage("No hay peliculas cargadas, por favor cargar las peliculas");
					}
					else 
					{
						view.printMessage("--------- \n Cargando Actores");
						modelo.cargarHashTableActores();
					}
					break;

				case 3:
					if(!modelo.darCarga()) {
						view.printMessage("No hay peliculas cargadas, por favor cargar las peliculas");
					}
					else 
					{
						modelo.cargarHashTableActores();
						view.printMessage("--------- \nSe cargo la lista en formato actores");
						view.printMessage("--------- \nEscriba el nombre del actor que desea conocer");
						String nombreActor = lector.next(); 
						String apellidoActor = lector.next(); 
						view.printMessage("El actor que quieres conocer es " + nombreActor + " " + apellidoActor);
						modelo.darPeliculasActorHash(nombreActor + " " + apellidoActor);
					}
					break;

				case 4:
					if(!modelo.darCarga()) {
						view.printMessage("No hay peliculas cargadas");
					}
					else {
							
					}
					break;
					
				default: 
					view.printMessage("--------- \n Opcion Invalida !! \n---------");
					break;
				}
			}
		}
		catch(Exception e) {
			view.printMessage("--------- \n Error!! \n---------");
			e.printStackTrace();
			run();
		}
	}
	
	public void ImprimirPelicula(Pelicula aImprimir) {
		if (aImprimir != null) {
			view.printMessage("----------");
			view.printMessage("ID:"+aImprimir.darIdentificador());
			view.printMessage("Nombre:"+aImprimir.darNombrePelicula());
			view.printMessage("Votos:"+(int)aImprimir.darCantidadVotos());
			view.printMessage("Promedio de Votacion:"+aImprimir.darVotosPromedio());
			view.printMessage("Genero:"+aImprimir.darGenero());
			view.printMessage("Actores:");
			String[] actores = aImprimir.darListaNombresActores();
			for (int i =0;i<5;i++) {
				view.printMessage(actores[i]);
			}
		}
		else {
			view.printMessage("Ocurrio un errror, revise que el indice dado sea menor al tamaño de la lista");
		}
	}
}
