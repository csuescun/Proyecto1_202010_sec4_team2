package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import model.data_structures.Queue;
import model.logic.Comparendo;
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
		modelo = new Modelo();
	}

	public void run() 
	{
		Scanner lector = new Scanner(System.in);
		boolean fin = false;
		String dato = "";
		String respuesta = "";

		while( !fin ){
			view.printMenu();

			int option = lector.nextInt();
			switch(option){
			case 1:
				modelo = new Modelo();

				long start = System.currentTimeMillis();
				modelo.cargarDatos(); 
				long end = System.currentTimeMillis();


				view.printMessage("Datos de comparendos cargados.");
				view.printMessage("Numero total de comparendos " + modelo.darTamanoCola() + "\n---------");		
				view.printMessage("Tiempo de carga (seg): " + (end-start)/1000.0 + "\n---------");
				view.printMessage("El comparendo con mayor OBJECTID es:");
				view.printMessage(modelo.darMayorComparendo().datosCluster() + "\n--------");
				view.printMessage("La zona Minimax es:  (" + modelo.darMenorLatitud() + ", " + modelo.darMenorLongitud() + ") y (" + modelo.darMayorLatitud() + " , " + modelo.darMayorLongitud() + ") " +"\n");
				break;				

			case 2:
				view.printMessage("-------"); 
				lector.close();
				fin = true;
				break;	

			case 3:

				Comparendo[] copia1 = modelo.copiarDatos();

				modelo.shellSortPorInfraccion(copia1);

				String input = lector.next();

				for(int i = copia1.length-1;i>=0;i--)
				{
					if(copia1[i].darSimpleDate().equals(input))
						view.printMessage(copia1[i].datosCluster());
				}

				break;

			case 4:
				break;


			case 5:
				view.printMessage("Ingrese el codigo de la infraccion que desea buscar");
				String codigo = lector.next();

				Comparendo buscado = modelo.darPrimerComparendoPorInfraccion(codigo);

				if(buscado != null)
				{
					view.printMessage("El primer comparendo encontrado fue:");
					view.printMessage(buscado.datosCluster());
				}

				else
				{
					view.printMessage("No se encontró ningún comparendo con ese código");
				}

				break;

			case 6:
				view.printMessage("Ingrese el codigo de la infraccion que desea buscar");
				String codigo2 = lector.next();

				Comparable[] buscados = modelo.darComparendosPorInfraccion(codigo2);

				for(int i = 0; i < buscados.length; i++)
				{
					Comparendo actual = (Comparendo) buscados[i] ;
					view.printMessage(actual.datosCluster());
				}

				break;

			case 7:

				ArrayList<String[]> porTipo = modelo.darComparendosPorTipo();

				view.printMessage("Infracción            |Particular            |Público");

				for(int i = 0; i < porTipo.size(); i++)
				{
					view.printMessage(porTipo.get(i)[0] + "                   |" + porTipo.get(i)[1] + "                  |" + porTipo.get(i)[2]);
				}

				view.printMessage("\n----------");

				break;

			case 8: 
				try
				{
					view.printMessage("Ingrese la localidad que desea buscar:");
					view.printMessage("Puede escoger entre Santa Fe, Barrios Unidos, Ciudad Bolivar, San Cristobal, Bogota, Rafael Uribe, Puente Aranda, Antonio Nariño" );
					String localidad = lector.next();


					view.printMessage("Ingrese la fecha de inicio que desea buscar (yyyy/MM/dd):");
					String fechaInicial = lector.next();

					view.printMessage("Ingrese la fecha de fin que desea buscar (yyyy/MM/dd):");
					String fechaFinal = lector.next();

					SimpleDateFormat parser=new SimpleDateFormat("yyyy/MM/dd");



					Date fechaI= parser.parse(fechaInicial);
					Date fechaF = parser.parse(fechaFinal);



					ArrayList<String[]> porFecha = modelo.numeroComparendosPorLocalidadYFecha(localidad, fechaI, fechaF);

					view.printMessage("Infraccion | # Comparendos"  );

					for(int i = 0; i < porFecha.size(); i++)
					{
						view.printMessage(porFecha.get(i)[0] + "|" + porFecha.get(i)[1]);
					}
				}

				catch(ParseException e)
				{
					view.printMessage(e.getMessage());
				}


				break; 

			case 9:
				try
				{

					view.printMessage("Ingrese el número de infracciones que desea buscar:");
					int N = lector.nextInt();

					view.printMessage("Ingrese la fecha de inicio que desea buscar (yyyy/MM/dd):");
					String fechaInicial = lector.next();

					view.printMessage("Ingrese la fecha de fin que desea buscar (yyyy/MM/dd):");
					String fechaFinal = lector.next();

					SimpleDateFormat parser=new SimpleDateFormat("yyyy/MM/dd");

					Date fechaI= parser.parse(fechaInicial);
					Date fechaF = parser.parse(fechaFinal);

					ArrayList<String[]> respuestas = modelo.darNComparendosPorFecha(N, fechaI, fechaF);

					view.printMessage("Infraccion | # Comparendos"  );

					for(int i = 0; i < respuestas.size(); i++)
					{
						view.printMessage(respuestas.get(i)[0] + "|" + respuestas.get(i)[1]);
					}

				}

				catch(ParseException e)
				{
					view.printMessage(e.getMessage());
				}


				break;

			case 10:
				break;

			case 11: 

				view.printMessage("--------- \n Hasta pronto !! \n---------"); 
				lector.close();
				fin = true;
				break;	


			default: 
				view.printMessage("--------- \n Opcion Invalida !! \n---------");
				break;
			}
		}

	}	
}
