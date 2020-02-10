package model.data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class Stack<T> implements IStack<T> 
{

	// -----------------------------------------------------------------
	// Atributos
	// -----------------------------------------------------------------

	/**
	 * 
	 */

	private Node<T> primerNodo;

	/**
	 * 
	 */

	private int tam;


	// -----------------------------------------------------------------
	// Constructores
	// -----------------------------------------------------------------

	public Stack()
	{
		primerNodo = null; 
		tam = 0;
	}

	// -----------------------------------------------------------------
	// Clases privadas
	// -----------------------------------------------------------------

	/**
	 * 
	 */

	private class ListIterator implements Iterator<T>
	{
		private Node<T> actual  = primerNodo;

		public boolean hasNext()
		{
			return actual != null; 
		}

		public T next()
		{
			if(!hasNext())
			{
				throw new NoSuchElementException();
			}

			T item = actual.darItem();
			actual = actual.darSiguiente();

			return item;
		}

		public void remove() 
		{ 

		}
	}


	// -----------------------------------------------------------------
	// Métodos
	// -----------------------------------------------------------------

	public boolean isEmpty()
	{
		return primerNodo == null; 
	}

	public int darTamanio()
	{
		return tam;
	}

	public void push(T item)
	{
		Node<T> nuevo = new Node<T>(item);
		Node<T> viejo = primerNodo; 

		primerNodo = nuevo; 
		nuevo.asignarSiguiente(viejo);
		tam++;
	}

	public T pop()
	{
		if(this.isEmpty())
		{
			return null;
		}

		else
		{
			Node<T> eliminado = primerNodo; 
			primerNodo = eliminado.darSiguiente();
			tam --; 

			return eliminado.darItem(); 
		}
	}


	public T peek()
	{
		if(this.isEmpty())
		{
			return null;
		}

		else
		{
			return primerNodo.darItem();
		}
	}

	/**
	 * Retorna un iterador.
	 * @return El iterador.
	 */

	public Iterator<T> iterator() 
	{
		return new ListIterator();
	}


}
