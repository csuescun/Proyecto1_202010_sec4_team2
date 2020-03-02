package view;

import model.logic.Modelo;

public class View 
{
	/**
	 * Metodo constructor
	 */
	public View()
	{

	}

	public void printMenu()
	{
		System.out.println("1. Cargar los datos sobre comparendos");

		System.out.println("2. Consultar el primer comparendo por localidad dada");
		System.out.println("3. Consultar comparendos con una fecha dada");
		System.out.println("4. Comparar cantidad de comparendos por código de infracción en dos fechas diferentes");
		System.out.println("5. Consultar el primer comparendo por código de infracción dado");
		System.out.println("6. Consultar comparendos dado un código de infracción");
		System.out.println("7. Comparar comparendos de tipo “Particular” y “Público” para todos los códigos de infracción");
		System.out.println("8. Consultar el número de comparendos por tipo de infracción para una localidad dada y para un periodo de tiempo dado");
		System.out.println("9. Consultar los N códigos de infracción con más comparendos para un periodo de tiempo dado");
		System.out.println("10. Generar gráfica ASCII del total de comparendos para cada localidad");
		System.out.println("11. Salir");
		System.out.println("Dar el numero de opcion a resolver, luego oprimir tecla Return: (e.g., 1):");

	}

	public void printMessage(String mensaje) {

		System.out.println(mensaje);
	}		


}
