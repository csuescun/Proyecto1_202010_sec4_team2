package model.logic;


import model.data_structures.IStack;
import model.data_structures.Queue;
import model.data_structures.Stack;

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
	private Stack<Comparendo> pilaComparendos;
	private Queue<Comparendo> datos;

	/**
	 * Constructor del modelo del mundo con capacidad predefinida
	 */

	public Modelo()
	{
		pilaComparendos = new Stack<Comparendo>();
		datos = new Queue<Comparendo>();
	}

	/**
	 * 
	 */
	public Stack<Comparendo> darPila()
	{
		return pilaComparendos;
	}

	/**
	 * Servicio de consulta de numero de elementos presentes en el modelo 
	 * @return numero de elementos presentes en el modelo
	 */
	public int darTamanoPila()
	{
		return pilaComparendos.darTamanio();
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
				pilaComparendos.push(c);
				datos.enqueue(c);
			}

		} catch (FileNotFoundException | ParseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}


	}

	public Comparendo darPrimeroPila()
	{
		return pilaComparendos.peek();
	}

	
	public Comparendo darPrimeroCola()
	{
		return datos.darPrimerElemento();
	}
	
	public Queue<Comparendo> darUltimosNComparendos(String infraccion, int numero)
	{
		Queue<Comparendo> buscados = new Queue<Comparendo>();
		
		if(pilaComparendos == null)
		{
			cargarDatos();
		}
		
		Iterator<Comparendo> iter = pilaComparendos.iterator();
		Comparendo actual = pilaComparendos.peek();
		
		boolean encontro = false;
		int contador = 0;
		
		while(actual != null && !encontro)
		{
			if(actual.darInfraccion().equals(infraccion))
			{
				contador ++;
				buscados.enqueue(actual);
				
				if(contador  == numero)
				{
					encontro  = true;
				}
			}
			
			if(iter.hasNext())
			{
				actual  = iter.next();
			}
			
			else
			{
				break;
			}
		}
		
		return buscados;
		
		
	}







}
