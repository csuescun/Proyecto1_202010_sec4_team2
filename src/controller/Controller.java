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
				view.printMessage("Escriba la localidad deseada"); 

				String inlocalidad = lector.next();

				view.printMessage(modelo.darPrimeroLocalidad(inlocalidad).datosCluster());

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
				try{

					view.printMessage("Escriba la primera fecha");
					String primeraf = lector.next();

					view.printMessage("Escriba la segunda fecha");
					String segundaf = lector.next();


					SimpleDateFormat format = new SimpleDateFormat("yyy/MM/dd"); 
					Date f1 = format.parse(primeraf);
					Date f2 = format.parse(segundaf);

					ArrayList<String[]> dosFechas = modelo.numeroComparendosDosFechas(f1, f2);

					view.printMessage("Infraccion     |"+primeraf+"     |"+segundaf);

					for(int i= 0;i<dosFechas.size();i++)
					{
						view.printMessage(dosFechas.get(i)[0] + "      |" + dosFechas.get(i)[1] + "      |" + dosFechas.get(i)[2]);
					}
				}
				catch(ParseException e)
				{
					view.printMessage(e.getMessage());
				}
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

				ArrayList<String[]> totallocal = modelo.graficaASCII();

				for(int i = 0;i<totallocal.size();i++)
				{
					String nombre = totallocal.get(i)[0];
					if(nombre.length()>16)
					{
						int num = 16 - nombre.length();

						for(int l = 0;l<num;l++)
						{
							nombre+="-";
						}

					}
					int rep = Integer.parseInt(totallocal.get(i)[1]);
					int asteriscos = rep/50;
					String cadena = "";
					for(int j= 0;j<asteriscos;j++)
					{
						cadena+="*";
					}
					if(rep%50!=0)
					{
						cadena+="*";
					}


					view.printMessage(nombre+"|"+cadena);
					view.printMessage(""+totallocal.size());
				}

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
