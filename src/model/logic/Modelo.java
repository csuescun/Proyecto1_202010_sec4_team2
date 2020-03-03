package model.logic;



import model.data_structures.Queue;
import model.data_structures.Node;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;



import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;


/**
 * Definicion del modelo del mundo
 *
 */
public class Modelo 
{

	public static String PATH = "./data/comparendos_dei_2018.geojson";

	/**
	 * Atributos del modelo del mundo
	 */

	private Queue<Comparendo> datos;

	/**
	 * 
	 */

	private Comparendo mayorComparendo;

	/**
	 * Zona minimax
	 */

	private double menorLongitud; 

	private double menorLatitud; 

	private double mayorLongitud; 

	private double mayorLatitud; 


	/**
	 * Constructor del modelo del mundo con capacidad predefinida
	 */

	public Modelo()
	{
		datos = new Queue<Comparendo>();
		menorLongitud = 100;
		menorLatitud = 100;

		mayorLongitud = -100;
		mayorLatitud = -100;
	}


	public int darTamanoCola()
	{
		return datos.darTamano();
	}

	public Queue<Comparendo> repetidos()
	{
		if(datos==null)
		{
			cargarDatos();
		}

		Queue<Comparendo> mayor = new Queue<Comparendo>();
		Queue<Comparendo> temp = new Queue<Comparendo>();
		String tipo = datos.darPrimerElemento().darInfraccion();

		for(Comparendo c: datos)
		{
			datos.dequeue();
			if(tipo.equals(c.darInfraccion()))
			{	
				temp.enqueue(c);
			}
			else
			{
				tipo = c.darInfraccion();
				temp = new Queue<Comparendo>() ;
				temp.enqueue(c);
			}
			if(temp.darTamano()>mayor.darTamano())
			{
				mayor = temp;
			}
		}

		return mayor;
	}

	public void cargarDatos() 

	{
		JsonReader reader;

		try {

			int mayorID  = 0;
			reader = new JsonReader(new FileReader(PATH));
			JsonParser jsonp = new JsonParser();

			JsonElement elem = jsonp.parse(reader);
			JsonArray e2 = elem.getAsJsonObject().get("features").getAsJsonArray();


			SimpleDateFormat parser=new SimpleDateFormat("yyyy/MM/dd");

			for(JsonElement e: e2) {
				int OBJECTID = e.getAsJsonObject().get("properties").getAsJsonObject().get("OBJECTID").getAsInt();

				String s = e.getAsJsonObject().get("properties").getAsJsonObject().get("FECHA_HORA").getAsString();	
				Date FECHA_HORA = parser.parse(s); 

				String MEDIO_DETE = e.getAsJsonObject().get("properties").getAsJsonObject().get("MEDIO_DETE").getAsString();
				String CLASE_VEHI = e.getAsJsonObject().get("properties").getAsJsonObject().get("CLASE_VEHI").getAsString();
				String TIPO_SERVI = e.getAsJsonObject().get("properties").getAsJsonObject().get("TIPO_SERVI").getAsString();
				String INFRACCION = e.getAsJsonObject().get("properties").getAsJsonObject().get("INFRACCION").getAsString();
				String DES_INFRAC = e.getAsJsonObject().get("properties").getAsJsonObject().get("DES_INFRAC").getAsString();	
				String LOCALIDAD = e.getAsJsonObject().get("properties").getAsJsonObject().get("LOCALIDAD").getAsString();

				double longitud = e.getAsJsonObject().get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray()
						.get(0).getAsDouble();

				double latitud = e.getAsJsonObject().get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray()
						.get(1).getAsDouble();

				Comparendo c = new Comparendo(OBJECTID, FECHA_HORA, MEDIO_DETE, CLASE_VEHI, TIPO_SERVI, INFRACCION,DES_INFRAC, LOCALIDAD, longitud, latitud);
				datos.enqueue(c);

				if(latitud < menorLatitud)
				{
					menorLatitud = latitud;
				}

				if(latitud > mayorLatitud)
				{
					mayorLatitud = latitud;
				}

				if(longitud < menorLongitud)
				{
					menorLongitud = longitud;
				}

				if(longitud > mayorLongitud)
				{
					mayorLongitud = longitud;
				}

				if(OBJECTID > mayorID)
				{
					mayorComparendo = c;
				}
			}

		} catch (FileNotFoundException | ParseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}


	}

	public Comparendo darPrimeroCola()
	{
		return datos.darPrimerElemento();
	}

	public Comparendo darUltimoCola()
	{
		return datos.darUltimoElemento();
	}

	public double darMenorLongitud()
	{
		return menorLongitud;
	}


	public double darMenorLatitud()
	{
		return menorLatitud;
	}

	public double darMayorLongitud()
	{
		return mayorLongitud;
	}

	public double darMayorLatitud()
	{
		return mayorLatitud;
	}

	public Comparendo darMayorComparendo()
	{
		return mayorComparendo;
	}


	//Copiar comparendos

	public Comparendo[] copiarDatos()
	{
		Comparendo[] comparendos = new Comparendo[datos.darTamano()];
		int i = 0;
		for(Comparendo e : datos)
		{
			comparendos[i] = e;
			i++;
		}
		return comparendos;
	}


	//Copiar un arreglo dado

	public Comparendo[] copiarArreglo(Queue<Comparendo> arreglo)
	{
		Comparendo[] comparendos = new Comparendo[arreglo.darTamano()];
		int i = 0;
		for(Comparendo e : arreglo)
		{
			comparendos[i] = e;
			i++;
		}
		return comparendos;
	}


	// Ordenamientos y comparadores

	// Comparador por fecha
	public static  boolean less(Comparable a, Comparable b)
	{
		return a.compareTo(b)<0;
	}

	//Comparador por localidad
	public static boolean lessPorLocalidad(Comparendo c1, Comparendo c2)
	{
		return c1.compararPorLocalidad(c2) <0;
	}


	//Comparador por infracción

	public static boolean lessPorInfraccion(Comparendo c1, Comparendo c2)
	{
		return c1.compararPorInfraccion(c2)<0;
	}

	//Shell por fecha
	public void shellSortPorFecha(Comparable[] a)
	{
		int N = a.length;
		int h = 1;
		while (h < N/3)
			h = 3*h + 1;
		while (h >= 1) {
			for (int i = h; i < N; i++) {
				for (int j = i; j >= h && less(a[j], a[j-h]); j -= h)
					exch(a, j, j-h);
			}
			h = h/3;
		}

	}

	public static void exch(Comparable[] a, int i, int j)
	{
		Comparable temporal = a[i];
		a[i] = a[j];
		a[j] = temporal;		
	}

	//Shell por localidad

	public void shellSortPorLocalidad(Comparable[] a)
	{
		int N = a.length;
		int h = 1;
		while (h < N/3)
			h = 3*h + 1;
		while (h >= 1) {
			for (int i = h; i < N; i++) {
				for (int j = i; j >= h && lessPorLocalidad((Comparendo)a[j], (Comparendo)a[j-h]); j -= h)
					exch(a, j, j-h);
			}
			h = h/3;
		}


	}


	//Shell por infraccion 

	public void shellSortPorInfraccion(Comparable[] a)
	{
		int N = a.length;
		int h = 1;
		while (h < N/3)
			h = 3*h + 1;
		while (h >= 1) {
			for (int i = h; i < N; i++) {
				for (int j = i; j >= h && lessPorInfraccion((Comparendo)a[j], (Comparendo)a[j-h]); j -= h)
					exch(a, j, j-h);
			}
			h = h/3;
		}
	}


	// Requerimiento B1:

	public Comparendo darPrimerComparendoPorInfraccion(String pInfraccion)
	{
		Node<Comparendo> actual = datos.darPrimerNodo();
		Comparendo buscado = null; 
		boolean encontrado = false; 

		while(actual != null && !encontrado)
		{
			if(actual.darItem().darInfraccion().equalsIgnoreCase(pInfraccion))
			{
				buscado = actual.darItem();
				encontrado = true; 
			}

			actual = actual.darSiguiente();
		}

		return buscado;

	}

	//Requerimiento B2: 

	public Comparable[] darComparendosPorInfraccion(String pInfraccion)
	{
		Queue<Comparendo> buscados = new Queue<Comparendo>();
		Node<Comparendo> act = datos.darPrimerNodo();

		while(act != null)
		{
			if(act.darItem().darInfraccion().equalsIgnoreCase(pInfraccion))
			{
				buscados.enqueue(act.darItem());
			}

			act = act.darSiguiente();
		}

		Comparable[] listaOrdenada = copiarArreglo(buscados); 
		shellSortPorFecha(listaOrdenada);

		return listaOrdenada;

	}


	//Requerimiento B3

	public ArrayList<String[]> darComparendosPorTipo()
	{
		Comparendo[] totalComparendos = copiarDatos();
		shellSortPorInfraccion(totalComparendos);

		String[] cantidades = new String[3];
		ArrayList<String[]> aDevolver = new ArrayList<String[]>();



		for(int i = 0; i < totalComparendos.length; i++)
		{

			String codigoActual = "";
			boolean cambioCodigo = false; 

			int totalParticulares = 0;
			int totalPublicos = 0;

			int repetidos = i;

			while(!cambioCodigo)
			{
				if(repetidos+1 >= totalComparendos.length-1 )
				{
					cambioCodigo =  true; 
					repetidos  = totalComparendos.length+1;

					if(totalComparendos[i].darTipoServicio().equalsIgnoreCase("Particular"))
					{
						totalParticulares ++;
					}

					if(totalComparendos[i].darTipoServicio().equalsIgnoreCase("Público"))
					{
						totalPublicos ++;
					}


					codigoActual = totalComparendos[i].darInfraccion();
				}

				else
				{
					String codigoA = totalComparendos[repetidos].darInfraccion();
					String codigoSiguiente = totalComparendos[repetidos+1].darInfraccion();

					if(totalComparendos[i].darTipoServicio().equalsIgnoreCase("Particular"))
					{
						totalParticulares ++;
					}

					if(totalComparendos[i].darTipoServicio().equalsIgnoreCase("Público"))
					{
						totalPublicos ++;
					}

					repetidos++;

					if(!codigoA.equals(codigoSiguiente))
					{
						cambioCodigo = true; 
						codigoActual = codigoA;
						i  = repetidos;

					}

				}
			}


			if (totalParticulares != 0 | totalPublicos != 0)
			{
				cantidades = new String[3];

				cantidades[0]= codigoActual;
				cantidades[1]= "" + totalParticulares;
				cantidades[2]= "" + totalPublicos;

				aDevolver.add(cantidades);


			}


		}


		return aDevolver;



	}

	// Requerimiento C1

	public ArrayList<String[]> numeroComparendosPorLocalidadYFecha(String pLocalidad, Date fechaI, Date fechaF)
	{

		Comparendo[] totalComparendos = copiarDatos();
		shellSortPorInfraccion(totalComparendos);

		ArrayList<String[]> aDevolver= new ArrayList<String[]>();
		String[] cantidades = new String[2];

		for(int i = 0; i < totalComparendos.length; i++)
		{

			String codigoActual = "";
			boolean cambioCodigo = false; 

			int total = 0;
			int repetidos = i;

			while(!cambioCodigo)
			{
				if(repetidos+1 >= totalComparendos.length-1 )
				{
					cambioCodigo =  true; 
					repetidos  = totalComparendos.length+1;

					if(totalComparendos[i].darFecha().after(fechaI) && totalComparendos[i].darFecha().before(fechaF) && totalComparendos[i].darLocalidad().equals(pLocalidad))
					{
						total ++;
					}

					codigoActual = totalComparendos[i].darInfraccion();
				}


				else
				{

					String codigoA = totalComparendos[repetidos].darInfraccion();
					String codigoSiguiente = totalComparendos[repetidos+1].darInfraccion();

					if(totalComparendos[i].darFecha().after(fechaI) && totalComparendos[i].darFecha().before(fechaF) && totalComparendos[i].darLocalidad().equals(pLocalidad))
					{
						total ++;
					}

					repetidos++;

					if(!codigoA.equals(codigoSiguiente))
					{
						cambioCodigo = true; 
						codigoActual = codigoA;
						i  = repetidos;

					}
				}

			}


			if (total != 0)
			{
				cantidades = new String[3];

				cantidades[0]= codigoActual;
				cantidades[1]= "" + total;

				aDevolver.add(cantidades);


			}

		}
		
		return aDevolver;

	}


//Requerimiento C2

public ArrayList<ArrayList<String>> darNComparendosPorFecha(int N, Date fechaIncial, Date fechaFinal)
{
	return null;
}


//Requerimiento C3


}
