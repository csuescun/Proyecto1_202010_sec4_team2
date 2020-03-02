package controller;

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
				
				break;
				
			case 6:
				break;
				
			case 7:
				
				break;
				
			case 8: 
				break; 
				
			case 9:
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
