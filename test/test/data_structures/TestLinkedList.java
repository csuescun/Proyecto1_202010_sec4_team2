package test.data_structures;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import model.data_structures.*;

public class TestLinkedList 
{

	private LinkedList<String> lista;
	
	public void setUpEscenario1()
	{
		lista = new LinkedList<String>();
		lista.agregarAlComienzo("D");
		lista.agregarAlFinal("O");
		lista.agregarAlFinal("N");
		lista.agregarAlFinal("E");
		
	}
	
	@Test
	public void testDarTamanio()
	{
		setUpEscenario1();
		assertEquals("El tamano no corresponde al esperado", 4, lista.darTamanio());
	}
	
	@Test 
	public void testIsEmpty()
	{
		setUpEscenario1();
		assertTrue("Se supone que la lista no se encuentra vacía", lista.isEmpty()== false);
	}
	
	
	@Test 
	public void testDarPrimerElemento()
	{
		setUpEscenario1();
		assertEquals("No corresponde al primer elemento", "D", lista.darPrimerElemento().darItem());
	}
	
	@Test
	public void testDarElementoPosicion()
	{
		setUpEscenario1();
		assertEquals("No corresponde al elemento", "D", lista.darElementoPosicion(0).darItem());
		assertEquals("No corresponde al elemento", "O", lista.darElementoPosicion(1).darItem());
		assertEquals("No corresponde al elemento", "N", lista.darElementoPosicion(2).darItem());
		assertEquals("No corresponde al elemento", "E", lista.darElementoPosicion(3).darItem());
		
	}
	
	@Test 
	public void testAgregarAlComienzo()
	{
		setUpEscenario1();
		lista.agregarAlComienzo("¡");
		assertEquals("No corresponde al elemento esperado", "¡", lista.darPrimerElemento().darItem());
		assertEquals("El tamaño no corresponde al esperado", 5, lista.darTamanio());
	}
	
	@Test
	public void testAgregarAlFinal()
	{
		setUpEscenario1();
		lista.agregarAlFinal("!");
		Node<String> cabeza = lista.darPrimerElemento();
		
		for(int i = 0; i < 4; i++)
		{
			cabeza = cabeza.darSiguiente(); 
		}
		
		assertEquals("No corresponde al elemento esperado", "!", cabeza.darItem());
		
	}
	
	@Test
	public void testAgregarEnPosicion()
	{
		setUpEscenario1();
		lista.agregarEnPosicion(2, "-");
		
		Node<String> cabeza = lista.darPrimerElemento();
		cabeza = cabeza.darSiguiente().darSiguiente();
		
		assertEquals("No corresponde al elemento esperado", "-", cabeza.darItem());
	}
	
	@Test
	public void testEliminarPrimero()
	{
		setUpEscenario1();
		lista.eliminarPrimero();
		
		assertEquals("No corresponde al elemento esperado", "O", lista.darPrimerElemento().darItem());
	}
	
	@Test
	public void testEliminar()
	{
		setUpEscenario1();
		lista.eliminar(1);
		
		assertEquals("No corresponde al elemento esperado", "N", lista.darPrimerElemento().darSiguiente().darItem());
	}

	
}
