package test.data_structures;

import static org.junit.Assert.*;
import model.data_structures.Stack;

import org.junit.Before;
import org.junit.Test;

public class TestStack 
{

	private Stack<String> pila; 
	
	@Before
	public void setUpEscenario1()
	{
		pila = new Stack<String>();
		pila.push("E");
		pila.push("N");
		pila.push("O");
		pila.push("D");
	}
	
	@Test
	public void testIsEmpty()
	{
		assertTrue("Se supone que la lista no se encuentra vacía", pila.isEmpty()== false);
	}
	
	@Test
	public void testDarTamanio()
	{
		assertEquals("El tamano no corresponde al esperado", 4, pila.darTamanio());
	}
	
	@Test
	public void testPush()
	{
		pila.push("¡");
		assertEquals("No corresponde al elemento esperado", "¡", pila.peek());
		assertEquals("El tamaño no corresponde al esperado", 5, pila.darTamanio());
		
	}
	
	@Test
	public void testPop()
	{
		assertEquals("No corresponde al elemento esperado", "D",pila.pop());
	}
	
	@Test
	public void testPeek()
	{
		assertEquals("No corresponde al primer elemento de la pila", "D", pila.peek());
	}
}
