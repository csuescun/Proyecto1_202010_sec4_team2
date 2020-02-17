package model.logic;



import model.data_structures.Queue;


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

	public static String PATH = "./data/comparendos_dei_2018_small.geojson";

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


	// Requerimiento B1:

	public Comparendo darPrimerComparendoPorInfraccion(String pInfraccion)
	{
		return null;
	}

	//Requerimiento B2: 

	public Queue<Comparendo> darComparendosPorInfraccion(String pInfraccion)
	{
		return null; 
	}


	//Requerimiento B3

	public ArrayList<ArrayList<String>> darComparendosPorTipo()
	{
		return null;
	}

	// Requerimiento C1

	public ArrayList<ArrayList<String>> numeroComparendosPorLocalidadYFecha(String pLocalidad, Date fechaIncial, Date fechaFinal)
	{
		return null;
	}

	//Requerimiento C2

	public ArrayList<ArrayList<String>> darNComparendosPorFecha(int N, Date fechaIncial, Date fechaFinal)
	{
		return null;
	}


	//Requerimiento C3


}
