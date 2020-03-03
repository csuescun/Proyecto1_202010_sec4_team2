package test.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import model.data_structures.*;
import model.logic.*;

public class TestModelo 
{

	private Modelo modelo; 


	@Before
	public void setUpEscenario1()
	{
		modelo = new Modelo();
		modelo.cargarDatosSmall();
	}


	@Test
	public void testModelo()
	{
		assertTrue(modelo != null);
	}


	@Test 
	public void testDarTamanoCola()
	{
		assertEquals("El tamaño de la cola no es el esperado",20, modelo.darTamanoCola());
	}

	@Test
	public void testDarPrimeroCola()
	{
		assertEquals("El primer comparendo no es el esperado", 29042, modelo.darPrimeroCola().darObjectID());
	}

	@Test
	public void testDarUltimoCola()
	{
		assertEquals("El ultimo comparendo no es el esperado", 209146, modelo.darUltimoCola().darObjectID());
	}


	//Test A1
	public void testDarPrimeroLocalidad()
	{
		Comparendo buscado = modelo.darPrimeroLocalidad("TUNJUELITO");
		assertEquals("El comparendo encontrado no es el esperado", 509329, buscado.darObjectID());
		
		buscado  = modelo.darPrimeroLocalidad("BOSA");
		assertEquals("El comparendo encontrado no es el esperado", 519553, buscado.darObjectID());

	}
	//Test B1
	@Test
	public void testDarPrimerComparendoPorInfraccion()
	{
		Comparendo buscado = modelo.darPrimerComparendoPorInfraccion("C02");
		assertEquals("El comparendo encontrado no es el esperado", 29042, buscado.darObjectID() );


		buscado  = modelo.darPrimerComparendoPorInfraccion("C35");
		assertEquals("El comparendo encontrado no es el esperado", 509329, buscado.darObjectID());

	} 


	//Test B2
	public void testDarComparendosPorInfraccion()
	{
		Comparable[] testear = modelo.darComparendosPorInfraccion("C02");

		assertEquals("El numero de comparendos encontrados no es el esperado", 9, testear.length);

		for(int i =0; i< testear.length; i++ )
		{
			Comparendo actual = (Comparendo) testear[i];
			assertEquals("El comparendo no tiene la infraccion deseada", "C02", actual.darInfraccion());
		}

	}

	//Test B3
	public void testDarComparendosPorTipo()
	{
		ArrayList<String[]> testear =  modelo.darComparendosPorTipo();

		//Chequear algunos codigos
		assertEquals("El codigo de la infraccion no corresponde al esperado", "A08", testear.get(0)[0]);
		assertEquals("El codigo de la infraccion no corresponde al esperado", "B02", testear.get(1)[0]);
		assertEquals("El codigo de la infraccion no corresponde al esperado", "D02", testear.get(8)[0]);
		assertEquals("El codigo de la infraccion no corresponde al esperado", "H03", testear.get(9)[0]);


		//Chequear algunos datos

		assertEquals("El numero de comparendos de tipo privado no es el esperado", 1, testear.get(0)[1]);
		assertEquals("El numero de comparendos de tipo publico no es el esperado", 0, testear.get(0)[2]);

		assertEquals("El numero de comparendos de tipo privado no es el esperado", 1, testear.get(1)[1]);
		assertEquals("El numero de comparendos de tipo publico no es el esperado", 0, testear.get(1)[2]);

		assertEquals("El numero de comparendos de tipo privado no es el esperado", 8, testear.get(2)[1]);
		assertEquals("El numero de comparendos de tipo publico no es el esperado", 1, testear.get(2)[2]);

		assertEquals("El numero de comparendos de tipo privado no es el esperado", 1, testear.get(8)[1]);
		assertEquals("El numero de comparendos de tipo publico no es el esperado", 0, testear.get(8)[2]);

		assertEquals("El numero de comparendos de tipo privado no es el esperado", 1, testear.get(9)[1]);
		assertEquals("El numero de comparendos de tipo publico no es el esperado", 0, testear.get(9)[2]);




	}


	//Test A1
	public void testNumeroComparendosPorLocalidadYFecha()
	{
		try 
		{

			SimpleDateFormat parser=new SimpleDateFormat("yyyy/MM/dd");

			Date fechaI= parser.parse("2018/01/01");
			Date fechaF = parser.parse("2018/12/31");

			ArrayList<String[]> testear = modelo.numeroComparendosPorLocalidadYFecha("TEUSAQUILLO", fechaI, fechaF );

			assertEquals("El numero de comparendos en esta localidad y entre estas fechas no es el esperado", 3, testear.size());

			assertEquals("El codigo de la infraccion no es el esperado", "A08", testear.get(0)[0]);
			assertEquals("El numero de comparendos por infraccion no es el esperado", 1, testear.get(0)[1]);

			assertEquals("El codigo de la infraccion no es el esperado", "C02", testear.get(1)[0]);
			assertEquals("El numero de comparendos por infraccion no es el esperado", 1, testear.get(1)[1]);

			assertEquals("El codigo de la infraccion no es el esperado", "C31", testear.get(2)[0]);
			assertEquals("El numero de comparendos por infraccion no es el esperado", 1, testear.get(2)[1]);
		}

		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}


	//Test A2
	public void testDarNComparendosPorFecha()
	{
		try 
		{

			SimpleDateFormat parser=new SimpleDateFormat("yyyy/MM/dd");

			Date fechaI= parser.parse("2018/01/01");
			Date fechaF = parser.parse("2018/12/31");

			ArrayList<String[]> testear = modelo.darNComparendosPorFecha(3, fechaI, fechaF);

			assertEquals("El numero de infracciones no es el deseado", 3, testear.size());

			assertEquals("El codigo de la infraccion no es el esperado", "C02", testear.get(0)[0]);
			assertEquals("El numero de comparendos por infraccion no es el esperado", 9, testear.get(0)[1]);

			assertEquals("El codigo de la infraccion no es el esperado", "C14", testear.get(1)[0]);
			assertEquals("El numero de comparendos por infraccion no es el esperado", 2, testear.get(1)[1]);


			assertEquals("El codigo de la infraccion no es el esperado", "C32", testear.get(2)[0]);
			assertEquals("El numero de comparendos por infraccion no es el esperado", 2, testear.get(2)[1]);

		}

		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}

	}
}
